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
import com.digitechlabs.neat.adapter.LoyaltyPointAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.LoyaltyPointHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class OrderHistory extends HeaderFooterActivity implements OnItemClickListener{

	ListView loyaltyPointListView;
	LoyaltyPointAdapter loyaltyAdapter;
	Gson gson,gsonUser = null;
	double SumPoints = 0.0 ;
	int LoginUserNumber = 0;
	int points;
	String T, U;
	MyNetDatabase localDb;
	Context context;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.order_history);
		loyaltyPointListView = (ListView) findViewById(R.id.loyaltyList);
		settingsSelected();
		gson = new Gson();
		loyaltyPointListView.setAdapter(loyaltyAdapter);
		loyaltyPointListView.setOnItemClickListener(this);
		context = this;
	}
	@Override
	protected void onResume() {
		super.onResume();
		context = this;
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
				OrderHistory.this, AlertDialog.THEME_HOLO_LIGHT);
		final ArrayList<LoyaltyProductItem> imageItems = new ArrayList<LoyaltyProductItem>();
		progressDialog.setMessage("Processing....");
		progressDialog.show();
		//CommonValues.getInstance().LoginUser.UserLoginInfoID
		String URL_Login = String.format(
				CommonURL.getInstance().GetAllLoyaltyPoints, AppConstant.RESTAURANTID,getUserLoginID()
				);

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
									SumPoints = SumPoints + userRoot.loyaltyPointList.get(i).TotalAmount;
							        points =  (int) userRoot.loyaltyPointList.get(i).TotalAmount;
									points= (int) Math.floor(points);
									imageItems
											.add(new LoyaltyProductItem(
													T,
													U,
													userRoot.loyaltyPointList.get(i).TotalAmount,
													points, userRoot.loyaltyPointList.get(i).CustomerOrderID
													));
								}								
								loyaltyAdapter = new LoyaltyPointAdapter(
										OrderHistory.this,
										 imageItems);
								loyaltyPointListView.setAdapter(loyaltyAdapter);

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
		Intent modify_intent = new Intent(OrderHistory.this,
				MyOrderActivity.class);
		modify_intent.putExtra("ORDER_ID",
				Integer.parseInt(String.valueOf(pID)));
		modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(modify_intent);
	}

	}
	

