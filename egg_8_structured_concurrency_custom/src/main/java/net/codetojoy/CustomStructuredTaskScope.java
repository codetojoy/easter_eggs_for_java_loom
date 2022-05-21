
// note: I no longer own this domain
package net.codetojoy;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import jdk.incubator.concurrent.*;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

public class CustomStructuredTaskScope<T> extends StructuredTaskScope<T> {
    private AtomicInteger counter = new AtomicInteger(0);
    private AtomicBoolean isOk = new AtomicBoolean(false);
    private static final int NUM_TASKS_FOR_SUCCESS = 5;
    private List<T> results = new CopyOnWriteArrayList<T>();

    @Override
    protected void handleComplete(Future<T> future) {
        try { 
            if (future.isDone()) {
                results.add(future.get());
                int value = counter.incrementAndGet();
                if (value >= NUM_TASKS_FOR_SUCCESS) {
                    isOk.getAndSet(true);
                    System.out.println("TRACER CSTScope success!");
                }
            }
        } catch (Exception ex) {
            System.err.println("TRACER CSTScope caught ex: " + ex.getMessage());
        }
    }    

    public String result() throws ExecutionException {
        if (isOk.get()) {
            var buffer = new StringBuilder();
            for (T result : results) {
                buffer.append(result.toString() + " ");
            } 
            return buffer.toString();
        } else {
            var cause = new IllegalStateException("");
            throw new ExecutionException("success threshold not met", cause);
        } 
    }
}
