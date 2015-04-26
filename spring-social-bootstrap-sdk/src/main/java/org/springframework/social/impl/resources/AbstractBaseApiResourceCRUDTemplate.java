/**
 * 
 */
package org.springframework.social.impl.resources;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.util.Assert.notNull;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.operations.resources.BaseApiResourceCRUDOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Abstract implementation of {@link BaseApiResourceCRUDOperations}, extending {@link AbstractBaseApiResourceQueryTemplate}
 * 
 * <p>Provides CRUD-style implementations of HTTP Method executions against a resource API, 
 * storing the last {@link ResponseEntity} in {@link #setLastResult(ResponseEntity)}
 *
 * @author robin
 */
public abstract class AbstractBaseApiResourceCRUDTemplate<R extends BaseApiResource, P extends BaseApiPage<R>, B extends BaseApiUriComponentsBuilder<?>> 
		extends AbstractBaseApiResourceQueryTemplate<R, P, B> 
		implements BaseApiResourceCRUDOperations<R, P, B> {

	public AbstractBaseApiResourceCRUDTemplate(ClientSettings settings, RestTemplate restTemplate) {
		super(settings, restTemplate);
	}

	@Override
	public R get(String uid) {
		notNull(uid, "uid must not be null");
		
		URI uri = qb().path(getResourceIdPath()).buildAndExpand(uid).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<R> result = (ResponseEntity<R>) createRequest(GET, uri, getResourceClass());
		R body = result.getBody();
		
		return body;
	}

	@Override
	public R create(R resource) {
		notNull(resource, "The resource to be created must not be null");
		
		URI uri = qb().build().toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<R> result =
				(ResponseEntity<R>) createRequest(POST, uri, resource, NO_ADDITIONAL_HEADERS, getResourceClass());
		R body = result.getBody();
		
		return body;
	}

	@Override
	public R update(R resource) {
		notNull(resource, "The Resource to be updated must not be null");
		notNull(resource.getUid(), "The Resource to be updated must have a UID");
		
		URI uri = qb().path(getResourceIdPath()).buildAndExpand(resource.getUid()).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<R> result = 
				(ResponseEntity<R>) createRequest(PUT, uri, resource, NO_ADDITIONAL_HEADERS, getResourceClass());
		R body = result.getBody();
		
		return body;
	}

	@Override
	public R delete(String uid) {
		notNull(uid, "uid must not be null");
		
		URI uri = qb().path(getResourceIdPath()).buildAndExpand(uid).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<R> result =
				(ResponseEntity<R>) createRequest(DELETE, uri, getResourceClass());
		R body = result.getBody();
		
		return body;
	}

}
