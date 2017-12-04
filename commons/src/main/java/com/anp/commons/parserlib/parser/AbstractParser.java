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

package com.anp.commons.parserlib.parser;

import com.anp.commons.parserlib.exception.JPlaylistParserException;
import com.anp.commons.data.entities.ChannelsList;
import com.anp.commons.data.entities.Channel;
import java.io.IOException;
import org.xml.sax.SAXException;

public abstract class AbstractParser implements Parser {

  private static final String REGEX_DOUBLE_QUOTE = "^\"+|\"+$";
  private static final String DEFAULT_GROUP_NAME = "Default";

  protected void parseEntry(Channel channel, ChannelsList channelsList) {
    try {
      AutoDetectParser parser = new AutoDetectParser(); // Should auto-detect!
      parser.parse(channel.get(Channel.URI), channelsList);
      return;
    } catch (IOException | SAXException | JPlaylistParserException e) {
    }

    channelsList.add(channel);
  }

  //todo: fix parse group title
  protected void parseAttributtes(String line, Channel playListEntry) {
    String[] lineString = line.split(" ");
    for (String attributte : lineString) {
      if (attributte.contains(Channel.LOGO)) {
        String urlLogo = attributte.replaceAll(Channel.LOGO, "")
            .replaceAll(Channel.EQUAL, "")
            .replaceAll("^\"|\"$", "");

        playListEntry.setLogo(urlLogo.trim());
      }

      if (attributte.contains(Channel.ATTR_ID) || attributte.contains(Channel.ATTR_TVG_ID)) {
        String id = attributte.replaceAll(Channel.ATTR_TVG_ID, "")
            .replaceAll(Channel.ATTR_ID, "")
            .replaceAll(Channel.EQUAL, "")
            .replaceAll("^\"|\"$", "");
        playListEntry.setId(id);
      }

      if (attributte.contains(Channel.GROUP)) {
        String group = attributte.replaceAll(Channel.GROUP, "")
            .replaceAll(Channel.EQUAL, "")
            .replaceAll("^\"+|\"+$", "");

        String[] splitGroupName = group.split(",");
        if (splitGroupName.length > 0) {
          String groupName = splitGroupName[0].replaceAll("\"", "");
          playListEntry.setGroup(groupName);
        } else {
          playListEntry.setGroup(DEFAULT_GROUP_NAME);
        }
      }
    }

    if (playListEntry.getId() == null || playListEntry.getId().isEmpty()) {
      playListEntry.setId(playListEntry.getTitleChannel());
    }
  }
}
