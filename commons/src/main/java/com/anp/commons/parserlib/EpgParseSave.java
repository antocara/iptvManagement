package com.anp.commons.parserlib;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.anp.commons.data.EpgRepository;
import com.anp.commons.data.database.tables.ChannelEpgRealm;
import com.anp.commons.data.database.tables.ProgramEpgRealm;
import io.realm.RealmList;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class EpgParseSave {

  private static final String TAG_TV = "tv";
  private static final String TAG_CHANNEL = "channel";
  private static final String TAG_DISPLAY_NAME = "display-name";
  private static final String TAG_ICON = "icon";
  private static final String TAG_APP_LINK = "app-link";
  private static final String TAG_PROGRAM = "programme";
  private static final String TAG_TITLE = "title";
  private static final String TAG_DESC = "desc";
  private static final String TAG_CATEGORY = "category";
  private static final String TAG_RATING = "rating";
  private static final String TAG_VALUE = "value";
  private static final String TAG_DISPLAY_NUMBER = "display-number";
  private static final String TAG_AD = "advertisement";
  private static final String TAG_REQUEST_URL = "request-url";

  private static final String ATTR_ID = "id";
  private static final String ATTR_START = "start";
  private static final String ATTR_STOP = "stop";
  private static final String ATTR_CHANNEL = "channel";
  private static final String ATTR_SYSTEM = "system";
  private static final String ATTR_SRC = "src";
  private static final String ATTR_REPEAT_PROGRAMS = "repeat-programs";
  private static final String ATTR_VIDEO_SRC = "video-src";
  private static final String ATTR_VIDEO_TYPE = "video-type";
  private static final String ATTR_APP_LINK_TEXT = "text";
  private static final String ATTR_APP_LINK_COLOR = "color";
  private static final String ATTR_APP_LINK_POSTER_URI = "poster-uri";
  private static final String ATTR_APP_LINK_INTENT_URI = "intent-uri";
  private static final String ATTR_AD_START = "start";
  private static final String ATTR_AD_STOP = "stop";
  private static final String ATTR_AD_TYPE = "type";

  private static final String VALUE_VIDEO_TYPE_HTTP_PROGRESSIVE = "HTTP_PROGRESSIVE";
  private static final String VALUE_VIDEO_TYPE_HLS = "HLS";
  private static final String VALUE_VIDEO_TYPE_MPEG_DASH = "MPEG_DASH";
  private static final String VALUE_ADVERTISEMENT_TYPE_VAST = "VAST";

  private static final String ANDROID_TV_RATING = "com.android.tv";

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyyMMddHHmmss Z", Locale.US);
  private static final String TAG = "EpgParseSave";

  public EpgParseSave() {

  }

  /**
   * Reads an InputStream and parses the data to identify channels and programs
   *
   * @param inputStream The InputStream of your data
   * @return A TvListing containing your channels and programs
   */
  public void parse(@NonNull InputStream inputStream) throws EpgParseSave.XmlTvParseException {
    parse(inputStream, Xml.newPullParser());
  }

  /**
   * Reads an InputStream and parses the data to identify channels and programs
   *
   * @param inputStream The InputStream of your data
   * @param parser The XmlPullParser the developer selects to parse this data
   * @return A TvListing containing your channels and programs
   */
  private void parse(@NonNull InputStream inputStream, @NonNull XmlPullParser parser)
      throws EpgParseSave.XmlTvParseException {
    try {
      parser.setInput(inputStream, null);
      int eventType = parser.next();
      if (eventType != XmlPullParser.START_TAG || !TAG_TV.equals(parser.getName())) {
        throw new EpgParseSave.XmlTvParseException(
            "Input stream does not contain an XMLTV description");
      }
      parseTvListings(parser);
    } catch (XmlPullParserException | IOException | ParseException e) {
      Log.w(TAG, e.getMessage());
    }
  }

  private void parseTvListings(XmlPullParser parser)
      throws IOException, XmlPullParserException, ParseException {
    EpgRepository repository = new EpgRepository();
    List<ChannelEpgRealm> listTempChannels = new ArrayList<>();
    List<ProgramEpgRealm> listTempPrograms = new ArrayList<>();

    while (parser.next() != XmlPullParser.END_DOCUMENT) {
      if (parser.getEventType() == XmlPullParser.START_TAG && TAG_CHANNEL.equalsIgnoreCase(
          parser.getName())) {
        ChannelEpgRealm channel = parseChannel(parser);

        listTempChannels.add(channel);
        if (listTempChannels.size() > 100) {
          repository.saveListChannelsEpg(listTempChannels);
          listTempChannels.clear();
        }
      }
      if (parser.getEventType() == XmlPullParser.START_TAG && TAG_PROGRAM.equalsIgnoreCase(
          parser.getName())) {
        ProgramEpgRealm program = parseProgram(parser);

        listTempPrograms.add(program);
        if (listTempPrograms.size() > 100) {
          repository.saveListProgramEpg(listTempPrograms);
          listTempPrograms.clear();
        }
      }
    }

    repository.saveListChannelsEpg(listTempChannels);
    listTempChannels.clear();
    repository.saveListProgramEpg(listTempPrograms);
    listTempPrograms.clear();
  }

  private static ChannelEpgRealm parseChannel(XmlPullParser parser)
      throws IOException, XmlPullParserException, ParseException {

    ChannelEpgRealm channelEpgRealm = new ChannelEpgRealm();

    String id = null;
    String displayName = null;
    String displayNumber = null;
    EpgParseSave.XmlTvIcon icon = null;
    EpgParseSave.XmlTvAppLink appLink = null;

    for (int i = 0; i < parser.getAttributeCount(); ++i) {
      String attr = parser.getAttributeName(i);
      String value = parser.getAttributeValue(i);
      if (ATTR_ID.equalsIgnoreCase(attr)) {
        id = value;
      }
    }

    while (parser.next() != XmlPullParser.END_DOCUMENT) {
      if (parser.getEventType() == XmlPullParser.START_TAG) {
        if (TAG_DISPLAY_NAME.equalsIgnoreCase(parser.getName()) && displayName == null) {
          displayName = parser.nextText();
        } else if (TAG_DISPLAY_NUMBER.equalsIgnoreCase(parser.getName()) && displayNumber == null) {
          displayNumber = parser.nextText();
        } else if (TAG_ICON.equalsIgnoreCase(parser.getName()) && icon == null) {
          icon = parseIcon(parser);
        } else if (TAG_APP_LINK.equalsIgnoreCase(parser.getName()) && appLink == null) {
          appLink = parseAppLink(parser);
        }
      } else if (TAG_CHANNEL.equalsIgnoreCase(parser.getName())
          && parser.getEventType() == XmlPullParser.END_TAG) {
        break;
      }
    }
    if (TextUtils.isEmpty(id) || TextUtils.isEmpty(displayName)) {
      throw new IllegalArgumentException("id and display-name can not be null.");
    }

    channelEpgRealm.setOriginalNetworkId(id);
    channelEpgRealm.setDisplayName(displayName);
    channelEpgRealm.setDisplayNumber(displayNumber);
    if (icon != null) {
      channelEpgRealm.setChannelLogo(icon.src);
    }
    if (appLink != null) {
      channelEpgRealm.setAppLinkText(appLink.text);
      channelEpgRealm.setAppLinkColor(appLink.color);
      channelEpgRealm.setAppLinkIconUri(appLink.icon.src);
      channelEpgRealm.setAppLinkPosterArtUri(appLink.posterUri);
      channelEpgRealm.setAppLinkIntentUri(appLink.intentUri);
    }

    return channelEpgRealm;
  }

  private static ProgramEpgRealm parseProgram(XmlPullParser parser)
      throws IOException, XmlPullParserException, ParseException {

    ProgramEpgRealm programEpgRealm = new ProgramEpgRealm();

    String channelId = null;
    Long startTimeUtcMillis = null;
    Long endTimeUtcMillis = null;

    String title = null;
    String description = null;
    EpgParseSave.XmlTvIcon icon = null;
    List<String> category = new ArrayList<>();

    for (int i = 0; i < parser.getAttributeCount(); ++i) {
      String attr = parser.getAttributeName(i);
      String value = parser.getAttributeValue(i);
      if (ATTR_CHANNEL.equalsIgnoreCase(attr)) {
        channelId = value;
      } else if (ATTR_START.equalsIgnoreCase(attr)) {
        startTimeUtcMillis = DATE_FORMAT.parse(value).getTime();
      } else if (ATTR_STOP.equalsIgnoreCase(attr)) {
        endTimeUtcMillis = DATE_FORMAT.parse(value).getTime();
      }
    }

    while (parser.next() != XmlPullParser.END_DOCUMENT) {
      String tagName = parser.getName();
      if (parser.getEventType() == XmlPullParser.START_TAG) {
        if (TAG_TITLE.equalsIgnoreCase(parser.getName())) {
          title = parser.nextText();
        } else if (TAG_DESC.equalsIgnoreCase(tagName)) {
          description = parser.nextText();
        } else if (TAG_ICON.equalsIgnoreCase(tagName)) {
          icon = parseIcon(parser);
        } else if (TAG_CATEGORY.equalsIgnoreCase(tagName)) {
          category.add(parser.nextText());
        }
      } else if (TAG_PROGRAM.equalsIgnoreCase(tagName)
          && parser.getEventType() == XmlPullParser.END_TAG) {
        break;
      }
    }
    if (TextUtils.isEmpty(channelId) || startTimeUtcMillis == null || endTimeUtcMillis == null) {
      throw new IllegalArgumentException("channel, start, and end can not be null.");
    }

    if (endTimeUtcMillis <= startTimeUtcMillis) {
      endTimeUtcMillis = startTimeUtcMillis + 1;
    }

    programEpgRealm.setChannelId(channelId);
    programEpgRealm.setTitle(title);
    programEpgRealm.setDescription(description);
    programEpgRealm.setPosterArtUri(icon != null ? icon.src : null);

    String[] categories = category.toArray(new String[category.size()]);
    RealmList<String> genres = new RealmList<String>(categories);
    programEpgRealm.setCanonicalGenres(genres);
    programEpgRealm.setStartTimeUtcMillis(startTimeUtcMillis);
    programEpgRealm.setEndTimeUtcMillis(endTimeUtcMillis);

    return programEpgRealm;
  }

  private static EpgParseSave.XmlTvIcon parseIcon(XmlPullParser parser)
      throws IOException, XmlPullParserException {

    String src = null;
    for (int i = 0; i < parser.getAttributeCount(); ++i) {
      String attr = parser.getAttributeName(i);
      String value = parser.getAttributeValue(i);
      if (ATTR_SRC.equalsIgnoreCase(attr)) {
        src = value;
      }
    }
    while (parser.next() != XmlPullParser.END_DOCUMENT) {
      if (TAG_ICON.equalsIgnoreCase(parser.getName())
          && parser.getEventType() == XmlPullParser.END_TAG) {
        break;
      }
    }
    if (TextUtils.isEmpty(src)) {
      throw new IllegalArgumentException("Icon src cannot be null.");
    }
    return new EpgParseSave.XmlTvIcon(src);
  }

  private static EpgParseSave.XmlTvAppLink parseAppLink(XmlPullParser parser)
      throws IOException, XmlPullParserException {
    String text = null;
    Integer color = null;
    String posterUri = null;
    String intentUri = null;
    for (int i = 0; i < parser.getAttributeCount(); ++i) {
      String attr = parser.getAttributeName(i);
      String value = parser.getAttributeValue(i);
      if (ATTR_APP_LINK_TEXT.equalsIgnoreCase(attr)) {
        text = value;
      } else if (ATTR_APP_LINK_COLOR.equalsIgnoreCase(attr)) {
        color = Color.parseColor(value);
      } else if (ATTR_APP_LINK_POSTER_URI.equalsIgnoreCase(attr)) {
        posterUri = value;
      } else if (ATTR_APP_LINK_INTENT_URI.equalsIgnoreCase(attr)) {
        intentUri = value;
      }
    }

    EpgParseSave.XmlTvIcon icon = null;
    while (parser.next() != XmlPullParser.END_DOCUMENT) {
      if (parser.getEventType() == XmlPullParser.START_TAG && TAG_ICON.equalsIgnoreCase(
          parser.getName()) && icon == null) {
        icon = parseIcon(parser);
      } else if (TAG_APP_LINK.equalsIgnoreCase(parser.getName())
          && parser.getEventType() == XmlPullParser.END_TAG) {
        break;
      }
    }

    return new EpgParseSave.XmlTvAppLink(text, color, posterUri, intentUri, icon);
  }

  private static class XmlTvIcon {
    public final String src;

    private XmlTvIcon(String src) {
      this.src = src;
    }
  }

  private static class XmlTvAppLink {
    public final String text;
    public final Integer color;
    public final String posterUri;
    public final String intentUri;
    public final EpgParseSave.XmlTvIcon icon;

    public XmlTvAppLink(String text, Integer color, String posterUri, String intentUri,
        EpgParseSave.XmlTvIcon icon) {
      this.text = text;
      this.color = color;
      this.posterUri = posterUri;
      this.intentUri = intentUri;
      this.icon = icon;
    }
  }

  /**
   * An exception that indicates the provided XMLTV file is invalid or improperly formatted.
   */
  public class XmlTvParseException extends Exception {
    public XmlTvParseException(String msg) {
      super(msg);
    }
  }
}
