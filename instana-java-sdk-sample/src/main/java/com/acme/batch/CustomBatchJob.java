package com.acme.batch;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.instana.sdk.annotation.Span;
import com.instana.sdk.annotation.TagParam;
import com.instana.sdk.annotation.TagReturn;
import com.instana.sdk.support.SpanSupport;

public class CustomBatchJob {

  /*
   * This instrumentation will create traces for each invocation of this "batch job". The Instana default
   * instrumentation will not see it, as no standard framework, like Spring Batch, is used.
   */
  @Span(type = Span.Type.ENTRY, value = "my-custom-batch")
  @TagReturn("job-result")
  public boolean run(@TagParam("job-input") String key) {
    try {
      Random random = new Random();
      int initialRnd = random.nextInt(4);
      Thread.sleep(TimeUnit.SECONDS.toMillis(initialRnd)); // do hard work

      // Annotate currently active span
      SpanSupport.annotate("initial-rnd", String.valueOf(initialRnd));

      // Annotate using explicit type and name
      SpanSupport.annotate(Span.Type.ENTRY, "my-custom-batch", "between-jobs", String.valueOf(System.currentTimeMillis()));

      // Mark span as batch
      SpanSupport.annotate(Span.Type.ENTRY, "my-custom-batch", "batch.job", "custom-batch-job");

      // Optionally change the call's name
      SpanSupport.annotate(Span.Type.ENTRY, "my-custom-batch", "tags.call.name", "custom-call-name");

      Thread.sleep(TimeUnit.SECONDS.toMillis(random.nextInt(4))); // do hard work
      return true;
    } catch (InterruptedException ignored) {
      return false;
    }
  }
}
