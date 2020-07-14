package com.github.gquintana.logtrans;

import java.util.Collection;
import java.util.function.Predicate;

public class CollectionField<T> extends Field<Collection<T>> {
    public CollectionField(String name) {
        super(name);

    }


    @Override
    public Collection<T> doConvert(Object value) {
        return (Collection<T>) Converters.toCollection(value);
    }

    public Predicate<Event> contains(T inner) {
        return is(s -> s.contains(inner));
    }

    public SetTransformer add(T... values) {
        return SetTransformer.set(name(), values).override(false).build();
    }

}
