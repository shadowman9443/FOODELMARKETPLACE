package com.digitechlabs.neat.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CustomerShoppingCartListHolder
{
@SerializedName("CustomerShoppingCartList")
    public List<CustomerShoppingCart> CustomerShoppingCartList;

    public CustomerShoppingCartListHolder()
    {
    	CustomerShoppingCartList = new ArrayList<CustomerShoppingCart>();
    }
}
