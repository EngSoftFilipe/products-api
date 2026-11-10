package br.com.projects.productsapi.Service;

import br.com.projects.productsapi.model.Product;
import br.com.projects.productsapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product save(Product product){
        if(product.getPrice() <= 0){
            throw new IllegalArgumentException("O preço não pode ser zero ou negativo");
        }
        return productRepository.save(product);
    }
}