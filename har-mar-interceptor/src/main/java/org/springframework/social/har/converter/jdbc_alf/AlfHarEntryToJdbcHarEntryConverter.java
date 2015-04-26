/**
 * 
 */
package org.springframework.social.har.converter.jdbc_alf;

import static org.slf4j.LoggerFactory.getLogger;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.social.har.converter.HarEntryDtoToHarEntryDomainConverter;
import org.springframework.social.har.domain.jdbc_alf.JdbcAlfHarEntry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;

/**
 * Spring Type {@link Converter} for converting between types of {@link AlfHarEntry} instances 
 * to {@link JdbcAlfHarEntry} Domain models
 *
 * @author robin
 */
public class AlfHarEntryToJdbcHarEntryConverter extends HarEntryDtoToHarEntryDomainConverter<AlfHarEntry, JdbcAlfHarEntry> {
	
	private static final Logger LOG = getLogger(AlfHarEntryToJdbcHarEntryConverter.class);
	
	private final ObjectMapper mapper;
	private final String user;
	private final String provider;
	
	public AlfHarEntryToJdbcHarEntryConverter(final ObjectMapper mapper, final String user, final String provider) {
		this.mapper = mapper;
		this.user = user;
		this.provider = provider;
	}

	@Override
	public JdbcAlfHarEntry convert(AlfHarEntry dtoHarEntry) {
		Long ts 				= (dtoHarEntry.getStartedDateTime().getMillis() / 1000);
		String method 			= dtoHarEntry.getRequest().getMethod();
		
		Integer respTime 		= dtoHarEntry.getTime();
		
		try {
			URI uri 			= dtoHarEntry.getRequest().getUrl().toURI();
			
			String pageref 		= dtoHarEntry.getPageref();
			
			String request 		= storeAsJson(dtoHarEntry.getRequest());
			
			Integer respCode 	= null;
			String respReason 	= null;
			String response 	= null;
			if (dtoHarEntry.getResponse() != null) {
				respCode 		= dtoHarEntry.getResponse().getStatus();
				respReason 		= dtoHarEntry.getResponse().getStatusText();				
				response 		= storeAsJson(dtoHarEntry.getResponse());
			}
			
			String cache 		= storeAsJson(dtoHarEntry.getCache());
			String timings 		= storeAsJson(dtoHarEntry.getTimings());
			
			String serverIP 	= dtoHarEntry.getServerIPAddress();
			String clientIP 	= dtoHarEntry.getClientIPAddress();
			String connection	= dtoHarEntry.getConnection();
			String comment 		= dtoHarEntry.getComment();
			
			return new JdbcAlfHarEntry(user, provider, ts, 
					method, uri.getScheme(), uri.getHost(), uri.getPath(), (uri.getPort() != -1 ? uri.getPort() : null), uri.getQuery(), 
					respCode, respReason, respTime, 
					request, response, pageref, cache, timings, 
					serverIP, clientIP, connection, comment);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
		} catch (URISyntaxException e) {
			LOG.error(e.getMessage());
		}
		
		return null;
	}

	/**
	 * The complex HAR objects are serialized as JSON and saved to the DB
	 */
	private String storeAsJson(Object harObject) throws JsonProcessingException {
		if (harObject != null) {			
			return getMapper().writeValueAsString(harObject);
		} else {
			return null;
		}
	}

	/**
	 * @return the mapper
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

}
