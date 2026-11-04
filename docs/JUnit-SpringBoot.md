# JUnit no Spring Boot

> ## Spring Boot Tests
- `GET`
- `POST`
- `PUT`
- `DELETE`

### Teste método GET
````java
// O Spring não tem outro jeito de saber onde entregar o repository. Aí precisa do @Autowired no campo
@Autowired
private ProductRepository productRepository;

@Test
@DisplayName("GET: Deve encontrar o produto pelo id") // Facilita a identificação
void shouldGetProductById(){ // void porque teste não retorna nada, apenas confere
    
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

### Teste método POST
````java
// O Spring não tem outro jeito de saber onde entregar o repository. Aí precisa do @Autowired no campo
@Autowired
private ProductRepository productRepository;

@Test
@DisplayName("POST: Deve salvar o produto no banco") // Facilita a identificação
void shouldPostProduct(){ // void porque teste não retorna nada, apenas confere

    // Cria o produto na memória preenchendo o nome e preço
    // Ele ainda não está no banco, e o id dele ainda é null
    Product product = new Product();
    product.setName("Mouse");
    product.setPrice(150.0);

    // Salva no banco: aqui o produto ganha um id
    productRepository.save(product);

    // Busca todos os produtos do banco e guarda na lista
    List<Product> products = productRepository.findAll();
    
    // Testa se a lista não está vazia, ou seja, o produto foi salvo
    assertFalse(products.isEmpty());
}
````

### Teste método PUT
````java
// O Spring não tem outro jeito de saber onde entregar o repository. Aí precisa do @Autowired no campo
@Autowired
private ProductRepository productRepository;

@Test
@DisplayName("PUT: Deve atualizar dados do produto")
void shouldUpdateProduct(){

    // Monta: cria o produto e salva no banco
    // O save devolve o produto já com o id gerado, e ele vai para a variável 'saved'
    Product product = new Product();
    product.setName("Mouse Attack Shark X11");
    product.setPrice(150.0);
    Product saved = productRepository.save(product);

    // Executa: muda os dados do MESMO objeto salvo e salva de novo
    // Como o 'saved' já tem id, o save atualiza em vez de criar um produto novo
    saved.setName("Geladeira");
    saved.setPrice(2579.90);
    productRepository.save(saved);

    // Confere: busca de novo no BANCO para provar que a atualização foi gravada
    // orElseThrow() --> tira o produto do Optional (se estiver vazio, dá erro e o teste falha)
    Product updated = productRepository.findById(saved.getId()).orElseThrow();

    // assertEquals --> confere se os dados novos foram salvos
    // 0.001 --> margem de tolerância para números com vírgula (Double)
    assertEquals("Geladeira", updated.getName());
    assertEquals(2579.90, updated.getPrice(), 0.001);
}
````

### Teste método DELETE
````java
// O Spring não tem outro jeito de saber onde entregar o repository. Aí precisa do @Autowired no campo
@Autowired
private ProductRepository productRepository;

@Test
@DisplayName("DELETE: Deve apagar o produto salvo")
void shouldDeleteProduct(){

    // Monta: cria o produto e salva no banco
    Product product = new Product();
    product.setName("Mouse Attack Shark X11");
    product.setPrice(150.0);
    Product saved = productRepository.save(product);

    // Executa: apaga o produto salvo
    // O delete não devolve nada (void), por isso fica sozinho na linha
    productRepository.delete(saved);

    // Confere: o id desse produto não existe mais no banco
    // Confere só esse id, e não a lista inteira, porque outros testes podem ter salvo produtos
    assertFalse(productRepository.existsById(saved.getId()));
}
````