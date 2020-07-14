package com.github.gquintana.logtrans;

public interface Event {
    Object get(String source);

    void set(String target, Object value);

    boolean has(String source);

    Object remove(String source);
}
