/**
 * 
 */
package org.springframework.social.har.dto.mocks;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.social.har.dto.mocks.MockHarEntries.harEntryTimingsWith1000MsReceive;
import static org.springframework.social.har.dto.mocks.MockHarRequests.getBinRequestWithTenHeaders;
import static org.springframework.social.har.dto.mocks.MockHarResponses.alfHarResponseWithEightHeadersAndHelloWorldHtmlBody;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.har.domain.jdbc_alf.JdbcAlfHarEntry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author robin
 *
 */
public class MockJdbcAlfHarEntries {
	
	public static final String DUMMY_PROVIDER_ID_VALUE = "dummyProviderId";
	public static final String TEST_USER_VALUE = "apiKey_123456";

	public static final JdbcAlfHarEntry jdbcAlfHarEntryWithRequestAndResponse() throws MalformedURLException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		return new JdbcAlfHarEntry(
				TEST_USER_VALUE, 
				DUMMY_PROVIDER_ID_VALUE,
				1404172800L,
				"GET", 
				"http", 
				"mockbin.com", 
				"/bin/3c149e20-bc9c-4c68-8614-048e6023a108", 
				null, 
				null, 
				OK.value(), 
				OK.name(), 
				1000, 
				mapper.writeValueAsString(getBinRequestWithTenHeaders()), 
				mapper.writeValueAsString(alfHarResponseWithEightHeadersAndHelloWorldHtmlBody()), 
				null, 
				null, 
				mapper.writeValueAsString(harEntryTimingsWith1000MsReceive()), 
				null, 
				"40.140.33.170", 
				null, 
				null);
				
	}
	
	public static final List<JdbcAlfHarEntry> listContainingSingleJdbcAlfHarEntry() throws MalformedURLException, JsonProcessingException {
		ArrayList<JdbcAlfHarEntry> entries = new ArrayList<JdbcAlfHarEntry>();
		
		entries.add(jdbcAlfHarEntryWithRequestAndResponse());
		
		return entries;
	}

}
