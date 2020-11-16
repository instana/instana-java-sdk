package com.instana.sample.http.custom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class CustomHttpFramework {

  private final Map<String, Function<Map<String, String>, String>> handlers = new ConcurrentHashMap<>();

  public void register(String path, Function<Map<String, String>, String> handler) {
    handlers.put(path, handler);
  }

  public void runOnPort(int port) throws IOException {
    try (ServerSocket server = new ServerSocket(port)) {
      while (true) {
        try (Socket connection = server.accept()) {
          BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          String path = readPath(in);
          Map<String, String> headers = readHeaders(in);
          PrintWriter out = new PrintWriter(connection.getOutputStream());
          Function<Map<String, String>, String> handler = handlers.get(path);
          if (handler == null) {
            out.println("HTTP/1.0 404 Not Found");
            out.println("Content-Type: text/plain");
            out.println();
            out.println("404 Not Found");
            out.flush();
          } else {
            String responseBody = handler.apply(headers);
            out.println("HTTP/1.0 200 OK");
            out.println("Content-Type: text/plain");
            out.println();
            out.println(responseBody);
            out.flush();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private String readPath(BufferedReader in) throws IOException {
    String line = in.readLine();
    if (!line.startsWith("GET /")) {
      throw new IOException("Failed to parse first request line: " + line);
    }
    return line.split(" ")[1];
  }

  private Map<String, String> readHeaders(BufferedReader in) throws IOException {
    Map<String, String> headers = new HashMap<>();
    while (true) {
      String line = in.readLine();
      if (line.length() == 0) {
        return headers;
      }
      if (!line.contains(":")) {
        throw new IOException("Failed to parse request header: " + line);
      }
      String[] header = line.split(":", 2);
      headers.put(header[0].trim(), header[1].trim());
    }
  }
}
