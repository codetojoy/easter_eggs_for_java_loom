
// note: I no longer own this domain
package net.codetojoy;

import java.io.*;
import java.net.*;
import java.util.stream.IntStream;

public class Runner {
    private static final String LOG_PREFIX = "TRACER Runner ";

    void send(String ip, int port, String message) {
        try (
            var clientSocket = new Socket(ip, port);
            var writer = new PrintWriter(clientSocket.getOutputStream(), true);
            var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
            writer.println(message);
            String response = reader.readLine();
            System.out.println(LOG_PREFIX + " received: " + response);
        } catch (Exception ex) { 
            System.err.println(LOG_PREFIX + " caught ex: " + ex.getMessage());
        }
    }

    // TODO: send messages on seperate threads
    void emit(String ip, int port) {
        int numMessages = 10;
        IntStream.range(0, numMessages).forEach(i -> send(ip, port, "hello - " + i )); 
        send(ip, port, "quit");
    }

    public static void main(String... args) {
        try {
            var ip = "localhost";
            var port = 5150;
            new Runner().emit(ip, port);
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
