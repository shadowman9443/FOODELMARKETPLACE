package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class UserLoginInfoHolder {

	@SerializedName("VerifiedUserInfo")
    public UserLoginInfo UserLoginInfoEntity;

    public UserLoginInfoHolder()
    {
          UserLoginInfoEntity = new UserLoginInfo();
    }
}
