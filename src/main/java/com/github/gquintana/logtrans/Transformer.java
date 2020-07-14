package com.github.gquintana.logtrans;

import java.util.Collection;

public interface Transformer {
    Collection<Event> transform(Event event);

    interface Builder<T extends Transformer> {
        T build();
    }
}
