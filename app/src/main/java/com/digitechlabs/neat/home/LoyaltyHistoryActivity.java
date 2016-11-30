package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.LoyaltyPointAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.entities.LoyaltyPointHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class LoyaltyHistoryActivity extends HeaderFooterActivity{

	ListView loyaltyPointListView;
	LoyaltyPointAdapter loyaltyAdapter;
	Gson gson,gsonUser = null;
	int pageIndex = 1;
	double SumPoints = 0.0 ;
	int LoginUserNumber = 0;
	int points;
	String T, U;
	ImageView loyaltystar;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.loyalty_history);
		homeSelected();
		loyaltyPointListView = (ListView) findViewById(R.id.loyaltyList);
		//getLoyaltyHistoryFromServer();
		gson = new Gson();
		loyaltyPointListView.setAdapter(loyaltyAdapter);
		loyaltystar = (ImageView)findViewById(R.id.loyaltystar);
	}
	@Override
	protected void onResume() {

		super.onResume();
		homeSelected();
		pageIndex = 1;
		
		getLoyaltyHistoryFromServer();
	}
	public void OnPressloyaltyCalendar(View Preessed)
	{
		Intent intent = new Intent(LoyaltyHistoryActivity.this,
				LoyaltyHistoryActivity.class);
	
		 
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);	
		
	}
	
	public void OnPressloyaltystar(View Preessed)
	{
		Intent intent = new Intent(LoyaltyHistoryActivity.this,
				LoyaltyPointsActivity.class);
	
		intent.putExtra("POINTS", SumPoints);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);	
		
	}

	public void OnPressloyaltyQuestion(View Preessed)
	{
		Intent intent = new Intent(LoyaltyHistoryActivity.this,
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
		final ProgressDialog progressDialog = new ProgressDialog(
				LoyaltyHistoryActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		/*
		 * progressDialog = new ProgressDialog(ProductListActivity.this,
		 * AlertDialog.THEME_HOLO_LIGHT);
		 */
		final ArrayList<LoyaltyProductItem> imageItems = new ArrayList<LoyaltyProductItem>();
		progressDialog.setMessage("Processing....");
		progressDialog.show();
		String URL_Login = String.format(
				CommonURL.getInstance().GetAllLoyaltyPoints, AppConstant.RESTAURANTID,
				getUserLoginID());

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				URL_Login, null, new Response.Listener<JSONObject>() {
				
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
	
							loyaltyPointListView = (ListView) findViewById(R.id.loyaltyList);
							LoyaltyPointHolder userRoot = gson.fromJson(
									response.toString(),
									LoyaltyPointHolder.class);
							if (progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
							if (userRoot != null
									&& userRoot.loyaltyPointList != null) {

								for (int i = 0; i < userRoot.loyaltyPointList.size(); i++) {

									String date = userRoot.loyaltyPointList.get(i).DeliveryDate;
									try {
										T = CommonTask.toMessageDateShortFormat(date);
										U = CommonTask.convertJsonDateToLiveAlarm2(date);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//userRoot.loyaltyPointList.get(i).TotalAmount += userRoot.loyaltyPointList.get(i).TotalAmount; 
									SumPoints = SumPoints + userRoot.loyaltyPointList.get(i).TotalAmount;
							        points =  (int) userRoot.loyaltyPointList.get(i).TotalAmount;
									  
									points= (int) Math.floor(points);
									imageItems
											.add(new LoyaltyProductItem(
													T,
													U,
													userRoot.loyaltyPointList.get(i).TotalAmount,
													points,0
													));
									// Log.d(Tag, "" +
									// userRoot.productList.get(i).Price);

								}
								// boxAdapter = new
								// ListAdapter(ProductListActivity.this,R.layout.row_grid,
								// imageItems);
								loyaltyAdapter = new LoyaltyPointAdapter(
										LoyaltyHistoryActivity.this,
										 imageItems);

								loyaltyPointListView.setAdapter(loyaltyAdapter);

								if (pageIndex == 1) {

									/*
									 * customGridAdapter = new GridViewAdapter(
									 * ProductListActivity.this,
									 * R.layout.row_grid, imageItems);
									 * gridView.setAdapter(customGridAdapter);
									 */

								} else {

									/*
									 * customGridAdapter.addAll(imageItems);
									 * customGridAdapter.notifyDataSetChanged();
									 */
								}
								// LoadData();

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
