
// note: I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.time.Instant;
import java.io.*;
import java.util.*;

// javadoc here: https://download.java.net/java/early_access/jdk19/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/package-summary.html

public class Runner {
    private static final int TIMEOUT_IN_SECONDS = 10;

    List<Info> taskFoo() {
        List<Info> result = new ArrayList<>();

        try {
            result = new FooWorker().doWork("foo");
        } catch (Exception ex) {
            System.err.println("TRACER foo caught ex: " + ex);
        }

        return result;
    }

    // We simply want to interrogate the JVM, so the actual work doesn't matter.
    // Create a Foo thread, then wait for awhile so that user can interrogate the JVM.
    // 
    void run() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var foo = scope.fork(() -> taskFoo()); 

            var deadline = Instant.now().plusSeconds(TIMEOUT_IN_SECONDS); 
            scope.joinUntil(deadline); 

            var infos = foo.resultNow();
            for (var info : infos) {
                System.out.println("TRACER received: " + info.toString());
            };
        }
    }

    public static void main(String... args) {
        try {
            new Runner().run();
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
