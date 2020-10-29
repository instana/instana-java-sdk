package com.instana.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Declaring this annotation on any method triggers a recording of this method's runtime and some basic properties.
 * Traces of identical type cannot be nested where only the inner-most method's runtime is recorded.
 * </p>
 * <p>
 * If the recording should end upon reaching a different method, the {@link Start} and {@link End} annotations should be
 * used instead. It is required to include this annotation in the final application, but running the application without
 * an active Instana agent will not affect the functionality of the annotated method.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Span {

  /**
   * @return An unique identifier of the captured span which is used for grouping spans and is used as a marker in the
   *         GUI.
   */
  String value();

  /**
   * @return Indicates the type of the span that this method implements. A span can either capture an exit, where this
   *         process currently communicates with an external resource such as a database, or an entry, which typically
   *         describes a scenario where this VM process a command from an external application.
   */
  Type type() default Type.INTERMEDIATE;

  /**
   * @return Indicates the number of stack frames that should be saved upon entering this method. A negative number
   *         indicates that no stack frames are captured (this is the default and improves performance). A value of 0
   *         does not capture any stacktrace. It is recommended that an Entry should capture 1 stack frame, or anyhow
   *         few (less than 10).
   */
  int capturedStackFrames() default -1;

  /**
   * @return Indicates that the arguments of this method should be captured upon entering it. Doing so, the
   *         {@link Object#toString()} method is invoked on all arguments. If any such invocation triggers an exception,
   *         the arguments for this methods are not stored. It is the responsibility of the user to assure that these
   *         methods do not imply any side-effects to the application.
   * @deprecated use annotation {@link TagParam}, which gives you full control over the names of the tags to be created
   */
  @Deprecated
  boolean captureArguments() default false;

  /**
   * @return Indicates that the return value of this method should be captured when it is exited. Doing so, the
   *         {@link Object#toString()} method is invoked on the return value. If this invocation triggers an exception,
   *         the return value is not stored. It is the responsibility of the user to assure that the invocation does not
   *         imply any unwanted side-effect to the application.
   * @deprecated use annotation {@link TagReturn}, which gives you full control over the name of the tag to be created
   */
  @Deprecated
  boolean captureReturn() default false;

  /**
   * <p>
   * Declaring this annotation on any method triggers a recording of this method's runtime and some basic properties.
   * The tracing is continued until a method declaring an {@link End} annotation is reached. Traces of identical type
   * cannot be nested where only the inner-most method's runtime is recorded.
   * </p>
   * <p>
   * If the recording should when completing this method, the {@link Span} annotation should be used instead. It is
   * required to include this annotation in the final application, but running the application without an active Instana
   * agent will not affect the functionality of the annotated method.
   * </p>
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  @interface Start {

    /**
     * @return An unique identifier of the captured span which is used for grouping spans and is used as a marker in the
     *         GUI.
     */
    String value();

    /**
     * @return Indicates the type of the span that this method implements. A span can either capture an exit where this
     *         process currently communicates with an external resource such as a database. An entry typically describes
     *         a scenario where this VM process a command from an external application.
     */
    Type type() default Type.INTERMEDIATE;

    /**
     * @return Indicates the number of stack frames that should be saved upon entering this method. A negative number
     *         indicates that no stack frames are captured (this is the default and improves performance). A value of 0
     *         does not capture any stacktrace. It is recommended that an Entry should capture 1 stack frame, or anyhow
     *         few (less than 10).
     */
    int capturedStackFrames() default -1;

    /**
     * @return Indicates that the arguments of this method should be captured upon entering it. Doing so, the
     *         {@link Object#toString()} method is invoked on all arguments. If any such invocation triggers an
     *         exception, the arguments for this methods are not stored. It is the responsibility of the user to assure
     *         that these methods do not imply any side-effects to the application.
     */
    boolean captureArguments() default false;
  }

  /**
   * Indicates the end of a method recording that was initiated by {@link Start}. In order to close a span, this exit
   * needs to declare both the same unique identifier and {@link Type} as the related exit.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  @interface End {

    /**
     * @return An unique identifier of the captured span which is used for grouping spans and is used as a marker in the
     *         GUI.
     */
    String value();

    /**
     * @return Indicates that the return value of this method should be captured when it is exited. Doing so, the
     *         {@link Object#toString()} method is invoked on the return value. If this invocation triggers an
     *         exception, the return value is not stored. It is the responsibility of the user to assure that the
     *         invocation does not imply any unwanted side-effect to the application.
     */
    boolean captureReturn() default false;
  }

  /**
   * Indication of a trace type.
   */
  enum Type {

    /**
     * An entry span is typically opened when an application processes a request that was received by another entity.
     */
    ENTRY,

    /**
     * An intermediate span is typically opened to mark a business transaction that might contain multiple exits.
     */
    INTERMEDIATE,

    /**
     * An exit span is typically opened when an application sends a request to another entity.
     */
    EXIT
  }
}
