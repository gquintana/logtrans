package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.github.gquintana.logtrans.Fields.dateTimeField;
import static com.github.gquintana.logtrans.TransformerHelper.event;
import static com.github.gquintana.logtrans.TransformerHelper.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DateTimeTransformerTest {
    @Test
    void parseDateTime() {
        OffsetDateTime now = OffsetDateTime.now();
        Event event = given(dateTimeField("source", Parsers::parseIsoDateTime).convertTo("dateTime"))
                .when(event().with("source", DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(now)))
                .expectOneEvent();
        assertThat(event.get("dateTime")).isEqualTo(now);
    }

    @Test
    void parseInstant() {
        Instant now = Instant.now();
        Event event = given(Transformers
                    .instant("source", Parsers::parseIsoInstant)
                    .target("instant"))
                .when(event().with("source", DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(now.atOffset(ZoneOffset.UTC))))
                .expectOneEvent();
        assertThat(event.get("instant")).isEqualTo(now);
    }
}
