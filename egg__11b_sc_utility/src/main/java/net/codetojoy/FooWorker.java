
// note:  I no longer own this domain
package net.codetojoy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

class FooWorker { 
    // Create 2 Bar tasks
    public Stream<Info> doWork(String name) throws Exception {
        List<Callable<Stream<Info>>> tasks = new ArrayList<>();
        tasks.add(() -> new BarWorker().doWork("bar-1"));
        tasks.add(() -> new BarWorker().doWork("bar-2"));

        var results = Stream.<Info>empty();
        try (var scope = new InvokeAllScope<Stream<Info>>()) {
            var streams = scope.forkAll(tasks);
            results = streams.reduce(Stream::concat).orElseGet(Stream::empty);
        }

        return results;
    }
}
