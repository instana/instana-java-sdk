package com.acme.batch;

import com.instana.sdk.annotation.Span;
import com.instana.sdk.support.SpanSupport;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CustomBatchJob {

  /*
   * This instrumentation will create traces for each invocation of this "batch job". The Instana default
   * instrumentation will not see it, as no standard framework, like Spring Batch, is used.
   */
  @Span(value = "my-custom-batch", captureArguments = true, captureReturn = true)
  public boolean run(String key) {
    try {
      Random random = new Random();
      Thread.sleep(TimeUnit.SECONDS.toMillis(random.nextInt(4))); // do hard work
      SpanSupport.annotate(Span.Type.INTERMEDIATE, "between-jobs", Long.toString(System.currentTimeMillis()));
      Thread.sleep(TimeUnit.SECONDS.toMillis(random.nextInt(4))); // do hard work
      return true;
    } catch (InterruptedException ignored) {
      return false;
    }
  }
}
