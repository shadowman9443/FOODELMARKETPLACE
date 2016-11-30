package com.digitechlabs.neat.entities;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class GalleryImageHolder {

	@SerializedName("AllGalleryImage")
	public ArrayList<GalleryImage> MDProductCategoryList;
	public GalleryImageHolder() {
	}
}
