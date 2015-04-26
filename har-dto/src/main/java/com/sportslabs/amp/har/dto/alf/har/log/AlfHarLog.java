/**
 * 
 */
package com.sportslabs.amp.har.dto.alf.har.log;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.creator.HarCreator;
import com.sportslabs.amp.har.dto.log.HarLog;

public class AlfHarLog extends HarLog {

	@JsonCreator
	public AlfHarLog(
			@JsonProperty("version") 	String version,
			@JsonProperty("creator") 	HarCreator creator, 
			@JsonProperty("entries") 	List<AlfHarEntry> entries) {
		super(version, creator, null, null, entries, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends AlfHarEntry> getEntries() {
		return (List<? extends AlfHarEntry>) super.getEntries();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlfHarLog [toString()=" + super.toString() + "]";
	}

}
