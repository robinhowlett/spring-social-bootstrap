/**
 * 
 */
package org.springframework.social.operations.resources;

import org.springframework.web.util.BaseApiUriComponentsBuilder;


/**
 * Spring Social advises favoring organizing the API binding hierarchically by RESTful resource
 * 
 * <p>This interface provides a base interface for operations to be performed on resource API endpoints. 
 * As per the Spring Social naming convention, implementing classes of this interface should have names 
 * ending with "Template"
 *
 * @author robin
 */
public interface BaseApiResourceOperations<B extends BaseApiUriComponentsBuilder<?>> {
	
	/**
	 * "qb" stands for Query Builder. This allows progressively building a 
	 * request using a builder pattern provided by {@link BaseApiUriComponentsBuilder} 
	 * 
	 * @return {@link BaseApiUriComponentsBuilder}
	 */
	public B qb();

}
