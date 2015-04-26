# Spring Social Bootstrap DTO

**Spring Social Bootstrap DTO** provides base interfaces and implementations of DTOs for [Spring Social Bootstrap]-based projects, plus a `Serializer` and `Deserializer` for `Page` resources.

* `BaseApiResource` is the root interface for all resource DTOs. All [Spring Social Bootstrap]-based DTOs should inherit from this interface. 

* `BaseApiPage` is the base decorator interface around multiple `BaseApiResource` DTOs

* `Page` is an implementation of the `BaseApiPage` DTO interface. It is intended to be compatible with [Spring Data](http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html)'s `Page` interface. `PageSerializer` and `PageDeserializer` are the respective Jackson serializer and deserializer classes.

Custom Jackson deserializers are also included:

* `UniquePropertyPolymorphicDeserializer`
* `UnixTimestampDeserializer`
* `YesNoBooleanDeserializer`

[Spring Social Bootstrap]: https://github.com/robinhowlett/spring-social-bootstrap