package com.sportslabs.amp.har.dto.entries;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object describes posted data, if any (embedded in {@link HarRequest} object). 
 *
 * @author robin
 */
public class HarPostData {
	
	private String 					mimeType;
	private List<HarPostDataParam> 	params;
	private String 					text;
	private String 					comment;	// optional
	
	/**
	 * @param mimeType 	Mime type of posted data
	 * @param params	List of posted parameters (in case of URL encoded parameters)
	 * @param text		Plain text posted data
	 * @param comment	(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarPostData(
			@JsonProperty("mimeType")	String mimeType, 
			@JsonProperty("params") 	List<HarPostDataParam> params,
			@JsonProperty("text") 		String text, 
			@JsonProperty("comment") 	String comment) {
		super();
		this.mimeType = mimeType;
		this.params = params;
		this.text = text;
		this.comment = comment;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @return the params
	 */
	public List<HarPostDataParam> getParams() {
		return params;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(List<HarPostDataParam> params) {
		this.params = params;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
		return "HarPostData [mimeType=" + mimeType + ", params=" + params
				+ ", text=" + text + ", comment=" + comment + "]";
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
				+ ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		HarPostData other = (HarPostData) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(params, other.params))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
