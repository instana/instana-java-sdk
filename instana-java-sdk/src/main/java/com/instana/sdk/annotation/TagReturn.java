/*
 * (c) Copyright IBM Corp. 2021
 * (c) Copyright Instana Inc.
 */
package com.instana.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * <p>
 * This annotation enables you to declare that the return value of the method it marks must be stored as a tag
 * on the currently active span. This can be an Instana AutoTrace span or a manually created SDK span.
 * </p>
 * <p>
 * For example, consider the following method annotation:
 * </p>
 * <pre>
 * {@code @TagReturn("my.awesome.tag")}
 * public String getVeryImportantData() {
 *     ...
 * }
 * </pre>
 * <p>
 * The value of the new tag {@code my.awesome.tag} will be the string representation of the return value.
 * More details on how the tag value is build are stated below.
 * You can then search this tag and its value in Instana using the {@code call.tag} filter in Unbounded Analytics.
 * </p>
 * <p>
 * The value of the resulting tag is build according to the following rules:
 * </p>
 * <p>
 * The value of the resulting tag will be the result of the {@code #toString()} method invoked on return value.
 * Primitive types will be converted to a string. Arrays will be formatted by invoking {@link Arrays#toString(Object[])}
 * on them.
 * </p>
 * <p>
 * If the returned value is {@code null}, the tag will not be created and you can verify that in Unbounded Analytics
 * using the {@code is not present} operator in conjunction with the {@code call.tag} filter.
 * </p>
 * <p>
 * If the type of the return value is {@link java.util.Optional}, and the method {@link java.util.Optional#isPresent()}
 * invoked on it returns {@code true}, the value of the resulting tag will be the string representation of the result
 * of {@link java.util.Optional#get()}, as described above.
 * If {@link java.util.Optional#isPresent()} returns {@code false}, the tag will not be added to the currently active span.
 * </p>
 * <p>
 * If the method annotated with the {@code Tag} annotation is invoked multiple times over the lifetime
 * of the currently active span, only the return value of the last call will be recorded. Likewise for recursive calls,
 * only the return value of the outermost one will be used.
 * </p>
 * <p>
 * If the name of the resulting tag is empty it will not be created. If a method and a parameter are annotated
 * with the same tag name only the method level annotation will be considered.
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
@Target({ ElementType.METHOD })
public @interface TagReturn {

  /**
   * @return The name of the tag to be created on the currently active span, e.g., {@code my.awesome.tag}.
   * You can then search this tag and its value in Instana using the {@code call.tag} filter in Unbounded Analytics.
   */
  String value();

}
