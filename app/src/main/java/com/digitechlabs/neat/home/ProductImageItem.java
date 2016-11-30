

package com.digitechlabs.neat.home;

import android.graphics.Bitmap;
import android.widget.TextView;

/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class 
ProductImageItem {
	private Bitmap image;
	public String title;
	public int productID;
	public double price;
	public String url;
    private boolean chk;
    public boolean IsRemoved;
	public double imageplus;
    public int itemCount;
    public String alergimonic;
    public String productDetails;
    public Boolean existInLocalDb;
    public int itemCountFromLocalDb;
    public double itemTotalPrice;
    public String specialNotes;
    public int isFavorite;
	public 
	ProductImageItem(Bitmap image, String title, int productID,double price,String url,Boolean IsRemoved,int imageplus, int itemCount, String alergimonic, String productDetails, Boolean existInLocalDb, int itemCountFromLocalDb, String specialNotes, double itemTotalPrice, int isFavorite ) {
		super();
		
		this.itemCount= itemCount;
		this.productDetails= productDetails;
		this.imageplus = imageplus++;
		this.alergimonic= alergimonic;
		this.itemCountFromLocalDb=itemCountFromLocalDb;
		this.existInLocalDb= existInLocalDb;
		this.image = image;
		this.title = title;
		this.productID = productID;
		this.price=price;
		this.url=url;
		this.chk = IsRemoved;
		this.specialNotes= specialNotes;
		this.itemTotalPrice= itemTotalPrice;
		this.isFavorite = isFavorite;
	}
	public double getItemTotalPrice() {
		return itemTotalPrice;
	}
	public void setItemTotalPrice(double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}
	public String getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}
	public String getAlergimonic() {
		return alergimonic;
	}

	public void setAlergimonic(String alergimonic) {
		this.alergimonic = alergimonic;
	}
	public int getItemCount() {
		return itemCount;
	}
    public int addItemCount() {
 		return itemCount++;
 	}
    public int deductItemCount() {
 		return itemCount--;
 	}
    
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public double getImageplus() {
		return imageplus;
	}

	public void setImageplus(int imageplus) {
		this.imageplus = imageplus;
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
	public boolean getcheck() {
		return chk;
	}

	public void setcheck(Boolean IsRemoved) {
		this.chk = IsRemoved;
	}
}
