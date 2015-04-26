package org.springframework.social.connect;

import org.springframework.social.BaseApi;
import org.springframework.social.connect.support.AbstractConnection;

/**
 * Base support class for {@link BaseApi} {@link Connection} implementations.
 * 
 * <p>Configures the associated {@link BaseApiServiceProvider} and {@link BaseApiAdapter}, and 
 * provides an abstract method to initialize, and store the reference to, the {@link BaseApi} implementation, most likely 
 * through the {@link BaseApiServiceProvider} instance. 
 * 
 * @author robin
 *
 * @param <A> The {@link BaseApi} service provider's type
 */
public abstract class AbstractBaseApiConnection<A extends BaseApi> extends AbstractConnection<A> {
	
	private static final long serialVersionUID = 1L;
	
	private transient final BaseApiServiceProvider<A> serviceProvider;
	
	protected transient A api;

	/**
	 * Creates a new connection
	 * @param apiAdapter
	 * @param serviceProvider
	 */
	public AbstractBaseApiConnection(BaseApiAdapter<A> apiAdapter, BaseApiServiceProvider<A> serviceProvider) {
		super(apiAdapter);
		this.serviceProvider = serviceProvider;
	}

	/**
	 * Creates a new {@link AbstractBaseApiConnection} from the data provided.
	 * Designed to be called when re-constituting an existing {@link Connection} using {@link ConnectionData}.
	 * 
	 * @param data the data holding the state of this connection
	 * @param serviceProvider the {@link BaseApiServiceProvider}
	 * @param apiAdapter the {@link BaseApiAdapter} for the {@link BaseApiServiceProvider}
	 */
	public AbstractBaseApiConnection(ConnectionData data, BaseApiAdapter<A> apiAdapter, BaseApiServiceProvider<A> serviceProvider) {
		super(data, apiAdapter);
		this.serviceProvider = serviceProvider;
	}
	
	/**
	 * Return an initialized {@link BaseApi} instance from the {@link BaseApiServiceProvider}. Intended 
	 * to be assigned to the {@link #api} instance
	 * 
	 * @return The initialized {@link BaseApi} instance
	 */
	protected abstract A initApi();

	/**
	 * @return the serviceProvider
	 */
	public BaseApiServiceProvider<A> getServiceProvider() {
		return serviceProvider;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.social.connect.support.AbstractConnection#getApi()
	 */
	@Override
	public A getApi() {
		return api;
	}
	
}