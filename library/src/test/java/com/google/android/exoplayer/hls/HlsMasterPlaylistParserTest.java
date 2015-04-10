/*
 * Copyright (C) 2014 The Android Open Source Project
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
package com.google.android.exoplayer.hls;

import com.google.android.exoplayer.C;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Test for {@link HlsMasterPlaylistParserTest}
 */
public class HlsMasterPlaylistParserTest extends TestCase {

  public void testParseMasterPlaylist() {
    String playlistUrl = "https://example.com/test.m3u8";
    String playlistString = "#EXTM3U\n"
        + "\n"
        + "#EXT-X-STREAM-INF:BANDWIDTH=1280000,CODECS=\"mp4a.40.2,avc1.66.30\",RESOLUTION=304x128\n"
        + "http://example.com/low.m3u8\n"
        + "\n"
        + "#EXT-X-STREAM-INF:BANDWIDTH=1280000,CODECS=\"mp4a.40.2 , avc1.66.30 \"\n"
        + "http://example.com/spaces_in_codecs.m3u8\n"
        + "\n"
        + "#EXT-X-STREAM-INF:BANDWIDTH=2560000,RESOLUTION=384x160\n"
        + "http://example.com/mid.m3u8\n"
        + "\n"
        + "#EXT-X-STREAM-INF:BANDWIDTH=7680000\n"
        + "http://example.com/hi.m3u8\n"
        + "\n"
        + "#EXT-X-STREAM-INF:BANDWIDTH=65000,CODECS=\"mp4a.40.5\"\n"
        + "http://example.com/audio-only.m3u8";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(
        playlistString.getBytes(Charset.forName(C.UTF8_NAME)));
    try {
      HlsPlaylist playlist = new HlsPlaylistParser().parse(playlistUrl, inputStream);
      assertNotNull(playlist);
      assertEquals(HlsPlaylist.TYPE_MASTER, playlist.type);

      HlsMasterPlaylist masterPlaylist = (HlsMasterPlaylist) playlist;

      List<Variant> variants = masterPlaylist.variants;
      assertNotNull(variants);
      assertEquals(5, variants.size());

      assertEquals(0, variants.get(0).index);
      assertEquals(1280000, variants.get(0).bandwidth);
      assertNotNull(variants.get(0).codecs);
      assertEquals(2, variants.get(0).codecs.length);
      assertEquals("mp4a.40.2", variants.get(0).codecs[0]);
      assertEquals("avc1.66.30", variants.get(0).codecs[1]);
      assertEquals(304, variants.get(0).width);
      assertEquals(128, variants.get(0).height);
      assertEquals("http://example.com/low.m3u8", variants.get(0).url);

      assertEquals(1, variants.get(1).index);
      assertEquals(1280000, variants.get(1).bandwidth);
      assertNotNull(variants.get(1).codecs);
      assertEquals(2, variants.get(1).codecs.length);
      assertEquals("mp4a.40.2", variants.get(1).codecs[0]);
      assertEquals("avc1.66.30", variants.get(1).codecs[1]);
      assertEquals("http://example.com/spaces_in_codecs.m3u8", variants.get(1).url);

      assertEquals(2, variants.get(2).index);
      assertEquals(2560000, variants.get(2).bandwidth);
      assertEquals(null, variants.get(2).codecs);
      assertEquals(384, variants.get(2).width);
      assertEquals(160, variants.get(2).height);
      assertEquals("http://example.com/mid.m3u8", variants.get(2).url);

      assertEquals(3, variants.get(3).index);
      assertEquals(7680000, variants.get(3).bandwidth);
      assertEquals(null, variants.get(3).codecs);
      assertEquals(-1, variants.get(3).width);
      assertEquals(-1, variants.get(3).height);
      assertEquals("http://example.com/hi.m3u8", variants.get(3).url);

      assertEquals(4, variants.get(4).index);
      assertEquals(65000, variants.get(4).bandwidth);
      assertNotNull(variants.get(4).codecs);
      assertEquals(1, variants.get(4).codecs.length);
      assertEquals("mp4a.40.5", variants.get(4).codecs[0]);
      assertEquals(-1, variants.get(4).width);
      assertEquals(-1, variants.get(4).height);
      assertEquals("http://example.com/audio-only.m3u8", variants.get(4).url);
    } catch (IOException exception) {
      fail(exception.getMessage());
    }
  }

}