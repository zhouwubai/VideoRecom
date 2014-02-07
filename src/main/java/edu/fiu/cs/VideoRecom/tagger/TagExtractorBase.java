package edu.fiu.cs.VideoRecom.tagger;

import java.util.List;

/**
 * Base class for tagger
 * @author zhouwubai
 *
 */
public abstract class TagExtractorBase {

	public abstract List<YouTubeTag> tag(String text);
	
}
