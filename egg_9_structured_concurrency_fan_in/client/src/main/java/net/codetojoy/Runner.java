
// note: I no longer own this domain
package net.codetojoy;

import java.io.*;
import java.net.*;
import java.util.stream.IntStream;

public class Runner {
    private static final String LOG_PREFIX = "TRACER Runner ";

    // TODO: send messages on seperate threads
    void run(String ip, int port) {
        int numMessages = 10;
        IntStream.range(0, numMessages).forEach(i -> new Actor().send(ip, port, "hello - " + i )); 
        new Actor().send(ip, port, "quit");
    }

    public static void main(String... args) {
        try {
            var ip = "localhost";
            var port = 5150;            // https://bit.ly/3Gc3n0n
            new Runner().run(ip, port);
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
