package com.digitechlabs.neat.entities;

import java.util.Vector;

public class ProductSubCatList {

	public static Vector<ProductSubCatModel> productSubCatLists = new Vector<ProductSubCatModel>();


	public static Vector<ProductSubCatModel> getAllSubCatList() {
		return productSubCatLists;
	}

	public static void setAllSubCatList(Vector<ProductSubCatModel> productSubCatLists) {
		ProductSubCatList.productSubCatLists = productSubCatLists;
	}

	public static ProductSubCatModel getSubCatList(int pos) {
		return productSubCatLists.elementAt(pos);
	}

	public static void setSubCatList(ProductSubCatModel productSubCatLists) {
		ProductSubCatList.productSubCatLists.addElement(productSubCatLists);
	}

	public static void removeSubCatList() {
		ProductSubCatList.productSubCatLists.removeAllElements();
	}

}
