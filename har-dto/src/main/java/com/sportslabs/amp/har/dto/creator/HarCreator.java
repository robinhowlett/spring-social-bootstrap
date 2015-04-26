/**
 * 
 */
package com.sportslabs.amp.har.dto.creator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Name and version info of the log creator application.
 * 
 * @author robin
 */
public class HarCreator {
	
	private String name;
	private String version;
	private String comment; // optional
	
	/**
	 * @param name 		Name of the application/browser used to export the log.
	 * @param version 	Version of the application/browser used to export the log.
	 * @param comment 	(Optional) A comment provided by the user or the application.
	 */
	@JsonCreator
	public HarCreator(
			@JsonProperty("name") 		String name, 
			@JsonProperty("version") 	String version, 
			@JsonProperty("comment") 	String comment) {
		super();
		this.name = name;
		this.version = version;
		this.comment = comment;
	}

	/**
	 * @return Name of the application/browser used to export the log.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Version of the application/browser used to export the log.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return A comment provided by the user or the application.
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
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
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
		return "HarCreator [name=" + name + ", version=" + version
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		HarCreator other = (HarCreator) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
