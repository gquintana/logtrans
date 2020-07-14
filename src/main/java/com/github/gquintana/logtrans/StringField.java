package com.github.gquintana.logtrans;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class StringField extends ComparableField<String> {
    public StringField(String name) {
        super(name);
    }

    @Override
    public String doConvert(Object value) {
        return Converters.toString(value);
    }

    public Predicate<Event> matches(String regex) {
        return matches(Pattern.compile(regex));
    }

    public Predicate<Event> matches(Pattern pattern) {
        return event -> {
            String s = get(event);
            return s != null && pattern.matcher(s).matches();
        };
    }

    public Predicate<Event> startsWith(String prefix) {
        return is(s -> s.startsWith(prefix));
    }

    public Predicate<Event> endsWith(String suffix) {
        return is(s -> s.endsWith(suffix));
    }

    public Predicate<Event> contains(String inner) {
        return is(s -> s.contains(inner));
    }

    public GrokTransformer.Builder grok(String pattern) {
        return GrokTransformer.grok(name(), pattern);
    }

    public DissectTransformer.Builder dissect() {
        return DissectTransformer.dissect(name());
    }

}
