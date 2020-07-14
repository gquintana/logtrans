package com.github.gquintana.logtrans;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class DissectTransformer implements Transformer {
    private final String source;
    private final Dissector dissector;
    private final boolean override;

    private DissectTransformer(String source, Dissector dissector, boolean override) {
        this.source = source;
        this.dissector = dissector;
        this.override = override;
    }

    @Override
    public Collection<Event> transform(Event event) {
        Object value = event.get(source);
        Fields.apply(value, v -> {
            doTransform(v, event);
            return null;
        });
        return Collections.singletonList(event);
    }

    private void doTransform(Object value, Event event) {
        String s = Converters.toString(value);
        if (s == null) {
            return;
        }
        Map<String, Object> capture = dissector.dissect(s);
        if (override) {
            capture.entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .forEach(e -> event.set(e.getKey(), e.getValue()));
        } else {
            capture.entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .forEach(e -> {
                        Object currentValue = event.get(e.getKey());
                        Object mergedValue = Fields.merge(currentValue, e.getValue());
                        event.set(e.getKey(), mergedValue);
                    });
        }

    }


    public static  DissectTransformer.Builder dissect(String source) {
        return new Builder().source(source);
    }

    public static class Builder implements Transformer.Builder {
        private String source;
        private Dissector.Builder dissectorBuilder = new Dissector.Builder();
        private boolean override;

        public Builder source(String source) {
            this.source = source;
            return this;
        }
        public Builder separator(Pattern pattern) {
            dissectorBuilder.separator(pattern);
            return this;
        }


        public Builder separator(String pattern) {
            dissectorBuilder.separator(pattern);
            return this;
        }

        public Builder field(String name) {
            dissectorBuilder.field(name);
            return this;
        }

        public Builder override(boolean override) {
            this.override = override;
            return this;
        }

        public DissectTransformer build() {
            Objects.requireNonNull(source);
            return new DissectTransformer(source, dissectorBuilder.build(), override);
        }
    }
}
