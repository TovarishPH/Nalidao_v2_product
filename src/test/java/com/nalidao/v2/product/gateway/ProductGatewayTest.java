package com.nalidao.v2.product.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.repository.ProductRepository;
import com.nalidao.v2.product.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class ProductGatewayTest {

	@InjectMocks
	private ProductGateway gateway;
	
	@Mock
	private ProductRepository repository;

	private TestUtils utils = new TestUtils();
	
	@Test
	public void testFindAllProductList() {
		List<Product> mockProductList = this.utils.getProductList();
		when(this.repository.findAll()).thenReturn(mockProductList);
		
		List<Product> list = this.gateway.findAll();
		
		assertThat(list).isNotNull().isNotEmpty();
		assertEquals(list.size(), mockProductList.size());
		assertSame(list, mockProductList);
	}
	
	@Test
	public void testFindProductById() {
		Product mockProd = this.utils.getProduct();
		Long id = 1L;
		when(this.repository.findById(id)).thenReturn(Optional.of(mockProd));
		
		Product found = this.gateway.findProductById(id).get();
		
		assertThat(found).isNotNull();
		assertSame(found, mockProd);
	}
	
	@Test
	public void testSaveProduct() {
		Product mockProd = this.utils.getProduct();
		
		this.gateway.saveProduct(mockProd);
		
		Mockito.verify(this.repository).save(mockProd);
	}
	
	@Test
	public void testRemoveProduct() {
		Product prod = this.utils.getProduct();
		this.gateway.removeProduct(prod.getId());
		
		Mockito.verify(this.repository).deleteById(prod.getId());
	}
}
