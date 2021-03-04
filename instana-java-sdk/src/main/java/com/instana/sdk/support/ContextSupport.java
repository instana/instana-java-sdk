/*
 * (c) Copyright IBM Corp. 2021
 * (c) Copyright Instana Inc.
 */
package com.instana.sdk.support;

public class ContextSupport {

  /**
   * @return A key that allows to restore the current (thread-local) tracing context in any thread by calling
   *         {@link ContextSupport#restoreSnapshot(Object)}. If the Instana agent is not active, {@code null} is
   *         returned. A key can only be returned a single time. The life-time of a snapshot is bound to the life-time
   *         of the key, i.e. if the key becomes eligible to garbage collection, the snapshot also becomes collectable.
   *         A key can only be used once.
   */
  public static Object takeSnapshot() {
    return null;
  }

  /**
   * Restores the snapshot bound to the supplied key. Keys can be generated by {@link ContextSupport#takeSnapshot()}. If
   * the key is {@code null}, was already used or is any other object, calling this method clears the tracing context.
   * 
   * @param key
   *          a single use key which was obtained by {@link #takeSnapshot()} before.
   */
  public static void restoreSnapshot(Object key) {
    /* empty */
  }
}
