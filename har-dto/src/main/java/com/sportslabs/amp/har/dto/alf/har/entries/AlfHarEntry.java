package com.sportslabs.amp.har.dto.alf.har.entries;

import org.joda.time.DateTime;
import org.springframework.social.dto.BaseApiResource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.entries.HarEntry;
import com.sportslabs.amp.har.dto.entries.HarEntryTimings;


/**
 * An exported HTTP request. 
 * 
 * <p>Sorting entries by startedDateTime (starting from the oldest) is preferred way how to export data since it can make importing faster. 
 * However the reader application should always make sure the array is sorted (if required for the import).
 *
 * @author robin
 */
public class AlfHarEntry extends HarEntry implements BaseApiResource {
	
	private static final long serialVersionUID = 1L;
	
	private final String clientIPAddress;
	
	private String _binId;

	/**
	 * @param startedDateTime	Date and time stamp of the request start (ISO 8601 - YYYY-MM-DDThh:mm:ss.sTZD).
	 * @param time				Total elapsed time of the request in milliseconds. 
	 * 							This is the sum of all timings available in the timings object (i.e. not including -1 values)
	 * @param request			Detailed info about the request
	 * @param response			Detailed info about the response
	 * @param timings			Detailed timing info about request/response round trip
	 * @param serverIPAddress	(Optional) IP address of the server that was connected (result of DNS resolution)
	 * @param clientIPAddress	(Optional) IP address of the client that was connected
	 */
	@JsonCreator
	public AlfHarEntry( 
			@JsonProperty("startedDateTime")	DateTime startedDateTime, 
			@JsonProperty("time") 				Integer time,
			@JsonProperty("request") 			AlfHarRequest request, 
			@JsonProperty("response") 			AlfHarResponse response, 
			@JsonProperty("timings") 			HarEntryTimings timings, 
			@JsonProperty("serverIPAddress") 	String serverIPAddress,
			@JsonProperty("clientIPAddress") 	String clientIPAddress) {
		super(null, startedDateTime, time, request, response, null, timings, serverIPAddress, null, null);
		this.clientIPAddress = clientIPAddress;
	}
	
	@Override
	public AlfHarRequest getRequest() {
		return (AlfHarRequest) super.getRequest();
	}
	
	@Override
	public AlfHarResponse getResponse() {
		return (AlfHarResponse) super.getResponse();
	}

	/**
	 * @return the clientIPAddress
	 */
	public String getClientIPAddress() {
		return clientIPAddress;
	}

	/**
	 * @return the _binId
	 */
	public String get_binId() {
		return _binId;
	}

	/**
	 * @param _binId the _binId to set
	 */
	public void set_binId(String _binId) {
		this._binId = _binId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlfHarEntry [toString()=" + super.toString()
				+ ", clientIPAddress=" + clientIPAddress + ", _binId=" + _binId
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_binId == null) ? 0 : _binId.hashCode());
		result = prime * result
				+ ((clientIPAddress == null) ? 0 : clientIPAddress.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlfHarEntry other = (AlfHarEntry) obj;
		if (_binId == null) {
			if (other._binId != null)
				return false;
		} else if (!_binId.equals(other._binId))
			return false;
		if (clientIPAddress == null) {
			if (other.clientIPAddress != null)
				return false;
		} else if (!clientIPAddress.equals(other.clientIPAddress))
			return false;
		return true;
	}

	// No UID for HAR Entries
	@Override
	public Object getUid() {
		return null;
	}

}
