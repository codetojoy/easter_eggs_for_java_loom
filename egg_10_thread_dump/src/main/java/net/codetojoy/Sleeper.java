
// note:  I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.StructuredTaskScope;

class Sleeper { 
    private void doSleep(long delayInMillis) throws InterruptedException {
        Thread.sleep(java.time.Duration.ofMillis(delayInMillis));
    }

    private void logInfo(int index, String name) {
        System.out.println("TRACER worker name: " + name + 
                            " thread: " + Thread.currentThread() +
                            " index:" + index);
    }

    void sleep(String name) throws Exception {
        // sleep with periodic logging 
        int i = 0;
        boolean isForever = true;
        
        while (isForever) {
            logInfo(i, name);

            int chunkDelayInMillis = 500;
            doSleep(chunkDelayInMillis);
            i++;
        }
    }
}
