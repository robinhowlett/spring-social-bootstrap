/**
 * 
 */
package org.springframework.social.connect.test_support.connect;

import org.springframework.social.client.ApiKeyParameterRequestInterceptor;
import org.springframework.social.connect.BaseApiServiceProvider;
import org.springframework.social.impl.test_support.TestBaseApi;
import org.springframework.social.impl.test_support.impl.TestBaseApiTemplate;
import org.springframework.social.impl.test_support.impl.resources.login.LoginTemplate;
import org.springframework.social.operations.resources.auth.AuthOperations;

/**
 * Example implementation of {@link BaseApiServiceProvider} for {@link TestBaseApi}
 * 
 * <p>The {@link #getApi(String, char[])} method can return an authenticated instance of {@link TestBaseApiTemplate} by using the 
 * {@link LoginTemplate} initialized in the {@link #TestBaseApiServiceProvider(String, char[], char[])} constructor 
 * to start the "authorization dance"
 *
 * @author robin
 */
public class TestBaseApiServiceProvider implements BaseApiServiceProvider<TestBaseApi> {
	
	private final String apiKey;
	private final AuthOperations authOperations;
	
	/**
	 * Sets the API key to use with the {@link ApiKeyParameterRequestInterceptor}, and provides the 
	 * username and password for use with {@link LoginTemplate} to acquire a session ID
	 * 
	 * @param apiKey
	 * @param username
	 * @param password
	 */
	public TestBaseApiServiceProvider(String apiKey, char[] username, char[] password) {
		this.apiKey = apiKey;
		authOperations = new LoginTemplate(username, password);
	}
	
	/**
	 * If a session ID has already been acquired, use the
	 * 
	 * @param apiKey 
	 * @param sessionId
	 * @return
	 */
	public TestBaseApi getApi(String apiKey, char[] sessionId) {
		return new TestBaseApiTemplate(apiKey, sessionId);
	}

	@Override
	public AuthOperations getAuthOperations() {
		return authOperations;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

}
