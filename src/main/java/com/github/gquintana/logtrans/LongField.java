package com.github.gquintana.logtrans;

public class LongField extends Field<Long> {
    public LongField(String name) {
        super(name);
    }

    @Override
    public Long doConvert(Object value) {
        return Converters.toLong(value);
    }
}
