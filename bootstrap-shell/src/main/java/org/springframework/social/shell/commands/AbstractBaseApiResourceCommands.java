/**
 * 
 */
package org.springframework.social.shell.commands;

import java.io.IOException;
import java.util.logging.Level;

import org.springframework.shell.converters.CliPrinterTypeConverter;
import org.springframework.shell.core.AbstractStepExecutionProcessor;
import org.springframework.shell.core.CliPrinterResult;
import org.springframework.shell.core.annotation.CliPrinter;
import org.springframework.shell.event.ParseResult;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.social.shell.navigation.StepNavigation;

/**
 * Abstract implementation of {@link BaseApiResourceCommands} with multi-step and {@link CliPrinter} support
 *
 * @author robin
 */
public abstract class AbstractBaseApiResourceCommands<R extends BaseApiResource, P extends BaseApiPage<R>> extends AbstractStepExecutionProcessor implements BaseApiResourceCommands {
	
	protected int stepCount = 1;
	protected boolean hasMoreSteps;
	
	@Override
	public abstract BaseApiResourceOperations<?> resourceOperations();
	
	@Override
	public ParseResult beforeInvocation(ParseResult invocationContext) {
		stepCount = 1;
		hasMoreSteps = true;
		
		return super.beforeInvocation(invocationContext);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void handleStepExecutionResult(ParseResult invocationContext, Object stepResult) {
		if (stepResult != null) {
			// if a page
			if (BaseApiPage.class.isAssignableFrom(stepResult.getClass())) {
				shell.getLogger().info(new CliPrinterResult<P>((P) stepResult, (CliPrinterTypeConverter<P>) invocationContext.getPrinter()).toString());
			} 
			// or if an API resource 
			else if (BaseApiResource.class.isAssignableFrom(stepResult.getClass())) {
				shell.getLogger().info(new CliPrinterResult<R>((R) stepResult, (CliPrinterTypeConverter<R>) invocationContext.getPrinter()).toString());
			} 
			// or other
			else {
				shell.getLogger().info(new CliPrinterResult<Object>(stepResult, (CliPrinterTypeConverter<Object>) invocationContext.getPrinter()).toString());
			}
		}		
	}
	
	@Override
	protected boolean hasMoreSteps(Object stepResult) {
		return hasMoreSteps;
	}
	
	@Override
	protected Object configureStep(Object stepResult) {
		try {
			shell.getLogger().info(StepNavigation.inputPrompt());
			
			return readStepNavigationInput();
		} catch (IOException e) {
			shell.getLogger().log(Level.SEVERE, e.getMessage());
		}
		
		return null;
	}
	
	protected StepNavigation readStepNavigationInput() throws IOException {
		int key = shell.getReader().readCharacter();
		
		shell.getReader().flush();
		
		if ((key == StepNavigation.NEXT.getInputChar()) || 
				(key == StepNavigation.NEW_LINE.getInputChar()) || 
				(key == StepNavigation.RETURN.getInputChar())) {
			stepCount++;
			return StepNavigation.NEXT;
		}
		
		if (key == StepNavigation.PREVIOUS.getInputChar()) {
			stepCount--;
			if (stepCount < 1) {
				stepCount = 1;
			}
			return StepNavigation.PREVIOUS;
		} 
		
		if (key == StepNavigation.QUIT.getInputChar()) {
			return StepNavigation.QUIT;
		}
		
		return null;
	}
	
	@Override
	protected Object executeStep(Object stepResult, Object stepConfig) {		
		hasMoreSteps = false;
		
		return stepResult;
	}

}
