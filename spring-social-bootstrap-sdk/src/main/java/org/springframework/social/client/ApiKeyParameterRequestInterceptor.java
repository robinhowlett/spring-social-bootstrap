/**
 * 
 */
package org.springframework.social.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.support.HttpRequestDecorator;

/**
 * A {@link ClientHttpRequestInterceptor} to append an API key query parameter 
 * (named by {@code apiKeyParamName}) with an API key value to the request
 *
 * @author robin
 */
public class ApiKeyParameterRequestInterceptor implements ClientHttpRequestInterceptor {
	
	private static final Logger LOG = getLogger(ApiKeyParameterRequestInterceptor.class);
	
	private String apiKeyParamName;
	private String apiKey;
	
	public ApiKeyParameterRequestInterceptor(String apiKeyParamName, String apiKey) {
		this.apiKeyParamName = apiKeyParamName;
		this.apiKey = apiKey;
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.client.ClientHttpRequestInterceptor#intercept(org.springframework.http.HttpRequest, byte[], org.springframework.http.client.ClientHttpRequestExecution)
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		HttpRequestDecorator requestDecorator = new HttpRequestDecorator(request);
		requestDecorator.addParameter(apiKeyParamName, apiKey);
		LOG.debug("Adding API key URL parameter named {} with value {} to request", apiKeyParamName, apiKey);
		
		return execution.execute(requestDecorator, body);
	}

}
