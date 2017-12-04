/*
 * Copyright 2014 William Seemann
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anp.commons.parserlib.parser.m3u8;

import com.anp.commons.parserlib.exception.JPlaylistParserException;
import com.anp.commons.parserlib.mime.MediaType;
import com.anp.commons.parserlib.parser.AbstractParser;
import com.anp.commons.data.entities.ChannelsList;
import com.anp.commons.data.entities.Channel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Set;
import org.xml.sax.SAXException;

public class M3U8PlaylistParser extends AbstractParser {
  public final static String EXTENSION = ".m3u8";

  private final static String EXTENDED_INFO_TAG = "#EXTM3U";
  private final static String INFO_TAG = "^[#][E|e][X|x][T|t][-][X|x][-].*";
  private final static String RECORD_TAG = "^[#][E|e][X|x][T|t][I|i][N|n][F|f].*";

  private final static String PROTOCOL = "^[H|h][T|t][T|t][P|p].*";

  private static int mNumberOfFiles = 0;
  private boolean processingEntry = false;

  private static final Set<MediaType> SUPPORTED_TYPES =
      Collections.singleton(MediaType.audio("x-mpegurl"));

  public Set<MediaType> getSupportedTypes() {
    return SUPPORTED_TYPES;
  }

  /**
   * Retrieves the files listed in a .m3u file
   *
   * @throws IOException
   * @throws JPlaylistParserException
   */
  private void parsePlaylist(String uri, InputStream stream, ChannelsList channelsList)
      throws IOException, JPlaylistParserException {
    String line = null;
    BufferedReader reader = null;
    Channel channel = null;

    String host = getHost(uri);

    // Start the query
    reader = new BufferedReader(new InputStreamReader(stream));

    while ((line = reader.readLine()) != null) {
      if (!(line.equalsIgnoreCase(EXTENDED_INFO_TAG) || line.matches(INFO_TAG) || line.trim()
          .equals(""))) {
        if (line.matches(RECORD_TAG)) {
          channel = new Channel();
          channel.setTitleChannel(line.replaceAll("^(.*?),", ""));
          parseAttributtes(line, channel);
          processingEntry = true;
        } else {
          if (!processingEntry) {
            channel = new Channel();
          }

          if (channel != null) {
            channel.setUrlStream(generateUri(line.trim(), host));
          }
          savePlaylistFile(channel, channelsList);
        }
      }
    }
  }

  private void savePlaylistFile(Channel channel, ChannelsList channelsList) {
    mNumberOfFiles = mNumberOfFiles + 1;
    channel.setTrack(String.valueOf(mNumberOfFiles));
    processingEntry = false;
    channelsList.add(channel);
  }

  private String getHost(String uri) throws JPlaylistParserException {
    try {
      URI host = new URI(uri);

      String path;

      if ((path = host.getPath()) == null || path.trim().equals("")) {
        return uri + '/';
      }

      int index = path.lastIndexOf('/');

      if (index > -1) {
        host = new URI(host.getScheme(), null, host.getHost(), host.getPort(),
            path.substring(0, index + 1), null, null);
      }

      return host.toString();
    } catch (URISyntaxException e) {
      throw new JPlaylistParserException(e.getMessage());
    }
  }

  private String generateUri(String uri, String host) {
    if (uri.matches(PROTOCOL)) {
      return uri;
    }

    return host + uri;
  }

  @Override public void parse(String uri, InputStream stream, ChannelsList channelsList)
      throws IOException, SAXException, JPlaylistParserException {
    parsePlaylist(uri, stream, channelsList);
  }
}