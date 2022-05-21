
// note: I no longer own this domain
package net.codetojoy;

import java.util.Random;
import java.util.stream.IntStream;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

public class Runner {
    private Random rn = new Random();
    private static final int MAX_DELAY_IN_SECONDS = 10;

    String taskFoo(int i) {
        String result = "";
        try {
            var value = "foo-";
            int x = rn.nextInt(MAX_DELAY_IN_SECONDS) + 1;
            long delayInMillis = x * 1000L;
            result = new Worker().doWork(delayInMillis, "taskFoo", value);
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }
        return result;
    }

    String run() throws Exception {
        try (var scope = new CustomStructuredTaskScope<String>()) {
            int numTasks = 20;
            IntStream.range(0, numTasks).forEach( i -> scope.fork(() -> taskFoo(i))); 

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
