/*
 * (c) Copyright IBM Corp. 2021
 * (c) Copyright Instana Inc.
 */
package com.instana.sample;

import com.instana.sample.http.custom.CustomHttpClient;
import com.instana.sample.http.custom.CustomHttpFramework;
import com.instana.sample.http.jetty.JettyServer;
import com.instana.sample.http.jetty.NameServlet;
import com.instana.sample.http.jetty.RootServlet;

import java.time.Duration;

public class App {
  public static final String HOST = "localhost";
  public static final int PORT = 8081;
  public static final String GREETING = "/greeting";

  public static void main(String[] args) throws Exception {
    CustomHttpFramework framework = new CustomHttpFramework();
    CustomHttpClient customHttpClient = new CustomHttpClient();
    final Thread thread = new Thread(() -> {
      try {
        JettyServer.run(8080, "/", RootServlet.class);
        JettyServer.run(8082, "/name", NameServlet.class);

        framework.register(GREETING, new GreetingHandler());
        framework.runOnPort(PORT);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    thread.start();
    framework.awaitServerStartup();
    Thread.sleep(Duration.ofMinutes(1).toMillis());
    customHttpClient.makeCall(HOST, PORT, GREETING);
  }
}
