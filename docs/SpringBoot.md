# Spring Boot

> ## Importante
- O Application é o inicializador do servidor, ele deve ficar na raiz do pacote, para o Spring poder mapear ele sem dar erro
- No repository, o Spring lê o nome do método e monta a consulta sozinho (ex: findByNameContainingIgnoreCase), dê preferência por padrões de mercado
> ## Anotações

### @RestController
- Diz para o Spring que essa classe vai receber requisições REST
- Usado nas classes controller
- O retorno dos métodos vira JSON automaticamente

### @RequestMapping
- Diz qual é a URL base do controller
- Usado em cima da classe controller

### @RequestBody
- Diz **o quê** a requisição está mandando (os dados)
- Só pega dados do corpo da requisição (o JSON)
- Exemplo prático: quero cadastrar um mouse de 150 reais. Na requisição, mando os dados no corpo em JSON, e o `@RequestBody` transforma esse JSON num objeto `Product` já preenchido
```java
@PostMapping
public Product saveProduct(@RequestBody Product product){}
 
// POST /products
// { "name": "Mouse", "price": 150.0 }
// → product.getName() = "Mouse" | product.getPrice() = 150.0
```
### @PathVariable
- Diz **qual** item a requisição quer (ex: qual id)
- Só pega dados da URL
- Exemplo prático: tenho 10 produtos cadastrados e quero ver só o de id 5. Na requisição, coloco o 5 na URL (`/products/5`), e o `@PathVariable` entrega esse 5 para a variável `id`
```java
@GetMapping("/{id}")
public ResponseEntity<Product> getById(@PathVariable Long id){}
 
// GET /products/5 → id = 5
```

- Só lê a URL: se o valor vier no JSON do corpo, quem pega é o `@RequestBody`
### @RequestParam
- Diz **como** a requisição quer os dados (ex: filtrar pelo nome)
- Só pega dados que vêm depois do `?` na URL
- Exemplo prático: tenho 10 produtos cadastrados e quero só os que têm "mouse" no nome. Na requisição, coloco `?name=mouse` no final da URL, e o `@RequestParam` entrega esse "mouse" para a variável `name`
```java
@GetMapping("/search")
public List<Product> getByName(@RequestParam String name){}
 
// GET /products/search?name=mouse → name = "mouse"
```

- Se o parâmetro puder não vir, usa `@RequestParam(required = false)`. Assim, sem o `?name=`, a variável chega `null` em vez de dar erro
> ## Classes Spring

### ResponseEntity
- É uma classe do Spring que representa a resposta HTTP inteira
- Possui métodos que abstraem os status HTTP
#### Métodos mais usados
- `ok()`: Deu certo e devolve algo (**200**)
- `notFound()`: Não achou o que estava procurando (**404**)
- `noContent()`: Deu certo, mas não tem nada para devolver (**204**)
- `build()`: Finaliza a resposta sem corpo
- `body()`: Finaliza a resposta com corpo

### JpaRepository
- Interface do Spring Data que já traz os métodos prontos de acesso ao banco

#### Métodos mais usados
- `existsById()`: Confere se o ID existe
- `findById()`: Procura pelo ID