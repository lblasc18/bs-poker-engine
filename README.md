# bs-poker-engine

I've used:

* Java 8
* SpringBoot 2 (Using template spring boot project at https://start.spring.io/)
* Maven

# Development

Run the following commands to clean project and execute the application's main class, including unit tests.

    mvn clean
    ./mvnw install

Or run java main class `BsPokerEngineApplication.class` (Needs Java JDK 8 installed).

# Testing

To launch your application's tests, run:

    ./mvnw clean test
    
Or run each test from `BsPokerEngineApplicationTests.class` (Needs Java JDK 8 installed).

# Notes

* You can find pokerdata.txt file on resources directory. (`templates/input/pokerdata.txt`)
* You Can find pokerdata[results].txt file on resources directory. (`templates/input/pokerdata[results].txt`) after run main class.
* After run the application you can remove the out file to generate it again.

# New Features

This application uses Spring Web for new improvement/integrations like to expose an API Rest or FrontEnd view.

# Getting Started

### Reference Documentation
For further references, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.1.15.BUILD-SNAPSHOT/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
