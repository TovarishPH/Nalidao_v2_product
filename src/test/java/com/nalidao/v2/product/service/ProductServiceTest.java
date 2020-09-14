package com.nalidao.v2.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.domain.dto.CreateProductDto;
import com.nalidao.v2.product.domain.dto.ProductDto;
import com.nalidao.v2.product.errorhandling.exception.ProductNotFoundException;
import com.nalidao.v2.product.gateway.ProductGateway;
import com.nalidao.v2.product.utils.ConvertCreateProductDtoToEntity;
import com.nalidao.v2.product.utils.ConvertDtoToProductEntity;
import com.nalidao.v2.product.utils.ConvertProductEntityToDto;
import com.nalidao.v2.product.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductGateway gateway;
	@Mock
	private ConvertProductEntityToDto convertEntityToDto;
	@Mock
	private ConvertCreateProductDtoToEntity createProductConverter;
	@Mock
	private ConvertDtoToProductEntity convertDtoToEntity;
	
	private TestUtils utils = new TestUtils();
	
	@Test
	public void findAllProductList() {
		List<Product> list = this.utils.getProductList();
		List<ProductDto> dtoList = this.utils.getProductDtoList();
		when(this.gateway.findAll()).thenReturn(list);
		when(this.convertEntityToDto.convertList(list)).thenReturn(dtoList);
		
		List<ProductDto> foundList = this.service.findall();
		
		assertThat(foundList).isNotNull().isNotEmpty();
		assertThat(foundList.size()).isEqualTo(dtoList.size());
		assertThat(foundList).isSameAs(dtoList);
	}
	
	@Test
	public void testFindProductById() {
		Product prod = this.utils.getProduct();
		ProductDto prodDto = this.utils.getProductDto();
		long id = 1L;
		when(this.gateway.findProductById(id)).thenReturn(Optional.of(prod));
		when(this.convertEntityToDto.convert(prod)).thenReturn(prodDto);
		
		ProductDto foundProdDto = this.service.getProductById(id);
		
		assertThat(foundProdDto).isNotNull();
		assertThat(foundProdDto).isEqualTo(prodDto);
	}
	
	@Test
	public void testFindProductByIdThrowProductNotFoundException() {
		long id = 2l;
		Throwable thrown = ThrowableAssert.catchThrowable(() -> {
			this.service.getProductById(id);
		});
		
		assertThat(thrown).isInstanceOf(ProductNotFoundException.class).hasMessage("Produto com id " + id + " não encontrado na base de dados");
	}
	
	@Test
	public void testCreateProduct() {
		CreateProductDto createDto = this.utils.getCreateProductDto();
		Product prod = this.utils.getProduct();
		ProductDto prodDto = this.utils.getProductDto();
		when(this.createProductConverter.convert(createDto)).thenReturn(prod);
		when(this.gateway.saveProduct(prod)).thenReturn(prod);
		when(this.convertEntityToDto.convert(prod)).thenReturn(prodDto);
		
		ProductDto createdDto = this.service.createProduct(createDto);
		
		assertThat(createdDto).isNotNull();
		assertThat(createdDto).isEqualTo(prodDto);
	}
	
	@Test
	public void testUpdateProduct() {
		Product prod = this.utils.getProduct();
		ProductDto updatedProduct = new ProductDto(prod.getId(), prod.getName(), prod.getPrice(), prod.getAmount());
		updatedProduct.setName("updated name");
		
		when(this.gateway.findProductById(prod.getId())).thenReturn(Optional.of(prod));
		when(this.convertDtoToEntity.convert(updatedProduct)).thenReturn(prod);
		when(this.gateway.saveProduct(prod)).thenReturn(prod);
		when(this.convertEntityToDto.convert(prod)).thenReturn(updatedProduct);
		
		ProductDto finalProduct = this.service.updateProduct(updatedProduct);
		
		assertThat(finalProduct).isNotNull();
		assertThat(finalProduct).isEqualTo(updatedProduct);
		assertThat(finalProduct.getName()).isNotSameAs(prod.getName());
	}
	
	@Test
	public void testUpdateThrowsProductNotFoundException() {
		ProductDto prodDto = this.utils.getProductDto();
		Throwable thrown = ThrowableAssert.catchThrowable(() -> {
			this.service.updateProduct(prodDto);
		});
		
		assertThat(thrown).isInstanceOf(ProductNotFoundException.class).hasMessage("Id " + prodDto.getId() + " não encontrado na base de dados, para atualização de produto.");
		
	}
	
	@Test
	public void testRemoveProduct() {
		Product prod = this.utils.getProduct();
		
		when(this.gateway.findProductById(prod.getId())).thenReturn(Optional.of(prod));
		
		this.service.removeProduct(prod.getId());
		
		Mockito.verify(this.gateway).removeProduct(prod.getId());
	}
	
	@Test
	public void testRemoveThrowsProductnotFoundException() {
		Product prod = this.utils.getProduct();
		Assertions.assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> {
			this.service.removeProduct(prod.getId());
		}).withMessage("Id " + prod.getId() + " não encontrado. Não é possível efetuar a remoção deste produto.");
		
	}
}
