
// note:  I no longer own this domain
package net.codetojoy;

import java.util.concurrent.StructuredTaskScope;

class FooWorker { 
    Void spawn(String name) {
        try {
            new BarWorker().doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }
        return null;
    }

    // Create 2 Bar threads, then sleep forever.
    // We simply want to interrogate the JVM, so the actual work doesn't matter.
    // 
    void doWork(String name) throws Exception {
        try (var scope = new StructuredTaskScope<Void>(name, Thread.ofVirtual().factory())) {
            scope.fork(() -> spawn("bar-A"));
            scope.fork(() -> spawn("bar-B"));

            new Sleeper().sleep(name);

            scope.join();
        }
    }
}
