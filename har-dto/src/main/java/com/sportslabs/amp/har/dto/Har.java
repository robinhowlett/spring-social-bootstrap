package com.sportslabs.amp.har.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.log.HarLog;

public class Har {
	
	public static String SPEC_VERSION = "1.2";
	
	private HarLog log;

	/**
	 * @param log
	 */
	@JsonCreator
	public Har(@JsonProperty("log") HarLog log) {
		super();
		this.log = log;
	}

	/**
	 * @return the log
	 */
	public HarLog getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(HarLog log) {
		this.log = log;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Har [log=" + log + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((log == null) ? 0 : log.hashCode());
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
		Har other = (Har) obj;
		if (log == null) {
			if (other.log != null)
				return false;
		} else if (!log.equals(other.log))
			return false;
		return true;
	}
}
