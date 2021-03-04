/*
 * (c) Copyright IBM Corp. 2021
 * (c) Copyright Instana Inc.
 */
package com.instana.sample.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomScheduler {

  private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

  public void schedule(Runnable job, long interval, TimeUnit timeUnit) {
    executorService.scheduleAtFixedRate(job, 0, interval, timeUnit);
  }
}
