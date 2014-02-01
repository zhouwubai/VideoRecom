package edu.fiu.cs.VideoRecom.tagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TagExtractor {

  public static Logger logger = LoggerFactory.getLogger(TagExtractor.class);
  public static String dbUrlBase = "http://lupedia.ontotext.com/lookup/text2json";

  private static JsonParser parser = new JsonParser();
  private String query;

  // = "Keegan-Michael Key and Jordan Peele sit "
  // + "down with Peter Rubin to talk about their love of Game of Thrones, "
  // + "their favorite comedy sketches";

  private String sendRequest() {
    String rtnJson;
    try {
      rtnJson = Jsoup.connect(dbUrlBase).data("lookupText", query)
          .method(Method.POST).ignoreContentType(true).execute().body();
      return rtnJson;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      logger
          .error("error happens when sending request to database,skip this task....");
      return "[]";
    }
  }

  private List<YouTubeTag> parseJsonToTag(String json) {
    JsonArray rawTags = parser.parse(json).getAsJsonArray();
    List<YouTubeTag> tags = new ArrayList<YouTubeTag>();

    for (JsonElement e : rawTags) {
      YouTubeTag tag = new YouTubeTag();
      JsonObject jsonTag = e.getAsJsonObject();
      int startOffset = jsonTag.get("startOffset").getAsInt();
      int endOffset = jsonTag.get("endOffset").getAsInt();
      String instanceClass = jsonTag.get("instanceClass").getAsString();
      tag.setName(query.substring(startOffset, endOffset));
      tag.setType(instanceClass.substring(instanceClass.lastIndexOf("/")+1));
      tags.add(tag);
    }

    return tags;
  }

  public List<YouTubeTag> tag(String lookupText) {
    this.query = lookupText;
    return parseJsonToTag(sendRequest());
  }

}
