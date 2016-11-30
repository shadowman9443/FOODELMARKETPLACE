package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class FavoriteProduct {

	@SerializedName("MDProductCategoryID")
	public int MDProductCategoryID;
	@SerializedName("TotalRowCount")
	public int TotalRowCount;
	@SerializedName("ResturantID")
	public int ResturantID;
	@SerializedName("ProductID")
	public int ProductID;
	@SerializedName("UserLoginInfoID")
	public int UserLoginInfoID;
	@SerializedName("ProductName")
	public String ProductName;
	@SerializedName("AlergimonicID")
	public String AlergimonicID;
	@SerializedName("IsRemoved")
	public boolean IsRemoved;
	@SerializedName("MPath")
	public String MPath;
	@SerializedName("FavouriteID")
	public int FavouriteID;
	@SerializedName("IsFaviorite")
	public boolean IsFaviorite;
	@SerializedName("Details")
	public String Details;
	@SerializedName("Price")
	public Double Price;
	@SerializedName("ItemCountLocalDb")
	public int ItemCountLocalDb=0;
	public FavoriteProduct() {
	}
}
