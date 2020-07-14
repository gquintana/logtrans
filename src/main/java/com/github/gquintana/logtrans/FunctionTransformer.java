package com.github.gquintana.logtrans;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

public class FunctionTransformer<T> implements Transformer {
    private final String source;
    private final String target;
    private final Function<Object, T> function;

    private FunctionTransformer(String source, String target, Function<Object, T> function) {
        this.source = source;
        this.target = target;
        this.function = function;
    }

    @Override
    public Collection<Event> transform(Event event) {
        Object value = event.get(source);
        value = Fields.apply(value, this::doTransform);
        event.set(target, value);
        return Collections.singletonList(event);
    }

    private T doTransform(Object value) {
        return value == null ? null : function.apply(value);
    }

    public static <T>  Builder<T> field(String source) {
        return new FunctionTransformer.Builder<T>().source(source);
    }

    public static <T>  Builder<T> field(String source, Function<Object, T> function) {
        return new Builder<T>().source(source).function(function);
    }

    public static class Builder<T> implements Transformer.Builder<FunctionTransformer<T>> {
        private String source;
        private String target;
        private Function<Object, T> function;

        public Builder<T> source(String source) {
            this.source = source;
            return this;
        }

        public Builder<T> target(String target) {
            this.target = target;
            return this;
        }


        public Builder<T> function(Function<Object, T> function) {
            this.function = function;
            return this;
        }

        public FunctionTransformer<T> build() {
            Objects.requireNonNull(source, "source");
            if (function == null) {
                function = s -> (T) s;
            }
            if (target == null) {
                target = source;
            }
            return new FunctionTransformer<T>(source, target, function);
        }
    }
}
