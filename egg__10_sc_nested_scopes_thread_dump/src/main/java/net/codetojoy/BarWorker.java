
// note:  I no longer own this domain
package net.codetojoy;

import java.util.concurrent.StructuredTaskScope;

class BarWorker { 

    Void spawn(String name) {
        try {
            new Worker().doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);

        }
        return null;
    }

    // Create 3 worker threads, then sleep forever.
    // We simply want to interrogate the JVM, so the actual work doesn't matter.
    // 
    void doWork(String name) throws Exception {
        try (var scope = new StructuredTaskScope<Void>(name, Thread.ofVirtual().factory())) {
            scope.fork(() -> spawn("worker-A"));
            scope.fork(() -> spawn("worker-B"));
            scope.fork(() -> spawn("worker-C"));
            
            new Sleeper().sleep(name);
            
            scope.join();
        }
    }
}
