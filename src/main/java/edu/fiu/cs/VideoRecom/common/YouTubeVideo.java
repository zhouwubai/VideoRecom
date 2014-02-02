package edu.fiu.cs.VideoRecom.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.fiu.cs.VideoRecom.tagger.YouTubeTag;

/**
 * This class represent one YouTube Video
 * 
 */
public class YouTubeVideo {

  public static String TAG_W = "tag.weight";
  public static String TITLE_W = "title.weight";
  public static String DESC_W = "desc.weight";
  public static String N_GRAM = "n.gram";

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

  /**
   * return ngrams of a video, set different weight for tag,title and
   * description
   * 
   * @param conf
   * @return tf term frequency
   */
  public Map<String, Integer> getNgrams(Map<String, Integer> conf) {
    Integer tagW = conf.containsKey(TAG_W) ? conf.get(TAG_W) : 5;
    Integer titleW = conf.containsKey(TITLE_W) ? conf.get(TITLE_W) : 2;
    Integer descW = conf.containsKey(DESC_W) ? conf.get(DESC_W) : 1;
    Integer ngram = conf.containsKey(N_GRAM) ? conf.get(N_GRAM) : 3;

    Map<String, Integer> tf = new HashMap<String, Integer>();
    // every occurrence of tag times tagW
    for (YouTubeTag tag : tags) {
      if (tf.containsKey(tag.getName())) {
        tf.put(tag.getName(), tf.get(tag.getName() + tagW * 1));
      } else {
        tf.put(tag.getName(), tagW * 1);
      }
    }

    // every occurrence of title times titleW, we using 1,..,n-grams at same
    // time
    String[] words = title.split(" ");
    for (int n = 1; n < ngram; n++) {
      for (int i = 0; i < words.length - n + 1; i++) {
        String tmpGram = words[i];
        for (int j = 1; j < n; j++) {
          tmpGram += " " + words[i + j];
        }
        if (tf.containsKey(tmpGram)) {
          tf.put(tmpGram, tf.get(tmpGram) + titleW * 1);
        } else {
          tf.put(tmpGram, titleW * 1);
        }
      }
    }

    // every occurrence of description times descW, we using 1,...,n-grams at
    // same time
    words = description.split(" ");
    for (int n = 1; n < ngram; n++) {
      for (int i = 0; i < words.length - n + 1; i++) {
        String tmpGram = words[i];
        for (int j = 1; j < n; j++) {
          tmpGram += " " + words[i + j];
        }
        if (tf.containsKey(tmpGram)) {
          tf.put(tmpGram, tf.get(tmpGram) + descW * 1);
        } else {
          tf.put(tmpGram, descW * 1);
        }
      }
    }

    return tf;
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
