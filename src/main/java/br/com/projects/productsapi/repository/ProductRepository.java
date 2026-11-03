package br.com.projects.productsapi.repository;

import br.com.projects.productsapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Dentro dos generics '< >' colocamos a entidade e o tipo do ID
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> id(Long id);
}