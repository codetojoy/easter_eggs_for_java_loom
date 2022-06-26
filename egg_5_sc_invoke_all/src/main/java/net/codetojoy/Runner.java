
// note: I no longer own this domain
package net.codetojoy;

import java.time.Duration;
import java.util.concurrent.*;
import jdk.incubator.concurrent.*;

// javadoc here: https://download.java.net/java/early_access/jdk19/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/package-summary.html

public class Runner {
    long taskFooDelayInMillis = 1000L;
    long taskBarDelayInMillis = 5000L;

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
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<String> foo = scope.fork(() -> taskFoo()); 
            Future<String> bar = scope.fork(() -> taskBar());

            scope.join();          // Join both forks
            scope.throwIfFailed(); // and propagate errors

            // Here, both forks have succeeded, so compose their results
            return foo.resultNow() + " " + bar.resultNow();
        }
    }

    static final int CASE_1_HAPPY_PATH = 1;
    static final int CASE_2_TASK_FOO_FAILS = 2;
    static final int CASE_3_TASK_BAR_FAILS = 3;

    static Runner buildRunner(int which) {
        Runner runner = new Runner();
        if (which == CASE_1_HAPPY_PATH) {
            // no-op
        } else if (which == CASE_2_TASK_FOO_FAILS) {
            // find user will fail
            runner.taskFooDelayInMillis = Worker.THROW_EXCEPTION;
        } else if (which == CASE_3_TASK_BAR_FAILS) {
            // fetch order will fail
            runner.taskBarDelayInMillis = Worker.THROW_EXCEPTION;
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
            ex.printStackTrace();
        }
    }
}
