package com.digitechlabs.neat.home;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.postcodelistADAPTER;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityPostCode extends HeaderFooterActivity implements OnItemClickListener{

	ListView PostcodeList;
	postcodelistADAPTER postcodeadapter;
	Gson gson,gsonUser = null;
	String pc;
	public String[] xCoords;
	final ArrayList<PostCodeItem> imageItems = new ArrayList<PostCodeItem>();
	ImageView loyaltystar;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.postcode);
		homeSelected();
		PostcodeList = (ListView) findViewById(R.id.PostcodeList);
		//getLoyaltyHistoryFromServer();
		PostcodeList.setOnItemClickListener(this);
		gson = new Gson();
		PostcodeList.setAdapter(postcodeadapter);
		
	}
	@Override
	protected void onResume() {

		super.onResume();
		homeSelected();
	
		loadpostcodeFromServer("DA74QA");
	}
	

	private int getUserLoginID(){
		db.open();
		int uID = db.getUserID();
		db.close();
		return uID;
	}
	
	private void loadpostcodeFromServer(String postcode) {
		// making fresh volley request and getting json
		/*
		 * progressDialog = new ProgressDialog(OrderSendActivity.this,
		 * AlertDialog.THEME_HOLO_LIGHT);
		 * progressDialog.setMessage("Loading...."); progressDialog.show();
		 */
		// http://82.165.197.66:3000/foodelapi/postcodeinfo?postcode=DA74QD
		// postcode=DA74QD public String
		// VerifyUserCredentials=applicationBaseUrl+"Userservice/VerifyUserCredentialsCustomer?userID=%s&pin=%s&userTypeID=%s";
		String URL_Login = "http://82.165.197.66:3000/foodelapi/postcodeinfo?postcode=%s";

		String URL_Loginfx = String.format(CommonURL.getInstance().PostCode, postcode);
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_Loginfx, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							JSONArray data;
							try {
								data = response.getJSONArray("data");
								xCoords = new String[data.length()];
								;

								for (int i = 0; i < data.length(); i++) {
									String s = data.getString(i);

									xCoords[i] = data.getString(i);
									imageItems
									.add(new PostCodeItem(data.getString(i)
											
											));
								}
								postcodeadapter = new postcodelistADAPTER(
										ActivityPostCode.this,
										 imageItems);

								PostcodeList.setAdapter(postcodeadapter);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} else {
							
						}
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						/*
						 * if (progressDialog.isShowing()) {
						 * progressDialog.dismiss(); }
						 */
						//Toast.makeText(OrderSendActivity.this, "No response from server", Toast.LENGTH_LONG).show();
					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		TextView tv = (TextView) view.findViewById(R.id.postcode);
		pc = (String.valueOf(tv.getTag()));
		
		/* Intent modify_intent = new Intent(ProductListActivity.this,
		 ProductDetailViewActivity.class);
		 modify_intent.putExtra("PRODUCT_ID",
		 Integer.parseInt(String.valueOf(pID)));
		 modify_intent.putExtra("Category_ID",
		 Integer.parseInt(String.valueOf(mdProductCategoryId)));
		 modify_intent.putExtra("CategoryName",
		 categoryName.getText().toString());
		 modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		 startActivity(modify_intent);*/
		 

		// for new design
/*
		TextView textView = (TextView) view.findViewById(R.id.postcode);
        String text = textView.getText().toString();

        Intent modify_intent = new Intent(ProductListActivity.this,
       		 ProductDetailViewActivity.class);
       		 modify_intent.putExtra("PRODUCT_ID",
       		 Integer.parseInt(String.valueOf(pID)));
       		 startActivity(modify_intent);*/
	}
}
