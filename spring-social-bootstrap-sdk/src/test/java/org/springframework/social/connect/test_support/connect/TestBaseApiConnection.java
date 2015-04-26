/**
 * 
 */
package org.springframework.social.connect.test_support.connect;

import static org.springframework.social.impl.test_support.TestBaseApi.TEST_API_PROVIDER_ID;

import org.springframework.social.connect.AbstractBaseApiConnection;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.impl.test_support.TestBaseApi;
import org.springframework.social.impl.test_support.impl.TestBaseApiTemplate;

/**
 * Example implementation of {@link AbstractBaseApiConnection}
 * 
 * <p>Represents a link to a {@link TestBaseApi} "user connection", through the concept of instances 
 * of this class having access to the authenticated {@link TestBaseApi} instance, the 
 * {@link TestBaseApiServiceProvider} and {@link TestBaseApiAdapter}. 
 * 
 * <p>It can also create a {@link TestBaseApiConnectionData} serializable instance so that 
 * valid connections can be restored from a store, without going through an authorization step. 
 *
 * @author robin
 */
public class TestBaseApiConnection extends AbstractBaseApiConnection<TestBaseApi> {

	private static final long serialVersionUID = 1L;
	
	private final char[] sessionId;
	private final char[] username;
	
	/**
	 * Creates a new {@link TestBaseApiConnection} from the data provided.
	 * Designed to be called when re-constituting an existing {@link Connection} using {@link ConnectionData}.
	 * 
	 * @param data the data holding the state of this connection
	 * @param apiAdapter the {@link TestBaseApiAdapter} for the {@link TestBaseApiServiceProvider}
	 * @param serviceProvider the {@link TestBaseApiServiceProvider}
	 */
	public TestBaseApiConnection(TestBaseApiConnectionData data, TestBaseApiAdapter apiAdapter, TestBaseApiServiceProvider serviceProvider) {
		super(data, apiAdapter, serviceProvider);
		this.username = data.getProviderUserId().toCharArray();
		this.sessionId = data.getSessionId();
		
		api = initApi();
	}
	
	/**
	 * Creates a new {@link TestBaseApiConnection} from the parameters provided.
	 * 
	 * @param username
	 * @param sessionId
	 * @param apiKey
	 * @param apiAdapter the {@link TestBaseApiAdapter} for the {@link TestBaseApiServiceProvider}
	 * @param serviceProvider the {@link TestBaseApiServiceProvider}
	 */
	public TestBaseApiConnection(char[] username, char[] sessionId, TestBaseApiAdapter apiAdapter, TestBaseApiServiceProvider serviceProvider) {
		super(apiAdapter, serviceProvider);
		this.username = username;
		this.sessionId = sessionId;
		
		api = initApi();
	}
	
	@Override
	public TestBaseApiServiceProvider getServiceProvider() {
		return (TestBaseApiServiceProvider) super.getServiceProvider();
	}
	
	/**
	 * Initializes a {@link TestBaseApiTemplate} instance through the {@link TestBaseApiServiceProvider}, using 
	 * the session ID acquired through the authorization step in the {@link TestBaseApiConnectionFactory}
	 */
	@Override
	protected TestBaseApi initApi() {
		return getServiceProvider().getApi(getServiceProvider().getApiKey(), sessionId);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.support.AbstractConnection#createData()
	 */
	@Override
	public ConnectionData createData() {
		synchronized (getMonitor()) {
			return new TestBaseApiConnectionData(TEST_API_PROVIDER_ID, username, sessionId, getServiceProvider().getApiKey());
		}
	}

}
