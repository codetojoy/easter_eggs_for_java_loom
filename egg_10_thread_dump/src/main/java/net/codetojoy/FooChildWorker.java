
// note:  I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.StructuredTaskScope;

class FooChildWorker extends BaseWorker { 
    String spawn(StructuredTaskScope scope) {
        String result = "";
        try {
            result = new FooGrandChildWorker().doWork(scope, "taskFoo-is-grandchild", "foo-5150");
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }
        return result;
    }

    String doWork(StructuredTaskScope scope, String name, String result) throws Exception {
        scope.fork(() -> spawn(scope));

        // sleep in increments for logging 
        int chunkDelayInMillis = 500;
        int i = 0;
        boolean isForever = true;
        
        while (isForever) {
            logInfo(i, name);
            doSleep(chunkDelayInMillis);
        }

        return result;
    }
}
