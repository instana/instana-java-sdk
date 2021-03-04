/*
 * (c) Copyright IBM Corp. 2021
 * (c) Copyright Instana Inc.
 */

package com.instana.sample;

import com.instana.sample.http.jetty.HelloWorldServlet;
import com.instana.sample.http.jetty.JettyServer;
import com.instana.sample.scheduler.CustomScheduler;

import java.util.concurrent.TimeUnit;

public class App {

  public static void main(String[] args) throws Exception {
    JettyServer.run(8080, "/", HelloWorldServlet.class);

    CustomScheduler scheduler = new CustomScheduler();
    scheduler.schedule(new HttpClientJob(), 4, TimeUnit.SECONDS);
  }
}
