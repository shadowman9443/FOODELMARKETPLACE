package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class ResturantHolder {

	@SerializedName("GCMID")
    public Restaurant restaurantEntity;

    public ResturantHolder()
    {
    	restaurantEntity = new Restaurant();
    }
}
