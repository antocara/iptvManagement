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

package com.anp.commons.parserlib.parser.pls;

import com.anp.commons.parserlib.exception.JPlaylistParserException;
import com.anp.commons.parserlib.mime.MediaType;
import com.anp.commons.parserlib.parser.AbstractParser;
import com.anp.commons.data.entities.ChannelsList;
import com.anp.commons.data.entities.Channel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Set;
import org.xml.sax.SAXException;

public class PLSPlaylistParser extends AbstractParser {
  public final static String EXTENSION = ".pls";

  private static int mNumberOfFiles = 0;
  private boolean processingEntry = false;

  private static final Set<MediaType> SUPPORTED_TYPES =
      Collections.singleton(MediaType.audio("x-scpls"));

  public Set<MediaType> getSupportedTypes() {
    return SUPPORTED_TYPES;
  }

  /**
   * Retrieves the files listed in a .pls file
   *
   * @throws IOException
   */
  private void parsePlaylist(InputStream stream, ChannelsList channelsList) throws IOException {
    String line = null;
    BufferedReader reader = null;
    Channel channel = null;

    reader = new BufferedReader(new InputStreamReader(stream));

    channel = new Channel();
    processingEntry = false;

    while ((line = reader.readLine()) != null) {
      if (line.trim().equals("")) {
        if (processingEntry) {
          savePlaylistFile(channel, channelsList);
        }

        channel = new Channel();
        processingEntry = false;
      } else {
        int index = line.indexOf('=');
        String[] parsedLine = new String[0];

        if (index != -1) {
          parsedLine = new String[2];
          parsedLine[0] = line.substring(0, index);
          parsedLine[1] = line.substring(index + 1);
        }

        if (parsedLine.length == 2) {
          if (parsedLine[0].trim().matches("[Ff][Ii][Ll][Ee].*")) {
            processingEntry = true;
            channel.set(Channel.URI, parsedLine[1].trim());
          } else if (parsedLine[0].trim().contains("Title")) {
            channel.set(Channel.PLAYLIST_METADATA, parsedLine[1].trim());
          } else if (parsedLine[0].trim().contains("Length")) {
            if (processingEntry) {
              savePlaylistFile(channel, channelsList);
            }

            channel = new Channel();
            processingEntry = false;
          }
        }
      }
    }

    // added in case the file doesn't follow the standard pls
    // structure:
    // FileX:
    // TitleX:
    // LengthX:
    if (processingEntry) {
      savePlaylistFile(channel, channelsList);
    }
  }

  private void savePlaylistFile(Channel channel, ChannelsList channelsList) {
    mNumberOfFiles = mNumberOfFiles + 1;
    channel.set(Channel.TRACK, String.valueOf(mNumberOfFiles));
    parseEntry(channel, channelsList);
    processingEntry = false;
  }

  @Override public void parse(String uri, InputStream stream, ChannelsList channelsList)
      throws IOException, SAXException, JPlaylistParserException {
    parsePlaylist(stream, channelsList);
  }
}

