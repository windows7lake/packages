// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.webviewflutter;

import android.webkit.DownloadListener;

import androidx.annotation.NonNull;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.ScrollListenerFlutterApi;
import io.flutter.plugins.webviewflutter.ScrollListenerWebView.ScrollListener;

/**
 * Flutter Api implementation for {@link ScrollListener}.
 *
 * <p>Passes arguments of callbacks methods from a {@link ScrollListener} to Dart.
 */
public class ScrollListenerFlutterApiImpl extends ScrollListenerFlutterApi {
  private final InstanceManager instanceManager;

  /**
   * Creates a Flutter api that sends messages to Dart.
   *
   * @param binaryMessenger handles sending messages to Dart
   * @param instanceManager maintains instances stored to communicate with Dart objects
   */
  public ScrollListenerFlutterApiImpl(
      @NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
    super(binaryMessenger);
    this.instanceManager = instanceManager;
  }

  /** Passes arguments from {@link ScrollListener#onScrollOffsetChange} to Dart. */
  public void onScrollOffsetChange(
          @NonNull ScrollListener scrollListener,
          @NonNull double offset,
          @NonNull ScrollListenerFlutterApi.Reply<Void> callback) {
    onScrollOffsetChange(
            getIdentifierForListener(scrollListener),
            offset,
            callback);
  }

  private long getIdentifierForListener(ScrollListener listener) {
    final Long identifier = instanceManager.getIdentifierForStrongReference(listener);
    if (identifier == null) {
      throw new IllegalStateException("Could not find identifier for ScrollListener.");
    }
    return identifier;
  }
}
