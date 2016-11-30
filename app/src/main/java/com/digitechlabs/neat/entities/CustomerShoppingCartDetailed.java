package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class CustomerShoppingCartDetailed {
	@SerializedName("CustomerShoppingCartID")
	public int CustomerShoppingCartID;
	@SerializedName("ProductCode")
	public String ProductCode;
	@SerializedName("ProductName")
	public String ProductName;
	@SerializedName("ProductCategoryName")
	public String ProductCategoryName;
	@SerializedName("UserLoginInfoID")
	public int UserLoginInfoID;
	@SerializedName("Path")
	public String Path;
	@SerializedName("ProductID")
	public int ProductID;
	public boolean isIsRemoved() {
		return IsRemoved;
	}

	public void setIsRemoved(boolean isRemoved) {
		IsRemoved = isRemoved;
	}

	public boolean IsRemoved;

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

	@SerializedName("Cost")
	public double Cost;
	@SerializedName("Qty")
	public int Qty;

	public CustomerShoppingCartDetailed() {

	}

	public double getsumCost() {

		return Cost = +Cost;
	}

}
