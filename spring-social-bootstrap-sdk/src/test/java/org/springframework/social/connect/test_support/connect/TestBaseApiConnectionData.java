/**
 * 
 */
package org.springframework.social.connect.test_support.connect;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.impl.test_support.TestBaseApi;

/**
 * Example extension of {@link ConnectionData} for the {@link TestBaseApi} service provider.
 *
 * <p>A serializable instance that may store valid connections, so they may be restored without 
 * going through an authorization step.
 *
 * @author robin
 */
public class TestBaseApiConnectionData extends ConnectionData {
	
	private static final long serialVersionUID = 1L;
	
	private final char[] sessionId;
	private final String apiKey;

	public TestBaseApiConnectionData(String providerId, char[] username, char[] sessionId, String apiKey) {
		super(providerId, new String(username), null, null, null, null, null, null, null);
		this.sessionId = sessionId;
		this.apiKey = apiKey;
	}

	/**
	 * @return the sessionId
	 */
	public char[] getSessionId() {
		return sessionId;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

}
