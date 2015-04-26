/**
 * 
 */
package com.sportslabs.amp.har.dto.log;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.browser.HarBrowser;
import com.sportslabs.amp.har.dto.creator.HarCreator;
import com.sportslabs.amp.har.dto.entries.HarEntry;
import com.sportslabs.amp.har.dto.pages.HarPage;

/**
 * This object represents the root of exported data.
 * 
 * <p>There is one {@link HarPage} object for every exported web page and one {@link HarEntry} object for every HTTP request. 
 * In case when an HTTP trace tool isn't able to group requests by a page, the pages list is empty and 
 * individual requests doesn't have a parent page.
 *
 * @author robin
 */
public class HarLog {
	
	private String 						version;
	private HarCreator 					creator;
	private HarBrowser 					browser; // optional
	private List<HarPage> 				pages;	 // optional
	private List<? extends HarEntry>	entries;
	private String 						comment; // optional
	
	/**
	 * @param creator 	Name and version info of the log creator application.
	 * @param browser 	(Optional) Name and version info of used browser.
	 * @param pages 	(Optional) List of all exported (tracked) pages. Leave out this field if the application 
	 * 					does not support grouping by pages.
	 * @param entries 	List of all exported (tracked) requests.
	 * @param comment 	(Optional) A comment provided by the user or the application.
	 */
	@JsonCreator
	public HarLog(
			@JsonProperty("version") 	String version,
			@JsonProperty("creator") 	HarCreator creator, 
			@JsonProperty("browser") 	HarBrowser browser,
			@JsonProperty("pages") 		List<HarPage> pages, 
			@JsonProperty("entries") 	List<? extends HarEntry> entries, 
			@JsonProperty("comment") 	String comment) {
		super();
		this.version = (version != null ? version : Har.SPEC_VERSION);
		this.creator = creator;
		this.browser = browser;
		this.pages = pages;
		this.entries = entries;
		this.comment = comment;
	}

	/**
	 * @return Version number of the format
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return Name and version info of the log creator application.
	 */
	public HarCreator getCreator() {
		return creator;
	}

	/**
	 * @return Name and version info of used browser
	 */
	public HarBrowser getBrowser() {
		return browser;
	}

	/**
	 * @return List of all exported (tracked) pages.
	 */
	public List<HarPage> getPages() {
		return pages;
	}

	/**
	 * @return List of all exported (tracked) requests.
	 */
	public List<? extends HarEntry> getEntries() {
		return entries;
	}

	/**
	 * @return A comment provided by the user or the application
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(HarCreator creator) {
		this.creator = creator;
	}

	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(HarBrowser browser) {
		this.browser = browser;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(List<HarPage> pages) {
		this.pages = pages;
	}

	/**
	 * @param entries the entries to set
	 */
	public void setEntries(List<? extends HarEntry> entries) {
		this.entries = entries;
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
		return "HarLog [version=" + version + ", creator=" + creator
				+ ", browser=" + browser + ", pages=" + pages + ", entries="
				+ entries + ", comment=" + comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((browser == null) ? 0 : browser.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((entries == null) ? 0 : entries.hashCode());
		result = prime * result + ((pages == null) ? 0 : pages.hashCode());
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
		HarLog other = (HarLog) obj;
		if (browser == null) {
			if (other.browser != null)
				return false;
		} else if (!browser.equals(other.browser))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (entries == null) {
			if (other.entries != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(entries, other.entries))
			return false;
		if (pages == null) {
			if (other.pages != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(pages, other.pages))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
