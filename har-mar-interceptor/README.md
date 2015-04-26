<p align="center">![HAR Mar Interceptor](http://i.imgur.com/oaqfyPi.png)

**HAR Mar Interceptor** allows capturing HTTP request/response exchanges, persisting them in a [HAR](http://www.softwareishard.com/blog/har-12-spec/) or [ALF](https://github.com/Mashape/api-log-format)-compatible format, and re-executing those requests at a later time.

HAR Mar Interceptor may be used separately from or integrated with [Spring Social Bootstrap SDK](https://github.com/robinhowlett/spring-social-bootstrap/tree/master/spring-social-bootstrap-sdk)

## JDBC & API Log Format (ALF)

HAR Mar Interceptor ships with an implementation for storing HTTP request/response exchanges in [Mashape](https://www.mashape.com/)'s HAR-based [API Log Format (ALF)](https://github.com/Mashape/api-log-format) to a JDBC database:

* `AsyncJdbcAlfHarRepository` asynchronously persists `HarEntry` Domain objects to a JDBC store using [Spring JDBC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html), and can also query the HAR Log Entries, including over a specified time period.

* `AlfHarEntryToJdbcHarEntryConverter` uses Spring [Type Converstion](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#core-convert) to convert an `AlfHarEntry` to a `JdbcHarEntry`

* `ListOfJdbcHarEntriesToAlfHarConverter` converts a `List` of `JdbcHarEntry` Domain objects to an `AlfHar`

* `JdbcAlfHarServiceImpl` is a service implementation that uses `AsyncJdbcAlfHarRepository`, `AlfHarEntryToJdbcHarEntryConverter`, and `ListOfJdbcHarEntriesToAlfHarConverter` to save and query HTTP request/response exchanges.

* `JdbcAlfHarInterceptor` executes HTTP requests and intercepts responses to create ALF Log Entries and save them to a JDBC store with `JdbcAlfHarServiceImpl`

* `JdbcAlfHarTemplate` is an implementation of `HarOperations` that permits easy integration with [Spring Social Bootstrap SDK](https://github.com/robinhowlett/spring-social-bootstrap/tree/master/spring-social-bootstrap-sdk)-based API clients

* `ReplayAlfHarTemplate` can execute the entries in an `AlfHar` log at various `Intervals`:
	* Real-Time
	* Fixed
	* No Interval 