package org.springframework.social.impl.test_support;

import org.springframework.social.BaseApi;
import org.springframework.social.har.Archivable;
import org.springframework.social.har.Replayable;
import org.springframework.social.har.client.jdbc_alf.JdbcAlfHarInterceptor;
import org.springframework.social.har.impl.alf.ReplayAlfHarTemplate;
import org.springframework.social.har.impl.jdbc_alf.JdbcAlfHarTemplate;
import org.springframework.social.har.service.jdbc_alf.JdbcAlfHarServiceImpl;
import org.springframework.social.impl.test_support.impl.TestBaseApiTemplate;
import org.springframework.social.impl.test_support.operations.resources.test.TestOperations;
import org.springframework.social.impl.test_support.settings.TestBaseApiClientSettings;

/**
 * Example base interface for a provider  
 *
 * @author robin
 */
public interface TestBaseApi extends 
			BaseApi, 
			Archivable<JdbcAlfHarTemplate, JdbcAlfHarInterceptor, JdbcAlfHarServiceImpl>, 
			Replayable<ReplayAlfHarTemplate> {
	
	public static final String TEST_API_PROVIDER_ID = "test";
	
	/**
	 * Allows ability to reset {@link TestBaseApiClientSettings} at runtime; here for demonstration purposes
	 */
	public TestBaseApiTemplate configureSettings(TestBaseApiClientSettings settings);
	
	/**
	 * The "/test" resource API endpoint
	 */
	public TestOperations testOperations();
	
}