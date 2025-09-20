# 🚗 Cadastro de Usuários e Veículos de Condomínio

Esta API foi desenvolvida para gerenciar moradores e seus veículos em um condomínio.  
Ela permite o cadastro de pessoas, o registro de veículos e a vinculação entre ambos, garantindo uma gestão eficiente e segura dos dados.

## 📌 Funcionalidades

- Cadastro de moradores
- Cadastro de veículos
- Vinculação de veículos aos seus respectivos moradores
- Desvinculação de veículos
- Busca por nome do morador (flexível, aceita nome parcial)
- Listagem de todos os moradores e veículos
- Validações e tratamento de exceções

## 🧠 Regras de negócio

- Moradores sem veículos possuem apenas seu próprio cadastro
- Moradores com veículos possuem cadastro pessoal e de seus veículos
- Cada veículo é vinculado a um único morador
- A busca por nome aceita apenas o primeiro nome ou parte do nome

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
