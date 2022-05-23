
// note: I no longer own this domain
package net.codetojoy;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import jdk.incubator.concurrent.*;

// see fan-in here: https://openjdk.java.net/jeps/428
// javadoc here: https://download.java.net/java/early_access/loom/docs/api/

public class Runner {
    private static final String LOG_PREFIX = "TRACER Runner ";
    private static final String CMD_SHUTDOWN = "quit";
    private static final String CMD_DELAY_REGEX = "delay: (.*)";

    private AtomicBoolean shutdownReceived = new AtomicBoolean(false);

    Void handle(Socket socket) {
        try (var writer = new PrintWriter(socket.getOutputStream(), true);
             var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            var inMessage = reader.readLine();
            var threadInfo = Thread.currentThread().toString();
            var outMessage = "OK t: " + threadInfo;

            if (inMessage.trim().equals(CMD_SHUTDOWN)) {
                shutdownReceived.getAndSet(true);
                outMessage = "shutdown ack t: " + threadInfo;
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
            runner.serve(serverSocket);
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
