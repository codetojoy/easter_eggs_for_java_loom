
// note:  I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.StructuredTaskScope;

class BaseWorker { 
    void doSleep(long delayInMillis) throws InterruptedException {
        Thread.sleep(java.time.Duration.ofMillis(delayInMillis));
    }

    void logInfo(int index, String name) {
        System.out.println("TRACER worker name: " + name + 
                            " thread: " + Thread.currentThread() +
                            " index:" + index);
    }
}
