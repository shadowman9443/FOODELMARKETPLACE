package com.digitechlabs.neat.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OpeningDeliverListHolder {
	@SerializedName("CustomerOrder")
	public List<OpeningDelivery> openningDeliveryList;
	
	public OpeningDeliverListHolder() {
		openningDeliveryList = new ArrayList<OpeningDelivery>();
	}
}
