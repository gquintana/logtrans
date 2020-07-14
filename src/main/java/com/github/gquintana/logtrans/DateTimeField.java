package com.github.gquintana.logtrans;

import java.time.OffsetDateTime;

public class DateTimeField extends Field<OffsetDateTime> {
    private Parser<OffsetDateTime> parser;

    public DateTimeField(String name, Parser<OffsetDateTime> parser) {
        super(name);
        this.parser = parser;
    }

    @Override
    public OffsetDateTime doConvert(Object value) {
        return Converters.toDateTime(value, parser);
    }

}
