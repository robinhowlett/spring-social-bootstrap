/**
 * 
 */
package org.springframework.social.shell.commands;

import org.springframework.shell.converters.CliPrinterTypeConverter;
import org.springframework.social.BaseApi;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.operations.resources.BaseApiResourceCRUDOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * {@link BaseApi} CLI CRUD operations 
 * 
 * @author robin
 */
public interface BaseApiResourceCRUDCommands<R extends BaseApiResource, P extends BaseApiPage<R>, B extends BaseApiUriComponentsBuilder<?>> extends BaseApiResourceQueryCommands<R, P, B> {
	
	public R get(String uid, CliPrinterTypeConverter<R> printer);
	public R create(R resource, CliPrinterTypeConverter<R> printer);
	public R update(R resource, CliPrinterTypeConverter<R> printer);
	public R delete(String uid, CliPrinterTypeConverter<R> printer);
	
	@Override
	public BaseApiResourceCRUDOperations<R, P, B> resourceOperations();

}
