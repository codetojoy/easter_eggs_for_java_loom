
// note:  I no longer own this domain
package net.codetojoy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

class FooWorker { 
    // Create 2 Bar tasks
    Stream<Info> doWork(String name) throws Exception {
        List<Callable<Stream<Info>>> tasks = new ArrayList<>();
        tasks.add(() -> new BarWorker().doWork("bar-1"));
        tasks.add(() -> new BarWorker().doWork("bar-2"));

        var scope = new Scope<Stream<Info>>();
        var streams = scope.forkAll(tasks);
        var results = streams.reduce(Stream::concat).orElseGet(Stream::empty);

        return results;
    }
}
