package com.acme.batch;

public class CustomBatchRunner extends Thread {

  @Override
  public void run() {
    while (true) {
      if (!runCustomBatch()) {
        return;
      }
    }
  }

  private Boolean runCustomBatch() {
    CustomBatchJob customBatchJob = new CustomBatchJob();
    System.out.println("Running batch job...");
    return customBatchJob.run(Long.toString(System.currentTimeMillis()));
  }

}
