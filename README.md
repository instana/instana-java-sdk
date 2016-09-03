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

    <dependency>
      <groupId>com.instana</groupId>
      <artifactId>instana-java-sdk</artifactId>
      <version>1.0.1</version>
    </dependency>

By design, the SDK will remain inactive when no Instana agent is monitoring the
JVM process. It will also return to inactivity when the Instana agent is
stopped. Therefore it is safe to keep it in your application code even when
deploying to systems not yet monitored by Instana.

The whole SDK contained in this repository is provided with an MIT License
(see `LICENSE.md`), to allow any use and conflict with strict open source
licensing requirements.

# Instana Trace Webservice

There is an additional option to generate traces. The Instana Agent offers a
WebService/Rest endpoint, which accepts a JSON description of a `Span`. The most
common use case for that is to generate Spans in unmonitored or very custom
components. In fact this endpoint allows to submit trace data for any
programming language manually. Of course we still work towards a fully automatic
and optimized instrumentation.

The endpoint listens on any running agent at
`http://localhost:42699/com.instana.plugin.generic.trace` and accepts the
following JSON:

```
{
  'spanId': <64 bit long>,
  'parentId': <64 bit long>,
  'traceId': <64 bit long>,
  'timestamp': <64 bit long>,
  'duration': <64 bit long>,
  'name' : <string>,
  'type' : <string>,
  'data' : {
    <string> : <string>
  },
  'agent' : <string>
}
```

`spanId` is a unique identifier for the span. `parentId` and `traceId` are optional and
refer to the hierarchy of spans. `timestamp` and `duration` are in milliseconds.
`name` can be any string which is used to visualize and group traces and can
contain any text, but it is recommended to keep it simple.
`type` is optional, but when given needs to be either `ENTRY`, `EXIT` or
`INTERMEDIATE`. `data` is optional and can contain arbitrary key-value pairs.
Behaviour of supplying duplicate keys is unspecified. `agent` is used in the
user interface to indicate what trace agent was used to generate the span and
potentially to access trace agent internal fields. When unset it will use `ws`.
Other trace agents provided by Instana already are `java`, `php` and `node`.
The field is optional, and only documented for completeness.

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
