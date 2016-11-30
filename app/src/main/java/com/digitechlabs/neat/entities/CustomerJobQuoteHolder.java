package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class CustomerJobQuoteHolder {

	@SerializedName("CustomerJobQuote")
    public int result;

    public CustomerJobQuoteHolder()
    {
    	result=-1;
    }
}
