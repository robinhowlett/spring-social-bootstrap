/**
 * 
 */
package org.springframework.social.dto.pagination;

import java.util.Collection;

import org.springframework.social.dto.BaseApiResource;

/**
 * Base decorator interface around multiple {@link BaseApiResource} DTOs 
 * 
 * @author robin
 */
public interface BaseApiPage<T extends BaseApiResource> extends BaseApiResource {

	/**
	 * The {@link BaseApiResource} DTOs being wrapped
	 * 
	 * @return Collection of {@link BaseApiResource}s 
	 */
	public Collection<T> getContent();
	
}
