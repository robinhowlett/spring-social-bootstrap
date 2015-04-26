/**
 * 
 */
package org.springframework.web.util;

import static org.springframework.util.Assert.notNull;

import java.net.URI;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.operations.resources.BaseApiResourceQueryOperations;
import org.springframework.util.MultiValueMap;

/**
 * Generic-friendly extension to {@link UriComponentsBuilder}
 * 
 * <p>Provides a {@code fromUri(URI, BaseApiResourceQueryOperations<? extends BaseApiResource>} factory method for creating a new instance, 
 * and a {@code query()} method to execute a query against a resource API endpoint using the builder to customize the request URL
 *
 * @author robin
 * 
 * @param <B> Type of {@link BaseApiUriComponentsBuilder} 
 */
@SuppressWarnings("unchecked")
public class BaseApiUriComponentsBuilder<B extends BaseApiUriComponentsBuilder<B>> extends UriComponentsBuilder {
	
	private final BaseApiResourceQueryOperations<? extends BaseApiResource, ? extends BaseApiPage<? extends BaseApiResource>, B> resourceTemplate;
	
	protected BaseApiUriComponentsBuilder(BaseApiResourceQueryOperations<? extends BaseApiResource, ? extends BaseApiPage<? extends BaseApiResource>, B> resourceTemplate) {
		super();
		this.resourceTemplate = resourceTemplate;
	}

	/**
	 * Factory method that returns a builder initialized with the given {@code URI} and {@link BaseApiResourceQueryOperations}.
	 * 
	 * @param uri the URI to initialize with
	 * @return the new {@code BaseApiUriComponentsBuilder}
	 */
	public static <B extends BaseApiUriComponentsBuilder<B>> BaseApiUriComponentsBuilder<B> fromUri(URI uri, BaseApiResourceQueryOperations<? extends BaseApiResource, ? extends BaseApiPage<? extends BaseApiResource>, ? extends BaseApiUriComponentsBuilder<?>> resourceTemplate) {
		BaseApiUriComponentsBuilder<B> builder = new BaseApiUriComponentsBuilder<B>((BaseApiResourceQueryOperations<? extends BaseApiResource, ? extends BaseApiPage<? extends BaseApiResource>, B>) resourceTemplate);
		builder.uri(uri);
		return builder;
	}

	/**
	 * @return the resourceTemplate
	 */
	public BaseApiResourceQueryOperations<? extends BaseApiResource, ? extends BaseApiPage<? extends BaseApiResource>, B> getResourceTemplate() {
		return resourceTemplate;
	}

	/**
	 * Execute a query against a resource API endpoint using the builder to customize the request URL
	 * 
	 * @return A {@link BaseApiPage} of {@link BaseApiResource}s
	 */
	public BaseApiPage<? extends BaseApiResource> query() {
		notNull(getResourceTemplate(), "A non-null BaseApiResourceOperations instance must be provided "
				+ "to query from a BaseApiUriComponentsBuilder");
		
		return getResourceTemplate().query((B) this);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#uri(java.net.URI)
	 */
	@Override
	public B uri(URI uri) {
		return (B) super.uri(uri);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#scheme(java.lang.String)
	 */
	@Override
	public B scheme(String scheme) {
		return (B) super.scheme(scheme);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#uriComponents(org.springframework.web.util.UriComponents)
	 */
	@Override
	public B uriComponents(UriComponents uriComponents) {
		return (B) super.uriComponents(uriComponents);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#schemeSpecificPart(java.lang.String)
	 */
	@Override
	public B schemeSpecificPart(String ssp) {
		return (B) super.schemeSpecificPart(ssp);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#userInfo(java.lang.String)
	 */
	@Override
	public B userInfo(String userInfo) {
		return (B) super.userInfo(userInfo);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#host(java.lang.String)
	 */
	@Override
	public B host(String host) {
		return (B) super.host(host);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#port(int)
	 */
	@Override
	public B port(int port) {
		return (B) super.port(port);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#port(java.lang.String)
	 */
	@Override
	public B port(String port) {
		return (B) super.port(port);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#path(java.lang.String)
	 */
	@Override
	public B path(String path) {
		return (B) super.path(path);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#replacePath(java.lang.String)
	 */
	@Override
	public B replacePath(String path) {
		return (B) super.replacePath(path);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#pathSegment(java.lang.String[])
	 */
	@Override
	public B pathSegment(String... pathSegments)
			throws IllegalArgumentException {
		return (B) super.pathSegment(pathSegments);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#query(java.lang.String)
	 */
	@Override
	public B query(String query) {
		return (B) super.query(query);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#replaceQuery(java.lang.String)
	 */
	@Override
	public B replaceQuery(String query) {
		return (B) super.replaceQuery(query);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#queryParam(java.lang.String, java.lang.Object[])
	 */
	@Override
	public B queryParam(String name, Object... values) {
		return (B) super.queryParam(name, values);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#queryParams(org.springframework.util.MultiValueMap)
	 */
	@Override
	public B queryParams(MultiValueMap<String, String> params) {
		return (B) super.queryParams(params);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#replaceQueryParam(java.lang.String, java.lang.Object[])
	 */
	@Override
	public B replaceQueryParam(String name, Object... values) {
		return (B) super.replaceQueryParam(name, values);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.util.UriComponentsBuilder#fragment(java.lang.String)
	 */
	@Override
	public B fragment(String fragment) {
		return (B) super.fragment(fragment);
	}

}
