
// note: I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.StructuredTaskScope.Joiner;

// NOTE: this is NOT production-ready, just an experiment
// 
// this is similar in spirit to ShutdownOnSuccess<T>, but for N tasks
// i.e. given M tasks, success is defined when N of them complete successfully
// to wit: `invokeSome(n)`
 
public class CustomJoiner<T> implements Joiner<T,List<T>> {
    private static final String LOG_PREFIX = "TRACER CustomJoiner ";

    private final AtomicInteger successCounter = new AtomicInteger(0);
    private final AtomicBoolean hasReachedThreshold = new AtomicBoolean(false);
    private final int numTasksForSuccess;
    private final List<T> results = new CopyOnWriteArrayList<>();

    // sanity check:
    private AtomicInteger failCounter = new AtomicInteger(0);

    public CustomJoiner(int numTasksForSuccess) {
        this.numTasksForSuccess = numTasksForSuccess;
    } 

    @Override
    public boolean onComplete(StructuredTaskScope.Subtask<? extends T> subtask) {
        boolean result = false;

        try { 
            var state = subtask.state();
            if (state == StructuredTaskScope.Subtask.State.SUCCESS) {
                int numSuccess = successCounter.incrementAndGet();
                if (numSuccess <= numTasksForSuccess) {
                    results.add(subtask.get());
                } 

                if (numSuccess == numTasksForSuccess) {
                    hasReachedThreshold.getAndSet(true);
                    System.out.println(LOG_PREFIX + " success threshold reached...");
                    result = true;
                }
            } else if (state == StructuredTaskScope.Subtask.State.FAILED) {
                failCounter.incrementAndGet();
            }
        } catch (Exception ex) {
            System.err.println(LOG_PREFIX + " ERROR caught ex: " + ex.getMessage());
        }

        return result;
    }

    @Override
    public List<T> result() {
        String stats = " num ok: " + successCounter.get() + " num failed: " + failCounter.get();
        if (! hasReachedThreshold.get()) {
            System.out.println(LOG_PREFIX + " failed. stats: " + stats);
            throw new IllegalStateException("success threshold not met");
        } 

        System.out.println(LOG_PREFIX + " success. stats: " + stats);

        return results;
    }
}
