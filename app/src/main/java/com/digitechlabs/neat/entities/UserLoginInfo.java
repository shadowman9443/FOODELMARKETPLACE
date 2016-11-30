package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class UserLoginInfo {

	public int getUserLoginInfoID() {
		return UserLoginInfoID;
	}

	public void setUserLoginInfoID(int userLoginInfoID) {
		UserLoginInfoID = userLoginInfoID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getLoweredUserName() {
		return LoweredUserName;
	}

	public void setLoweredUserName(String loweredUserName) {
		LoweredUserName = loweredUserName;
	}

	public boolean isIsAnonymous() {
		return IsAnonymous;
	}

	public void setIsAnonymous(boolean isAnonymous) {
		IsAnonymous = isAnonymous;
	}

	public int getUserReferenceID() {
		return UserReferenceID;
	}

	public void setUserReferenceID(int userReferenceID) {
		UserReferenceID = userReferenceID;
	}

	public int getUserTypeID() {
		return UserTypeID;
	}

	public void setUserTypeID(int userTypeID) {
		UserTypeID = userTypeID;
	}

	public int getUserStatusID() {
		return UserStatusID;
	}

	public void setUserStatusID(int userStatusID) {
		UserStatusID = userStatusID;
	}

	public String getPasswordQuestion() {
		return PasswordQuestion;
	}

	public void setPasswordQuestion(String passwordQuestion) {
		PasswordQuestion = passwordQuestion;
	}

	public String getPasswordAnswer() {
		return PasswordAnswer;
	}

	public void setPasswordAnswer(String passwordAnswer) {
		PasswordAnswer = passwordAnswer;
	}

	public boolean isIsApproved() {
		return IsApproved;
	}

	public void setIsApproved(boolean isApproved) {
		IsApproved = isApproved;
	}

	public boolean isIsLockedOut() {
		return IsLockedOut;
	}

	public void setIsLockedOut(boolean isLockedOut) {
		IsLockedOut = isLockedOut;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public String getLastLoginDate() {
		return LastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		LastLoginDate = lastLoginDate;
	}

	public String getLastPasswordChangedDate() {
		return LastPasswordChangedDate;
	}

	public void setLastPasswordChangedDate(String lastPasswordChangedDate) {
		LastPasswordChangedDate = lastPasswordChangedDate;
	}

	public String getLastLockoutDate() {
		return LastLockoutDate;
	}

	public void setLastLockoutDate(String lastLockoutDate) {
		LastLockoutDate = lastLockoutDate;
	}

	public String getLastActivityDate() {
		return LastActivityDate;
	}

	public void setLastActivityDate(String lastActivityDate) {
		LastActivityDate = lastActivityDate;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	@SerializedName("UserLoginInfoID")
    public int UserLoginInfoID;
	@SerializedName("UserID")
    public String UserID;
	@SerializedName("Password")
    public String Password;
	@SerializedName("UserName")
    public String UserName;
	@SerializedName("LoweredUserName")
    public String LoweredUserName;
	@SerializedName("IsAnonymous")
    public boolean IsAnonymous;
	@SerializedName("UserReferenceID")
    public int UserReferenceID;
	@SerializedName("UserTypeID")
    public int UserTypeID;
	@SerializedName("UserStatusID")
    public int UserStatusID;
	@SerializedName("PasswordQuestion")
    public String PasswordQuestion;
	@SerializedName("PasswordAnswer")
    public String PasswordAnswer;
	@SerializedName("IsApproved")
    public boolean IsApproved;
	@SerializedName("IsLockedOut")
    public boolean IsLockedOut;
	@SerializedName("CreateDate")
    public String CreateDate;
	@SerializedName("LastLoginDate")
    public String LastLoginDate;
	@SerializedName("LastPasswordChangedDate")
    public String LastPasswordChangedDate;
	@SerializedName("LastLockoutDate")
    public String LastLockoutDate;
	@SerializedName("LastActivityDate")
    public String LastActivityDate;
	@SerializedName("Comment")
    public String Comment;
	@SerializedName("Extra3")
    public String Extra3;
	
	
    public UserLoginInfo()
    {
    }
}
