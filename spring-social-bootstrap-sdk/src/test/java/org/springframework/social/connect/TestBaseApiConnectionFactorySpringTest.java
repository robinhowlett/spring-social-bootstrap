/**
 * 
 */
package org.springframework.social.connect;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.test_support.connect.TestBaseApiConnectionFactory;
import org.springframework.social.impl.test_support.TestBaseApi;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test registering the {@link TestBaseApiConnectionFactory} with a {@link ConnectionFactoryRegistry} and 
 * acquiring the {@link TestBaseApiConnectionFactory} through a {@link ConnectionFactoryLocator}
 * 
 * @author robin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestBaseApiConnectionFactorySpringTest extends AbstractTestBaseApiConnectionFactoryTestBase {
	
	@Before
	public void setUp() {
		connectionFactory = (TestBaseApiConnectionFactory) connectionFactoryLocator.getConnectionFactory(TestBaseApi.class);
	}
	
	@After
	public void tearDown() {
		connectionFactoryLocator = null;
		connectionFactory = null;
	}
	
	@Configuration
	static class Config {
		
		@Bean
	    public ConnectionFactoryLocator connectionFactoryLocator() {
	        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
	        
	        registry.addConnectionFactory(
	        		new TestBaseApiConnectionFactory(TEST_API_KEY, TEST_USERNAME, TEST_PASSWORD));
	            
	        return registry;
	    }

	}

}
