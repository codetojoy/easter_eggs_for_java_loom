
// note:  I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.stream.Stream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class BarWorker { 
    private static final AtomicInteger counter = new AtomicInteger(5150);

    Info spawn(String name) {
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

    Stream<Info> doWork(String name) throws Exception {
        List<Callable<Info>> tasks = new ArrayList<>();
        tasks.add(() -> spawn("worker-A"));
        tasks.add(() -> spawn("worker-B"));
        tasks.add(() -> spawn("worker-C"));

        var scope = new Scope<Info>();
        var results = scope.run(tasks);
        return results;
    }
}
