package com.digitechlabs.neat.home;

import android.graphics.Bitmap;

/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class ImageItem {
	private Bitmap image;
	private String title;
	private int productID;
	private double price;
	private String url;
 private boolean chk;
	public ImageItem(Bitmap image, String title, int productID,double price,String url) {
		super();
		this.image = image;
		this.title = title;
		this.productID = productID;
		this.price=price;
		this.url=url;
		
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
