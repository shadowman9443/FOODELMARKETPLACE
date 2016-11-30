package com.digitechlabs.neat.entities;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
public class LoyaltyPointHolder {
	@SerializedName("GetBooktableStausList")
	public List<LoyaltyPointUser> loyaltyPointList;
	public LoyaltyPointHolder() {
		loyaltyPointList = new ArrayList<LoyaltyPointUser>();
	}

}
