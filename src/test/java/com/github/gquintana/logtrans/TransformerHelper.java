package com.github.gquintana.logtrans;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TransformerHelper {
    public static class ExpectationBuilder {
        private final Transformer transformer;
        private final List<Event> events = new ArrayList<>();

        private ExpectationBuilder(Transformer transformer) {
            this.transformer = transformer;
        }

        public ExpectationBuilder when(EventBuilder eventBuilder) {
            return when(eventBuilder.build());
        }

        public ExpectationBuilder when(Event event) {
            events.addAll(transformer.transform(event));
            return this;
        }

        public void expectNoEvent() {
            assertThat(events).isEmpty();
        }

        public Event expectOneEvent() {
            assertThat(events).hasSize(1);
            return events.get(0);
        }
    }

    public static class EventBuilder {
        private final Event event = new MapEvent();

        private EventBuilder() {
        }

        public EventBuilder with(String name, Object value) {
            event.set(name, value);
            return this;
        }

        public Event build() {
            return event;
        }
    }

    public static ExpectationBuilder given(Transformer transformer) {
        return new ExpectationBuilder(transformer);
    }

    public static ExpectationBuilder given(Transformer.Builder transformerBuilder) {
        return given(transformerBuilder.build());
    }

    public static EventBuilder event() {
        return new EventBuilder();
    }

}
