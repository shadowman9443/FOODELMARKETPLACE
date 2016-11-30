package com.digitechlabs.neat.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.entities.LoyaltyPointHolder;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

public class LoyaltyIntroductionActivity extends HeaderFooterActivity {
	
	 TextView pointsnumber,worthprice;
	 Gson gson,gsonUser = null;
	 double SumPoints = 0.0 ;
	 int points;
	 int k=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loyalty_introduction);
		homeSelected();
		gson = new Gson();
		getLoyaltyHistoryFromServer();		
	}
	public void OnPressloyaltyCalendar(View Preessed)
	{
		Intent intent = new Intent(LoyaltyIntroductionActivity.this,
				LoyaltyHistoryActivity.class);
			 
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);	
		
	}
	
	public void OnPressloyaltystar(View Preessed)
	{
		Intent intent = new Intent(LoyaltyIntroductionActivity.this,
				LoyaltyPointsActivity.class);
	
		intent.putExtra("POINTS", SumPoints);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);	
		
	}

	public void OnPressloyaltyQuestion(View Preessed)
	{
		Intent intent = new Intent(LoyaltyIntroductionActivity.this,
				LoyaltyIntroductionActivity.class);
	
		 
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);	
		
	}
	private int getUserLoginID(){
		db.open();
		int uID = db.getUserID();
		db.close();
		return uID;
	}
	private void getLoyaltyHistoryFromServer() {
		
		String URL_Login = String.format(
				CommonURL.getInstance().GetAllLoyaltyPoints, "1",
				getUserLoginID());

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				URL_Login, null, new Response.Listener<JSONObject>() {
				
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
	
							
							LoyaltyPointHolder userRoot = gson.fromJson(
									response.toString(),
									LoyaltyPointHolder.class);
							if (userRoot != null
									&& userRoot.loyaltyPointList != null) {
								
								for (int i = 0; i < userRoot.loyaltyPointList.size(); i++) {
									SumPoints = SumPoints + userRoot.loyaltyPointList.get(i).TotalAmount;
							        /*points =  (int) userRoot.loyaltyPointList.get(i).TotalAmount;
									points= (int) Math.floor(points);*/
								}
								

							} else {

							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
				DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
	}

	
	
}
