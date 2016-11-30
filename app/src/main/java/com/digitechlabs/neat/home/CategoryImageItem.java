package com.digitechlabs.neat.home;

import android.graphics.Bitmap;

/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class CategoryImageItem {
	private Bitmap image;
	private String name;

	private String description;
	private int productID;
	private String url;
	private boolean chk;
	private int ParentID;

	public CategoryImageItem(Bitmap image, String name, int productID,
			String description, String url,int ParentID) {
		super();
		this.image = image;
		this.name = name;
		this.productID = productID;
		this.description = description;
		this.url = url;
		this.ParentID = ParentID;

	}
	public int getParentID() {
		return ParentID;
	}

	public void setParentID(int ParentID) {
		this.ParentID = ParentID;
	}
	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
