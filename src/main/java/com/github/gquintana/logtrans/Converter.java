package com.github.gquintana.logtrans;

import java.util.function.Function;

public interface Converter<T> {
    T convert(Object o);

    default Function<Object, T> toFunction() {
        return this::convert;
    }
}
