package com.acme;

import com.acme.batch.CustomBatchRunner;
import com.acme.resources.CallCustomTCPResource;
import com.acme.resources.HelloWorldResource;
import com.acme.tcp.server.CustomTCPServer;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class SampleAppApplication extends Application<SampleAppConfiguration> {

  public static void main(final String[] args) throws Exception {
    new SampleAppApplication().run(args);
  }

  @Override
  public void run(final SampleAppConfiguration configuration, final Environment environment) {
    environment.jersey().register(new HelloWorldResource());
    environment.jersey().register(new CallCustomTCPResource());

    // lets pretend there is some kind of unsupported batch job run by this JVM.
    new CustomBatchRunner().start();

    new CustomTCPServer().start();
  }

}
