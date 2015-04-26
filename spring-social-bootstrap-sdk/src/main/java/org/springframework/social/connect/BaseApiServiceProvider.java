/**
 * 
 */
package org.springframework.social.connect;

import org.springframework.social.BaseApi;
import org.springframework.social.ServiceProvider;
import org.springframework.social.operations.resources.auth.AuthOperations;

/**
 * Base {@link ServiceProvider} interface for {@link BaseApi}s
 * 
 * <p>The service provider's {@link AuthOperations} conducts the "authorization" dance to 
 * authorize a connection to a {@link BaseApi} service provider, allowing it to return an 
 * authorized instance of {@link BaseApi} that the application can invoke 
 * on behalf of a user. 
 *
 * @author robin
 */
public interface BaseApiServiceProvider<A extends BaseApi> extends ServiceProvider<A> {

	public AuthOperations getAuthOperations();

}
