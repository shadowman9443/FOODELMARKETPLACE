package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class User {
	@SerializedName("UserID")
	public int UserID;
	@SerializedName("Name")
	public String Name;
	@SerializedName("Email")
	public String Email;
	@SerializedName("MobileNo")
	public String MobileNo;
	@SerializedName("Password")
	public String Password;
	@SerializedName("UserLevelID")
	public int UserLevelID;
	@SerializedName("CustomerProfileComplete")
	public int CustomerProfileComplete;
	@SerializedName("AgentName")
	public String AgentName;
	@SerializedName("AgentPhoneNumber")
	public String AgentPhoneNumber;
	@SerializedName("TrailKey")
	public String TrailKey;
	@SerializedName("LoginType")
	public String LoginType;
}
