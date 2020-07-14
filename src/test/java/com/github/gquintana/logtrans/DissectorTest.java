package com.github.gquintana.logtrans;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DissectorTest {

    @Test
    void dissect() {
        Dissector dissector = new Dissector.Builder()
                .separator("\"")
                .field("a")
                .separator("\"\\s*;\\s*")
                .field("b")
                .build();
        Map<String, Object> r1 = dissector.dissect("\"Foo\";1");
        assertThat(r1.get("a")).isEqualTo("Foo");
        assertThat(r1.get("b")).isEqualTo("1");
        Map<String, Object> r2 = dissector.dissect("\"Bar\"  ;  2");
        assertThat(r2.get("a")).isEqualTo("Bar");
        assertThat(r2.get("b")).isEqualTo("2");

    }
    @Test
    void dissectMulti() {
        Dissector dissector = new Dissector.Builder()
                .field("a")
                .separator("\\s*;\\s*")
                .field("b")
                .separator("\\s*;\\s*")
                .field("a")
                .build();
        Map<String, Object> r1 = dissector.dissect("Foo;1;Bar");
        assertThat((List<String>) r1.get("a")).containsExactly("Foo", "Bar");
        assertThat(r1.get("b")).isEqualTo("1");

    }

}