package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class GusetUser {

	public int getGuestUserId() {
		return GuestUserId;
	}


	public void setGuestUserId(int guestUserId) {
		GuestUserId = guestUserId;
	}


	public String  getDeciceID() {
		return DeciceID;
	}


	public void setDeciceID(String deciceID) {
		DeciceID = deciceID;
	}


	public String getCreatedate() {
		return Createdate;
	}


	public void setCreatedate(String createdate) {
		Createdate = createdate;
	}


	public Double getLastLatitude() {
		return LastLatitude;
	}


	public void setLastLatitude(Double lastLatitude) {
		LastLatitude = lastLatitude;
	}


	public Double getLastLongitude() {
		return LastLongitude;
	}


	public void setLastLongitude(Double lastLongitude) {
		LastLongitude = lastLongitude;
	}


	@SerializedName("GuestUserId")
    public int GuestUserId;
	@SerializedName("DeciceID")
    public String DeciceID;
	@SerializedName("Createdate")
    public String Createdate;
    public Double LastLatitude;
	@SerializedName("LastLongitude")
    public Double LastLongitude;


    public GusetUser()
    {
    }
}
