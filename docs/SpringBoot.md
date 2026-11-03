# Spring Boot

> ## Anotações

### @RestController
- Diz para o Spring que essa classe vai receber requisições REST
- Usado nas classes controller
- O retorno dos métodos vira JSON automaticamente

### @RequestMapping
- Diz qual é a URL base do controller
- Usado em cima da classe controller

### @RequestBody
- Pega o JSON da requisição e transforma num objeto Java
- Usado no parâmetro do método: saveProduct(@RequestBody Product product)

### @PathVariable
- Pega o valor que veio no caminho da URL e entrega para o parâmetro do método
- O `{id}` no mapeamento marca qual parte da URL é variável
- Diz **qual** recurso o cliente quer (ex: qual produto)

```java
@GetMapping("/{id}")
public ResponseEntity<Product> getById(@PathVariable Long id) {
    // GET /products/5 → id = 5
}
```

> ## Classes Spring

### ResponseEntity
- É uma classe do Spring que representa a resposta HTTP inteira
- Possui métodos que abstraem os status HTTP

#### Métodos mais usados
- `ok()`: Deu certo e devolve algo(**200**)
- `notFound()`: Não achou o que estava procurando (**404**)
- `noContent()`: Deu certo, mas não tem nada para devolver (**204**) 
- `build()`: Finaliza a resposta sem corpo
- `body()`: Finaliza a resposta com corpo