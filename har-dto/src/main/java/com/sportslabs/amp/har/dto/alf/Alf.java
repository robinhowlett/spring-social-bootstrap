/**
 * 
 */
package com.sportslabs.amp.har.dto.alf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.alf.har.AlfHar;

/**
 * Mashape's API Log Format (ALF)
 * 
 * <p>ALF is a new HTTP logging format based on <a href="http://www.softwareishard.com/blog/har-12-spec/">HAR</a> (HTTP Archive Format).
 * 
 * @author robin
 * @see <a href="https://github.com/Mashape/api-log-format">api-log-format on GitHub</a>
 */
public class Alf {
	
	private final String serviceToken;
	private final AlfHar har;
	
	@JsonCreator
	public Alf(
			@JsonProperty("serviceToken") String serviceToken,
			@JsonProperty("har") AlfHar har) {
		this.serviceToken = serviceToken;
		this.har = har;
	}

	/**
	 * @return the serviceToken
	 */
	public String getServiceToken() {
		return serviceToken;
	}

	/**
	 * @return the har
	 */
	public AlfHar getHar() {
		return har;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Alf [serviceToken=" + serviceToken + ", har=" + har + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((har == null) ? 0 : har.hashCode());
		result = prime * result
				+ ((serviceToken == null) ? 0 : serviceToken.hashCode());
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
		Alf other = (Alf) obj;
		if (har == null) {
			if (other.har != null)
				return false;
		} else if (!har.equals(other.har))
			return false;
		if (serviceToken == null) {
			if (other.serviceToken != null)
				return false;
		} else if (!serviceToken.equals(other.serviceToken))
			return false;
		return true;
	}

}
