package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class GalleryImage {

	public int getMDProductCategoryID() {
		return MDProductCategoryID;
	}
	public void setMDProductCategoryID(int mDProductCategoryID) {
		MDProductCategoryID = mDProductCategoryID;
	}
	public int getTotalRowCount() {
		return TotalRowCount;
	}
	public void setTotalRowCount(int totalRowCount) {
		TotalRowCount = totalRowCount;
	}
	public int getResturantID() {
		return ResturantID;
	}
	public void setResturantID(int resturantID) {
		ResturantID = resturantID;
	}
	public int getParentProductCategoryID() {
		return ParentProductCategoryID;
	}
	public void setParentProductCategoryID(int parentProductCategoryID) {
		ParentProductCategoryID = parentProductCategoryID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public boolean isIsRemoved() {
		return IsRemoved;
	}
	public void setIsRemoved(boolean isRemoved) {
		IsRemoved = isRemoved;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	@SerializedName("MDProductCategoryID")
	public int MDProductCategoryID;
	@SerializedName("TotalRowCount")
	public int TotalRowCount;
	@SerializedName("ResturantID")
	public int ResturantID;
	@SerializedName("ParentProductCategoryID")
	public int ParentProductCategoryID;
	@SerializedName("Name")
	public String Name;
	@SerializedName("Description")
	public String Description;
	@SerializedName("IsRemoved")
	public boolean IsRemoved;
	@SerializedName("Path")
	public String Path;
	
	@SerializedName("Title")
	public String Title;
	public GalleryImage() {
	}
}
