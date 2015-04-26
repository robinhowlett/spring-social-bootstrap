package org.springframework.social.impl.test_support.mocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.pagination.Page.PageMetadata;
import org.springframework.social.dto.pagination.Page.PageMetadata.Sort;
import org.springframework.social.impl.test_support.dto.TestBaseApiResource;

/**
 * Mock objects for use in simulating service provider responses 
 *
 * @author robin
 */
public class MockTestBaseApiResources {
	
	public static final DateTime JULY_1_2014_MIDNIGHT_UTC = new DateTime(2014, 7, 1, 0, 0, DateTimeZone.UTC);
	
	public static final TestBaseApiResource sample1TestBaseApiResource() {		
		return new TestBaseApiResource("1234", "Sample 1", JULY_1_2014_MIDNIGHT_UTC, Boolean.TRUE);
	}
	
	public static final TestBaseApiResource sample2TestBaseApiResource() {		
		return new TestBaseApiResource("5678", "Sample 2", JULY_1_2014_MIDNIGHT_UTC, Boolean.FALSE);
	}
	
	public static final TestBaseApiResource sample3TestBaseApiResource() {		
		return new TestBaseApiResource("9101112", "Sample 3", JULY_1_2014_MIDNIGHT_UTC, Boolean.TRUE);
	}
	
	public static final TestBaseApiResource sample4TestBaseApiResource() {		
		return new TestBaseApiResource("13141516", "Sample 4", JULY_1_2014_MIDNIGHT_UTC, Boolean.TRUE);
	}
	
	public static final TestBaseApiResource sample5TestBaseApiResource() {		
		return new TestBaseApiResource("17181920", "Sample 5", JULY_1_2014_MIDNIGHT_UTC, Boolean.FALSE);
	}
	
	public static final TestBaseApiResource sample6TestBaseApiResource() {		
		return new TestBaseApiResource("21222324", "Sample 6", JULY_1_2014_MIDNIGHT_UTC, Boolean.FALSE);
	}

	public static final Page<TestBaseApiResource> multipleSampleTestBaseApiResources() {
		List<TestBaseApiResource> testBaseApiResources = 
				Arrays.asList(sample1TestBaseApiResource(), sample2TestBaseApiResource());
		
		PageMetadata metadata = 
				new PageMetadata(1, testBaseApiResources.size(), Page.DEFAULT_PAGE_SIZE, PageMetadata.NO_SORT, 2, 1);
		return new Page<TestBaseApiResource>(testBaseApiResources, metadata);
	}
	
	public static final Page<TestBaseApiResource> multipleSampleTestBaseApiResourcesWithPaginationQuery() {
		Page<TestBaseApiResource> resources = multipleSampleTestBaseApiResources();
		resources.getMetadata().setSorts(Collections.singletonList(new Sort("name", Sort.Direction.ASC)));
		return resources;
	}
	
	public static final Page<TestBaseApiResource> multipleSampleTestBaseApiResourcesFirstPage() {
		List<TestBaseApiResource> testBaseApiResources = 
				Arrays.asList(sample1TestBaseApiResource(), sample2TestBaseApiResource());
		
		PageMetadata metadata = 
				new PageMetadata(1, testBaseApiResources.size(), testBaseApiResources.size(), PageMetadata.NO_SORT, 6, 3);
		return new Page<TestBaseApiResource>(testBaseApiResources, metadata);
	}
	
	public static final Page<TestBaseApiResource> multipleSampleTestBaseApiResourcesSecondPage() {
		List<TestBaseApiResource> testBaseApiResources = 
				Arrays.asList(sample3TestBaseApiResource(), sample4TestBaseApiResource());
		
		PageMetadata metadata = 
				new PageMetadata(2, testBaseApiResources.size(), testBaseApiResources.size(), PageMetadata.NO_SORT, 6, 3);
		return new Page<TestBaseApiResource>(testBaseApiResources, metadata);
	}
	
	public static final Page<TestBaseApiResource> multipleSampleTestBaseApiResourcesThirdPage() {
		List<TestBaseApiResource> testBaseApiResources = 
				Arrays.asList(sample5TestBaseApiResource(), sample6TestBaseApiResource());
		
		PageMetadata metadata = 
				new PageMetadata(3, testBaseApiResources.size(), testBaseApiResources.size(), PageMetadata.NO_SORT, 6, 3);
		return new Page<TestBaseApiResource>(testBaseApiResources, metadata);
	}
	
}
