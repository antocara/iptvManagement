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

package com.anp.commons.data.entities;

import android.os.Parcel;
import android.os.Parcelable;
import com.anp.commons.Constants;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * A multi-valued metadata container.
 */
public class Channel implements Parcelable {


  /**
   * A map of all metadata attributes.
   */
  private Map<String, String[]> metadata = null;

  /**
   * The common delimiter used between the namespace abbreviation and the property name
   */
  public static final String NAMESPACE_PREFIX_DELIMITER = ":";

  @Deprecated public static final String FORMAT = "format";
  @Deprecated public static final String IDENTIFIER = "identifier";
  @Deprecated public static final String MODIFIED = "modified";
  @Deprecated public static final String CONTRIBUTOR = "contributor";
  @Deprecated public static final String COVERAGE = "coverage";
  @Deprecated public static final String DESCRIPTION = "description";
  @Deprecated public static final String LANGUAGE = "language";
  @Deprecated public static final String PUBLISHER = "publisher";
  @Deprecated public static final String RELATION = "relation";
  @Deprecated public static final String RIGHTS = "rights";
  @Deprecated public static final String SOURCE = "source";
  @Deprecated public static final String SUBJECT = "subject";
  @Deprecated public static final String TYPE = "type";
  public static final String TITLE = "title";
  public static final String ATTR_ID = "id";
  public static final String ATTR_TVG_ID = "tvg-id";
  public static final String TRACK = "track";
  public static final String URI = "urlStream";
  public static final String PLAYLIST_METADATA = "playlist_metadata";
  public static final String LOGO = "tvg-logo";
  public static final String GROUP = "group-title";
  public static final String EQUAL = "=";

  private String id;
  private String track;
  private String urlStream;
  private String titleChannel;
  private String logo;
  private String group = Constants.Companion.getDEFAULT_GROUP();
  private String sourceListId;
  private List<ProgramEpg> programEpgList;//fixme

  public Channel(String titleChannel, String group) {
    this.titleChannel = titleChannel;
    this.group = group;
  }

  public Channel(String titleChannel, String group, String urlStream) {
    this.titleChannel = titleChannel;
    this.group = group;
    this.urlStream = urlStream;
  }

  /**
   * Constructs a new, empty playlist entry.
   */
  public Channel() {
    metadata = new HashMap<String, String[]>();
  }

  /**
   * Returns true if named value is multivalued.
   *
   * @param name name of metadata
   * @return true is named value is multivalued, false if single value or null
   */
  public boolean isMultiValued(final String name) {
    return metadata.get(name) != null && metadata.get(name).length > 1;
  }

  /**
   * Returns an array of the names contained in the metadata.
   *
   * @return Metadata names
   */
  public String[] names() {
    return metadata.keySet().toArray(new String[metadata.keySet().size()]);
  }

  /**
   * Get the value associated to a metadata name. If many values are assiociated
   * to the specified name, then the first one is returned.
   *
   * @param name of the metadata.
   * @return the value associated to the specified metadata name.
   */
  public String get(final String name) {
    String[] values = metadata.get(name);
    if (values == null) {
      return null;
    } else {
      return values[0];
    }
  }

  /**
   * Get the values associated to a metadata name.
   *
   * @param name of the metadata.
   * @return the values associated to a metadata name.
   */
  public String[] getValues(final String name) {
    return _getValues(name);
  }

  private String[] _getValues(final String name) {
    String[] values = metadata.get(name);
    if (values == null) {
      values = new String[0];
    }
    return values;
  }

  private String[] appendedValues(String[] values, final String value) {
    String[] newValues = new String[values.length + 1];
    System.arraycopy(values, 0, newValues, 0, values.length);
    newValues[newValues.length - 1] = value;
    return newValues;
  }

  /**
   * Add a metadata name/value mapping. Add the specified value to the list of
   * values associated to the specified metadata name.
   *
   * @param name the metadata name.
   * @param value the metadata value.
   */
  public void add(final String name, final String value) {
    String[] values = metadata.get(name);
    if (values == null) {
      set(name, value);
    } else {
      metadata.put(name, appendedValues(values, value));
    }
  }

  /**
   * Copy All key-value pairs from properties.
   *
   * @param properties properties to copy from
   */
  @SuppressWarnings("unchecked") public void setAll(Properties properties) {
    Enumeration<String> names = (Enumeration<String>) properties.propertyNames();
    while (names.hasMoreElements()) {
      String name = names.nextElement();
      metadata.put(name, new String[] { properties.getProperty(name) });
    }
  }

  /**
   * Set metadata name/value. Associate the specified value to the specified
   * metadata name. If some previous values were associated to this name, they
   * are removed.
   *
   * @param name the metadata name.
   * @param value the metadata value.
   */
  public void set(String name, String value) {
    metadata.put(name, new String[] { value });
  }

  /**
   * Remove a metadata and all its associated values.
   *
   * @param name metadata name to remove
   */
  public void remove(String name) {
    metadata.remove(name);
  }

  /**
   * Returns the number of metadata names in this metadata.
   *
   * @return number of metadata names
   */
  public int size() {
    return metadata.size();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    String[] names = names();
    for (int i = 0; i < names.length; i++) {
      String[] values = _getValues(names[i]);
      for (int j = 0; j < values.length; j++) {
        buf.append(names[i]).append("=").append(values[j]).append(" ");
      }
    }
    return buf.toString();
  }

  public String getTrack() {
    return track;
  }

  public void setTrack(String track) {
    this.track = track;
  }

  public String getUrlStream() {
    return urlStream;
  }

  public void setUrlStream(String urlStream) {
    this.urlStream = urlStream;
  }

  public String getTitleChannel() {
    return titleChannel;
  }

  public void setTitleChannel(String titleChannel) {
    this.titleChannel = titleChannel;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSourceListId() {
    return sourceListId;
  }

  public void setSourceListId(String sourceListId) {
    this.sourceListId = sourceListId;
  }

  public List<ProgramEpg> getProgramEpgList() {
    return programEpgList;
  }

  public void setProgramEpgList(List<ProgramEpg> programEpgList) {
    this.programEpgList = programEpgList;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.metadata.size());
    for (Map.Entry<String, String[]> entry : this.metadata.entrySet()) {
      dest.writeString(entry.getKey());
      dest.writeStringArray(entry.getValue());
    }
    dest.writeString(this.id);
    dest.writeString(this.track);
    dest.writeString(this.urlStream);
    dest.writeString(this.titleChannel);
    dest.writeString(this.logo);
    dest.writeString(this.group);
    dest.writeString(this.sourceListId);
  }

  protected Channel(Parcel in) {
    int metadataSize = in.readInt();
    this.metadata = new HashMap<String, String[]>(metadataSize);
    for (int i = 0; i < metadataSize; i++) {
      String key = in.readString();
      String[] value = in.createStringArray();
      this.metadata.put(key, value);
    }
    this.id = in.readString();
    this.track = in.readString();
    this.urlStream = in.readString();
    this.titleChannel = in.readString();
    this.logo = in.readString();
    this.group = in.readString();
    this.sourceListId = in.readString();
  }

  public static final Creator<Channel> CREATOR = new Creator<Channel>() {
    @Override public Channel createFromParcel(Parcel source) {
      return new Channel(source);
    }

    @Override public Channel[] newArray(int size) {
      return new Channel[size];
    }
  };

  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (this == other) {
      return true;
    }

    Channel channelOther = (Channel) other;
    return this.titleChannel.equals(channelOther.titleChannel);
  }
}
