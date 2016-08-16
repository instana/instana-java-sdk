package com.acme.tcp.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import com.instana.sdk.annotation.Span;
import com.instana.sdk.annotation.Span.Type;
import com.instana.sdk.support.SpanSupport;

public class CustomTCPClient {

  @Span(type = Type.EXIT, value = "custom-tcp-client", capturedStackFrames = 5)
  public static Map<String, String> callServer(Map<String, String> params) throws Exception {

    // Convenience method which automatically checks if tracing is active and sets all headers.
    // Headers can also be transported in custom named fields or as long manually.
    SpanSupport.addTraceHeadersIfTracing(Type.EXIT, params);

    try (Socket clientSocket = new Socket("localhost", 8888)) {
      try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
          ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {
        oos.writeObject(params);
        return (Map<String, String>) ois.readObject();
      }
    }
  }

}
