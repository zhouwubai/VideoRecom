package edu.fiu.cs.VideoRecom;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.fiu.cs.VideoRecom.tagger.LupediaTagExtractor;
import edu.fiu.cs.VideoRecom.tagger.YouTubeTag;

public class TestTagExtractor {

  
  
  private static Logger logger = LoggerFactory.getLogger(TestTagExtractor.class);
  private String query;
  private LupediaTagExtractor te;

  @Before
  public void setup() {
    this.te = new LupediaTagExtractor();
    this.query = "Beverly D\u0027Angelo reads a copy of "
        + "More in a meeting with Ari on the HBO Series Entourage.";
  }

  @Test
  public void test() throws IOException {
    
    List<YouTubeTag> tags = te.tag(query);
//    assertTrue("Tags Number", tags.size() == 3);
    for(YouTubeTag tag : tags) {
      System.out.println(tag.toString());
    }
  }

}
