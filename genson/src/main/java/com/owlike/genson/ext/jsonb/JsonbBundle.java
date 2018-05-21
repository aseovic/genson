package com.owlike.genson.ext.jsonb;

import com.owlike.genson.GensonBuilder;
import com.owlike.genson.Trilean;
import com.owlike.genson.ext.GensonBundle;
import com.owlike.genson.reflect.BeanMutatorAccessorResolver;
import com.owlike.genson.reflect.PropertyNameResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Genson bundle which allows usage of Jsonb annotations for serialization.
 *
 * @author Aleksandar Seovic  2018.05.21
 */
public class JsonbBundle extends GensonBundle {
    @Override
    public void configure(GensonBuilder builder) {
        builder
              .with(new JsonbAnnotationsResolver())
              .with(new JsonbNameResolver());
    }

    private static class JsonbAnnotationsResolver implements BeanMutatorAccessorResolver {
        @Override
        public Trilean isCreator(Constructor<?> constructor, Class<?> fromClass) {
            return null;
        }

        @Override
        public Trilean isCreator(Method method, Class<?> fromClass) {
            return null;
        }

        @Override
        public Trilean isAccessor(Field field, Class<?> fromClass) {
            return null;
        }

        @Override
        public Trilean isAccessor(Method method, Class<?> fromClass) {
            return null;
        }

        @Override
        public Trilean isMutator(Field field, Class<?> fromClass) {
            return null;
        }

        @Override
        public Trilean isMutator(Method method, Class<?> fromClass) {
            return null;
        }
    }

    private class JsonbNameResolver implements PropertyNameResolver {
        @Override
        public String resolve(int parameterIdx, Constructor<?> fromConstructor) {
            return null;
        }

        @Override
        public String resolve(int parameterIdx, Method fromMethod) {
            return null;
        }

        @Override
        public String resolve(Field fromField) {
            return null;
        }

        @Override
        public String resolve(Method fromMethod) {
            return null;
        }
    }
}
