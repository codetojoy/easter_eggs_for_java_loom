
// note:  I no longer own this domain
package net.codetojoy;

import java.util.List;
import java.util.stream.Stream;
import java.util.concurrent.Callable;

class Scope<T> { 
    Stream<T> forkAll(List<Callable<T>> tasks) throws Exception {
        try (var scope = new CustomScope<T>()) {
            for (var task : tasks) {
                scope.fork(() -> task.call());
            }

            scope.join();
    
            return scope.results();
        }
    }
}
