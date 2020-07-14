package com.github.gquintana.logtrans;

import java.util.function.Predicate;

public class ComparableField<T extends Comparable<T>> extends Field<T> {
    public ComparableField(String name) {
        super(name);
    }

    public Predicate<Event> greaterStrict(T v) {
        return is(t -> t.compareTo(v) > 0);
    }
    public Predicate<Event> greaterEqual(T v) {
        return is(t -> t.compareTo(v) >= 0);
    }
    public Predicate<Event> lowerStrict(T v) {
        return is(t -> t.compareTo(v) > 0);
    }
    public Predicate<Event> lowerEqual(T v) {
        return is(t -> t.compareTo(v) >= 0);
    }
}
