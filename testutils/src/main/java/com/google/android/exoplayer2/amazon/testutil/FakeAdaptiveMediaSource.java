/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.exoplayer2.amazon.testutil;

import android.os.Handler;
import com.google.android.exoplayer2.amazon.Timeline;
import com.google.android.exoplayer2.amazon.Timeline.Period;
import com.google.android.exoplayer2.amazon.source.MediaSource;
import com.google.android.exoplayer2.amazon.source.MediaSourceEventListener;
import com.google.android.exoplayer2.amazon.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.amazon.source.TrackGroupArray;
import com.google.android.exoplayer2.amazon.upstream.Allocator;

/**
 * Fake {@link MediaSource} that provides a given timeline. Creating the period returns a
 * {@link FakeAdaptiveMediaPeriod} from the given {@link TrackGroupArray}.
 */
public class FakeAdaptiveMediaSource extends FakeMediaSource {

  private final FakeChunkSource.Factory chunkSourceFactory;

  public FakeAdaptiveMediaSource(
      Timeline timeline,
      Object manifest,
      TrackGroupArray trackGroupArray,
      Handler eventHandler,
      MediaSourceEventListener eventListener,
      FakeChunkSource.Factory chunkSourceFactory) {
    super(timeline, manifest, trackGroupArray);
    this.chunkSourceFactory = chunkSourceFactory;
    addEventListener(eventHandler, eventListener);
  }

  @Override
  protected FakeMediaPeriod createFakeMediaPeriod(
      MediaPeriodId id,
      TrackGroupArray trackGroupArray,
      Allocator allocator,
      EventDispatcher eventDispatcher) {
    Period period = timeline.getPeriod(id.periodIndex, new Period());
    return new FakeAdaptiveMediaPeriod(
        trackGroupArray, eventDispatcher, allocator, chunkSourceFactory, period.durationUs);
  }

}
