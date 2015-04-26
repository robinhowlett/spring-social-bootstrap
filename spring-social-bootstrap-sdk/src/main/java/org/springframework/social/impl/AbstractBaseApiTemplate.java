/**
 * 
 */
package org.springframework.social.impl;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.social.support.ClientHttpRequestFactorySelector.bufferRequests;
import static org.springframework.social.support.ClientHttpRequestFactorySelector.getRequestFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.BaseApi;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.ser.PageDeserializer;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Abstract implementation of {@link BaseApi}. API-specific Spring Social Bootstrap SDK-compatible implements should extend this class. 
 * 
 * <ul>
 * 	<li>Initializes the Jackson {@link ObjectMapper} and Spring {@link RestTemplate}
 * 	<li>Associates a default Joda {@link DateTimeZone}
 * 	<li>Configures the {@link ObjectMapper} with the {@link RestTemplate}
 * 	<li>Adds a {@link UserAgentRequestInterceptor} for customizing the user agent string used 
 * 	<li>Provides abstract method to further customize {@link ClientHttpRequestInterceptor}s on the {@link RestTemplate}
 * 	<li>Provides abstract method to initialize the API resource operations.
 * </ul>
 *
 * @author robin
 */
public abstract class AbstractBaseApiTemplate implements BaseApi {
	
	private static final Logger LOG = getLogger(AbstractBaseApiTemplate.class);
	
	private final ObjectMapper objectMapper;
	
	private ClientSettings settings;
	private RestTemplate restTemplate;
	
	/**
	 * Initialize the {@link BaseApi} implementation with the provided {@link ClientSettings}.
	 * 
	 * @param settings The {@link ClientSettings} providing API URI, security, time zone and user agent configuration
	 */
	public AbstractBaseApiTemplate(ClientSettings settings) {
		this.settings = settings;
		
		objectMapper = createObjectMapper();
		restTemplate = createRestTemplate();
		
		associateDefaultTimeZoneToObjectMapper(getSettings().getDefaultTimeZone());
		associateObjectMapperWithRestTemplate();
		
		// ensure a custom User Agent string has been provided by the ClientSettings 
		getRestTemplate().getInterceptors().add(new UserAgentRequestInterceptor());
		
		addInterceptorsToRestTemplate();
		
		initializeResourceOperations();
	}
	
	@Override
	public ClientSettings getSettings() {
		return settings;
	}
	
	protected void setSettings(ClientSettings settings) {
		this.settings = settings;
	}

	@Override
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	
	@Override
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	/**
	 * Override this method to customize the Jackson {@link ObjectMapper} being initialized
	 * 
	 * @return A new {@link ObjectMapper}
	 */
	protected ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		final SimpleModule module = new SimpleModule();
		module.addDeserializer(Page.class, new PageDeserializer());
		mapper.registerModule(module);
		
		return mapper;
	}
	
	/**
	 * Override this method to customize the Spring {@link RestTemplate} being initialized
	 * 
	 * Default implementations initializes a {@link RestTemplate} with a buffered request factory. 
	 * Picks a HttpComponentsClientRequestFactory factory if Apache HttpComponents HttpClient is 
	 * in the classpath. 
	 * @return
	 */
	protected RestTemplate createRestTemplate() {
		return new RestTemplate(bufferRequests(getRequestFactory()));
	}

	/**
	 * Implementations of this method can further customize what {@link ClientHttpRequestInterceptor}s 
	 * are configured on the {@link RestTemplate} e.g. ensuring an API key is always included
	 */
	protected abstract void addInterceptorsToRestTemplate();
	
	/**
	 * In accordance with the Spring Social guidance of "favoring organizing the API binding hierarchically by RESTful resource", 
	 * implementations of this method will initialize the various {@link BaseApiResourceOperations} implementations
	 * 
	 * @see BaseApiResourceOperations
	 */
	protected abstract void initializeResourceOperations();
	
	/**
	 * Configure Jackson's {@link ObjectMapper} with a default {@link DateTimeZone}
	 * 
	 * @param defaultTimeZone The default {@link DateTimeZone} to use for serialization and deserialization
	 */
	private void associateDefaultTimeZoneToObjectMapper(DateTimeZone defaultTimeZone) {
		if (defaultTimeZone != null) {
			objectMapper.setTimeZone(defaultTimeZone.toTimeZone());
			LOG.debug("Set default time zone to {}", defaultTimeZone);
		}
	}
	
	/**
	 * Sets the created Jackson {@link ObjectMapper} as a {@link HttpMessageConverter} on the Spring {@link RestTemplate}.
	 * 
	 * @param template
	 */
	protected void associateObjectMapperWithRestTemplate() {
		List<HttpMessageConverter<?>> messageConverters = getRestTemplate().getMessageConverters();
		List<HttpMessageConverter<?>> newMessageConverters = new ArrayList<>();
		for (HttpMessageConverter<?> converter : messageConverters) {
			if (converter.getClass().isAssignableFrom(MappingJackson2HttpMessageConverter.class)) {
				// delay adding until after loop
			} else {
				newMessageConverters.add(converter);
			}
		}
		
		MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
		jacksonMessageConverter.setObjectMapper(getObjectMapper());
		newMessageConverters.add(jacksonMessageConverter);
		
		getRestTemplate().setMessageConverters(newMessageConverters);
	}

	/**
	 * A {@link ClientHttpRequestInterceptor} that sets the "User-Agent" HTTP request header to the value configured on the {@link ClientSettings} 
	 *
	 * @author robin
	 */
	public class UserAgentRequestInterceptor implements ClientHttpRequestInterceptor {

		/* (non-Javadoc)
		 * @see org.springframework.http.client.ClientHttpRequestInterceptor#intercept(org.springframework.http.HttpRequest, byte[], org.springframework.http.client.ClientHttpRequestExecution)
		 */
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			HttpHeaders headers = request.getHeaders();
			String userAgent = getSettings().getUserAgent();
			if (userAgent != null) {
				headers.put(HttpHeaders.USER_AGENT, Collections.singletonList(userAgent));
				LOG.debug("Set User Agent header value to {}", userAgent);
			}
			
			return execution.execute(request, body);
		}

	}

}
