/**
 * 
 */
package org.springframework.social.har.dto.mocks;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.social.har.dto.mocks.MockHarHeaders.tenHarRequestHeaders;

import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarResponse;
import com.sportslabs.amp.har.dto.entries.HarContent;

/**
 * @author robin
 *
 */
public class MockHarResponses {
	
	public static final AlfHarResponse alfHarResponseWithEightHeadersAndHelloWorldHtmlBody() {
		return new AlfHarResponse(
				OK.value(), 
				OK.name(), 
				null, 
				tenHarRequestHeaders("20"), 
				helloWorldHtmlContent(), 
				308, 
				20);
	}

	public static HarContent helloWorldHtmlContent() {
		return new HarContent(20, null, "text/html; charset=UTF-8", "<h1>Hello World</h1>", null, null);
	}

}
