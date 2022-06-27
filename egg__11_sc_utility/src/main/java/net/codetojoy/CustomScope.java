
// note: I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.stream.Stream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import jdk.incubator.concurrent.StructuredTaskScope;

// javadoc here: https://download.java.net/java/early_access/jdk19/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/package-summary.html

// NOTE: this is NOT production-ready, just an experiment
// 
// ref: https://github.com/openjdk/jdk19/blob/master/src/jdk.incubator.concurrent/share/classes/jdk/incubator/concurrent/StructuredTaskScope.java

class CustomScope<T> extends StructuredTaskScope<T> {
    private final Queue<T> results = new ConcurrentLinkedQueue<>();

    CustomScope() {
        super(null, Thread.ofVirtual().factory());
    }

    @Override
    protected void handleComplete(Future<T> future) {
        if (future.state() == Future.State.SUCCESS) {
            T result = future.resultNow();
            results.add(result);
        }
    }

    public Stream<T> results() {  
        return results.stream();
    }
}
 
/*
public class CustomStructuredTaskScope<T> extends StructuredTaskScope<T> {
    private static final String LOG_PREFIX = "TRACER CustomStructuredTaskScope ";

    private AtomicInteger successCounter = new AtomicInteger(0);
    private AtomicBoolean hasReachedThreshold = new AtomicBoolean(false);
    private int numTasksForSuccess = 0;
    private List<T> results = new CopyOnWriteArrayList<>();

    // sanity check:
    private AtomicInteger failCounter = new AtomicInteger(0);

    public CustomStructuredTaskScope(int numTasksForSuccess) {
        this.numTasksForSuccess = numTasksForSuccess;
    } 

    @Override
    protected void handleComplete(Future<T> future) {
        try { 
            var state = future.state();
            if (state == Future.State.SUCCESS) {
                int numSuccess = successCounter.incrementAndGet();
                if (numSuccess <= numTasksForSuccess) {
                    results.add(future.resultNow());
                } 

                if (numSuccess == numTasksForSuccess) {
                    hasReachedThreshold.getAndSet(true);
                    System.out.println(LOG_PREFIX + " success threshold reached...");
                    shutdown();
                }
            } else if (state == Future.State.FAILED) {
                failCounter.incrementAndGet();
            }
        } catch (Exception ex) {
            System.err.println(LOG_PREFIX + " ERROR caught ex: " + ex.getMessage());
        }
    }    

    // TODO: deep copy?
    public List<T> results() {
        if (! hasReachedThreshold.get()) {
            throw new IllegalStateException("success threshold not met");
        } 
        System.out.println(LOG_PREFIX + " success! num ok: " + numTasksForSuccess +
                            " num failed: " + failCounter.get());

        return new ArrayList<T>(results);
    }
}
*/
