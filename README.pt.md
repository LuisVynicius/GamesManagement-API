# API para o gerenciamento de jogos

## Sobre

Essa API foi desenvolvida em prol de testar meus novos conhecimentos em Java com o framework Spring. Ela visa gerenciar jogos e usuários por meio de endpoints, contando com autenticação e autorização por meio de tokens

## Ferramentas
Para a criação dessa aplicação foram utilizadas as seguintes ferramentas:

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

## Personas

#### Persona com total acesso:
- Username: Admin
- Senha: Password
#### Persona com acesso de um usuário:
- Username: User
- Senha: Password
#### Persona com acesso de um desenvolvedor:
- Username: GameDeveloper
- Senha Password

## EndPoints
- **/login**
  - **POST:** Usado para adquirir o token de autorização a partir de um login que utiliza um username e um password.
- **/category**
  - **GET:** Usado para adquirir todas as categorias.
  - **POST:** Usado para a criação de categorias.
  - **PUT:** Usado para a atualização de categorias.
  - **/{id}**
    -  **DELETE:** Usado para deletar uma categoria através do id.
- **/game**
  - **POST:** Usado para a criação de jogos.
  - **PUT:** Usado para a atualização de jogos.
  - **/name**
    - **/{name}**
      - **GET:** Usado para adquirir um jogo a partir do nome.
  - **/{id}**
    - **GET:** Usado para adquirir um jogo a partir do id.
    - **DELETE:** Usado para deletar um jogo a partir do id.
- **/userInformations**
  - **GET:** Usado para para adquirir todas as "Informações do usuário" registradas.
  - **/{id}**
    - **GET:** Usado para adquirir um usuário por id.
  - **/current**
    - **GET:** Usado para adquirir as informações do usuário logado.
    - **PUT:** Usado para atualizar as informações do usuário logado.
- **/user**
  - **GET:** Usado para adquirir todos os usuários.
  - **POST:** Usado para criar um usuário.
  - **/{id}**
    - **GET:** Usado para adquirir um usuário pelo id.
    - **DELETE:** Usado para deletar um usuário pelo id.
  - **/current**
    - **GET:** Usado para adquirir o usuário logado.
    - **DELETE:** Usado para adicionar uma data de deleção ao usuário logado.
    - **PUT:** Usado para atualizar o usuário logado.

## Autenticação

A autenticação é feita por meio de uma requisição POST na rota /login, usando o corpo {username: "", password: ""}. Essa ação retornará um token de acesso no header "Authorization" da resposta.

## Documentação do Swagger

Esse projeto conta com uma documentação criada utilizando o Swagger-UI, podendo ser acessada através dos seguintes links:
- HTML: http://localhost:8080/swagger-ui.html
- HTML: http://localhost:8080/swagger-ui/index.html
- Json: http://localhost:8080/v3/api-docs