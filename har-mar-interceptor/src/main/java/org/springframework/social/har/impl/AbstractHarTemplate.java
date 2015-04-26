/**
 * 
 */
package org.springframework.social.har.impl;

import static org.springframework.data.domain.Sort.Direction.ASC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.social.har.operations.HarOperations;
import org.springframework.social.har.service.HarService;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarEntry;

/**
 * Abstract implementation of {@link HarOperations} that wraps a {@link HarService} for saving 
 * {@link org.springframework.social.har.domain.HarEntry} Domain objects and querying {@link Har} logs
 *
 *@author robin
 */
public abstract class AbstractHarTemplate<D extends org.springframework.social.har.domain.HarEntry, E extends HarEntry, H extends Har> implements HarOperations<D, E, H> {
	
	private final HarService<D, E, H> harService;

	@Autowired
	public AbstractHarTemplate(HarService<D, E, H> harService) {
		this.harService = harService;
	}

	@Override
	public D create(E harEntry) {
		return getHarService().save(harEntry);
	}

	@Override
	public H query() {
		return query(ASC);
	}
	
	@Override
	public H query(Direction direction) {
		if (ASC.equals(direction)) {
			return getHarService().findAll();
		} else {
			return getHarService().findAllOrderByTimestampDesc();
		}
	}
	
	@Override
	public HarService<D, E, H> getHarService() {
		return harService;
	}

}
