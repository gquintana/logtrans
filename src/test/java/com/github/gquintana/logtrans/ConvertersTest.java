package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConvertersTest {


    @Test
    void testToString() {
        assertThat(Converters.toString(null)).isNull();
        assertThat(Converters.toString(" foo ")).isEqualTo("foo");
        assertThat(Converters.toString(" ")).isNull();
        assertThat(Converters.toString(1)).isEqualTo("1");
        assertThat(Converters.toString(2L)).isEqualTo("2");
        assertThat(Converters.toString(true)).isEqualTo("true");
    }

    @Test
    void toInteger() {
        assertThat(Converters.toInteger(null)).isNull();
        assertThat(Converters.toInteger("123")).isEqualTo(123);
        assertThat(Converters.toInteger(" ")).isNull();
        assertThat(Converters.toInteger(1)).isEqualTo(1);
        assertThat(Converters.toInteger(2L)).isEqualTo(2);
        assertThat(Converters.toInteger(true)).isEqualTo(1);
    }

    @Test
    void toLong() {
        assertThat(Converters.toLong(null)).isNull();
        assertThat(Converters.toLong("123")).isEqualTo(123L);
        assertThat(Converters.toLong(" ")).isNull();
        assertThat(Converters.toLong(1)).isEqualTo(1L);
        assertThat(Converters.toLong(2L)).isEqualTo(2L);
        assertThat(Converters.toLong(false)).isEqualTo(0L);
    }
    
    @Test
    void toBoolean() {
        assertThat(Converters.toBoolean(null)).isNull();
        assertThat(Converters.toBoolean("true")).isTrue();
        assertThat(Converters.toBoolean(" ")).isNull();
        assertThat(Converters.toBoolean(1)).isTrue();
        assertThat(Converters.toBoolean(0L)).isFalse();
        assertThat(Converters.toBoolean(false)).isFalse();
    }
}