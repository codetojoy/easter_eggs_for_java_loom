
// note:  I no longer own this domain
package net.codetojoy;

class Worker { 
    static final long THROW_EXCEPTION = -1L;

    void doSleep(long delayInMillis) throws Exception {
        Thread.sleep(java.time.Duration.ofMillis(delayInMillis));
    }

    void logInfo(int index, String name) {
        System.out.println("TRACER worker name: " + name + 
                            " thread: " + Thread.currentThread() +
                            " index:" + index);
    }

    String doWork(long delayInMillis, String name, String result) throws Exception {
        if (delayInMillis == THROW_EXCEPTION) {
            throw new Exception("operation failed");
        } 

        // sleep in increments for logging 
        int chunkDelayInMillis = 1000;
        int numChunks = (int) delayInMillis / chunkDelayInMillis;
        for (int i = 0; i < numChunks; i += 1) {
            logInfo(i, name);
            doSleep(chunkDelayInMillis);
        }

        return result;
    }
}
