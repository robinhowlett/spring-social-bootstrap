/**
 * 
 */
package com.sportslabs.amp.har.dto.entries;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object contains detailed info about performed request
 *
 * @author robin
 */
public class HarRequest {

	private String 				method;
	private URL 				url;
	private String 				httpVersion;
	private List<HarCookie> 	cookies;
	private List<HarHeader> 	headers;
	private List<HarQueryParam> queryString;
	private HarPostData 		postData;		// optional
	private Integer 			headersSize;
	private Integer 			bodySize;
	private String 				comment;		// optional
	
	/**
	 * @param method		Request method (GET, POST, ...)
	 * @param url			Absolute URL of the request (fragments are not included)
	 * @param httpVersion	Request HTTP Version
	 * @param cookies		List of cookie objects
	 * @param headers		List of header objects
	 * @param queryString	List of query parameter objects
	 * @param postData		(Optional) Posted data info
	 * @param headersSize	Total number of bytes from the start of the HTTP request message until (and including) the double 
	 * 						CRLF before the body. Set to -1 if the info is not available
	 * @param bodySize		Size of the request body (POST data payload) in bytes. Set to -1 if the info is not available
	 * @param comment		(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarRequest(
			@JsonProperty("method") 		String method, 
			@JsonProperty("url") 			URL url, 
			@JsonProperty("httpVersion") 	String httpVersion,
			@JsonProperty("cookies") 		List<HarCookie> cookies, 
			@JsonProperty("headers") 		List<HarHeader> headers,
			@JsonProperty("queryString")	List<HarQueryParam> queryString, 
			@JsonProperty("postData") 		HarPostData postData,
			@JsonProperty("headersSize") 	Integer headersSize, 
			@JsonProperty("bodySize") 		Integer bodySize, 
			@JsonProperty("comment") 		String comment) {
		super();
		this.method = method;
		this.url = url;
		this.httpVersion = (httpVersion != null ? httpVersion : "HTTP/1.1");
		this.cookies = (cookies != null ? cookies : Collections.<HarCookie> emptyList());
		this.headers = (headers != null ? headers : Collections.<HarHeader> emptyList());
		this.queryString = queryString;
		this.postData = postData;
		this.headersSize = headersSize;
		this.bodySize = bodySize;
		this.comment = comment;
	}
	
	public HarRequest() {
		this(null, null, null, null, null, null, null, null, null, null);
	}

	/**
	 * @return Request method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @return Absolute URL of the request
	 */
	public URL getUrl() {
		return url;
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
	 * @return List of query parameter objects
	 */
	public List<HarQueryParam> getQueryString() {
		return queryString;
	}

	/**
	 * @return Posted data info
	 */
	public HarPostData getPostData() {
		return postData;
	}

	/**
	 * @return Total number of bytes from the start of the HTTP request message until (and including) the double CRLF before the body
	 */
	public Integer getHeadersSize() {
		return headersSize;
	}

	/**
	 * @return Size of the request body (POST data payload) in bytes
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
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(URL url) {
		this.url = url;
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
	 * @param queryString the queryString to set
	 */
	public void setQueryString(List<HarQueryParam> queryString) {
		this.queryString = queryString;
	}

	/**
	 * @param postData the postData to set
	 */
	public void setPostData(HarPostData postData) {
		this.postData = postData;
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
		return "HarRequest [method=" + method + ", url=" + url
				+ ", httpVersion=" + httpVersion + ", cookies=" + cookies
				+ ", headers=" + headers + ", queryString=" + queryString
				+ ", postData=" + postData + ", headersSize=" + headersSize
				+ ", bodySize=" + bodySize + ", comment=" + comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bodySize == null) ? 0 : bodySize.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((cookies == null) ? 0 : cookies.hashCode());
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result
				+ ((headersSize == null) ? 0 : headersSize.hashCode());
		result = prime * result
				+ ((httpVersion == null) ? 0 : httpVersion.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result
				+ ((postData == null) ? 0 : postData.hashCode());
		result = prime * result
				+ ((queryString == null) ? 0 : queryString.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		HarRequest other = (HarRequest) obj;
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
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (postData == null) {
			if (other.postData != null)
				return false;
		} else if (!postData.equals(other.postData))
			return false;
		if (queryString == null) {
			if (other.queryString != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(queryString, other.queryString))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
