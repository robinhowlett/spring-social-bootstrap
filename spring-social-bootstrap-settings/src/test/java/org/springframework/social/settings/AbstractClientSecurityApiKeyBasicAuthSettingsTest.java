package org.springframework.social.settings;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test that the Authorization header value was created and calculated correct for 
 * and implementation of {@link AbstractClientSecurityApiKeyBasicAuthSettings} 
 *
 * @author robin
 */
public class AbstractClientSecurityApiKeyBasicAuthSettingsTest {

	@Test
	public void createBasicAuthSecuritySettings_WithUsernameAndPassword_AuthorizationHeaderValueCalculatedCorrectly() {
		TestSecuritySettings securitySettings = new TestSecuritySettings("testUsername".toCharArray(), "testPassword".toCharArray());
		
		assertThat(securitySettings.getAuthorization(), equalTo("Basic dGVzdFVzZXJuYW1lOnRlc3RQYXNzd29yZA=="));
	}
	
	private class TestSecuritySettings extends AbstractClientSecurityApiKeyBasicAuthSettings {

		public TestSecuritySettings(char[] username, char[] password) {
			super("testApiKeyValue", username, password, PreemptiveAuthentication.ENABLED);
		}
		
	}

}
