package br.com.projects.productsapi;

import br.com.projects.productsapi.model.Product;
import br.com.projects.productsapi.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductsApiApplicationTests {

	// O Spring não tem outro jeito de saber onde entregar o repository. Aí precisa do @Autowired no campo
	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("POST: Deve salvar o produto no banco")
	void shouldPostProduct(){
		Product product = new Product();
		product.setName("Mouse");
		product.setPrice(150.0);
		productRepository.save(product);
		List<Product> products = productRepository.findAll();
		assertFalse(products.isEmpty());
	}

	@Test
	@DisplayName("GET: Deve encontrar o produto pelo ID")
	void shouldGetProductByID(){
		Product product = new Product();
		product.setName("Mouse Attack Shark X11");
		product.setPrice(150.0);
		Product saved = productRepository.save(product);
		Optional<Product> found = productRepository.findById(saved.getId());
		assertTrue(found.isPresent());
		assertEquals("Mouse Attack Shark X11", found.get().getName());
	}
	@Test
	@DisplayName("PUT: Deve atualizar dados do produto")
	void shouldUpdateProduct(){
		Product product = new Product();
		product.setName("Mouse Attack Shark X11");
		product.setPrice(150.0);
		Product saved = productRepository.save(product);

		saved.setName("Geladeira");
		saved.setPrice(2579.90);
		productRepository.save(saved);

		Product updated = productRepository.findById(saved.getId()).orElseThrow();
		assertEquals("Geladeira", updated.getName());
		assertEquals(2579.90, updated.getPrice(), 0.001);
	}

	@Test
	@DisplayName("DELETE: Deve apagar o produto salvo")
	void shouldDeleteProduct(){
		Product product = new Product();
		product.setName("Mouse Attack Shark X11");
		product.setPrice(150.0);
		Product saved = productRepository.save(product);
		productRepository.delete(saved);
		assertFalse(productRepository.existsById(saved.getId()));
	}
}