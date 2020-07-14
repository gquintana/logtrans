package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class SetTransformerTest {

    @Test
    void transform() {
        Event event = given(Transformers.set("target", "Bar"))
                .when(event().with("source", "Foo"))
                .expectOneEvent();
        assertThat(event.get("source")).isEqualTo("Foo");
        assertThat(event.get("target")).isEqualTo("Bar");

    }
    @Test
    void transformMulti() {
        Event event = given(Transformers.set("target", "Bar").override(false))
                .when(event().with("target", "Foo"))
                .expectOneEvent();
        assertThat((List<String>) event.get("target")).containsExactly("Foo","Bar");
    }

    @Test
    void transformAddTag() {
        Event event = given(Transformers.addTag("Bar","Qix"))
                .when(event().with("tag", "Foo"))
                .expectOneEvent();
        assertThat((List<String>) event.get("tag")).containsExactly("Foo", "Bar", "Qix");
    }

}