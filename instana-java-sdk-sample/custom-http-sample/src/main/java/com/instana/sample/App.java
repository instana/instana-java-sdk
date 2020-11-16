package com.instana.sample;

import com.instana.sample.http.custom.CustomHttpFramework;
import com.instana.sample.http.jetty.JettyServer;
import com.instana.sample.http.jetty.NameServlet;
import com.instana.sample.http.jetty.RootServlet;

public class App {

  public static void main(String[] args) throws Exception {
    JettyServer.run(8080, "/", RootServlet.class);
    JettyServer.run(8082, "/name", NameServlet.class);

    CustomHttpFramework framework = new CustomHttpFramework();
    framework.register("/greeting", new GreetingHandler());
    framework.runOnPort(8081);
  }
}
