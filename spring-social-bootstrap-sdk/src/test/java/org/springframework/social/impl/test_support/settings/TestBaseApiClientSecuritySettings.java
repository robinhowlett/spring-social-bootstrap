/**
 * 
 */
package org.springframework.social.impl.test_support.settings;

import org.springframework.social.settings.AbstractClientSecurityApiKeyBasicAuthSettings;
import org.springframework.social.settings.AbstractClientSecurityApiKeyUsernamePasswordSettings;

/**
 * Example concrete implementation of {@link AbstractClientSecurityApiKeyBasicAuthSettings}
 *
 * <p>Configures security settings with an API key and Basic Authentication credentials, and to 
 * also store a reference to a session ID returned by the service provider
 * 
 * @author robin
 */
public class TestBaseApiClientSecuritySettings extends AbstractClientSecurityApiKeyUsernamePasswordSettings {
	
	public static final String API_KEY_PARAM = "apiKey";
	
	private final char[] sessionId;
	
	public TestBaseApiClientSecuritySettings(String apiKey) {
		this(apiKey, null);
	}
	
	public TestBaseApiClientSecuritySettings(String apiKey, char[] sessionId) {
		super(apiKey, null, null);
		this.sessionId = sessionId;
	}
	
	public TestBaseApiClientSecuritySettings(String apiKey, char[] username, char[] password) {		
		super(apiKey, username, password);
		this.sessionId = null;
	}

	/**
	 * @return the sessionId
	 */
	public char[] getSessionId() {
		return sessionId;
	}
	
}