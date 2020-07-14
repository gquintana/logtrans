package com.github.gquintana.logtrans;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class SetTransformer implements Transformer {
    private final String target;
    private final Object value;
    private final boolean override;

    private SetTransformer(String target, Object value, boolean override) {
        this.target = target;
        this.value = value;
        this.override = override;
    }

    @Override
    public Collection<Event> transform(Event event) {
        if (override) {
            event.set(target, value);
        } else {
            event.set(target, Fields.merge(event.get(target), value));
        }
        return Collections.singletonList(event);
    }

    public static Builder set(String target, Object value) {
        return new Builder().target(target).value(value);
    }


    public static class Builder implements Transformer.Builder<SetTransformer> {
        private Object value;
        private String target;
        private boolean override = true;

        public Builder value(Object value) {
            this.value = value;
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

        public SetTransformer build() {
            Objects.requireNonNull(value, "value");
            Objects.requireNonNull(target, "target");
            return new SetTransformer(target, value, override);
        }

    }
}
