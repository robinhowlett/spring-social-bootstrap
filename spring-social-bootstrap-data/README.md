# Spring Social Bootstrap Data

**Spring Social Bootstrap Data** provides the bridge between Domain and DTO models for [Spring Social Bootstrap]-based projects. 

This project provides the abstract class `AbstractPageDomainToPageDtoConverter`, a [Spring Type Converter] which converts a [Spring Data] `Page` of `Objects`s to a [Spring Social Bootstrap] `BaseApiPage` of `BaseApiResources`. 

The converter is initialized with a separate converter to convert the `Object`s into `BaseApiResources`.

The concrete implementation `PageDomainToPageDtoConverter` converts a Spring Data `Page` to a Spring Social Bootstrap `Page`.

[Spring Social Bootstrap]: https://github.com/robinhowlett/spring-social-bootstrap
[Spring Data]: http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html
[Spring Type Converter]: http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#core-convert