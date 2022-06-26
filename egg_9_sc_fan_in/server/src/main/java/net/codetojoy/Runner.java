
// note: I no longer own this domain
package net.codetojoy;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import jdk.incubator.concurrent.*;

// see fan-in here: https://openjdk.java.net/jeps/428
// javadoc here: https://download.java.net/java/early_access/jdk19/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/package-summary.html

public class Runner {
    private static final String LOG_PREFIX = "TRACER Runner ";
    private static final String CMD_SHUTDOWN = "quit";
    private static final String CMD_DELAY_REGEX = "delay: (.*)";

    private AtomicBoolean shutdownReceived = new AtomicBoolean(false);

    private final Random random = new Random(System.currentTimeMillis());
    private static final int MAX_DELAY_IN_SECONDS = 3;

    void simulateWork() {
        long delayInMillis = (random.nextInt(MAX_DELAY_IN_SECONDS) + 1) * 1000L;
        try { Thread.sleep(delayInMillis); } catch (Exception ex) {} 
    }

    Void handle(Socket socket) {
        try (var writer = new PrintWriter(socket.getOutputStream(), true);
             var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            var inMessage = reader.readLine();
            var threadInfo = Thread.currentThread().toString();
            var outMessage = "OK server-t: " + threadInfo;

            if (inMessage.trim().equals(CMD_SHUTDOWN)) {
                System.out.println(LOG_PREFIX + " received shutdown request");
                shutdownReceived.getAndSet(true);
                outMessage = "shutdown ack t: " + threadInfo;
            } else {
                simulateWork();
            }
            writer.println(outMessage);
        } catch (Exception ex) {
            System.err.println(LOG_PREFIX + " caught ex: " + ex.getMessage());
        }
        return null;
    }

    void serve(ServerSocket serverSocket) throws IOException, InterruptedException {
        try (var scope = new StructuredTaskScope<Void>()) {
            try {
                while (! shutdownReceived.get()) {
                    var socket = serverSocket.accept();
                    scope.fork(() -> handle(socket));
                }
            } finally {
                System.out.println(LOG_PREFIX + " shutting down");
                scope.shutdown();
                scope.join();
            }
        }
    }

    public static void main(String... args) {
        var runner = new Runner(); 

        try {
            var port = 5150;
            var serverSocket = new ServerSocket(port);
            System.out.println(LOG_PREFIX + " listening on " + port);
            runner.serve(serverSocket);
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
