
// note: I no longer own this domain
package net.codetojoy;

import static net.codetojoy.Constants.*;

import java.util.*;
import java.util.stream.IntStream;
import java.util.concurrent.*;

public class Runner {
    private final Random random = new Random(System.currentTimeMillis());

    String taskFoo(int i) {
        long delayInMillis = (random.nextInt(MAX_DELAY_IN_SECONDS) + 1) * 1000L;
        var name = "TaskFoo-" + i;
        var value = "foo-" + i;

        var result = new Worker().doWork(delayInMillis, name, value);

        return result;
    }

    List<String> run() throws Exception {
        StructuredTaskScope.Joiner<String,List<String>> joiner = new CustomJoiner(NUM_TASKS_FOR_SUCCESS);
        try (var scope = StructuredTaskScope.open(joiner)) {
            IntStream.range(0, NUM_TASKS).forEach(i -> scope.fork(() -> taskFoo(i))); 

            return scope.join();
        }
    }

    public static void main(String... args) {
        var runner = new Runner(); 

        try {
            var results = runner.run();
            for (var result : results) {
                System.out.println("TRACER result: " + result.toString());
            }
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
