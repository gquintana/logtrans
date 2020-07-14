package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import static com.github.gquintana.logtrans.Fields.intField;
import static com.github.gquintana.logtrans.Fields.stringField;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class ChainTransformerTest {

    @Test
    void transform() {
        Event event1=given(Transformers
                    .chain(intField("i").convert())
                    .then(stringField("s").map(String::trim)))

                .when(event()
                        .with("i", "1")
                        .with("s", "  bar\n"))

                .expectOneEvent();
        assertThat(event1.get("i")).isEqualTo(1);
        assertThat(event1.get("s")).isEqualTo("bar");

    }
}