
// note:  I no longer own this domain
package net.codetojoy;

import java.util.List;
import java.util.concurrent.Callable;

import java.util.concurrent.StructuredTaskScope;

class Scope { 
    void run(List<Callable<Void>> tasks, Runnable mainWorker) throws Exception {
        try (var scope = new StructuredTaskScope<Void>()) {
            for (var task : tasks) {
                scope.fork(() -> task.call());
            }

            mainWorker.run();

            scope.join();
        }
    }
}
