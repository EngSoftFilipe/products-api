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
| `POST` | `/products` | Cadastra um novo produto |
| `GET` | `/products` | Lista todos os produtos |

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

**Resposta**

```json
{
  "id": 1,
  "name": "Mouse",
  "description": "Mouse sem fio",
  "price": 150.0
}
```

O `id` é gerado automaticamente pelo banco.
 
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

## Como executar

**Pré-requisito:** Java 21 instalado. O Maven não é necessário, porque o projeto usa o Maven Wrapper.

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

Com a aplicação rodando, o banco pode ser visualizado em `http://localhost:8080/h2-console`:

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
 
---

## Anotações de estudo

Durante o desenvolvimento, registro o que aprendo na pasta [`docs`](docs):

| Arquivo | Conteúdo |
|---------|----------|
| [Annotations](docs/Annotations.md) | Anotações do Spring usadas no projeto e o que cada uma faz |
| [Dicas](docs/Dicas.md) | Dicas gerais e pontos que aprendi na prática (GET x POST, injeção de dependência, testes) |
 
---

## O que pratiquei neste projeto

- Criação de API REST com `@RestController`, `@GetMapping`, `@PostMapping` e `@RequestBody`
- Persistência com JPA (`@Entity`, `@Id`, `@GeneratedValue`) e `JpaRepository`
- Injeção de dependência pelo construtor
- Configuração com `application.yml`
- Testes de integração com `@SpringBootTest`