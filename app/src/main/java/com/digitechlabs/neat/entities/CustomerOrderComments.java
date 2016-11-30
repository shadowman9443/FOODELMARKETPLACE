package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class CustomerOrderComments {

	@SerializedName("CommentsID")
	public int CommentsID;
	@SerializedName("TotalRowCount")
	public int TotalRowCount;
	@SerializedName("CustomerID")
	public int CustomerID;
	@SerializedName("CustomerOrderID")
	public int CustomerOrderID;
	@SerializedName("MDCustomerOrderStatusID")
	public int MDCustomerOrderStatusID;
	@SerializedName("Comments")
	public String Comments;
	@SerializedName("CommentsBy")
	public int CommentsBy;
	@SerializedName("CommentsTime")
	public String CommentsTime;
	@SerializedName("UserType")
	public int UserType;

	public CustomerOrderComments() {
	}
}
