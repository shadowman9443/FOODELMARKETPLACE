package com.digitechlabs.neat.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CustomerShoppingCartDetailedListHolder
{
@SerializedName("CustomerShoppingCartDetailedList")
    public ArrayList<CustomerShoppingCartDetailed> customerShoppingCartList;

    public CustomerShoppingCartDetailedListHolder()
    {
    	customerShoppingCartList = new ArrayList<CustomerShoppingCartDetailed>();
    }
}
