package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class MDProductCategory {

	@SerializedName("MDProductCategoryID")
	public int MDProductCategoryID;
	@SerializedName("TotalRowCount")
	public int TotalRowCount;
	@SerializedName("ResturantID")
	public int ResturantID;
	@SerializedName("ParentProductCategoryID")
	public int ParentProductCategoryID;
	@SerializedName("Name")
	public String Name;
	@SerializedName("Description")
	public String Description;
	@SerializedName("IsRemoved")
	public boolean IsRemoved;
	@SerializedName("Path")
	public String Path;
	@SerializedName("ParentID")
	public int ParentID;
	public MDProductCategory() {
	}
}
