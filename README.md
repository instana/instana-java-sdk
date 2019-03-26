# Instana Java Trace SDK &nbsp; [![Build Status](https://travis-ci.org/instana/instana-java-sdk.svg?branch=master)](https://travis-ci.org/instana/instana-java-sdk)

Instana automatically instruments well known frameworks for calls coming into a
monitored JVM, which we call `Entry` (also known as Server in OpenTracing), and
calls leaving a monitored JVM, which we call `Exit` (also known as Client).
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
  <version>1.1.0</version>
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
stopped. Therefore it is safe to keep it in your application code even when
deploying to systems not yet monitored by Instana.



The whole SDK contained in this repository is provided with an MIT License
(see `LICENSE.md`), to allow any use and conflict with strict open source
licensing requirements.

# Instana Trace Webservice

Using the Trace SDK REST Web Service, it is possible to integrate Instana into
any application written in any language. Each running Instana Agent can be used
to feed in custom traces, which can be part of automatically captured traces or
completely separated. The Agent offers an endpoint which listens on
`http://localhost:42699/com.instana.plugin.generic.trace` and accepts the
following JSON via a POST request:

```
{
  'spanId': <string>,
  'parentId': <string>,
  'traceId': <string>,
  'timestamp': <64 bit long>,
  'duration': <64 bit long>,
  'name' : <string>,
  'type' : <string>,
  'error' : <boolean>,
  'data' : {
    <string> : <string>
  }
}
```

`spanId` is an unique identifier for the span. Define the root span of a trace
with the same `spanId` and `traceId`; define child spans with a unique `spanId`,
the `traceId` of the root span and the `spanId` of the span immediately
preceding in the hierarchy as parentId. Trace Id, Span Id and Parent Id are
64 bit unique values encoded as hex string like `b0789916ff8f319f`.

```
root (spanId=1, traceID=1)
   child (spanId=2,traceId=1, parentId=1)
      child (spanId=3, traceId=1, parentId=2)
```

`timestamp` and `duration` are in milliseconds.  `timestamp` must be the epoch
timestamp coordinated to UTC. `name` can be any string which is used to
visualize and group traces and can contain any text, but it is recommended to
keep it simple. `type` is optional, but when given needs to be either `ENTRY`,
`EXIT` or `INTERMEDIATE`. `data` is optional and can contain arbitrary
key-value pairs. `error` is optional and can be set to `true` to indicate an
erroneous span. Behaviour of supplying duplicate keys is unspecified.

The endpoint also accepts a batch of spans, which then need to be given as array:
```
[
  {
    // span as above
  },
  {
    // span as above
  }
]
```

## Limitations

Adher to the following rate limits for the trace webservice:

* Maximum API calls/sec: 20
* Maximum payload per POST request: A span must not exceed 4 KiB. The request size must not exceed 4 MiB.
* Maximum batch size (spans/array): 1000

## FAQ

### Is there a limit for the amount of calls from Agent to Backend?
Data transmission between Instana agent and Instana backend depends on a lot of factors. It is done using a persistent HTTP2 connection and highly optimized.

### What is the optimal package size (splitted size) to send 50,000 spans with a size about 40 MiB over the Agent to Backend?
Recommended strategy is to buffer spans for up to one second or until 500 spans were collected, then transmit the spans to the agent. An implementation of this transmission strategy can be seen here:

https://github.com/instana/nodejs-sensor/blob/6ec0d469006ad52f4d5fde7218b163e05bf5b2ad/src/tracing/transmission.js

### Which requirements are recommended for best Agent performance (cpu, ram, etc.)?
This depends on the host that should be monitored, e.g. the number of Docker containers per host etc.

### How should the agent environment be configured?
See https://docs.instana.io/quick_start/agent_configuration/
