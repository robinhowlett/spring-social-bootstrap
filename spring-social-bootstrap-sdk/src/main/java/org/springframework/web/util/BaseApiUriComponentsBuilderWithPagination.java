package org.springframework.web.util;

import static org.springframework.social.dto.pagination.Page.DIRECTION;
import static org.springframework.social.dto.pagination.Page.PAGE;
import static org.springframework.social.dto.pagination.Page.PAGE_SIZE;
import static org.springframework.social.dto.pagination.Page.SORT;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.dto.pagination.Page.PageMetadata.Sort;
import org.springframework.social.operations.resources.BaseApiResourceQueryOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Extension of {@link BaseApiUriComponentsBuilder} to add Spring Data-like 
 * pagination builder-method support
 *   
 * @author robin
 *
 * @param <B> Type of {@link BaseApiUriComponentsBuilderWithPagination}
 */
public class BaseApiUriComponentsBuilderWithPagination<B extends BaseApiUriComponentsBuilderWithPagination<B>> extends BaseApiUriComponentsBuilder<B> {
	
	protected static final String DEFAULT_PAGE_PREFIX = "";
	
	private String pagePrefix;

	protected BaseApiUriComponentsBuilderWithPagination(BaseApiResourceQueryOperations<? extends BaseApiResource, ? extends BaseApiPage<? extends BaseApiResource>, B> resourceTemplate) {
		this(resourceTemplate, DEFAULT_PAGE_PREFIX);
	}
	
	protected BaseApiUriComponentsBuilderWithPagination(BaseApiResourceQueryOperations<? extends BaseApiResource, ? extends BaseApiPage<? extends BaseApiResource>, B> resourceTemplate, String pagePrefix) {
		super(resourceTemplate);
		setPagePrefix(pagePrefix);
	}
	
	/**
	 * @return the pagePrefix
	 */
	public String getPagePrefix() {
		return pagePrefix;
	}

	/**
	 * @param pagePrefix the pagePrefix to set
	 */
	public void setPagePrefix(String pagePrefix) {
		this.pagePrefix = pagePrefix;
	}

	public B withPaging(Integer page, Integer size, String sort, Sort.Direction direction) {
		MultiValueMap<String, String> pageParams = new LinkedMultiValueMap<String, String>();
		if (page != null ) pageParams.add(getPagePrefix() + PAGE, String.valueOf(page));
		if (size != null ) pageParams.add(getPagePrefix() + PAGE_SIZE, String.valueOf(size));
		if (sort != null ) pageParams.add(getPagePrefix() + SORT, sort);
		if (direction != null ) pageParams.add(getPagePrefix() + DIRECTION, direction.toString());

		return queryParams(pageParams);
	}
	
	public B withPageNumber(Integer page) {
		return queryParam(getPagePrefix() + PAGE, String.valueOf(page));
	}

	public B withPageSize(Integer size) {
		return queryParam(getPagePrefix() + PAGE_SIZE, String.valueOf(size));
	}

	public B sortBy(String sort, Sort.Direction direction) {
		MultiValueMap<String, String> pageParams = new LinkedMultiValueMap<String, String>();
		pageParams.add(getPagePrefix() + SORT, sort);
		pageParams.add(getPagePrefix() + DIRECTION, direction.toString());

		return queryParams(pageParams);
	}

}
