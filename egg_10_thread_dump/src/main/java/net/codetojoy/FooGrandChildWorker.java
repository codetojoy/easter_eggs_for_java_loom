
// note:  I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.StructuredTaskScope;

class FooGrandChildWorker extends BaseWorker { 

    String spawn() {
        String result = "";
        try {
            result = new Worker().doWork("taskFoo-worker-from-grandchild", "foo-5150");
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }
        return result;
    }

    String doWork(String name, String result) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            scope.fork(() -> spawn());
            
            // sleep in increments for logging
            int i = 0;
            boolean isForever = true;
            
            while (isForever) {
                logInfo(i, name);

                int chunkDelayInMillis = 500;
                doSleep(chunkDelayInMillis);
            }
            
            return scope.result();
        }
    }
}
