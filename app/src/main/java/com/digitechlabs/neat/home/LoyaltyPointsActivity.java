package com.digitechlabs.neat.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.LoyaltyHolder;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.NumberFormat;

public class LoyaltyPointsActivity extends HeaderFooterActivity {
	TextView pointsnumber,worthprice;
	double Points,PointsWorth;
	String cr;
	final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	MyNetDatabase localDb;
	Context context;
	Gson gson = null;
	private String redeemPoints,afterloyaity;
	private String redeemAmounts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		homeSelected();
		setContentView(R.layout.loyalty_points);
		Points = savedInstanceState
				.getDouble("POINTS");
		Points= Math.floor(Points);
		PointsWorth =  Math.floor(Points / 100);
		cr = currencyFormatter.format(PointsWorth);
		// startService(new Intent(this, MyNetService.class));fghsdifgi
		init();
		LoadReedimpoints();
		//show();
	}

	private void init() {
		// TODO Auto-generated method stub
		pointsnumber = (TextView) findViewById(R.id.pointsnumber);
		worthprice = (TextView) findViewById(R.id.worthprice);
		gson = new Gson();
		localDb = new MyNetDatabase(context);
	}
	private int getUserLoginID() {
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}
	private void LoadReedimpoints() {
		// making fresh volley request and getting json

		String URL_Login = String.format(CommonURL.getInstance().GetLoyaltyPointsByUser, getUserLoginID());

		JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL_Login, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							LoyaltyHolder userRoot = gson.fromJson(response.toString(), LoyaltyHolder.class);
							if (userRoot != null && userRoot.Loyalty != null) {


								redeemPoints = Double.toString(userRoot.Loyalty.LoyalityPointValue);
								redeemAmounts =  Double.toString(Math.round(userRoot.Loyalty.LoyalityPointValue*100)).split("\\.")[0];


								String cr = currencyFormatter.format(userRoot.Loyalty.LoyalityPointValue);
							//	holder.tvPrice.setText("Item "+cr);
								pointsnumber.setText(String.valueOf(redeemAmounts) + " points");
								// etLastName.setText(booktable.UserName);
								worthprice.setText(String.valueOf(cr));
							} else {

							}
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}
	public void OnPressloyaltyCalendar(View Preessed)
	{
		Intent intent = new Intent(LoyaltyPointsActivity.this,
				LoyaltyHistoryActivity.class);


		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}

	public void OnPressloyaltystar(View Preessed)
	{
		Intent intent = new Intent(LoyaltyPointsActivity.this,
				LoyaltyPointsActivity.class);

		intent.putExtra("POINTS", Points);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}

	public void OnPressloyaltyQuestion(View Preessed)
	{
		Intent intent = new Intent(LoyaltyPointsActivity.this,
				LoyaltyIntroductionActivity.class);


		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}
	private void show() {

		pointsnumber.setText(String.valueOf(Points) + " points");
		// etLastName.setText(booktable.UserName);
		worthprice.setText(String.valueOf(cr));


	}
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		homeSelected();
		init();
		LoadReedimpoints();
	}




}
