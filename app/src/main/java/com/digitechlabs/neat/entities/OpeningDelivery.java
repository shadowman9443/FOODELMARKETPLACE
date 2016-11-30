package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class OpeningDelivery {

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
	
	@SerializedName("OpeningDeliveryId")
	public int OpeningDeliveryId;
	@SerializedName("LunchStart")
	public String LunchStart;
	@SerializedName("LunchEnd")
	public String LunchEnd;
	@SerializedName("DinnerStart")
	public String DinnerStart;
	
	@SerializedName("DinnerEnd")
	public String DinnerEnd;
	@SerializedName("Day")
	public String Day;
	
	
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
	
	

	
	public OpeningDelivery() {

	}
	  public OpeningDelivery(String _describe, double _price,  boolean _box) {
		
		   // image = _image;
		  
		  }
	  
	

}
