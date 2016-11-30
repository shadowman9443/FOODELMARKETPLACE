package com.digitechlabs.neat.home;

import android.graphics.Bitmap;

/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class FavoriteImageItem {
	public String name;
	public int productID;
	public String url;
	public String description;
	public double price;
	public int itemCount;
	public int itemCountFromLocalDb;
	  public String alergimonic;
	public int getItemCount() {
		return itemCount;
	}


	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}

	public String getAlergimonic() {
		return alergimonic;
	}
	public Boolean getIsFavorite() {
		return isFavorite;
	}


	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public Boolean isFavorite;
	public FavoriteImageItem(String name, int productID, String url, String description, double price, Boolean isFavorite, int itemCountFromLocalDb,String alergimonic) {
		super();
		this.name = name;
		this.productID = productID;
		this.url = url;
		this.description = description;
		this.alergimonic= alergimonic;
		this.price = price;
		this.isFavorite = isFavorite;
		this.itemCountFromLocalDb=itemCountFromLocalDb;
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

}
