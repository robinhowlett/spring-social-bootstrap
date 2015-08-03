/**
 * 
 */
package org.springframework.social.shell.converters.json;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.Assert.notNull;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.converters.CliPrinterTypeConverter;
import org.springframework.social.dto.BaseApiResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link CliPrinterTypeConverter} for printing a {@link BaseApiResource} in JSON
 *
 * @author robin
 */
public class JsonCliPrinterTypeConverter<S extends BaseApiResource> implements CliPrinterTypeConverter<S> {
	
	private static final Logger LOG = getLogger(JsonCliPrinterTypeConverter.class); 
	
	private final ObjectMapper mapper;

	@Autowired
	public JsonCliPrinterTypeConverter(ObjectMapper mapper) {
		notNull(mapper);
		
		this.mapper = mapper;
	}

	@Override
	public String convert(S resource) {
		try {
			return getMapper().writeValueAsString(resource);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
		}
		
		return null;
	}
	
	public ObjectMapper getMapper() {
		return mapper;
	}

	@Override
	public void setParameters(String parameters) { }
}
