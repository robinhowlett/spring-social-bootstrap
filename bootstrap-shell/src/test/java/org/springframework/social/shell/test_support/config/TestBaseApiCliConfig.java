package org.springframework.social.shell.test_support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.social.shell.TestBaseApiCliITest.TestBaseApiCliTemplate;
import org.springframework.social.shell.converters.json.JsonCliPrinterTypeConverter;
import org.springframework.social.shell.test_support.commands.TestBaseApiCliCRUDCommands;
import org.springframework.social.shell.test_support.dto.TestBaseApiCliResource;

@Configuration
@ComponentScan(basePackageClasses=TestBaseApiCliCRUDCommands.class)
public class TestBaseApiCliConfig {

	@Bean
	public TestBaseApiCliTemplate api() {
		return new TestBaseApiCliTemplate();
	}
	
	@Bean
	@DependsOn("api")
	public JsonCliPrinterTypeConverter<TestBaseApiCliResource> jsonOutputConverter() {
		return new JsonCliPrinterTypeConverter<TestBaseApiCliResource>(api().getObjectMapper());
	}
	
}
