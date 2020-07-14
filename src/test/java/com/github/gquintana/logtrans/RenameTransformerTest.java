package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.gquintana.logtrans.Fields.field;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class RenameTransformerTest {

    @Test
    void transform() {
        Event event = given(field("source").renameTo("target"))
                .when(event().with("source", "Foo"))
                .expectOneEvent();
        assertThat(event.get("target")).isEqualTo("Foo");
        assertThat(event.get("source")).isNull();

    }

    @Test
    void transformMulti() {
        Event event = given(Transformers
                    .chain(field("source1").renameTo("target").override(false))
                    .then(field("source2").renameTo("target").override(false)))
                .when(event().with("source1", "Foo").with("source2", "Bar"))
                .expectOneEvent();
        assertThat((List<Object>) event.get("target")).containsExactly("Foo", "Bar");
        assertThat(event.get("source1")).isNull();
        assertThat(event.get("source2")).isNull();
    }
}