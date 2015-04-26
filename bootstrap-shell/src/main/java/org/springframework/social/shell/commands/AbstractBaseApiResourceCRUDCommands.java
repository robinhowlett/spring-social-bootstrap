/**
 * 
 */
package org.springframework.social.shell.commands;

import org.springframework.shell.converters.CliPrinterTypeConverter;
import org.springframework.shell.core.annotation.CliPrinter;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.operations.resources.BaseApiResourceCRUDOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * CRUD CLI operations with {@link CliPrinter} support
 * 
 * @author robin
 */
public abstract class AbstractBaseApiResourceCRUDCommands<R extends BaseApiResource, P extends BaseApiPage<R>, B extends BaseApiUriComponentsBuilder<?>> extends AbstractBaseApiResourceQueryCommands<R, P, B> implements BaseApiResourceCRUDCommands<R, P, B> {

	@Override
	public R get(String uid, CliPrinterTypeConverter<R> printer) {
		return resourceOperations().get(uid);
	}

	@Override
	public R create(R resource, CliPrinterTypeConverter<R> printer) {
		return resourceOperations().create(resource);
	}

	@Override
	public R update(R resource, CliPrinterTypeConverter<R> printer) {
		return resourceOperations().update(resource);
	}

	@Override
	public R delete(String uid, CliPrinterTypeConverter<R> printer) {
		return resourceOperations().delete(uid);
	}
	
	@Override
	public abstract BaseApiResourceCRUDOperations<R, P, B> resourceOperations();
	
}
