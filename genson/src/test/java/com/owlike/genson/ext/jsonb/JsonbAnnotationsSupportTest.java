package com.owlike.genson.ext.jsonb;

import com.owlike.genson.ext.GensonBundle;

import com.owlike.genson.ext.common.BaseAnnotationTest;

public class JsonbAnnotationsSupportTest extends BaseAnnotationTest {
  @Override
  protected GensonBundle createTestBundle() {
    return new JsonbBundle();
  }
}
