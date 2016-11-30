package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class BookATableHolder {

	@SerializedName("BookTableList")
    public BookATable bookATableEntity;

    public BookATableHolder()
    {
    	bookATableEntity = new BookATable();
    }
}
