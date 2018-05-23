package com.owlike.genson.ext.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.owlike.genson.GensonBuilder;
import com.owlike.genson.Trilean;
import com.owlike.genson.ext.GensonBundle;
import com.owlike.genson.reflect.BeanMutatorAccessorResolver;
import com.owlike.genson.reflect.PropertyNameResolver;

import java.lang.annotation.Annotation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Arrays;

/**
 * Genson bundle which allows usage of Jackson annotations for serialization.
 *
 * @author Aleksandar Seovic  2018.05.21
 */
public class JacksonBundle extends GensonBundle {
    @Override
    public void configure(GensonBuilder builder) {
        builder.with(new JacksonAnnotationsResolver())
            .with(new JacksonNameResolver());
    }

    private static class JacksonAnnotationsResolver implements BeanMutatorAccessorResolver {
        @Override
        public Trilean isCreator(Constructor<?> constructor, Class<?> fromClass) {
            if (find(JsonCreator.class, constructor, fromClass) != null) {
                return Trilean.TRUE;
            }
            return Trilean.UNKNOWN;
        }

        @Override
        public Trilean isCreator(Method method, Class<?> fromClass) {
            if (find(JsonCreator.class, method, fromClass) != null) {
                return Trilean.TRUE;
            }
            return Trilean.UNKNOWN;
        }

        @Override
        public Trilean isAccessor(Field field, Class<?> fromClass) {
            if (ignore(field, field.getType())) {
                return Trilean.FALSE;
            }
            if (include(field, field.getType())) {
                return Trilean.TRUE;
            }
            return Trilean.FALSE;
        }

        @Override
        public Trilean isAccessor(Method method, Class<?> fromClass) {
            if (ignore(method, method.getReturnType())) return Trilean.FALSE;

            String name = null;
            if (method.getName().startsWith("get") && method.getName().length() > 3)
                name = method.getName().substring(3);
            else if (method.getName().startsWith("is") && method.getName().length() > 2
                    && method.getReturnType() == boolean.class
                    || method.getReturnType() == Boolean.class)
                name = method.getName().substring(2);

            if (name != null) {
                if (include(method, method.getReturnType())) return Trilean.TRUE;
                if (find(JsonIgnore.class, fromClass, "set" + name, method.getReturnType()) != null)
                    return Trilean.FALSE;
            }

            return Trilean.FALSE;
        }

        @Override
        public Trilean isMutator(Field field, Class<?> fromClass) {
            if (ignore(field, field.getType())) {
                return Trilean.FALSE;
            }
            if (include(field, field.getType())) {
                return Trilean.TRUE;
            }
            return Trilean.FALSE;
        }

        @Override
        public Trilean isMutator(Method method, Class<?> fromClass) {
            Class<?> paramClass = method.getParameterTypes().length == 1 ? method
                    .getParameterTypes()[0] : Object.class;
            if (ignore(method, paramClass)) return Trilean.FALSE;

            if (method.getName().startsWith("set") && method.getName().length() > 3) {
                if (include(method, method.getReturnType())) return Trilean.TRUE;

                String name = method.getName().substring(3);

                // Exclude it if there is a corresponding accessor annotated with JsonIgnore
                if (find(JsonIgnore.class, fromClass, "get" + name) != null) return Trilean.FALSE;
                if (paramClass.equals(boolean.class) || paramClass.equals(Boolean.class)) {
                    if (find(JsonIgnore.class, fromClass, "is" + name) != null)
                        return Trilean.FALSE;
                }
            }

            return Trilean.FALSE;
        }

        // ---- private methods ---------------------------------------------

        private static boolean ignore(AccessibleObject property, Class<?> ofType) {
            return find(JsonIgnore.class, property, ofType) != null;
        }

        private static boolean include(AccessibleObject property, Class<?> ofType) {
            return find(JsonProperty.class, property, ofType) != null;
        }

        private static <A extends Annotation> A find(Class<A> annotation, AccessibleObject onObject,
                                              Class<?> onClass) {
            A ann = onObject.getAnnotation(annotation);
            if (ann != null) return ann;
            return find(annotation, onClass);
        }

        private static <A extends Annotation> A find(Class<A> annotation, Class<?> onClass) {
            A ann = onClass.getAnnotation(annotation);
            if (ann == null && onClass.getPackage() != null)
                ann = onClass.getPackage().getAnnotation(annotation);
            return ann;
        }

        private static <A extends Annotation> A find(Class<A> annotation, Class<?> inClass, String methodName,
                                                     Class<?>... parameterTypes) {
            for (Class<?> clazz = inClass; clazz != null; clazz = clazz.getSuperclass()) {
                try {
                    for (Method m : clazz.getDeclaredMethods())
                        if (m.getName().equals(methodName)
                                && Arrays.equals(m.getParameterTypes(), parameterTypes))
                            if (m.isAnnotationPresent(annotation))
                                return m.getAnnotation(annotation);
                            else
                                break;

                } catch (SecurityException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }
    }

    private static class JacksonNameResolver implements PropertyNameResolver {
        @Override
        public String resolve(int parameterIdx, Constructor<?> fromConstructor) {
            return scanForJsonPropertyAnnotation(parameterIdx, fromConstructor);
        }

        @Override
        public String resolve(int parameterIdx, Method fromMethod) {
            return scanForJsonPropertyAnnotation(parameterIdx, fromMethod);
        }

        @Override
        public String resolve(Field fromField) {
            return extractName(fromField);
        }

        @Override
        public String resolve(Method fromMethod) {
            return extractName(fromMethod);
        }

        // ---- private methods ---------------------------------------------

        private static String extractName(AccessibleObject object) {
            JsonProperty attr = object.getAnnotation(JsonProperty.class);
            String result = ((attr != null) ? attr.value() : null);
            return ((result == null || result.isEmpty()) ? null : result);
        }

        private static String scanForJsonPropertyAnnotation(int paramIndex, Executable executable) {
            Annotation[][] parameterAnnotations = executable.getParameterAnnotations();
            if (parameterAnnotations.length == 0) {
                return null;
            }
            Annotation[] paramAnnotationAtIndex = parameterAnnotations[paramIndex];
            for (Annotation a : paramAnnotationAtIndex) {
                if (JsonProperty.class.equals(a.annotationType())) {
                    String result = ((JsonProperty) a).value();
                    return ((result.isEmpty()) ? null : result);
                }
            }
            return null;
        }
    }
}
