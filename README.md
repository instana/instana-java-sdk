# Instana Java Trace SDK &nbsp; [![Build Status](https://travis-ci.org/instana/instana-java-sdk.svg?branch=master)](https://travis-ci.org/instana/instana-java-sdk)

Instana automatically instruments well-known frameworks for calls coming into a
monitored JVM, which we call `Entry` (also known as Server in OpenTracing), and
calls leaving a monitored JVM, which we call `Exit` (also known as Client). This
feature is called [Instana AutoTrace](https://www.instana.com/docs/tracing/instana-autotrace).
Usually calls pass instrumented code twice: at the start of a request, and at
the end. The time elapsed between those two passings is the duration this call
took.
This duration, along with additional specific metadata is collected in so called
`Span`s.

For the case where Instana does not (yet) know how to instrument a framework,
or for monitoring the requests of a very custom application, this SDK can be
used.

There is little code needed to enhance monitoring of a custom application.
The most common use cases are:

* Adding a new Entry
* Adding a new Exit
* Collecting additional Metadata

Each of those use cases is demonstrated in `instana-java-sdk-sample`, which
contains a Dropwizard based web application, which uses additional custom code.
The two classes required are `com.instana.sdk.annotation.Span` and
`com.instana.sdk.support.SpanSupport` which contain Javadoc explaining their
usage.

To enable the SDK in your application, you should add a dependency to the SDK
and make sure you package and deploy the SDK jar with your application.

When using maven you can add it with:

```
<dependency>
  <groupId>com.instana</groupId>
  <artifactId>instana-java-sdk</artifactId>
  <version>1.2.0</version>
</dependency>
```

To avoid any unwanted interference by scanning for the SDK annotations, the
agent requires the following configuration to whitelist packages for SDK usage:

```
com.instana.plugin.javatrace:
  instrumentation:
    sdk:
      packages:
        - 'com.acme'
```

By design, the SDK will remain inactive when no Instana agent is monitoring the
JVM process. It will also return to inactivity when the Instana agent is
stopped. Therefore, it is safe to keep it in your application code even when
deploying to systems not yet monitored by Instana.

The whole SDK contained in this repository is provided with an MIT License
(see `LICENSE.md`), to allow any use and conflict with strict open source
licensing requirements.

# Instana Trace Webservice

In some cases, for example using languages without an SDK or for applications that cannot be changed,
another way of integration is needed. Using the [Trace SDK Web Service](https://www.instana.com/docs/api/agent/#trace-sdk-web-service),
it is possible to integrate Instana into any application written in any language.
Each running Instana Agent can be used to feed in custom traces, which can be part of automatically
captured traces or completely separated.

A detailed description about the [Trace SDK Web Service](https://www.instana.com/docs/api/agent/#trace-sdk-web-service)
can be found in the [documentation](https://www.instana.com/docs/api/agent/#trace-sdk-web-service).

## Limitations

Adhere to the following rate limits for the trace webservice:

* Maximum API calls/sec: 20
* Maximum payload per POST request: A span must not exceed 4 KiB. The request size must not exceed 4 MiB.
* Maximum batch size (spans/array): 1000

## FAQ

### Are there any recommendations on how to create spans and annotations?
On [Best practices for custom tracing](https://www.instana.com/docs/tracing/custom-best-practices/)
you can find recommended practices and guidance on how to annotate spans to change their semantics.

### Is there a limit for the amount of calls from Agent to Backend?
Data transmission between Instana agent and Instana backend depends on a lot of factors. It is done using a persistent HTTP2 connection and highly optimized.

### What is the optimal package size (split size) to send 50,000 spans with a size about 40 MiB over the Agent to Backend?
Recommended strategy is to buffer spans for up to one second or until 500 spans were collected, then transmit the spans to the agent. An implementation of this transmission strategy can be seen here:

https://github.com/instana/nodejs-sensor/blob/6ec0d469006ad52f4d5fde7218b163e05bf5b2ad/src/tracing/transmission.js

### Which requirements are recommended for best Agent performance (cpu, ram, etc.)?
This depends on the host that should be monitored, e.g. the number of Docker containers per host etc.

### How should the agent environment be configured?
See https://docs.instana.io/quick_start/agent_configuration/

# Copyright header / checks

Every source file contains a copyright header. If you are creating new files you can add those by running the command: `mvn validate license:format`.
Furthermore, a git hook check the headers in the `pre-commit` phase.
