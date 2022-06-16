
package net.codetojoy;

// from https://javahippie.net/java/concurrency/2022/04/12/getting-started-with-virtual-threads.html
// and https://www.youtube.com/watch?v=UqlF6Mfhnz0

public class Runner {
    static void doWork() {
        System.out.println("TRACER working t: " + Thread.currentThread());
        try { Thread.sleep(5000); } catch (Exception ex) {} 
    }

    public static void main(String... args) throws Exception {
        // set this to a large number, e.g. 500_000
        int numThreads = 20_000;

        Thread thread = null;

        for (int i = 0; i < numThreads; i++) {
            thread = Thread.startVirtualThread(Runner::doWork);
        }
    
        // this is a pathological example, intended to run out of memory, 
        // so we just wait on the last thread
        thread.join();

        System.out.println("Ready.");
    }
}
