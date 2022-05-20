
// TBH, I no longer own this domain
package net.codetojoy;

import java.time.Duration;
import java.util.concurrent.*;
import jdk.incubator.concurrent.*;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

class Worker { 
    static final int THROW_EXCEPTION = -1;

    void doSleep(long delayInMillis) throws InterruptedException {
        Thread.sleep(Duration.ofMillis(delayInMillis));
    }

    void logInfo(int index, String name) {
        System.out.println("TRACER worker name: " + name + 
                            " thread: " + Thread.currentThread() +
                            " index:" + index);
    }

    void logInterrupt(int index, String name) {
        System.err.println("TRACER worker INTERRUPTED name: " + name + 
                            " thread: " + Thread.currentThread() +
                            " index:" + index);
    }

    String doWork(int delayInMillis, String name, String result) throws Exception {
        if (delayInMillis == THROW_EXCEPTION) {
            throw new Exception("operation failed");
        } 

        // sleep in increments so that we can see if thread is interrupted
        int chunkDelayInMillis = 100;
        int numChunks = delayInMillis / chunkDelayInMillis;
        for (int i = 0; i < numChunks; i += 1) {
            if (Thread.currentThread().isInterrupted()) {
                logInterrupt(i, name);
                return "INTERRUPTED";
            }
            logInfo(i, name);
            doSleep(chunkDelayInMillis);
        }

        return result;
    }
}

public class Runner {
    int taskFooDelayInMillis = 1000;
    int taskBarDelayInMillis = 5000;

    String taskFoo() {
        String result = "";
        try {
            result = new Worker().doWork(taskFooDelayInMillis, "taskFoo", "foo-5150");
        } catch (Exception ex) {
            System.err.println("TRACER ex: " + ex);
        }
        return result;
    }

    String taskBar() {
        String result = "";
        try {
            result = new Worker().doWork(taskBarDelayInMillis, "taskBar", "bar-6160");
        } catch (Exception ex) {
            System.err.println("TRACER caught ex: " + ex);
        }
        return result;
    }

    String run() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            Future<String>  user  = scope.fork(() -> taskFoo()); 
            Future<String> order = scope.fork(() -> taskBar());

            scope.join(); 

            return scope.result();
        }
    }

/*
    static final int CASE_1_HAPPY_PATH = 1;

    static Runner buildRunner(int which) {
        Runner runner = new Runner();
        if (which == CASE_1_HAPPY_PATH) {
            // no-op
        } 
        return runner;
    }
*/

    public static void main(String... args) {
        var runner = new Runner(); // buildRunner(which);

        try {
            String result = runner.run();
            System.out.println("TRACER result: " + result);
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
