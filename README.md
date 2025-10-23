🛍️ API de Loja Virtual
Esta API foi desenvolvida para gerenciar produtos, categorias e usuários em uma loja virtual. Ela oferece funcionalidades completas para cadastro, associação e navegação entre recursos, com suporte a HATEOAS e mapeamento inteligente via Dozer.

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

- Kotlin — linguagem moderna e concisa para desenvolvimento backend
- Spring Boot 3.5.3 — estrutura robusta para criação de APIs REST
- Spring Data JPA — abstração para persistência e consultas no banco
- MySQL — banco de dados relacional para armazenar entidades
- Dozer Mapper — mapeamento automático entre entidades e DTOs
- Springdoc OpenAPI — documentação automática dos endpoints
- HATEOAS — inclusão de links navegáveis nas respostas REST
- Maven — gerenciamento de dependências e empacotamento do projeto

## 📦 Empacotamento

O projeto é empacotado como um **JAR executável** via Maven. Para gerar o artefato, execute:

```bash
mvn clean package
