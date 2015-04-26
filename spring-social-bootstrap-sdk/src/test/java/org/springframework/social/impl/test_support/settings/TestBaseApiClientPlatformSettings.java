/**
 * 
 */
package org.springframework.social.impl.test_support.settings;

import org.springframework.social.settings.AbstractClientPlatformSettings;

/**
 * Example concrete implementation of {@link AbstractClientPlatformSettings} that 
 * defines a base URI of {@link http://localhost:8080/[basePath]}
 *
 * @author robin
 */
public class TestBaseApiClientPlatformSettings extends AbstractClientPlatformSettings {

	public TestBaseApiClientPlatformSettings() throws IllegalArgumentException {
		this("api"); // http://localhost:8080/api
	}
	
	public TestBaseApiClientPlatformSettings(String basePath) throws IllegalArgumentException {
		super("http", "localhost", 8080, basePath);
	}
	
}