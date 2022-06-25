
// note:  I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.concurrent.Callable;

class FooWorker { 
    Void spawn(String name) {
        try {
            new BarWorker().doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER foo.spawn caught ex: " + ex);
        }
        return null;
    }

    void sleep(String name) {
        try {
            new Sleeper().sleep(name); 
        } catch (Exception ex) {
            System.err.println("TRACER foo.sleep caught ex: " + ex);
        }
    }

    // Create 2 Bar threads, then sleep forever.
    // We simply want to interrogate the JVM, so the actual work doesn't matter.
    // 
    void doWork(String name) throws Exception {
        var scope = new Scope();
        List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
        tasks.add(() -> spawn("bar-A"));
        tasks.add(() -> spawn("bar-B"));
        scope.run(tasks, () -> sleep(name));
    }
}
