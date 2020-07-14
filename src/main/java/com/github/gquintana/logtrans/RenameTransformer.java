package com.github.gquintana.logtrans;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class RenameTransformer implements Transformer {
    private final String source;
    private final String target;
    private final boolean override;

    private RenameTransformer(String source, String target, boolean override) {
        this.source = source;
        this.target = target;
        this.override = override;
    }

    @Override
    public Collection<Event> transform(Event event) {
        if (event.has(source)) {
            Object value = event.get(source);
            if (override) {
                event.set(target, value);
            } else {
                event.set(target, Fields.merge(event.get(target), value));
            }
            event.remove(source);
        }
        return Collections.singletonList(event);
    }

    public static Builder rename(String source, String target) {
        return new RenameTransformer.Builder().source(source).target(target);
    }


    public static class Builder implements Transformer.Builder<RenameTransformer> {
        private String source;
        private String target;
        private boolean override = true;

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder target(String target) {
            this.target = target;
            return this;
        }


        public Builder override(boolean override) {
            this.override = override;
            return this;
        }

        public RenameTransformer build() {
            Objects.requireNonNull(source, "source");
            Objects.requireNonNull(target, "target");
            return new RenameTransformer(source, target, override);
        }
    }
}
