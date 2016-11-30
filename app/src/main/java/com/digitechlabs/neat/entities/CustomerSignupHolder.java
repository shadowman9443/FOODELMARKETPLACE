package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class CustomerSignupHolder {

	@SerializedName("UserLoginInfoEntity") 
    public UserLoginInfo UserLoginInfoEntity;

    public CustomerSignupHolder()
    {
          UserLoginInfoEntity = new UserLoginInfo();
    }
}