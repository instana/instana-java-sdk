package com.instana.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.Function;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.instana.sdk.annotation.Span;
import com.instana.sdk.support.SpanSupport;

public class GreetingHandler implements Function<Map<String, String>, String> {

  private final String SPAN_NAME = "my-custom-http-server";

  @Override
  public String apply(Map<String, String> headers) {
    correlateTracing(headers);
    return doApply(headers);
  }

  @Span(type = Span.Type.ENTRY, value = SPAN_NAME)
  private String doApply(Map<String, String> headers) {
    System.out.println("Current span ID: " + Long.toHexString(SpanSupport.currentSpanId(Span.Type.ENTRY)));
    SpanSupport.annotate(Span.Type.ENTRY, SPAN_NAME, "tags.http.url", "http://localhost:8080/greeting");
    SpanSupport.annotate(Span.Type.ENTRY, SPAN_NAME, "tags.http.method", "GET");
    SpanSupport.annotate(Span.Type.ENTRY, SPAN_NAME, "tags.http.status_code", "200");
    SpanSupport.annotate(Span.Type.ENTRY, SPAN_NAME, "tags.error", "true");
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      HttpGet request = new HttpGet("http://localhost:8082/name");
      CloseableHttpResponse response = httpClient.execute(request);
      BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      return "Hello, " + reader.readLine() + "!";
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void correlateTracing(Map<String, String> request) {
    String level = request.remove(SpanSupport.LEVEL);
    String traceId = request.remove(SpanSupport.TRACE_ID);
    String parentSpanId = request.remove(SpanSupport.SPAN_ID);

    if (SpanSupport.SUPPRESS.equals(level)) {
      SpanSupport.suppressNext();
    } else if (traceId != null && parentSpanId != null) {
      SpanSupport.inheritNext(traceId, parentSpanId);
    }
  }
}
