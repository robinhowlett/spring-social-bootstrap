/**
 * 
 */
package org.springframework.social.impl.resources;

import static org.springframework.web.util.BaseApiUriComponentsBuilder.fromUri;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Abstract implementation of the {@link BaseApiResourceOperations} interface.
 * 
 * <p>Uses the provided {@link RestTemplate} to execute the HTTP method to the given URI template from the {@link ClientSettings}, 
 * writing the given request entity to the request, and returning the response as {@link ResponseEntity}.
 * 
 * <p>Abstract methods are provided to define the general resource API path, the individual resource API path, and the resource class
 *
 * @author robin
 */
public abstract class AbstractBaseApiResourceTemplate<B extends BaseApiUriComponentsBuilder<?>> implements BaseApiResourceOperations<B> {
	
	protected final static HttpHeaders NO_ADDITIONAL_HEADERS = null;
	protected final static Object EMPTY_BODY = null;
	
	private final ClientSettings settings;
	private final RestTemplate restTemplate;
	
	public AbstractBaseApiResourceTemplate(ClientSettings settings, RestTemplate restTemplate) {
		this.settings = settings;
		this.restTemplate = restTemplate;
	}
	
	public ClientSettings getSettings() {
		return settings;
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	/**
	 * @return the {@link BaseApiUriComponentsBuilder} initialized from the 
	 * {@link ClientSettings} {@code getBaseUri()} URI and resource API path
	 */
	@SuppressWarnings("unchecked")
	@Override
	public B qb() {
		return (B) fromUri(getSettings().getBaseUri(), null).path(getResourcePath());
	}
	
	/**
	 * @return The general resource API path
	 */
	protected abstract String getResourcePath();
	
	/**
	 * @return The individual resource API path
	 */
	protected abstract String getResourceIdPath();
	
	/**
	 * @return The class type of the {@link BaseApiResource} coupled with this resource API
	 */
	protected abstract Class<? extends BaseApiResource> getResourceClass();
	
	@SuppressWarnings("rawtypes")
	protected abstract Class<? extends BaseApiPage> getPageClass();
	
	/**
	 * Using the provided {@link RestTemplate}, executes the HTTP method to the given URI template, creating a request entity without a body or headers and 
	 * writing it to the request, and returning the response as a {@link ResponseEntity} whose body can be of type {@code responseClass}.
	 * 
	 * @param httpMethod
	 * @param uri
	 * @param responseClass
	 * @return
	 */
	protected ResponseEntity<?> createRequest(HttpMethod httpMethod, URI uri, Class<?> responseClass) {
		return createRequest(httpMethod, uri, EMPTY_BODY, NO_ADDITIONAL_HEADERS, responseClass);
	}
	
	/**
	 * Using the provided {@link RestTemplate}, executes the HTTP method to the given URI template, creating a request entity from the body and headers and 
	 * writing it to the request, and returning the response as a {@link ResponseEntity} whose body can be of type {@code responseClass}
	 * 
	 * @param httpMethod
	 * @param uri
	 * @param body
	 * @param headers
	 * @param responseClass
	 * @return The response as a {@link ResponseEntity}
	 */
	protected ResponseEntity<?> createRequest(HttpMethod httpMethod, URI uri, 
			Object body, HttpHeaders headers, Class<?> responseClass) {
		RequestEntity<Object> requestEntity = new RequestEntity<Object>(body, headers, httpMethod, uri);
		
		ResponseEntity<?> responseEntity = getRestTemplate().exchange(requestEntity, responseClass);
		
		return responseEntity;
	}

}
