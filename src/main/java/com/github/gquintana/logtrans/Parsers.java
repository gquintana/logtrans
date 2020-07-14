package com.github.gquintana.logtrans;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Parsers {
    public static OffsetDateTime parseIsoDateTime(String s) {
        return OffsetDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    public static Instant parseIsoInstant(String s) {
        return Instant.from(parseIsoDateTime(s));
    }
}
