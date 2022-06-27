
// note:  I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

class FooWorker { 
    Stream<Info> spawn(String name) {
        try {
            return new BarWorker().doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER foo.spawn caught ex: " + ex);
        }
        return null;
    }

    // Create 2 Bar threads, then sleep forever.
    // We simply want to interrogate the JVM, so the actual work doesn't matter.
    // 
    List<Info> doWork(String name) throws Exception {
        List<Callable<Stream<Info>>> tasks = new ArrayList<>();
        tasks.add(() -> spawn("bar-A"));
        tasks.add(() -> spawn("bar-B"));

        var scope = new Scope<Stream<Info>>();
        var streams = scope.run(tasks);
        var results = new ArrayList<Info>();
        // ugh: fix this
        streams.forEach((stream) -> stream.forEach((info) -> { results.add(info); }));
        return results;
    }
}
