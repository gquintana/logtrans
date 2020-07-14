package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import static com.github.gquintana.logtrans.Fields.stringField;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class RouterTransformerTest {

    @Test
    void transform() {
        RouterTransformer<String> transformer =
                stringField("type").route()
                .when("up", stringField("string").map(String::toUpperCase).target("string"))
                .when("down", stringField("string").map(String::toLowerCase).target("string"))
                .otherwise(stringField("string").map(x -> x + "?").target("string"))
                .build();


        check(transformer, "up", "FOO");
        check(transformer, "down", "foo");
        check(transformer, "default", "Foo?");
    }

    private void check(RouterTransformer<String> transformer, String type, String expectedString) {
        Event event = given(transformer)
                .when(event().with("type", type).with("string", "Foo"))
                .expectOneEvent();
        assertThat(event.get("string")).isEqualTo(expectedString);
    }

}