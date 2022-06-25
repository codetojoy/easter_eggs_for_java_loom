
// note:  I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.concurrent.Callable;

class BarWorker { 

    Void spawn(String name) {
        try {
            new Worker().doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER Bar.spawn caught ex: " + ex);

        }
        return null;
    }

    void sleep(String name) {
        try {
            new Sleeper().sleep(name); 
        } catch (Exception ex) {
            System.err.println("TRACER Bar.sleep caught ex: " + ex);
        }
    }


    // Create 3 worker threads, then sleep forever.
    // We simply want to interrogate the JVM, so the actual work doesn't matter.
    // 
    void doWork(String name) throws Exception {
        var scope = new Scope();
        List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
        tasks.add(() -> spawn("worker-A"));
        tasks.add(() -> spawn("worker-B"));
        tasks.add(() -> spawn("worker-C"));
        scope.run(tasks, () -> sleep(name));
    }
}
