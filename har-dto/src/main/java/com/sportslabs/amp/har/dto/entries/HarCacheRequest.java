package com.sportslabs.amp.har.dto.entries;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cache entries (either before or after a request)
 *
 * @author robin
 */
public class HarCacheRequest {
	
	private DateTime 	expires;	// optional
	private DateTime	lastAccess;
	private String 		eTag;
	private Integer 	hitCount;
	private String 		comment;	// optional
	
	/**
	 * @param expires		(Optional) Expiration time of the cache entry
	 * @param lastAccess	The last time the cache entry was opened
	 * @param eTag			Etag
	 * @param hitCount		The number of times the cache entry has been opened
	 * @param comment		(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarCacheRequest(
			@JsonProperty("expires") 	DateTime expires, 
			@JsonProperty("lastAccess")	DateTime lastAccess, 
			@JsonProperty("eTag") 		String eTag,
			@JsonProperty("hitCount") 	Integer hitCount, 
			@JsonProperty("comment") 	String comment) {
		super();
		this.expires = expires;
		this.lastAccess = lastAccess;
		this.eTag = eTag;
		this.hitCount = hitCount;
		this.comment = comment;
	}

	/**
	 * @return Expiration time of the cache entry
	 */
	public DateTime getExpires() {
		return expires;
	}

	/**
	 * @return The last time the cache entry was opened
	 */
	public DateTime getLastAccess() {
		return lastAccess;
	}

	/**
	 * @return the eTag
	 */
	public String geteTag() {
		return eTag;
	}

	/**
	 * @return The number of times the cache entry has been opened
	 */
	public Integer getHitCount() {
		return hitCount;
	}

	/**
	 * @return A comment provided by the user or the application
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param expires the expires to set
	 */
	public void setExpires(DateTime expires) {
		this.expires = expires;
	}

	/**
	 * @param lastAccess the lastAccess to set
	 */
	public void setLastAccess(DateTime lastAccess) {
		this.lastAccess = lastAccess;
	}

	/**
	 * @param eTag the eTag to set
	 */
	public void seteTag(String eTag) {
		this.eTag = eTag;
	}

	/**
	 * @param hitCount the hitCount to set
	 */
	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
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
		return "HarCacheRequest [expires=" + expires + ", lastAccess="
				+ lastAccess + ", eTag=" + eTag + ", hitCount=" + hitCount
				+ ", comment=" + comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((eTag == null) ? 0 : eTag.hashCode());
		result = prime * result + ((expires == null) ? 0 : expires.hashCode());
		result = prime * result
				+ ((hitCount == null) ? 0 : hitCount.hashCode());
		result = prime * result
				+ ((lastAccess == null) ? 0 : lastAccess.hashCode());
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
		HarCacheRequest other = (HarCacheRequest) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (eTag == null) {
			if (other.eTag != null)
				return false;
		} else if (!eTag.equals(other.eTag))
			return false;
		if (expires == null) {
			if (other.expires != null)
				return false;
		} else if (!expires.equals(other.expires))
			return false;
		if (hitCount == null) {
			if (other.hitCount != null)
				return false;
		} else if (!hitCount.equals(other.hitCount))
			return false;
		if (lastAccess == null) {
			if (other.lastAccess != null)
				return false;
		} else if (!lastAccess.equals(other.lastAccess))
			return false;
		return true;
	}

}
