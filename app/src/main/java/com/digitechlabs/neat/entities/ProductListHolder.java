package com.digitechlabs.neat.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ProductListHolder {
	@SerializedName("ProductList")
	public List<Product> productList;
	
	public ProductListHolder() {
		productList = new ArrayList<Product>();
	}
}
