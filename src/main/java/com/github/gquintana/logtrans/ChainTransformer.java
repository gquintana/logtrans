package com.github.gquintana.logtrans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class ChainTransformer implements Transformer {
    private final List<Transformer> transformers;

    private ChainTransformer(List<Transformer> transformers) {
        this.transformers = transformers;
    }

    public Collection<Event> transform(Event event) {
        List<Event> events = Collections.singletonList(event);
        for (Transformer t : transformers) {
            events = events.stream().flatMap(e -> t.transform(e).stream()).collect(toList());
            if (events.isEmpty()) {
                break;
            }

        }
        return events;
    }

    public static ChainTransformer.Builder chain(Transformer transformer) {
        return new ChainTransformer.Builder().then(transformer);
    }

    public static class Builder implements Transformer.Builder<ChainTransformer> {
        private final List<Transformer> transformers = new ArrayList<>();

        public Builder then(Transformer transformer) {
            Objects.requireNonNull(transformer, "transformer");
            transformers.add(transformer);
            return this;
        }

        public Builder then(Transformer.Builder transformerBuilder) {
            return then(transformerBuilder.build());
        }

        public ChainTransformer build() {
            return new ChainTransformer(transformers.stream().collect(toList()));
        }
    }
}
