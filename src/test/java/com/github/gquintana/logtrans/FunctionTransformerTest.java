package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.github.gquintana.logtrans.Fields.stringField;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class FunctionTransformerTest {

    @Test
    void transform() {
        Event event = given(
                    stringField("source")
                            .map(String::toUpperCase)
                            .target("target"))
                .when(event().with("source", "foo"))
                .expectOneEvent();
        assertThat(event.get("source")).isEqualTo("foo");
        assertThat(event.get("target")).isEqualTo("FOO");
    }

    @Test
    void transformList() {
        Event event = given(stringField("source")
                    .map(String::toUpperCase)
                    .target("target"))
                .when(event().with("source", asList("foo", "bar")))
                .expectOneEvent();
        assertThat((Collection<String>) event.get("source")).containsExactly("foo", "bar");
        assertThat((Collection<String>) event.get("target")).containsExactly("FOO", "BAR");

    }
}