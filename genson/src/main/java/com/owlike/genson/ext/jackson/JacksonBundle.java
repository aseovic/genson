package com.owlike.genson.ext.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.owlike.genson.GensonBuilder;

import com.owlike.genson.ext.GensonBundle;

import com.owlike.genson.reflect.BeanMutatorAccessorResolver.AnnotationPropertyResolver;
import com.owlike.genson.reflect.PropertyNameResolver.AnnotationPropertyNameResolver;

/**
 * Genson bundle which allows usage of Jackson annotations for serialization.
 *
 * @author Aleksandar Seovic  2018.05.21
 */
public class JacksonBundle extends GensonBundle {
    @Override
    public void configure(GensonBuilder builder) {
        builder.with(new JacksonAnnotationPropertyResolver()).with(new JacksonAnnotationPropertyNameResolver());
    }

    private static class JacksonAnnotationPropertyResolver extends AnnotationPropertyResolver {
      public JacksonAnnotationPropertyResolver() {
        super(JsonProperty.class, JsonIgnore.class, JsonCreator.class);
      }
    }

    private static class JacksonAnnotationPropertyNameResolver extends AnnotationPropertyNameResolver<JsonProperty> {
      public JacksonAnnotationPropertyNameResolver() {
        super(JsonProperty.class);
      }

      @Override
      protected String getNameFromAnnotation(final JsonProperty annotation) {
        return annotation.value();
      }
    }
}
