/*
 * (c) Copyright IBM Corp. 2026
 * (c) Copyright Instana Inc.
 */
package com.instana.sample.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Instana Java SDK - Custom Intermediate Span Sample",
        version = "1.2.0",
        description = "Sample application demonstrating custom intermediate span creation using Instana Java SDK",
        contact = @Contact(
            name = "IBM Instana Java Trace SDK",
            url = "https://www.ibm.com/docs/en/instana-observability/1.0.311?topic=references-java-trace-sdk"
        )
    )
)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
