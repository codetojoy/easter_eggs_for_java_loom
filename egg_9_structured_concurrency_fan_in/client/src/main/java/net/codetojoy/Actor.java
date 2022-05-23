
// note: I no longer own this domain
package net.codetojoy;

import java.io.*;
import java.net.*;
import java.util.stream.IntStream;

class Actor {
    private static final String LOG_PREFIX = "TRACER Actor";
    private static final String OUTPUT_FORMAT = "%s [%s] received: %s";
    private static final String REGEX_STR = "\\/runnable.*?worker-\\d+";

    // e.g. s: VirtualThread[#35]/runnable@ForkJoinPool-1-worker-1
    //      return: VirtualThread[#35]/-1 
    String cleanThreadInfo(String s) {
        return s.replaceAll(REGEX_STR, "");
    }

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
            var cleanOutStr = cleanThreadInfo(outStr);
            System.out.println(cleanOutStr);
        } catch (Exception ex) { 
            System.err.println(LOG_PREFIX + " caught ex: " + ex.getMessage());
        }
    }
}
