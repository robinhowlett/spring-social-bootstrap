<p align="center">![Bootstrap Shell](http://i.imgur.com/PypWRjx.png)

**Bootstrap Shell** is a [Spring Shell](http://docs.spring.io/spring-shell/docs/current/reference/htmlsingle/)-based framework to aid creating command-line interface (CLI) applications for API clients built on [Spring Social Bootstrap SDK](https://github.com/robinhowlett/spring-social-bootstrap/tree/master/spring-social-bootstrap-sdk).

_e.g. [Mockbin CLI](https://github.com/robinhowlett/mockbin-cli): an Bootstrap Shell-based CLI application for [Mockbin](http://mockbin.com/) powered by [Spring Social Mockbin](https://github.com/robinhowlett/spring-social-mockbin)_
![CLI Example](http://i.imgur.com/8Eca4p3.gif)

## Spring Shell Influence

The underlying Spring Shell version is based on version 1.1.0.RELEASE but with the following modifications:

* [SHL-106: Java Configuration support](https://github.com/spring-projects/spring-shell/pull/66): Permits configuring a Spring Shell application using Java `@Configuration` classes instead of XML ([JIRA](https://jira.spring.io/browse/SHL-106))
* [SHL-174: Multi-Step Commands](https://github.com/spring-projects/spring-shell/pull/67): Permits commands annotated with `@CliStepIndicator` to perform additional logic during its execution, including accepting user input e.g. pagination instructions ([JIRA](https://jira.spring.io/browse/SHL-174))
* [SHL-175: Multiple Output Formats for Commands](https://github.com/spring-projects/spring-shell/pull/68): Permits command results to be printed to the console with different formats by using Spring Type Converters, denoted by `@CliPrinter` annotations on Command parameters ([JIRA](https://jira.spring.io/browse/SHL-175))

CLI applications based on Bootstrap Shell can also be executed from within an IDE, although the output is formatted for Terminals. Installing an ANSI-compatible console in your IDE is recommended e.g. [ANSI Escape in Console](http://www.mihai-nita.net/eclipse) for Eclipse.

## Creating a CLI application for an API Client built on Spring Social Bootstrap SDK

The process of developing a CLI application for an API client based on Spring Social Bootstrap SDK consists of just a few steps:

1. Create a base CLI class that extends `BaseApiCli` and specify the base package path to scan in the `basePackages()` method.
1. Create a [`@Configuration` class](https://github.com/spring-projects/spring-shell/pull/66) that declares an instance of an Spring Social Bootstrap SDK-based API client.
1. Create `@Component` Command classes that either implement Spring Shell's `CommandMarker` or `ExecutionProcessor` interfaces.
1. `@Autowire` the Spring Social Bootstrap SDK-based API client that was declared in the configuration.
1. Create command methods annotated with `@CliCommand` that execute operations on the API client. Parameters annotated with `@CliOption` denote arguments to the command.

### Multi-Step Commands

Traditionally, Spring Shell will execute a command and then return control to the terminal. 

[Multi-step commands](https://github.com/spring-projects/spring-shell/pull/67) allow interrupting this flow by adding additional control either before or after the original command has been invoked. This can be useful for user control of the CLI application e.g. paginating paged API responses.

* If multi-step commands are desired, the Command class should extend the `AbstractStepExecutionProcessor` abstract class that adds multi-step support instead of implementing the `CommandMarker` or `ExecutionProcessor` interfaces. 
* Annotate multi-step Command methods with the additional `@CliStepIndicator` annotation
* Implement the following methods:
	* `handleStepExecutionResult(ParseResult invocationContext, Object stepResult)`
	* `hasMoreSteps(Object stepResult)`
	* `configureStep(Object stepResult)`
	* `executeStep(Object stepResult, Object stepConfig)`
	
### Multiple Command Output Formats

Traditionally, Spring Shell will log the output of a command result's `toString()` method.

If commands that [support multiple output formats](https://github.com/spring-projects/spring-shell/pull/68) are desired, then the following steps are required:

1. As the final argument to a Command method, add the following: `@CliPrinter CliPrinterTypeConverter<XYZ> printer`, where `XYZ` is the `BaseApiResource` type of the command result
	* By default, the `@CliPrinter` argument is bound to the CLI parameter `p` e.g. `--p json`. The key may be customized on the annotation.
	* A default value may also be specified on the `@CliPrinter` annotation
1. Declare an instance of a converter that implements `CliPrinterTypeConverter` in the `@Configuration` class.
	* The bean name **_must_** follow the naming convention of `"xyzOutputConverter"`, where `xyz` will be the argument value passed to the `@CliPrinter CliPrinterTypeConverter` command method argument e.g. `jsonOutputConverter()` will permit the following example command result to be printed as JSON: `access-log --id 123456 --p json`
	* Spring Shell Bootstrap CLI ships with `JsonCliPrinterTypeConverter` that will write the JSON representation of a `BaseApiResource` using Jackson's `ObjectMapper`. 