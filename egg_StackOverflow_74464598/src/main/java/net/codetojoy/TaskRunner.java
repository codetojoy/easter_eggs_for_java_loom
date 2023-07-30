
// note: I no longer own this domain
package net.codetojoy;

import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;

public class TaskRunner {
    private long taskDelayInMillis = 20_000L;
    private final boolean doShutdown;

    TaskRunner(boolean doShutdown) {
        this.doShutdown = doShutdown;
    }

    private String taskGetNumItemsFromRequest() throws Exception { 
        String result = "";
        try {
            result = new Worker().doWork(taskDelayInMillis, "taskGetNumItemsFromRequest", "numitems-5150");
        } catch (Exception ex) {
            System.err.println("TRACER TaskRunner num-items caught ex: " + ex);
            throw ex;
        }
        return result;
    }

    private String taskGetPriceFromDB() throws Exception { 
        String result = "";
        try {
            result = new Worker().doWork(taskDelayInMillis, "taskGetPriceFromDB", "price-6160");
        } catch (Exception ex) {
            System.err.println("TRACER TaskRunner get-price caught ex: " + ex);
            throw ex;
        }
        return result;
    }

    public String run() throws Exception {
        var result = "error";

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var numOrders = scope.fork(() -> taskGetNumItemsFromRequest()); 
            var price = scope.fork(() -> taskGetPriceFromDB());

            try { Thread.sleep(1000L); } catch (Exception ex) {} 

            if (doShutdown) {
                System.out.println("TRACER TaskRunner shutting down");
                scope.shutdown();
            } else {
                System.out.println("TRACER TaskRunner no shutdown");
            }
            
            scope.join();          
            scope.throwIfFailed();

            result = numOrders.get() + " " + price.get();

            System.out.println("TRACER TaskRunner post result");
        } catch (Exception ex) {
            if (ex != null) {
                System.err.println("TRACER TaskRunner caught ex: " + ex.getClass());
                System.err.println("TRACER TaskRunner caught ex: " + ex.getMessage());
            }
        }
        return result;
    }
}
