/**
 * 
 */
package org.springframework.social.har.dto.mocks;

import static org.springframework.social.har.dto.mocks.MockHar.JULY_1_2014_MIDNIGHT_UTC;
import static org.springframework.social.har.dto.mocks.MockHarRequests.getBinRequestWithTenHeaders;
import static org.springframework.social.har.dto.mocks.MockHarResponses.alfHarResponseWithEightHeadersAndHelloWorldHtmlBody;

import java.net.MalformedURLException;

import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.entries.HarEntryTimings;

/**
 * @author robin
 *
 */
public class MockHarEntries {

	public static final AlfHarEntry alfHarEntryWithRequestAndResponse() throws MalformedURLException {
		HarEntryTimings timings = harEntryTimingsWith1000MsReceive();
		
		return new AlfHarEntry(
				JULY_1_2014_MIDNIGHT_UTC, 
				1000, 
				getBinRequestWithTenHeaders(), 
				alfHarResponseWithEightHeadersAndHelloWorldHtmlBody(), 
				timings, 
				null,
				"40.140.33.170");
	}
	
	public static final HarEntryTimings harEntryTimingsWith1000MsReceive() {
		return new HarEntryTimings(null, null, null, 0, 0, 1000, null, null);
	}

}
