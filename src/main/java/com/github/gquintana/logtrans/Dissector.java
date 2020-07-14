package com.github.gquintana.logtrans;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dissector {
    private final Token startToken;

    private Dissector(Token startToken) {
        this.startToken = startToken;
    }

    private static abstract class Token<N extends Token> {
        protected N next;

        abstract int parse(String s, int pos, Map<String, Object> fields);

        Token next() {
            return next;
        }

        void next(N next) {
            this.next = next;
        }
    }

    private static class Field extends Token<Separator> {
        private final String name;

        public Field(String name) {
            this.name = name;
        }

        @Override
        public int parse(String s, int pos, Map<String, Object> fields) {
            if (next == null) {
                append(fields, s.substring(pos));
                return s.length();
            } else {
                int sepPos = next.find(s, pos);
                if (sepPos < 0) {
                    throw new DissectorException("Separator after field " + name + " not found");
                }
                append(fields, s.substring(pos, sepPos));
                return sepPos;
            }
        }

        private void append(Map<String, Object> fields, String value) {
            fields.put(name, Fields.merge(fields.get(name), value));
        }

    }

    private static class Separator extends Token<Field> {
        private final Pattern pattern;

        public Separator(Pattern pattern) {
            this.pattern = pattern;
        }

        @Override
        public int parse(String s, int pos, Map<String, Object> fields) {
            Matcher matcher = pattern.matcher(s);
            if (!matcher.find(pos) || matcher.start() != pos) {
                throw new DissectorException("Separator " + pattern.pattern() + " not found");
            }
            return matcher.end();
        }


        public int find(String s, int pos) {
            Matcher matcher = pattern.matcher(s);
            return matcher.find(pos) ? matcher.start() : -1;
        }
    }

    public Map<String, Object> dissect(String s) {
        Token currentToken = startToken;
        int currentPos = 0;
        Map<String, Object> fields = new HashMap<>();
        while (currentToken != null && currentPos >= 0 && currentPos < s.length()) {
            currentPos = currentToken.parse(s, currentPos, fields);
            currentToken = currentToken.next();
        }
        return fields;
    }

    public static class Builder {
        private Token startToken;
        private Token currentToken;

        private Builder add(Token token) {
            if (startToken == null) {
                startToken = token;
            } else if (currentToken != null) {
                if (currentToken.getClass().equals(token.getClass())) {
                    throw new IllegalArgumentException("Two consecutive tokens of type " + currentToken.getClass());
                }
                currentToken.next(token);
            }
            currentToken = token;
            return this;
        }

        public Builder separator(Pattern pattern) {
            return add(new Separator(pattern));
        }


        public Builder separator(String pattern) {
            return separator(Pattern.compile(pattern));
        }

        public Builder field(String name) {
            return add(new Field(name));
        }

        public Dissector build() {
            Objects.requireNonNull(startToken, "no token");
            return new Dissector(startToken);
        }
    }

}
