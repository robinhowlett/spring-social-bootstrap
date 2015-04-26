/**
 * 
 */
package com.sportslabs.amp.har.dto.alf.har.entries;

import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.entries.HarContent;
import com.sportslabs.amp.har.dto.entries.HarCookie;
import com.sportslabs.amp.har.dto.entries.HarHeader;
import com.sportslabs.amp.har.dto.entries.HarQueryParam;
import com.sportslabs.amp.har.dto.entries.HarRequest;

/**
 * This object contains detailed info about performed request
 *
 * @author robin
 */
public class AlfHarRequest extends HarRequest {

	private final HarContent content;

	/**
	 * @param method		Request method (GET, POST, ...)
	 * @param url			Absolute URL of the request (fragments are not included)
	 * @param httpVersion	Request HTTP Version
	 * @param headers		List of header objects
	 * @param queryString	List of query parameter objects
	 * @param headersSize	Total number of bytes from the start of the HTTP request message until (and including) the double 
	 * 						CRLF before the body. Set to -1 if the info is not available
	 * @param bodySize		Size of the request body (POST data payload) in bytes. Set to -1 if the info is not available
	 * @param content		Details about the request body
	 */
	@JsonCreator
	public AlfHarRequest(
			@JsonProperty("method") 		String method, 
			@JsonProperty("url") 			URL url, 
			@JsonProperty("httpVersion") 	String httpVersion,
			@JsonProperty("cookies") 		List<HarCookie> cookies,
			@JsonProperty("headers") 		List<HarHeader> headers,
			@JsonProperty("queryString")	List<HarQueryParam> queryString,
			@JsonProperty("headersSize") 	Integer headersSize, 
			@JsonProperty("bodySize") 		Integer bodySize,
			@JsonProperty("content") 		HarContent content) {
		super(method, url, httpVersion, cookies, headers, queryString, null, headersSize, bodySize, null);
		this.content = content;
	}
	
	/**
	 * @return Request data
	 */
	public HarContent getContent() {
		return content;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlfRequest [toString()=" + super.toString() + ", content="
				+ content + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlfHarRequest other = (AlfHarRequest) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}
	
}
