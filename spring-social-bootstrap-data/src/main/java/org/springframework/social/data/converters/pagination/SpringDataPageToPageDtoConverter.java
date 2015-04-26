/**
 * 
 */
package org.springframework.social.data.converters.pagination;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.pagination.Page.PageMetadata;
import org.springframework.social.dto.pagination.Page.PageMetadata.Sort;

/**
 * Convert a Spring Data {@link org.springframework.data.domain.Page} of {@link Object}s to 
 * a Spring Social Bootstrap {@link Page} of {@link BaseApiResource}s
 * 
 * @author robin
 */
public class SpringDataPageToPageDtoConverter<S extends Object, T extends BaseApiResource> extends AbstractSpringDataPageToBaseApiPageConverter<S, T> {
	
	private static final List<Sort> NO_SORT_CONVERSION_SUPPORTED = null;
	
	/**
	 * @param converter The converter to convert the {@link org.springframework.data.domain.Page} contents to 
	 * the {@link Page} contents
	 */
	public SpringDataPageToPageDtoConverter(Converter<S, T> converter) {
		super(converter);
	}

	@Override
	protected Page<T> createPage() {
		return new Page<T>();
	}

	@Override
	protected Page<T> createPage(Iterable<T> content, Object metadata) {
		return new Page<T>(content, (PageMetadata) metadata);
	}
	
	@Override
	protected PageMetadata createPageMetadata(org.springframework.data.domain.Page<? extends S> page) {
		return new PageMetadata(
				(long) page.getNumber(), 
				(long) page.getNumberOfElements(), 
				(long) page.getSize(), 
				NO_SORT_CONVERSION_SUPPORTED, 
				(long) page.getTotalElements(), 
				(long) page.getTotalPages());
	}
	
	@Override
	public Page<T> convert(org.springframework.data.domain.Page<? extends S> page) {
		return (Page<T>) super.convert(page);
	}

}
