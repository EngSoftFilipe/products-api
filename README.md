# Products API

Projeto de estudo para **aprofundar meu aprendizado em Spring Boot**, construindo uma API REST de produtos do zero.

Aqui coloco em prática os fundamentos do ecossistema Spring: arquitetura em camadas, persistência com JPA, injeção de dependência, configuração da aplicação e testes automatizados.

> Em evolução: novas funcionalidades e testes são adicionados conforme avanço nos estudos.

---

## Tecnologias

- **Java 21**
- **Spring Boot 4**
- **Spring Web MVC**: criação dos endpoints REST
- **Spring Data JPA / Hibernate**: persistência dos dados
- **H2 Database**: banco de dados em memória
- **JUnit**: testes automatizados
- **Maven**: gerenciamento de dependências

---

## Endpoints

| Método | Rota | Descrição |
|:------:|------|-----------|
| `POST` | `/products` | Cadastra um novo produto (retorna 201) |
| `GET` | `/products` | Lista todos os produtos |
| `GET` | `/products/{id}` | Busca um produto pelo id (retorna 404 se não existir) |
| `GET` | `/products/search?name={nome}` | Busca produtos pelo nome, sem diferenciar maiúsculas e minúsculas |
| `PUT` | `/products/{id}` | Atualiza um produto pelo id (retorna 404 se não existir) |
| `DELETE` | `/products/{id}` | Remove um produto pelo id (retorna 204, ou 404 se não existir) |

### Exemplo: cadastrar produto

**Requisição**

```http
POST /products
Content-Type: application/json
```

```json
{
  "name": "Mouse",
  "description": "Mouse sem fio",
  "price": 150.0
}
```

**Resposta** (`201 Created`)

```json
{
  "id": 1,
  "name": "Mouse",
  "description": "Mouse sem fio",
  "price": 150.0
}
```

O `id` é gerado automaticamente pelo banco.

### Exemplo: buscar pelo nome

```http
GET /products/search?name=mouse
```

Retorna todos os produtos que têm "mouse" no nome, como "Mouse", "MOUSE gamer" ou "mouse sem fio".

---

## Estrutura

```text
src/main/java/br/com/projects/productsapi
├── ProductsApiApplication.java    # Classe principal
├── controller
│   └── ProductController.java     # Recebe as requisições HTTP
├── model
│   └── Product.java               # Entidade (tabela do banco)
└── repository
    └── ProductRepository.java     # Acesso ao banco com Spring Data JPA
```

---

## Variáveis de ambiente

As credenciais do banco ficam em um arquivo `.env`, que não é enviado para o repositório. Para rodar o projeto, copie o arquivo de exemplo e preencha os valores:

```bash
# Windows
copy .env.example .env

# Linux/Mac
cp .env.example .env
```

| Variável | Descrição | Valor para o H2 |
|----------|-----------|-----------------|
| `DB_URL` | Endereço do banco | `jdbc:h2:mem:products` |
| `DB_USER` | Usuário do banco | `sa` |
| `DB_PASSWORD` | Senha do banco | `password` |

---

## Como executar

**Pré-requisitos:** Java 21 instalado e o arquivo `.env` criado (veja [Variáveis de ambiente](#variáveis-de-ambiente)). O Maven não é necessário, porque o projeto usa o Maven Wrapper.

```bash
# Clonar o repositório
git clone https://github.com/EngSoftFilipe/products-api.git
cd products-api

# Rodar a aplicação (Windows)
mvnw.cmd spring-boot:run

# Rodar a aplicação (Linux/Mac)
./mvnw spring-boot:run
```

A API fica disponível em `http://localhost:8080`.

### Console do H2

Com a aplicação rodando, o banco pode ser visualizado em `http://localhost:8080/h2-console`, usando os mesmos valores do `.env`:

| Campo | Valor |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:products` |
| Usuário | `sa` |
| Senha | `password` |

> O H2 roda em memória: os dados são apagados sempre que a aplicação é reiniciada.

---

## Testes

```bash
# Windows
mvnw.cmd test

# Linux/Mac
./mvnw test
```

Os testes de integração cobrem as operações de CRUD:

- Cadastrar um produto (POST)
- Buscar um produto pelo id (GET)
- Atualizar um produto (PUT)
- Remover um produto (DELETE)

---

## Anotações de estudo

Durante o desenvolvimento, registro o que aprendo na pasta [`docs`](docs):

| Arquivo | Conteúdo |
|---------|----------|
| [Spring Boot](docs/SpringBoot.md) | Anotações e classes do Spring usadas no projeto e o que cada uma faz |
| [JUnit no Spring Boot](docs/JUnit-SpringBoot.md) | Testes do projeto explicados passo a passo |
| [Dicas](docs/Tips.md) | Dicas gerais e pontos que aprendi na prática (GET x POST, injeção de dependência, testes) |

---

## O que pratiquei neste projeto

- Criação de API REST com `@RestController`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@RequestBody`, `@PathVariable` e `@RequestParam`
- Respostas com o status HTTP correto usando `ResponseEntity` (200, 201, 204 e 404)
- Persistência com JPA (`@Entity`, `@Id`, `@GeneratedValue`) e `JpaRepository`
- Consultas derivadas do nome do método (`findByNameContainingIgnoreCase`)
- Injeção de dependência pelo construtor
- Configuração com `application.yml` e credenciais em variáveis de ambiente (`.env`)
- Testes de integração com `@SpringBootTest` para as operações de CRUD