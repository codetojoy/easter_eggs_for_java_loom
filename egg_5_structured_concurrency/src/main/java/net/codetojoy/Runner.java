
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
    int findUserDelayInMillis = 1000;
    int fetchOrderDelayInMillis = 5000;

    String findUser() throws Exception { 
        return new Worker().doWork(findUserDelayInMillis, "findUser", "user-5150");
    }

    String fetchOrder() throws Exception { 
        return new Worker().doWork(fetchOrderDelayInMillis, "fetchOrder", "order-6160");
    }

    String run() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<String>  user  = scope.fork(() -> findUser()); 
            Future<String> order = scope.fork(() -> fetchOrder());

            scope.join();          // Join both forks
            scope.throwIfFailed(); // ... and propagate errors

            // Here, both forks have succeeded, so compose their results
            return user.resultNow() + " " + order.resultNow();
        }
    }

    static final int CASE_1_HAPPY_PATH = 1;
    static final int CASE_2_FIND_USER_FAILS = 2;
    static final int CASE_3_FETCH_ORDER_FAILS = 3;

    static Runner buildRunner(int which) {
        Runner runner = new Runner();
        if (which == CASE_1_HAPPY_PATH) {
            // no-op
        } else if (which == CASE_2_FIND_USER_FAILS) {
            // find user will fail
            runner.findUserDelayInMillis = Worker.THROW_EXCEPTION;
        } else if (which == CASE_3_FETCH_ORDER_FAILS) {
            // fetch order will fail
            runner.fetchOrderDelayInMillis = Worker.THROW_EXCEPTION;
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
