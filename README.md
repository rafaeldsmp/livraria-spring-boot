# livraria-spring-boot

API REST para gerenciamento de uma livraria, incluindo cadastro, consulta, atualização e exclusão de **Autores** e **Livros**.

---

## Sumário

- [Descrição Geral](#descrição-geral)  
- [Tecnologias](#tecnologias)  
- [Regras de Negócio](#regras-de-negócio)  
- [Modelos de Dados](#modelos-de-dados)  
- [Contratos da API](#contratos-da-api)  
  - [Autores](#autores)  
    - [Cadastrar Autor](#cadastrar-autor)  
    - [Visualizar Autor](#visualizar-autor)  
    - [Excluir Autor](#excluir-autor)  
    - [Pesquisar Autores](#pesquisar-autores)  
    - [Atualizar Autor](#atualizar-autor)  
  - [Livros](#livros)  
    - [Cadastrar Livro](#cadastrar-livro)  
    - [Visualizar Livro](#visualizar-livro)  
    - [Excluir Livro](#excluir-livro)  
    - [Pesquisar Livros](#pesquisar-livros)  
    - [Atualizar Livro](#atualizar-livro)  

---

## Descrição Geral

Esta API permite o gerenciamento dos autores e livros de uma livraria, contemplando operações de criação, leitura, atualização e exclusão (CRUD), com regras de negócio para garantir integridade e controle de acesso.

### Atores

- **Gerente:** Pode cadastrar, atualizar e remover Autores e Livros.  
- **Operador:** Pode consultar Autores e Livros, e também cadastrar/atualizar/excluir Livros.

---

## Tecnologias

- Java 17+  
- Spring Boot  
- JPA / Hibernate  
- Bean Validation (JSR-380)  
- Banco de dados relacional (PostgreSQL, MySQL, etc)  
- Swagger/OpenAPI para documentação  
- JWT / OAuth2 (opcional, para controle de acesso)  

---

## Regras de Negócio

### Autores

- Não permitir cadastro de Autor com mesmo nome, data de nascimento e nacionalidade já existente.  
- Não permitir exclusão de Autor que possua livros vinculados.

### Livros

- Não permitir cadastro de Livro com ISBN já existente.  
- Se data de publicação for a partir de 2020, preço é obrigatório.  
- Data de publicação não pode ser futura.

---

## Modelos de Dados

### Autor

| Campo                | Tipo   | Obrigatório | Descrição                        |
|----------------------|--------|-------------|---------------------------------|
| id                   | UUID   | Sim         | Identificador único             |
| nome                 | String | Sim         | Nome completo do autor          |
| dataNascimento       | Date   | Sim         | Data de nascimento do autor     |
| nacionalidade        | String | Sim         | Nacionalidade do autor          |
| dataCadastro         | Date   | Não         | Data de criação do registro     |
| dataUltimaAtualizacao | Date   | Não         | Data da última atualização      |
| usuarioUltimaAtualizacao | String | Não      | Usuário que fez última atualização |

### Livro

| Campo                | Tipo    | Obrigatório | Descrição                        |
|----------------------|---------|-------------|---------------------------------|
| id                   | UUID    | Sim         | Identificador único             |
| isbn                 | String  | Sim         | Código ISBN                    |
| titulo               | String  | Sim         | Título do livro                |
| dataPublicacao       | Date    | Sim         | Data de publicação             |
| genero               | Enum    | Não         | Gênero literário               |
| preco                | Number  | Condicional | Preço, obrigatório se pub. >=2020 |
| autor                | Autor   | Sim         | Autor do livro                 |
| dataCadastro         | Date    | Não         | Data de criação do registro    |
| dataUltimaAtualizacao | Date    | Não         | Data da última atualização     |
| usuarioUltimaAtualizacao | String | Não        | Usuário que fez última atualização |

---

## Contratos da API

---

### Autores

#### Cadastrar Autor

- **POST** `/autores`  
- **Body:**  
```json
{
  "nome": "João Cabral de Melo Neto",
  "dataNascimento": "1920-01-06",
  "nacionalidade": "Brasileira"
}
```

- **Respostas:**  
```json
{
  "201 Created": {
    "description": "Autor criado com sucesso",
    "headers": {
      "Location": "/autores/{id}"
    }
  },
  "422 Unprocessable Entity": {
    "status": 422,
    "message": "Erro de Validação",
    "errors": [
      { "field": "nome", "error": "Nome é obrigatório" }
    ]
  },
  "409 Conflict": {
    "status": 409,
    "message": "Registro Duplicado",
    "errors": []
  }
}
```

---

#### Visualizar Autor

- **GET** `/autores/{id}`

- **Respostas:**  
```json
{
  "200 OK": {
    "id": "b8f3db6a-3e01-4b17-90a5-7842151cdbb1",
    "nome": "João Cabral de Melo Neto",
    "dataNascimento": "1920-01-06",
    "nacionalidade": "Brasileira"
  },
  "404 Not Found": {
    "status": 404,
    "message": "Autor não encontrado",
    "errors": []
  }
}
```

---

#### Excluir Autor

- **DELETE** `/autores/{id}`

- **Respostas:**  
```json
{
  "204 No Content": "Autor excluído com sucesso",
  "400 Bad Request": {
    "status": 400,
    "message": "Erro na exclusão: registro está sendo utilizado.",
    "errors": []
  }
}
```

---

#### Pesquisar Autores

- **GET** `/autores?nome={nome}&nacionalidade={nacionalidade}`

- **Respostas:**  
```json
{
  "200 OK": [
    {
      "id": "b8f3db6a-3e01-4b17-90a5-7842151cdbb1",
      "nome": "João Cabral de Melo Neto",
      "dataNascimento": "1920-01-06",
      "nacionalidade": "Brasileira"
    }
  ]
}
```

---

#### Atualizar Autor

- **PUT** `/autores/{id}`  
- **Body:**  
```json
{
  "nome": "João Cabral de Melo Neto",
  "dataNascimento": "1920-01-06",
  "nacionalidade": "Brasileira"
}
```

- **Respostas:**  
```json
{
  "204 No Content": "Autor atualizado com sucesso",
  "422 Unprocessable Entity": {
    "status": 422,
    "message": "Erro de Validação",
    "errors": [
      { "field": "nome", "error": "Nome é obrigatório" }
    ]
  },
  "409 Conflict": {
    "status": 409,
    "message": "Registro Duplicado",
    "errors": []
  }
}
```

---

### Livros

#### Cadastrar Livro

- **POST** `/livros`  
- **Body:**  
```json
{
  "isbn": "978-85-12345-67-8",
  "titulo": "Grande Sertão: Veredas",
  "dataPublicacao": "1956-01-01",
  "genero": "ROMANCE",
  "preco": 45.90,
  "id_autor": "b8f3db6a-3e01-4b17-90a5-7842151cdbb1"
}
```

- **Respostas:**  
```json
{
  "201 Created": {
    "description": "Livro criado com sucesso",
    "headers": {
      "Location": "/livros/{id}"
    }
  },
  "422 Unprocessable Entity": {
    "status": 422,
    "message": "Erro de Validação",
    "errors": [
      { "field": "titulo", "error": "Campo obrigatório" }
    ]
  },
  "409 Conflict": {
    "status": 409,
    "message": "ISBN Duplicado",
    "errors": []
  }
}
```

---

#### Visualizar Livro

- **GET** `/livros/{id}`

- **Respostas:**  
```json
{
  "200 OK": {
    "id": "c1e4db6a-2f12-4b22-84b5-7842151caaaa",
    "isbn": "978-85-12345-67-8",
    "titulo": "Grande Sertão: Veredas",
    "dataPublicacao": "1956-01-01",
    "genero": "ROMANCE",
    "preco": 45.90,
    "autor": {
      "nome": "João Cabral de Melo Neto",
      "dataNascimento": "1920-01-06",
      "nacionalidade": "Brasileira"
    }
  },
  "404 Not Found": {
    "status": 404,
    "message": "Livro não encontrado",
    "errors": []
  }
}
```

---

#### Excluir Livro

- **DELETE** `/livros/{id}`

- **Respostas:**  
```json
{
  "204 No Content": "Livro excluído com sucesso",
  "404 Not Found": {
    "status": 404,
    "message": "Livro inexistente",
    "errors": []
  }
}
```

---

#### Pesquisar Livros

- **GET** `/livros?isbn={isbn}&titulo={titulo}&nomeAutor={nomeAutor}&genero={genero}&anoPublicacao={ano}`

- **Respostas:**  
```json
{
  "200 OK": [
    {
      "id": "c1e4db6a-2f12-4b22-84b5-7842151caaaa",
      "isbn": "978-85-12345-67-8",
      "titulo": "Grande Sertão: Veredas",
      "dataPublicacao": "1956-01-01",
      "genero": "ROMANCE",
      "preco": 45.90,
      "autor": {
        "nome": "João Cabral de Melo Neto",
        "dataNascimento": "1920-01-06",
        "nacionalidade": "Brasileira"
      }
    }
  ]
}
```

---

#### Atualizar Livro

- **PUT** `/livros/{id}`  
- **Body:**  
```json
{
  "isbn": "978-85-12345-67-8",
  "titulo": "Grande Sertão: Veredas",
  "dataPublicacao": "1956-01-01",
  "genero": "ROMANCE",
  "preco": 45.90,
  "id_autor": "b8f3db6a-3e01-4b17-90a5-7842151cdbb1"
}
```

- **Respostas:**  
```json
{
  "204 No Content": "Livro atualizado com sucesso",
  "422 Unprocessable Entity": {
    "status": 422,
    "message": "Erro de Validação",
    "errors": [
      { "field": "titulo", "error": "Campo obrigatório" }
    ]
  },
  "409 Conflict": {
    "status": 409,
    "message": "ISBN Duplicado",
    "errors": []
  }
}
```

---

## Considerações finais

- Todos os endpoints devem validar entradas e responder erros de forma clara e consistente.  
- Os campos de auditoria (`dataCadastro`, `dataUltimaAtualizacao`, `usuarioUltimaAtualizacao`) são gerenciados automaticamente pelo sistema e não são obrigatórios no corpo da requisição.  
- Controle de acesso deve ser aplicado conforme o papel do usuário (Gerente ou Operador).

---

## Como rodar o projeto

1. Clone o repositório:  
   ```bash
   git clone https://github.com/rafaeldsmp/livraria-spring-boot.git
   ```
2. Configure o banco de dados em `application.properties` ou `application.yml`.  

3. Acesse a documentação Swagger em:  
   `http://localhost:8080/swagger-ui.html`
