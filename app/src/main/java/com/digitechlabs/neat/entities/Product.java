package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class Product {

	@SerializedName("ProductID")
	public int ProductID;

	@SerializedName("ResturantID")
	public int ResturantID;
	@SerializedName("MDProductCategoryID")
	public int MDProductCategoryID;
	@SerializedName("ProductCode")
	public String ProductCode;
	@SerializedName("ProductName")
	public String ProductName;
	@SerializedName("ProductOriginId")
	public int ProductOriginId;
	@SerializedName("UnitID")
	public int UnitID;
	@SerializedName("TootTip")
	public String TootTip;
	@SerializedName("Details")
	public String Details;
	@SerializedName("SearchKeys")
	public String SearchKeys;
	@SerializedName("Price")
	public double Price;
	@SerializedName("DeliverByHour")
	public double DeliverByHour;
	@SerializedName("MDProductStatusID")
	public int MDProductStatusID;
	@SerializedName("OwnerMemberID")
	public int OwnerMemberID;
	@SerializedName("CreatedByMemberID")
	public int CreatedByMemberID;
	@SerializedName("ReleaseDate")
	public String ReleaseDate;
	@SerializedName("CreateDate")
	public String CreateDate;
	@SerializedName("ReleasedAppStoreID")
	public int ReleasedAppStoreID;
	@SerializedName("Path")
	public String Path;
	@SerializedName("OriginalFileName")
	public String OriginalFileName;
	@SerializedName("CurrentFileName")
	public String CurrentFileName;
	@SerializedName("FileType")
	public String FileType;
	@SerializedName("Extension")
	public String Extension;
	@SerializedName("IsRemoved")
	public Boolean IsRemoved;
	@SerializedName("imagePlus")
	public int imagePlus =0;
	@SerializedName("itemCount")
	public int itemCount=0;
	@SerializedName("AlergimonicID")
	public String AlergimonicID;
	@SerializedName("SpecialNotes")
	public String SpecialNotes;
	@SerializedName("Quantity")
	public int Quantity;
	@SerializedName("ItemCountLocalDb")
	public int ItemCountLocalDb=0;
	@SerializedName("ItemTotalPrice")
	public double ItemTotalPrice;
	@SerializedName("IsFaviorite")
	public Boolean IsFaviorite;
	
	//total price of the order
	@SerializedName("TotalAmount")
	public double TotalAmount;
	
	
	@SerializedName("MPath")
	public String MPath;
	
	public int getImagePlus() {
		return imagePlus++;
	}
	public void setImagePlus(int imagePlus) {
		this.imagePlus = imagePlus;
	}

	
	public Product() {

	}
	  public Product(String _describe, double _price,  boolean _box) {
		  ProductName = _describe;
		    Price  = _price;
		   // image = _image;
		    IsRemoved = _box;
		  }
	  
	

}
