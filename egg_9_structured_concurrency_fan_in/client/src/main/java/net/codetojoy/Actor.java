
// note: I no longer own this domain
package net.codetojoy;

import java.io.*;
import java.net.*;
import java.util.stream.IntStream;

class Actor {
    private static final String LOG_PREFIX = "TRACER Actor";
    private static final String OUTPUT_FORMAT = "%s [%s] received: %s";

    void send(String ip, int port, String message) {
        try (
            var clientSocket = new Socket(ip, port);
            var writer = new PrintWriter(clientSocket.getOutputStream(), true);
            var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
            writer.println(message);
            String response = reader.readLine();
            var threadInfo = Thread.currentThread().toString();
            var outStr = String.format(OUTPUT_FORMAT, LOG_PREFIX, threadInfo, response);
            System.out.println(outStr);
        } catch (Exception ex) { 
            System.err.println(LOG_PREFIX + " caught ex: " + ex.getMessage());
        }
    }
}
