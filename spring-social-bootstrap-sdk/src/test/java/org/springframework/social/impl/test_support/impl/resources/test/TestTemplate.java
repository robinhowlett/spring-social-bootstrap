/**
 * 
 */
package org.springframework.social.impl.test_support.impl.resources.test;

import static org.springframework.social.impl.test_support.TestBaseApi.TEST_API_PROVIDER_ID;
import static org.springframework.social.impl.test_support.builders.TestPaginationUriComponentsBuilder.fromUri;
import static org.springframework.util.Assert.isTrue;

import java.io.Serializable;
import java.net.URI;
import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.impl.resources.AbstractBaseApiResourceCRUDTemplate;
import org.springframework.social.impl.test_support.builders.TestPaginationUriComponentsBuilder;
import org.springframework.social.impl.test_support.dto.TestBaseApiResource;
import org.springframework.social.impl.test_support.operations.resources.test.TestOperations;
import org.springframework.social.impl.test_support.settings.TestBaseApiClientSettings;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Example extension of {@link AbstractBaseApiResourceCRUDTemplate} and implemenation of {@link TestOperations}
 * 
 * <p>CRUD, query, page navigation, health check, and query builder support 
 *
 * @author robin
 */
public class TestTemplate extends AbstractBaseApiResourceCRUDTemplate<TestBaseApiResource, Page<TestBaseApiResource>, TestPaginationUriComponentsBuilder> implements TestOperations {
	
	public static final String RESOURCES_PATH = "/test";
	public static final String RESOURCE_ID_PATH = "/{id}";
	public static final String HEALTH_CHECK = "/health-check";
	
	private boolean isAuthorized;	

	public TestTemplate(TestBaseApiClientSettings settings, RestTemplate restTemplate, boolean isAuthorized) {
		super(settings, restTemplate);
		this.isAuthorized = isAuthorized;
	}
	
	@Override
	public TestBaseApiClientSettings getSettings() {
		return (TestBaseApiClientSettings) super.getSettings();
	}
	
	@Override
	public TestPaginationUriComponentsBuilder qb() {
		return fromUri(getSettings().getBaseUri(), this).path(getResourcePath());
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.impl.resources.AbstractBaseApiResourceCRUDTemplate#create(org.springframework.social.dto.BaseApiResource)
	 */
	@Override
	public TestBaseApiResource create(TestBaseApiResource resource) {
		isTrue(isAuthorized, "Must be authorized to create a TestBaseApiResource");
		
		return super.create(resource);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.impl.resources.AbstractBaseApiResourceCRUDTemplate#update(org.springframework.social.dto.BaseApiResource)
	 */
	@Override
	public TestBaseApiResource update(TestBaseApiResource resource) {
		isTrue(isAuthorized, "Must be authorized to update a TestBaseApiResource");
		
		return super.update(resource);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.impl.resources.AbstractBaseApiResourceCRUDTemplate#delete(java.lang.String)
	 */
	@Override
	public TestBaseApiResource delete(String uid) {
		isTrue(isAuthorized, "Must be authorized to delete a TestBaseApiResource");
		
		return super.delete(uid);
	}

	@Override
	public Page<TestBaseApiResource> previousPage(Page<TestBaseApiResource> currentPage) throws OperationNotPermittedException {
		return query(generatePaginationUri(PaginationNavigation.PREVIOUS, currentPage));
	}

	@Override
	public Page<TestBaseApiResource> nextPage(Page<TestBaseApiResource> currentPage) throws OperationNotPermittedException {
		return query(generatePaginationUri(PaginationNavigation.NEXT, currentPage));
	}
	
	@Override
	public HealthCheck healthCheck() {
		Assert.isTrue(isAuthorized);
		
		URI uri = qb().path(HEALTH_CHECK).build().toUri();
		
		ResponseEntity<HealthCheck> result = getRestTemplate().getForEntity(uri, HealthCheck.class);
		return result.getBody();
	}

	@Override
	protected String getResourcePath() {
		return RESOURCES_PATH;
	}

	@Override
	protected String getResourceIdPath() {
		return RESOURCE_ID_PATH;
	}

	@Override
	protected Class<? extends BaseApiResource> getResourceClass() {
		return TestBaseApiResource.class;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends BaseApiPage> getPageClass() {
		return Page.class;
	}

	/**
	 * Generate the URL of the next or previous page
	 * 
	 * @param navigation {@link PaginationNavigation} enum detailing next or previous page
	 * @param lastResult The most recent {@link ResponseEntity} body received; if it is a {@link Page}, then a next or previous url can be generated
	 * @return
	 */
	private TestPaginationUriComponentsBuilder generatePaginationUri(PaginationNavigation navigation, Page<TestBaseApiResource> lastResult) {
		TestPaginationUriComponentsBuilder builder = TestPaginationUriComponentsBuilder.fromUri(
				qb().build().toUri(), 
				this);
		
		if (lastResult != null && Page.class.isAssignableFrom(lastResult.getClass())) {
			Page<TestBaseApiResource> lastPageResult = (Page<TestBaseApiResource>) lastResult;
			
			if (navigation.equals(PaginationNavigation.PREVIOUS) && lastPageResult.getMetadata().isFirstPage()) {
				throw new OperationNotPermittedException(TEST_API_PROVIDER_ID, "Cannot navigate to previous Page because already at first Page");
			} else if (navigation.equals(PaginationNavigation.NEXT) && lastPageResult.getMetadata().isLastPage()) {
				throw new OperationNotPermittedException(TEST_API_PROVIDER_ID, "Cannot navigate to next Page because already at last Page");
			}
			
			long lastPageNumber = lastPageResult.getMetadata().getNumber();
			long nextPageNumber = (navigation.equals(PaginationNavigation.PREVIOUS) ? lastPageNumber - 1 : lastPageNumber + 1);
			
			MultiValueMap<String,String> queryParams = builder.build().getQueryParams();
			MultiValueMap<String,String> newQueryParams = new LinkedMultiValueMap<String, String>(queryParams);
			newQueryParams.put(builder.getPagePrefix() + Page.PAGE, Collections.singletonList(String.valueOf(nextPageNumber)));
			
			return qb().queryParams(newQueryParams);
		} else {
			throw new UnsupportedOperationException("No Page-compatible lastResult found");
		}
	}

	/** 
	 * Enum for indicating navigation choice: the next or previous page 
	 * @author robin
	 */
	private enum PaginationNavigation {
		PREVIOUS, NEXT;
	}
	
	/**
	 * Example DTO for the /healthcheck API endpoint 
	 *
	 * @author robin
	 */
	public static class HealthCheck implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private final Boolean isHealthy;
		
		public HealthCheck(Boolean isHealthy) {
			this.isHealthy = isHealthy;
		}

		/**
		 * @return the status
		 */
		public Boolean getIsHealthy() {
			return isHealthy;
		}
		
	}
	
}