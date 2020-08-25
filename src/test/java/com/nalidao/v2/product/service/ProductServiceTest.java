package com.nalidao.v2.product.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jayway.jsonpath.Option;
import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.gateway.ProductGateway;
import com.nalidao.v2.product.repository.ProductRepository;
import com.nalidao.v2.product.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductGateway gateway;
	
	private TestUtils utils = new TestUtils();
	
	@Test
	public void findAllProductList() {
		List<Product> list = this.utils.getProductList();
		when(this.gateway.findAll()).thenReturn(list);
		
		List<Product> foundList = this.service.findall();
		
		assertThat(foundList).isNotNull().isNotEmpty();
		assertThat(foundList.size()).isEqualTo(list.size());
		assertThat(foundList).isSameAs(list);
	}
	
	@Test
	public void testFindProductById() {
		Product prod = this.utils.getProduct();
		Long id = 1L;
		when(this.gateway.findProductById(id)).thenReturn(Optional.of(prod));
		
		Product foundProd = this.service.getProductById(id).get();
		
		assertThat(foundProd).isNotNull();
		assertThat(foundProd).isEqualTo(prod);
	}
	
	@Test
	public void testCreateProduct() {
		Product prod = this.utils.getProduct();
		when(this.gateway.saveProduct(prod)).thenReturn(prod);
		
		Product createdProd = this.service.createProduct(prod);
		
		assertThat(createdProd).isNotNull();
		assertThat(createdProd).isEqualTo(prod);
	}
	
	@Test
	public void testUpdateProduct() {
		Product prod = this.utils.getProduct();
		Product updatedProduct = new Product(prod.getId(), prod.getName(), prod.getPrice(), prod.getAmount());
		updatedProduct.setName("updated name");
		
		when(this.gateway.findProductById(prod.getId())).thenReturn(Optional.of(prod));
		when(this.gateway.saveProduct(updatedProduct)).thenReturn(updatedProduct);
		
		Product finalProduct = this.service.updateProduct(updatedProduct);
		
		assertThat(finalProduct).isNotNull();
		assertThat(finalProduct).isEqualTo(updatedProduct);
		assertThat(finalProduct.getName()).isNotSameAs(prod.getName());
	}
	
	@Test
	public void testRemoveProduct() {
		Product prod = this.utils.getProduct();
		
		when(this.gateway.findProductById(prod.getId())).thenReturn(Optional.of(prod));
		
		this.service.removeProduct(prod.getId());
		
		Mockito.verify(this.gateway).removeProduct(prod.getId());
	}
}
