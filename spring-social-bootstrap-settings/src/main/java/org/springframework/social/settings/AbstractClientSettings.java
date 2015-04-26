/**
 * 
 */
package org.springframework.social.settings;

import static org.joda.time.DateTimeZone.UTC;
import static org.springframework.util.Assert.notNull;

import java.net.URI;

import org.joda.time.DateTimeZone;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Abstract implementation of {@link ClientSettings} that configures the target API's 
 * base URI, the necessary {@link ClientSecuritySettings}, the default {@link DateTimeZone}, 
 * and identifying User Agent String.
 *
 * @author robin
 */
public abstract class AbstractClientSettings implements ClientSettings {
	
	private final ClientSecuritySettings securitySettings;
	private final URI uri;
	
	public AbstractClientSettings(final ClientPlatformSettings platformSettings) {
		this(platformSettings, null);
	}
	
	public AbstractClientSettings(
			final ClientPlatformSettings platformSettings,
			final ClientSecuritySettings securitySettings) {
		this.securitySettings = securitySettings;
		
		notNull(platformSettings, "platformSettings must not be null");
		uri = initUriBuilder(platformSettings).build().toUri();
	}
	
	@Override
	public ClientSecuritySettings getSecuritySettings() {
		return securitySettings;
	}
	
	@Override
	public DateTimeZone getDefaultTimeZone() {
		return UTC;
	}

	@Override
	public URI getBaseUri() {
		return uri;
	}
	
	protected UriComponentsBuilder initUriBuilder(ClientPlatformSettings platformSettings) {
		return platformSettings.getBaseUriBuilder();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractClientSettings [securitySettings=" + securitySettings
				+ ", uri=" + uri + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((securitySettings == null) ? 0 : securitySettings.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractClientSettings other = (AbstractClientSettings) obj;
		if (securitySettings == null) {
			if (other.securitySettings != null)
				return false;
		} else if (!securitySettings.equals(other.securitySettings))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

}
