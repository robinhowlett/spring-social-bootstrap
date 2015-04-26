/**
 * 
 */
package org.springframework.social.har.client;

import static java.lang.System.nanoTime;

import java.io.IOException;

import org.apache.commons.collections4.Predicate;
import org.joda.time.DateTime;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.har.service.HarService;

import com.sportslabs.amp.har.dto.Har;
import com.sportslabs.amp.har.dto.entries.HarCache;
import com.sportslabs.amp.har.dto.entries.HarEntry;
import com.sportslabs.amp.har.dto.entries.HarEntryTimings;
import com.sportslabs.amp.har.dto.entries.HarRequest;
import com.sportslabs.amp.har.dto.entries.HarResponse;

/**
 * {@link ClientHttpRequestInterceptor} that executes the given {@link HttpRequest} and creates a {@link HarEntry}
 *
 * @author robin
 * @see <a href="http://www.softwareishard.com/blog/har-12-spec/">HTTP Archive (HAR) 1.2 Spec</a>
 */
public abstract class AbstractHarInterceptor<D extends org.springframework.social.har.domain.HarEntry, E extends HarEntry, H extends Har> implements HarInterceptor {
	
	private final HarService<D, E, H> harService;
	private final Predicate<HttpRequest> requestPredicate;
	
	public AbstractHarInterceptor(HarService<D, E, H> harService) {
		this(harService, persistAllRequestsFilter());
	}
	
	public AbstractHarInterceptor(HarService<D, E, H> harService, Predicate<HttpRequest> requestPredicate) {
		this.harService = harService;
		this.requestPredicate = requestPredicate;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.http.client.ClientHttpRequestInterceptor#intercept(org.springframework.http.HttpRequest, byte[], org.springframework.http.client.ClientHttpRequestExecution)
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] requestBody,
			ClientHttpRequestExecution execution) throws IOException {
		long startNano 					= nanoTime();
		ClientHttpResponse httpResponse	= execution.execute(httpRequest, requestBody);
		long endNano 					= nanoTime();
		
		if (getRequestPredicate().evaluate(httpRequest)) {
			String pageref 				= createPageref(httpRequest, requestBody, httpResponse);
			DateTime startedDateTime	= createStartedDateTime(startNano);
			Integer time 				= createTime(startNano, endNano);
			HarRequest request 			= createHarRequest(httpRequest, requestBody);
			HarResponse response 		= createHarResponse(httpResponse);
			HarCache cache 				= createHarCache(httpRequest, requestBody, httpResponse);
			HarEntryTimings timings		= createHarEntryTimings(time);
			String serverIPAddress 		= createServerIPAddress(httpRequest, requestBody, httpResponse);
			String connection 			= createConnection(httpRequest, requestBody, httpResponse);
			String comment 				= createComment(httpRequest, requestBody, httpResponse);
			
			createOthers(httpRequest, requestBody, httpResponse);
	
			E entry = createHarEntry(pageref, startedDateTime, time, request, response, cache, 
					timings, serverIPAddress, connection, comment);
			
			saveHarEntry(entry);
		}
		
		return httpResponse;
	}
	
	protected abstract String createComment(HttpRequest httpRequest, byte[] requestBody,
			ClientHttpResponse httpResponse);

	protected abstract String createConnection(HttpRequest httpRequest,
			byte[] requestBody, ClientHttpResponse httpResponse);

	protected abstract String createServerIPAddress(HttpRequest httpRequest,
			byte[] requestBody, ClientHttpResponse httpResponse);

	protected abstract String createPageref(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse);
	
	protected abstract E createHarEntry(String pageref, DateTime startedDateTime, Integer time,
			HarRequest request, HarResponse response, HarCache cache, HarEntryTimings timings,
			String serverIPAddress, String connection, String comment);
	
	protected abstract HarRequest createHarRequest(HttpRequest httpRequest, byte[] requestBody);
	
	protected abstract HarResponse createHarResponse(ClientHttpResponse httpResponse);
	
	protected abstract HarCache createHarCache(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse);
	
	protected abstract void createOthers(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse);

	protected DateTime createStartedDateTime(long startNano) {
		return new DateTime(startNano / 1000000);
	}

	protected int createTime(long startNano, long endNano) {
		return (int) ((endNano - startNano) / 1000000);
	}	
	
	protected HarEntryTimings createHarEntryTimings(Integer time) {
		Integer receive = time;
		HarEntryTimings timings = new HarEntryTimings(
				null, 		// blocked
				null, 		// dns
				null, 		// connect
				0, 			// send
				0, 			// wait
				receive, 	
				null, 		// ssl
				null		// comment
		);
		
		return timings;
	}

	protected void saveHarEntry(E entry) {
		getHarService().save(entry);
	}
	
	protected static Predicate<HttpRequest> persistAllRequestsFilter() {
		return new Predicate<HttpRequest>() {
			@Override
			public boolean evaluate(HttpRequest object) {
				return true;
			}
		};
	}

	/**
	 * @return the harService
	 */
	public HarService<D, E, H> getHarService() {
		return harService;
	}

	/**
	 * @return the requestPredicate
	 */
	public Predicate<HttpRequest> getRequestPredicate() {
		return requestPredicate;
	}

}
