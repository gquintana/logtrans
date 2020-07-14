package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.github.gquintana.logtrans.Fields.*;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

class AccessLogTransformerTest {
    @Test
    void transform() {
        Event event = given(Transformers
                    .chain(stringField("source").grok("%{COMBINEDAPACHELOG}"))
                    .then(dateTimeField("timestamp", DateTimeFormatter.ofPattern("dd/MMM/uuuu:HH:mm:ss Z", Locale.ENGLISH))
                            .convertTo("@timestamp"))
                    .then(longField("bytes").convert()))

                .when(event()
                        .with("source", "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22\""))

                .expectOneEvent();

        assertThat(event.get("bytes")).isEqualTo(44346L);
        assertThat(event.get("@timestamp")).isEqualTo(OffsetDateTime.of(2013, 3, 6, 1, 36, 30, 0, ZoneOffset.ofHours(9)));

    }
}
