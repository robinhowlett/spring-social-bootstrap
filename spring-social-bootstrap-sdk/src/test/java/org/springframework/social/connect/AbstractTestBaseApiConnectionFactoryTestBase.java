/**
 * 
 */
package org.springframework.social.connect;

import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.social.impl.test_support.client.SessionIdRequestInterceptor.SESSION_ID_KEY;
import static org.springframework.social.impl.test_support.impl.resources.login.LoginTemplate.FAKE_SESSION_ID;
import static org.springframework.social.impl.test_support.settings.TestBaseApiClientSecuritySettings.API_KEY_PARAM;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.test_support.connect.TestBaseApiConnection;
import org.springframework.social.connect.test_support.connect.TestBaseApiConnectionFactory;
import org.springframework.social.impl.test_support.impl.resources.test.TestTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Unit tests for {@link TestBaseApiConnectionFactory}
 * 
 * @see TestBaseApiConnectionFactorySpringTest
 * @see TestBaseApiConnectionFactoryTest
 *
 * @author robin
 */
public abstract class AbstractTestBaseApiConnectionFactoryTestBase {

	protected static final String TEST_API_KEY = "123456_apiKey";
	protected static final char[] TEST_USERNAME = "amvUsername".toCharArray();
	protected static final char[] TEST_PASSWORD = "amvPassword".toCharArray();
	
	@Autowired
	protected ConnectionFactoryLocator connectionFactoryLocator;

	protected TestBaseApiConnectionFactory connectionFactory;
	
	protected UriComponentsBuilder uriBuilder() {
		return UriComponentsBuilder.fromUriString("http://localhost:8080/api/test/health-check")
				.queryParam(API_KEY_PARAM, TEST_API_KEY)
				.queryParam(SESSION_ID_KEY, new String(FAKE_SESSION_ID));
	}
	
	/**
	 * Test that the connection factory that was configured in the respective {@code setUp()} method 
	 * can establish a valid connection
	 * 
	 * @see TestBaseApiConnectionFactorySpringTest#setUp()
	 * @see TestBaseApiConnectionFactoryTest#setUp()
	 */
	@Test
	public void connect_withValidAuthorizedSession_healthCheckRespondsAsHealthy() throws Exception {
		// Authorize the user; the authorization request will be faked in LoginTemplate
		char[] sessionId = (char[]) connectionFactory.getAuthOperations().authorize();
		
		// Create the connection to the TestBaseApi with the acquired session ID for this user
		TestBaseApiConnection connection = connectionFactory.createConnection(TEST_USERNAME, sessionId);
		
		// Mock the request to the "/test/health-check" resource API endpoint
		MockRestServiceServer mockServer = createServer(connection.getApi().getRestTemplate());
		TestTemplate.HealthCheck healthCheck = new TestTemplate.HealthCheck(Boolean.TRUE);
		mockServer.expect(
				requestTo(uriBuilder().build().toUri()))
					.andRespond(withSuccess(healthCheck.getIsHealthy().toString(), APPLICATION_JSON));
		
		// Confirm that the test connection reported as being healthy
		Boolean isValidConnection = connection.test();
		assertTrue("The connection was not able to successfully connect", isValidConnection);
	}
	
}
