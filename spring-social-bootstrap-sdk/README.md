<p align="center">![Spring Social Bootstrap SDK](http://i.imgur.com/asX8yGM.jpg)

**Spring Social Bootstrap SDK** builds on [Spring Social] by providing an opinionated foundation for building API clients e.g.

```java
// API client settings configuration
TestBaseApiClientPlatformSettings platformSettings = new TestBaseApiClientPlatformSettings();
TestBaseApiClientSecuritySettings securitySettings = new TestBaseApiClientSecuritySettings(TEST_API_KEY);
settings = new TestBaseApiClientSettings(platformSettings, securitySettings);

// Initialize API client
TestBaseApi testApi = new TestBaseApiTemplate(settings);

// CRUD examples
TestBaseApiResource result = testApi.testOperations().create(testBaseApiResource);

result = testApi.testOperations().get(testBaseApiResource.getId());

testBaseApiResource.setName("New Name");

testApi.testOperations().update(testBaseApiResource)

testApi.testOperations().delete(testBaseApiResource.getId());

// Querying for all
testApi.testOperations().query()

// Custom querying
testApi.testOperations().query(uriComponentsBuilder())

// Query Builder
testApi.testOperations().qb().withPaging(1, 25, "name", Sort.Direction.ASC).query();

// Pagination
testApi.testOperations().previousPage(currentPage);
testApi.testOperations().nextPage(currentPage);

// Configuring settings post-initialization
testApi.configureSettings(settings).testOperations().get(testBaseApiResource.getId());

// Reading the audit log
testApi.auditLogOperations().query().get(0)

// Replaying the audit log; 30 seconds between each request
testApi.replayOperations.replay(auditLogResourcesRequested, new Interval.FixedInterval(30));

```

While Spring Social is advertised as a framework to "connect your Spring application with Software-as-a-Service (SaaS) API providers such as Facebook, Twitter, and LinkedIn", there is no inherent requirement that using it be limited to either SaaS providers or social networks.

Many companies have developed APIs that have perhaps evolved organically, with non-standard response formats, custom auth schemes and/or without strict REST compliance. Spring Social Bootstrap SDK intends to make creating clients of these APIs much easier.

Unlike existing Spring Social projects like [Spring Social Twitter](http://projects.spring.io/spring-social-twitter/) or [Spring Social Facebook](http://projects.spring.io/spring-social-facebook/), Spring Social Bootstrap SDK is not necessarily anticipating integrating against an [OAuth 1.0a- or 2.0](http://oauth.net/)-compatible API or SaaS provider.

Spring Social Bootstrap SDK projects may also be used both inside and outside of a Spring Application Context.

## Spring Social Influence

This project follows the [Spring Social Core] convention of declaring a base interface (`BaseApi`) whose implementation (`AbstractBaseApiTemplate`) provides access to implementations of API resource operations (`BaseApiResourceOperations`).

It also provides interfaces and abstract implementations of Spring Social's [Service Provider 'Connect' Framework] for common, non-OAuth authentication and authorization connections e.g. API keys, session IDs, and/or Basic Authentication.

Spring's `RestTemplate` provides the synchronous client-side HTTP access. `AbstractBaseApiTemplate` configures this client with [Spring Social Bootstrap Settings]' `ClientSettings`, which is composed of:

* `ClientPlatformSettings` (to configure the API environment to be used), and
* `ClientSecuritySettings` (to define the authentication and/or authorization requirements, if any, to connect to an API)

The project provides hierarchical abstract implementations of `BaseApiResourceOperations` that provide basic-, query-, and CRUD-type API operations:

* `AbstractBaseApiResourceTemplate` (uses a provided `RestTemplate` to execute queries, configures the resource API URI template, and can also store the last response received),

* `AbstractBaseApiResourceQueryTemplate` (adds default or custom query capabilities, a builder pattern for queries, and pagination navigation)

* `AbstractBaseApiResourceCRUDTemplate` (adds CRUD-style capabilities)

## Adding Support for a New Service Provider with Spring Social Bootstrap SDK

The process of developing a client for a new service provider based on Spring Social Bootstrap SDK consists of several steps. For most use cases, the following may be sufficient:

1. Create a base API interface extending `BaseApi` for the provider.
1. Create a base resource DTO extending `BaseApiResource`.
1. Create a client platform settings implementation extending `AbstractClientPlatformSettings`.
1. Create a client security settings implementation of `ClientSecuritySettings`, potentially extending one of the provided abstract implementations:
	* `AbstractClientSecurityApiKeySettings`
	* `AbstractClientSecurityApiKeyUsernamePasswordSettings`
	* `AbstractClientSecurityApiKeyBasicAuthSettings`
1. Create a client settings implementation extending `AbstractClientSettings` incorporating the platform and security settings implementations created.
1. Create a resource API interface for the provider extending the desired interface:
	* `BaseApiResourceOperations`
	* `BaseApiResourceQueryOperations`
	* `BaseApiResourceCRUDOperations`
1. Add the resource API operation method to the base API interface.
1. Create an implementation of the resource API interface extending the desired abstract implemenation:
	* `AbstractBaseApiResourceTemplate`
	* `AbstractBaseApiResourceQueryTemplate`
	* `AbstractBaseApiResourceCRUDTemplate`
1. Create an implementation of the base API interface extending `AbstractBaseApiTemplate`, initializing the resource API template, performing any required configuration of the `ObjectMapper` or `RestTemplate`, and/or adding any request interceptors.

### Optional steps

* If paged results are needed and the provided Spring Data-like `Page` DTO can't be used, create a page DTO extending `BaseApiPage`. A custom Jackson serializer and deserializer may also then be required (see `PageSerializer` and `PageDeserializer`).
* If custom query builder capabilities are desired, either extend `BaseApiUriComponentsBuilder` or `BaseApiUriComponentsBuilderWithPagination`, depending on your needs.
* Create new implementations of `ClientHttpRequestInterceptor` when API requests need to be augmented before being sent, and associate it with the `RestTemplate` in the base API template.
* If template settings need to be configurable at runtime (for example, to switch to a different subdomain), see `TestBaseApiTemplate#configureSettings()` for an example of how to do this

### Supporting Spring Social's Service Provider 'Connect' Framework

Spring Social Bootstrap SDK-based projects are designed to work either inside or outside a Spring application context, either directly instantiated or registered with a factory.

It also provides some assistance with integrating the project with Spring Social's [Service Provider 'Connect' Framework]:

1. Create an implementation of `AuthOperations`; if pre-authorization is not required, always provide authorization.
1. Create a service provider implementation of `BaseApiServiceProvider`.
1. Create an API adapter implementation of `BaseApiAdapter`.
1. Create a connection extension of `AbstractBaseApiConnection`5. 
1. Create a connection factory extension of `AbstractBaseApiConnectionFactory`
		

## Example Implementation

Spring Social Bootstrap SDK provides an example implementation of a new service provider (`TestBaseApi`), plus unit tests demonstrating its usage, under the `src/test/java` package.

The classes under a `test_support` package denote the implementations created for a new service provider (named "Test") API that has the following characteristics:

* A `/test` resource API endpoint with full RESTful/CRUD support
* A `/test/health-check` API endpoint for validating API connections
* The API returns both JSON and XML representations of resources
* Query responses are returned wrapped in a Spring Data-like `Page` representation
* All requests require a session ID to have been acquired

The following classes were created:

* `TestBaseApi`, the base API interface
* `TestBaseApiResource`, the resource DTO
* `TestBaseApiClientSettings`, the API client settings incorporating `TestBaseApiClientPlatformSettings` and `TestBaseApiClientSecuritySettings`
* `TestOperations`, the resource API interface
* `TestTemplate`, the resource API implementation
* `TestBaseApiTemplate`, the base API implementation
* `TestPaginationUriComponentsBuilder`, a custom URI builder
* `SessionIdRequestInterceptor`, a new `ClientHttpRequestInterceptor`
* `LoginTemplate`, an `AuthOperations` implementation
* `TestBaseApiConnectionData`, a `ConnectionData` extension
* `TestBaseApiServiceProvider`, the service provider implementation
* `TestBaseApiAdapter`, the API adapter implementation
* `TestBaseApiConnection`, the connection extension
* `TestBaseApiConnectionFactory`, the connection factory extension

Unit tests are also provided demonstrating direct and factory creation of the example implementation, inside and outside of a Spring application context:

* `TestBaseApiTemplateTest`, directly creating a `TestBaseApiTemplate` and performing CRUD, query, and page navigation operations on it
* `TestBaseApiConnectionFactoryTest`, directly creating a `TestBaseApiConnectionFactory`, authorizing against it, and creating a connection
* `TestBaseApiConnectionFactorySpringTest`, creating `ConnectionFactoryLocator` Spring `@Bean` that locates the bean from a `ConnectionFactoryRegistry`

[Spring Social]: http://projects.spring.io/spring-social
[Spring Social Core]: http://projects.spring.io/spring-social/core.html
[Service Provider 'Connect' Framework]: http://docs.spring.io/spring-social/docs/1.1.0.RELEASE/reference/htmlsingle/#connectFramework
[Spring Social Bootstrap Settings]: https://bitbucket.org/robinhowlett/spring-social-bootstrap/spring-social-bootstrap-settings