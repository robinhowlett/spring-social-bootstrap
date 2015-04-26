/**
 * 
 */
package org.springframework.social.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.support.HttpRequestDecorator;

/**
 * Preemptive Authentication is including authentication/authorization credentials on 
 * requests without being told by a service provider that credentials are required. It 
 * is designed to reduce the number of requests required to be made by clients.
 * 
 * <p>A {@link ClientHttpRequestInterceptor} to set an {@code Authorization} 
 * HTTP header value for preemptive Basic Authentication requests.
 *
 * @author robin
 */
public class PreemptiveBasicAuthRequestInterceptor implements ClientHttpRequestInterceptor {
	
	private static final Logger LOG = getLogger(PreemptiveBasicAuthRequestInterceptor.class);
	
	private String authorization;

	public PreemptiveBasicAuthRequestInterceptor(String authorization) {
		this.authorization = authorization;
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.client.ClientHttpRequestInterceptor#intercept(org.springframework.http.HttpRequest, byte[], org.springframework.http.client.ClientHttpRequestExecution)
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		HttpRequestDecorator requestDecorator = new HttpRequestDecorator(request);
		requestDecorator.getHeaders().set(HttpHeaders.AUTHORIZATION, authorization);
		LOG.debug("Adding Basic Authentication {} header with value {} to request", HttpHeaders.AUTHORIZATION, authorization);
		
		return execution.execute(requestDecorator, body);
	}

}
