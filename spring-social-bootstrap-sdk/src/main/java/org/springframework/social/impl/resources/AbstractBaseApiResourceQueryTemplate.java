/**
 * 
 */
package org.springframework.social.impl.resources;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.operations.resources.BaseApiResourceQueryOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Abstract implementation of the {@link BaseApiResourceQueryOperations} interface, extending {@link AbstractBaseApiResourceTemplate}
 * 
 * <p>Configures a {@link BaseApiUriComponentsBuilder} with a reference to this instance so that the 
 * builder can execute {@link #query(BaseApiUriComponentsBuilder)} calls on the resource template
 *
 * @author robin
 */
public abstract class AbstractBaseApiResourceQueryTemplate<R extends BaseApiResource, P extends BaseApiPage<R>, B extends BaseApiUriComponentsBuilder<?>> extends AbstractBaseApiResourceTemplate<B> implements BaseApiResourceQueryOperations<R, P, B> {
	
	public AbstractBaseApiResourceQueryTemplate(ClientSettings settings, RestTemplate restTemplate) {
		super(settings, restTemplate);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.social.operations.resources.BaseApiResourceQueryOperations#query()
	 */
	@Override
	public P query() {
		return query(qb());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.social.operations.resources.BaseApiResourceQueryOperations#query(org.springframework.web.util.BaseApiUriComponentsBuilder)
	 */
	@Override
	public P query(B builder) {
		URI uri = builder.build().toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<P> result =
				(ResponseEntity<P>) createRequest(HttpMethod.GET, uri, EMPTY_BODY, NO_ADDITIONAL_HEADERS, getPageClass());
		
		P body = result.getBody();
		
		return body;
	}

}
