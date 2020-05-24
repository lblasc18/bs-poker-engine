# bs-poker-engine

This application was generated using:

* Java 8
* SpringBoot 2
* Maven
 
 Using template spring boot project at https://start.spring.io/
 
### Remarks

* You can find pokerdata.txt file on resources directory path: templates / input / pokerdata.txt
* You Can find pokerdata[results].txt file on resources directory path: templates/input/pokerdata[results].txt after main class execution.
* After than application execution you can remove the out file to generate it again.

# Development

Run the following commands to clean project and execute the application's main class, including unit tests.

    mvn clean
    ./mvnw install

Or run java main class: BsPokerEngineApplication.class, for it you will need Java JDK 8 installed.

# Testing

To launch your application's tests, run:

    ./mvnw clean test
    
Or run each test of BsPokerEngineApplicationTests class, for it you will need Java JDK 8 installed.

# New Features

This application use to Spring Web for new features on a future like to create Apis REST or integrate with Frontend App.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.1.15.BUILD-SNAPSHOT/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)