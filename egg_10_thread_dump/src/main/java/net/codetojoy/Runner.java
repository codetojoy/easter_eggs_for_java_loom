
// note: I no longer own this domain
package net.codetojoy;

import java.time.*;
import jdk.incubator.concurrent.StructuredTaskScope;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

public class Runner {
    String taskFoo(StructuredTaskScope scope) {
        String result = "";
        try {
            result = new FooChildWorker().doWork(scope, "taskFoo-is-child", "foo-5150");
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }
        return result;
    }

    String run() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            var foo = scope.fork(() -> taskFoo(scope)); 

            var deadline = Instant.now().plusSeconds(45); 
            scope.joinUntil(deadline); 

            return scope.result();
        }
    }

    public static void main(String... args) {
        var runner = new Runner(); 

        try {
            long processId = ProcessHandle.current().pid();
            System.out.println("TRACER running with process id: " + processId);
            String result = runner.run();
            System.out.println("TRACER result: " + result);
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
