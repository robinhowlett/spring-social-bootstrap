/**
 * 
 */
package org.springframework.social.har.impl;

import org.springframework.web.client.RestTemplate;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarEntry;
import com.sportslabs.amp.har.dto.entries.HarPostData;

/**
 * Replay HAR entries
 * 
 * @author robin
 */
public class ReplayHarTemplate extends AbstractReplayHarTemplate<Har, HarEntry> {
	
	public ReplayHarTemplate(RestTemplate restTemplate) {
		super(restTemplate);
	}

	@Override
	protected String getRequestBody(HarEntry entry) {
		HarPostData postData = entry.getRequest().getPostData();
		return (postData != null ? postData.getText() : null);
	}

}
