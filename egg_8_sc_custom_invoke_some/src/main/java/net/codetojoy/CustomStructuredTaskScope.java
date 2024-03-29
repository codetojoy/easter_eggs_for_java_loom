
// note: I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

// NOTE: this is NOT production-ready, just an experiment
// 
// this is similar in spirit to StructuredTaskScope.ShutdownOnSuccess<T>, but for N tasks
// i.e. given M tasks, success is defined when N of them complete successfully
// to wit: `invokeSome(n)`
 
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
    protected void handleComplete(StructuredTaskScope.Subtask<? extends T> subtask) {
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
                    shutdown();
                }
            } else if (state == StructuredTaskScope.Subtask.State.FAILED) {
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
