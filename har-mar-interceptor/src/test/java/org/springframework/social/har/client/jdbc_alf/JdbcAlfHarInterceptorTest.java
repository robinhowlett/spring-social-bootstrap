package org.springframework.social.har.client.jdbc_alf;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.social.har.dto.mocks.MockHar.JULY_1_2014_MIDNIGHT_UTC;
import static org.springframework.social.har.dto.mocks.MockHarEntries.alfHarEntryWithRequestAndResponse;
import static org.springframework.social.har.dto.mocks.MockHarResponses.helloWorldHtmlContent;
import static org.springframework.social.har.dto.mocks.MockHttpHeaders.tenHttpHeaders;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.social.har.service.jdbc_alf.JdbcAlfHarServiceImpl;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.web.client.RestTemplate;

import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;

/**
 * Test that an {@link JdbcAlfHarInterceptor} registered with a {@link RestTemplate} intercepts HTTP requests, 
 * executes them and creates {@link AlfHarEntry} instances
 *
 * @author robin
 */
public class JdbcAlfHarInterceptorTest {
	
	private RestTemplate restTemplate;
	private MockRestServiceServer mockServer;
	
	private JdbcAlfHarInterceptor spy;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		spy = spy(new JdbcAlfHarInterceptor(mock(JdbcAlfHarServiceImpl.class)));
		
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = 
				new BufferingClientHttpRequestFactory(requestFactory);
		
		restTemplate = new RestTemplate(bufferingClientHttpRequestFactory);
		
		restTemplate.getInterceptors().add(spy);
		
		mockServer = createServer(restTemplate);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mockServer = null;
	}
	
	@Test
	public void intercept_WithRequestWithTenHeaders_InterceptorCreatesCompleteAlfHarEntryOfRequest() throws Exception {
		doReturn(JULY_1_2014_MIDNIGHT_UTC).when(spy).createStartedDateTime(anyLong());
		doReturn(1000).when(spy).createTime(anyLong(), anyLong());
		doReturn("40.140.33.170").when(spy).getClientIPAddress();
		
		DefaultResponseCreator response = 
				withStatus(OK)
					.headers(tenHttpHeaders("20"))
					.body(helloWorldHtmlContent().getText());
		
		URI uri = new URI("http://mockbin.com/bin/3c149e20-bc9c-4c68-8614-048e6023a108");
		
		mockServer.expect(requestTo(uri))
			.andExpect(method(GET))
			.andRespond(response);
		
		restTemplate.exchange(uri, GET, new HttpEntity<String>(tenHttpHeaders("0")), String.class);
		
		// Validate that the interceptor's saveHarEntry() method was called with a complete AlfHarEntry instance
		ArgumentCaptor<AlfHarEntry> harEntryArgumentCaptor = forClass(AlfHarEntry.class);
		
		verify(spy).saveHarEntry(harEntryArgumentCaptor.capture());
		
		AlfHarEntry harEntry = harEntryArgumentCaptor.getValue();
		
		assertThat(harEntry, equalTo(alfHarEntryWithRequestAndResponse()));
	}

}
