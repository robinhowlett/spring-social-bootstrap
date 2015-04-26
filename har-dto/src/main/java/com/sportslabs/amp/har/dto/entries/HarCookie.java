package com.sportslabs.amp.har.dto.entries;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object contains list of all cookies (used in {@link HarRequest} and {@link HarResponse} objects)
 *
 * @author robin
 */
public class HarCookie {
	
	private String 		name;
	private String 		value;
	private String 		path;		// optional
	private String 		domain;		// optional
	private DateTime 	expires; 	// optional
	private Boolean 	httpOnly;	// optional
	private Boolean 	secure;		// optional
	private String 		comment;	// optional
	
	/**
	 * @param name 		The name of the cookie
	 * @param value 	The cookie value
	 * @param path 		(Optional) The path pertaining to the cookie
	 * @param domain 	(Optional) The host of the cookie
	 * @param expires 	(Optional) Cookie expiration time. (ISO 8601 - YYYY-MM-DDThh:mm:ss.sTZD, e.g. 2009-07-24T19:20:30.123+02:00)
	 * @param httpOnly 	(Optional) True if the cookie is HTTP only, false otherwise
	 * @param secure 	(Optional) True if the cookie was transmitted over SSL, false otherwise
	 * @param comment 	(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarCookie(
			@JsonProperty("name") 		String name, 
			@JsonProperty("value") 		String value, 
			@JsonProperty("path") 		String path, 
			@JsonProperty("domain") 	String domain,
			@JsonProperty("expires") 	DateTime expires, 
			@JsonProperty("httpOnly")	Boolean httpOnly, 
			@JsonProperty("secure") 	Boolean secure, 
			@JsonProperty("comment") 	String comment) {
		super();
		this.name = name;
		this.value = value;
		this.path = path;
		this.domain = domain;
		this.expires = expires;
		this.httpOnly = httpOnly;
		this.secure = secure;
		this.comment = comment;
	}
	
	public HarCookie() { }

	/**
	 * @return The cookie value
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The cookie value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return The path pertaining to the cookie
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return The host of the cookie
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @return Cookie expiration time
	 */
	public DateTime getExpires() {
		return expires;
	}

	/**
	 * @return True if the cookie is HTTP only, false otherwise
	 */
	public Boolean getHttpOnly() {
		return httpOnly;
	}

	/**
	 * @return True if the cookie was transmitted over SSL, false otherwise
	 */
	public Boolean getSecure() {
		return secure;
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
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @param expires the expires to set
	 */
	public void setExpires(DateTime expires) {
		this.expires = expires;
	}

	/**
	 * @param httpOnly the httpOnly to set
	 */
	public void setHttpOnly(Boolean httpOnly) {
		this.httpOnly = httpOnly;
	}

	/**
	 * @param secure the secure to set
	 */
	public void setSecure(Boolean secure) {
		this.secure = secure;
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
		return "HarCookie [name=" + name + ", value=" + value + ", path="
				+ path + ", domain=" + domain + ", expires=" + expires
				+ ", httpOnly=" + httpOnly + ", secure=" + secure
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
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((expires == null) ? 0 : expires.hashCode());
		result = prime * result
				+ ((httpOnly == null) ? 0 : httpOnly.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((secure == null) ? 0 : secure.hashCode());
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
		HarCookie other = (HarCookie) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (expires == null) {
			if (other.expires != null)
				return false;
		} else if (!expires.equals(other.expires))
			return false;
		if (httpOnly == null) {
			if (other.httpOnly != null)
				return false;
		} else if (!httpOnly.equals(other.httpOnly))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (secure == null) {
			if (other.secure != null)
				return false;
		} else if (!secure.equals(other.secure))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}	

}
