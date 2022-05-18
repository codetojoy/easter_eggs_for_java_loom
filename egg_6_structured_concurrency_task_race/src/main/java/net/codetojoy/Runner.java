
package net.codetojoy;

import java.util.*;
import java.util.concurrent.*;
import jdk.incubator.concurrent.*;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

class Worker { 
    static final int THROW_EXCEPTION = -1;

    void doSleep(int delayInMillis) {
        try { Thread.sleep(delayInMillis); } catch (Exception ex) {} 
    }
    
    void log(int index, String name) {
        System.out.println("TRACER worker name: " + name + 
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
            log(i, name);
            doSleep(chunkDelayInMillis);
        }

        return result;
    }
}

public class Runner {
    int taskFooDelayInMillis = 1000;
    int taskBarDelayInMillis = 5000;

    String taskFoo() throws Exception { 
        return new Worker().doWork(taskFooDelayInMillis, "taskFoo", "FOO-5150");
    }

    String taskBar() throws Exception { 
        return new Worker().doWork(taskBarDelayInMillis, "taskBar", "BAR-6160");
    }

    String run() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess()) {
            Future<String>  user  = scope.fork(() -> taskFoo()); 
            Future<String> order = scope.fork(() -> taskBar());

            scope.join(); 

            return (String) scope.result();
        }
    }

    static final int CASE_1_HAPPY_PATH = 1;

    static Runner buildRunner(int which) {
        Runner runner = new Runner();
        if (which == CASE_1_HAPPY_PATH) {
            // no-op
        } 
        return runner;
    }

    public static void main(String... args) {
        int which = CASE_1_HAPPY_PATH;
        var runner = buildRunner(which);

        try {
            String result = runner.run();
            System.out.println("TRACER result: " + result);
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
