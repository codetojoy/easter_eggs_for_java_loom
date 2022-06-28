
// note:  I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.stream.Stream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class BarWorker { 
    private static final AtomicInteger counter = new AtomicInteger(5150);

    private Info spawn(String name) {
        Info result = null;
        try {
            int expectedResult = counter.getAndIncrement();
            var expectedInfo = new Info(expectedResult, name);
            result = new Worker<Info>(expectedInfo).doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER Bar.spawn caught ex: " + ex);
        }
        return result;
    }

    public Stream<Info> doWork(String name) throws Exception {
        List<Callable<Info>> tasks = new ArrayList<>();
        tasks.add(() -> spawn("worker-A [" + name + "]"));
        tasks.add(() -> spawn("worker-B [" + name + "]"));
        tasks.add(() -> spawn("worker-C [" + name + "]"));

        var results = Stream.<Info>empty();
        try (var scope = new InvokeAllScope<Info>()) {
            results = scope.forkAll(tasks);
        }
        return results;
    }
}
