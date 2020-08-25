package com.nalidao.v2.product.controller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.domain.dto.CreateProductDto;
import com.nalidao.v2.product.domain.dto.ProductDto;
import com.nalidao.v2.product.errorhandling.exception.ProductNotFoundException;
import com.nalidao.v2.product.gateway.ProductGateway;
import com.nalidao.v2.product.repository.ProductRepository;
import com.nalidao.v2.product.service.ProductService;
import com.nalidao.v2.product.utils.ConvertCreateProductDtoToEntity;
import com.nalidao.v2.product.utils.ConvertDtoToProductEntity;
import com.nalidao.v2.product.utils.ConvertProductEntityToDto;
import com.nalidao.v2.product.utils.TestUtils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

	@MockBean
	private ProductService service;
	@MockBean
	private ProductGateway gateway;
	@MockBean
	private ProductRepository repository;
	@MockBean
	private ConvertProductEntityToDto convertEntityToDto;
	@MockBean
	private ConvertCreateProductDtoToEntity convertCreateProductDtoToEntity;
	@MockBean
	private ConvertDtoToProductEntity convertDtoToEntity;
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper mapper;
	
	private TestUtils utils = new TestUtils();
	
	@Test
	public void testWhenProductListRetunsOk() throws Exception {
		List<Product> list = this.utils.getProductList();
		
		when(this.service.findall()).thenReturn(list);
		
		mockMvc.perform(get("/product-api")
						.accept(MediaType.APPLICATION_JSON))
						.andDo(MockMvcResultHandlers.print())
						.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	public void testFindProductByIdReturnsOk() throws Exception {
		ProductDto dto = this.utils.getProductDto();
		Product prod = this.utils.getProduct();
		long id = 1l;

		when(this.service.getProductById(id)).thenReturn(Optional.of(prod));
		when(this.convertEntityToDto.convert(prod)).thenReturn(dto);
		
		ResultActions result = mockMvc.perform(get("/product-api/{id}", id)
												.accept(MediaType.APPLICATION_JSON)
												.content(this.mapper.writeValueAsString(dto)));
		
		result.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
				
	}
	
	@Test
	public void testFindProductByIdReturnsnotFound() throws Exception {
		long id = 3l;
		
		when(this.service.getProductById(id)).thenThrow(ProductNotFoundException.class);
		
		ResultActions result = mockMvc.perform(get("/product-api/{id}", id)
												.accept(MediaType.APPLICATION_JSON));
		
		result.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testCreateProductReturnCreated() throws Exception {
		CreateProductDto createDto = new CreateProductDto("testName", 2.5, 3);
		Product prod = this.utils.getProduct();
		ProductDto dto = this.utils.getProductDto();
		
		when(this.convertCreateProductDtoToEntity.convert(createDto)).thenReturn(prod);
		when(this.service.createProduct(prod)).thenReturn(prod);
		when(this.convertEntityToDto.convert(prod)).thenReturn(dto);
		
		ResultActions result = mockMvc.perform(post("/product-api")
												.contentType(MediaType.APPLICATION_JSON)
												.content(this.mapper.writeValueAsString(createDto)));
		
		result.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
	}
	
	@Test
	public void testUpdateProductReturnOk() throws Exception {
		ProductDto dto = this.utils.getProductDto();
		Product prod = this.utils.getProduct();
		
		when(this.convertDtoToEntity.convert(dto)).thenReturn(prod);
		when(this.service.updateProduct(prod)).thenReturn(prod);
		when(this.convertEntityToDto.convert(prod)).thenReturn(dto);
		
		ResultActions result = mockMvc.perform(put("/product-api")
												.contentType(MediaType.APPLICATION_JSON)
												.content(this.mapper.writeValueAsString(dto)));
		
		result.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}
	
	@Test
	public void testUpdateProductReturnNotFound() throws Exception {
		ProductDto dto = this.utils.getProductDto();
		Product prod = this.utils.getProduct();
		
		when(this.convertDtoToEntity.convert(dto)).thenReturn(prod);
		when(this.service.updateProduct(prod)).thenThrow(ProductNotFoundException.class);
		
		ResultActions result = mockMvc.perform(put("/product-api")
												.contentType(MediaType.APPLICATION_JSON)
												.content(this.mapper.writeValueAsString(dto)));
		
		result.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();
	}
	
	@Test
	public void testRemoveProductReturnOk() throws Exception {
		long id = 3l;
		
		ResultActions result = mockMvc.perform(delete("/product-api/{id}", id)
												.accept(MediaType.APPLICATION_JSON));
		
		result.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}
	
	@Test
	public void testRemoveProductReturnNotFound() throws Exception {
		long id = 3l;
		
		Mockito.doThrow(ProductNotFoundException.class).when(this.service).removeProduct(id);
		
		ResultActions result = mockMvc.perform(delete("/product-api/{id}", id)
												.accept(MediaType.APPLICATION_JSON));
		
		result.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();
	}
	
}
