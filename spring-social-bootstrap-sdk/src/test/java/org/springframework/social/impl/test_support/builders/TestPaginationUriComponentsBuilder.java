/**
 * 
 */
package org.springframework.social.impl.test_support.builders;

import java.net.URI;

import org.springframework.social.dto.pagination.Page;
import org.springframework.social.impl.test_support.dto.TestBaseApiResource;
import org.springframework.social.impl.test_support.impl.resources.test.TestTemplate;
import org.springframework.social.impl.test_support.operations.resources.test.TestOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilderWithPagination;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Example extension of {@link BaseApiUriComponentsBuilder} to provider additional 
 * builder methods for 
 *
 * @author robin
 */
public class TestPaginationUriComponentsBuilder extends BaseApiUriComponentsBuilderWithPagination<TestPaginationUriComponentsBuilder> {
	
	public static final String PAGE_PREFIX = "page.";
	
	protected TestPaginationUriComponentsBuilder(TestOperations testTemplate) {
		super(testTemplate, PAGE_PREFIX);
	}
	
	public static TestPaginationUriComponentsBuilder fromUri(URI uri, TestOperations testTemplate) {
		TestPaginationUriComponentsBuilder uriBuilder = new TestPaginationUriComponentsBuilder(testTemplate);
		uriBuilder.uri(uri);
		return uriBuilder;
	}
	
	@Override
	public TestTemplate getResourceTemplate() {
		return (TestTemplate) super.getResourceTemplate();
	}

	public Page<TestBaseApiResource> query() {
		return getResourceTemplate().query(this);
	}
	
}
