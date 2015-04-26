/**
 * 
 */
package org.springframework.social.har.service.jdbc_alf;

import org.springframework.social.har.converter.jdbc_alf.AlfHarEntryToJdbcHarEntryConverter;
import org.springframework.social.har.converter.jdbc_alf.ListOfJdbcHarEntriesToAlfHarConverter;
import org.springframework.social.har.domain.jdbc_alf.JdbcAlfHarEntry;
import org.springframework.social.har.repository.jdbc_alf.AsyncJdbcAlfHarRepository;
import org.springframework.social.har.service.AbstractHarService;
import org.springframework.social.har.service.HarService;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;

/**
 * ALF and JDBC-compatible {@link HarService}
 *
 * @author robin
 */
public class JdbcAlfHarServiceImpl extends AbstractHarService<JdbcAlfHarEntry, AlfHarEntry, AlfHar> {
	
	public JdbcAlfHarServiceImpl(
			final AsyncJdbcAlfHarRepository harRepository, 
			final ListOfJdbcHarEntriesToAlfHarConverter listOfHarEntriesDomainToHarDtoConverter,
			final AlfHarEntryToJdbcHarEntryConverter harEntryDtoToHarEntryDomainConverter) {
		super(harRepository, listOfHarEntriesDomainToHarDtoConverter, harEntryDtoToHarEntryDomainConverter);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.har.service.AbstractHarServiceImpl#getHarRepository()
	 */
	@Override
	public AsyncJdbcAlfHarRepository getHarRepository() {
		return (AsyncJdbcAlfHarRepository) super.getHarRepository();
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.har.service.AbstractHarServiceImpl#getListOfHarEntriesDomainToHarDtoConverter()
	 */
	@Override
	public ListOfJdbcHarEntriesToAlfHarConverter getListOfHarEntriesDomainToHarDtoConverter() {
		return (ListOfJdbcHarEntriesToAlfHarConverter) super.getListOfHarEntriesDomainToHarDtoConverter();
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.har.service.AbstractHarServiceImpl#getDtoToDomainConverter()
	 */
	@Override
	public AlfHarEntryToJdbcHarEntryConverter getDtoToDomainConverter() {
		return (AlfHarEntryToJdbcHarEntryConverter) super.getDtoToDomainConverter();
	}	
	
}
