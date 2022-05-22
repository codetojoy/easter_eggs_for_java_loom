
// note:  I no longer own this domain
package net.codetojoy;

import java.time.Duration;
import java.util.Random;

class Worker { 
    public static final long THROW_EXCEPTION = -1L;
    // private final Random random = new Random(System.currentTimeMillis());

    void doSleep(long delayInMillis) {
        try {
            Thread.sleep(Duration.ofMillis(delayInMillis));
        } catch (InterruptedException ex) {
            throw new RuntimeException("worker interrupted", ex);
        }
    }

    void logInfo(int index, String name) {
        System.out.println("TRACER worker name: " + name + 
                            " thread: " + Thread.currentThread() +
                            " index:" + index);
    }

    boolean isPathogenic() {
        long now = System.currentTimeMillis();
        long remainder = now % 10;
        return remainder == 0 || remainder == 5;
    }

    String doWork(long delayInMillis, String name, String result) {
        if (delayInMillis == THROW_EXCEPTION) {
            throw new RuntimeException("operation failed: client request");
        } 

        var isPathogenic = isPathogenic();
        System.err.println("TRACER worker trace isP: " + isPathogenic);
        
        if (isPathogenic()) {
            System.err.println("TRACER worker: failed [coin flip]. name: " + name);
            throw new RuntimeException("operation failed: coin flip");
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
