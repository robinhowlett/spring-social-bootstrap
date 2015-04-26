/**
 * 
 */
package com.sportslabs.amp.har.dto.pages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Detailed timing info about page load
 * 
 * @author robin
 */
public class HarPageTimings {
	
	private Integer	onContentLoad;
	private Integer onLoad;
	private String 	comment;
	
	/**
	 * @param onContentLoad	(Optional) Content of the page loaded. Number of milliseconds since page load 
	 * 						started (page.startedDateTime). Use -1 if the timing does not apply to the current request.
	 * @param onLoad		(Optional) Page is loaded (onLoad event fired). Number of milliseconds since page load 
	 * 						started (page.startedDateTime). Use -1 if the timing does not apply to the current request.
	 * @param comment		(Optional) A comment provided by the user or the application.
	 */
	@JsonCreator
	public HarPageTimings(
			@JsonProperty("onContentLoad")	Integer onContentLoad, 
			@JsonProperty("onLoad") 		Integer onLoad, 
			@JsonProperty("comment") 		String comment) {
		super();
		this.onContentLoad = onContentLoad;
		this.onLoad = onLoad;
		this.comment = comment;
	}

	/**
	 * @return Number of milliseconds since page load started, or -1 if the timing does not apply to the current request
	 */
	public Integer getOnContentLoad() {
		return onContentLoad;
	}

	/**
	 * @return Number of milliseconds since page load, or -1 if the timing does not apply to the current request
	 */
	public Integer getOnLoad() {
		return onLoad;
	}

	/**
	 * @return A comment provided by the user or the application.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param onContentLoad the onContentLoad to set
	 */
	public void setOnContentLoad(Integer onContentLoad) {
		this.onContentLoad = onContentLoad;
	}

	/**
	 * @param onLoad the onLoad to set
	 */
	public void setOnLoad(Integer onLoad) {
		this.onLoad = onLoad;
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
		return "HarPageTimings [onContentLoad=" + onContentLoad + ", onLoad="
				+ onLoad + ", comment=" + comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((onContentLoad == null) ? 0 : onContentLoad.hashCode());
		result = prime * result + ((onLoad == null) ? 0 : onLoad.hashCode());
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
		HarPageTimings other = (HarPageTimings) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (onContentLoad == null) {
			if (other.onContentLoad != null)
				return false;
		} else if (!onContentLoad.equals(other.onContentLoad))
			return false;
		if (onLoad == null) {
			if (other.onLoad != null)
				return false;
		} else if (!onLoad.equals(other.onLoad))
			return false;
		return true;
	}

}
