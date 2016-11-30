package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class CustomerOrderEntity {

	@SerializedName("CustomerOrderID")
	public int CustomerOrderID;
	@SerializedName("UserLoginInfoID")
	public int UserLoginInfoID;
	@SerializedName("OrderDate")
	public String OrderDate;
	@SerializedName("DeliveryDate")
	public String DeliveryDate;
	@SerializedName("Comment")
	public String Comment;
	@SerializedName("OrderDeliveredTime")
	public String OrderDeliveredTime;
	@SerializedName("IsFeedBack")
	public Boolean IsFeedBack;
	
	@SerializedName("PackageID")
	public int PackageID;
	@SerializedName("MDCustomerOrderStatusID")
	public int MDCustomerOrderStatusID;
	@SerializedName("TotalAmount")
	public Double TotalAmount;

	public CustomerOrderEntity() {
	}
}
