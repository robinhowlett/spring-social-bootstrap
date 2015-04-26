package com.sportslabs.amp.har.dto.entries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * List of posted parameters, if any (embedded in {@link HarPostData} object) 
 *
 * @author robin
 */
public class HarPostDataParam {

	private String name;
	private String value;		// optional
	private String fileName;	// optional
	private String contentType;	// optional
	private String comment;		// optional
	
	/**
	 * @param name			Name of a posted parameter
	 * @param value			(Optional) Value of a posted parameter or content of a posted file
	 * @param fileName		(Optional) Name of a posted file
	 * @param contentType	(Optional) Content type of a posted file
	 * @param comment		(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarPostDataParam(
			@JsonProperty("name") 			String name, 
			@JsonProperty("value") 			String value, 
			@JsonProperty("fileName") 		String fileName,
			@JsonProperty("contentType")	String contentType, 
			@JsonProperty("comment") 		String comment) {
		super();
		this.name = name;
		this.value = value;
		this.fileName = fileName;
		this.contentType = contentType;
		this.comment = comment;
	}

	/**
	 * @return name of a posted parameter
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return value of a posted parameter or content of a posted file
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @return Name of a posted file
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * @return Content type of a posted file
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @return A comment provided by the user or the application
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
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
		return "HarPostDataParam [name=" + name + ", value=" + value
				+ ", fileName=" + fileName + ", contentType=" + contentType
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
		result = prime * result
				+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		HarPostDataParam other = (HarPostDataParam) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
