package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class LoyaltyHolder {

	@SerializedName("LoyalityPoints")
    public Loyalty Loyalty;

    public LoyaltyHolder()
    {
    	Loyalty = new Loyalty();
    }
}
