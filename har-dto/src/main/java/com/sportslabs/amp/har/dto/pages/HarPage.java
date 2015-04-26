/**
 * 
 */
package com.sportslabs.amp.har.dto.pages;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.log.HarLog;

/**
 * An exported (tracked) page
 *
 * @author robin
 */
public class HarPage {
	
	private DateTime 		startedDateTime;
	private String 			id;
	private String 			title;
	private HarPageTimings 	pageTimings;
	private String 			comment; 			// optional
	
	/**
	 * @param startedDateTime	Date and time stamp for the beginning of the page load 
	 * 							(ISO 8601 - YYYY-MM-DDThh:mm:ss.sTZD, e.g. 2009-07-24T19:20:30.45+01:00).
	 * @param id				Unique identifier of a page within the {@link HarLog}. {@link HarEntry} entries use it to refer the parent page.
	 * @param title				Page title.
	 * @param pageTimings		(Optional) Detailed timing info about page load
	 * @param comment			(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarPage(
			@JsonProperty("startedDateTime")	DateTime startedDateTime, 
			@JsonProperty("id") 				String id, 
			@JsonProperty("title") 				String title,
			@JsonProperty("pageTimings") 		HarPageTimings pageTimings, 
			@JsonProperty("comment") 			String comment) {
		super();
		this.startedDateTime = startedDateTime;
		this.id = id;
		this.title = title;
		this.pageTimings = pageTimings;
		this.comment = comment;
	}

	/**
	 * @return Date and time stamp for the beginning of the page load
	 */
	public DateTime getStartedDateTime() {
		return startedDateTime;
	}

	/**
	 * @return Unique identifier of a page within the {@link HarLog}
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return Page title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return Detailed timing info about page load
	 */
	public HarPageTimings getPageTimings() {
		return pageTimings;
	}

	/**
	 * @return A comment provided by the user or the application
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param startedDateTime the startedDateTime to set
	 */
	public void setStartedDateTime(DateTime startedDateTime) {
		this.startedDateTime = startedDateTime;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param pageTimings the pageTimings to set
	 */
	public void setPageTimings(HarPageTimings pageTimings) {
		this.pageTimings = pageTimings;
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
		return "HarPage [startedDateTime=" + startedDateTime + ", id=" + id
				+ ", title=" + title + ", pageTimings=" + pageTimings
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((pageTimings == null) ? 0 : pageTimings.hashCode());
		result = prime * result
				+ ((startedDateTime == null) ? 0 : startedDateTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		HarPage other = (HarPage) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pageTimings == null) {
			if (other.pageTimings != null)
				return false;
		} else if (!pageTimings.equals(other.pageTimings))
			return false;
		if (startedDateTime == null) {
			if (other.startedDateTime != null)
				return false;
		} else if (!startedDateTime.equals(other.startedDateTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
