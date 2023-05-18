// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.webviewflutter;

import androidx.annotation.NonNull;

import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.ScrollListenerHostApi;
import io.flutter.plugins.webviewflutter.ScrollListenerWebView.ScrollListener;

/**
 * Host api implementation for {@link ScrollListener}.
 *
 * <p>Handles creating {@link ScrollListener}s that intercommunicate with a paired Dart object.
 */
public class ScrollListenerHostApiImpl implements ScrollListenerHostApi {
  private final InstanceManager instanceManager;
  private final ScrollListenerCreator ScrollListenerCreator;
  private final ScrollListenerFlutterApiImpl flutterApi;

  /**
   * Implementation of {@link ScrollListener} that passes arguments of callback methods to Dart.
   */
  public static class ScrollListenerImpl implements ScrollListener {
    private final ScrollListenerFlutterApiImpl flutterApi;

    /**
     * Creates a {@link ScrollListenerImpl} that passes arguments of callbacks methods to Dart.
     *
     * @param flutterApi handles sending messages to Dart
     */
    public ScrollListenerImpl(@NonNull ScrollListenerFlutterApiImpl flutterApi) {
      this.flutterApi = flutterApi;
    }

    @Override
    public void onScrollOffsetChange(double offset) {
      flutterApi.onScrollOffsetChange(
              this, offset, reply -> {});
    }
  }

  /** Handles creating {@link ScrollListenerImpl}s for a {@link ScrollListenerHostApiImpl}. */
  public static class ScrollListenerCreator {
    /**
     * Creates a {@link ScrollListenerImpl}.
     *
     * @param flutterApi handles sending messages to Dart
     * @return the created {@link ScrollListenerImpl}
     */
    @NonNull
    public ScrollListenerImpl createScrollListener(
        @NonNull ScrollListenerFlutterApiImpl flutterApi) {
      return new ScrollListenerImpl(flutterApi);
    }
  }

  /**
   * Creates a host API that handles creating {@link ScrollListener}s.
   *
   * @param instanceManager maintains instances stored to communicate with Dart objects
   * @param ScrollListenerCreator handles creating {@link ScrollListenerImpl}s
   * @param flutterApi handles sending messages to Dart
   */
  public ScrollListenerHostApiImpl(
      @NonNull InstanceManager instanceManager,
      @NonNull ScrollListenerCreator ScrollListenerCreator,
      @NonNull ScrollListenerFlutterApiImpl flutterApi) {
    this.instanceManager = instanceManager;
    this.ScrollListenerCreator = ScrollListenerCreator;
    this.flutterApi = flutterApi;
  }

  @Override
  public void create(@NonNull Long instanceId) {
    final ScrollListener ScrollListener =
        ScrollListenerCreator.createScrollListener(flutterApi);
    instanceManager.addDartCreatedInstance(ScrollListener, instanceId);
  }
}
