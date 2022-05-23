
// note: I no longer own this domain
package net.codetojoy;

import java.io.*;
import java.net.*;
import java.util.stream.IntStream;

import jdk.incubator.concurrent.*;

public class Runner {
    private static final String LOG_PREFIX = "TRACER Runner ";
    private static final String COMMAND_SHUTDOWN = "quit";
    private final String ip;
    private final int port;

    public Runner(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    Void runActor(int i) {
        new Actor().send(ip, port, "hello - " + i); 
        return null;
    } 

    void run(String ip, int port) throws Exception {
        try (var scope = new StructuredTaskScope<Void>()) {
            int numActors = 50;
            IntStream.range(0, numActors).forEach(i -> scope.fork(() -> runActor(i))); 

            scope.join();
        }

        new Actor().send(ip, port, COMMAND_SHUTDOWN);
    }

    public static void main(String... args) {
        try {
            var ip = "localhost";
            var port = 5150;                     // https://bit.ly/3Gc3n0n
            var runner = new Runner(ip, port);
            runner.run(ip, port);
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
