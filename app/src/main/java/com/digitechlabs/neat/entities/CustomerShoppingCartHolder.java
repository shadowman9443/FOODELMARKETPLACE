package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class CustomerShoppingCartHolder
{
@SerializedName("CustomerShoppingCartEntity")
    public CustomerShoppingCart customerShoppingCartEntity;

    public CustomerShoppingCartHolder()
    {
    	customerShoppingCartEntity = new CustomerShoppingCart();
    }
}
