/**
 * 
 */
package org.springframework.social.har.operations;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.social.har.service.HarService;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarEntry;

/**
 * Operations for a HTTP Archive's log of entries
 * 
 * <p>An integration point for Spring Social Bootstrap SDK-based API clients that wish to use a HTTP Archive
 * 
 * @author robin
 */
public interface HarOperations<D extends org.springframework.social.har.domain.HarEntry, E extends HarEntry, H extends Har> {
	
	D create(E harEntry);
	
	H query();
	H query(Direction direction);
	
	HarService<D, E, H> getHarService();
	
}
