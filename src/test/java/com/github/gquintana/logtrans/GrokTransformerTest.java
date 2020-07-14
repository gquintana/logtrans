package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import static com.github.gquintana.logtrans.Fields.stringField;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class GrokTransformerTest {

    @Test
    void transform() {
        Event event =  given(stringField("source").grok("%{COMBINEDAPACHELOG}"))
                .when(event()
                        .with("source", "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22\""))
                .expectOneEvent();
        assertThat(event.get("response")).isEqualTo("200");
        assertThat(event.get("verb")).isEqualTo("GET");
        assertThat(event.get("clientip")).isEqualTo("112.169.19.192");
    }
}