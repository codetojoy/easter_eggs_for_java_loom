
// note: I no longer own this domain
package net.codetojoy;

import java.time.*;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class Runner {
    long taskFooDelayInMillis = 20000L;
    long taskBarDelayInMillis = 15000L;

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
        Duration timeout = Duration.ofSeconds(2);

        try (var scope = StructuredTaskScope.open(Joiner.<String>anySuccessfulResultOrThrow(),
                                                  cf -> cf.withTimeout(timeout))) {
            var foo = scope.fork(() -> taskFoo()); 
            var bar = scope.fork(() -> taskBar());

            return scope.join();
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
