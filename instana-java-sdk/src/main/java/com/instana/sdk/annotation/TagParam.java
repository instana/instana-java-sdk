package com.instana.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * This annotation enables you to declare that the value of a method parameter it marks must be stored as a tag on
 * the currently active span.
 * <p>
 * For example, consider the following parameter annotation:
 * </p>
 * <pre><code>
 * public void getVeryImportantData(@TagParam("my.awesome.tag") Object importantParameter) {
 * ...
 * }
 * </code></pre>
 * <p>
 * The value of the new tag {@code my.awesome.tag} will be the string representation of the parameter passed to the
 * invocation of the method annotated with this annotation. More details on how the tag value is build are stated below.
 * You can then search this tag and its value in Instana using the {@code call.tag} filter in Unbounded Analytics.
 * </p>
 * <p>
 * The value of the resulting tag is build according to the following rules:
 * </p>
 * <p>
 * The value of the new tag will be the result of the {@code #toString()} method invoked on the annotated parameter.
 * Primitive types will be converted to a String. Arrays will be formatted by invoking {@code Arrays.toString()} on them.
 * </p>
 * <p>
 * If the value of the annotated parameter is {@code null}, the value of the resulting tag will be set to the empty
 * string, so that you can still search and group for it in Unbounded Analytics.
 * </p>
 * <p>
 * If the type of the annotated parameter is {@link java.util.Optional}, and the method
 * {@link java.util.Optional#isPresent()} invoked on the passed in value returns {@code true}, the value of the
 * resulting tag will be the string representation of the result of {@link java.util.Optional#get()}.
 * If {@link java.util.Optional#isPresent()} returns {@code false}, the tag will not be added to the currently active span.
 * </p>
 * <p>
 * If the method with the annotated parameter is invoked multiple times over the lifetime of the currently active span,
 * only the parameter of the last call will be used to create the tag. Likewise for recursive calls, only the parameter
 * of the outermost one will be used.
 * </p>
 * <p>
 * If the name of the resulting tag is empty it will not be created. Also if multiple parameters are annotated with the
 * same tag name only the first one will be created. If a return value and a parameter are annotated with the same tag
 * name only the return value annotation will be considered.
 * </p>
 * <p>
 * If, when the method is invoked, there is no currently active span, the annotation will be ignored.
 * For example, this may occur because the annotation is on a path of the code that is not traced, which mostly
 * applies to code executed on bootstrap by your application, rather than to serve a request.
 * </p>
 *
 * @since 1.2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface TagParam {

  /**
   * @return The name of the tag to be created on the currently active span, e.g., {@code my.awesome.tag}.
   * You can then search this tag and its value in Instana using the {@code call.tag} filter in
   * Unbounded Analytics.
   */
  String value();

}
