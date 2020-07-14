package com.github.gquintana.logtrans;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Fields {
    static Object apply(Object value, Function<Object, Object> function) {
        if (value instanceof Collection) {
            Collection<Object> values = (Collection<Object>) value;
            value = values.stream().map(function).collect(toList());
        } else if (value instanceof Object[]) {
            Object[] values = (Object[]) value;
            value = Stream.of(values).map(function).toArray();
        } else {
            value = function.apply(value);
        }
        return value;
    }

    static Object merge(Object current, Object newOne) {
        if (newOne == null) {
            return current;
        }
        if (current == null) {
            return newOne;
        }
        Collection<Object> collection0 = Converters.toCollection(current);
        Collection<Object> collection1 = Converters.toCollection(newOne);
        List<Object> collection = new ArrayList<>(collection0);
        collection.addAll(collection1);
        if (current instanceof Object[]) {
            return collection.toArray();
        }
        return collection;
    }


    public static Field<Object> field(String source) {
        return new Field<>(source);
    }

    public static StringField stringField(String source) {
        return new StringField(source);
    }

    public static IntegerField intField(String source) {
        return new IntegerField(source);
    }

    public static LongField longField(String source) {
        return new LongField(source);
    }

    public static <T> CollectionField<T> collectionField(String source) {
        return new CollectionField<>(source);
    }

    public static DateTimeField dateTimeField(String source, Parser<OffsetDateTime> parser) {
        return new DateTimeField(source, parser);
    }
    public static DateTimeField dateTimeField(String source, DateTimeFormatter formatter) {
        return dateTimeField(source, s -> OffsetDateTime.parse(s, formatter));
    }


}
