package org.springframework.social.dto.ser;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.SimplePage;

/**
 * Jackson deserializer for a {@link SimplePage} of {@link BaseApiResource}s
 * 
 * @author robin
 */
public class SimplePageDeserializer extends AbstractSimplePageDeserializer<SimplePage<BaseApiResource>> {

	private static final String DEFAULT_CONTENT_ARRAY_FIELD_NAME = "content";

	public SimplePageDeserializer(Class<? extends BaseApiResource> contentClass) {
		this(contentClass, DEFAULT_CONTENT_ARRAY_FIELD_NAME);
	}
	
	public SimplePageDeserializer(Class<? extends BaseApiResource> contentClass, String contentArrayFieldName) {
		super(contentClass, contentArrayFieldName);
	}

	@Override
	protected SimplePage<BaseApiResource> createPage(Iterable<BaseApiResource> content) {
		return new SimplePage<BaseApiResource>(content, getContentArrayFieldName());
	}
    
}