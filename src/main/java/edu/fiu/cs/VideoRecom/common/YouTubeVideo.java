package edu.fiu.cs.VideoRecom.common;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.fiu.cs.VideoRecom.tagger.YouTubeTag;

/**
 * This class represent one YouTube Video
 * 
 */
public class YouTubeVideo {

  private String title;
  private String description;
  private ArrayList<String> categories;
  private List<YouTubeTag> tags;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArrayList<String> getCategories() {
    if (categories == null)
      this.categories = new ArrayList<String>();
    return categories;
  }

  public void setCategories(ArrayList<String> categories) {
    this.categories = categories;
  }

  public List<YouTubeTag> getTags() {
    if (tags == null)
      this.tags = new ArrayList<YouTubeTag>();
    return tags;
  }

  public void setTags(List<YouTubeTag> tags) {
    this.tags = tags;
  }
  
  

  public static List<YouTubeVideo> parseJsonToVideos(JsonArray jsonVideos) {

    List<YouTubeVideo> videos = new ArrayList<YouTubeVideo>();

    for (JsonElement je : jsonVideos) {
      YouTubeVideo vd = new YouTubeVideo();
      JsonObject jeTmp = je.getAsJsonObject();
      vd.setTitle(jeTmp.get("title").getAsString());
      vd.setDescription(jeTmp.get("description").getAsString());

      JsonArray cgs = jeTmp.get("categories").getAsJsonArray();
      for (JsonElement cg : cgs) {
        vd.getCategories().add(cg.getAsString());
      }

      videos.add(vd);
    }

    return videos;
  }

}
