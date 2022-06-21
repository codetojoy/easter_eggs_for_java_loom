
// note:  I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.StructuredTaskScope;

class FooWorker { 
    Void spawn(String name) {
        try {
            new BarWorker().doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }
        return null;
    }

    void doWork(String name) throws Exception {
        try (var scope = new StructuredTaskScope<Void>()) {
            scope.fork(() -> spawn("bar-A"));
            scope.fork(() -> spawn("bar-B"));

            new Sleeper().sleep(name);

            scope.join();
        }
    }
}
