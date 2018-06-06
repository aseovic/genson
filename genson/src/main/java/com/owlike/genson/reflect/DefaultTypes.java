package com.owlike.genson.reflect;

import com.owlike.genson.stream.ValueType;

import java.util.HashMap;
import java.util.Map;

/**
 * Default type mappings.
 * <p/>
 * This class allows users to change how various {@link ValueType}
 * enum values should be mapped to Java classes during deserialization.
 *
 * @author Aleksandar Seovic  2018.06.05
 */
public class DefaultTypes {
    private final Map<ValueType, Class<?>> mappings = new HashMap<>();

    /**
     * Default constructor.
     */
    public DefaultTypes() {
        for (ValueType type : ValueType.values()) {
            mappings.put(type, type.toClass());
        }
    }

    /**
     * Set the class to use for deserialization of the
     * specified {@link ValueType}.
     *
     * @param type  the {@code ValueType} to set the default class for
     * @param clazz the default class for the specified {@code ValueType}
     *
     * @return this instance
     */
    public DefaultTypes setClass(ValueType type, Class<?> clazz) {
        mappings.put(type, clazz);
        return this;
    }

    /**
     * Get the class to use for deserialization of the
     * specified {@link ValueType}.
     *
     * @param type  the {@code ValueType} to get the default class for
     *
     * @return the default class for the specified {@code ValueType}
     */
    public Class<?> getClass(ValueType type) {
        return mappings.get(type);
    }
}
