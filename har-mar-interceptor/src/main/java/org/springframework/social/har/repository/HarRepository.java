/**
 * 
 */
package org.springframework.social.har.repository;

import org.joda.time.DateTime;
import org.springframework.social.har.domain.HarEntry;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository} for saving and querying {@link HarEntry} domain objects to/from a database
 * 
 * @author robin
 */
@Repository
public interface HarRepository<D extends HarEntry> {

	D save(final D entry);
	
	Iterable<D> findAll();
	Iterable<D> findAllOrderByTimestampDesc();
	Iterable<D> findByTimestampGreaterThan(final DateTime start);
	Iterable<D> findByTimestampBetween(final DateTime start, final DateTime end);

}
