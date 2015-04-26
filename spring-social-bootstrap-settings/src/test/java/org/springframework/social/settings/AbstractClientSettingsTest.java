/**
 * 
 */
package org.springframework.social.settings;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test that the base API URI was created correctly when defining a new 
 * {@link ClientPlatformSettings} for a new {@link ClientSettings}.
 *
 * @author robin
 */
public class AbstractClientSettingsTest {

	@Test
	public void createClientPlatformSettings_WithHostname_CreatesCorrectBaseUri() {
		TestClientPlatformSettings platformSettings = new TestClientPlatformSettings("hostname");
		TestClientSettings settings = new TestClientSettings(platformSettings);
		
		assertThat(settings.getBaseUri().toString(), equalTo("http://hostname"));
	}

	@Test
	public void createClientPlatformSettings_WithSchemeAndHostname_CreatesCorrectBaseUri() {
		TestClientPlatformSettings platformSettings = new TestClientPlatformSettings("https", "hostname");
		TestClientSettings settings = new TestClientSettings(platformSettings);
		
		assertThat(settings.getBaseUri().toString(), equalTo("https://hostname"));
	}

	@Test
	public void createClientPlatformSettings_WithSchemeHostnamePortAndBasePath_CreatesCorrectBaseUri() {
		TestClientPlatformSettings platformSettings = new TestClientPlatformSettings("http", "hostname", 1234, "basePath");
		TestClientSettings settings = new TestClientSettings(platformSettings);
		
		assertThat(settings.getBaseUri().toString(), equalTo("http://hostname:1234/basePath"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createClientPlatformSettings_WithNullHostname_ThrowsException() {
		TestClientPlatformSettings platformSettings = new TestClientPlatformSettings(null);
		new TestClientSettings(platformSettings);
	}
	
	private class TestClientPlatformSettings extends AbstractClientPlatformSettings {
		
		public TestClientPlatformSettings(String hostname) {
			super(hostname);
		}
		
		public TestClientPlatformSettings(String scheme, String hostname) {
			super(scheme, hostname);
		}

		public TestClientPlatformSettings(String scheme, String hostname, Integer port, String basePath) {
			super(scheme, hostname, port, basePath);
		}				
		
	}
	
	private class TestClientSettings extends AbstractClientSettings {

		public TestClientSettings(ClientPlatformSettings platformSettings) {
			super(platformSettings);
		}

		@Override
		public String getUserAgent() {
			return "Test Client Settings";
		}		
		
	}

}
