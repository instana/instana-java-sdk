package com.acme.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.acme.tcp.server.CustomTCPClient;

/*
 * This class shows how to build custom instrumentation based on an auto discovered Entry and a custom exit.
 */
@Path("/customTCP")
@Produces(MediaType.APPLICATION_JSON)
public class CallCustomTCPResource {

  @GET
  public String callTCP() throws Exception {
    Thread.sleep(1000); // sleeping makes the resulting trace nicer

    HashMap<String, String> tcpParams = new HashMap<String, String>();
    tcpParams.put("my-action", "load-customer");
    Map<String, String> callServer = CustomTCPClient.callServer(tcpParams);

    Thread.sleep(1000); // sleeping makes the resulting trace nicer

    return "I called my TCP service, and took several naps. Server responded with " + callServer.toString();
  }

}
