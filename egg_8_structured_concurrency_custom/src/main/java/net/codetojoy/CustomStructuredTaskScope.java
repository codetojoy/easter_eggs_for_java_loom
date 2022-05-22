
// note: I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import jdk.incubator.concurrent.*;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

// NOTE: this is NOT production-ready, just an experiment
// 
// ref: https://github.com/openjdk/loom/blob/fibers/src/jdk.incubator.concurrent/share/classes/jdk/incubator/concurrent/StructuredTaskScope.java
// 
// this is similar in spirit to StructuredTaskScope.ShutdownOnSuccess<T>, but for N tasks
 
public class CustomStructuredTaskScope<T> extends StructuredTaskScope<T> {
    private AtomicInteger successCounter = new AtomicInteger(0);
    private AtomicBoolean hasReachedThreshold = new AtomicBoolean(false);
    private int numTasksForSuccess = 0;
    private List<T> results = new CopyOnWriteArrayList<>();
    private static final String LOG_PREFIX = "TRACER CustomStructuredTaskScope ";

    public CustomStructuredTaskScope(int numTasksForSuccess) {
        this.numTasksForSuccess = numTasksForSuccess;
    } 

/*
    private boolean isFutureSuccessful(Future<T> future) {
        Objects.requireNonNull(future);
        Future.State state = future.state();
        if (state == Future.State.RUNNING) {
            System.err.println("TRACER CustomStructuredScope: ERROR task is not completed");
            throw new IllegalArgumentException("Task is not completed");
        }
        return state == Future.State.SUCCESS;
    }
*/

    private Optional<T> getSuccessfulResult(Future<T> future) {
        Optional<T> result = Optional.empty();
        try {
            if (future.state() == Future.State.SUCCESS) {
                result = Optional.of(future.resultNow());
            }
        } catch (Exception ex) {
            System.err.println(LOG_PREFIX + " getResult caught ex: " + ex.getMessage());
        }
        return result;
    }

    @Override
    protected void handleComplete(Future<T> future) {
        try { 
            var resultOption = getSuccessfulResult(future);
            if (resultOption.isPresent()) {
                var result = resultOption.get();
                int numSuccess = successCounter.incrementAndGet();
                if (numSuccess <= numTasksForSuccess) {
                    String tmp = result.toString();
                    if (tmp.isEmpty()) {
                       System.err.println(LOG_PREFIX + " BINGO WTF...");
                    } else {
                        results.add(result);
                    }
                } 

                if (numSuccess == numTasksForSuccess) {
                    hasReachedThreshold.getAndSet(true);
                    System.out.println(LOG_PREFIX + " success threshold reached...");
                    shutdown();
                }
            }
        } catch (Exception ex) {
            System.err.println(LOG_PREFIX + " ERROR caught ex: " + ex.getMessage());
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
