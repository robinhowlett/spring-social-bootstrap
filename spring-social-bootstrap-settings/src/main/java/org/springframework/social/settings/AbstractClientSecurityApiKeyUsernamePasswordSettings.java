/**
 * 
 */
package org.springframework.social.settings;


/**
 * Extends {@link AbstractClientSecurityApiKeySettings} to also require a username and password
 *
 * @author robin
 */
public abstract class AbstractClientSecurityApiKeyUsernamePasswordSettings extends AbstractClientSecurityApiKeySettings {
	
	private final char[] username;
	private final char[] password;

	public AbstractClientSecurityApiKeyUsernamePasswordSettings(final String apiKey, final char[] username, final char[] password) {
		super(apiKey);
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public char[] getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public char[] getPassword() {
		return password;
	}
	
	/**
	 * @return Boolean representing whether a username and password has been provided
	 */
	public Boolean hasCredentials() {
		return (username != null && password != null);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	@SuppressWarnings("all")
	public String toString() {
		return "AbstractClientSecurityApiKeyUsernamePasswordSettings [username="
				+ username + ", password=" + password + "]";
	}
	
}
