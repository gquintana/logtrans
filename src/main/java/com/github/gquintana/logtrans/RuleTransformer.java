package com.github.gquintana.logtrans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RuleTransformer implements Transformer {
    private final List<Rule> rules;
    private final boolean single;
    private final Transformer defaultTransformer;

    private RuleTransformer(List<Rule> rules, boolean single, Transformer defaultTransformer) {
        this.rules = rules;
        this.single = single;
        this.defaultTransformer = defaultTransformer;
    }

    private static class Rule {
        private final Predicate<Event> predicate;
        private final Transformer transformer;

        private Rule(Predicate<Event> predicate, Transformer transformer) {
            this.predicate = predicate;
            this.transformer = transformer;
        }

        private boolean apply(Event event) {
            boolean test = predicate.test(event);
            if (test) {
                transformer.transform(event);
            }
            return test;
        }
    }

    @Override
    public Collection<Event> transform(Event event) {
        int appliedRuleCount = 0;
        for (Rule rule : rules) {
            if (rule.apply(event)) {
                appliedRuleCount++;
                if (single) {
                    break;
                }

            }
        }
        if (appliedRuleCount == 0) {
            defaultTransformer.transform(event);
        }
        return Collections.singleton(event);
    }

    public static RuleTransformer.Builder rules() {
        return new Builder();
    }

    public static class Builder implements Transformer.Builder {
        private final List<Rule> rules = new ArrayList<>();
        private boolean single;
        private Transformer defaultTransformer = Transformers.noop();

        public Builder single(boolean single) {
            this.single = single;
            return this;
        }

        public Builder when(Predicate<Event> predicate, Transformer.Builder transformerBuilder) {
            return when(predicate, transformerBuilder.build());
        }

        public Builder when(Predicate<Event> predicate, Transformer transformer) {
            Objects.requireNonNull(predicate, "predicate");
            Objects.requireNonNull(transformer, "transformer");
            this.rules.add(new Rule(predicate, transformer));
            return this;
        }

        public Builder otherwise(Transformer.Builder transformerBuilder) {
            return otherwise(transformerBuilder.build());
        }

        public Builder otherwise(Transformer transformer) {
            Objects.requireNonNull(transformer, "defaultTransformer");
            this.defaultTransformer = transformer;
            return this;
        }

        public RuleTransformer build() {
            if (rules.isEmpty()) {
                throw new IllegalArgumentException("No rules");
            }
            return new RuleTransformer(rules, single, defaultTransformer);
        }
    }
}
