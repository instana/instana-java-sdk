package com.instana.sdk.support;

import java.util.Map;
import java.util.concurrent.Callable;

import com.instana.sdk.annotation.Span;

/**
 * This support class allows for recording additional information when recording a {@link Span}.
 */
@SuppressWarnings("unused")
public class SpanSupport {

  private SpanSupport() {
    throw new UnsupportedOperationException();
  }

  /**
   * The header name used by Instana to send a trace id.
   */
  public static final String TRACE_ID = "X-INSTANA-T";

  /**
   * The header name used by Instana to send a span id.
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
   * A trace id of value {@code 0} that indicates an inactive span.
   *
   * @deprecated inactive spans are returned as null Strings
   */
  @Deprecated
  public static final long VOID_ID = 0L;

  /**
   * @return Indicates if a span is currently recorded. If the Instana agent is not active, this
   *         method always returns {@code false}.
   * @since 1.2.0
   */
  public static boolean isTracing() {
    return false;
  }

  /**
   * @return Indicates if a span for the type {@code type} is currently recorded.
   *         If the Instana agent is not active, this method always returns {@code false}.
   * @param type
   *          span type.
   * @deprecated Use {@link SpanSupport#isTracing(Span.Type, String)}.
   */
  @Deprecated
  public static boolean isTracing(Span.Type type) {
    return isTracing(type, null);
  }

  /**
   * @return Indicates if a span for the type {@code type} with name {@code name} is currently recorded.
   *         If the Instana agent is not active, this method always returns {@code false}.
   * @param type
   *          span type.
   * @param name
   *          span name.
   */
  public static boolean isTracing(Span.Type type, String name) {
    return false;
  }

  /**
   * @return The trace ID for the currently recorded {@link Span} of type {@code type}.
   *         If no span is currently recorded, {@code 0} is returned.
   * @param type
   *          span type.
   * @deprecated Use {@link SpanSupport#traceId(Span.Type, String)}.
   */
  @Deprecated
  public static long currentTraceId(Span.Type type) {
    return currentTraceId(type, null);
  }

  /**
   * @return The trace ID for the currently recorded {@link Span} of type {@code type} with name {@code name}.
   *         If no span is currently recorded, {@code 0} is returned.
   * @param type
   *          span type.
   * @param name
   *          span name.
   * @deprecated Use {@link SpanSupport#traceId(Span.Type, String)}.
   */
  @Deprecated
  public static long currentTraceId(Span.Type type, String name) {
    return VOID_ID;
  }

  /**
   * @return The trace ID of the currently recorded {@link Span}.
   *         If no span is currently recorded, {@code null} is returned.
   * @since 1.2.0
   */
  public static String traceId() {
    return null;
  }

  /**
   * @return The trace ID of the currently recorded {@link Span} of type {@code type} with name {@code name}.
   *         If no span is currently recorded, {@code null} is returned.
   * @param type
   *          span type.
   * @param name
   *          span name.
   */
  public static String traceId(Span.Type type, String name) {
    return null;
  }

  /**
   * @return The span ID of the currently recorded {@link Span} of type {@code type}.
   *         If no span is currently recorded, {@code 0} is returned.
   * @param type
   *          span type.
   * @deprecated Use {@link SpanSupport#currentSpanId(Span.Type, String)}.
   */
  @Deprecated
  public static long currentSpanId(Span.Type type) {
    return currentSpanId(type, null);
  }

  /**
   * @return The span ID of the currently recorded {@link Span} of type {@code type} with name {@code name}.
   *         If no span is currently recorded, {@code 0} is returned.
   * @param type
   *          span type.
   * @param name
   *          span name.
   * @deprecated Use {@link SpanSupport#spanId(Span.Type, String)}.
   */
  @Deprecated
  public static long currentSpanId(Span.Type type, String name) {
    return VOID_ID;
  }

  /**
   * @return The span ID for the currently recorded {@link Span}.
   *         If no span is currently recorded, {@code null} is returned.
   * @since 1.2.0
   */
  public static String spanId() {
    return null;
  }

  /**
   * @return The span ID for the currently recorded {@link Span} of type {@code type} and name {@code name}.
   *         If no span is currently recorded, {@code null} is returned.
   * @param type
   *          span type.
   * @param name
   *          span name.
   */
  public static String spanId(Span.Type type, String name) {
    return null;
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written.
   *
   * @param key
   *          a String key. Uniqueness is enforced in backend.
   * @param value
   *          a String value.
   * @since 1.2.0
   */
  public static void annotate(String key, String value) {
    /* empty */
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span. If no span is currently recorded or
   * if the Instana agent is not active, no annotation is written. This method evaluates the {@code value} lazily and
   * only if a span is currently recorded. Using this method is mainly intended for being used with a lambda expression
   * as the last argument if Java 8 is available.
   *
   * @param key
   *          a String key. Uniqueness is enforced in backend.
   * @param value
   *          a Callable, which resolves to a String value, which is invoked if span is recorded.
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
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written.
   *
   * @param type
   *          span type.
   * @param key
   *          a String key. Uniqueness is enforced in backend.
   * @param value
   *          a String value.
   * @deprecated Use {@link SpanSupport#annotate(Span.Type, String, String, String)}.
   */
  @Deprecated
  public static void annotate(Span.Type type, String key, String value) {
    annotate(type, null, key, value);
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span of type {@code type} and name {@code name}.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written.
   *
   * @param type
   *          span type.
   * @param name
   *          span name.
   * @param key
   *          a String key. Uniqueness is enforced in backend.
   * @param value
   *          a String value.
   */
  public static void annotate(Span.Type type, String name, String key, String value) {
    /* empty */
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span of type {@code type}.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written. This method
   * evaluates the {@code value} lazily and only if a span is currently recorded. Using this method is mainly intended
   * for being used with a lambda expression as the last argument if Java 8 is available.
   *
   * @param type
   *          span type.
   * @param key
   *          a String key. Uniqueness is enforced in backend.
   * @param value
   *          a Callable, which resolves to a String value, which is invoked if span is recorded.
   * @deprecated Use {@link SpanSupport#annotate(Span.Type, String, String, Callable)}.
   */
  @Deprecated
  public static void annotate(Span.Type type, String key, Callable<? extends String> value) {
    annotate(type, null, key, value);
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span of type {@code type} with name {@code name}.
   * If no span is currently recorded or if the Instana agent is not active, no annotation is written. This method
   * evaluates the {@code value} lazily and only if a span is currently recorded. Using this method is mainly intended
   * for being used with a lambda expression as the last argument if Java 8 is available.
   *
   * @param type
   *          span type.
   * @param name
   *          span name.
   * @param key
   *          a String key. Uniqueness is enforced in backend.
   * @param value
   *          a Callable, which resolves to a String value, which is invoked if span is recorded.
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
   * Indicates to Instana that the subsequent {@link Span.Type#ENTRY} span should always be traced after receiving a
   * request from another entity that has the supplied {@code traceId} and {@code spanId}. The trace id must not be
   * {@code 0}.
   *
   * @param traceId
   *          to be inherited by next entry.
   * @param spanId
   *          to be set as parent by next entry.
   * @deprecated Use {@link SpanSupport#inheritNext(String, String)}.
   */
  @Deprecated
  public static void inheritNext(long traceId, long spanId) {
    /* empty */
  }

  /**
   * Indicates to Instana that the subsequent {@link Span.Type#ENTRY} span should always be traced after receiving a
   * request from another entity that has the supplied {@code traceId} and {@code spanId}. The trace id and span id must
   * not be {@code null}.
   *
   * @param traceId
   *          to be inherited by next entry.
   * @param spanId
   *          to be set as parent by next entry.
   */
  public static void inheritNext(String traceId, String spanId) {
    /* empty */
  }

  /**
   * Clears the currently open span, i.e. ends the currently open span without committing it.
   *
   * @since 1.2.0
   */
  public static void clearCurrent() {
    /* empty */
  }

  /**
   * Clears an open span of type {@code type}, i.e. ends a currently open span without committing it.
   *
   * @param type
   *          The type of span to clear.
   * @deprecated Use {@link SpanSupport#clearCurrent(Span.Type, String)}.
   */
  @Deprecated
  public static void clearCurrent(Span.Type type) {
    /* empty */
  }

  /**
   * Clears an open span of type {@code type} with name {@code name},
   * i.e. ends a currently open span without committing it.
   *
   * @param type
   *          The type of span to clear.
   * @param name
   *          span name.
   */
  public static void clearCurrent(Span.Type type, String name) {
    /* empty */
  }

  /**
   * @return Represents a trace or span id in a string format that can be send over the wire. This format is recognized
   *         by Instana's built-in instrumentations.
   * @param id
   *          a long id.
   * @deprecated always use string ids without long conversion. trace ids might not fit long.
   */
  @Deprecated
  public static String idAsString(long id) {
    return Long.toHexString(id);
  }

  /**
   * @return Decodes a trace or span id that was represented in a string format that can be send over the wire. This
   *         format is used by Instana's built-in instrumentations.
   * @param stringId
   *          a String id.
   * @deprecated always use string ids without long conversion. trace ids might not fit long.
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
   * Adds all trace headers of the currently open span to the given map. All values are rendered as {@link String}.
   *
   * @param map
   *          a map which accepts Strings as key value pair.
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
   * Adds all trace headers of the span of type {@code type} to the given map. All values are rendered as {@link String}.
   *
   * @param type
   *          span type.
   * @param map
   *          a map which accepts Strings as key value pair.
   * @deprecated Use {@link SpanSupport#addTraceHeadersIfTracing(Span.Type, String, Map)}.
   */
  @Deprecated
  public static void addTraceHeadersIfTracing(Span.Type type, Map<? super String, ? super String> map) {
    addTraceHeadersIfTracing(type, null, map);
  }

  /**
   * Adds all trace headers of the span of type {@code type} with name {@code name} to the given map.
   * All values are rendered as {@link String}.
   *
   * @param type
   *          span type.
   * @param name
   *          span name.
   * @param map
   *          a map which accepts Strings as key value pair.
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
   *
   * The values of the {@link SpanSupport#TRACE_ID} and {@link SpanSupport#SPAN_ID} keys are passed
   * to the {@link #inheritNext(String, String)} method.
   *
   * @param map {@code Map} to load tracing headers from
   * @return {@code true} if {@link SpanSupport#TRACE_ID} and {@link SpanSupport#SPAN_ID} are found in the map and the
   *    trace context is restored; {@code false} if {@code map} is {@code null}, or it does not contain both
   *    {@link SpanSupport#TRACE_ID} and {@link SpanSupport#SPAN_ID} as keys.
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
