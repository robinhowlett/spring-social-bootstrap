/**
 * 
 */
package org.springframework.social.shell;

import org.springframework.social.BaseApi;


/**
 * Base abstract class for {@link BaseApi} CLI
 *
 * @author robin
 */
public abstract class BaseApiCli {
	
	/**
	 * The packages to scan (Java configuration support)
	 * 
	 * @return
	 */
	public abstract String[] basePackages();

}
