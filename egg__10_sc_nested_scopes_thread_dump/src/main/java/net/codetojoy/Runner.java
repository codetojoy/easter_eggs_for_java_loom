
// note: I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.time.Instant;
import java.io.*;

// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

public class Runner {
    private static final int TIMEOUT_IN_SECONDS = 120;

    Void taskFoo() {
        try {
            new FooWorker().doWork("foo");
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

    void writeProcessId() throws Exception {
        long processId = ProcessHandle.current().pid();
        String fileName = "pid.txt";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
           writer.write("" + processId);
        }
    }

    public static void main(String... args) throws Exception {
        var runner = new Runner(); 

        try {
            runner.writeProcessId();
            runner.run();
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
