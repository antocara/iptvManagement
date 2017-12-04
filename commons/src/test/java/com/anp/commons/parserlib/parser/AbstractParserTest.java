package com.anp.commons.parserlib.parser;

import com.anp.commons.data.entities.Channel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractParserTest {

  @Test public void parse_group_attribute() {

    String groupName = parse_group();
    assertEquals("PortugalExtra", groupName);
  }

  private String parse_group() {
    String line =
        "#EXTINF:-1 tvg-id=\"rtp1.pt\" tvg-name=\"RTP 1 HD PT\" tvg-logo=\"http://cinedestak.com/wp-content/uploads/2012/04/RTP1-logo.jpg\" group-title=\"PortugalExtra\" parent-code=\"\",RTP 1 HD PT";
    String line2 = "http://portal.inservice4u.com:8080/live/antocara/ZlzEu22A7L/2714.ts";
    //String[] lineString = line.split("([\"'])(?:(?=(\\\\?))\\2.)*?\\1");

    if (line.contains(Channel.GROUP)) {

      String[] lineString = line.split(Channel.GROUP + Channel.EQUAL);

      Pattern pattern = Pattern.compile("(\"[^\"]+\")");
      Matcher matcher = pattern.matcher(lineString[1]);
      if (matcher.find()) {
        String groupFind =  matcher.group(1);
        return groupFind.replaceAll("\"", "");
      }

      return lineString[0];

      //if (splitGroupName.length > 0) {
      //  String groupName = splitGroupName[0].replaceAll("\"", "");
      //  return groupName;
      //} else {
      //  return "DEFAULT";
      //}

    }
    return "DEFAULT";
  }
}