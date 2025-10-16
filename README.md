🛍️ API de Loja Virtual
Esta API foi desenvolvida para gerenciar produtos, categorias e os seus relacionamentos numa loja virtual. Ela permite o cadastro de itens, organização por categorias e navegação entre recursos via HATEOAS, garantindo uma gestão eficiente e segura dos dados.

## 📌 Funcionalidades

- Cadastro de produtos
- Cadastro de categorias
- Vinculação de produtos às suas categorias
- Listagem de produtos com as suas respectivas categorias
- Busca de produtos por nome (flexível, aceita nome parcial)
- Exclusão de categorias com validação de associação
- Retorno de links HATEOAS para navegação entre recursos
- Validações e tratamento de exceções

## 🧠 Regras de negócio

- Cada produto pode estar vinculado a uma ou mais categorias
- Cada categoria pode conter vários produtos
- Não é permitido excluir uma categoria que esteja associada a produtos
- A busca por nome de produto aceita nome parcial
- Todas as respostas relevantes incluem links HATEOAS para facilitar a navegação

## 🛠️ Tecnologias utilizadas

- Kotlin
- Spring Boot 3.5.3
- Maven
- Spring Data JPA
- MySQL
- Springdoc OpenAPI
- HATEOAS
- Dozer Mapper

## 📦 Empacotamento

O projeto é empacotado como um **JAR executável** via Maven. Para gerar o artefato, execute:

```bash
mvn clean package
