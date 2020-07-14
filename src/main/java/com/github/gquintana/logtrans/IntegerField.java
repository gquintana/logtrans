package com.github.gquintana.logtrans;

public class IntegerField extends Field<Integer> {
    public IntegerField(String name) {
        super(name);
    }

    @Override
    public Integer doConvert(Object value) {
        return Converters.toInteger(value);
    }
}
