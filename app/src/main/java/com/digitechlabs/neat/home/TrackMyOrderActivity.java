package com.digitechlabs.neat.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.CustomerOrderCommentsHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;

public class TrackMyOrderActivity extends HeaderFooterActivity {

	EditText etValueforMoney, etComments;
	RelativeLayout llOrderRecieved, llBeingcooked, llCookingFinished,
			llCOokingDispatched, llOrderDelivered, llOrderAcceptedMessage,
			llOrderPreparedMessage, llOrderEnrouteMessage,
			llOrderOrderArrivedMessage, llAll,

	llOrderdeliverdMessage, llOrderCancelMessage;
	TextView tvJobNo, txttimeorderaccepted, textView4, tvOrderStatusMessage,orderDeliveredTime, orderArrivedTime, enrouteTime;
	Context context;
	Button btnSubmit, btnCancel;
	ImageView ivOrderAcceptedwithouttick, ivOrderAcceptedtick,
			ivOrderPreparedtick, ivOrderPreparedwithouttick,ivOrderEnroute, ivOrderArrived, ivOrderDelivered;
	Gson gson = null;
	int j;
	String T;
	MyNetDatabase localDb;
	Intent intent;
	int orderID;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.neat_track_my_order);
		homeSelected();
		// startService(new Intent(this, MyNetService.class));



		gson = new Gson();
		init();
		homeSelected();
		localDb = new MyNetDatabase(context);
		//CommonValues.getInstance().LoginUser.UserLoginInfoID
		getUserLoginID();
		GetAllStatusByOrderId(orderID);

	}

	private void init() {
		// TODO Auto-generated method stub
		context = this;
		intent = getIntent();
		orderID = intent.getExtras().getInt("ORDER_ID");
		llOrderRecieved = (RelativeLayout) findViewById(R.id.llOrderRecieved);
		llBeingcooked = (RelativeLayout) findViewById(R.id.llBeingcooked);
		// llAll = (RelativeLayout) findViewById(R.id.llAll);
		llCookingFinished = (RelativeLayout) findViewById(R.id.llCookingFinished);
		llCOokingDispatched = (RelativeLayout) findViewById(R.id.llCOokingDispatched);
		llOrderDelivered = (RelativeLayout) findViewById(R.id.llOrderDelivered);
		ivOrderAcceptedwithouttick = (ImageView) findViewById(R.id.ivOrderAcceptedwithouttick);
		ivOrderPreparedwithouttick = (ImageView) findViewById(R.id.ivOrderPreparedwithouttick);
		ivOrderEnroute= (ImageView) findViewById(R.id.ivOrderEnroute);
		ivOrderArrived= (ImageView) findViewById(R.id.ivArrived);
		ivOrderDelivered = (ImageView) findViewById(R.id.ivOrderDelivered);
		/*ivOrderAcceptedtick = (ImageView) findViewById(R.id.ivOrderAcceptedtick);
		ivOrderPreparedtick = (ImageView) findViewById(R.id.ivOrderPreparedtick);*/
		txttimeorderaccepted = (TextView) findViewById(R.id.txttimeorderaccepted);
		tvOrderStatusMessage = (TextView) findViewById(R.id.tvOrderStatusMessage);
		orderDeliveredTime = (TextView) findViewById(R.id.orderDeliveredTime);
		orderArrivedTime = (TextView) findViewById(R.id.orderArrivedTime);
		enrouteTime = (TextView) findViewById(R.id.enrouteTime);
		textView4 = (TextView) findViewById(R.id.textView4);
		llOrderAcceptedMessage = (RelativeLayout) findViewById(R.id.llOrderAcceptedMessage);

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private int getUserLoginID(){
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}

	@Override
	protected void onResume() {
		super.onResume();
		/*init();
		homeSelected();
		localDb = new MyNetDatabase(context);
		//CommonValues.getInstance().LoginUser.UserLoginInfoID
		getUserLoginID();
		GetAllStatusByOrderId(orderID);*/

		getUserLoginID();
		GetAllStatusByOrderId(orderID);
	}

	private void GetAllStatusByOrderId(int customerOrderId) {
		String URL = "";
		// URL =
		// String.format(CommonURL.getInstance().CreateCustomer,URLEncoder.encode(userCreateData,
		// "UTF8"));
		URL = String.format(CommonURL.getInstance().GetAllStatusByOrderId,
				customerOrderId,getUserLoginID());

		Log.i("Url Are ",URL);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL,
				null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				if (response != null) {

					// GetProductCategoryByResturantID
					CustomerOrderCommentsHolder productroot = gson
							.fromJson(response.toString(),
									CustomerOrderCommentsHolder.class);
					if (productroot != null
							&& productroot.CustomerOrderCommentsEntityList != null) {
						for (int i = 0; i < productroot.CustomerOrderCommentsEntityList
								.size(); i++) {

							llOrderAcceptedMessage
									.setVisibility(View.VISIBLE);
							if (productroot.CustomerOrderCommentsEntityList
									.get(i).MDCustomerOrderStatusID == 1) {
								try {
									T = CommonTask
											.convertJsonDateToLiveAlarm2(productroot.CustomerOrderCommentsEntityList
													.get(i).CommentsTime);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								//ivOrderAcceptedwithouttick.setBackgroundDrawable(R.drawable.tick);
								ivOrderAcceptedwithouttick.setImageDrawable(null);
								ivOrderAcceptedwithouttick.setBackgroundResource(R.drawable.tick);
								txttimeorderaccepted
										.setVisibility(View.VISIBLE);
								txttimeorderaccepted.setText(T);
								tvOrderStatusMessage.setText(R.string.ORDERACCEPTED);

							}
							else if (productroot.CustomerOrderCommentsEntityList
									.get(i).MDCustomerOrderStatusID == 2) {
								try {
									T = CommonTask
											.convertJsonDateToLiveAlarm2(productroot.CustomerOrderCommentsEntityList.get(i).CommentsTime);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								ivOrderAcceptedwithouttick.setImageDrawable(null);
								ivOrderAcceptedwithouttick.setBackgroundResource(R.drawable.tick);
								ivOrderPreparedwithouttick.setImageDrawable(null);
								ivOrderPreparedwithouttick.setBackgroundResource(R.drawable.tick);
								textView4.setVisibility(View.VISIBLE);
								textView4.setText(T);
								tvOrderStatusMessage.setText(R.string.ORDERPREPARED);

							}
							else if (productroot.CustomerOrderCommentsEntityList
									.get(i).MDCustomerOrderStatusID == 3) {
								try {
									T = CommonTask
											.convertJsonDateToLiveAlarm2(productroot.CustomerOrderCommentsEntityList
													.get(i).CommentsTime);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								ivOrderAcceptedwithouttick.setImageDrawable(null);
								ivOrderAcceptedwithouttick.setBackgroundResource(R.drawable.tick);
								ivOrderPreparedwithouttick.setImageDrawable(null);
								ivOrderPreparedwithouttick.setBackgroundResource(R.drawable.tick);
								ivOrderEnroute.setImageDrawable(null);
								ivOrderEnroute.setBackgroundResource(R.drawable.tick);
								enrouteTime.setVisibility(View.VISIBLE);
								enrouteTime.setText(T);
								tvOrderStatusMessage.setText(R.string.ORDERENROUTE);
							}
							else if (productroot.CustomerOrderCommentsEntityList
									.get(i).MDCustomerOrderStatusID == 10) {
								try {
									T = CommonTask
											.convertJsonDateToLiveAlarm2(productroot.CustomerOrderCommentsEntityList
													.get(i).CommentsTime);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								ivOrderAcceptedwithouttick.setImageDrawable(null);
								ivOrderAcceptedwithouttick.setBackgroundResource(R.drawable.tick);
								ivOrderPreparedwithouttick.setImageDrawable(null);
								ivOrderPreparedwithouttick.setBackgroundResource(R.drawable.tick);
								ivOrderEnroute.setImageDrawable(null);
								ivOrderEnroute.setBackgroundResource(R.drawable.tick);
								ivOrderArrived.setImageDrawable(null);
								ivOrderArrived.setBackgroundResource(R.drawable.tick);
								orderArrivedTime.setVisibility(View.VISIBLE);
								orderArrivedTime.setText(T);
								//tvOrderStatusMessage.setText(R.string.ORDERARRIVED);
								if(!AppConstant.APP_GCM_DELAIED_MSG.isEmpty())
								{
									tvOrderStatusMessage.setText(AppConstant.APP_GCM_DELAIED_MSG);
								}
								else
								{
									tvOrderStatusMessage.setText(R.string.ORDERARRIVED);
								}
							}
							else if (productroot.CustomerOrderCommentsEntityList
									.get(i).MDCustomerOrderStatusID == 5) {
								try {
									T = CommonTask
											.convertJsonDateToLiveAlarm2(productroot.CustomerOrderCommentsEntityList
													.get(i).CommentsTime);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								ivOrderAcceptedwithouttick.setImageDrawable(null);
								ivOrderAcceptedwithouttick.setBackgroundResource(R.drawable.tick);
								ivOrderPreparedwithouttick.setImageDrawable(null);
								ivOrderPreparedwithouttick.setBackgroundResource(R.drawable.tick);
								ivOrderEnroute.setImageDrawable(null);
								ivOrderEnroute.setBackgroundResource(R.drawable.tick);
								ivOrderArrived.setImageDrawable(null);
								ivOrderArrived.setBackgroundResource(R.drawable.tick);
								ivOrderDelivered.setImageDrawable(null);
								ivOrderDelivered.setBackgroundResource(R.drawable.tick);
								orderDeliveredTime.setVisibility(View.VISIBLE);
								orderDeliveredTime.setText(T);
								tvOrderStatusMessage.setText(R.string.ORDERDELIVERED);
							}
							else if (productroot.CustomerOrderCommentsEntityList
									.get(i).MDCustomerOrderStatusID == 13) {
								Toast.makeText(
										TrackMyOrderActivity.this,
										"Your order is cancel...",
										Toast.LENGTH_LONG).show();
								tvOrderStatusMessage.setText("Order Canceled");
							}
									/*else if (productroot.CustomerOrderCommentsEntityList
											.get(i).MDCustomerOrderStatusID == 10) {
										tvOrderStatusMessage.setText("Order Delayed");
									}*/
						}

					} else {

						Toast.makeText(
								TrackMyOrderActivity.this,
								"No response from server",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

				Toast.makeText(TrackMyOrderActivity.this,
						error.getMessage(), Toast.LENGTH_LONG).show();

			}
		});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
				DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}

	public static int[] splitToComponentTimes(BigDecimal biggy) {
		long longVal = biggy.longValue();
		int hours = (int) longVal / 3600;
		int remainder = (int) longVal - hours * 3600;
		int mins = remainder / 60;
		remainder = remainder - mins * 60;
		int secs = remainder;

		int[] ints = { hours, mins, secs };
		return ints;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}
}
