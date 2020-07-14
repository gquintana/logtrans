package com.github.gquintana.logtrans;

import java.util.HashMap;
import java.util.Map;

public class MapEvent implements Event {
    private final Map<String, Object> fields = new HashMap();

    @Override
    public Object get(String source) {
        return fields.get(source);
    }

    @Override
    public void set(String target, Object value) {
        fields.put(target, value);
    }

    @Override
    public boolean has(String source) {
        return fields.containsKey(source);
    }

    @Override
    public Object remove(String source) {
        return fields.remove(source);
    }
}
