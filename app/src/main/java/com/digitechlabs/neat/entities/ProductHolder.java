package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class ProductHolder {
	@SerializedName("ProductEntity")
	public Product productEntity;

	public ProductHolder() {
		productEntity = new Product();
	}
}
