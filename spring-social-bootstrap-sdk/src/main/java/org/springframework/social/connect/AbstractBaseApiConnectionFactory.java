/**
 * 
 */
package org.springframework.social.connect;

import org.springframework.social.BaseApi;
import org.springframework.social.ServiceProvider;
import org.springframework.social.operations.resources.auth.AuthOperations;

/**
 * A simple base abstraction for establishing authorized connections with a factory that constructs 
 * {@link AbstractBaseApiConnection} instances for a {@link BaseApi} service provider.
 * 
 * <p>The connection factory may be used directly, or it may be added to a registry where it can be 
 * used by the framework to establish connections in a dynamic, self-service manner
 * 
 * @author robin
 *
 * @param <A> A {@link BaseApi} type
 */
public abstract class AbstractBaseApiConnectionFactory<A extends BaseApi> extends ConnectionFactory<A> {

	/**
	 * Creates a new ConnectionFactory.
	 * 
	 * @param providerId the assigned, unique id of the provider this factory creates connections to (used when indexing this factory in a registry)
	 * @param serviceProvider the model for the ServiceProvider used to conduct the connection authorization/refresh flow and obtain a native service API instance
	 * @param apiAdapter the adapter that maps common operations exposed by the ServiceProvider's API to the uniform {@link Connection} model
	 */
	public AbstractBaseApiConnectionFactory(String providerId, ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
		super(providerId, serviceProvider, apiAdapter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionFactory#getServiceProvider()
	 */
	@Override
	protected BaseApiServiceProvider<A> getServiceProvider() {
		return (BaseApiServiceProvider<A>) super.getServiceProvider();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionFactory#getApiAdapter()
	 */
	@Override
	protected BaseApiAdapter<A> getApiAdapter() {
		return (BaseApiAdapter<A>) super.getApiAdapter();
	}
	
	public AuthOperations getAuthOperations() {
		return getServiceProvider().getAuthOperations();
	}

}
