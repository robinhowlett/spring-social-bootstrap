/**
 * 
 */
package org.springframework.social.shell;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.joda.time.DateTimeZone.UTC;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.pagination.Page.PageMetadata;
import org.springframework.social.impl.AbstractBaseApiTemplate;
import org.springframework.social.settings.ClientSettings;
import org.springframework.social.shell.test_support.commands.TestBaseApiCliCRUDCommands;
import org.springframework.social.shell.test_support.dto.TestBaseApiCliResource;
import org.springframework.social.shell.test_support.operations.resources.TestBaseApiCliResourceCRUDTemplate;
import org.springframework.social.shell.test_support.shell.TestBaseApiCli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration test for {@link TestBaseApiCli}'s {@link TestBaseApiCliCRUDCommands}
 *
 * @author robin
 */
public class TestBaseApiCliITest {
	
	protected static Bootstrap bootstrap;
	
	private static JLineShellComponent shell;
	
	@BeforeClass
	public static void startUp() throws InterruptedException {
		TestBaseApiCli cli = new TestBaseApiCli();
		
		bootstrap = new Bootstrap(cli.basePackages());
		
		shell = bootstrap.getJLineShellComponent();
	}
	
	@AfterClass
	public static void shutdown() {
		shell.stop();
	}
	
	public static JLineShellComponent getShell() {
		return shell;
	}

	/**
	 * Test method for {@link org.springframework.social.shell.test_support.commands.TestBaseApiCliCRUDCommands#get(java.lang.String)}.
	 */
	@Test
	public void testGetCommand() {
		TestBaseApiCliResource expected = new TestBaseApiCliResource("1234");
		
		CommandResult cr = getShell().executeCommand("api g --id 1234");

		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected.toString()));	
	}
	
	/**
	 * Test method for {@link org.springframework.social.shell.test_support.commands.TestBaseApiCliCRUDCommands#create(java.lang.String)}.
	 */
	@Test
	public void testCreateCommand() {
		TestBaseApiCliResource expected = new TestBaseApiCliResource("1234");
		
		CommandResult cr = getShell().executeCommand("api c --id 1234");

		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected.toString()));	
	}
	
	/**
	 * Test method for {@link org.springframework.social.shell.test_support.commands.TestBaseApiCliCRUDCommands#update(java.lang.String)}.
	 */
	@Test
	public void testUpdateCommand() {
		TestBaseApiCliResource expected = new TestBaseApiCliResource("5678");
		
		CommandResult cr = getShell().executeCommand("api u --id 5678");

		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected.toString()));
	}
	
	/**
	 * Test method for {@link org.springframework.social.shell.test_support.commands.TestBaseApiCliCRUDCommands#get(java.lang.String)}.
	 */
	@Test
	public void testDeleteCommand() {
		TestBaseApiCliResource expected = new TestBaseApiCliResource("1234");
		
		CommandResult cr = getShell().executeCommand("api d --id 1234");

		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected.toString()));	
	}
	
	/**
	 * Test method for {@link org.springframework.social.shell.test_support.commands.TestBaseApiCliCRUDCommands#query()}.
	 */
	@Test
	public void testQueryCommand() {
		TestBaseApiCliResource one = new TestBaseApiCliResource("1234");
		TestBaseApiCliResource two = new TestBaseApiCliResource("5678");
		
		Page<TestBaseApiCliResource> expected = new Page<TestBaseApiCliResource>(Arrays.asList(one, two), new PageMetadata(2));
		
		CommandResult cr = getShell().executeCommand("api q");

		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected.toString()));	
	}
	
	@Test
	public void testStepSingleResourceCommand() throws Exception {
		String expected = "1"; 
		
		getShell().executeCommand("api step-resource");
		
		CommandResult cr = getShell().executeCommand("api step-check");
		
		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected.toString()));
	}
	
	@Test
	public void testStepMultipleResourcesCommand() throws Exception {
		String expected = "1"; 
		
		getShell().executeCommand("api step-page");
		
		CommandResult cr = getShell().executeCommand("api step-check");
		
		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected.toString()));
	}
	
	@Test
	public void testStepObjectCommand() throws Exception {
		String expected = "1"; 
		
		getShell().executeCommand("api step-object");
		
		CommandResult cr = getShell().executeCommand("api step-check");
		
		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected.toString()));
	}
	
	@Test
	public void testGetWithCustomOutputCommand() throws JsonProcessingException {
		TestBaseApiCliResource one = new TestBaseApiCliResource("1234");
		String expected = new ObjectMapper().writeValueAsString(one);
		
		CommandResult cr = getShell().executeCommand("api gwco --id 1234 --p json");

		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected));	
	}
	
	@Test
	public void testGetWithCustomOutputCommandAndStep() throws JsonProcessingException {
		String expected = "1";
		
		getShell().executeCommand("api gwcos --id 1234 --p json");
		
		CommandResult cr = getShell().executeCommand("api step-check");
		
		String result = cr.getResult().toString();
		
		assertThat(result, equalTo(expected));	
	}
	
	public static class TestBaseApiCliTemplate extends AbstractBaseApiTemplate {

		TestBaseApiCliResourceCRUDTemplate testOperations;
		
		public TestBaseApiCliTemplate() {
			super(mockClientSettings());
		}

		private static ClientSettings mockClientSettings() {
			ClientSettings clientSettings = mock(ClientSettings.class);
			doReturn(UTC).when(clientSettings).getDefaultTimeZone();
			
			return clientSettings;
		}

		@Override
		protected void initializeResourceOperations() {
			TestBaseApiCliResourceCRUDTemplate resourceCRUDTemplate = mock(TestBaseApiCliResourceCRUDTemplate.class);
			
			TestBaseApiCliResource resource = new TestBaseApiCliResource("1234");
			TestBaseApiCliResource updatedResource = new TestBaseApiCliResource("5678");
			BaseApiPage<TestBaseApiCliResource> page = new Page<TestBaseApiCliResource>(asList(resource, updatedResource), new PageMetadata(2));
			
			doReturn(resource).when(resourceCRUDTemplate).get(anyString());
			doReturn(resource).when(resourceCRUDTemplate).create(any(TestBaseApiCliResource.class));
			doReturn(updatedResource).when(resourceCRUDTemplate).update(any(TestBaseApiCliResource.class));
			doReturn(resource).when(resourceCRUDTemplate).delete(anyString());
			doReturn(page).when(resourceCRUDTemplate).query();
			
			this.testOperations = resourceCRUDTemplate;
		}
		
		public TestBaseApiCliResourceCRUDTemplate testOperations() {
			return testOperations;
		}

		@Override
		public boolean isAuthorized() {
			return false;
		}

		@Override
		protected void addInterceptorsToRestTemplate() { }
		
	}

}
