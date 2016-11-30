package com.digitechlabs.neat.entities;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class PsubcatListParser {

	private static final String PRODUCT_CAT_ID = "MDProductCategoryID";
	private static final String TOTAL_COUNT = "TotalRowCount";
	private static final String RESTURANT_ID = "ResturantID";
	private static final String NAME = "Name";
	private static final String DESCRIPTION = "Description";
	private static final String PATH = "Path";

	public static boolean connect(Context con, String result) throws JSONException, IOException {

		ProductSubCatList.removeSubCatList();
		if (result.length() < 1) {
			return false;

		}

		final JSONObject mainJsonObject = new JSONObject(result);

		JSONArray jsonArray = mainJsonObject.getJSONArray("CetagoryParentInfo");
		ProductSubCatModel productSubCatModel;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject top_list_jsonObject = jsonArray.getJSONObject(i);
			productSubCatModel = new ProductSubCatModel();
			ProductSubCatList productSubCatList = new ProductSubCatList();
			productSubCatModel.setDescription(top_list_jsonObject.getString(DESCRIPTION));
			productSubCatModel.setMDProductCategoryID(top_list_jsonObject.getInt(PRODUCT_CAT_ID));
			productSubCatModel.setName(top_list_jsonObject.getString(NAME));
			productSubCatModel.setPath(top_list_jsonObject.getString(PATH));
			productSubCatModel.setResturantID(top_list_jsonObject.getInt(RESTURANT_ID));
			productSubCatModel.setTotalRowCount(top_list_jsonObject.getInt(TOTAL_COUNT));
			Log.i("productSubCatModel ",top_list_jsonObject.getString(NAME));
			
			productSubCatList.setSubCatList(productSubCatModel);
			
			productSubCatModel = null;
		}

		return true;
	}
}
