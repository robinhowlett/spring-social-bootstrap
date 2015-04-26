/**
 * 
 */
package com.sportslabs.amp.har.dto.entries;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object contains detailed info about the response
 *
 * @author robin
 */
public class HarResponse {
	
	private Integer 		status;
	private String			statusText;
	private String 			httpVersion;
	private List<HarCookie>	cookies;
	private List<HarHeader> headers;
	private HarContent 		content;
	private String			redirectURL;
	private Integer 		headersSize;
	private Integer 		bodySize;
	private String 			comment;		// optional
	
	/**
	 * @param status		Response status
	 * @param statusText	Response status description
	 * @param httpVersion	Request HTTP Version
	 * @param cookies		List of cookie objects
	 * @param headers		List of header objects
	 * @param content		Details about the response body
	 * @param redirectURL	Redirection target URL from the Location response header
	 * @param headersSize	Total number of bytes from the start of the HTTP response message until (and including) the 
	 * 						double CRLF before the body. Set to -1 if the info is not available
	 * @param bodySize		Size of the received response body in bytes. Set to zero in case of responses coming from the cache (304). 
	 * 						Set to -1 if the info is not available
	 * @param comment		(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarResponse(
			@JsonProperty("status") 		Integer status, 
			@JsonProperty("statusText") 	String statusText, 
			@JsonProperty("httpVersion")	String httpVersion,
			@JsonProperty("cookies") 		List<HarCookie> cookies, 
			@JsonProperty("headers") 		List<HarHeader> headers,
			@JsonProperty("content") 		HarContent content, 
			@JsonProperty("redirectURL") 	String redirectURL,
			@JsonProperty("headersSize") 	Integer headersSize, 
			@JsonProperty("bodySize") 		Integer bodySize, 
			@JsonProperty("comment") 		String comment) {
		super();
		this.status = status;
		this.statusText = statusText;
		this.httpVersion = (httpVersion != null ? httpVersion : "HTTP/1.1");
		this.cookies = (cookies != null ? cookies : Collections.<HarCookie> emptyList());
		this.headers = (headers != null ? headers : Collections.<HarHeader> emptyList());
		this.content = content;
		this.redirectURL = (redirectURL != null ? redirectURL : "");
		this.headersSize = headersSize;
		this.bodySize = bodySize;
		this.comment = comment;
	}
	
	public HarResponse() {
		this(null, null, null, null, null, null, null, null, null, null);
	}

	/**
	 * @return Response status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @return Response status description
	 */
	public String getStatusText() {
		return statusText;
	}

	/**
	 * @return Request HTTP Version
	 */
	public String getHttpVersion() {
		return httpVersion;
	}

	/**
	 * @return List of cookie objects
	 */
	public List<HarCookie> getCookies() {
		return cookies;
	}

	/**
	 * @return List of header objects
	 */
	public List<HarHeader> getHeaders() {
		return headers;
	}

	/**
	 * @return Details about the response body
	 */
	public HarContent getContent() {
		return content;
	}

	/**
	 * @return Redirection target URL from the Location response header
	 */
	public String getRedirectURL() {
		return redirectURL;
	}

	/**
	 * @return Total number of bytes from the start of the HTTP response message until (and including) the double CRLF before the body
	 */
	public Integer getHeadersSize() {
		return headersSize;
	}

	/**
	 * @return Size of the received response body in bytes
	 */
	public Integer getBodySize() {
		return bodySize;
	}

	/**
	 * @return A comment provided by the user or the application
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	/**
	 * @param httpVersion the httpVersion to set
	 */
	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	/**
	 * @param cookies the cookies to set
	 */
	public void setCookies(List<HarCookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(List<HarHeader> headers) {
		this.headers = headers;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(HarContent content) {
		this.content = content;
	}

	/**
	 * @param redirectURL the redirectURL to set
	 */
	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	/**
	 * @param headersSize the headersSize to set
	 */
	public void setHeadersSize(Integer headersSize) {
		this.headersSize = headersSize;
	}

	/**
	 * @param bodySize the bodySize to set
	 */
	public void setBodySize(Integer bodySize) {
		this.bodySize = bodySize;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HarResponse [status=" + status + ", statusText=" + statusText
				+ ", httpVersion=" + httpVersion + ", cookies=" + cookies
				+ ", headers=" + headers + ", content=" + content
				+ ", redirectURL=" + redirectURL + ", headersSize="
				+ headersSize + ", bodySize=" + bodySize + ", comment="
				+ comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bodySize == null) ? 0 : bodySize.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((cookies == null) ? 0 : cookies.hashCode());
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result
				+ ((headersSize == null) ? 0 : headersSize.hashCode());
		result = prime * result
				+ ((httpVersion == null) ? 0 : httpVersion.hashCode());
		result = prime * result
				+ ((redirectURL == null) ? 0 : redirectURL.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((statusText == null) ? 0 : statusText.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HarResponse other = (HarResponse) obj;
		if (bodySize == null) {
			if (other.bodySize != null)
				return false;
		} else if (!bodySize.equals(other.bodySize))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (cookies == null) {
			if (other.cookies != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(cookies, other.cookies))
			return false;
		if (headers == null) {
			if (other.headers != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(headers, other.headers))
			return false;
		if (headersSize == null) {
			if (other.headersSize != null)
				return false;
		} else if (!headersSize.equals(other.headersSize))
			return false;
		if (httpVersion == null) {
			if (other.httpVersion != null)
				return false;
		} else if (!httpVersion.equals(other.httpVersion))
			return false;
		if (redirectURL == null) {
			if (other.redirectURL != null)
				return false;
		} else if (!redirectURL.equals(other.redirectURL))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusText == null) {
			if (other.statusText != null)
				return false;
		} else if (!statusText.equals(other.statusText))
			return false;
		return true;
	}

}
