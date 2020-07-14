package com.github.gquintana.logtrans;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Field<T> {
    private final String name;

    protected Field(String name) {
        this.name = name;
    }

    protected T doConvert(Object value) {
        return (T) value;
    }

    public String name() {
        return name;
    }

    public T get(Event event) {
        return doConvert(event.get(name));
    }

    public void set(Event event, T value) {
        event.set(name, value);
    }

    public Predicate<Event> is(Predicate<T> predicate) {
        return e -> predicate.test(get(e));
    }

    public Predicate<Event> isEqualTo(T value) {
        return is(t -> Objects.equals(t, value));
    }

    public Predicate<Event> isNull() {
        return is(Objects::isNull);
    }

    public FunctionTransformer<T> convertTo(String target) {
        return FunctionTransformer.field(name, this::doConvert).target(target).build();
    }

    public FunctionTransformer<T> convert() {
        return convertTo(name);
    }

    public <X> FunctionTransformer.Builder<X> map(Function<T, X> function) {
        Function<Object, T> f1 = this::doConvert;
        return FunctionTransformer.field(name, f1.andThen(function));
    }
    public RouterTransformer.Builder<T> route() {
        return RouterTransformer.route(this);
    }

    public RenameTransformer.Builder renameTo(String target) {
        return RenameTransformer.rename(name, target);
    }

    public RemoveTransformer.Builder remove() {
        return RemoveTransformer.remove(name);
    }

    public static SetTransformer.Builder set(String target, Object value) {
        return SetTransformer.set(target, value);
    }

}
