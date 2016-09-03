# Changelog

## Version 1.0.1
* Deprecated
`com.instana.sdk.support.SpanSupport.inheritNext(long)`
which has unclear semantics. Should a span join an existing
trace, `traceId` and the parent `spanId` should be supplied.
* Added documentation for Trace REST Endpoint

## Version 1.0.0
* Initial release of the Instana Java Trace SDK.
