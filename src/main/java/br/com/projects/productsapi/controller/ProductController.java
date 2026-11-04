package br.com.projects.productsapi.controller;

import br.com.projects.productsapi.model.Product;
import br.com.projects.productsapi.repository.ProductRepository;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        System.out.println("Product received: " + product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product)); // 'save' salva no BD
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

    @GetMapping("/search")
    public List<Product> getByName(@RequestParam String name){
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        if(!productRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        product.setId(id); // sem setId, o save entende que é um produto novo e cria outro
        return ResponseEntity.ok(productRepository.save(product));
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