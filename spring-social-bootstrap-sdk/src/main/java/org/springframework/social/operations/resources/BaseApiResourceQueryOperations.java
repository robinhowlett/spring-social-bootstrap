package org.springframework.social.operations.resources;

import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.web.util.BaseApiUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Further specialization of {@link BaseApiResourceOperations} that supports querying a resource endpoint, 
 * navigating paginated results, and supporting using a builder pattern in creating a request through {@link UriComponentsBuilder} 
 *
 * @author robin
 * @param <R> A sub-type of {@link BaseApiResource}
 */
public interface BaseApiResourceQueryOperations<R extends BaseApiResource, P extends BaseApiPage<R>, B extends BaseApiUriComponentsBuilder<?>> extends BaseApiResourceOperations<B> {
	
	/**
	 * Query the resource API endpoint
	 * 
	 * @return A {@link BaseApiPage} of {@link BaseApiResource}s
	 */
	public P query();
	
	/**
	 * Query the resource API endpoint with a {@link BaseApiUriComponentsBuilder} that customizes the request URL
	 * 
	 * @param builder A {@link BaseApiUriComponentsBuilder} configuring the request URL
	 * @return A {@link BaseApiPage} of {@link BaseApiResource}s
	 */
	public P query(B builder);
	
	/**
	 * Navigate to the previous page, if any
	 * 
	 * @param currentPage The current {@link BaseApiPage}
	 * @return A {@link BaseApiPage}
	 * @throws OperationNotPermittedException 
	 */
	public P previousPage(P currentPage) throws OperationNotPermittedException;

	/**
	 * Navigate to the next page, if any
	 * 
	 * @return
	 * @throws OperationNotPermittedException
	 */
	public P nextPage(P currentPage) throws OperationNotPermittedException;
	
}
