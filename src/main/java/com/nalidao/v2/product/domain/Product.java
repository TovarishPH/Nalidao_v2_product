package com.nalidao.v2.product.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dominio que representa um produto
 * @author paulo
 */
@Entity
@Data
@NoArgsConstructor
public class Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private Double price;
	
	private int amount;

	public Product(String name, Double price, int amount) {
		super();
		this.name = name;
		this.price = price;
		this.amount = amount;
	}
}
