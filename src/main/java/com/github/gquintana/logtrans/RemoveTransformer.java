package com.github.gquintana.logtrans;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class RemoveTransformer implements Transformer {
    private final String source;

    private RemoveTransformer(String source) {
        this.source = source;
    }

    @Override
    public Collection<Event> transform(Event event) {
        event.remove(source);
        return Collections.singletonList(event);
    }

    public static Builder remove(String source) {
        return new RemoveTransformer.Builder().source(source);
    }


    public static class Builder implements Transformer.Builder<RemoveTransformer> {
        private String source;

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public RemoveTransformer build() {
            Objects.requireNonNull(source, "source");
            return new RemoveTransformer(source);
        }
    }
}
