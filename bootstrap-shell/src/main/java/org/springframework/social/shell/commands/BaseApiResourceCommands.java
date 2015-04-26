/**
 * 
 */
package org.springframework.social.shell.commands;

import org.springframework.social.BaseApi;
import org.springframework.social.operations.resources.BaseApiResourceOperations;

/**
 * Base interface for {@link BaseApi}-based CLI commands
 *
 * @author robin
 */
public interface BaseApiResourceCommands {
	
	BaseApiResourceOperations<?> resourceOperations();

}
