
// note: I no longer own this domain
package net.codetojoy;

import java.util.concurrent.StructuredTaskScope;

import java.time.Instant;
import java.io.*;

// javadoc here: https://download.java.net/java/early_access/jdk19/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/package-summary.html

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

    // We simply want to interrogate the JVM, so the actual work doesn't matter.
    // Create a Foo thread, then wait for awhile so that user can interrogate the JVM.
    // 
    void run() throws Exception {
        var name = "main";
        try (var scope = new StructuredTaskScope<Void>(name, Thread.ofVirtual().factory())) {
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
