/**
 * 
 */
package org.springframework.social.har.impl;

import static java.lang.Thread.sleep;
import static org.joda.time.DateTime.now;
import static org.slf4j.LoggerFactory.getLogger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.har.operations.ReplayHarOperations;
import org.springframework.social.har.replay.Interval;
import org.springframework.web.client.RestTemplate;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarEntry;
import com.sportslabs.amp.har.dto.entries.HarHeader;

/**
 * Abstract implementaiton of {@link ReplayHarOperations} to replay the requests in a {@link Har} HTTP Archive
 * 
 * @author robin
 */
public abstract class AbstractReplayHarTemplate<H extends Har, E extends HarEntry> implements ReplayHarOperations<H, E> {
	
	private static final Logger LOG = getLogger(AbstractReplayHarTemplate.class);
	
	private final RestTemplate restTemplate;
	
	public AbstractReplayHarTemplate(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	/**
	 * HAR (postData) and ALF (content) differentiate on how the request body is provided
	 * 
	 * @param entry
	 * @return
	 */
	protected abstract String getRequestBody(E entry);
	
	public void replay(H har, Interval interval) {
		DateTime timekeeper = now();
		long previousRequestTimestamp;
		
		@SuppressWarnings("unchecked")
		Iterator<? extends E> iterator = (Iterator<? extends E>) har.getLog().getEntries().iterator();
		
		while (iterator.hasNext()) {
			E entry = iterator.next();
			
			long nextRequestTimestamp = entry.getStartedDateTime().getMillis();
			
			sleepUntilTimeForNextRequest(interval.getInterval(), timekeeper);
			
			HttpMethod method = HttpMethod.valueOf(entry.getRequest().getMethod());
			
			HttpHeaders headers = new HttpHeaders();
			for (HarHeader header : entry.getRequest().getHeaders()) {
				headers.add(header.getName(), header.getValue());
			}
			
			URI uri = null; 
			
			try {
				uri = entry.getRequest().getUrl().toURI();
			} catch (URISyntaxException e) {
				LOG.error(e.getMessage());
				return;
			}

			String text = getRequestBody(entry);
			
			ResponseEntity<String> responseEntity = 
					restTemplate.exchange(
							uri, 
							method, 
							new HttpEntity<String>(text, headers), 
							String.class);
			
			LOG.debug("Executed request to {} and received response code {}", uri, responseEntity.getStatusCode());
			
			timekeeper = now();
			previousRequestTimestamp = entry.getStartedDateTime().getMillis();
			interval.updateInterval(nextRequestTimestamp, previousRequestTimestamp);
		}
	}
	
	private void sleepUntilTimeForNextRequest(long desiredInterval, DateTime timekeeper) {
		long intervalBetweenNowAndLastRequest = now().getMillis() - timekeeper.getMillis();
		
		while (intervalBetweenNowAndLastRequest < desiredInterval) {
			try {
				sleep(100);
				intervalBetweenNowAndLastRequest = now().getMillis() - timekeeper.getMillis();
			} catch (InterruptedException e) {
				LOG.error(e.getMessage());
			}
		}
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
}
