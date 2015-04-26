/**
 * 
 */
package org.springframework.social.data.converters.pagination;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;

/**
 * Convert a Spring Data {@link Page} to a Spring Social Bootstrap {@link BaseApiPage}
 * 
 * @author robin
 */
public abstract class AbstractSpringDataPageToBaseApiPageConverter<S extends Object, T extends BaseApiResource> implements Converter<Page<? extends S>, BaseApiPage<T>> {
	
	private static final Logger LOG = getLogger(AbstractSpringDataPageToBaseApiPageConverter.class);
	
	private final Converter<S, T> contentConverter;

	public AbstractSpringDataPageToBaseApiPageConverter(Converter<S, T> converter) {
		this.contentConverter = converter;
	}

	@Override
	public BaseApiPage<T> convert(Page<? extends S> page) {
		if (page != null) {
			List<T> content = new ArrayList<T>();
			
			for (S source : page.getContent()) {
				try {
					T target = getContentConverter().convert(source);
					
					content.add(target);
				} catch (IllegalArgumentException e) {
					LOG.warn("Could not convert source object {} with error {}", source, e);
				}
			}
			
			return createPage(content, createPageMetadata(page));
		}
		
		return createPage();
	}
	
	protected abstract BaseApiPage<T> createPage();
	protected abstract BaseApiPage<T> createPage(final Iterable<T> content, final Object metadata);
	protected abstract Object createPageMetadata(Page<? extends S> page);

	/**
	 * @return the contentConverter
	 */
	public Converter<S, T> getContentConverter() {
		return contentConverter;
	}

}
