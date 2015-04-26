/**
 * 
 */
package org.springframework.social.har.client.jdbc_alf;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.har.client.AbstractHarInterceptor;
import org.springframework.social.har.domain.jdbc_alf.JdbcAlfHarEntry;
import org.springframework.social.har.service.HarService;
import org.springframework.util.MultiValueMap;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarRequest;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarResponse;
import com.sportslabs.amp.har.dto.entries.HarCache;
import com.sportslabs.amp.har.dto.entries.HarContent;
import com.sportslabs.amp.har.dto.entries.HarEntryTimings;
import com.sportslabs.amp.har.dto.entries.HarHeader;
import com.sportslabs.amp.har.dto.entries.HarQueryParam;
import com.sportslabs.amp.har.dto.entries.HarRequest;
import com.sportslabs.amp.har.dto.entries.HarResponse;

/**
 * {@link ClientHttpRequestInterceptor} that executes the given {@link HttpRequest}, creates an {@link AlfHarEntry}, 
 * and asks the {@link HarService} to convert it to a {@link JdbcAlfHarEntry} and save it to the JDBC store
 *
 * @author robin
 * @see <a href="https://github.com/Mashape/api-log-format">API Logging Format (ALF) Spec</a>
 * @see <a href="http://www.softwareishard.com/blog/har-12-spec/">HTTP Archive (HAR) 1.2 Spec</a>
 */
public class JdbcAlfHarInterceptor extends AbstractHarInterceptor<JdbcAlfHarEntry, AlfHarEntry, AlfHar> {
	
	private static final Logger LOG = getLogger(JdbcAlfHarInterceptor.class);
	
	private String mimeType = null;
	private String clientIPAddress = null;
	
	public JdbcAlfHarInterceptor(HarService<JdbcAlfHarEntry, AlfHarEntry, AlfHar> harService) {
		super(harService);
	}
	
	@Override
	protected AlfHarRequest createHarRequest(HttpRequest httpRequest, byte[] requestBody) {
		String method = httpRequest.getMethod().toString();
		
		URL url = null;
		try {
			url = httpRequest.getURI().toURL();
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage());
			// TODO throw error
		}
		
		// Request body text and bodySize
		String text = null;
		Integer bodySize = null;
		if (requestBody != null) {
			try {
				text = new String(requestBody);
			} catch (Exception e) {
				// TODO base64 request body
			}
			
			bodySize = (text != null ? text.getBytes(UTF_8).length : null);
		}
				
		// Request headers, headersSize and, if applicable, postData mimeType
		List<HarHeader> requestHeaders = null;
		Integer headersSize = null;
		
		if (httpRequest != null && httpRequest.getHeaders() != null) {
			requestHeaders = new ArrayList<HarHeader>();
			headersSize = 0;
			
			for (Entry<String, List<String>> requestHeader : httpRequest.getHeaders().entrySet()) {
				String name = requestHeader.getKey();
				String value = collectionToCommaDelimitedString(requestHeader.getValue());
				
				// Calculating header size is not very clear in the spec so this is a best effort for now
				headersSize += name.getBytes(UTF_8).length + ": ".getBytes(UTF_8).length + value.getBytes(UTF_8).length;
				
				// Check if the Content-Type header has been set and, if so, use its value as the postData mimeType
				if (name.equals(HttpHeaders.CONTENT_TYPE)) {
					mimeType = value;
				}
				
				// Create each header
				requestHeaders.add(
						new HarHeader(
							name, 
							value, 
							null	// comment
						)
				);
			}
		}
		
		// Request content
		HarContent content = null;
		if (text != null && !text.isEmpty()) {
			content = new HarContent(
					text.getBytes(UTF_8).length,	// size, 
					null, 							// TODO compression, 
					mimeType, 
					text, 
					null, 							// TODO encoding, 
					null							// comment
			);
		}
		
		// Request query parameters
		List<HarQueryParam> queryString = null;
		if (httpRequest != null && httpRequest.getURI() != null) {
			MultiValueMap<String, String> queryParams = fromUri(httpRequest.getURI()).build().getQueryParams();
			
			if (queryParams != null && !queryParams.isEmpty()) {
				queryString = new ArrayList<HarQueryParam>();

				for (Entry<String, List<String>> queryParam : queryParams.entrySet()) {
					String name = queryParam.getKey();
					String value = collectionToCommaDelimitedString(queryParam.getValue());
					
					queryString.add(new HarQueryParam(
							name, 
							value, 
							null	// comment
					));
				}
			}
		}
		
		AlfHarRequest request = new AlfHarRequest(
				method, 
				url, 
				null, 				// httpVersion (use default), 
				null, 				// cookies, 
				requestHeaders,		// headers 
				queryString, 
				headersSize, 
				bodySize, 
				content
		);
		
		return request;
	}
	
	@Override
	protected AlfHarResponse createHarResponse(ClientHttpResponse httpResponse) {
		Integer status = null;
		String statusText = null;
		try {
			status = (httpResponse != null ? httpResponse.getStatusCode().value() : null);
			statusText = (httpResponse != null ? httpResponse.getStatusCode().getReasonPhrase() : null);
		} catch (IOException e) {
			LOG.error(e.getMessage());
			// TODO throw error
		}
		
		List<HarHeader> responseHeaders = null;
		Integer headersSize = null;
		
		if (httpResponse != null && httpResponse.getHeaders() != null) {
			responseHeaders = new ArrayList<HarHeader>();
			headersSize = 0;
			
			for (Entry<String, List<String>> responseHeader : httpResponse.getHeaders().entrySet()) {
				String name = responseHeader.getKey();
				String value = collectionToCommaDelimitedString(responseHeader.getValue());
				
				// Calculating header size is not very clear in the spec so this is a best effort for now
				headersSize += name.getBytes(UTF_8).length + ": ".getBytes(UTF_8).length + value.getBytes(UTF_8).length;
				
				// Check if the Content-Type header has been set and, if so, use its value as the content mimeType
				if (name.equals(HttpHeaders.CONTENT_TYPE)) {
					mimeType = value;
				}
				
				responseHeaders.add(new HarHeader(
						name, 
						value, 
						null	// comment
				));
			}
		}
		
		String responseContent = null;
		try {
			InputStream responseBodyInputStream = httpResponse.getBody();
			
			responseContent = (responseBodyInputStream != null ? IOUtils.toString(responseBodyInputStream) : null);
			
			if (responseBodyInputStream != null) {
				responseBodyInputStream.reset();
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
			// TODO throw exception
		}
		
		
		int size = (responseContent != null ? responseContent.getBytes(UTF_8).length : 0);
		HarContent content = new HarContent(
				size,	// size, 
				null, 									// TODO compression, 
				mimeType, 
				responseContent,						// text 
				null, 									// TODO encoding, 
				null									// comment
		);
		
		int responseBodySize = size;
		AlfHarResponse response = new AlfHarResponse(
				status, 
				statusText, 
				null, 				// httpVersion (use default),
				responseHeaders,	// headers 
				content,  
				headersSize, 
				responseBodySize 	// bodySize
		);
		
		return response;
	}
	
	@Override
	protected void createOthers(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse) {
		setClientIPAddress(null);
	}
	
	@Override
	protected AlfHarEntry createHarEntry(String pageref,
			DateTime startedDateTime, Integer time, HarRequest request,
			HarResponse response, HarCache cache, HarEntryTimings timings,
			String serverIPAddress, String connection, String comment) {
		AlfHarRequest alfRequest = (AlfHarRequest) request;
		AlfHarResponse alfResponse = (AlfHarResponse) response;
		
		return new AlfHarEntry(startedDateTime, time, alfRequest, alfResponse, timings, serverIPAddress, getClientIPAddress());
	}
	
	@Override
	protected void saveHarEntry(AlfHarEntry entry) {
		super.saveHarEntry(entry);
	}
	
	@Override
	protected DateTime createStartedDateTime(long startNano) {
		return super.createStartedDateTime(startNano);
	}
	
	@Override
	protected int createTime(long startNano, long endNano) {
		return super.createTime(startNano, endNano);
	}
	
	@Override
	protected HarEntryTimings createHarEntryTimings(Integer time) {
		return super.createHarEntryTimings(time);
	}

	@Override
	protected String createComment(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse) {
		return null;
	}

	@Override
	protected String createConnection(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse) {
		return null;
	}

	@Override
	protected String createServerIPAddress(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse) {
		return null;
	}

	@Override
	protected String createPageref(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse) {
		return null;
	}

	@Override
	protected HarCache createHarCache(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse) {
		return null;
	}

	/**
	 * @return the clientIPAddress
	 */
	protected String getClientIPAddress() {
		return clientIPAddress;
	}

	/**
	 * @param clientIPAddress the clientIPAddress to set
	 */
	protected void setClientIPAddress(String clientIPAddress) {
		this.clientIPAddress = clientIPAddress;
	}

}
