
// note:  I no longer own this domain
package net.codetojoy;

import java.time.Duration;

class Worker { 
    public static final long THROW_EXCEPTION = -1L;
    public static final String LOG_PREFIX = "TRACER Worker: ";

    void doSleep(long delayInMillis, String name) {
        try {
            Thread.sleep(Duration.ofMillis(delayInMillis));
        } catch (InterruptedException ex) {
            System.out.println(LOG_PREFIX + "worker interrupted name: " + name);
            throw new RuntimeException("worker interrupted", ex);
        }
    }

    void logInfo(int index, String name) {
        System.out.println(LOG_PREFIX + " name: " + name + 
                            " thread: " + Thread.currentThread() +
                            " index:" + index);
    }

    boolean isPathogenic() {
        long now = System.currentTimeMillis();
        long remainder = now % 10;
        return remainder <= 3;
    }

    // see https://stackoverflow.com/questions/15160782
    // many brain cells lost due to my bug involving checked exceptions

    String doWork(long delayInMillis, String name, String result) {
        if (delayInMillis == THROW_EXCEPTION) {
            throw new RuntimeException("operation failed: client request");
        } 

        if (delayInMillis < 1000L) {
            throw new IllegalArgumentException("illegal argument for delay: " + delayInMillis);
        }

        var isPathogenic = isPathogenic();
        
        if (isPathogenic) {
            System.err.println(LOG_PREFIX + " failed [random pathogen]. name: " + name);
            throw new RuntimeException("operation failed: random pathogen");
        }

        System.out.println(LOG_PREFIX + " operation ok. name: " + name);
        // sleep in increments for logging 

        int chunkDelayInMillis = 500;
        int numChunks = (int) delayInMillis / chunkDelayInMillis;
        for (int i = 0; i < numChunks; i += 1) {
            logInfo(i, name);
            doSleep(chunkDelayInMillis, name);
        }

        return result;
    }
}
