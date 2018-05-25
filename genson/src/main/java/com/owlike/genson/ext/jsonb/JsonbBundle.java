package com.owlike.genson.ext.jsonb;

import com.owlike.genson.GensonBuilder;

import com.owlike.genson.ext.GensonBundle;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import static com.owlike.genson.reflect.PropertyNameResolver.AnnotationPropertyNameResolver;
import static com.owlike.genson.reflect.BeanMutatorAccessorResolver.AnnotationPropertyResolver;

/**
 * Genson bundle which allows usage of JSON-B annotations for serialization.
 *
 * @author Aleksandar Seovic  2018.05.21
 */
public class JsonbBundle extends GensonBundle {
  @Override
  public void configure(GensonBuilder builder) {
    builder.with(new JsonbAnnotationPropertyResolver()).with(new JsonbAnnotationPropertyNameResolver());
  }

  private static class JsonbAnnotationPropertyResolver extends AnnotationPropertyResolver {
    public JsonbAnnotationPropertyResolver() {
      super(JsonbProperty.class, JsonbTransient.class, JsonbCreator.class);
    }
  }

  private static class JsonbAnnotationPropertyNameResolver extends AnnotationPropertyNameResolver<JsonbProperty> {
    public JsonbAnnotationPropertyNameResolver() {
      super(JsonbProperty.class);
    }

    @Override
    protected String getNameFromAnnotation(final JsonbProperty annotation) {
      return annotation.value();
    }
  }
}
