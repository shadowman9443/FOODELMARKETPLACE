package com.digitechlabs.neat.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CustomerOrderListHolder
{
@SerializedName("CustomerOrderList")
    public List<CustomerOrderEntity> customerOrderList;

    public CustomerOrderListHolder()
    {
    	customerOrderList = new ArrayList<CustomerOrderEntity>();
    }
}
