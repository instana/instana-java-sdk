# Instana Java SDK &nbsp; [![Build Status](https://travis-ci.org/instana/instana-java-sdk.svg?branch=master)](https://travis-ci.org/instana/instana-java-sdk)

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
      <version>1.0.0</version>
    </dependency>

By design, the SDK will remain inactive when no Instana agent is monitoring the
JVM process. It will also return to inactivity when the Instana agent is
stopped. Therefore it is safe to keep it in your application code even when
deploying to systems not yet monitored by Instana.

The whole SDK contained in this repository is provided with an MIT License
(see `LICENSE.md`), to allow any use and conflict with strict open source
licensing requirements.
