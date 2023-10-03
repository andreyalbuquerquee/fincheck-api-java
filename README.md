# Fincheck

A Rest API FinCheck é uma aplicação Java Spring Boot que oferece recursos para gestão de finanças pessoais. A API inclui autenticação de usuário, criação e gerenciamento de contas bancárias, transações e categorias das transações.

Além disso, a API oferece a funcionalidade de busca de usuários por ID, tornando o processo de gerenciamento mais eficiente. A integração do Pageable na API permite que os resultados da busca possam ser paginados, facilitando a navegação e a visualização dos dados.

Em resumo, o projeto Parking Spot é uma solução completa para gerenciamento de estacionamentos em condomínios.

## Tecnologias Utilizadas

- Java Spring Boot
- Spring Security
- Spring Data JPA
- Spring Validation
- Tokens JSON Web (JWT)
- Banco de Dados PostgreSQL
- Docker
<div style="display: inline_block"><br>
 <img alt="Java" align="center" width="40" height="30" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg">
  <img alt="SpringBoot" align="center" width="40" height="30" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg">
  <img alt="PostgreSQL" align="center" width="40" height="30" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg">
  <img alt="Docker" align="center" width="40" height="30" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg">
</div>

## Pré-requisitos

Antes de começar, certifique-se de que você possui os seguintes pré-requisitos instalados:

- Java Development Kit (JDK)
- Banco de Dados PostgreSQL
- IDE Java (por exemplo, IntelliJ IDEA ou Eclipse)

## Configuração

1. Clone este repositório em sua máquina local:

   ```shell
   git clone https://github.com/andreyalbuquerquee/fincheck-api-java.git

2. Configure as informações do banco de dados no `application.properties`

   ```shell
   spring.datasource.url=jdbc:postgresql://localhost:5432/seu-banco-de-dados
   spring.datasource.username=seu-usuario
   spring.datasource.password=sua-senha

3. Execute a aplicação Spring Boot

   ```shell
   ./mvnw spring-boot:run

## Autenticação

A API utiliza autenticação baseada em tokens JSON Web (JWT). Para acessar endpoints protegidos, você precisa incluir um token JWT válido no cabeçalho HTTP da seguinte forma:

   ```shell
   Authorization: Bearer seu-token-jwt
   ```

## Endpoints

## Rotas públicas:

### Autenticação
- Se registrar: `POST /auth/signup`
>  ```
>    // body
>    {
>      "name": string,
>      "email": string,
>      "password": string (min 8)
>    }
>    ```
- Fazer login: `POST /auth/signin`
>  ```
>    // body
>    {
>      "email": string,
>      "password": string (min 8)
>    }
>    ```

## Rotas privadas:

### Contas bancárias
- Listar todas as contas bancárias: `GET /bank-accounts`
- Criar uma nova conta bancária: `POST /bank-accounts`
>  ```
>    // body
>   {
     "name": string,
     "initialBalance": double,
     "color": string, (hexcode)
     "type": string ("CHECKING", "INVESTMENT", "CASH")
    }
>    ```
- Atualizar uma conta bancária existente: `PUT /bank-accounts/:id`
>  ```
>    // body
>    {
     "name": string,
     "initialBalance": double,
     "color": string, (hexcode)
     "type": string ("CHECKING", "INVESTMENT", "CASH")
    }
>    ```
- Excluir uma conta bancária: `DELETE /bank-accounts/:id`

### Categorias
- Listar todas as categorias: `GET /categories`

### Transações
- Listar todas as transações: `GET /transactions`
- Criar uma nova transação: `POST /transactions`
>  ```
>    // body
>   {
     "bankAccountId": string, (UUID)
     "categoryId": string, (UUID)
     "name": string
     "value": double,
     "date": string, (ISO-8601)
     "type": string, ("INCOME", "EXPENSE")
    }
>    ```
- Atualizar uma transação existente: `PUT /transactions/:id`
>  ```
>    // body
>    {
      "bankAccountId": string, (UUID)
      "categoryId": string, (UUID)
      "name": string
      "value": double,
      "date": string, (ISO-8601)
      "type": string, ("INCOME", "EXPENSE")
    }
>    ```
- Excluir uma transação: `DELETE /transactions/:id`

### Usuários
- Listar informações do usuário logado: `GET /users/me`

### Contribuição
Sinta-se à vontade para contribuir com melhorias para este projeto. Você pode abrir problemas ou enviar solicitações de pull para colaborar no desenvolvimento.
