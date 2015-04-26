/**
 * 
 */
package org.springframework.social.connect;

import org.springframework.social.BaseApi;

/**
 * An adapter that bridges between the uniform {@link Connection} model 
 * and a specific {@link BaseApi} provider API user model
 * 
 * <p>For non social-network-type APIs, the {@link ApiAdapter#test(Object)} 
 * method is of more importance that the methods to set user profile data or 
 * update a "status"
 *
 * @author robin
 */
public interface BaseApiAdapter<A extends BaseApi> extends ApiAdapter<A> {

}
