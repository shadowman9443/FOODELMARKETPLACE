package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class LoyaltyPointUser {

	@SerializedName("ProductID")
	public int ProductID;
	@SerializedName("ResturantID")
	public int ResturantID;
	@SerializedName("MDProductCategoryID")
	public int MDProductCategoryID;
	@SerializedName("ProductCode")
	public String ProductCode;
	@SerializedName("ProductName")
	public String ProductName;
	@SerializedName("ProductOriginId")
	public int ProductOriginId;
	@SerializedName("UnitID")
	public int UnitID;
	@SerializedName("CreatedByMemberID")
	public int CreatedByMemberID;
	@SerializedName("DeliveryDate")
	public String DeliveryDate;
	@SerializedName("LoyaltyTime")
	public String LoyaltyTime;
	@SerializedName("LoyaltyPrice")
	public double LoyaltyPrice;
	@SerializedName("LoyaltyPoints")
	public int LoyaltyPoints;
	@SerializedName("CustomerOrderID")
	public int CustomerOrderID;
	@SerializedName("CommentsTime")
	public String CommentsTime;
	@SerializedName("OrderDate")
	public String OrderDate;
	@SerializedName("TotalAmount")
	public double TotalAmount;	
	public LoyaltyPointUser() {

	}
	  public LoyaltyPointUser(String _describe) {
		  ProductName = _describe;
		   
		  }
	  
	

}
