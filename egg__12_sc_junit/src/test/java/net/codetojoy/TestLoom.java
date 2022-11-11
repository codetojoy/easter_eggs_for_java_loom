
package net.codetojoy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import jdk.incubator.concurrent.StructuredTaskScope;

// Loom: we want to ensure this compiles, runs
class CustomStructuredTaskScope<T> extends StructuredTaskScope<T> {}

class Worker {
    static AtomicBoolean isDone = new AtomicBoolean(false);

    static void doSleep() {
        System.out.println("TRACER working t: " + Thread.currentThread());
        try { Thread.sleep(500); } catch (Exception ex) {} 
        isDone.set(true);
    }

    static void doWork() {
        isDone.set(false);
        try {
            // Loom: we want to ensure this compiles, runs
            var thread = Thread.startVirtualThread(Worker::doSleep);
        } catch(Exception ex) {
            System.err.println("TRACER caught ex !? msg: " + ex.getMessage());
        }
    }
}

// This isn't really a unit test, but it exercises Loom features to ensure
// that JUnit is happy and configured properly.

public class TestLoom {
    @Test
    void canaryTest() {
        // test 
        var x = 4 + 4;

        assertEquals(8, x);
    }

    @Test
    void workerTest() {
        var worker = new Worker();

        // test
        worker.doWork();

        while (! worker.isDone.get()) {
            System.out.println("TRACER waiting on worker");
            try { Thread.sleep(250); } catch (Exception ex) {} 
        }
    }
}
