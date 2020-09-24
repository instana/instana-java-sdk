# Changelog

## Version 1.2.0
* Add `@TagParam` and `@TagReturn` annotations
* Add overloaded methods to `SpanSupport` to use on currently active span
* Add method `continueTraceIfTracing` to continue a trace with previously extracted trace headers 

## Version 1.1.1
* Improved API for clearing current span.

## Version 1.1.0
* Switch Trace ID and Span ID to be String in preparation for
vendor interop via [TraceContext](https://w3c.github.io/distributed-tracing/report-trace-context.html).

## Version 1.0.3
* Added API for clearing current span.

## Version 1.0.2
* Support intermediate spans.

## Version 1.0.1
* Deprecated
`com.instana.sdk.support.SpanSupport.inheritNext(long)`
which has unclear semantics. Should a span join an existing
trace, `traceId` and the parent `spanId` should be supplied.
* Added documentation for Trace REST Endpoint.

## Version 1.0.0
* Initial release of the Instana Java Trace SDK.
