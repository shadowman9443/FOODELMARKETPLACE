package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.FeedbackListAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.CustomerOrderHolder;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class MyFeedBackListActivity extends HeaderFooterActivity implements
		FeedbackListAdapter.FeedbackAdapterCallback {

	TextView tvamount, tvtotaltopay, tvtotalperperson, tvTotalTip, tvService,
			tvserviceTitle, tvSplitName,tvTotalTipPerPerson;
	
	ListView feedbackListView;
	FeedbackListAdapter feedbackAdapter;
	Gson gson, gsonUser = null;
	String T, U, deliveredDate, deliveredTime;
	MyNetDatabase localDb;
	Context context;
	final NumberFormat currencyFormatter = NumberFormat
			.getCurrencyInstance();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		super.setContentView(R.layout.feedback_list);
		settingsSelected();
		feedbackListView = (ListView) findViewById(R.id.myfeedback_list);
		
		gson = new Gson();
		init();
		feedbackListView.setAdapter(feedbackAdapter);
	}

	@Override
	protected void onResume() {
		context = this;
		super.onResume();
		localDb = new MyNetDatabase(context);
		getFeedBackList();
	}

	public void init() {
		
		
	}
	private int getUserLoginID(){
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}
	public void getFeedBackList(){
		final ArrayList<FeedbackItem> imageItems = new ArrayList<FeedbackItem>();
		final ProgressDialog progressDialog = new ProgressDialog(
				MyFeedBackListActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		/*final NumberFormat currencyFormatter = NumberFormat
				.getCurrencyInstance();*/		
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		//CommonValues.getInstance().LoginUser.UserLoginInfoID
		String order_url = String.format(
				CommonURL.getInstance().GetFeedBackList, getUserLoginID());

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				order_url, null, new Response.Listener<JSONObject>() {
					
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
							
							CustomerOrderHolder userRoot = gson.fromJson(
									response.toString(),
									CustomerOrderHolder.class);
							if (userRoot != null
									&& userRoot.customerOrderEntity != null) {
								
								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
								for (int i = 0; i < userRoot.customerOrderEntity.size(); i++) {

									deliveredDate = userRoot.customerOrderEntity.get(i).DeliveryDate;
									deliveredTime = userRoot.customerOrderEntity.get(i).OrderDeliveredTime;
									

									try {
										T = CommonTask.toMessageDateShortFormat(deliveredDate);
										U = CommonTask.convertJsonDateToLiveAlarm2(deliveredTime);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//userRoot.loyaltyPointList.get(i).TotalAmount += userRoot.loyaltyPointList.get(i).TotalAmount; 
									
									imageItems
											.add(new FeedbackItem(
													T,
													U,
													userRoot.customerOrderEntity.get(i).TotalAmount,
													userRoot.customerOrderEntity.get(i).IsFeedBack,
													userRoot.customerOrderEntity.get(i).CustomerOrderID
													));
									
								}
								feedbackAdapter = new FeedbackListAdapter(
										MyFeedBackListActivity.this,
										 imageItems);

								feedbackListView.setAdapter(feedbackAdapter);

							} else {
								Toast.makeText(
										MyFeedBackListActivity.this,
										"Your order hasn't been delivered yet. To provide feedback wait untill your order is delivered",
										Toast.LENGTH_LONG).show();
								Intent intent = new Intent(MyFeedBackListActivity.this,
										HomePageActivity.class);
								
								intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								startActivity(intent);
								//finish();
								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
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
	public void onFeedbackPage(String date, String time, String totoalPrice, int customerOrderId) {
		// TODO Auto-generated method stub
		Intent modify_intent = new Intent(MyFeedBackListActivity.this,
				FeedBackActivity.class);
		modify_intent.putExtra("Delivery_Date",
				date);
		modify_intent.putExtra("Delivery_Time", time);
		modify_intent.putExtra("Total_Price", totoalPrice);
		modify_intent.putExtra("Customer_Order_Id", customerOrderId);
		modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(modify_intent);
	}
	

	/*private void getLoyaltyHistoryFromServer() {
		final ProgressDialog progressDialog = new ProgressDialog(
				neat_tip_calculator.this, AlertDialog.THEME_HOLO_LIGHT);

		final ArrayList<LoyaltyProductItem> imageItems = new ArrayList<LoyaltyProductItem>();
		progressDialog.setMessage("Processing....");
		progressDialog.show();
		String URL_Login = String.format(
				CommonURL.getInstance().GetAllLoyaltyPoints, "1",
				CommonValues.getInstance().LoginUser.UserLoginInfoID);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				URL_Login, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							
							if(response)
							{
								
							}
							else {


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
*/
}
