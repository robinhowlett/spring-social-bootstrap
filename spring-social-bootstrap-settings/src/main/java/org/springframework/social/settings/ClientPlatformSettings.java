/**
 * 
 */
package org.springframework.social.settings;

import org.springframework.web.util.UriComponentsBuilder;


/**
 * Interface to define the base URI of the API being connected to
 *
 * @author robin
 */
public interface ClientPlatformSettings {
	
	public UriComponentsBuilder getBaseUriBuilder();
	

}
