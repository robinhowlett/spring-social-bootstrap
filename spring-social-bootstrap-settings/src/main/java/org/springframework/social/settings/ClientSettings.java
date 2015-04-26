/**
 * 
 */
package org.springframework.social.settings;

import java.net.URI;

import org.joda.time.DateTimeZone;

/**
 * Interface defining the recommended configuration required when connecting to a target API, 
 * including any authentication or authorization data required, the default time zone to apply, 
 * the User Agent to be used, and the base URL of the API being connected to.
 *
 * @author robin
 */
public interface ClientSettings {
	
	public ClientSecuritySettings getSecuritySettings();
	public DateTimeZone getDefaultTimeZone();
	public String getUserAgent();
	public URI getBaseUri();

}
