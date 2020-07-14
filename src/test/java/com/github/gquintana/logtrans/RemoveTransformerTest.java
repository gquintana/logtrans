package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import static com.github.gquintana.logtrans.Fields.field;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class RemoveTransformerTest {

    @Test
    void transform() {
        Event event = given(field("source").remove())
                .when(event().with("source", "Foo").with("other","Bar"))
                .expectOneEvent();
        assertThat(event.get("other")).isEqualTo("Bar");
        assertThat(event.get("source")).isNull();
    }
}