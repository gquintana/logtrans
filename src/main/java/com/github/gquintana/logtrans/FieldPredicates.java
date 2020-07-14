package com.github.gquintana.logtrans;

import java.util.Collection;
import java.util.function.Predicate;

public class FieldPredicates {
    public static Predicate<Event> contains(String source, Object value) {
        return event -> {
            Collection collection = Converters.toCollection(event.get(source));
            return collection != null && collection.contains(value);
        };
    }
}
