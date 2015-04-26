/**
 * 
 */
package org.springframework.social.har;

import org.springframework.social.har.operations.ReplayHarOperations;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarEntry;

/**
 * Interface for replaying/re-executing HTTP requests from the HTTP Archive log
 *
 * @author robin
 */
public interface Replayable<O extends ReplayHarOperations<? extends Har, ? extends HarEntry>> {
	
	/**
	 * Operations to replay an HTTP Archive's Entries
	 */
	O replayOperations();

}
