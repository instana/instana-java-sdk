/*
 * (c) Copyright IBM Corp. 2021
 * (c) Copyright Instana Inc.
 */
package com.instana.sample.http.jetty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RootServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String greeting;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpUriRequest greetingRequest = new HttpGet("http://localhost:8081/greeting");
    try (CloseableHttpResponse greetingResponse = httpClient.execute(greetingRequest)) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(greetingResponse.getEntity().getContent()));
      greeting = reader.readLine();
    }
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("text/plain");
    try (PrintWriter responseWriter = response.getWriter()) {
      responseWriter.println(greeting);
    }
  }
}
