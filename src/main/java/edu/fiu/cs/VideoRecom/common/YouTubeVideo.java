package edu.fiu.cs.VideoRecom.common;

import java.util.ArrayList;

import edu.fiu.cs.VideoRecom.tagger.YouTubeTag;

/**
 *This class represent one YouTube Video 
 *
 */
public class YouTubeVideo {

  private String title;
  private String description;
  private ArrayList<String> categories;
  private ArrayList<YouTubeTag> tags;

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
    if(categories == null)
      this.categories = new ArrayList<String>();
    return categories;
  }

  public void setCategories(ArrayList<String> categories) {
    this.categories = categories;
  }

  public ArrayList<YouTubeTag> getTags() {
    if(tags == null)
      this.tags = new ArrayList<YouTubeTag>();
    return tags;
  }

  public void setTags(ArrayList<YouTubeTag> tags) {
    this.tags = tags;
  }

}
