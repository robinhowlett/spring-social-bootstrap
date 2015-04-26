/**
 * 
 */
package org.springframework.social.data.converters.pagination;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.pagination.Page.PageMetadata;

/**
 * Test that the {@link SpringDataPageToPageDtoConverter} can convert a Spring Data {@link org.springframework.data.domain.Page} 
 * of {@link Domain} instances to a Spring Social Bootstrap {@link Page} of {@link BaseApiResource}-type 
 * {@link Dto} instances.
 *
 * @author robin
 */
public class SpringDataPageToPageDtoConverterTest {

	private static final String DOMAIN_NAME = "domainName";
	private static final String DTO_NAME = "dtoName";
	
	private SpringDataPageToPageDtoConverter<Domain, Dto> converter;
	private Page<Dto> expectedPageOfDtos;
	
	public SpringDataPageToPageDtoConverterTest() {
		PageMetadata metadata = new PageMetadata(1, 1, 1, PageMetadata.NO_SORT, 1, 1);
		expectedPageOfDtos = new Page<Dto>(Collections.singletonList(new Dto(DTO_NAME)), metadata);
	}

	@Before
	public void setUp() throws Exception {
		converter = new SpringDataPageToPageDtoConverter<Domain, Dto>(new SourceToTargetConverter());
	}
	
	@After
	public void tearDown() throws Exception {
		converter = null;
	}
	
	@Test
	public void convert_PageOfDomainObjects_WasCorrectlyConvertedToPageOfDtoObjects() throws Exception {
		List<Domain> content = Collections.singletonList(new Domain(DOMAIN_NAME));
		
		org.springframework.data.domain.Page<Domain> pageOfDomains = 
				new PageImpl<Domain>(content, new PageRequest(1, 1), 1);
		
		Page<Dto> pageOfDtos = converter.convert(pageOfDomains);
		
		assertThat(pageOfDtos, equalTo(expectedPageOfDtos));
	}
	
	/**
	 * The Domain class representing the data source entity
	 *
	 * @author robin
	 */
	private class Domain {
		
		private final String sourceName;
		
		public Domain(String sourceName) {
			this.sourceName = sourceName;
		}

		public String getSourceName() {
			return sourceName;
		}	
		
	}
	
	/**
	 * The DTO class representing the serializable {@link BaseApiResource} instance 
	 * that the Spring Social Bootstrap client will consume/produce 
	 *
	 * @author robin
	 */
	private class Dto implements BaseApiResource {

		private static final long serialVersionUID = 1L;
		private final String targetName;

		protected Dto(String targetName) {
			this.targetName = targetName;
		}

		@Override
		public Object getUid() {
			return new Random();
		}

		/**
		 * @return the targetName
		 */
		@SuppressWarnings("unused")
		public String getTargetName() {
			return targetName;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((targetName == null) ? 0 : targetName.hashCode());
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
			Dto other = (Dto) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (targetName == null) {
				if (other.targetName != null)
					return false;
			} else if (!targetName.equals(other.targetName))
				return false;
			return true;
		}

		private SpringDataPageToPageDtoConverterTest getOuterType() {
			return SpringDataPageToPageDtoConverterTest.this;
		}
		
	}
	
	/**
	 * Converter to convert a {@link Domain} to a {@link Dto}
	 *
	 * @author robin
	 */
	private class SourceToTargetConverter implements Converter<Domain, Dto> {

		@Override
		public Dto convert(Domain source) {
			return new Dto(source.getSourceName().replace("domain", "dto"));
		}
		
	}
	
}
