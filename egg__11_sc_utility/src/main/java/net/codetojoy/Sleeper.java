
// note:  I no longer own this domain
package net.codetojoy;

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
        long delayInMillis = 1400L;

        // sleep in increments for logging 
        int chunkDelayInMillis = 200;
        int numChunks = (int) delayInMillis / chunkDelayInMillis;

        for (int i = 0; i < numChunks; i += 1) {
            logInfo(i, name);
            doSleep(chunkDelayInMillis);
        }
    }
}
