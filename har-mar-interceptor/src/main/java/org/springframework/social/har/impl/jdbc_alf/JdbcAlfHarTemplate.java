/**
 * 
 */
package org.springframework.social.har.impl.jdbc_alf;

import org.springframework.social.har.domain.jdbc_alf.JdbcAlfHarEntry;
import org.springframework.social.har.impl.AbstractHarTemplate;
import org.springframework.social.har.service.jdbc_alf.JdbcAlfHarServiceImpl;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;

/**
 * Implementation of {@link AbstractHarTemplate} for using a {@link JdbcAlfHarServiceImpl} for saving
 * and querying the ALF log of entries
 * 
 * @author robin
 */
public class JdbcAlfHarTemplate extends AbstractHarTemplate<JdbcAlfHarEntry, AlfHarEntry, AlfHar> {

	public JdbcAlfHarTemplate(JdbcAlfHarServiceImpl harService) {
		super(harService);
	}
	
	@Override
	public JdbcAlfHarServiceImpl getHarService() {
		return (JdbcAlfHarServiceImpl) super.getHarService();
	}

}
