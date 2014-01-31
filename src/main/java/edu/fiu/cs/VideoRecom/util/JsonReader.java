package edu.fiu.cs.VideoRecom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * JsonReader Responsible for reading and parse Json file to lists.
 *
 */
public class JsonReader {

	public static String DATA_PATH = "./src/test/resources/CodeAssignmentDataSet.json";
	private static Logger logger = LoggerFactory.getLogger(JsonReader.class);
	
	private String path;
	
	public JsonReader(String path) {
		// TODO Auto-generated constructor stub
		this.path = path;
	}
	
	
	
}
