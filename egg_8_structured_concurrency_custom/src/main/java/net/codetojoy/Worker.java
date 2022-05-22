
// note:  I no longer own this domain
package net.codetojoy;

import java.time.Duration;
import java.util.Random;

class Worker { 
    public static final long THROW_EXCEPTION = -1L;
    private Random random = new Random();

    void doSleep(long delayInMillis) throws InterruptedException {
        Thread.sleep(Duration.ofMillis(delayInMillis));
    }

    void logInfo(int index, String name) {
        System.out.println("TRACER worker name: " + name + 
                            " thread: " + Thread.currentThread() +
                            " index:" + index);
    }

    boolean isPathogenic() {
        // approximately 1 in N chance of failing
        int n = 10;
        return false; // random.nextInt(n) == 0;
    }

    String doWork(long delayInMillis, String name, String result) throws Exception {
        if (delayInMillis == THROW_EXCEPTION) {
            throw new Exception("operation failed: client request");
        } 

        if (isPathogenic()) {
            System.err.println("TRACER worker: failed [coin flip]. name: " + name);
            throw new Exception("operation failed: coin flip");
        }

        System.out.println("TRACER worker: operation ok. name: " + name);
        // sleep in increments for logging 

        int chunkDelayInMillis = 200;
        int numChunks = (int) delayInMillis / chunkDelayInMillis;
        for (int i = 0; i < numChunks; i += 1) {
            logInfo(i, name);
            doSleep(chunkDelayInMillis);
        }

        return result;
    }
}
