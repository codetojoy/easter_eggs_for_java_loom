
// note: I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import jdk.incubator.concurrent.*;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

// NOTE: this is not production-ready
public class CustomStructuredTaskScope<T> extends StructuredTaskScope<T> {
    private AtomicInteger counter = new AtomicInteger(0);
    private AtomicBoolean hasReachedThreshold = new AtomicBoolean(false);
    private int numTasksForSuccess = 0;
    private List<T> results = new CopyOnWriteArrayList<>();

    public CustomStructuredTaskScope(int numTasksForSuccess) {
        this.numTasksForSuccess = numTasksForSuccess;
    } 

    private boolean isFutureSuccessful(Future<T> future) {
        Objects.requireNonNull(future);
        Future.State state = future.state();
        if (state == Future.State.RUNNING) {
            System.err.println("TRACER CustomStructuredScope: Task is not completed");
            throw new IllegalArgumentException("Task is not completed");
        }
        return state == Future.State.SUCCESS && future.isDone();
    }

    @Override
    protected void handleComplete(Future<T> future) {
        try { 
            if (isFutureSuccessful(future)) {
                int value = counter.incrementAndGet();
                if (value <= numTasksForSuccess) {
                    results.add(future.resultNow());
                } 

                if (value == numTasksForSuccess) {
                    hasReachedThreshold.getAndSet(true);
                    System.out.println("TRACER CSTScope success reached...");
                    shutdown();
                }
            } else {
                System.err.println("TRACER CSTScope error: future");
            }
        } catch (Exception ex) {
            System.err.println("TRACER CSTScope caught ex: " + ex.getMessage());
        }
    }    

    // TODO: deep copy?
    public List<T> results() throws ExecutionException {
        List<T> values = null;

        if (hasReachedThreshold.get()) {
            values = new ArrayList<T>(results);
        } else {
            var cause = new IllegalStateException("");
            throw new ExecutionException("success threshold not met", cause);
        } 

        return values;
    }
}
