package com.owlike.genson.ext.jackson;

import com.owlike.genson.ext.GensonBundle;

import com.owlike.genson.ext.common.BaseAnnotationTest;


public class JacksonAnnotationsSupportTest extends BaseAnnotationTest {
  @Override
  protected GensonBundle createTestBundle() {
    return new JacksonBundle();
  }
}
