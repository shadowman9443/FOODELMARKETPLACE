package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class UserInfoPinHolder {

	@SerializedName("NewPin")
    public UserLoginInfo UserLoginInfoEntity;

    public UserInfoPinHolder()
    {
          UserLoginInfoEntity = new UserLoginInfo();
    }
}
