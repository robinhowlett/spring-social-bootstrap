/**
 * 
 */
package org.springframework.social.har.converter;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.social.har.domain.HarEntry;

import com.sportslabs.amp.har.dto.Har;

/**
 * Spring Type {@link Converter} abstract class for converting between a List of types of {@link HarEntry} instances 
 * to a {@link Har} HTTP Archive format
 * 
 * @author robin
 */
public abstract class ListOfHarEntriesDomainToHarDtoConverter<LD extends List<? extends HarEntry>, H extends Har> implements Converter<LD, H> {
	
}
