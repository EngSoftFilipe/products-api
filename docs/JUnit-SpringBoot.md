# JUnit no Spring Boot

> ## Spring Boot Tests
- GET
- POST
- DELETE

### Teste método GET

````java
// O Spring não tem outro jeito de saber onde entregar o repository. Aí precisa do @Autowired no campo
@Autowired
private ProductRepository productRepository;

@Test
@DisplayName("Método GET") // Facilita a identificação
void getProductById(){ // void porque teste não retorna nada, apenas confere
    
    // Cria o produto na memória preenchendo o nome e preço
    // Ele ainda não está no banco, e o id dele ainda é null
    Product product = new Product();
    product.setName("Mouse Attack Shark X11");
    product.setPrice(150.0);
    
    // Salva no banco.
    // O banco gera o id (por exemplo, 3), e o save devolve o produto já com o id preenchido.
    // Esse produto vai para a variável 'saved'
    Product saved = productRepository.save(product);

    // Executa
    // saved.getId() --> pega o id que o banco acabou de gerar (o 3)
    // findById() --> busca esse produto no banco
    // O resultado vem como Optional, que pode ou não ter um produto dentro, e vai para a variável 'found'
    Optional<Product> found = productRepository.findById(saved.getId());

    // Confere se for true
    // isPresent() pergunta se existe um produto dentro do Optional, já que o assertTrue exige que seja true
    assertTrue(found.isPresent());
    
    // found.get() --> pega o produto
    // getName() --> pega o nome
    // assertEquals --> confere se o resultado bate com o esperado
    assertEquals("Mouse Attack Shark X11", found.get().getName());
}
````