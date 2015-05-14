/**
 * 
 */
package org.springframework.social.impl;

import static com.sportslabs.amp.har.dto.Har.SPEC_VERSION;
import static java.lang.Thread.sleep;
import static java.util.Collections.singletonList;
import static org.apache.commons.codec.Charsets.UTF_8;
import static org.h2.tools.Server.createWebServer;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import static org.springframework.social.dto.pagination.Page.DIRECTION;
import static org.springframework.social.dto.pagination.Page.PAGE;
import static org.springframework.social.dto.pagination.Page.PAGE_SIZE;
import static org.springframework.social.dto.pagination.Page.SORT;
import static org.springframework.social.impl.test_support.TestBaseApi.TEST_API_PROVIDER_ID;
import static org.springframework.social.impl.test_support.builders.TestPaginationUriComponentsBuilder.PAGE_PREFIX;
import static org.springframework.social.impl.test_support.builders.TestPaginationUriComponentsBuilder.fromUri;
import static org.springframework.social.impl.test_support.impl.resources.login.LoginTemplate.FAKE_SESSION_ID;
import static org.springframework.social.impl.test_support.impl.resources.test.TestTemplate.RESOURCES_PATH;
import static org.springframework.social.impl.test_support.impl.resources.test.TestTemplate.RESOURCE_ID_PATH;
import static org.springframework.social.impl.test_support.mocks.MockAlfHarEntries.alfHarEntryWithTimeSensitiveInfoSupplied;
import static org.springframework.social.impl.test_support.mocks.MockTestBaseApiResources.JULY_1_2014_MIDNIGHT_UTC;
import static org.springframework.social.impl.test_support.mocks.MockTestBaseApiResources.multipleSampleTestBaseApiResources;
import static org.springframework.social.impl.test_support.mocks.MockTestBaseApiResources.multipleSampleTestBaseApiResourcesFirstPage;
import static org.springframework.social.impl.test_support.mocks.MockTestBaseApiResources.multipleSampleTestBaseApiResourcesSecondPage;
import static org.springframework.social.impl.test_support.mocks.MockTestBaseApiResources.multipleSampleTestBaseApiResourcesThirdPage;
import static org.springframework.social.impl.test_support.mocks.MockTestBaseApiResources.multipleSampleTestBaseApiResourcesWithPaginationQuery;
import static org.springframework.social.impl.test_support.mocks.MockTestBaseApiResources.sample1TestBaseApiResource;
import static org.springframework.social.impl.test_support.settings.DefaultTestBaseApiClientSettings.TEST_API_KEY;
import static org.springframework.social.impl.test_support.settings.TestBaseApiClientSecuritySettings.API_KEY_PARAM;
import static org.springframework.social.impl.test_support.settings.TestBaseApiClientSettings.TEST_CLIENT_USER_AGENT;
import static org.springframework.social.test.BaseApiTestUtils.readFileAsJsonString;
import static org.springframework.social.test.BaseApiTestUtils.readFileAsString;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.pagination.Page.PageMetadata;
import org.springframework.social.dto.pagination.Page.PageMetadata.Sort;
import org.springframework.social.har.converter.jdbc_alf.AlfHarEntryToJdbcHarEntryConverter;
import org.springframework.social.har.converter.jdbc_alf.ListOfJdbcHarEntriesToAlfHarConverter;
import org.springframework.social.har.impl.jdbc_alf.JdbcAlfHarTemplate;
import org.springframework.social.har.replay.Interval;
import org.springframework.social.har.repository.jdbc_alf.AsyncJdbcAlfHarRepository;
import org.springframework.social.har.service.jdbc_alf.JdbcAlfHarServiceImpl;
import org.springframework.social.impl.test_support.TestBaseApi;
import org.springframework.social.impl.test_support.builders.TestPaginationUriComponentsBuilder;
import org.springframework.social.impl.test_support.dto.TestBaseApiResource;
import org.springframework.social.impl.test_support.impl.TestBaseApiTemplate;
import org.springframework.social.impl.test_support.impl.resources.test.TestTemplate;
import org.springframework.social.impl.test_support.settings.TestBaseApiClientPlatformSettings;
import org.springframework.social.impl.test_support.settings.TestBaseApiClientSecuritySettings;
import org.springframework.social.impl.test_support.settings.TestBaseApiClientSettings;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.LinkedMultiValueMap;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.alf.har.log.AlfHarLog;
import com.sportslabs.amp.har.dto.creator.HarCreator;

/**
 * Unit tests demonstrating capabilities of {@link TestBaseApiTemplate} against 
 * the "/test" resource API endpoint, including:
 * 
 * <ul>
 * 	<li>CRUD operations (GET, POST, PUT, DELETE)
 * 	<li>Query operations
 * 	<li>Query builder operations
 * 	<li>Page navigation operations
 * 	<li>HTTP Archive operations
 * 	<li>Replay operations
 * </ul>
 *
 * @author robin
 */
public class TestBaseApiTemplateTest {

	private static final String RESOURCE_FOLDER = "bootstrap/testBaseApiResources";
	private static final String UPDATED_NAME = "Sample Updated"; 
	
	private String get1234ResponseBody;
	private String get1234ResponseBodyXml;
	private String getMockTestBaseApiResourcesResponseBody;
	private String getMockTestBaseApiResourcesResponseBodyWithPaginationQuery;
	private String post1234RequestBody;
	private String getMockTestBaseApiResourcesResponseBodyFirstPage;
	private String getMockTestBaseApiResourcesResponseBodyThirdPage;
	
	private MockRestServiceServer mockServer;
	
	private TestBaseApi testApi;
	private TestBaseApiClientSettings settings;
	private TestBaseApiResource testBaseApiResource;
	
	private Page<TestBaseApiResource> multipleTestBaseApiResources;
	private Page<TestBaseApiResource> multipleTestBaseApiResourcesWithPaginationQuery;
	private Page<TestBaseApiResource> multipleTestBaseApiResourcesFirstPage;
	private Page<TestBaseApiResource> multipleTestBaseApiResourcesSecondPage;
	private Page<TestBaseApiResource> multipleTestBaseApiResourcesThirdPage;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// API client settings configuration
		TestBaseApiClientPlatformSettings platformSettings = new TestBaseApiClientPlatformSettings();
		TestBaseApiClientSecuritySettings securitySettings = new TestBaseApiClientSecuritySettings(TEST_API_KEY);
		settings = new TestBaseApiClientSettings(platformSettings, securitySettings);

		testApi = new TestBaseApiTemplate(settings);

		// Read and store sample API request/response text files as JSON
		get1234ResponseBody = 
				readFileAsJsonString(
						testApi.getObjectMapper(), RESOURCE_FOLDER, "GET_response_1234.json");
		
		getMockTestBaseApiResourcesResponseBody = 
				readFileAsJsonString(
						testApi.getObjectMapper(), RESOURCE_FOLDER, "GET_response_testBaseApiResources.json");
		
		getMockTestBaseApiResourcesResponseBodyWithPaginationQuery = 
				readFileAsJsonString(
						testApi.getObjectMapper(), RESOURCE_FOLDER, "GET_response_testBaseApiResources_with_pagination_query.json");
		
		post1234RequestBody = 
				readFileAsJsonString(
						testApi.getObjectMapper(), RESOURCE_FOLDER, "POST_request_1234.json");
		
		getMockTestBaseApiResourcesResponseBodyFirstPage = 
				readFileAsJsonString(
						testApi.getObjectMapper(), RESOURCE_FOLDER, "GET_response_testBaseApiResources_first_page.json");
		
		getMockTestBaseApiResourcesResponseBodyThirdPage = 
				readFileAsJsonString(
						testApi.getObjectMapper(), RESOURCE_FOLDER, "GET_response_testBaseApiResources_third_page.json");
		
		// XML response
		get1234ResponseBodyXml = readFileAsString(RESOURCE_FOLDER, "GET_response_1234.xml");
		
		mockServer = createServer(testApi.getRestTemplate());
		
		/*
		 * Mock request/response Page<TestBaseApiResource> and TestBaseApiResource objects 
		 */
		testBaseApiResource = sample1TestBaseApiResource();
		multipleTestBaseApiResources = multipleSampleTestBaseApiResources();
		multipleTestBaseApiResourcesWithPaginationQuery = multipleSampleTestBaseApiResourcesWithPaginationQuery();
		multipleTestBaseApiResourcesFirstPage = multipleSampleTestBaseApiResourcesFirstPage();
		multipleTestBaseApiResourcesSecondPage = multipleSampleTestBaseApiResourcesSecondPage();
		multipleTestBaseApiResourcesThirdPage = multipleSampleTestBaseApiResourcesThirdPage();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mockServer = null;
		
		testBaseApiResource = null;
		multipleTestBaseApiResources = null;
		multipleTestBaseApiResourcesWithPaginationQuery = null;
		multipleTestBaseApiResourcesFirstPage = null;
		multipleTestBaseApiResourcesSecondPage = null;
		multipleTestBaseApiResourcesThirdPage = null;
	}

	/*
	 * The TestPaginationUriComponentsBuilder to use to build request URIs 
	 */
	private TestPaginationUriComponentsBuilder uriBuilder() throws URISyntaxException {
		return uriBuilderWithoutApiKey().queryParam(API_KEY_PARAM, TEST_API_KEY);
	}
	
	/*
	 * The TestPaginationUriComponentsBuilder without the API key query parameter (which can be added later)
	 * so that URI strings can match exactly
	 */
	private TestPaginationUriComponentsBuilder uriBuilderWithoutApiKey() throws URISyntaxException {
		return fromUri(new URI("http://localhost:8080/api/" + RESOURCES_PATH), testApi.testOperations());
	}

	@Test
	public void createResource_WithValidRequestBody_PerformsPOSTRequestToCorrectURIAndWithCorrectResponse() throws Exception {
		MediaType expectedContentType = new MediaType("application", "json", UTF_8);
		
		mockServer.expect(requestTo(uriBuilder().build().toUri()))
			.andExpect(method(POST))
			.andExpect(header(ACCEPT, "application/json, application/xml, application/*+json, text/xml, application/*+xml"))
			.andExpect(header(USER_AGENT, TEST_CLIENT_USER_AGENT))
			.andExpect(content().contentType(expectedContentType))
			.andExpect(content().string(post1234RequestBody))
			.andRespond(withSuccess(get1234ResponseBody, APPLICATION_JSON));
		
		testApi.configureSettings(configureSettingsAsAuthorized());

		TestBaseApiResource result = testApi.testOperations().create(testBaseApiResource);

		// explicit
		assertThat(result.getId(), equalTo(testBaseApiResource.getUid()));
		assertThat(result.getName(), equalTo(testBaseApiResource.getName()));
		assertThat(result.getCreateDate(), equalTo(testBaseApiResource.getCreateDate()));
		assertThat(result.getActive(), equalTo(true));
	}
	
	@Test
	public void getResource_WithValidResourceId_PerformsGETRequestToCorrectURIAndWithCorrectResponse() throws Exception {
		URI expectedUri = uriBuilder().path(RESOURCE_ID_PATH).build().expand(testBaseApiResource.getId()).toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(header(ACCEPT, "application/json, application/xml, application/*+json, text/xml, application/*+xml"))
			.andExpect(method(GET))
			.andRespond(withSuccess(get1234ResponseBody, APPLICATION_JSON));
		
		TestBaseApiResource result = testApi.testOperations().get(testBaseApiResource.getId());
		
		assertThat(result.getId(), equalTo(testBaseApiResource.getId()));
	}
	
	@Test
	public void getXmlResource_WithValidResourceId_PerformsGETRequestToCorrectURIAndWithCorrectResponse() throws Exception {
		URI expectedUri = uriBuilder().path(RESOURCE_ID_PATH).build().expand(testBaseApiResource.getId()).toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(header(ACCEPT, "application/json, application/xml, application/*+json, text/xml, application/*+xml"))
			.andExpect(method(GET))
			.andRespond(withSuccess(get1234ResponseBodyXml, APPLICATION_XML));
		
		TestBaseApiResource result = testApi.testOperations().get(testBaseApiResource.getId());
		
		assertThat(result, equalTo(testBaseApiResource));
	}
	
	@Test
	public void updateResource_WithValidResourceId_PerformsPUTRequestToCorrectURIAndResponseReflectsUpdate() throws Exception {
		TestBaseApiResource test1234Resource = testApi.getObjectMapper().readValue(get1234ResponseBody, TestBaseApiResource.class);
		test1234Resource.setName(UPDATED_NAME);
		
		URI expectedUri = uriBuilder().path(RESOURCE_ID_PATH).build().expand(testBaseApiResource.getId()).toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(PUT))
			.andRespond(withSuccess(testApi.getObjectMapper().writeValueAsString(test1234Resource), APPLICATION_JSON));
		
		testBaseApiResource.setName(UPDATED_NAME);
		
		testApi.configureSettings(configureSettingsAsAuthorized());
		
		TestBaseApiResource result = testApi.testOperations().update(testBaseApiResource);

		assertThat(result, equalTo(testBaseApiResource));
	}
	
	@Test
	public void deleteResource_WithValidSessionIdAndResourceId_PerformsDELETERequestToCorrectURIAndResponseReturnsDeletedResource() throws Exception {
		URI expectedUri = uriBuilder().path(RESOURCE_ID_PATH).build().expand(testBaseApiResource.getId()).toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(DELETE))
			.andRespond(withSuccess(get1234ResponseBody, APPLICATION_JSON));
		
		testApi.configureSettings(configureSettingsAsAuthorized());
		
		TestBaseApiResource result = testApi.testOperations().delete(testBaseApiResource.getId());
		
		assertThat(result.getId(), equalTo(testBaseApiResource.getId()));
	}
	
	@Test
	public void queryResources_WithoutQueryParams_PerformsGETRequestToCorrectURIAndResponsePageMetadataAndDataIsCorrect() throws Exception {
		URI expectedUri = uriBuilder().build().toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(getMockTestBaseApiResourcesResponseBody, APPLICATION_JSON));
		
		Page<TestBaseApiResource> result = testApi.testOperations().query();
		
		// page metadata 
		assertThat(result.getMetadata().isFirstPage(), equalTo(multipleTestBaseApiResources.getMetadata().isFirstPage()));
		assertThat(result.getMetadata().isLastPage(), equalTo(multipleTestBaseApiResources.getMetadata().isLastPage()));
		assertThat(result.getMetadata().getNumber(), equalTo(multipleTestBaseApiResources.getMetadata().getNumber()));
		assertThat(result.getMetadata().getNumberOfElements(), equalTo(multipleTestBaseApiResources.getMetadata().getNumberOfElements()));
		assertThat(result.getMetadata().getSize(), equalTo(multipleTestBaseApiResources.getMetadata().getSize()));
		assertThat(result.getMetadata().getTotalElements(), equalTo(multipleTestBaseApiResources.getMetadata().getTotalElements()));
		assertThat(result.getMetadata().getTotalPages(), equalTo(multipleTestBaseApiResources.getMetadata().getTotalPages()));
		
		List<Sort> actualSorts = result.getMetadata().getSorts();
		List<Sort> expectedSorts = multipleTestBaseApiResources.getMetadata().getSorts();
		
		assertThat(actualSorts, equalTo(null));
		assertThat(actualSorts, equalTo(expectedSorts));		
	}
	
	@Test
	public void queryResources_WithCustomUriBuilder_PerformsGETRequestToCorrectURIAsApiKeyParameterIsAddedThroughInterceptor() throws Exception {
		TestPaginationUriComponentsBuilder builder = uriBuilder();
		
		URI expectedUri = builder.build().toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(getMockTestBaseApiResourcesResponseBody, APPLICATION_JSON));
		
		Page<TestBaseApiResource> result = (Page<TestBaseApiResource>) testApi.testOperations().query(uriBuilderWithoutApiKey());
		
		// page metadata 
		assertThat(result.getMetadata().isFirstPage(), equalTo(multipleTestBaseApiResources.getMetadata().isFirstPage()));
	}
	
	@Test
	public void queryBuilder_WithPaginationBuilderMethods_PerformsGETRequestToCorrectURIAndWithCorrectResponse() throws Exception {
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.put(PAGE_PREFIX + PAGE, singletonList("1"));
		params.put(PAGE_PREFIX + PAGE_SIZE, singletonList("25"));
		params.put(PAGE_PREFIX + SORT, singletonList("name"));
		params.put(PAGE_PREFIX + DIRECTION, singletonList("ASC"));
		
		URI expectedUri = uriBuilderWithoutApiKey()
				.queryParams(params)
				.queryParam(API_KEY_PARAM, TEST_API_KEY)
				.build().toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(getMockTestBaseApiResourcesResponseBodyWithPaginationQuery, APPLICATION_JSON));
		
		TestPaginationUriComponentsBuilder uriBuilder = 
				(TestPaginationUriComponentsBuilder) testApi.testOperations().qb().withPaging(1, 25, "name", Sort.Direction.ASC);
		
		Page<TestBaseApiResource> result = uriBuilder.query();
		
		// page metadata 
		PageMetadata actualMetadata = result.getMetadata();
		PageMetadata expectedMetadata = multipleTestBaseApiResourcesWithPaginationQuery.getMetadata();
		
		assertThat(actualMetadata.isFirstPage(), equalTo(expectedMetadata.isFirstPage()));
		assertThat(actualMetadata.isLastPage(), equalTo(expectedMetadata.isLastPage()));
		assertThat(actualMetadata.getNumber(), equalTo(expectedMetadata.getNumber()));
		assertThat(actualMetadata.getNumberOfElements(), equalTo(expectedMetadata.getNumberOfElements()));
		assertThat(actualMetadata.getSize(), equalTo(expectedMetadata.getSize()));
		assertThat(actualMetadata.getTotalElements(), equalTo(expectedMetadata.getTotalElements()));
		assertThat(actualMetadata.getTotalPages(), equalTo(expectedMetadata.getTotalPages()));
		
		List<Sort> actualSorts = actualMetadata.getSorts();
		List<Sort> expectedSorts = expectedMetadata.getSorts();
		
		assertThat(actualSorts.size(), equalTo(1));
		assertThat(actualSorts.size(), equalTo(expectedSorts.size()));
		
		for (int i = 0; i < actualSorts.size(); i++) {
			assertEquals(actualSorts.get(i), expectedSorts.get(i));			
		}
	}
	
	@Test
	public void previousPage_WithSecondPageResponseAsLastResult_ResponseIsFirstPage() throws Exception {
		TestTemplate spy = (TestTemplate) spy(testApi.testOperations());

		doReturn(uriBuilderWithoutApiKey()
				.queryParam(PAGE_PREFIX + PAGE, 2)
				.queryParam(PAGE_PREFIX + PAGE_SIZE, 2))
			.when(spy).qb();
		
		URI expectedUri = uriBuilderWithoutApiKey()
				.queryParam(PAGE_PREFIX + PAGE, 1)
				.queryParam(PAGE_PREFIX + PAGE_SIZE, 2)
				.queryParam(API_KEY_PARAM, TEST_API_KEY)
				.build().toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(getMockTestBaseApiResourcesResponseBodyFirstPage, APPLICATION_JSON));

		Page<TestBaseApiResource> result = spy.previousPage(multipleTestBaseApiResourcesSecondPage);
		
		// page metadata 
		PageMetadata actualFirstPageMetadata = result.getMetadata();
		PageMetadata expectedFirstPageMetadata = multipleTestBaseApiResourcesFirstPage.getMetadata();
		
		assertThat(actualFirstPageMetadata.isFirstPage(), equalTo(expectedFirstPageMetadata.isFirstPage()));
		assertThat(actualFirstPageMetadata.isLastPage(), equalTo(expectedFirstPageMetadata.isLastPage()));
		assertThat(actualFirstPageMetadata.getNumber(), equalTo(expectedFirstPageMetadata.getNumber()));
	}
	
	@Test
	public void nextPage_WithSecondPageAsLastResult_ResponseIsThirdPage() throws Exception {
		TestTemplate spy = (TestTemplate) spy(testApi.testOperations());

		doReturn(uriBuilderWithoutApiKey()
				.queryParam(PAGE_PREFIX + PAGE, 2)
				.queryParam(PAGE_PREFIX + PAGE_SIZE, 2)).when(spy).qb();
		
		URI expectedUri = uriBuilderWithoutApiKey()
				.queryParam(PAGE_PREFIX + PAGE, 3)
				.queryParam(PAGE_PREFIX + PAGE_SIZE, 2)
				.queryParam(API_KEY_PARAM, TEST_API_KEY)
				.build().toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(getMockTestBaseApiResourcesResponseBodyThirdPage, APPLICATION_JSON));

		Page<TestBaseApiResource> result = (Page<TestBaseApiResource>) spy.nextPage(multipleTestBaseApiResourcesSecondPage);
		
		// page metadata 
		PageMetadata actualThirdPageMetadata = result.getMetadata();
		PageMetadata expectedThirdPageMetadata = multipleTestBaseApiResourcesThirdPage.getMetadata();
		
		assertThat(actualThirdPageMetadata.isFirstPage(), equalTo(expectedThirdPageMetadata.isFirstPage()));
		assertThat(actualThirdPageMetadata.isLastPage(), equalTo(expectedThirdPageMetadata.isLastPage()));
		assertThat(actualThirdPageMetadata.getNumber(), equalTo(expectedThirdPageMetadata.getNumber()));
	}
	
	@Test
	public void configureSettings_WithDifferentBaseApiPath_PerformsGETRequestToURIWithNewBaseApiPath() throws Exception {
		String newBaseApiPath = "newApiBasePath";
		
		URI expectedUri =
			uriBuilder()
				.replacePath(newBaseApiPath)
				.path(RESOURCES_PATH)
				.path(RESOURCE_ID_PATH).buildAndExpand(testBaseApiResource.getId())
				.toUri();
		
		// expected request URI with new base path
		mockServer.expect(requestTo(expectedUri))
			.andExpect(header(ACCEPT, "application/json, application/xml, application/*+json, text/xml, application/*+xml"))
			.andExpect(method(GET))
			.andRespond(withSuccess(get1234ResponseBody, APPLICATION_JSON));

		TestBaseApiClientSettings settings = 
				new TestBaseApiClientSettings(
						new TestBaseApiClientPlatformSettings(newBaseApiPath), 
						new TestBaseApiClientSecuritySettings(TEST_API_KEY, FAKE_SESSION_ID));
		
		testApi.configureSettings(settings).testOperations().get(testBaseApiResource.getId());
	}
	
	@Test
	public void queryHarLog_WithApiInteraction_ReturnsHarEntryFromHarLog() throws Exception {
		// Start H2 in-memory database
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(H2).addScript("schema.sql").build();
		
		// Start H2 web viewer; database can be viewed at http://localhost:8082
		Server webServer = createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
		
		// Audit Log configuration
		AsyncJdbcAlfHarRepository harRepository = new AsyncJdbcAlfHarRepository();
		harRepository.setDataSource(db);
		
		ListOfJdbcHarEntriesToAlfHarConverter listOfHarEntriesDomainToHarDtoConverter = 
				new ListOfJdbcHarEntriesToAlfHarConverter(testApi.getObjectMapper());
		
		TestBaseApiClientSettings clientSettings = (TestBaseApiClientSettings) testApi.getSettings();
		
		AlfHarEntryToJdbcHarEntryConverter harEntryDtoToHarEntryDomainConverter = 
				new AlfHarEntryToJdbcHarEntryConverter(testApi.getObjectMapper(), clientSettings.getSecuritySettings().getApiKey(), TEST_API_PROVIDER_ID);
		
		JdbcAlfHarServiceImpl harService = new JdbcAlfHarServiceImpl(
				harRepository, 
				listOfHarEntriesDomainToHarDtoConverter, 
				harEntryDtoToHarEntryDomainConverter);
		
		JdbcAlfHarTemplate harTemplate = new JdbcAlfHarTemplate(harService);

		testApi = new TestBaseApiTemplate(settings, harTemplate);		
		mockServer = createServer(testApi.getRestTemplate());
		
		URI expectedUri = uriBuilder().path(RESOURCE_ID_PATH).build().expand(testBaseApiResource.getId()).toUri();
		
		String expectedAcceptHeaderValue = "application/json, application/xml, application/*+json, text/xml, application/*+xml";
		
		// Execute GET request
		mockServer.expect(requestTo(expectedUri))
			.andExpect(header(ACCEPT, expectedAcceptHeaderValue))
			.andExpect(method(GET))
			.andRespond(withSuccess(get1234ResponseBody, APPLICATION_JSON));
		
		testApi.testOperations().get(testBaseApiResource.getId());
		
		// sleep to ensure the asynchronous HAR log database save has completed
		sleep(250);
		
		AlfHarEntry harEntry = (AlfHarEntry) testApi.harOperations().query().getLog().getEntries().get(0);
		
		AlfHarEntry expectedEntry = alfHarEntryWithTimeSensitiveInfoSupplied(harEntry.getStartedDateTime(), harEntry.getTime());
		
		assertThat(harEntry, equalTo(expectedEntry));
		
		db.shutdown();
		webServer.shutdown();
	}
	
	@Test
	public void replayAuditLog_WithGETRequestInAuditLog_ExecutesTheGETRequestAgain() throws Exception {
		mockServer = createServer(testApi.replayOperations().getRestTemplate());
		
		URI expectedUri = uriBuilder().path(RESOURCE_ID_PATH).build().expand(testBaseApiResource.getId()).toUri();
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andExpect(header(ACCEPT, "application/json, application/xml, application/*+json, text/xml, application/*+xml"))
			.andExpect(header(CONTENT_LENGTH, "0"))
			.andExpect(header(USER_AGENT, "Test User Agent"))
			.andRespond(withSuccess(get1234ResponseBody, APPLICATION_JSON));
		
		ArrayList<AlfHarEntry> entries = new ArrayList<AlfHarEntry>();
		entries.add(alfHarEntryWithTimeSensitiveInfoSupplied(JULY_1_2014_MIDNIGHT_UTC, 1000));
		
		HarCreator harCreator = new HarCreator("Har Mar Interceptor", "1.0.0", null);
		AlfHarLog log = new AlfHarLog(SPEC_VERSION, harCreator, entries);
		AlfHar alfHar = new AlfHar(log);

		testApi.replayOperations().replay(alfHar, new Interval.NoInterval());
	}
	
	/**
	 * Pretend that the client setting credentials have been authorized by the API by setting a session ID 
	 * 
	 * @return
	 */
	private TestBaseApiClientSettings configureSettingsAsAuthorized() {
		TestBaseApiClientPlatformSettings platformSettings = new TestBaseApiClientPlatformSettings();
		TestBaseApiClientSecuritySettings securitySettings = new TestBaseApiClientSecuritySettings(TEST_API_KEY, FAKE_SESSION_ID);
		
		return new TestBaseApiClientSettings(platformSettings, securitySettings);
	}
	
}
