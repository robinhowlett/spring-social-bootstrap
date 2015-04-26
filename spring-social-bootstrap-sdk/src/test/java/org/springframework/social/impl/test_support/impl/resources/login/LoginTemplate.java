/**
 * 
 */
package org.springframework.social.impl.test_support.impl.resources.login;

import static org.springframework.social.support.ClientHttpRequestFactorySelector.getRequestFactory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.Charsets;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.impl.test_support.settings.DefaultTestBaseApiClientSettings;
import org.springframework.social.impl.test_support.settings.TestBaseApiClientSecuritySettings;
import org.springframework.social.impl.test_support.settings.TestBaseApiClientSettings;
import org.springframework.social.operations.resources.auth.AuthOperations;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Abstract implementation of {@link AuthOperations} that expects a username and password, 
 * creates a {@link RestTemplate} and configures it with a {@link FormHttpMessageConverter} 
 *
 * @author robin
 */
public class LoginTemplate implements AuthOperations {
	
	public static final char[] FAKE_SESSION_ID = "123456".toCharArray();
	
	private final char[] username;
	private final char[] password;
	private final RestTemplate restTemplate;

	public LoginTemplate(char[] username, char[] password) {
		this(new DefaultTestBaseApiClientSettings());
	}
	
	public LoginTemplate(TestBaseApiClientSettings settings) {
		this.username = ((TestBaseApiClientSecuritySettings) settings.getSecuritySettings()).getUsername();
		this.password = ((TestBaseApiClientSecuritySettings) settings.getSecuritySettings()).getPassword();
		
		this.restTemplate = createRestTemplate();
	}
	
	/**
	 * Initialize a {@link RestTemplate} and add a {@link FormHttpMessageConverter} and a 
	 * {@link StringHttpMessageConverter} to its message converters
	 * 
	 * @return
	 */
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(getRequestFactory());
		
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(1);
		
		converters.add(new FormHttpMessageConverter() {
			public boolean canRead(Class<?> clazz, MediaType mediaType) {
				/*
				 * always read MultiValueMaps as x-www-url-formencoded 
				 * even if contentType not set properly by provider				
				 */
				return MultiValueMap.class.isAssignableFrom(clazz);
			}
		});
		
		// we may need to interact with non-JSON text content
		converters.add(new StringHttpMessageConverter(Charsets.UTF_8));
		
		restTemplate.setMessageConverters(converters);
		
		return restTemplate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.social.operations.resources.auth.AuthOperations#getRestTemplate()
	 */
	@Override
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	/**
	 * Fake the authorization step for demonstration purposes, and return a hard-coded value.
	 * 
	 * Normally the {@link RestTemplate} would be used to to send a request to an authorization endpoint
	 */
	@Override
	public char[] authorize() {
		return FAKE_SESSION_ID;
	}

	/**
	 * @return the username
	 */
	public char[] getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public char[] getPassword() {
		return password;
	}

}
