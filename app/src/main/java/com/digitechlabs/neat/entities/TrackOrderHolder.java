package com.digitechlabs.neat.entities;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
public class TrackOrderHolder {
	@SerializedName("CustomerStatusList")
	public List<LoyaltyPointUser> loyaltyPointList;
	public TrackOrderHolder() {
		loyaltyPointList = new ArrayList<LoyaltyPointUser>();
	}

}
