package com.github.gquintana.logtrans;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RouterTransformer<K> implements Transformer {
    private final Field<K> source;
    private final Map<K, Transformer> transformers;
    private final Transformer defaultTransformer;

    private RouterTransformer(Field<K> source, Map<K, Transformer> transformers, Transformer defaultTransformer) {
        this.source = source;
        this.transformers = transformers;
        this.defaultTransformer = defaultTransformer;
    }

    @Override
    public Collection<Event> transform(Event event) {
        K key = source.get(event);
        Transformer transformer = null;
        if (key != null) {
            transformer = transformers.get(key);
        }
        if (transformer == null) {
            transformer = defaultTransformer;
        }
        return transformer.transform(event);
    }

    public static <K> RouterTransformer.Builder<K> route(Field<K> source) {
        return new Builder<K>().source(source);
    }

    public static class Builder<K> implements Transformer.Builder {
        private Field<K> source;
        private Map<K, Transformer> transformers = new HashMap<>();
        private Transformer defaultTransformer = Transformers.noop();

        public Builder<K> source(Field<K> source) {
            this.source = source;
            return this;
        }

        public Builder<K> when(K key, Transformer.Builder transformerBuilder) {
            return when(key, transformerBuilder.build());
        }

        public Builder<K> when(K key, Transformer transformer) {
            Objects.requireNonNull(transformer, "transformer for " + key);
            this.transformers.put(key, transformer);
            return this;
        }

        public Builder<K> otherwise(Transformer.Builder transformerBuilder) {
            return otherwise(transformerBuilder.build());
        }

        public Builder<K> otherwise(Transformer transformer) {
            Objects.requireNonNull(transformer, "defaultTransformer");
            this.defaultTransformer = transformer;
            return this;
        }

        public RouterTransformer<K> build() {
            Objects.requireNonNull(source);
            if (transformers.isEmpty()) {
                throw new IllegalArgumentException("No transformers");
            }
            return new RouterTransformer<>(source, transformers, defaultTransformer);
        }
    }
}
