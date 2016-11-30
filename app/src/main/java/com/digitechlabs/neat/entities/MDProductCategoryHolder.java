package com.digitechlabs.neat.entities;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class MDProductCategoryHolder {

	@SerializedName("ProductCategoryList")
	public ArrayList<MDProductCategory> MDProductCategoryList;

	public MDProductCategoryHolder() {

		MDProductCategoryList = new ArrayList<MDProductCategory>();
	}
}
