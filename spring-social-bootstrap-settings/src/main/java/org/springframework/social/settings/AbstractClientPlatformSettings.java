/**
 * 
 */
package org.springframework.social.settings;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.Assert.notNull;

import java.net.URI;

import org.slf4j.Logger;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Abstract implementation of {@link ClientPlatformSettings} to define the base URI of an API 
 * from a scheme, host name, port, path, etc.
 *
 * @author robin
 */
public abstract class AbstractClientPlatformSettings implements ClientPlatformSettings {
	
	private static final Logger LOG = getLogger(AbstractClientPlatformSettings.class);
	
	private final UriComponentsBuilder baseUriBuilder;
	
	/**
	 * Default scheme is "http"
	 * 
	 * @param hostname
	 */
	public AbstractClientPlatformSettings(final String hostname) {
		this("http", hostname);
	}
	
	public AbstractClientPlatformSettings(final String scheme, final String hostname) {
		this(scheme, hostname, null, null);
	}

	public AbstractClientPlatformSettings(final String scheme, final String hostname, final Integer port, final String basePath) {
		notNull(scheme, "scheme must not be null");
		notNull(hostname, "hostname must not be null");
		
		this.baseUriBuilder = createBaseUriBuilder(scheme, hostname, port, basePath);
	}
	
	public AbstractClientPlatformSettings(final URI baseUri) {
		notNull(baseUri, "baseUri must not be null");
		
		this.baseUriBuilder = createBaseUriBuilder(baseUri);
	}
	
	private UriComponentsBuilder createBaseUriBuilder(final URI baseUri) {
		return UriComponentsBuilder.fromUri(baseUri);
	}
	
	private UriComponentsBuilder createBaseUriBuilder(final String scheme, final String hostname, final Integer port, final String basePath) {
		StringBuilder sb = new StringBuilder();
		sb.append(scheme + "://");
		sb.append(hostname);
		sb.append(port != null ? ":" + port : "");
		sb.append(basePath != null ? "/" + basePath : "");
		String uri = sb.toString();
		
		LOG.debug("Base URI calculated was {} from inputs {}, {}, {}, {}", uri, scheme, hostname, port, basePath);
		
		return UriComponentsBuilder.fromUriString(uri);
	}
	
	/**
	 * @return the baseUriBuilder
	 */
	@Override
	public UriComponentsBuilder getBaseUriBuilder() {
		return baseUriBuilder;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractClientPlatformSettings [baseUriBuilder=" + baseUriBuilder + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((baseUriBuilder == null) ? 0 : baseUriBuilder.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractClientPlatformSettings other = (AbstractClientPlatformSettings) obj;
		if (baseUriBuilder == null) {
			if (other.baseUriBuilder != null)
				return false;
		} else if (!baseUriBuilder.equals(other.baseUriBuilder))
			return false;
		return true;
	}
	
}
