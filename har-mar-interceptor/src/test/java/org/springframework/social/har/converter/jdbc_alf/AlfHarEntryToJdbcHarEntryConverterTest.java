/**
 * 
 */
package org.springframework.social.har.converter.jdbc_alf;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.social.har.dto.mocks.MockHarEntries.alfHarEntryWithRequestAndResponse;
import static org.springframework.social.har.dto.mocks.MockJdbcAlfHarEntries.DUMMY_PROVIDER_ID_VALUE;
import static org.springframework.social.har.dto.mocks.MockJdbcAlfHarEntries.TEST_USER_VALUE;
import static org.springframework.social.har.dto.mocks.MockJdbcAlfHarEntries.jdbcAlfHarEntryWithRequestAndResponse;

import java.net.MalformedURLException;

import org.junit.Test;
import org.springframework.social.har.domain.jdbc_alf.JdbcAlfHarEntry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author robin
 *
 */
public class AlfHarEntryToJdbcHarEntryConverterTest {

	/**
	 * Test method for {@link org.springframework.social.har.converter.jdbc_alf.AlfHarEntryToJdbcHarEntryConverter#convert(com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry)}.
	 * 
	 * @throws MalformedURLException 
	 * @throws JsonProcessingException 
	 */
	@Test
	public final void convert_WithAlfHarEntry_ReturnsMatchingJdbcAlfHarEntry() throws MalformedURLException, JsonProcessingException {
		AlfHarEntryToJdbcHarEntryConverter converter = new AlfHarEntryToJdbcHarEntryConverter(new ObjectMapper(), TEST_USER_VALUE, DUMMY_PROVIDER_ID_VALUE);
		
		JdbcAlfHarEntry jdbcAlfHarEntry = converter.convert(alfHarEntryWithRequestAndResponse());
		
		assertThat(jdbcAlfHarEntry, equalTo(jdbcAlfHarEntryWithRequestAndResponse()));
	}

}
