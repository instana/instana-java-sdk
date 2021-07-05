package com.instana.sample.http.custom;

import com.instana.sdk.annotation.Span;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CustomHttpClient {

  @Span(type = Span.Type.EXIT, value = "http-client")
  public void makeCall(String host, int port, String path) throws IOException {
    try (Socket socket = new Socket(host, port);
         PrintWriter outputStream = new PrintWriter(socket.getOutputStream());
         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
      outputStream.println("GET " + path + " HTTP/1.1");
      outputStream.println("");
      outputStream.flush();
      reader.lines().forEach(System.out::println);
    }
  }
}
