
// note: I no longer own this domain
package net.codetojoy;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import jdk.incubator.concurrent.*;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

public class CustomStructuredTaskScope<T> extends StructuredTaskScope<T> {
    private AtomicInteger counter = new AtomicInteger(0);
    private AtomicBoolean isOk = new AtomicBoolean(false);
    private static final int NUM_TASKS_FOR_SUCCESS = 5;

    @Override
    protected void handleComplete(Future<T> future) {
        int value = counter.incrementAndGet();
        if (value >= NUM_TASKS_FOR_SUCCESS) {
            isOk.getAndSet(true);
            System.out.println("TRACER CSTScope success!");
        }
    }    

    public T result() throws ExecutionException {
        if (isOk.get()) {
            return null;
        } else {
            var cause = new IllegalStateException("");
            throw new ExecutionException("success threshold not met", cause);
        } 
    }
}
