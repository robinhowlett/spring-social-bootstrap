/**
 * 
 */
package org.springframework.social.har.dto.mocks;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.har.dto.mocks.MockHarHeaders.tenHarRequestHeaders;

import java.net.MalformedURLException;
import java.net.URL;

import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarRequest;

/**
 * @author robin
 *
 */
public class MockHarRequests {
	
	public static final AlfHarRequest getBinRequestWithTenHeaders() throws MalformedURLException {
		return new AlfHarRequest(
				GET.name(), 
				new URL("http://mockbin.com/bin/3c149e20-bc9c-4c68-8614-048e6023a108"), 
				null, 
				null, 
				tenHarRequestHeaders("0"),
				null,
				307, 
				0, 
				null);
	}
	
	public static final AlfHarEntry getBinEntryWithRequestWithTenHeaders() throws MalformedURLException {
		return new AlfHarEntry(null, null, getBinRequestWithTenHeaders(), null, null, null, null);
	}

}
