/**
 * 
 */
package org.springframework.social.har;

import org.springframework.social.har.client.AbstractHarInterceptor;
import org.springframework.social.har.operations.HarOperations;
import org.springframework.social.har.service.HarService;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarEntry;

/**
 * Interface for defining operations may be performed against the HTTP archive
 *
 * @author robin
 */
public interface Archivable<
		O extends HarOperations<? extends org.springframework.social.har.domain.HarEntry, 			? extends HarEntry, ? extends Har>,
		I extends AbstractHarInterceptor<? extends org.springframework.social.har.domain.HarEntry,	? extends HarEntry, ? extends Har>,
		S extends HarService<? extends org.springframework.social.har.domain.HarEntry, 				? extends HarEntry, ? extends Har>> {
	
	/**
	 * Operations on the HTTP Archive (HAR) database of requests/entries made
	 */
	public O harOperations();
	
	public I createHarInterceptor(S harService);

}
