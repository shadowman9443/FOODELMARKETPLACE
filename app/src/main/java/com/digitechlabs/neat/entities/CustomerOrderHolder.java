package com.digitechlabs.neat.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CustomerOrderHolder {
	@SerializedName("CustomerOrder")
	public  List<CustomerOrderEntity> customerOrderEntity;

	public CustomerOrderHolder() {
		customerOrderEntity = new ArrayList<CustomerOrderEntity>();
	}
}
