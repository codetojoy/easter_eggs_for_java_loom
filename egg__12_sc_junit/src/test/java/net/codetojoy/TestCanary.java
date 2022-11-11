
package net.codetojoy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestCanary {
    @Test
    void simpleAdd() {
        // test 
        var x = 2 + 2;

        assertEquals(4, x);
    }
}
