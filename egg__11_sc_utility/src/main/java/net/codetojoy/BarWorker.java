
// note:  I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.stream.Stream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class BarWorker { 
    private static final AtomicInteger counter = new AtomicInteger(5150);

    Integer spawn(String name) {
        int result = -1;
        try {
            int expectedResult = counter.getAndIncrement();
            result =  new Worker<Integer>(expectedResult).doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER Bar.spawn caught ex: " + ex);
        }
        return result;
    }

    Stream<Integer> doWork(String name) throws Exception {
        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(() -> spawn("worker-A"));
        tasks.add(() -> spawn("worker-B"));
        tasks.add(() -> spawn("worker-C"));

        var scope = new Scope<Integer>();
        var results = scope.run(tasks);
        return results;
    }
}
