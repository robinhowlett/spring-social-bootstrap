/**
 * 
 */
package com.sportslabs.amp.har.dto.alf.har.entries;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.entries.HarContent;
import com.sportslabs.amp.har.dto.entries.HarHeader;
import com.sportslabs.amp.har.dto.entries.HarResponse;

/**
 * This object contains detailed info about the response
 *
 * @author robin
 */
public class AlfHarResponse extends HarResponse {
	
	/**
	 * @param status		Response status
	 * @param statusText	Response status description
	 * @param httpVersion	Request HTTP Version
	 * @param headers		List of header objects
	 * @param content		Details about the response body
	 * @param headersSize	Total number of bytes from the start of the HTTP response message until (and including) the 
	 * 						double CRLF before the body. Set to -1 if the info is not available
	 * @param bodySize		Size of the received response body in bytes. Set to zero in case of responses coming from the cache (304). 
	 * 						Set to -1 if the info is not available
	 */
	@JsonCreator
	public AlfHarResponse(
			@JsonProperty("status") 		Integer status, 
			@JsonProperty("statusText") 	String statusText, 
			@JsonProperty("httpVersion")	String httpVersion, 
			@JsonProperty("headers") 		List<HarHeader> headers,
			@JsonProperty("content") 		HarContent content, 
			@JsonProperty("headersSize") 	Integer headersSize, 
			@JsonProperty("bodySize") 		Integer bodySize) {
		super(status, statusText, httpVersion, null, headers, content, null, headersSize, bodySize, null);
	}
	
	public AlfHarResponse() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlfHarResponse [toString()=" + super.toString() + "]";
	}

}
