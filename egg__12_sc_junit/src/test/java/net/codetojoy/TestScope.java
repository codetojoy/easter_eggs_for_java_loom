
package net.codetojoy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jdk.incubator.concurrent.StructuredTaskScope;

// we want to ensure this compiles, runs
class CustomStructuredTaskScope<T> extends StructuredTaskScope<T> {}

public class TestScope {
    @Test
    void canaryTestForScope() {
        // test 
        var x = 4 + 4;

        assertEquals(8, x);
    }
}
