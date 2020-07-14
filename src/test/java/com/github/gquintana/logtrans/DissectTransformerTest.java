package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.github.gquintana.logtrans.Fields.stringField;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class DissectTransformerTest {

    @Test
    void transform() {
        Event event = given(stringField("source").dissect()
                    .field("a").separator("\\|\\[").field("b").separator("\\]\\|").field("c"))
                .when(event().with("source", "Foo|[Bar | raB]|ooF*"))
                .expectOneEvent();
        assertThat(event.get("a")).isEqualTo("Foo");
        assertThat(event.get("b")).isEqualTo("Bar | raB");
        assertThat(event.get("c")).isEqualTo("ooF*");
    }

    @Test
    void transformMulti() {
        Event event = given(stringField("source").dissect()
                .field("a").separator("\\|\\[").field("b").separator("\\]\\|").field("a"))
                .when(event().with("source", "Foo|[Bar | raB]|ooF*").with("b", "Qix"))
                .expectOneEvent();
        assertThat((Collection<String>) event.get("a")).containsExactly("Foo","ooF*");
        assertThat((Collection<String>) event.get("b")).containsExactly("Qix", "Bar | raB");
    }

}