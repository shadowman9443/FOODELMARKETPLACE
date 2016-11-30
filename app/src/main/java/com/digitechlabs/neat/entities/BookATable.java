package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class BookATable {

	public int getTableID() {
		return TableID;
	}
	public void setTableID(int tableID) {
		TableID = tableID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	public int getPartySize() {
		return PartySize;
	}
	public void setPartySize(int partySize) {
		PartySize = partySize;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	public int getRestaurantID() {
		return RestaurantID;
	}
	public void setRestaurantID(int restaurantID) {
		RestaurantID = restaurantID;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getCreatedTime() {
		return CreatedTime;
	}
	public void setCreatedTime(String createdTime) {
		CreatedTime = createdTime;
	}
	public boolean isStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
	public int getTableBookStaus() {
		return TableBookStaus;
	}
	public void setTableBookStaus(int tableBookStaus) {
		TableBookStaus = tableBookStaus;
	}
	public int getTotalRowCount() {
		return TotalRowCount;
	}
	public void setTotalRowCount(int totalRowCount) {
		TotalRowCount = totalRowCount;
	}
	public String getContactNumber() {
		return ContactNumber;
	}
	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}
	@SerializedName("TableID")
	public int TableID;
	@SerializedName("UserName")
	public String UserName;
	@SerializedName("SurName")
	public String SurName;
	@SerializedName("EmailAddress")
	public String EmailAddress;
	@SerializedName("DateTime")
	public String DateTime;
	@SerializedName("PartySize")
	public int PartySize;
	@SerializedName("Notes")
	public String Notes;
	@SerializedName("RestaurantID")
	public int RestaurantID;
	@SerializedName("UserID")
	public int UserID;
	@SerializedName("CreatedTime")
	public String CreatedTime;
	@SerializedName("Status")
	public boolean Status;
	@SerializedName("TableBookStaus")
	public int TableBookStaus;
	@SerializedName("TotalRowCount")
	public int TotalRowCount;
	@SerializedName("ContactNumber")
	public String ContactNumber;
	public BookATable() {
	}
}
