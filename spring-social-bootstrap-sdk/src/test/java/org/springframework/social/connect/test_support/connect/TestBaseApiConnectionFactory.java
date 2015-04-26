/**
 * 
 */
package org.springframework.social.connect.test_support.connect;

import static org.springframework.social.impl.test_support.TestBaseApi.TEST_API_PROVIDER_ID;

import org.springframework.social.connect.AbstractBaseApiConnection;
import org.springframework.social.connect.AbstractBaseApiConnectionFactory;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.TestBaseApiConnectionFactorySpringTest;
import org.springframework.social.connect.TestBaseApiConnectionFactoryTest;
import org.springframework.social.impl.test_support.TestBaseApi;

/**
 * Example extension of {@link AbstractBaseApiConnectionFactory}, establishing a 
 * {@link TestBaseApiConnection} to the {@link TestBaseApi} provider API.
 * 
 * <ul>
 * 	<li>Direct usage example: {@link TestBaseApiConnectionFactoryTest#setUp()}
 * 	<li>Registry usage example: {@link TestBaseApiConnectionFactorySpringTest}
 * </ul> 
 *
 * @author robin
 */
public class TestBaseApiConnectionFactory extends AbstractBaseApiConnectionFactory<TestBaseApi> {

	public TestBaseApiConnectionFactory(String apiKey) {
		this(apiKey, null, null);
	}
	
	public TestBaseApiConnectionFactory(String apiKey, char[] username, char[] password) {
		super(TEST_API_PROVIDER_ID, new TestBaseApiServiceProvider(apiKey, username, password), new TestBaseApiAdapter());
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionFactory#createConnection(org.springframework.social.connect.ConnectionData)
	 */
	@Override
	public AbstractBaseApiConnection<TestBaseApi> createConnection(ConnectionData data) {
		return new TestBaseApiConnection((TestBaseApiConnectionData) data, getApiAdapter(), getServiceProvider());
	}
	
	public TestBaseApiConnection createConnection(char[] username, char[] sessionId) {
		return new TestBaseApiConnection(username, sessionId, getApiAdapter(), getServiceProvider());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.social.connect.AbstractBaseApiConnectionFactory#getApiAdapter()
	 */
	@Override
	protected TestBaseApiAdapter getApiAdapter() {
		return (TestBaseApiAdapter) super.getApiAdapter();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.social.connect.AbstractBaseApiConnectionFactory#getServiceProvider()
	 */
	@Override
	protected TestBaseApiServiceProvider getServiceProvider() {
		return (TestBaseApiServiceProvider) super.getServiceProvider();
	}

}
