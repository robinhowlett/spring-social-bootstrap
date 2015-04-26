/**
 * 
 */
package org.springframework.social.impl.test_support.settings;

import org.springframework.social.settings.AbstractClientSettings;

/**
 * Example concrete implementation of {@link AbstractClientSettings}
 * 
 * <p>Initialized with instances of {@link TestBaseApiClientPlatformSettings} and 
 * {@link TestBaseApiClientSecuritySettings}, and also defines an HTTP User-Agent 
 * header value
 *
 * @author robin
 */
public class TestBaseApiClientSettings extends AbstractClientSettings {

	public static final String TEST_CLIENT_USER_AGENT = "Test User Agent";

	public TestBaseApiClientSettings(TestBaseApiClientPlatformSettings platformSettings, TestBaseApiClientSecuritySettings securitySettings) {
		super(platformSettings, securitySettings);
	}
	
	@Override
	public TestBaseApiClientSecuritySettings getSecuritySettings() {
		return (TestBaseApiClientSecuritySettings) super.getSecuritySettings();
	}

	@Override
	public String getUserAgent() {
		return TEST_CLIENT_USER_AGENT;
	}
	
}
