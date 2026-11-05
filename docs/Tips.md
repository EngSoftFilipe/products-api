# Dicas que achei interessantes

> ## Annotations do SpringBoot 

### @GetMapping
- Não precisa de parâmetros no getProducts(), o repository sabe qual tabela usar pelo JpaRepository<Product, Long>
- Só lê o que está no banco, não depende de nada que o cliente mandar
```java
@GetMapping
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
```
### @PostMapping
- Precisa de parâmetros no 'saveProduct()' porque o repository sabe onde guardar, mas não sabe o quê
```java
@PostMapping
    public Product saveProduct(@RequestBody Product product){
        System.out.println("Product received: " + product);
        return productRepository.save(product); // .save: salva no Banco de dados
    }
```
### Injeção de dependência
- Na presença de um construtor, o Spring tem a obrigação de passar o repository para criar a classe
- Não precisaria usar a annotation, o autowired vira repetição
- Em testes, normalmente usamos @Autowired no campo, porque quem cria a classe de teste é o JUnit
- OBS: No código da aplicação (controller, service), o recomendado é receber pelo **construtor**
```java
// @Autowired --> Redundante, construtor já leva os parâmetros
private final ProductRepository productRepository;

public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
}
```
- No teste, por outro lado, o @Autowired é necessário, porque a classe de teste não tem construtor recebendo o repository
```java
@Autowired
private ProductRepository productRepository;

@Test
void saveProduct(){
    Product product = new Product();
    product.setName("Mouse");
    product.setPrice(150.0);
    productRepository.save(product);

    List<Product> products = productRepository.findAll();
    assertFalse(products.isEmpty());
}
```
> ## Java e Classes do Spring

### Optional
- Avisa ao sistema que um produto pode não existir
- Se o controller devolver o Optional direto e o produto não existir, a resposta é 200 (Ok) com corpo null --> pode causar confusão

````java
import org.springframework.web.bind.annotation.PathVariable;

@GetMapping("/{id}")
public Optional<Product> getById(@PathVariable Long id) {
    return productRepository.findById(id);
}
````
### ResponseEntity
- Recebe o resultado do Optional (com map e orElse) e transforma numa resposta HTTP com o status certo
- Retorna o status HTTP conforme a requisição
- Permite devolver o status HTTP correto (200, 404, 204...), em vez de 200 com corpo null
- Possui métodos que abstraem os status HTTP

### Method Reference
- Usa :: para abreviar código

````java
@GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
````
````
.map(ResponseEntity::ok)

    // Fazem a mesma coisa
    
.map(product -> ResponseEntity.ok(product));
````