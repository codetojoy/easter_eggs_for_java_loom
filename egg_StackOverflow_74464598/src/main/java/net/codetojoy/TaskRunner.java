
// note: I no longer own this domain
package net.codetojoy;

import java.util.concurrent.Future;
import jdk.incubator.concurrent.StructuredTaskScope;

// javadoc here: https://download.java.net/java/early_access/jdk19/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/package-summary.html

public class TaskRunner {
    private long taskDelayInMillis = 20_000L;
    private final boolean doShutdown;

    TaskRunner(boolean doShutdown) {
        this.doShutdown = doShutdown;
    }

    private String taskGetNumItemsFromRequest() { 
        String result = "";
        try {
            result = new Worker().doWork(taskDelayInMillis, "taskGetNumItemsFromRequest", "numitems-5150");
        } catch (Exception ex) {
            System.err.println("TRACER TaskRunner num-items caught ex: " + ex);
        }
        return result;
    }

    private String taskGetPriceFromDB() { 
        String result = "";
        try {
            result = new Worker().doWork(taskDelayInMillis, "taskGetPriceFromDB", "price-6160");
        } catch (Exception ex) {
            System.err.println("TRACER TaskRunner get-price caught ex: " + ex);
        }
        return result;
    }

    public String run() throws Exception {
        var result = "";
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var numOrders = scope.fork(() -> taskGetNumItemsFromRequest()); 
            var price = scope.fork(() -> taskGetPriceFromDB());

            try { Thread.sleep(500L); } catch (Exception ex) {} 

            if (doShutdown) {
                System.out.println("TRACER TaskRunner shutting down");
                scope.shutdown();
            } else {
                System.out.println("TRACER TaskRunner no shutdown");
            }
            
            scope.join();          

            try {
                scope.throwIfFailed();

                result = numOrders.resultNow() + " " + price.resultNow();
            } catch (Exception ex) {
                if (ex != null) {
                    System.err.println("TRACER TaskRunner inner caught ex: " + ex.getClass());
                    System.err.println("TRACER TaskRunner inner caught ex: " + ex.getMessage());
                }
            } 
        } catch (Exception ex) {
            if (ex != null) {
                System.err.println("TRACER TaskRunner caught ex: " + ex.getClass());
                System.err.println("TRACER TaskRunner caught ex: " + ex.getMessage());
            }
        }
        return result;
    }
}
