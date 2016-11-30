package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class Loyalty {

	

	@SerializedName("LoyalityPointValue")
    public double LoyalityPointValue;
	
	public double getLoyalityPointValue() {
		return LoyalityPointValue;
	}


	public void setLoyalityPointValue(double loyalityPointValue) {
		LoyalityPointValue = loyalityPointValue;
	}


	public String getLoyalityAmount() {
		return LoyalityAmount;
	}


	public void setLoyalityAmount(String loyalityAmount) {
		LoyalityAmount = loyalityAmount;
	}


	@SerializedName("LoyalityAmount")
    public String LoyalityAmount;
	
	@SerializedName("LoyalityPointText")
	public String  LoyalityPointText;
	@SerializedName("UserLoginInfoId")
	public String  UserLoginInfoId;
	@SerializedName("LoyalityPointID")
	public int  LoyalityPointID;
	
    public Loyalty()
    {
    }
}
