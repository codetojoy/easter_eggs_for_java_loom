
// note: I no longer own this domain
package net.codetojoy;

import java.io.*;
import java.net.*;
import java.util.stream.IntStream;

import java.util.concurrent.*;

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
        new Actor(i).send(ip, port, "hello - " + i); 
        return null;
    } 

    void run(String ip, int port) throws Exception {
        try (var scope = StructuredTaskScope.open()) {
            int numActors = 20;
            IntStream.range(0, numActors).forEach(i -> scope.fork(() -> runActor(i))); 

            scope.join();
        }

        // poor design but we need to send 'quit' twice :-)
        new Actor(666).send(ip, port, COMMAND_SHUTDOWN);
        new Actor(777).send(ip, port, COMMAND_SHUTDOWN);
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
