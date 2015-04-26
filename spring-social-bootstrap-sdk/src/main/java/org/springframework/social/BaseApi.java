/**
 * 
 */
package org.springframework.social;

import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base interface for Spring Social Bootstrap SDK-based API integrations
 * 
 * <p>Represents an API binding to a provider's API
 *
 * @author robin
 */
public interface BaseApi extends ApiBinding {
	
	public ClientSettings getSettings();
	public ObjectMapper getObjectMapper();
	public RestTemplate getRestTemplate();

}
