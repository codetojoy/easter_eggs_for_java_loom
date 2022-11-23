
// note: I no longer own this domain
package net.codetojoy;

import jdk.incubator.concurrent.*;

// javadoc here: https://download.java.net/java/early_access/jdk19/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/package-summary.html

public class Runner {
    String runInThread(boolean doShutdown) throws Exception {
        try (var scope = new StructuredTaskScope<String>()) {
            var future = scope.fork(() -> new TaskRunner(doShutdown).run());   
            scope.join();
            return future.resultNow();
        }
    }

    public static void main(String... args) {
        var runner = new Runner();

        try {
            String arg = null;
            if (args.length >= 1) {
                arg = args[0];
            }
            var doShutdown = arg != null && (arg.equalsIgnoreCase("yes") || arg.equalsIgnoreCase("y"));
            System.out.println("TRACER Runner doShutdown: " + doShutdown);
            var result = runner.runInThread(doShutdown);
            System.out.println("TRACER Runner sleeping");
            try { Thread.sleep(12_000L); } catch (Exception ex) {} 
            System.out.println("TRACER Runner done result: " + result);
        } catch (Exception ex) {
            System.err.println("TRACER Runner caught exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
