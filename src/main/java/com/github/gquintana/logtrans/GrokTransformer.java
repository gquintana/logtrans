package com.github.gquintana.logtrans;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class GrokTransformer implements Transformer {
    private final String source;
    private final Grok grok;
    private boolean override;

    private GrokTransformer(String source, Grok grok, boolean override) {
        this.source = source;
        this.grok = grok;
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
        Match match = grok.match(s);
        Map<String, Object> capture = match.capture();
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

    public static <K> GrokTransformer.Builder<K> grok(String source, String pattern) {
        return new Builder<K>().source(source).pattern(pattern);
    }

    public static class Builder<K> implements Transformer.Builder {
        private String source;
        private String pattern;
        private boolean namedOnly = true;
        private boolean override;

        public Builder<K> source(String source) {
            this.source = source;
            return this;
        }

        public Builder<K> pattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder<K> override(boolean override) {
            this.override = override;
            return this;
        }

        public Builder<K> namedOnly(boolean namedOnly) {
            this.namedOnly = namedOnly;
            return this;
        }

        public GrokTransformer build() {
            Objects.requireNonNull(source);
            GrokCompiler grokCompiler = GrokCompiler.newInstance();
            grokCompiler.registerDefaultPatterns();
            Objects.requireNonNull(pattern);
            Grok grok = grokCompiler.compile(pattern, namedOnly);
            return new GrokTransformer(source, grok, override);
        }
    }
}
