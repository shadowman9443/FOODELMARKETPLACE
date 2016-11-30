package com.digitechlabs.neat.entities;

import java.util.ArrayList;


import com.google.gson.annotations.SerializedName;

public class CustomerOrderCommentsHolder {

	@SerializedName("AllOrderList")
	public ArrayList<CustomerOrderComments> CustomerOrderCommentsEntityList;

	public CustomerOrderCommentsHolder() {

		CustomerOrderCommentsEntityList = new ArrayList<CustomerOrderComments>();
	}
}
