/**
 * 
 */
package org.springframework.social.impl.test_support.builders;

import java.net.URI;

import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.shell.test_support.dto.TestBaseApiCliResource;
import org.springframework.social.shell.test_support.operations.resources.TestBaseApiCliResourceCRUDTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;
import org.springframework.web.util.BaseApiUriComponentsBuilderWithPagination;

/**
 * Example extension of {@link BaseApiUriComponentsBuilder} to provider additional 
 * builder methods for 
 *
 * @author robin
 */
public class TestPaginationCliUriComponentsBuilder extends BaseApiUriComponentsBuilderWithPagination<TestPaginationCliUriComponentsBuilder> {
	
	public static final String PAGE_PREFIX = "page.";
	
	protected TestPaginationCliUriComponentsBuilder(TestBaseApiCliResourceCRUDTemplate testTemplate) {
		super(testTemplate, PAGE_PREFIX);
	}
	
	public static TestPaginationCliUriComponentsBuilder fromUri(URI uri, TestBaseApiCliResourceCRUDTemplate testTemplate) {
		TestPaginationCliUriComponentsBuilder uriBuilder = new TestPaginationCliUriComponentsBuilder(testTemplate);
		uriBuilder.uri(uri);
		return uriBuilder;
	}
	
	@Override
	public TestBaseApiCliResourceCRUDTemplate getResourceTemplate() {
		return (TestBaseApiCliResourceCRUDTemplate) super.getResourceTemplate();
	}

	public BaseApiPage<TestBaseApiCliResource> query() {
		return getResourceTemplate().query(this);
	}
	
}
