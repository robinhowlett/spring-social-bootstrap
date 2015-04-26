/**
 * 
 */
package org.springframework.social.operations.resources.auth;

import org.springframework.web.client.RestTemplate;

/**
 * Interface for non-OAuth authorizations against an API/Service Provider
 * 
 * <p>Implementing classes will use the provided {@link RestTemplate} to authorize the client against the API
 *
 * @author robin
 */
public interface AuthOperations {
	
	/**
	 * Authorize the client against a service provider's API
	 * @return An authorization response, if any
	 */
	Object authorize();
	
	/**
	 * A separate {@link RestTemplate} to use for authorizing against a service provider's API
	 * @return The {@link RestTemplate} to use for authorization requests
	 */
	RestTemplate getRestTemplate();

}
