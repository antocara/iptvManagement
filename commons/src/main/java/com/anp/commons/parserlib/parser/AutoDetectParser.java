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

import android.util.Log;
import com.anp.commons.helpers.HelperFiles;
import com.anp.commons.parserlib.exception.JPlaylistParserException;
import com.anp.commons.parserlib.mime.MediaType;
import com.anp.commons.parserlib.parser.asx.ASXPlaylistParser;
import com.anp.commons.parserlib.parser.m3u.M3UPlaylistParser;
import com.anp.commons.parserlib.parser.m3u8.M3U8PlaylistParser;
import com.anp.commons.parserlib.parser.pls.PLSPlaylistParser;
import com.anp.commons.parserlib.parser.xspf.XSPFPlaylistParser;
import com.anp.commons.data.entities.ChannelsList;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import org.xml.sax.SAXException;

public class AutoDetectParser {

  public void parse(String uri, String mimeType, InputStream stream, ChannelsList channelsList)
      throws IOException, SAXException, JPlaylistParserException {
    Parser parser = null;
    String extension = null;

    if (uri == null) {
      throw new IllegalArgumentException("URI cannot be NULL");
    }

    if (stream == null) {
      throw new IllegalArgumentException("stream cannot be NULL");
    }

    if (mimeType == null) {
      mimeType = "";
    }

    if (mimeType.split("\\;").length > 0) {
      mimeType = mimeType.split("\\;")[0];
    }

    ASXPlaylistParser asxPlaylistParser = new ASXPlaylistParser();
    M3UPlaylistParser m3uPlaylistParser = new M3UPlaylistParser();
    M3U8PlaylistParser m3u8PlaylistParser = new M3U8PlaylistParser();
    PLSPlaylistParser plsPlaylistParser = new PLSPlaylistParser();
    XSPFPlaylistParser xspfPlaylistParser = new XSPFPlaylistParser();

    extension = HelperFiles.INSTANCE.getFileExtension(uri);

    if (extension.equalsIgnoreCase(ASXPlaylistParser.EXTENSION)
        || asxPlaylistParser.getSupportedTypes().contains(MediaType.parse(mimeType))) {
      parser = asxPlaylistParser;
    } else if (extension.equalsIgnoreCase(M3UPlaylistParser.EXTENSION)
        || (m3uPlaylistParser.getSupportedTypes().contains(MediaType.parse(mimeType))
        && !extension.equalsIgnoreCase(M3U8PlaylistParser.EXTENSION))) {
      parser = m3uPlaylistParser;
    } else if (extension.equalsIgnoreCase(M3U8PlaylistParser.EXTENSION)
        || m3uPlaylistParser.getSupportedTypes().contains(MediaType.parse(mimeType))) {
      parser = m3u8PlaylistParser;
    } else if (extension.equalsIgnoreCase(PLSPlaylistParser.EXTENSION)
        || plsPlaylistParser.getSupportedTypes().contains(MediaType.parse(mimeType))) {
      parser = plsPlaylistParser;
    } else if (extension.equalsIgnoreCase(XSPFPlaylistParser.EXTENSION)
        || xspfPlaylistParser.getSupportedTypes().contains(MediaType.parse(mimeType))) {
      parser = xspfPlaylistParser;
    } else {
      parser = m3uPlaylistParser;
      //throw new JPlaylistParserException("Unsupported format");
    }

    parser.parse(uri, stream, channelsList);
  }

  public void parse(String uri, ChannelsList channelsList)
      throws IOException, SAXException, JPlaylistParserException {
    Parser parser = null;
    String extension = null;

    if (uri == null) {
      throw new IllegalArgumentException("URI cannot be NULL");
    }

    ASXPlaylistParser asxPlaylistParser = new ASXPlaylistParser();
    M3UPlaylistParser m3uPlaylistParser = new M3UPlaylistParser();
    M3U8PlaylistParser m3u8PlaylistParser = new M3U8PlaylistParser();
    PLSPlaylistParser plsPlaylistParser = new PLSPlaylistParser();
    XSPFPlaylistParser xspfPlaylistParser = new XSPFPlaylistParser();

    extension = HelperFiles.INSTANCE.getFileExtension(uri);

    if (extension.equalsIgnoreCase(ASXPlaylistParser.EXTENSION)) {
      parser = asxPlaylistParser;
    } else if (extension.equalsIgnoreCase(M3UPlaylistParser.EXTENSION)
        && !extension.equalsIgnoreCase(M3U8PlaylistParser.EXTENSION)) {
      parser = m3uPlaylistParser;
    } else if (extension.equalsIgnoreCase(M3U8PlaylistParser.EXTENSION)) {
      parser = m3u8PlaylistParser;
    } else if (extension.equalsIgnoreCase(PLSPlaylistParser.EXTENSION)) {
      parser = plsPlaylistParser;
    } else if (extension.equalsIgnoreCase(XSPFPlaylistParser.EXTENSION)) {
      parser = xspfPlaylistParser;
    } else {
      throw new JPlaylistParserException("Unsupported format");
    }

    URL url;
    HttpURLConnection conn = null;
    InputStream is = null;

    try {
      url = new URL(URLDecoder.decode(uri, "UTF-8"));
      conn = (HttpURLConnection) url.openConnection();
      conn.setConnectTimeout(6000);
      conn.setReadTimeout(6000);
      conn.setRequestMethod("GET");

      is = conn.getInputStream();

      parser.parse(url.toString(), is, channelsList);
    } catch (IOException e) {
      Log.d("AutoDetectParser", e.getLocalizedMessage());
    } finally {
      if (conn != null) {
        conn.disconnect();
      }

      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          Log.d("AutoDetectParser", e.getLocalizedMessage());
        }
      }
    }
  }
}