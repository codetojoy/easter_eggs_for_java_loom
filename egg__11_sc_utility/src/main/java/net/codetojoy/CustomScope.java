
// note: I no longer own this domain
package net.codetojoy;

import java.util.*;
import java.util.stream.Stream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

class CustomScope<T> extends StructuredTaskScope<T> {
    private final Queue<T> results = new ConcurrentLinkedQueue<>();

    CustomScope() {
        super(null, Thread.ofVirtual().factory());
    }

    @Override
    protected void handleComplete(StructuredTaskScope.Subtask<? extends T> subtask) {
        if (subtask.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
            T result = subtask.get();
            results.add(result);
        }
    }

    public Stream<T> results() {  
        return results.stream();
    }
}
