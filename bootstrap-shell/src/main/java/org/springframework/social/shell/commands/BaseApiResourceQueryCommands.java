package org.springframework.social.shell.commands;

import org.springframework.shell.converters.CliPrinterTypeConverter;
import org.springframework.shell.core.annotation.CliPrinter;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.operations.resources.BaseApiResourceQueryOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

public interface BaseApiResourceQueryCommands<R extends BaseApiResource, P extends BaseApiPage<R>, B extends BaseApiUriComponentsBuilder<?>> extends BaseApiResourceCommands {
	
	public P query(@CliPrinter CliPrinterTypeConverter<R> printer);
	
	@Override
	public BaseApiResourceQueryOperations<R, P, B> resourceOperations();

}
