package com.acme.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/*
 * This class represents what works by automatic instrumentation through the Instana agent. All dropwizard/jersey
 * resources are automatically monitored whenever they are called. Instana supports a wide array of standard Java
 * frameworks. But it is also possible to create custom entries not (yet) known to Instana.
 */
@Path("/helloWorld")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

  @GET
  public String listPeople() throws InterruptedException {
    Thread.sleep(1000);
    return "Hello World! I had a one second nap";
  }

}
