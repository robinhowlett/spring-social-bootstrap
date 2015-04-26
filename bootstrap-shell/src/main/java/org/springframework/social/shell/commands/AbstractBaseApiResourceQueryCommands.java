/**
 * 
 */
package org.springframework.social.shell.commands;

import org.springframework.shell.converters.CliPrinterTypeConverter;
import org.springframework.shell.core.annotation.CliPrinter;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.operations.resources.BaseApiResourceQueryOperations;
import org.springframework.social.shell.navigation.StepNavigation;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Query support
 * 
 * @author robin
 */
public abstract class AbstractBaseApiResourceQueryCommands<R extends BaseApiResource, P extends BaseApiPage<R>, B extends BaseApiUriComponentsBuilder<?>> extends AbstractBaseApiResourceCommands<R, P> implements BaseApiResourceQueryCommands<R, P, B> {
	
	@Override
	public P query(@CliPrinter CliPrinterTypeConverter<R> printer) {
		return resourceOperations().query();
	}
	
	@Override
	public abstract BaseApiResourceQueryOperations<R, P, B> resourceOperations();
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object executeStep(Object stepResult, Object stepConfig) {
		StepNavigation input = (StepNavigation) stepConfig;
		
		if (input.equals(StepNavigation.NEXT)) {				
			return (R) resourceOperations().nextPage((P) stepResult);
		} else if (input.equals(StepNavigation.PREVIOUS)) {
			return (R) resourceOperations().previousPage((P) stepResult);
		} else {
			hasMoreSteps = false;
		}
		
		return null;
	}

}
