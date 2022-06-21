
// note: I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.StructuredTaskScope;
import java.time.Instant;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

public class Runner {
    private static final int TIMEOUT_IN_SECONDS = 120;

    Void taskFoo() {
        try {
            new FooChildWorker().doWork("foo");
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }
        return null;
    }

    void run() throws Exception {
        try (var scope = new StructuredTaskScope<Void>()) {
            var foo = scope.fork(() -> taskFoo()); 

            var deadline = Instant.now().plusSeconds(TIMEOUT_IN_SECONDS); 
            scope.joinUntil(deadline); 
        }
    }

    public static void main(String... args) {
        var runner = new Runner(); 

        try {
            long processId = ProcessHandle.current().pid();
            System.out.println("TRACER running with process id: " + processId);
            runner.run();
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
