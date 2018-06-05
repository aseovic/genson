package com.owlike.genson;

import java.util.Objects;

/**
 * @author Aleksandar Seovic  2018.06.03
 */
@FunctionalInterface
public interface Modifier<T> {
    T apply(T obj);

   default Modifier<T> andThen(Modifier<T> after) {
        Objects.requireNonNull(after);
        return t -> after.apply(apply(t));
    }

    static <T> Modifier<T> identity() {
        return t -> t;
    }
}