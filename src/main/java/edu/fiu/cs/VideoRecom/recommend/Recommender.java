package edu.fiu.cs.VideoRecom.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.fiu.cs.VideoRecom.common.YouTubeVideo;

/**
 * Here i use simple algorithm to get related video The feature I am using is
 * tag,categories, title and description. Title and feature will merge into one
 * and use n-gram model to featurize it. I will assign different weight these
 * three kinds of features.
 * 
 */

public class Recommender {

  private static Logger logger = LoggerFactory.getLogger(Recommender.class);

  private List<YouTubeVideo> videos;
  private double[][] simGraph = null;

  private void checkVideosSetup() {
    if (videos == null) {
      logger.error("please set videos.. ");
      System.exit(0);
    }
  }

  public void computeSimGraph(Map<String, Double> conf) {
    checkVideosSetup();

    Map<String, Integer> idf = new HashMap<String, Integer>();
    List<Map<String, Double>> X = new ArrayList<Map<String, Double>>();
    List<Map<String, Double>> rawX = new ArrayList<Map<String, Double>>();
    Integer numOfVideos = videos.size();
    simGraph = new double[numOfVideos][numOfVideos];

    // get document frequency, # of occurrence in documents
    for (YouTubeVideo video : videos) {
      Map<String, Double> x = video.getNgrams(conf);
      for (String ngram : x.keySet()) {
        Integer df = idf.get(ngram);
        if (df == null)
          df = 0;
        idf.put(ngram, df + 1);// just document frequency, not idf
      }
      rawX.add(x);
    }
    logger.info("idf done");

    for (Map<String, Double> x : rawX) {

      double norm = 0;
      for (Entry<String, Double> en : x.entrySet()) {
        String ngram = en.getKey();
        Integer ngramidf = idf.get(ngram);// document frequency
        Double tf = en.getValue(); // term frequency
        tf *= Math.log(numOfVideos / (ngramidf + 1)); // smoothing
        en.setValue(tf);
        norm += tf * tf;
      }

      norm = Math.sqrt(norm);
      // normalization
      for (Entry<String, Double> en : x.entrySet()) {
        en.setValue(en.getValue() / norm);
      }

      X.add(x);
    }
    logger.info("vectorization done");

    for (int i = 0; i < numOfVideos; i++) {
      simGraph[i][i] = 1;
      for (int j = i + 1; j < numOfVideos; j++) {
        simGraph[i][j] = simGraph[j][i] = innerProduct(X.get(i), X.get(j));
      }
    }

  }

  public static double innerProduct(Map<String, Double> a, Map<String, Double> b) {
    double inner = 0;
    if (a.size() > b.size()) {
      Map<String, Double> c = a;
      a = b;
      b = c;
    }
    for (Entry<String, Double> en : a.entrySet()) {
      Double v = b.get(en.getKey());
      if (v != null) {
        inner += en.getValue() * v;
      }
    }
    return inner;
  }

  public List<YouTubeVideo> getVideos() {
    return videos;
  }

  public void setVideos(List<YouTubeVideo> videos) {
    this.videos = videos;
  }

  public double[][] getSimGraph() {
    return simGraph;
  }

}
