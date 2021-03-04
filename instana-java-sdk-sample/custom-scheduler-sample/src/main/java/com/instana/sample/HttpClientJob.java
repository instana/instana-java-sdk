/*
 * (c) Copyright IBM Corp. 2021
 * (c) Copyright Instana Inc.
 */
package com.instana.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.instana.sdk.annotation.Span;
import com.instana.sdk.support.SpanSupport;

public class HttpClientJob implements Runnable {

  private final String SPAN_NAME = "my-custom-scheduler";

  @Span(type = Span.Type.ENTRY, value = SPAN_NAME)
  public void run() {
    SpanSupport.annotate(Span.Type.ENTRY, SPAN_NAME, "tags.batch.job", "hello world job");
    SpanSupport.annotate(Span.Type.ENTRY, SPAN_NAME, "tags.error", "true");
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpUriRequest request = new HttpGet("http://localhost:8080/");
    try (CloseableHttpResponse response = httpClient.execute(request)) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      System.out.println("Received " + response.getStatusLine() + " with body " + reader.readLine());
    } catch (Exception e) {
      System.err.println("Could not call " + request.getURI() + ". Error: " + e.getMessage());
    }
  }
}
