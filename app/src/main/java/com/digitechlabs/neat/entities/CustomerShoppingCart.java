package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;


public class CustomerShoppingCart {
	public int getCustomerShoppingCartID() {
		return CustomerShoppingCartID;
	}
	public void setCustomerShoppingCartID(int customerShoppingCartID) {
		CustomerShoppingCartID = customerShoppingCartID;
	}
	public int getUserLoginInfoID() {
		return UserLoginInfoID;
	}
	public void setUserLoginInfoID(int userLoginInfoID) {
		UserLoginInfoID = userLoginInfoID;
	}
	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		ProductID = productID;
	}
	public double getCost() {
		return Cost;
	}
	public void setCost(double cost) {
		Cost = cost;
	}
	public int getQty() {
		return Qty;
	}
	public void setQty(int qty) {
		Qty = qty;
	}
	public int getSpicyLevelID() {
		return SpicyLevelID;
	}
	public void setSpicyLevelID(int spicyLevelID) {
		SpicyLevelID = spicyLevelID;
	}
	public double getTotalCost() {
		return TotalCost;
	}
	public void setTotalCost(int totalCost) {
		TotalCost = totalCost;
	}
	@SerializedName("CustomerShoppingCartID")
    public int CustomerShoppingCartID;
	@SerializedName("UserLoginInfoID")
    public int UserLoginInfoID;
	@SerializedName("ProductID")
    public int ProductID;
	@SerializedName("Cost")
    public double Cost;
	@SerializedName("Qty")
    public int Qty;  
	@SerializedName("SpicyLevelID")
    public int SpicyLevelID; 
	@SerializedName("TotalCost")
    public double TotalCost; 
	@SerializedName("ItemTotalCost")
    public double ItemTotalCost;
	@SerializedName("RestaurantId")
    public int RestaurantId; 
	@SerializedName("Path")
	public String Path;
	@SerializedName("SpecialNotes")
	public String SpecialNotes;
	@SerializedName("IsFavorite")
	public int IsFavorite;
	@SerializedName("Title")
	public String Title;
	public int pID=0;
    public CustomerShoppingCart()
    {
    }
}
