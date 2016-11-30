package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class UserInfoHolder {

	@SerializedName("UserInfo")
    public UserLoginInfo UserLoginInfoEntity;

    public UserInfoHolder()
    {
          UserLoginInfoEntity = new UserLoginInfo();
    }
}
