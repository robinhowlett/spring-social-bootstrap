package org.springframework.social.har.dto.mocks;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.HttpHeaders.CONNECTION;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.DATE;
import static org.springframework.http.HttpHeaders.EXPIRES;
import static org.springframework.http.HttpHeaders.SERVER;
import static org.springframework.http.HttpHeaders.TRANSFER_ENCODING;

import java.util.ArrayList;
import java.util.List;

import com.sportslabs.amp.har.dto.entries.HarHeader;

public class MockHarHeaders {
	
	public static List<HarHeader> tenHarRequestHeaders(String contentLengthValue) {
		List<HarHeader> headers = new ArrayList<HarHeader>();
		
		HarHeader accept = 				new HarHeader(ACCEPT, 				"text/plain, application/json, application/*+json, */*", null);
		HarHeader contentType = 		new HarHeader(CONTENT_TYPE, 		"text/html; charset=UTF-8", null);
		HarHeader date = 				new HarHeader(DATE, 				"Wed, 21 Jan 2015 23:36:35 GMT", null);
		HarHeader server = 				new HarHeader(SERVER, 				"Apache", null);
		HarHeader transferEncoding =	new HarHeader(TRANSFER_ENCODING,	"chunked", null);
		HarHeader cacheControl = 		new HarHeader(CACHE_CONTROL, 		"max-age=7200", null);
		HarHeader connection = 			new HarHeader(CONNECTION, 			"Keep-Alive", null);
		HarHeader keepAlive = 			new HarHeader("Keep-Alive", 		"timeout=5, max=50", null);
		HarHeader expires = 			new HarHeader(EXPIRES, 				"Thu, 22 Jan 2015 01:36:35 GMT", null);
		HarHeader contentLength = 		new HarHeader(CONTENT_LENGTH, 		contentLengthValue, null);
		
		headers.add(accept);
		headers.add(contentType);
		headers.add(date);
		headers.add(server);
		headers.add(transferEncoding);
		headers.add(cacheControl);
		headers.add(connection);
		headers.add(keepAlive);
		headers.add(expires);
		headers.add(contentLength);
		
		return headers;
	}

}
