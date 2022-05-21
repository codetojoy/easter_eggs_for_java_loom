
// note: I no longer own this domain
package net.codetojoy;

import java.util.stream.IntStream;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

public class Runner {
    long taskFooDelayInMillis = 5000L;

    String taskFoo() {
        String result = "";
        try {
            result = new Worker().doWork(taskFooDelayInMillis, "taskFoo", "foo-5150");
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }
        return result;
    }

    String run() throws Exception {
        try (var scope = new CustomStructuredTaskScope<String>()) {
            int numTasks = 20;
            IntStream.range(0, numTasks).forEach( i -> scope.fork(() -> taskFoo())); 

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
