package com.digitechlabs.neat.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OfferListHolder {
	@SerializedName("ItemWiseOfferList")
	public List<Offer> itemOfferList;
	
	public OfferListHolder() {
		itemOfferList = new ArrayList<Offer>();
	}
}
