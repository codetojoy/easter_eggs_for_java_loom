
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

public class InvokeAllScope<T> extends StructuredTaskScope<T> {
    private final Queue<T> results = new ConcurrentLinkedQueue<>();
    private final AtomicInteger successCounter = new AtomicInteger(0);
    private final AtomicInteger failureCounter = new AtomicInteger(0);
    private final AtomicInteger cancelCounter = new AtomicInteger(0);

    public InvokeAllScope() {
        super(null, Thread.ofVirtual().factory());
    }

    @Override
    protected void handleComplete(Future<T> future) {
        if (future.state() == Future.State.SUCCESS) {
            T result = future.resultNow();
            results.add(result);
            successCounter.getAndIncrement();
        } else if (future.state() == Future.State.FAILED) {
            failureCounter.getAndIncrement();
        } else if (future.state() == Future.State.CANCELLED) {
            cancelCounter.getAndIncrement();
        }
    }

    private void emitCounters() {
        boolean doEmit = true;
        if (doEmit) {
            System.out.println("TRACER scope # success: " + successCounter.get());
            System.out.println("TRACER scope # failure: " + failureCounter.get());
            System.out.println("TRACER scope # cancel : " + cancelCounter.get());
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
