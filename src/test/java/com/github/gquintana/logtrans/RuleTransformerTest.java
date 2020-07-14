package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import static com.github.gquintana.logtrans.Fields.stringField;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class RuleTransformerTest {

    @Test
    void transform() {
        RuleTransformer transformer = Transformers.rules()
                .when(stringField("source").isEqualTo("Foo"), Transformers.addTag( "one"))
                .when(stringField("source").matches("^.*o+$"), Transformers.addTag( "two"))
                .otherwise(Transformers.addTag( "default"))
                .build();
        check(transformer, "Foo", "one", "two");
        check(transformer, "Boo",  "two");
        check(transformer, "Bar",  "default");
    }

    @Test
    void transformSingle() {
        RuleTransformer transformer = Transformers.rules()
                .single(true)
                .when(stringField("source").isEqualTo("Foo"), Transformers.addTag( "one"))
                .when(stringField("source").matches("^.*o+$"), Transformers.addTag( "two"))
                .otherwise(Transformers.addTag( "default"))
                .build();
        check(transformer, "Foo", "one");
        check(transformer, "Boo",  "two");
        check(transformer, "Bar",  "default");
    }

    void check(RuleTransformer transformer, String source, String ... expectedTags) {
        Event event = given(transformer)
                .when(event().with("source", source))
                .expectOneEvent();
        assertThat(Converters.toCollection(event.get("tag"))).containsExactly(expectedTags);
    }
}