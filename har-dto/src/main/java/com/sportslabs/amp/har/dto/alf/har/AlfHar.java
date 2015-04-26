package com.sportslabs.amp.har.dto.alf.har;

import java.util.Collection;

import org.springframework.social.dto.pagination.BaseApiPage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.alf.har.log.AlfHarLog;

public class AlfHar extends Har implements BaseApiPage<AlfHarEntry> {

	private static final long serialVersionUID = 1L;

	@JsonCreator
	public AlfHar(@JsonProperty("log") AlfHarLog log) {
		super(log);
	}
	
	@Override
	public AlfHarLog getLog() {
		return (AlfHarLog) super.getLog();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlfHar [toString()=" + super.toString() + "]";
	}
	
	/**
	 * HTTP Archives do not have a unique identifier
	 */
	@Override
	public Object getUid() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<AlfHarEntry> getContent() {
		return (getLog() != null ? (Collection<AlfHarEntry>) getLog().getEntries() : null);
	}
}
