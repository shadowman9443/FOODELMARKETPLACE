package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class Offer {

	@SerializedName("ProductID")
	public int ProductID;
	@SerializedName("ResturantID")
	public int ResturantID;
	@SerializedName("MDProductCategoryID")
	public int MDProductCategoryID;
	@SerializedName("ProductOriginId")
	public int ProductOriginId;
	@SerializedName("UnitID")
	public int UnitID;
	@SerializedName("TootTip")
	public String TootTip;
	@SerializedName("SearchKeys")
	public String SearchKeys;
	@SerializedName("MDProductStatusID")
	public int MDProductStatusID;
	@SerializedName("OwnerMemberID")
	public int OwnerMemberID;
	
	@SerializedName("Amount")
	public int Amount;
	@SerializedName("Description")
	public String Description;
	@SerializedName("OfferTitle")
	public String OfferTitle;
	@SerializedName("EndDate")
	public String EndDate;
	@SerializedName("StartDate")
	public String StartDate;
	@SerializedName("ItemID")
	public int ItemID;
	@SerializedName("ItemOfferID")
	public int ItemOfferID;
	@SerializedName("OfferImageUrl")
	public String OfferImageUrl;
	@SerializedName("OfferPosition")
	public String OfferPosition;
	@SerializedName("OfferTermsCondition")
	public String OfferTermsCondition;
	@SerializedName("OfferType")
	public int OfferType;
	@SerializedName("Status")
	public Boolean Status;
	
	@SerializedName("ReleasedAppStoreID")
	public int ReleasedAppStoreID;
	@SerializedName("OriginalFileName")
	public String OriginalFileName;
	@SerializedName("CurrentFileName")
	public String CurrentFileName;
	@SerializedName("FileType")
	public String FileType;
	@SerializedName("Extension")
	public String Extension;
	
	

	
	public Offer() {

	}
	  public Offer(String _describe, double _price,  boolean _box) {
		
		   // image = _image;
		  
		  }
	  
	

}
