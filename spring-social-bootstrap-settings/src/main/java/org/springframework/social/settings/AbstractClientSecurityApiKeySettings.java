/**
 * 
 */
package org.springframework.social.settings;

import org.springframework.util.Assert;

/**
 * Implementation of {@link ClientSecuritySettings} that requires an API Key to be provided
 *
 * @author robin
 */
public abstract class AbstractClientSecurityApiKeySettings implements ClientSecuritySettings {
	
	private final String apiKey;
	
	public AbstractClientSecurityApiKeySettings(String apiKey) {
		Assert.notNull(apiKey, "apiKey must not be null");
		this.apiKey = apiKey;		
	}

	public String getApiKey() {
		return apiKey;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractClientSecurityApiKeySettings [apiKey=" + apiKey + "]";
	}

}
