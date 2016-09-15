package com.instana.sdk.support;

import java.util.Map;
import java.util.concurrent.Callable;

import com.instana.sdk.annotation.Span;

/**
 * This support class allows for recording additional information when recording a {@link Span}.
 */
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
   */
  public static final long VOID_ID = 0L;

  /**
   * @return Indicates if a span for the current type is currently recorded. If the Instana agent is not active, this
   *         method always returns {@code false}.
   * @param type
   *          span type.
   */
  public static boolean isTracing(Span.Type type) {
    return false;
  }

  /**
   * @return The current trace ID for the currently recorded {@link Span}. If no span is currently recorded, {@code 0}
   *         is returned.
   * @param type
   *          span type.
   */
  public static long currentTraceId(Span.Type type) {
    return VOID_ID;
  }

  /**
   * @return The current span ID for the currently recorded {@link Span}. If no span is currently recorded, {@code 0} is
   *         returned.
   * @param type
   *          span type.
   */
  public static long currentSpanId(Span.Type type) {
    return VOID_ID;
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span. If no span is currently recorded or
   * if the Instana agent is not active, no annotation is written.
   * 
   * @param type
   *          span type.
   * @param key
   *          a String key. Uniqueness is enforced in backend.
   * @param value
   *          a String value.
   */
  public static void annotate(Span.Type type, String key, String value) {
    /* empty */
  }

  /**
   * Writes an annotation as a {@code key}-{@code value} pair to the current span. If no span is currently recorded or
   * if the Instana agent is not active, no annotation is written. This method evaluates the {@code value} lazily and
   * only if a span is currently recorded. Using this method is mainly intended for being used with a lambda expression
   * as the last argument if Java 8 is available.
   * 
   * @param type
   *          span type.
   * @param key
   *          a String key. Uniqueness is enforced in backend.
   * @param value
   *          a Callable, which resolves to a String value, which is invoked if span is recorded.
   */
  public static void annotate(Span.Type type, String key, Callable<? extends String> value) {
    if (isTracing(type)) {
      try {
        annotate(type, key, value.call());
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
   * request from another entity that has the supplied {@code traceId}. The trace id must not be {@code 0}.
   * 
   * @param traceId
   *          to be inherited by next entry.
   * @deprecated use two argument {@link #inheritNext(long, long)} instead to build a correct parent child relationship.
   *             Will be removed in SDK release 1.1.
   */
  @Deprecated
  public static void inheritNext(long traceId) {
    inheritNext(traceId, traceId);
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
   */
  public static void inheritNext(long traceId, long spanId) {
    /* empty */
  }

  /**
   * Clears an open span, i.e. ends a currently open span without committing it.
   *
   * @param type
   *          The type of span to clear.
   */
  public static void clearCurrent(Span.Type type) {
    /* empty */
  }

  /**
   * @return Represents a trace or span id in a string format that can be send over the wire. This format is recognized
   *         by Instana's built-in instrumentations.
   * @param id
   *          a long id.
   */
  public static String idAsString(long id) {
    return Long.toHexString(id);
  }

  /**
   * @return Decodes a trace or span id that was represented in a string format that can be send over the wire. This
   *         format is used by Instana's built-in instrumentations.
   * @param stringId
   *          a String id.
   */
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
   * Adds all trace headers to the map. All values are rendered as {@link String}.
   * 
   * @param type
   *          span type.
   * @param map
   *          a map which accepts Strings as key value pair.
   */
  public static void addTraceHeadersIfTracing(Span.Type type, Map<? super String, ? super String> map) {
    if (isTracing(type)) {
      map.put(TRACE_ID, idAsString(currentTraceId(type)));
      map.put(SPAN_ID, idAsString(currentSpanId(type)));
      map.put(LEVEL, "1");
    }
  }
}
