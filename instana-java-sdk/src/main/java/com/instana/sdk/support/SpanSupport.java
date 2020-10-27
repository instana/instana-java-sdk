package com.instana.sdk.support;

import java.util.Map;
import java.util.concurrent.Callable;

import com.instana.sdk.annotation.Span;

/**
 * This support class allows for specifying additional information when recording a {@link Span}.
 * <p>
 * Most methods work on spans created by the SDK and spans created by the automatic trace instrumentation,
 * known as Instana AutoTrace. Details on which kind of spans can be created or manipulated are explained
 * in the method documentation.
 */
@SuppressWarnings("unused")
public class SpanSupport {

  private SpanSupport() {
    throw new UnsupportedOperationException();
  }

  /**
   * The header name used by Instana to send a trace ID.
   */
  public static final String TRACE_ID = "X-INSTANA-T";

  /**
   * The header name used by Instana to send a span ID.
   */
  public static final String SPAN_ID = "X-INSTANA-S";

  /**
   * The header name used by Instana to level information. The level can be set to {@code 0} to express that a
   * {@code Span} should not be collected when receiving a request.
   */
  public static final String LEVEL = "X-INSTANA-L";

  /**
   * Indicates that a {@link Span} should not be collected.
   *
   * @see SpanSupport#LEVEL
   */
  public static final String SUPPRESS = "0";

  /**
   * A trace ID of value {@code 0} that indicates an inactive span.
   *
   * @deprecated inactive spans are returned as null Strings
   */
  @Deprecated
  public static final long VOID_ID = 0L;

  /**
   * Indicates if a span, regardless of its {@code type} or {@code name}, is currently recorded.
   * This can be an SDK or AutoTrace span. If the Instana agent is not active, this method always returns {@code false}.
   *
   * @return {@code true} if a span is currently recorded, otherwise, or if the Instana agent is not active, {@code false}
   * @since 1.2.0
   */
  public static boolean isTracing() {
    return false;
  }

  /**
   * Indicates if a span of type {@code type} is currently recorded.
   * This can be an SDK or AutoTrace span. If the Instana agent is not active, this method always returns {@code false}.
   * Furthermore, the {@code type} has to be {@link Span.Type#ENTRY} or {@link Span.Type#EXIT}.
   *
   * @param type span type
   * @return {@code true} if a span is currently recorded, otherwise, or if the Instana agent is not active, {@code false}
   * @deprecated Use {@link SpanSupport#isTracing(Span.Type, String)}.
   */
  @Deprecated
  public static boolean isTracing(Span.Type type) {
    return isTracing(type, null);
  }

  /**
   * Indicates if a span of type {@code type} with name {@code name} is currently recorded.
   * This can be an SDK or AutoTrace span. {@code name} is only taken into account for SDK spans of type {@link Span.Type#INTERMEDIATE}.
   * If the Instana agent is not active, this method always returns {@code false}.
   *
   * @param type span type
   * @param name span name
   * @return {@code true} if a span is currently recorded, otherwise, or if the Instana agent is not active, {@code false}
   */
  public static boolean isTracing(Span.Type type, String name) {
    return false;
  }

  /**
   * Provides the trace ID of the currently recorded span of type {@code type}.
   * This can be an SDK or AutoTrace span. If no span is currently recorded, or if the Instana agent is not active,
   * {@code 0} is returned. Furthermore, the {@code type} has to be {@link Span.Type#ENTRY} or {@link Span.Type#EXIT}.
   *
   * @param type span type
   * @return trace ID of the currently recorded span of type {@code type}, or {code 0} if no span is recorded or the
   * Instana agent is not active
   * @deprecated Use {@link SpanSupport#traceId(Span.Type, String)}.
   */
  @Deprecated
  public static long currentTraceId(Span.Type type) {
    return currentTraceId(type, null);
  }

  /**
   * Provides the trace ID of the currently recorded span of type {@code type} with name {@code name}.
   * This can be an SDK or AutoTrace span. {@code name} is only taken into account for SDK spans of type {@link Span.Type#INTERMEDIATE}.
   * If no span is currently recorded, or if the Instana agent is not active, {@code 0} is returned.
   *
   * @param type span type
   * @param name span name
   * @return trace ID for the currently recorded span of type {@code type} with name {@code name},
   * or {code 0} if no span is recorded or the Instana agent is not active
   * @deprecated Use {@link SpanSupport#traceId(Span.Type, String)}.
   */
  @Deprecated
  public static long currentTraceId(Span.Type type, String name) {
    return VOID_ID;
  }

  /**
   * Provides the trace ID of the currently recorded span, regardless of its {@code type} or {@code name}.
   * This can be an SDK or AutoTrace span. If no span is currently recorded, or if the Instana agent is not active,
   * {@code null} is returned.
   *
   * @return trace ID of the currently recorded span,
   * or {@code null} if no span is recorded or the Instana agent is not active
   * @since 1.2.0
   */
  public static String traceId() {
    return null;
  }

  /**
   * Provides the trace ID of the currently recorded span of type {@code type} with name {@code name}.
   * This can be an SDK or AutoTrace span. {@code name} is only taken into account for SDK spans of type {@link Span.Type#INTERMEDIATE}.
   * If no span is currently recorded, or if the Instana agent is not active, {@code null} is returned.
   *
   * @param type span type
   * @param name span name
   * @return trace ID of the currently recorded span of type {@code type} with name {@code name},
   * or {code null} if no span is recorded or the Instana agent is not active
   */
  public static String traceId(Span.Type type, String name) {
    return null;
  }

  /**
   * Provides the span ID of the currently recorded span of type {@code type}.
   * This can be an SDK or AutoTrace span. If no span is currently recorded, or if the Instana agent is not active,
   * {@code 0} is returned. Furthermore, the {@code type} has to be {@link Span.Type#ENTRY} or {@link Span.Type#EXIT}.
   *
   * @param type span type
   * @return span ID of the currently recorded span of type {@code type}, or {code 0} if no span is recorded or the
   * Instana agent is not active
   * @deprecated Use {@link SpanSupport#currentSpanId(Span.Type, String)}.
   */
  @Deprecated
  public static long currentSpanId(Span.Type type) {
    return currentSpanId(type, null);
  }

  /**
   * Provides the span ID of the currently recorded span of type {@code type} with name {@code name}.
   * This can be an SDK or AutoTrace span. {@code name} is only taken into account for SDK spans of type {@link Span.Type#INTERMEDIATE}.
   * If no span is currently recorded, or if the Instana agent is not active, {@code 0} is returned.
   *
   * @param type span type
   * @param name span name
   * @return span ID for the currently recorded span of type {@code type} with name {@code name},
   * or {code 0} if no span is recorded or the Instana agent is not active
   * @deprecated Use {@link SpanSupport#spanId(Span.Type, String)}.
   */
  @Deprecated
  public static long currentSpanId(Span.Type type, String name) {
    return VOID_ID;
  }

  /**
   * Provides the span ID of the currently recorded span, regardless of its {@code type} or {@code name}.
   * This can be an SDK or AutoTrace span. If no span is currently recorded, or if the Instana agent is not active,
   * {@code null} is returned.
   *
   * @return span ID of the currently recorded span,
   * or {@code null} if no span is recorded or the Instana agent is not active
   * @since 1.2.0
   */
  public static String spanId() {
    return null;
  }

  /**
   * Provides the span ID of the currently recorded span of type {@code type} with name {@code name}.
   * This can be an SDK or AutoTrace span. {@code name} is only taken into account for SDK spans of type {@link Span.Type#INTERMEDIATE}.
   * If no span is currently recorded, or if the Instana agent is not active, {@code null} is returned.
   *
   * @param type span type
   * @param name span name
   * @return span ID of the currently recorded span of type {@code type} with name {@code name},
   * or {code null} if no span is recorded or the Instana agent is not active
   */
  public static String spanId(Span.Type type, String name) {
    return null;
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span. This can be an SDK or AutoTrace span.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written.
   *
   * @param key   a String key. Uniqueness is enforced in backend
   * @param value a String value
   * @since 1.2.0
   */
  public static void annotate(String key, String value) {
    /* empty */
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span. This can be an SDK or AutoTrace span.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written.
   * This method evaluates the {@code value} lazily and only if a span is currently recorded. Using this method is
   * mainly intended for being used with a lambda expression as the last argument if Java 8 is available.
   *
   * @param key   a String key. Uniqueness is enforced in the backend
   * @param value a Callable, which resolves to a String value, which is invoked if span is recorded
   * @since 1.2.0
   */
  public static void annotate(String key, Callable<? extends String> value) {
    if (isTracing()) {
      try {
        annotate(key, value.call());
      } catch (Exception ignored) {
      }
    }
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span of type {@code type}.
   * This can be an SDK or AutoTrace span. Furthermore, the {@code type} has to be {@link Span.Type#ENTRY} or {@link Span.Type#EXIT}.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written.
   *
   * @param type  span type
   * @param key   a String key. Uniqueness is enforced in backend
   * @param value a String value
   * @deprecated Use {@link SpanSupport#annotate(Span.Type, String, String, String)}.
   */
  @Deprecated
  public static void annotate(Span.Type type, String key, String value) {
    annotate(type, null, key, value);
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span of type {@code type} and name {@code name}.
   * This can be an SDK or AutoTrace span. {@code name} is only taken into account for SDK spans of type {@link Span.Type#INTERMEDIATE}.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written.
   *
   * @param type  span type
   * @param name  span name
   * @param key   a String key. Uniqueness is enforced in backend
   * @param value a String value
   */
  public static void annotate(Span.Type type, String name, String key, String value) {
    /* empty */
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span of type {@code type}.
   * This can be an SDK or AutoTrace span. Furthermore, the {@code type} has to be {@link Span.Type#ENTRY} or {@link Span.Type#EXIT}.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written. This method
   * evaluates the {@code value} lazily and only if a span is currently recorded. Using this method is mainly intended
   * for being used with a lambda expression as the last argument if Java 8 is available.
   *
   * @param type  span type
   * @param key   a String key. Uniqueness is enforced in backend
   * @param value a Callable, which resolves to a String value, which is invoked if span is recorded
   * @deprecated Use {@link SpanSupport#annotate(Span.Type, String, String, Callable)}.
   */
  @Deprecated
  public static void annotate(Span.Type type, String key, Callable<? extends String> value) {
    annotate(type, null, key, value);
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span of type {@code type} with name {@code name}.
   * This can be an SDK or AutoTrace span. {@code name} is only taken into account for SDK spans of type {@link Span.Type#INTERMEDIATE}.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written. This method
   * evaluates the {@code value} lazily and only if a span is currently recorded. Using this method is mainly intended
   * for being used with a lambda expression as the last argument if Java 8 is available.
   *
   * @param type  span type
   * @param name  span name
   * @param key   a String key. Uniqueness is enforced in backend
   * @param value a Callable, which resolves to a String value, which is invoked if span is recorded
   */
  public static void annotate(Span.Type type, String name, String key, Callable<? extends String> value) {
    if (isTracing(type, name)) {
      try {
        annotate(type, name, key, value.call());
      } catch (Exception ignored) {
      }
    }
  }

  /**
   * Indicates to Instana that no tracing information should be collected until a subsequent {@link Span.Type#ENTRY}
   * span is exited.
   */
  public static void suppressNext() {
    /* empty */
  }

  /**
   * Indicates to Instana that the subsequent SDK or AutoTrace span should be traced with the given trace and span ID.
   * The next span can be a SDK or AutoTrace span with any type. This method is normally called after receiving a
   * request from another entity that has the supplied {@code traceId} and {@code spanId}. The trace ID must not be
   * {@code 0}.
   *
   * @param traceId to be inherited by next span
   * @param spanId  to be set as parent by next span
   * @deprecated Use {@link SpanSupport#inheritNext(String, String)}
   */
  @Deprecated
  public static void inheritNext(long traceId, long spanId) {
    /* empty */
  }

  /**
   * Indicates to Instana that the subsequent SDK or AutoTrace span should be traced with the given trace and span ID.
   * The next span can be a SDK or AutoTrace span with any type. This method is normally called after receiving a
   * request from another entity that has the supplied {@code traceId} and {@code spanId}. The trace ID and span ID must
   * not be {@code null}.
   *
   * @param traceId to be inherited by next span
   * @param spanId  to be set as parent by next span
   */
  public static void inheritNext(String traceId, String spanId) {
    /* empty */
  }

  /**
   * Clears the currently open SDK span, i.e. ends the currently open span without committing it.
   *
   * @return {@code true} if the currently open span cloud be cleared, otherwise {@code false}
   * @since 1.2.0
   */
  public static boolean clearCurrent() {
    return false;
  }

  /**
   * Clears an open SDK span of type {@code type}, i.e. ends a currently open span without committing it.
   * {@code type} has to be {@link Span.Type#ENTRY} or {@link Span.Type#EXIT}.
   *
   * @param type The type of span to clear
   * @return {@code true} if the open span cloud be cleared, otherwise {@code false}
   */
  public static boolean clearCurrent(Span.Type type) {
    return false;
  }

  /**
   * Clears an open SDK span of type {@code type} with name {@code name},
   * i.e. ends a currently open span without committing it.
   *
   * @param type The type of span to clear
   * @param name span name
   * @return {@code true} if the open span cloud be cleared, otherwise {@code false}
   */
  public static boolean clearCurrent(Span.Type type, String name) {
    return false;
  }

  /**
   * Converts a trace or span ID in a string format that can be send over the wire. This format is recognized
   * by Instana's built-in instrumentations.
   *
   * @param id a long trace or span ID
   * @return trace or span ID
   * @deprecated always use string IDs without long conversion. trace IDs might not fit long
   */
  @Deprecated
  public static String idAsString(long id) {
    return Long.toHexString(id);
  }

  /**
   * Decodes a trace or span ID that was represented in a string format that can be send over the wire. 
   * This format is used by Instana's built-in instrumentations.
   *
   * @param stringId a trace or span ID
   * @return decoded trace or span ID
   * @deprecated always use string IDs without long conversion. trace IDs might not fit long
   */
  @Deprecated
  public static long stringAsId(String stringId) {
    int len = stringId.length();
    if (len <= 12) {
      return Long.parseLong(stringId, 16);
    }
    long first = Long.parseLong(stringId.substring(0, len - 1), 16);
    int second = Character.digit(stringId.charAt(len - 1), 16);
    return first * 16 + second;
  }

  /**
   * Adds the trace headers of the currently open span to the given map.
   * This can be an SDK or AutoTrace span. If tracing is not active the provided map will not be changed.
   * <p>
   * Header keys are represented by {@link SpanSupport#TRACE_ID}, {@link SpanSupport#SPAN_ID} and {@link SpanSupport#LEVEL} and
   * values are rendered as {@link String}
   *
   * @param map a map which accepts Strings as key value pair
   * @since 1.2.0
   */
  public static void addTraceHeadersIfTracing(Map<? super String, ? super String> map) {
    if (isTracing()) {
      map.put(TRACE_ID, traceId());
      map.put(SPAN_ID, spanId());
      map.put(LEVEL, "1");
    }
  }

  /**
   * Adds the trace headers of the span of type {@code type} to the given map.
   * This can be an SDK or AutoTrace span. The {@code type} has to be {@link Span.Type#ENTRY} or {@link Span.Type#EXIT}.
   * If tracing is not active the provided map will not be changed.
   * <p>
   * Header keys are represented by {@link SpanSupport#TRACE_ID}, {@link SpanSupport#SPAN_ID} and {@link SpanSupport#LEVEL} and
   * values are rendered as {@link String}
   *
   * @param type span type
   * @param map  a map which accepts Strings as key value pair
   * @deprecated Use {@link SpanSupport#addTraceHeadersIfTracing(Span.Type, String, Map)}.
   */
  @Deprecated
  public static void addTraceHeadersIfTracing(Span.Type type, Map<? super String, ? super String> map) {
    addTraceHeadersIfTracing(type, null, map);
  }

  /**
   * Adds all trace headers of the span of type {@code type} with name {@code name} to the given map.
   * This can be an SDK or AutoTrace span. {@code name} is only taken into account for SDK spans of type {@link Span.Type#INTERMEDIATE}.
   * If tracing is not active the provided map will not be changed.
   * <p>
   * Header keys are represented by {@link SpanSupport#TRACE_ID}, {@link SpanSupport#SPAN_ID} and {@link SpanSupport#LEVEL} and
   * values are rendered as {@link String}
   *
   * @param type span type
   * @param name span name
   * @param map  a map which accepts Strings as key value pair
   */
  public static void addTraceHeadersIfTracing(Span.Type type, String name, Map<? super String, ? super String> map) {
    if (isTracing(type, name)) {
      map.put(TRACE_ID, traceId(type, name));
      map.put(SPAN_ID, spanId(type, name));
      map.put(LEVEL, "1");
    }
  }

  /**
   * Restores the trace context from the provided {@code map} argument, given that it contains
   * {@link SpanSupport#TRACE_ID} and {@link SpanSupport#SPAN_ID} as keys.
   * <p>
   * The values of the {@link SpanSupport#TRACE_ID} and {@link SpanSupport#SPAN_ID} keys are passed
   * to the {@link #inheritNext(String, String)} method.
   *
   * @param map {@code Map} to load tracing headers from
   * @return {@code true} if {@link SpanSupport#TRACE_ID} and {@link SpanSupport#SPAN_ID} are found in the map and the
   * trace context is restored; {@code false} if {@code map} is {@code null}, or it does not contain both
   * {@link SpanSupport#TRACE_ID} and {@link SpanSupport#SPAN_ID} as keys.
   * @since 1.2.0
   */
  public static boolean continueTraceIfTracing(Map<? super String, ? super String> map) {
    if (map != null) {
      Object traceId = map.get(TRACE_ID);
      Object spanId = map.get(SPAN_ID);
      if (traceId != null && spanId != null) {
        inheritNext(traceId.toString(), spanId.toString());
        return true;
      }
    }
    return false;
  }
}
