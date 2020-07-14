package com.github.gquintana.logtrans;

import java.util.Collection;
import java.util.Collections;

import static com.github.gquintana.logtrans.Fields.intField;

public class CustomTransformer implements Transformer {
    private static final IntegerField A = intField("a");
    private static final IntegerField B = intField("b");
    private static final IntegerField C = intField("c");

    @Override
    public Collection<Event> transform(Event event) {
        Integer a = A.get(event);
        Integer b = B.get(event);
        if (a != null && b != null) {
            C.set(event, a + b);
        }
        return Collections.singleton(event);
    }
}
