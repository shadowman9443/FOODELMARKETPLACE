package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class CustomerHolder {
	//CustomerEntity
	@SerializedName("CustomerEntity")
    public Customer CustomerEntity;

    public CustomerHolder()
    {
    	CustomerEntity = new Customer();
    }
}
