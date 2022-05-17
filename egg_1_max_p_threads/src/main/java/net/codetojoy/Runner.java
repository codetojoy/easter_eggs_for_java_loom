
package net.codetojoy;

// from https://javahippie.net/java/concurrency/2022/04/12/getting-started-with-virtual-threads.html
// and https://www.youtube.com/watch?v=UqlF6Mfhnz0

public class Runner {
    static void doWork() {
        System.out.println("TRACER working t: " + Thread.currentThread());
        try { Thread.sleep(5000); } catch (Exception ex) {} 
    }

    public static void main(String... args) throws Exception {
        int numThreads = 20;
        Thread thread = null;

        for (int i = 0; i < numThreads; i++) {
            thread = new Thread(Runner::doWork);
            thread.start();
        }
    
        thread.join();
        System.out.println("Ready.");
    }
}
