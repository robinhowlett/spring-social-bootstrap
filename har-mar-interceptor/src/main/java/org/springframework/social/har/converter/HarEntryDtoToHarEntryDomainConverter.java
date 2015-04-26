/**
 * 
 */
package org.springframework.social.har.converter;

import org.springframework.core.convert.converter.Converter;

import com.sportslabs.amp.har.dto.entries.HarEntry;

/**
 * Spring Type {@link Converter} abstract class for converting between types of {@link HarEntry} instances 
 * to {@link org.springframework.social.har.domain.HarEntry} Domain models
 * 
 * @author robin
 */
public abstract class HarEntryDtoToHarEntryDomainConverter<S extends HarEntry, T extends org.springframework.social.har.domain.HarEntry> implements Converter<S, T> {
	
}
