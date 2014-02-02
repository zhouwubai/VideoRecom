package edu.fiu.cs.VideoRecom.tagger;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.fiu.cs.VideoRecom.common.YouTubeVideo;
import edu.fiu.cs.VideoRecom.util.JsonReader;
import edu.fiu.cs.VideoRecom.util.JsonWriter;

public class YouTubeTagger {

  private static Logger logger = LoggerFactory.getLogger(YouTubeTagger.class);
  
  private String inputPath;
  private String outputPath;

  public void run() {

    List<YouTubeVideo> videos = YouTubeVideo.parseJsonToVideos((new JsonReader(
        inputPath)).parse().getAsJsonArray());
    
    //Here we can try to speedup using multi thread
    int count = 1;
    TagExtractor tagE = new TagExtractor();
    for(YouTubeVideo video: videos) {
      String lookupText = video.getTitle() + video.getDescription();
      List<YouTubeTag> tags = tagE.tag(lookupText);
      video.setTags(tags);
      logger.info(String.format("done tagging %d video", count++));
    }

    //write to output
    (new JsonWriter(outputPath)).write(videos);
  }
  
  
  public static void main(String[] args) {
    
   YouTubeTagger tagger = new YouTubeTagger();
   tagger.setInputPath(JsonReader.DATA_INPUT_PATH);
   tagger.setOutputPath(JsonWriter.DATA_OUT_PATH);
   tagger.run();
    
  }
  
  

  public String getInputPath() {
    return inputPath;
  }

  public void setInputPath(String inputPath) {
    this.inputPath = inputPath;
  }

  public String getOutputPath() {
    return outputPath;
  }

  public void setOutputPath(String outputPath) {
    this.outputPath = outputPath;
  }

}
