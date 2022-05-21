
// TBH, I no longer own this domain
package net.codetojoy;

import java.time.Duration;
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

    String doWork(int delayInMillis, String name, String result) throws Exception {
        if (delayInMillis == THROW_EXCEPTION) {
            throw new Exception("operation failed");
        } 

        // sleep in increments for logging 
        int chunkDelayInMillis = 200;
        int numChunks = delayInMillis / chunkDelayInMillis;
        for (int i = 0; i < numChunks; i += 1) {
            logInfo(i, name);
            doSleep(chunkDelayInMillis);
        }

        return result;
    }
}

public class Runner {
    int taskFooDelayInMillis = 2000;
    int taskBarDelayInMillis = 15000;

    String taskFoo() {
        String result = "";
        try {
            result = new Worker().doWork(taskFooDelayInMillis, "taskFoo", "foo-5150");
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }
        return result;
    }

    String taskBar() {
        String result = "";
        try {
            result = new Worker().doWork(taskBarDelayInMillis, "taskBar", "bar-6160");
        } catch (Exception ex) {
            System.err.println("TRACER bar caught ex: " + ex);
        }
        return result;
    }

    String run() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            var foo = scope.fork(() -> taskFoo()); 
            var bar = scope.fork(() -> taskBar());

            scope.join(); 

            return scope.result();
        }
    }

    public static void main(String... args) {
        var runner = new Runner(); 

        try {
            String result = runner.run();
            System.out.println("TRACER result: " + result);
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
