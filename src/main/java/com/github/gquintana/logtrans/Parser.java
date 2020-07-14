package com.github.gquintana.logtrans;

public interface Parser<T> {
    T parse(String s);
}
