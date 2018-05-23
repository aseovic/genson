package com.owlike.genson.ext.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class JacksonAnnotationsSupportTest {

    private Genson genson;

    // ---- Test Setup ------------------------------------------------------

    @Before
    public void setUp() {
        genson = new GensonBuilder().withBundle(new JacksonBundle())
                         .useClassMetadata(true)
                         .useConstructorWithArguments(true)
                         .useIndentation(true)
                         .create();
    }


    // ---- Test Methods ----------------------------------------------------

    @Test
    public void testJsonPropertySerializationOnField() {
        final String expected = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnField\",\n" +
                "  \"name\":\"Bilbo\"\n" +
                "}";
        assertEquals(expected, genson.serialize(new JsonPropertyOnField("Bilbo")));
    }

    @Test
    public void testJsonPropertyDeserializationOnField() {
        final String input = "{\n" +
            "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnField\",\n" +
            "  \"name\":\"Bilbo\"\n" +
            "}";
        assertEquals(new JsonPropertyOnField("Bilbo"), genson.deserialize(input, JsonPropertyOnField.class));
    }

    @Test
    public void testJsonPropertySerializationOnGetterSetter() {
        final String expected = "{\n" +
            "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnGetterSetter\",\n" +
            "  \"name\":\"Bilbo\"\n" +
            "}";
        assertEquals(expected, genson.serialize(new JsonPropertyOnGetterSetter("Bilbo")));
    }

    @Test
    public void testJsonPropertyDeserializationOnGetterSetter() {
        final String input = "{\n" +
            "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnGetterSetter\",\n" +
            "  \"name\":\"Bilbo\"\n" +
            "}";
        assertEquals(new JsonPropertyOnGetterSetter("Bilbo"),
                     genson.deserialize(input, JsonPropertyOnGetterSetter.class));
    }

    @Test
    public void testJsonPropertySerializationOnFieldIgnored() {
        final String expected = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnFieldIgnored\"\n" +
                "}";
        assertEquals(expected, genson.serialize(new JsonPropertyOnFieldIgnored("Bilbo")));
    }

    @Test
    public void testJsonPropertyDeserializationOnFieldIgnored() {
        final String input = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnFieldIgnored\",\n" +
                "  \"name\":\"Bilbo\"\n" +
                "}";
        assertEquals(new JsonPropertyOnFieldIgnored(), genson.deserialize(input, JsonPropertyOnFieldIgnored.class));
    }

    @Test
    public void testJsonPropertySerializationOnGetterIgnoredSetter() {
        final String expected = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnGetterIgnoredSetter\"\n" +
                "}";
        assertEquals(expected, genson.serialize(new JsonPropertyOnGetterIgnoredSetter("Bilbo")));
    }

    @Test
    public void testJsonPropertyDeserializationOnGetterIgnoredSetter() {
        final String input = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnGetterIgnoredSetter\",\n" +
                "  \"name\":\"Bilbo\"\n" +
                "}";
        assertEquals(new JsonPropertyOnGetterIgnoredSetter("Bilbo"),
                genson.deserialize(input, JsonPropertyOnGetterIgnoredSetter.class));
    }

    @Test
    public void testJsonPropertySerializationOnGetterSetterIgnored() {
        final String expected = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnGetterSetterIgnored\",\n" +
                "  \"name\":\"Bilbo\"\n" +
                "}";
        assertEquals(expected, genson.serialize(new JsonPropertyOnGetterSetterIgnored("Bilbo")));
    }

    @Test
    public void testJsonPropertyDeserializationOnGetterSetterIgnored() {
        final String input = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnGetterSetterIgnored\",\n" +
                "  \"name\":\"Bilbo\"\n" +
                "}";
        assertEquals(new JsonPropertyOnGetterSetterIgnored(),
                genson.deserialize(input, JsonPropertyOnGetterSetterIgnored.class));
    }

    @Test
    public void testJsonPropertySerializationOnFieldCustomName() {
        final String expected = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnFieldCustomName\",\n" +
                "  \"n\":\"Bilbo\"\n" +
                "}";
        assertEquals(expected, genson.serialize(new JsonPropertyOnFieldCustomName("Bilbo")));
    }

    @Test
    public void testJsonPropertyDeserializationOnFieldCustomName() {
        final String input = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnFieldCustomName\",\n" +
                "  \"n\":\"Bilbo\"\n" +
                "}";
        assertEquals(new JsonPropertyOnFieldCustomName("Bilbo"), genson.deserialize(input, JsonPropertyOnFieldCustomName.class));
    }

    @Test
    public void testJsonPropertySerializationOnGetterSetterCustomName() {
        final String expected = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnGetterSetterCustomName\",\n" +
                "  \"n\":\"Bilbo\"\n" +
                "}";
        assertEquals(expected, genson.serialize(new JsonPropertyOnGetterSetterCustomName("Bilbo")));
    }

    @Test
    public void testJsonPropertyDeserializationOnGetterSetterCustomName() {
        final String input = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonPropertyOnGetterSetterCustomName\",\n" +
                "  \"n\":\"Bilbo\"\n" +
                "}";
        assertEquals(new JsonPropertyOnGetterSetterCustomName("Bilbo"), genson.deserialize(input, JsonPropertyOnGetterSetterCustomName.class));
    }

    @Test
    public void testJsonCreatorDeserialization() {
        final String input = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonCreator\",\n" +
                "  \"name\":\"Bilbo\"\n" +
                "}";
        assertEquals(new JsonCreator("Bilbo"), genson.deserialize(input, JsonCreator.class));
    }

    @Test
    public void testJsonCreatorFactoryMethodDeserialization() {
        final String input = "{\n" +
                "  \"@class\":\"com.owlike.genson.ext.jackson.JacksonAnnotationsSupportTest$JsonCreatorFactoryMethod\",\n" +
                "  \"name\":\"Bilbo\"\n" +
                "}";
        assertEquals(JsonCreatorFactoryMethod.newInstance("Bilbo"), genson.deserialize(input, JsonCreatorFactoryMethod.class));
    }

    // ---- Test Types ------------------------------------------------------

    public static class JsonPropertyOnField {

        @JsonProperty
        private String name;

        @SuppressWarnings("unused")
        public JsonPropertyOnField() {}
        JsonPropertyOnField(final String name) {
            this.name = name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof JsonPropertyOnField)) return false;
            final JsonPropertyOnField that = (JsonPropertyOnField) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class JsonPropertyOnGetterSetter {

        private String name;

        @SuppressWarnings("unused")
        public JsonPropertyOnGetterSetter() {}
        JsonPropertyOnGetterSetter(final String name) {
            this.name = name;
        }

        @JsonProperty
        public String getName() {
            return name;
        }

        @JsonProperty
        public void setName(final String name) {
            this.name = name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof JsonPropertyOnGetterSetter)) return false;
            final JsonPropertyOnGetterSetter that = (JsonPropertyOnGetterSetter) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class JsonPropertyOnFieldIgnored {

        @JsonProperty
        @JsonIgnore
        private String name;

        @SuppressWarnings("WeakerAccess")
        public JsonPropertyOnFieldIgnored() {}
        JsonPropertyOnFieldIgnored(final String name) {
            this.name = name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof JsonPropertyOnFieldIgnored)) return false;
            final JsonPropertyOnFieldIgnored that = (JsonPropertyOnFieldIgnored) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class JsonPropertyOnGetterIgnoredSetter {

        private String name;

        @SuppressWarnings("unused")
        public JsonPropertyOnGetterIgnoredSetter() {}
        JsonPropertyOnGetterIgnoredSetter(final String name) {
            this.name = name;
        }

        @JsonProperty
        @JsonIgnore
        public String getName() {
            return name;
        }

        @JsonProperty
        public void setName(final String name) {
            this.name = name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof JsonPropertyOnGetterIgnoredSetter)) return false;
            final JsonPropertyOnGetterIgnoredSetter that = (JsonPropertyOnGetterIgnoredSetter) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class JsonPropertyOnGetterSetterIgnored {

        private String name;

        @SuppressWarnings("WeakerAccess")
        public JsonPropertyOnGetterSetterIgnored() {}
        JsonPropertyOnGetterSetterIgnored(final String name) {
            this.name = name;
        }

        @JsonProperty
        public String getName() {
            return name;
        }

        @JsonProperty
        @JsonIgnore
        public void setName(final String name) {
            this.name = name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof JsonPropertyOnGetterSetterIgnored)) return false;
            final JsonPropertyOnGetterSetterIgnored that = (JsonPropertyOnGetterSetterIgnored) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class JsonPropertyOnFieldCustomName {

        @JsonProperty("n")
        private String name;

        @SuppressWarnings("unused")
        public JsonPropertyOnFieldCustomName() {}
        JsonPropertyOnFieldCustomName(final String name) {
            this.name = name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof JsonPropertyOnFieldCustomName)) return false;
            final JsonPropertyOnFieldCustomName that = (JsonPropertyOnFieldCustomName) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class JsonPropertyOnGetterSetterCustomName {

        private String name;

        @SuppressWarnings("unused")
        public JsonPropertyOnGetterSetterCustomName() {}
        JsonPropertyOnGetterSetterCustomName(final String name) {
            this.name = name;
        }

        @JsonProperty("n")
        public String getName() {
            return name;
        }

        @JsonProperty("n")
        public void setName(final String name) {
            this.name = name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof JsonPropertyOnGetterSetterCustomName)) return false;
            final JsonPropertyOnGetterSetterCustomName that = (JsonPropertyOnGetterSetterCustomName) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class JsonCreator {

        @JsonProperty
        private String name;

        @SuppressWarnings("WeakerAccess")
        @com.fasterxml.jackson.annotation.JsonCreator
        public JsonCreator(@JsonProperty("name") String name) {
            this.name = name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof JsonCreator)) return false;
            final JsonCreator that = (JsonCreator) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class JsonCreatorFactoryMethod {

        @JsonProperty
        private String name;

        private JsonCreatorFactoryMethod(String name) {
            this.name = name;
        }

        @SuppressWarnings("WeakerAccess")
        @com.fasterxml.jackson.annotation.JsonCreator
        public static JsonCreatorFactoryMethod newInstance(@JsonProperty("name") String name) {
            return new JsonCreatorFactoryMethod(name);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof JsonCreatorFactoryMethod)) return false;
            final JsonCreatorFactoryMethod that = (JsonCreatorFactoryMethod) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
