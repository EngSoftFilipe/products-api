package br.com.projects.productsapi.controller;

import br.com.projects.productsapi.model.Product;
import br.com.projects.productsapi.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    // Na presença de um construtor, o Spring tem a obrigação de passar o repository para criar a classe
    // Não precisaria usar a annotation, o autowired vira repetição
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping()
    // Precisa de parâmetros por que o repository sabe onde guardar, mas não sabe o quê
    public Product saveProduct(@RequestBody Product product){
        System.out.println("Product received: " + product);
        return productRepository.save(product); // 'save' salva no BD
    }

    @GetMapping
    // Não precisa de parâmetros, no escopo da interface tem 'Products'
    // Só lê o que está no banco e não depende de nada que o cliente mandar
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
}