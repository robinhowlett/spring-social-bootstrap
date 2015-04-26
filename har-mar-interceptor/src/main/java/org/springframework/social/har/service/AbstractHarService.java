/**
 * 
 */
package org.springframework.social.har.service;

import static org.apache.commons.collections4.IteratorUtils.toList;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.social.har.converter.HarEntryDtoToHarEntryDomainConverter;
import org.springframework.social.har.converter.ListOfHarEntriesDomainToHarDtoConverter;
import org.springframework.social.har.repository.HarRepository;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarEntry;

/**
 * Abstract implementation of {@link HarService} for saving {@link HarEntry} DTO instances as domain 
 * {@link org.springframework.social.har.domain.HarEntry} entities to a data store and for 
 * querying a list of domain {@link org.springframework.social.har.domain.HarEntry}-type entries from a 
 * data store and converting them to {@link Har} DTO instances.
 *
 * @author robin
 */
public abstract class AbstractHarService<D extends org.springframework.social.har.domain.HarEntry, E extends HarEntry, H extends Har> implements HarService<D, E, H> {
	
	private final HarRepository<D> harRepository;
	private final ListOfHarEntriesDomainToHarDtoConverter<List<D>, H> listOfHarEntriesDomainToHarDtoConverter;
	private final HarEntryDtoToHarEntryDomainConverter<E, D> harEntryDtoToHarEntryDomainConverter;

	public AbstractHarService(
			final HarRepository<D> harRepository, 
			final ListOfHarEntriesDomainToHarDtoConverter<List<D>, H> listOfHarEntriesDomainToHarDtoConverter,
			final HarEntryDtoToHarEntryDomainConverter<E, D> harEntryDtoToHarEntryDomainConverter) {
		this.harRepository = harRepository;
		this.listOfHarEntriesDomainToHarDtoConverter = listOfHarEntriesDomainToHarDtoConverter;
		this.harEntryDtoToHarEntryDomainConverter = harEntryDtoToHarEntryDomainConverter;
	}

	@Override
	public D save(E entry) {
		D harEntryDomain = getDtoToDomainConverter().convert(entry);
		
		if (harEntryDomain != null) {
			getHarRepository().save(harEntryDomain);
		}
		
		return harEntryDomain;
	}

	@Override
	public H findAll() {
		Iterable<D> entries = getHarRepository().findAll();
		
		if (entries != null) {
			return listOfHarEntriesDomainToHarDtoConverter.convert(toList(entries.iterator()));
		}
		
		return null;
	}

	@Override
	public H findAllOrderByTimestampDesc() {
		Iterable<D> entries = getHarRepository().findAllOrderByTimestampDesc();
		
		if (entries != null) {
			return listOfHarEntriesDomainToHarDtoConverter.convert(toList(entries.iterator()));
		}
		
		return null;
	}

	@Override
	public H findByTimestampGreaterThan(DateTime start) {
		Iterable<D> entries = getHarRepository().findByTimestampGreaterThan(start);
		
		if (entries != null) {
			return listOfHarEntriesDomainToHarDtoConverter.convert(toList(entries.iterator()));
		}
		
		return null;
	}

	@Override
	public H findByTimestampBetween(DateTime start, DateTime end) {
		Iterable<D> entries = getHarRepository().findByTimestampBetween(start, end);
		
		if (entries != null) {
			return listOfHarEntriesDomainToHarDtoConverter.convert(toList(entries.iterator()));
		}
		
		return null;
	}

	/**
	 * @return the harRepository
	 */
	public HarRepository<D> getHarRepository() {
		return harRepository;
	}

	/**
	 * @return the domainToDtoConverter
	 */
	public ListOfHarEntriesDomainToHarDtoConverter<List<D>, H> getListOfHarEntriesDomainToHarDtoConverter() {
		return listOfHarEntriesDomainToHarDtoConverter;
	}

	/**
	 * @return the dtoToDomainConverter
	 */
	public HarEntryDtoToHarEntryDomainConverter<E, D> getDtoToDomainConverter() {
		return harEntryDtoToHarEntryDomainConverter;
	}
	
}
