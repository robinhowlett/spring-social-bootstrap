/**
 * 
 */
package org.springframework.social.connect;

import org.junit.After;
import org.junit.Before;
import org.springframework.social.connect.test_support.connect.TestBaseApiConnectionFactory;


/**
 * Test direct initialization of the {@link TestBaseApiConnectionFactory} outside of a Spring application context
 *
 * @author robin
 */
public class TestBaseApiConnectionFactoryTest extends AbstractTestBaseApiConnectionFactoryTestBase {
	
	@Before
	public void setUp() {
		connectionFactory = new TestBaseApiConnectionFactory(TEST_API_KEY, TEST_USERNAME, TEST_PASSWORD);
	}
	
	@After
	public void tearDown() {
		connectionFactory = null;
	}
}
