package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class CustomTransformerTest {

    @Test
    void transform() {
        Event event = given(new CustomTransformer())
                .when(event()
                        .with("a", "1")
                        .with("b", 2))
                .expectOneEvent();
        assertThat(event.get("c")).isEqualTo(3);
    }
}