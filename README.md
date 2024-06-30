# Game Management API

## About

This API was developed to test my new knowledge in Java using the Spring framework. It aims to manage games and users through endpoints, featuring authentication and authorization via tokens.

## Tools and Technologies
The following tools and technologies were used to create this application:

- [Spring-Web](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring-Boot-Starter-Data-Jpa](https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/#reference)
- [SpringDoc-OpenApi-Starter-WebMVC-Ui](https://springdoc.org/)
- [Spring-Boot-Starter-Validation](https://reflectoring.io/bean-validation-with-spring-boot/)
- [Spring-Boot-Starter-Web](https://docs.spring.io/spring-boot/tutorial/first-application/index.html)
- [Spring-Boot-DevTools](https://docs.spring.io/spring-boot/tutorial/first-application/index.html)
- [H2-Database](https://www.baeldung.com/spring-boot-h2-database)
- [Lombok](https://projectlombok.org/features/)
- [Spring-Boot-Starter-Test](https://docs.spring.io/spring-boot/reference/testing/index.html)
- [Json-Web-Token-Api](https://central.sonatype.com/artifact/io.jsonwebtoken/jjwt-api)
- [Json-Web-Token-Impl](https://central.sonatype.com/artifact/io.jsonwebtoken/jjwt-impl/0.12.6)
- [Json-Web-Token-Jackson](https://central.sonatype.com/artifact/io.jsonwebtoken/jjwt-jackson/0.12.6)
- [Commons-Lang3](https://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html)
- [Spring-Boot-Starter-Security](https://docs.spring.io/spring-security/reference/getting-spring-security.html)

## User Roles

#### Full Access Persona
- Username: Admin
- Password: Password
#### Regular User Persona
- Username: User
- Password: Password
#### Developer User Persona
- Username: GameDeveloper
- Password Password

## EndPoints
- **/login**
  - **POST:** Used to acquire the authorization token via login with a username and password.
- **/category**
  - **GET:** Retrieve all categories.
  - **POST:** Create a new category.
  - **PUT:** Update an existing category.
  - **/{id}**
    -  **DELETE:** Delete a category by ID.
- **/game**
  - **POST:** Create a new game.
  - **PUT:** Update an existing game
  - **/name**
    - **/{name}**
      - **GET:** Retrieve a game by name.
  - **/{id}**
    - **GET:** Retrieve a game by ID.
    - **DELETE:** Delete a game by ID.
- **/userInformations**
  - **GET:** Retrieve all registered user informations.
  - **/{id}**
    - **GET:** Retrieve user information by ID.
  - **/current**
    - **GET:** Retrieve information of the logged-in user.
    - **PUT:** Update the information of the logged-in user.
- **/user**
  - **GET:** Retrieve all users.
  - **POST:** Create a new user.
  - **/{id}**
    - **GET:** Retrieve a user by ID.
    - **DELETE:** Delete a user by ID.
  - **/current**
    - **GET:** Retrieve the logged-in user.
    - **DELETE:** Add a deletion date to the logged-in user.
    - **PUT:** Update the logged-in user.

## Authentication

Authentication is performed via a POST request to the /login route, using the body {username: "", password: ""}. This action returns an access token in the "Authorization" header of the response.

## Swagger Documentation

This project includes documentation created using Swagger-UI, accessible through the following links:
- HTML: http://localhost:8080/swagger-ui.html
- HTML: http://localhost:8080/swagger-ui/index.html
- Json: http://localhost:8080/v3/api-docs