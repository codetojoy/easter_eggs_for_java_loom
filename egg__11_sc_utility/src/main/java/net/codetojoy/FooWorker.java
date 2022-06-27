
// note:  I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

class FooWorker { 
    Stream<Integer> spawn(String name) {
        try {
            return new BarWorker().doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER foo.spawn caught ex: " + ex);
        }
        return null;
    }

    /*
    void sleep(String name) {
        try {
            new Sleeper().sleep(name); 
        } catch (Exception ex) {
            System.err.println("TRACER foo.sleep caught ex: " + ex);
        }
    }
*/

    // Create 2 Bar threads, then sleep forever.
    // We simply want to interrogate the JVM, so the actual work doesn't matter.
    // 
    List<Integer> doWork(String name) throws Exception {
        List<Callable<Stream<Integer>>> tasks = new ArrayList<>();
        tasks.add(() -> spawn("bar-A"));
        tasks.add(() -> spawn("bar-B"));

        var scope = new Scope<Stream<Integer>>();
        var streams = scope.run(tasks);
        var results = new ArrayList<Integer>();
        // ugh: fix this
        streams.forEach((stream) -> stream.forEach((i) -> { results.add(i); }));
        return results;
    }
}
