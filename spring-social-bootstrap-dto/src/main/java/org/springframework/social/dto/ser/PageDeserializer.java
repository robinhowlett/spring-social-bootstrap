package org.springframework.social.dto.ser;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.pagination.Page.PageMetadata;

/**
 * Jackson deserializer for a {@link Page} of {@link BaseApiResource}s
 * 
 * @author robin
 */
public class PageDeserializer extends AbstractPageDeserializer<Page<? extends BaseApiResource>, BaseApiResource> {

	public PageDeserializer() {
		super(BaseApiResource.class);
	}
	
	public PageDeserializer(Class<? extends BaseApiResource> resourceClass) {
		super(resourceClass);
	}

	@Override
	protected Page<? extends BaseApiResource> createPage(Iterable<BaseApiResource> content, PageMetadata metadata) {
		return new Page<BaseApiResource>(content, metadata);
	}
    
}