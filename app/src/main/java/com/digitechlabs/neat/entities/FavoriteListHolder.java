package com.digitechlabs.neat.entities;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FavoriteListHolder {

	@SerializedName("AllFaviorteList")
	public ArrayList<FavoriteProduct> favoriteList;

	public FavoriteListHolder() {
		favoriteList = new ArrayList<FavoriteProduct>();
	}
}
