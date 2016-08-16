package com.acme.tcp.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.instana.sdk.annotation.Span;
import com.instana.sdk.annotation.Span.Type;
import com.instana.sdk.support.SpanSupport;

public class CustomTCPServer extends Thread {

  @Override
  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      while (true) {
        Socket connectionSocket = serverSocket.accept();
        Map<String, String> response;
        try (ObjectInputStream ois = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(connectionSocket.getOutputStream())) {
          Map<String, String> request = (Map<String, String>) ois.readObject();
          correlateTracing(request);
          response = runRequest(request);

          Thread.sleep(1000); // sleeping makes the resulting trace nicer

          oos.writeObject(response);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void correlateTracing(Map<String, String> request) {
    // it is best practice to clean up the headers so a program with agent receives the same values as a program
    // without agent would.
    String level = request.remove(SpanSupport.LEVEL);
    String trace = request.remove(SpanSupport.TRACE_ID);
    String span = request.remove(SpanSupport.SPAN_ID);

    // note that the headers need to be evaluated before the actual "Entry" span is created.
    if (SpanSupport.SUPPRESS.equals(level)) {
      SpanSupport.suppressNext();
    } else {
      if (trace != null && span != null) {
        SpanSupport.inheritNext(SpanSupport.stringAsId(trace), SpanSupport.stringAsId(span));
      }
    }
  }

  @Span(type = Type.ENTRY, value = "custom-tcp-server", captureArguments = true)
  private Map<String, String> runRequest(Map<String, String> request) throws InterruptedException {
    Thread.sleep(1000); // sleeping makes the resulting trace nicer
    HashMap<String, String> result = new HashMap<String, String>();
    result.put("nap", "yes");
    return result;
  }

}
