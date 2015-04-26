/**
 * 
 */
package org.springframework.social.har.converter.jdbc_alf;

import static com.sportslabs.amp.har.dto.Har.SPEC_VERSION;
import static org.joda.time.DateTimeZone.UTC;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.social.har.converter.ListOfHarEntriesDomainToHarDtoConverter;
import org.springframework.social.har.domain.jdbc_alf.JdbcAlfHarEntry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarRequest;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarResponse;
import com.sportslabs.amp.har.dto.alf.har.log.AlfHarLog;
import com.sportslabs.amp.har.dto.creator.HarCreator;
import com.sportslabs.amp.har.dto.entries.HarEntryTimings;

/**
 * Spring Type {@link Converter} abstract class for converting between a List of types of {@link JdbcAlfHarEntry} instances 
 * to a {@link AlfHar} API Log Format
 *
 * @author robin
 */
public class ListOfJdbcHarEntriesToAlfHarConverter extends ListOfHarEntriesDomainToHarDtoConverter<List<JdbcAlfHarEntry>, AlfHar> {
	
	private static final String DEFAULT_CREATOR_NAME = "HAR Mar Interceptor";
	private static final String DEFAULT_CREATOR_VERSION = "1.0.0";

	private static final Logger LOG = getLogger(ListOfJdbcHarEntriesToAlfHarConverter.class);
	
	private final ObjectMapper mapper;
	private final String creatorName;
	private final String creatorVersion;
	private DateTimeZone timeZone;
	
	public ListOfJdbcHarEntriesToAlfHarConverter(final ObjectMapper mapper) {
		this(mapper, UTC);
	}

	public ListOfJdbcHarEntriesToAlfHarConverter(final ObjectMapper mapper, final DateTimeZone timeZone) {
		this(mapper, timeZone, DEFAULT_CREATOR_NAME, DEFAULT_CREATOR_VERSION);
	}
	
	public ListOfJdbcHarEntriesToAlfHarConverter(final ObjectMapper mapper, final DateTimeZone timeZone, final String creatorName, final String creatorVersion) {
		this.mapper = mapper;
		this.timeZone = timeZone;
		this.creatorName = creatorName;
		this.creatorVersion = creatorVersion;
	}

	@Override
	public AlfHar convert(List<JdbcAlfHarEntry> entries) {
		List<AlfHarEntry> harEntries = new ArrayList<AlfHarEntry>(); 
		
		for (JdbcAlfHarEntry entry : entries) {
		
			DateTime startedDateTime	= new DateTime(entry.getTs() * 1000).withZone(timeZone);
			Integer time 				= entry.getRespTime();
			
			AlfHarRequest request 		= null;
			AlfHarResponse response 	= null;
			HarEntryTimings timings 	= null;
			
			try {
				request 				= getMapper().readValue(entry.getRequest(), AlfHarRequest.class);
				timings 				= getMapper().readValue(entry.getTimings(), HarEntryTimings.class);
				
				if (entry.getResponse() != null) {
					response				= getMapper().readValue(entry.getResponse(), AlfHarResponse.class);
				}
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
					
			String serverIPAddress = entry.getServerIP();
			String clientIPAddress = entry.getClientIP();
			
			harEntries.add(new AlfHarEntry(startedDateTime, time, request, response, timings, serverIPAddress, clientIPAddress));
		}
		
		HarCreator creator = new HarCreator(getCreatorName(), getCreatorVersion(), null);
		
		AlfHarLog harLog = new AlfHarLog(SPEC_VERSION, creator, harEntries);
		
		return new AlfHar(harLog);
	}

	/**
	 * @return the mapper
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @return the creatorVersion
	 */
	public String getCreatorVersion() {
		return creatorVersion;
	}

}
