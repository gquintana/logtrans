package com.github.gquintana.logtrans;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Collection;

import static java.util.Collections.singleton;

public class Converters {
    public static String toString(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof String) {
            return trimToNull((String) o);
        } else if (o instanceof Number) {
            return ((Number) o).toString();
        } else if (o instanceof Boolean) {
            return ((Boolean) o).toString();
        } else {
            return o.toString();
        }
    }

    public static Integer toInteger(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof String) {
            String s = trimToNull((String) o);
            return s == null ? null : Integer.valueOf(s);
        } else if (o instanceof Number) {
            return ((Number) o).intValue();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1 : 0;
        } else {
            throw new IllegalArgumentException("Can not convert to integer " + o);
        }
    }

    public static Long toLong(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Long) {
            return (Long) o;
        } else if (o instanceof String) {
            String s = trimToNull((String) o);
            return s== null ? null : Long.valueOf(s);
        } else if (o instanceof Number) {
            return ((Number) o).longValue();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1L : 0L;
        } else {
            throw new IllegalArgumentException("Can not convert to long " + o);
        }
    }

    public static Boolean toBoolean(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Boolean) {
            return (Boolean) o;
        } else if (o instanceof String) {
            String s = trimToNull((String) o);
            return s == null ? null : Boolean.valueOf(s);
        } else if (o instanceof Number) {
            return ((Number) o).intValue() != 0;
        } else {
            throw new IllegalArgumentException("Can not convert to long " + o);
        }
    }

    public static Instant toInstant(Object o, Parser<Instant> parser) {
        if (o == null) {
            return null;
        } else if (o instanceof Instant) {
            return (Instant) o;
        } else if (o instanceof TemporalAccessor) {
            return Instant.from((TemporalAccessor) o);
        } else if (o instanceof String) {
            String s = trimToNull((String) o);
            if (s == null) {
                return null;
            }
            if (parser == null) {
                return Instant.parse(s);
            } else {
                return parser.parse(s);
            }
        } else if (o instanceof Number) {
            return Instant.ofEpochMilli(((Number) o).longValue());
        } else {
            throw new IllegalArgumentException("Can not convert to instant " + o);
        }
    }

    public static OffsetDateTime toDateTime(Object o, Parser<OffsetDateTime> parser) {
        if (o == null) {
            return null;
        } else if (o instanceof OffsetDateTime) {
            return (OffsetDateTime) o;
        } else if (o instanceof TemporalAccessor) {
            return OffsetDateTime.from((TemporalAccessor) o);
        } else if (o instanceof String) {
            String s = trimToNull((String) o);
            if (s == null) {
                return null;
            }
            if (parser == null) {
                return OffsetDateTime.parse(s);
            } else {
                return parser.parse(s);
            }
        } else if (o instanceof Number) {
            Instant instant = Instant.ofEpochMilli(((Number) o).longValue());
            return OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());
        } else {
            throw new IllegalArgumentException("Can not convert to instant " + o);
        }
    }

    public static Collection<Object> toCollection(Object object) {
        Collection<Object> result;
        if (object == null) {
            return null;
        } else if (object instanceof Collection) {
            result = (Collection<Object>) object;
        } else if (object instanceof Object[]) {
            result = Arrays.asList((Object[]) object);
        } else {
            result = singleton(object);
        }
        return result;
    }

    private static String trimToNull(String s) {
        s = s.trim();
        return s.isEmpty() ? null : s;
    }
}
