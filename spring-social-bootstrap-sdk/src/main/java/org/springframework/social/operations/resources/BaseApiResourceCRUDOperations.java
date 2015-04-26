/**
 * 
 */
package org.springframework.social.operations.resources;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Extended implementation of {@link BaseApiResourceQueryOperations} supporting 
 * CRUD-type operations against a resource API endpoint
 * 
 * @author robin
 */
public interface BaseApiResourceCRUDOperations<R extends BaseApiResource, P extends BaseApiPage<R>, B extends BaseApiUriComponentsBuilder<?>> extends BaseApiResourceQueryOperations<R, P, B> {

	public R get(String uid);
	
	public R create(R resource);
	
	public R update(R resource);
	
	public R delete(String uid);
	
}
