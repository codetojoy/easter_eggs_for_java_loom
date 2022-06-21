
// note:  I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.StructuredTaskScope;

class FooGrandChildWorker { 

    Void spawn(String name) {
        try {
            new Worker().doWork(name);
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);

        }
        return null;
    }

    void doWork(String name) throws Exception {
        try (var scope = new StructuredTaskScope<Void>()) {
            scope.fork(() -> spawn("worker-A"));
            scope.fork(() -> spawn("worker-B"));
            scope.fork(() -> spawn("worker-C"));
            
            new Sleeper().sleep(name);
            
            scope.join();
        }
    }
}
