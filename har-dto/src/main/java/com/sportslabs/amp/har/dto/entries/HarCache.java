/**
 * 
 */
package com.sportslabs.amp.har.dto.entries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object contains info about a request coming from browser/client cache
 * 
 * @author robin
 */
public class HarCache {
	
	private HarCacheRequest beforeRequest;	// optional
	private HarCacheRequest	afterRequest;	// optional
	private String 			comment;		// optional
	
	/**
	 * @param beforeRequest	(Optional) State of a cache entry before the request. Null if no cache entry.
	 * 						Leave out this field if the information is not available
	 * @param afterRequest	(Optional) State of a cache entry after the request. Null if no cache entry.
	 * 						Leave out this field if the information is not available
	 * @param comment		(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarCache(
			@JsonProperty("beforeRequest")	HarCacheRequest beforeRequest,
			@JsonProperty("afterRequest") 	HarCacheRequest afterRequest, 
			@JsonProperty("comment") 		String comment) {
		super();
		this.beforeRequest = beforeRequest;
		this.afterRequest = afterRequest;
		this.comment = comment;
	}

	/**
	 * @return State of a cache entry before the request
	 */
	public HarCacheRequest getBeforeRequest() {
		return beforeRequest;
	}

	/**
	 * @return State of a cache entry after the request
	 */
	public HarCacheRequest getAfterRequest() {
		return afterRequest;
	}

	/**
	 * @return A comment provided by the user or the application
	 */
	public String getComment() {
		return comment;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HarCache [beforeRequest=" + beforeRequest + ", afterRequest="
				+ afterRequest + ", comment=" + comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((afterRequest == null) ? 0 : afterRequest.hashCode());
		result = prime * result
				+ ((beforeRequest == null) ? 0 : beforeRequest.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
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
		HarCache other = (HarCache) obj;
		if (afterRequest == null) {
			if (other.afterRequest != null)
				return false;
		} else if (!afterRequest.equals(other.afterRequest))
			return false;
		if (beforeRequest == null) {
			if (other.beforeRequest != null)
				return false;
		} else if (!beforeRequest.equals(other.beforeRequest))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		return true;
	}

}
