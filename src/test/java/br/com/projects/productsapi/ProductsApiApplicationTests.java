package br.com.projects.productsapi;

import br.com.projects.productsapi.model.Product;
import br.com.projects.productsapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ProductsApiApplicationTests {

		// O Spring não tem outro jeito de saber onde entregar o repository. Aí precisa do @Autowired no campo
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
}