package br.com.projects.productsapi.controller;

import br.com.projects.productsapi.model.Product;
import br.com.projects.productsapi.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping()
    public Product saveProduct(@RequestBody Product product){
        System.out.println("Product received: " + product);
        return productRepository.save(product); // 'save' salva no BD
    }

    // Método em uso por causa do H2, apagar quando migrar de BD
    @GetMapping
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        if(!productRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}