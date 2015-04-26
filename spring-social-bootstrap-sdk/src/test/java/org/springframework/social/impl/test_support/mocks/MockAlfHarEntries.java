package org.springframework.social.impl.test_support.mocks;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.social.impl.test_support.settings.TestBaseApiClientSecuritySettings.API_KEY_PARAM;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarRequest;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarResponse;
import com.sportslabs.amp.har.dto.entries.HarContent;
import com.sportslabs.amp.har.dto.entries.HarEntryTimings;
import com.sportslabs.amp.har.dto.entries.HarHeader;
import com.sportslabs.amp.har.dto.entries.HarQueryParam;

public class MockAlfHarEntries {
	
	public static final AlfHarEntry alfHarEntryWithTimeSensitiveInfoSupplied(DateTime startedDateTime, Integer time) throws MalformedURLException {
		ArrayList<HarHeader> requestHeaders = new ArrayList<HarHeader>();
		HarHeader accept		= new HarHeader(ACCEPT, "application/json, application/xml, application/*+json, text/xml, application/*+xml", null);
		HarHeader contentLength	= new HarHeader(CONTENT_LENGTH, "0", null);
		HarHeader userAgent		= new HarHeader(USER_AGENT, "Test User Agent", null);
		requestHeaders.add(accept);
		requestHeaders.add(contentLength);
		requestHeaders.add(userAgent);
		
		ArrayList<HarQueryParam> queryString = new ArrayList<HarQueryParam>();
		HarQueryParam queryParam = new HarQueryParam(API_KEY_PARAM, "123456_apiKey", null);
		queryString.add(queryParam);
		
		AlfHarRequest request = new AlfHarRequest(
				GET.name(), 
				new URL("http://localhost:8080/api/test/1234?apiKey=123456_apiKey"), 
				null, 
				null, 
				requestHeaders, 
				queryString, 
				134, 
				0, 
				null);
		
		ArrayList<HarHeader> responseHeaders = new ArrayList<HarHeader>();
		HarHeader contentType = new HarHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE, null);
		responseHeaders.add(contentType);
		
		HarContent content = new HarContent(
				85, 
				null, 
				APPLICATION_JSON_VALUE, 
				"{\"id\":\"1234\",\"name\":\"Sample 1\",\"createDate\":\"2014-07-01T00:00:00.000Z\",\"active\":true}", 
				null, 
				null);
		
		AlfHarResponse response = new AlfHarResponse(
				OK.value(), 
				OK.name(), 
				null, 
				responseHeaders, 
				content, 30, 85); 
		
		HarEntryTimings timings = new HarEntryTimings(null, null, null, 0, 0, time, null, null);
		
		return new AlfHarEntry(startedDateTime, time, request, response, timings, null, null);
	}

}
