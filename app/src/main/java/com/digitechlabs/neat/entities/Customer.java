package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class Customer {

	@SerializedName("CustomerID")
	public int CustomerID;
	@SerializedName("CustomerNo")
	public int CustomerNo;

	@SerializedName("RestaurentID")
	public int RestaurentID;
	@SerializedName("UserLoginInfoID")
	public int UserLoginInfoID;
	@SerializedName("FirstName")
	public String FirstName;
	@SerializedName("MiddleName")
	public String MiddleName;
	@SerializedName("LastName")
	public String LastName;
	@SerializedName("Surname")
	public String Surname;
	@SerializedName("MobileNo")
	public String MobileNo;
	@SerializedName("Email")
	public String Email;
	@SerializedName("AddressLine1")
	public String AddressLine1;
	@SerializedName("AddressLine2")
	public String AddressLine2;
	@SerializedName("Address")
	public String Address;
	@SerializedName("PostCode")
	public String PostCode;
	@SerializedName("CityID")
	public int CityID;
	@SerializedName("CountryID")
	public int CountryID;
	@SerializedName("FamilySize")
	public int FamilySize;
	@SerializedName("IsOrdered")
	public int IsOrdered;
	@SerializedName("PaymentPreferenceID")
	public int PaymentPreferenceID;
	@SerializedName("IsSMSPreference")
	public Boolean IsSMSPreference;
	@SerializedName("ReceptEmail")
	public String ReceptEmail;
	@SerializedName("LastLatitude")
	public Double LastLatitude;
	@SerializedName("LastLongitude")
	public Double LastLongitude;
	@SerializedName("IsEmailPreferenceID")
	public Boolean IsEmailPreferenceID;

	@SerializedName("Town")
	public String Town;

	@SerializedName("Title")
	public String Title;

	@SerializedName("DoorNumber")
	public String DoorNumber;

	@SerializedName("Street")
	public String Street;

	@SerializedName("Extra1")
	public String Extra1;

	@SerializedName("Extra2")
	public String Extra2;

	@SerializedName("Extra3")
	public String Extra3;

	@SerializedName("Extra4")
	public String Extra4;
	
	@SerializedName("GuestUserPass")
	public String GuestUserPass;
	
	
	@SerializedName("TotalRowCount")
	public String TotalRowCount;
	public Customer() {
	}
}
