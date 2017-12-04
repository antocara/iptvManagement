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

package com.anp.commons.parserlib.parser.asx;

import com.anp.commons.parserlib.exception.JPlaylistParserException;
import com.anp.commons.parserlib.mime.MediaType;
import com.anp.commons.parserlib.parser.AbstractParser;
import com.anp.commons.parserlib.parser.AutoDetectParser;
import com.anp.commons.data.entities.ChannelsList;
import com.anp.commons.data.entities.Channel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;

public class ASXPlaylistParser extends AbstractParser {
	public final static String EXTENSION = ".asx";
    
	private final String ABSTRACT_ELEMENT = "ABSTRACT";
	private final String ASX_ELEMENT = "ASX";
	private final String AUTHOR_ELEMENT = "AUTHOR";
	private final String BASE_ELEMENT = "BASE";
	private final String COPYRIGHT_ELEMENT = "COPYRIGHT";
	private final String DURATION_ELEMENT = "DURATION";
	private final String ENDMARKER_ELEMENT = "ENDMARKER";
	private final String ENTRY_ELEMENT = "ENTRY";
	private final String ENTRYREF_ELEMENT = "ENTRYREF";
	private final String EVENT_ELEMENT = "EVENT";
	private final String MOREINFO_ELEMENT = "MOREINFO";
	private final String PARAM_ELEMENT = "PARAM";
	private final String REF_ELEMENT = "REF";
	private final String REPEAT_ELEMENT = "REPEAT";
	private final String STARTMARKER_ELEMENT = "STARTMARKER";
	private final String STARTTIME_ELEMENT = "STARTTIME";
	private final String TITLE_ELEMENT = "TITLE";
	
	private final String HREF_ATTRIBUTE = "href";
	
	private static int mNumberOfFiles = 0;
	
    private static final Set<MediaType> SUPPORTED_TYPES =
    		Collections.singleton(MediaType.video("x-ms-asf"));

    public Set<MediaType> getSupportedTypes() {
    	return SUPPORTED_TYPES;
    }
	
	/**
	 * Retrieves the files listed in a .asx file
	 * @throws IOException 
	 */
    private void parsePlaylist(InputStream stream, ChannelsList channelsList) throws IOException {
		String xml = "";
        String line = null;
        BufferedReader reader = null;
        
		// Start the query
        reader = new BufferedReader(new InputStreamReader(stream));
		    
		while ((line = reader.readLine()) != null) {
		    xml = xml + line; 
        }
		    
		parseXML(xml, channelsList);
    }

    private String validateXML(String xml, SAXBuilder builder) throws JDOMException, IOException {
	    Reader in;
	    int i = 0;

	    xml = xml.replaceAll("\\&", "&amp;");
	    
	    while (i < 5) {
		    in = new StringReader(xml);
	    	
	    	try {
	    		builder.build(in);
	    		break;
	    	} catch (JDOMParseException e) {
	    		String message = e.getMessage();
	    		if (message.matches("^.*.The element type.*.must be terminated by the matching end-tag.*")) {
	    			String tag = message.substring(message.lastIndexOf("type") + 6, message.lastIndexOf("must") - 2);
	    			xml = xml.replaceAll("(?i)</" + tag + ">" , "</" + tag + ">");
	    		} else {
	    			break;
	    		}
	    		
	    		i++;
	    	}
	    }
	    
		return xml;
    }
    
	private void parseXML(String xml, ChannelsList channelsList) {
		SAXBuilder builder = new SAXBuilder();
	    Reader in;
	    Document doc = null;
	    Element root = null;
	    
	    try {
	    	xml = validateXML(xml, builder);
		    in = new StringReader(xml);
		    
	    	doc = builder.build(in);
	    	root = doc.getRootElement();
	    	List<Element> children = castList(Element.class, root.getChildren());
	    	
	    	for (int i = 0; i < children.size(); i++) {
	    		String tag = children.get(i).getName();
	    		
	    		if (tag != null && tag.equalsIgnoreCase(ENTRY_ELEMENT)) {
	    			List<Element> children2 = castList(Element.class, children.get(i).getChildren());
	    			
	    			buildPlaylistEntry(children2, channelsList);
	    		} else if (tag != null && tag.equalsIgnoreCase(ENTRYREF_ELEMENT)) {
	    			URL url;
	    			HttpURLConnection conn = null;
	    			InputStream is = null;
	    			
	    			try {
	    				String href = children.get(i).getAttributeValue(HREF_ATTRIBUTE);
	    	    	
	    				if (href == null) {
	    					href = children.get(i).getAttributeValue(HREF_ATTRIBUTE.toUpperCase());
	    				}
	    	    	
	    				if (href == null) {
	    					href = children.get(i).getValue();
	    				}
	    	    	
	    				url = new URL(href);
	    				conn = (HttpURLConnection) url.openConnection();        	
	    				conn.setConnectTimeout(6000);
	    				conn.setReadTimeout(6000);
	    				conn.setRequestMethod("GET");
	        		
	    				String contentType = conn.getContentType();
	    				is = conn.getInputStream();
	    			
	    				AutoDetectParser parser = new AutoDetectParser();
	    				parser.parse(url.toString(), contentType, is, channelsList);
	    			} catch (IOException e) {
	    			} finally {
	    				if (conn != null) {
	    					conn.disconnect();
	    				}
	    				
	    				if (is != null) {
	    					try {
	    						is.close();
	    					} catch (IOException e) {
	    					}
	    				}
	    			}
	    		}
	    	}
	    } catch (Exception e) {
	    }
	}
    
    private void buildPlaylistEntry(List<Element> children, ChannelsList channelsList) {
    	Channel channel = new Channel();
    	
    	for(int i = 0; i < children.size(); i++) {
    	    String attributeName = children.get(i).getName();
    		
    	    if (attributeName.equalsIgnoreCase(ABSTRACT_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(ASX_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(AUTHOR_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(BASE_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(COPYRIGHT_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(DURATION_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(ENDMARKER_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(ENTRY_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(ENTRYREF_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(EVENT_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(MOREINFO_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(PARAM_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(REF_ELEMENT)) {
    	    	String href = children.get(i).getAttributeValue(HREF_ATTRIBUTE);
    	    	
    	    	if (href == null) {
        	    	href = children.get(i).getAttributeValue(HREF_ATTRIBUTE.toUpperCase());
    	    	}
    	    	
    	    	if (href == null) {
    	    	    href = children.get(i).getValue();
    	    	}
    	    	
    	    	// TODO: add trim?
    	    	channel.set(Channel.URI, href);
    	    } else if (attributeName.equalsIgnoreCase(REPEAT_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(STARTMARKER_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(STARTTIME_ELEMENT)) {
    	    } else if (attributeName.equalsIgnoreCase(TITLE_ELEMENT)) {
    	    	String title = children.get(i).getValue();
    	    	
    	    	if (title != null) {
		        	channel.set(Channel.PLAYLIST_METADATA, title);
    	    	}
    	    }
    	}
    	
    	mNumberOfFiles = mNumberOfFiles + 1;
    	channel.set(Channel.TRACK, String.valueOf(mNumberOfFiles));
    	parseEntry(channel, channelsList);
    }
    
    private <T> List<T> castList(Class<? extends T> castClass, List<?> c) {
        List<T> list = new ArrayList<T>(c.size());
        
        for(Object o: c) {
        	list.add(castClass.cast(o));
        }
        
        return list;
    }

	@Override
	public void parse(String uri, InputStream stream, ChannelsList channelsList)
			throws IOException, SAXException, JPlaylistParserException {
		parsePlaylist(stream, channelsList);
	}
}

