package org.springframework.social.impl.test_support.operations.resources.test;

import org.springframework.social.connect.test_support.connect.TestBaseApiAdapter;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.impl.test_support.builders.TestPaginationUriComponentsBuilder;
import org.springframework.social.impl.test_support.dto.TestBaseApiResource;
import org.springframework.social.impl.test_support.impl.resources.test.TestTemplate.HealthCheck;
import org.springframework.social.operations.resources.BaseApiResourceCRUDOperations;
import org.springframework.web.util.UriComponentsBuilder;

/** 
 * Example extended interface of {@link BaseApiResourceCRUDOperations} with a custom 
 * {@link UriComponentsBuilder}, and query-type operations returning {@link Page}s of 
 * {@link TestBaseApiResource}s
 * 
 * <p>Corresponds to the "/test" resource API endpoint
 * 
 * <p>A {@link HealthCheck} operation is also included for demonstrating Spring Social's 
 * Service Provider 'Connect' Framework with {@link TestBaseApiAdapter}; corresponds to 
 * the "/test/health-check" resource API endpoint
 *
 * @author robin
 */
public interface TestOperations extends BaseApiResourceCRUDOperations<TestBaseApiResource, Page<TestBaseApiResource>, TestPaginationUriComponentsBuilder> {

	HealthCheck healthCheck();
	
}