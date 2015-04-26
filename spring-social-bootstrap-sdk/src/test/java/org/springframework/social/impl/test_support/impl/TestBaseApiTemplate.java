/**
 * 
 */
package org.springframework.social.impl.test_support.impl;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.springframework.social.impl.test_support.settings.TestBaseApiClientSecuritySettings.API_KEY_PARAM;
import static org.springframework.social.support.ClientHttpRequestFactorySelector.getRequestFactory;
import static org.springframework.util.Assert.notNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.social.client.ApiKeyParameterRequestInterceptor;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.ser.PageDeserializer;
import org.springframework.social.har.client.jdbc_alf.JdbcAlfHarInterceptor;
import org.springframework.social.har.impl.alf.ReplayAlfHarTemplate;
import org.springframework.social.har.impl.jdbc_alf.JdbcAlfHarTemplate;
import org.springframework.social.har.service.jdbc_alf.JdbcAlfHarServiceImpl;
import org.springframework.social.impl.AbstractBaseApiTemplate;
import org.springframework.social.impl.TestBaseApiTemplateTest;
import org.springframework.social.impl.test_support.TestBaseApi;
import org.springframework.social.impl.test_support.client.SessionIdRequestInterceptor;
import org.springframework.social.impl.test_support.dto.TestBaseApiResource;
import org.springframework.social.impl.test_support.impl.resources.test.TestTemplate;
import org.springframework.social.impl.test_support.operations.resources.test.TestOperations;
import org.springframework.social.impl.test_support.settings.DefaultTestBaseApiClientSettings;
import org.springframework.social.impl.test_support.settings.TestBaseApiClientSettings;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Example extension of {@link AbstractBaseApiTemplate} implementing {@link TestBaseApi}
 * 
 * <ul>
 * 	<li>Custom configures the {@link ObjectMapper} and {@link ClientHttpRequestInterceptor}s
 * 	<li>Initializes the {@link TestTemplate} resource template
 * </ul>
 *  
 * @author robin
 */
public class TestBaseApiTemplate extends AbstractBaseApiTemplate implements TestBaseApi {

	private static final JdbcAlfHarTemplate NO_AUDIT_LOG_OPERATIONS = null;
	
	private TestOperations testOperations;
	
	private JdbcAlfHarTemplate harTemplate;
	private ReplayAlfHarTemplate replayTemplate;
	
	public TestBaseApiTemplate() {
		this(new DefaultTestBaseApiClientSettings());
	}
	
	public TestBaseApiTemplate(String apiKey, char[] sessionId) {
		this(new DefaultTestBaseApiClientSettings(apiKey, sessionId));
	}
	
	public TestBaseApiTemplate(TestBaseApiClientSettings settings) {
		this(settings, NO_AUDIT_LOG_OPERATIONS);
	}
	
	public TestBaseApiTemplate(TestBaseApiClientSettings settings, JdbcAlfHarTemplate harTemplate) {
		super(settings);
		this.harTemplate = harTemplate;
		
		if (harTemplate != null) {
			getRestTemplate().getInterceptors().add(createHarInterceptor(harOperations().getHarService()));
		}
	}

	/**
	 * Allows re-configuring the base URIs, security settings etc. at runtime
	 * 
	 * Here for demonstration purposes only; see {@link TestBaseApiTemplateTest#configureSettings_WithDifferentBaseApiPath_PerformsGETRequestToURIWithNewBaseApiPath()}
	 */
	public TestBaseApiTemplate configureSettings(TestBaseApiClientSettings settings) {
		setSettings(settings);
		initializeResourceOperations();
		
		return this;
	}
	
	/**
	 * Initializes the {@link TestTemplate} resource template
	 */
	@Override
	protected void initializeResourceOperations() {
		this.testOperations = new TestTemplate(getSettings(), getRestTemplate(), isAuthorized());
		this.replayTemplate = new ReplayAlfHarTemplate(new RestTemplate(getRequestFactory()));
	}
	
	@Override
	protected ObjectMapper createObjectMapper() {
		JodaMapper mapper = new JodaMapper();
		mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
		
		final SimpleModule module = new SimpleModule();
		module.addDeserializer(Page.class, new PageDeserializer(TestBaseApiResource.class));
		mapper.registerModule(module);
		
		return mapper;
	}
	
	@Override
	public TestBaseApiClientSettings getSettings() {
		return (TestBaseApiClientSettings) super.getSettings();
	}

	/**
	 * A {@link TestBaseApi} user is authorized if a session ID has been stored
	 */
	@Override
	public boolean isAuthorized() {
		return (getSettings().getSecuritySettings().getSessionId() != null);
	}

	/**
	 * Adds an API key and, if authorized, a session ID as query parameters key-value pairs to all request URLs
	 */
	@Override
	protected void addInterceptorsToRestTemplate() {
		getRestTemplate().getInterceptors().add(
				new ApiKeyParameterRequestInterceptor(API_KEY_PARAM, getSettings().getSecuritySettings().getApiKey()));
		
		if (isAuthorized()) {
			getRestTemplate().getInterceptors().add(new SessionIdRequestInterceptor(new String(getSettings().getSecuritySettings().getSessionId())));
		}
	}
	
	@Override
	public JdbcAlfHarInterceptor createHarInterceptor(JdbcAlfHarServiceImpl harService) {
		return new JdbcAlfHarInterceptor(harService);
	}
	
	/**
	 * Add both a JSON {@link ObjectMapper} (set on a {@link MappingJackson2HttpMessageConverter}) 
	 * and an XML {@link XmlMapper} (set on a {@link MappingJackson2XmlHttpMessageConverter})
	 */
	@Override
	protected void associateObjectMapperWithRestTemplate() {
		List<HttpMessageConverter<?>> messageConverters = getRestTemplate().getMessageConverters();
		List<HttpMessageConverter<?>> newMessageConverters = new ArrayList<>();
		
		for (HttpMessageConverter<?> converter : messageConverters) {
			if (converter.getClass().isAssignableFrom(MappingJackson2HttpMessageConverter.class)) {
				// delay adding until after loop
			} else if (converter.getClass().isAssignableFrom(MappingJackson2XmlHttpMessageConverter.class)) {
				// delay adding until after loop
			} else {
				newMessageConverters.add(converter);
			}	
		}
		
		// add JSON support first
		MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
		jacksonMessageConverter.setObjectMapper(getObjectMapper());
		newMessageConverters.add(jacksonMessageConverter);
		
		// then XML (with Joda support)
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.registerModule(new JodaModule());
		MappingJackson2XmlHttpMessageConverter xmlMessageConverter = new MappingJackson2XmlHttpMessageConverter();
		xmlMessageConverter.setObjectMapper(xmlMapper);
		newMessageConverters.add(xmlMessageConverter);
		
		getRestTemplate().setMessageConverters(newMessageConverters);
	}
	
	@Override
	public TestOperations testOperations() {
		return testOperations;
	}
	
	@Override
	public JdbcAlfHarTemplate harOperations() {
		notNull(harTemplate, "harTemplate has not been initialized");
		
		return harTemplate;
	}
	
	@Override
	public ReplayAlfHarTemplate replayOperations() {
		return replayTemplate;
	}
	
}