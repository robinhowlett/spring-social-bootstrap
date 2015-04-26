/**
 * 
 */
package org.springframework.social.impl.test_support.settings;

import static org.springframework.social.impl.test_support.settings.TestBaseApiClientSecuritySettings.API_KEY_PARAM;


/**
 * Extension of {@link TestBaseApiClientSettings} to provider default values for testing
 *
 * @author robin
 */
public class DefaultTestBaseApiClientSettings extends TestBaseApiClientSettings {
	
	public static final String TEST_API_KEY = "123456_apiKey";
	
	private static final char[] TEST_USERNAME = "adminUsername".toCharArray();
	private static final char[] TEST_PASSWORD = "adminPassword".toCharArray();

	public DefaultTestBaseApiClientSettings() {
		super(new TestBaseApiClientPlatformSettings(), new TestBaseApiClientSecuritySettings(API_KEY_PARAM, TEST_USERNAME, TEST_PASSWORD));
	}
	
	public DefaultTestBaseApiClientSettings(String apiKey, char[] sessionId) {
		super(new TestBaseApiClientPlatformSettings(), new TestBaseApiClientSecuritySettings(apiKey, sessionId));
	}

}
