package org.springframework.social.shell.test_support.commands;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.converters.CliPrinterTypeConverter;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.core.annotation.CliPrinter;
import org.springframework.shell.core.annotation.CliStepIndicator;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.impl.test_support.builders.TestPaginationCliUriComponentsBuilder;
import org.springframework.social.operations.resources.BaseApiResourceCRUDOperations;
import org.springframework.social.shell.TestBaseApiCliITest;
import org.springframework.social.shell.TestBaseApiCliITest.TestBaseApiCliTemplate;
import org.springframework.social.shell.commands.AbstractBaseApiResourceCRUDCommands;
import org.springframework.social.shell.test_support.dto.TestBaseApiCliResource;
import org.springframework.social.shell.test_support.shell.TestBaseApiCli;
import org.springframework.stereotype.Component;

/**
 * CRUD commands for {@link TestBaseApiCli}
 * 
 * <p>See {@link TestBaseApiCliITest} for executions of these commands
 * 
 * @author robin
 */
@Component
public class TestBaseApiCliCRUDCommands extends AbstractBaseApiResourceCRUDCommands<TestBaseApiCliResource, BaseApiPage<TestBaseApiCliResource>, TestPaginationCliUriComponentsBuilder> {
	
	private static final String PROVIDER_ID = "api";
	
	private TestBaseApiCliTemplate api;
	
	private int steps = 0;

	@Autowired
	public TestBaseApiCliCRUDCommands(TestBaseApiCliTemplate api) {
		this.api = api;
	}
	
	@CliCommand(value={ PROVIDER_ID + " " + "g"})
	public TestBaseApiCliResource get(@CliOption(key="id") String uid) {
		return super.get(uid, null);
	}
	
	@CliCommand(value={ PROVIDER_ID + " " + "c"})
	public TestBaseApiCliResource create(@CliOption(key="id") String uid) {
		return super.create(new TestBaseApiCliResource(uid), null);
	};
	
	@CliCommand(value={ PROVIDER_ID + " " + "u"})
	public TestBaseApiCliResource update(@CliOption(key="id") String uid) {
		return super.update(new TestBaseApiCliResource(uid), null);
	};

	@CliCommand(value={ PROVIDER_ID + " " + "d"})
	public TestBaseApiCliResource delete(@CliOption(key="id") String uid) {
		return super.delete(uid, null);
	}

	@CliCommand(value={ PROVIDER_ID + " " + "q"})
	public BaseApiPage<TestBaseApiCliResource> query() {
		return super.query(null);
	};

	@CliCommand(value={ PROVIDER_ID + " " + "step-resource"})
	@CliStepIndicator
	public TestBaseApiCliResource stepResource(@CliOption(key="id") String uid) {
		return super.get(uid, null);
	}
	
	@CliCommand(value={ PROVIDER_ID + " " + "step-page"})
	@CliStepIndicator
	public BaseApiPage<TestBaseApiCliResource> stepPage() {
		return super.query(null);
	}
	
	@CliCommand(value={ PROVIDER_ID + " " + "step-object"})
	@CliStepIndicator
	public Collection<TestBaseApiCliResource> stepObject() {
		return super.query(null).getContent();
	}
	
	@CliCommand(value={ PROVIDER_ID + " " + "step-check"})
	public int stepCheck() {
		return steps;
	}

	@CliCommand(value={ PROVIDER_ID + " " + "gwco"})
	public TestBaseApiCliResource getWithCustomOutput(
			@CliOption(key="id") String uid,
			@CliPrinter CliPrinterTypeConverter<TestBaseApiCliResource> printer) {
		return super.get(uid, printer);
	};
	
	@CliCommand(value={ PROVIDER_ID + " " + "gwcos"})
	@CliStepIndicator
	public TestBaseApiCliResource getWithCustomOutputAndStep(
			@CliOption(key="id") String uid,
			@CliPrinter CliPrinterTypeConverter<TestBaseApiCliResource> printer) {
		return super.get(uid, printer);
	};

	/* (non-Javadoc)
	 * @see org.springframework.social.AbstractBaseApiResourceCRUDCommands#resourceOperations()
	 */
	@Override
	public BaseApiResourceCRUDOperations<TestBaseApiCliResource, BaseApiPage<TestBaseApiCliResource>, TestPaginationCliUriComponentsBuilder> resourceOperations() {
		return api.testOperations();
	}

	@Override
	protected boolean hasMoreSteps(Object stepResult) {
		return steps < 1;
	}

	@Override
	protected Object configureStep(Object stepResult) {	
		steps++;
		return null;
	}

	@Override
	protected Object executeStep(Object stepResult, Object stepConfig) {
		return stepResult;
	}

}
