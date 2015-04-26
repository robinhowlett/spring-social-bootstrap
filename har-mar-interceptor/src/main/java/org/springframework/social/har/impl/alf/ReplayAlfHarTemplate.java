/**
 * 
 */
package org.springframework.social.har.impl.alf;

import org.springframework.social.har.impl.AbstractReplayHarTemplate;
import org.springframework.web.client.RestTemplate;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.entries.HarContent;

/**
 * Replay ALF entries
 * 
 * @author robin
 */
public class ReplayAlfHarTemplate extends AbstractReplayHarTemplate<AlfHar, AlfHarEntry> {

	public ReplayAlfHarTemplate(RestTemplate restTemplate) {
		super(restTemplate);
	}

	@Override
	protected String getRequestBody(AlfHarEntry entry) {
		HarContent harContent = entry.getRequest().getContent();
		return (harContent != null ? harContent.getText() : null);
	}

}
