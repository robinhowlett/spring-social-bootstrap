/**
 * 
 */
package org.springframework.social.impl.test_support.client;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.impl.test_support.impl.TestBaseApiTemplate;
import org.springframework.social.support.HttpRequestDecorator;

/**
 * Example {@link ClientHttpRequestInterceptor} implementation for adding a query parameter 
 * containing the session ID acquired
 *
 * @author robin
 * @see TestBaseApiTemplate addInterceptorsToRestTemplate()
 */
public class SessionIdRequestInterceptor implements ClientHttpRequestInterceptor {
	
	public static final String SESSION_ID_KEY = "session";
	
	private String session;
	
	public SessionIdRequestInterceptor(String session) {
		this.session = session;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.http.client.ClientHttpRequestInterceptor#intercept(org.springframework.http.HttpRequest, byte[], org.springframework.http.client.ClientHttpRequestExecution)
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		HttpRequestDecorator requestDecorator = new HttpRequestDecorator(request);
		requestDecorator.addParameter(SESSION_ID_KEY, this.session);
		
		return execution.execute(requestDecorator, body);
	}

}
