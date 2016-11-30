package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.TrackOrderAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.TrackOrderHolder;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class TrackMyOrderListActivity extends HeaderFooterActivity implements OnItemClickListener{

	ListView loyaltyPointListView;
	TrackOrderAdapter loyaltyAdapter;
	Gson gson,gsonUser = null;
	double SumPoints = 0.0 ;
	int LoginUserNumber = 0;
	int points;
	String T, U;
	MyNetDatabase localDb;
	TextView loyaltyhistory;
	Context context;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.track_order_list);
		homeSelected();
		loyaltyPointListView = (ListView) findViewById(R.id.loyaltyList);
		loyaltyhistory = (TextView) findViewById(R.id.loyaltyhistory);
		gson = new Gson();
		loyaltyPointListView.setAdapter(loyaltyAdapter);
		loyaltyPointListView.setOnItemClickListener(this);
		context = this;
	}
	@Override
	protected void onResume() {
		super.onResume();
		homeSelected();
		context = this;
		loyaltyhistory.setVisibility(View.GONE);
		localDb = new MyNetDatabase(context);
		getLoyaltyHistoryFromServer();
	}
	private int getUserLoginID(){
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}
	private void getLoyaltyHistoryFromServer() {
		final ProgressDialog progressDialog = new ProgressDialog(
				TrackMyOrderListActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		final ArrayList<LoyaltyProductItem> imageItems = new ArrayList<LoyaltyProductItem>();
		progressDialog.setMessage("Processing....");
		progressDialog.show();
		//CommonValues.getInstance().LoginUser.UserLoginInfoID
		String URL_Login = String.format(
				CommonURL.getInstance().GetAllDeliveredOrder,getUserLoginID()
				);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				URL_Login, null, new Response.Listener<JSONObject>() {
				
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
							
							loyaltyPointListView = (ListView) findViewById(R.id.loyaltyList);
							TrackOrderHolder userRoot = gson.fromJson(
									response.toString(),
									TrackOrderHolder.class);
							if (progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
							if (userRoot != null
									&& userRoot.loyaltyPointList != null) {
								if(userRoot.loyaltyPointList.size()==1){
									int orderID = userRoot.loyaltyPointList.get(0).CustomerOrderID;
									Intent modify_intent = new Intent(TrackMyOrderListActivity.this,
											TrackMyOrderActivity.class);
									modify_intent.putExtra("ORDER_ID",
											Integer.parseInt(String.valueOf(orderID)));
									modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
									startActivity(modify_intent);
								}
								else {
									loyaltyhistory.setVisibility(View.VISIBLE);
								for (int i = 0; i < userRoot.loyaltyPointList.size(); i++) {

									String date = userRoot.loyaltyPointList.get(i).OrderDate;
									try {
										T = CommonTask.toMessageDateShortFormat(date);
										
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								/*	SumPoints = SumPoints + userRoot.loyaltyPointList.get(i).TotalAmount;
							        points =  (int) userRoot.loyaltyPointList.get(i).TotalAmount;
									points= (int) Math.floor(points);*/
									imageItems
											.add(new LoyaltyProductItem(
													T,
													"",
													0,
													0, userRoot.loyaltyPointList.get(i).CustomerOrderID
													));
								}								
									loyaltyAdapter = new TrackOrderAdapter(
											TrackMyOrderListActivity.this,
											 imageItems);
									loyaltyPointListView.setAdapter(loyaltyAdapter);
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
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TextView text = (TextView) view.findViewById(R.id.loyaltyDate);
		int pID = Integer.valueOf(String.valueOf(text.getTag()));
		Intent modify_intent = new Intent(TrackMyOrderListActivity.this,
				TrackMyOrderActivity.class);
		modify_intent.putExtra("ORDER_ID",
				Integer.parseInt(String.valueOf(pID)));
		modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(modify_intent);
	}

	}
	

