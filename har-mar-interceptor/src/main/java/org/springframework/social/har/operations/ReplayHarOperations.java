/**
 * 
 */
package org.springframework.social.har.operations;

import org.springframework.social.har.replay.Interval;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarEntry;

/**
 * Operations to replay a {@link Har} HTTP Archive's log of {@link HarEntry} entries at a specified {@link Interval}
 * 
 * <p>An integration point for Spring Social Bootstrap SDK-based API clients that wish to replay a HTTP Archive
 * 
 * @author robin
 */
public interface ReplayHarOperations<H extends Har, E extends HarEntry> {
	
	void replay(H har, Interval interval);

}
