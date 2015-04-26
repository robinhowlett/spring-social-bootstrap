package com.sportslabs.amp.har.dto.entries;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * An exported HTTP request. 
 * 
 * <p>Sorting entries by startedDateTime (starting from the oldest) is preferred way how to export data since it can make importing faster. 
 * However the reader application should always make sure the array is sorted (if required for the import).
 *
 * @author robin
 */
public class HarEntry implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String 			pageref;
	private DateTime 		startedDateTime;
	private Integer 		time;
	private HarRequest 		request;
	private HarResponse 	response;
	private HarCache 		cache;
	private HarEntryTimings	timings;
	private String			serverIPAddress;
	private String			connection;
	private String 			comment;
	
	/**
	 * @param pageref			(Unique, Optional) Reference to the parent page. 
	 * 							Leave out this field if the application does not support grouping by pages.
	 * @param startedDateTime	Date and time stamp of the request start (ISO 8601 - YYYY-MM-DDThh:mm:ss.sTZD).
	 * @param time				Total elapsed time of the request in milliseconds. 
	 * 							This is the sum of all timings available in the timings object (i.e. not including -1 values)
	 * @param request			Detailed info about the request
	 * @param response			Detailed info about the response
	 * @param cache				Info about cache usage.
	 * @param timings			Detailed timing info about request/response round trip
	 * @param serverIPAddress	(Optional) IP address of the server that was connected (result of DNS resolution)
	 * @param connection		(Optional) Unique ID of the parent TCP/IP connection, can be the client or server port number. 
	 * 							Leave out this field if the application doesn't support this info.
	 * @param comment			(Optional) A comment provided by the user or the application.
	 */
	@JsonCreator
	public HarEntry(
			@JsonProperty("pageref") 			String pageref, 
			@JsonProperty("startedDateTime")	DateTime startedDateTime, 
			@JsonProperty("time") 				Integer time,
			@JsonProperty("request") 			HarRequest request, 
			@JsonProperty("response") 			HarResponse response, 
			@JsonProperty("cache") 				HarCache cache,
			@JsonProperty("timings") 			HarEntryTimings timings, 
			@JsonProperty("serverIPAddress") 	String serverIPAddress, 
			@JsonProperty("connection") 		String connection,
			@JsonProperty("comment") 			String comment) {
		super();
		this.pageref = pageref;
		this.startedDateTime = startedDateTime;
		this.time = time;
		this.request = request;
		this.response = response;
		this.cache = cache;
		this.timings = timings;
		this.serverIPAddress = serverIPAddress;
		this.connection = connection;
		this.comment = comment;
	}

	/**
	 * @return Reference to the parent page.
	 */
	public String getPageref() {
		return pageref;
	}

	/**
	 * @return Date and time stamp of the request start
	 */
	public DateTime getStartedDateTime() {
		return startedDateTime;
	}

	/**
	 * @return Total elapsed time of the request in milliseconds.
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * @return Detailed info about the request
	 */
	public HarRequest getRequest() {
		return request;
	}

	/**
	 * @return Detailed info about the response
	 */
	public HarResponse getResponse() {
		return response;
	}

	/**
	 * @return Info about cache usage.
	 */
	public HarCache getCache() {
		return cache;
	}

	/**
	 * @return Detailed timing info about request/response round trip
	 */
	public HarEntryTimings getTimings() {
		return timings;
	}

	/**
	 * @return IP address of the server that was connected (result of DNS resolution)
	 */
	public String getServerIPAddress() {
		return serverIPAddress;
	}

	/**
	 * @return Unique ID of the parent TCP/IP connection, can be the client or server port number
	 */
	public String getConnection() {
		return connection;
	}

	/**
	 * @return A comment provided by the user or the application
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param pageref the pageref to set
	 */
	public void setPageref(String pageref) {
		this.pageref = pageref;
	}

	/**
	 * @param startedDateTime the startedDateTime to set
	 */
	public void setStartedDateTime(DateTime startedDateTime) {
		this.startedDateTime = startedDateTime;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Integer time) {
		this.time = time;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(HarRequest request) {
		this.request = request;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(HarResponse response) {
		this.response = response;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(HarCache cache) {
		this.cache = cache;
	}

	/**
	 * @param timings the timings to set
	 */
	public void setTimings(HarEntryTimings timings) {
		this.timings = timings;
	}

	/**
	 * @param serverIPAddress the serverIPAddress to set
	 */
	public void setServerIPAddress(String serverIPAddress) {
		this.serverIPAddress = serverIPAddress;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(String connection) {
		this.connection = connection;
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
		return "HarEntry [pageref=" + pageref + ", startedDateTime="
				+ startedDateTime + ", time=" + time + ", request=" + request
				+ ", response=" + response + ", cache=" + cache + ", timings="
				+ timings + ", serverIPAddress=" + serverIPAddress
				+ ", connection=" + connection + ", comment=" + comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cache == null) ? 0 : cache.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((connection == null) ? 0 : connection.hashCode());
		result = prime * result + ((pageref == null) ? 0 : pageref.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result
				+ ((response == null) ? 0 : response.hashCode());
		result = prime * result
				+ ((serverIPAddress == null) ? 0 : serverIPAddress.hashCode());
		result = prime * result
				+ ((startedDateTime == null) ? 0 : startedDateTime.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((timings == null) ? 0 : timings.hashCode());
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
		HarEntry other = (HarEntry) obj;
		if (cache == null) {
			if (other.cache != null)
				return false;
		} else if (!cache.equals(other.cache))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (connection == null) {
			if (other.connection != null)
				return false;
		} else if (!connection.equals(other.connection))
			return false;
		if (pageref == null) {
			if (other.pageref != null)
				return false;
		} else if (!pageref.equals(other.pageref))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (serverIPAddress == null) {
			if (other.serverIPAddress != null)
				return false;
		} else if (!serverIPAddress.equals(other.serverIPAddress))
			return false;
		if (startedDateTime == null) {
			if (other.startedDateTime != null)
				return false;
		} else if (!startedDateTime.equals(other.startedDateTime))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (timings == null) {
			if (other.timings != null)
				return false;
		} else if (!timings.equals(other.timings))
			return false;
		return true;
	}

}
