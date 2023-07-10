
// note: I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.stream.Stream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

// NOTE: this is NOT production-ready, just an experiment
// 

public class InvokeAllScope<T> extends StructuredTaskScope<T> {
    private final Queue<T> results = new ConcurrentLinkedQueue<>();
    private final AtomicInteger successCounter = new AtomicInteger(0);
    private final AtomicInteger failureCounter = new AtomicInteger(0);
    private final AtomicInteger unavailableCounter = new AtomicInteger(0);

    public InvokeAllScope() {
        super(null, Thread.ofVirtual().factory());
    }

    @Override
    protected void handleComplete(StructuredTaskScope.Subtask<? extends T> subtask) {
        if (subtask.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
            T result = subtask.get();
            results.add(result);
            successCounter.getAndIncrement();
        } else if (subtask.state() == StructuredTaskScope.Subtask.State.FAILED) {
            failureCounter.getAndIncrement();
        } else if (subtask.state() == StructuredTaskScope.Subtask.State.UNAVAILABLE) {
            unavailableCounter.getAndIncrement();
        }
    }

    private void emitCounters() {
        boolean doEmit = true;
        if (doEmit) {
            System.out.println("TRACER scope # success: " + successCounter.get());
            System.out.println("TRACER scope # failure: " + failureCounter.get());
            System.out.println("TRACER scope # unavailable : " + unavailableCounter.get());
        }
    }

    public Stream<T> forkAll(List<Callable<T>> tasks) throws Exception {
        for (var task : tasks) {
            this.fork(() -> task.call());
        }

        this.join();
        emitCounters();

        return this.results.stream();
    }
}
