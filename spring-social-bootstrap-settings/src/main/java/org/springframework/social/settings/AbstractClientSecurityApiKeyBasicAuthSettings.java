/**
 * 
 */
package org.springframework.social.settings;

import static org.slf4j.LoggerFactory.getLogger;

import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

/**
 * Implementation of {@link ClientSecuritySettings} that requires an API Key, and a username and password to be 
 * provided. 
 * 
 * <p>The Basic Authorization header value will be created and set.
 * 
 * <p>Requiring preemptive authentication or authorization on read requests can also be configured.
 *
 * @author robin
 */
public abstract class AbstractClientSecurityApiKeyBasicAuthSettings extends AbstractClientSecurityApiKeyUsernamePasswordSettings {

	private static final Logger LOG = getLogger(AbstractClientSecurityApiKeyBasicAuthSettings.class);
	
	/*
	 * Whether to always attempt preemptive authentication by including the authorization credentials 
	 * as an Authorization HTTP Header value in the HTTP request
	 */
	private final PreemptiveAuthentication preemtiveAuthentication;
	private final String authorization;
	
	// Whether reads (GET, HEAD etc.) should require authorization credentials
	private boolean authorizedReadsEnabled;
	
	public AbstractClientSecurityApiKeyBasicAuthSettings(final String apiKey, final char[] username, final char[] password) {
		this(apiKey, username, password, PreemptiveAuthentication.ENABLED);
	}

	public AbstractClientSecurityApiKeyBasicAuthSettings(final String apiKey, final char[] username, final char[] password, 
			final PreemptiveAuthentication preemtiveAuthentication) {
		super(apiKey, username, password);
		
		Assert.notNull(preemtiveAuthentication, "preemptiveAuthentication must not be null");
		this.preemtiveAuthentication = preemtiveAuthentication;
		
		this.authorization = createAuthorizationHeaderValue(username, password);
	}
	
	/**
	 * Create the Basic Authorization value to be used in an HTTP Authorization Header
	 * 
	 * @param username
	 * @param password
	 * @return The Basic Authorization value String
	 */
	private String createAuthorizationHeaderValue(final char[] username, final char[] password) {
		if (username != null && password != null) {
			String sUsername = new String(username);
			String sPassword = new String(password);
			if (!sUsername.isEmpty() && !sPassword.isEmpty()) {
				String authorizationValue = "Basic " + Base64Utils.encodeToString((sUsername + ":" + sPassword).getBytes(Charsets.UTF_8));
				LOG.debug("Authorization value for username {} was {}", sUsername, authorizationValue);
				return authorizationValue;
			}
		}
		return null;
	}
	
	public PreemptiveAuthentication getPreemtiveAuthentication() {
		return preemtiveAuthentication;
	}
	
	public boolean isPreemtiveAuthenticationEnabled() {
		return preemtiveAuthentication == PreemptiveAuthentication.ENABLED;
	}

	public boolean isAuthorizedReadsEnabled() {
		return authorizedReadsEnabled;
	}

	public void setAuthorizedReadsEnabled(boolean authorizedReadsEnabled) {
		this.authorizedReadsEnabled = authorizedReadsEnabled;
	}

	public String getAuthorization() {
		return authorization;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.social.settings.AbstractClientSecurityApiKeyUsernamePasswordSettings#hasCredentials()
	 */
	@Override
	public Boolean hasCredentials() {
		return (getAuthorization() != null && !getAuthorization().isEmpty());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractClientSecurityApiKeyBasicAuthSettings [preemtiveAuthentication="
				+ preemtiveAuthentication
				+ ", authorizedReadsEnabled="
				+ authorizedReadsEnabled
				+ ", toString()="
				+ super.toString()
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authorization == null) ? 0 : authorization.hashCode());
		result = prime * result + (authorizedReadsEnabled ? 1231 : 1237);
		result = prime
				* result
				+ ((preemtiveAuthentication == null) ? 0
						: preemtiveAuthentication.hashCode());
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
		AbstractClientSecurityApiKeyBasicAuthSettings other = (AbstractClientSecurityApiKeyBasicAuthSettings) obj;
		if (authorization == null) {
			if (other.authorization != null)
				return false;
		} else if (!authorization.equals(other.authorization))
			return false;
		if (authorizedReadsEnabled != other.authorizedReadsEnabled)
			return false;
		if (preemtiveAuthentication != other.preemtiveAuthentication)
			return false;
		return true;
	}

	/**
	 * Preemptive authentication (including Authorization credentials without being asked) 
	 * is either enabled or disabled
	 */
	public enum PreemptiveAuthentication {
		ENABLED, DISABLED;
	}
	
	/**
	 * Authorized reads (requiring Authorization credentials for reading resources) 
	 * is either enabled or disabled
	 */
	public enum AuthorizedReads {
		ENABLED, DISABLED;
	}
	
}
