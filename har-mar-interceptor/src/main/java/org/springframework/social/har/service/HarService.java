/**
 * 
 */
package org.springframework.social.har.service;

import org.joda.time.DateTime;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarEntry;

/**
 * Service interface for saving domain {@link org.springframework.social.har.domain.HarEntry}-type entries to a 
 * data store and querying the data store to convert the entries to a {@link Har} HTTP Archive.
 *
 * @author robin
 */
public interface HarService<D extends org.springframework.social.har.domain.HarEntry, E extends HarEntry, H extends Har> {
	
	public D save(E entry);
	
	public H findAll();
	public H findAllOrderByTimestampDesc();
	public H findByTimestampGreaterThan(DateTime start);
	public H findByTimestampBetween(DateTime start, DateTime end);
	
}
