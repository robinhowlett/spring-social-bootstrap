/**
 * 
 */
package org.springframework.social.har.converter.jdbc_alf;

import static org.hamcrest.Matchers.equalTo;
import static org.joda.time.DateTimeZone.UTC;
import static org.junit.Assert.assertThat;
import static org.springframework.social.har.dto.mocks.MockHar.alfHarWithLogContainingSingleEntry;
import static org.springframework.social.har.dto.mocks.MockJdbcAlfHarEntries.listContainingSingleJdbcAlfHarEntry;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportslabs.amp.har.dto.alf.har.AlfHar;

/**
 * @author robin
 *
 */
public class ListOfJdbcHarEntriesToAlfHarConverterTest {

	@Test
	public void convert_WithListContainingSingleJdbcAlfHarEntry_ReturnsAlfHarWithSingleAlfHarEntryInAlfHarLog() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setTimeZone(UTC.toTimeZone());
		
		ListOfJdbcHarEntriesToAlfHarConverter converter = new ListOfJdbcHarEntriesToAlfHarConverter(mapper, UTC, "mockbin.com", "1.5.0");
		
		AlfHar alfHar = converter.convert(listContainingSingleJdbcAlfHarEntry());
		
		assertThat(alfHar, equalTo(alfHarWithLogContainingSingleEntry()));
	}
	
}
