
package net.codetojoy;

import java.util.concurrent.*;
import java.util.*;

class MyTask implements Runnable {
    int id;

    MyTask(int id) {
        this.id = id;
    }

    @Override 
    public void run() {
        try {
            long delayInMillis = id * 1000;
            System.out.println("TRACER id: " + id + 
                                " t: " + Thread.currentThread() + 
                                " sleeping...");
            Thread.sleep(delayInMillis);
        } catch(Exception ex) {
        }
    }
}

public class Runner {
    void run() throws Exception {
        int numThreads = 8;
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            int numTasks = 10; 
            for (int i = 0; i < numTasks; i++) {
                executor.submit(new MyTask(i)); 
            }
        }
        // executor is closed/shutdown here
    }

    static public void main(String... args) throws Exception {
        new Runner().run();
        System.out.println("Ready.");
    }
}
