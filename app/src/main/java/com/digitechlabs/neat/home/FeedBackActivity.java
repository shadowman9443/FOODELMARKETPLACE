package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
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
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.NumberFormat;

public class FeedBackActivity extends HeaderFooterActivity {

	TextView tvSingleOrderDetails;
	Gson gson, gsonUser = null;
	String T, U, deliveredDate, deliveredTime;
	RatingBar qualityRating, serviceRating, deliveryTimeRating, valueForMoneyRating;
	Button btnSubmitFeedback;
	EditText suggestion;
	Intent intent;
	int customerOrderId;
	String deliveryDate, deliveryTime, totalPrice;
	MyNetDatabase localDb;
	Context context;
	final NumberFormat currencyFormatter = NumberFormat
			.getCurrencyInstance();
	int qualityPoints, servicePoints, deliveryPoints, valueForMoneyPoints;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.feedback);
		context = this;
		gson = new Gson();
		init();
		
	}

	@Override
	protected void onResume() {

		super.onResume();
		localDb = new MyNetDatabase(context);
	}
	private int getUserLoginID(){
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}
	public void init() {
		intent = getIntent();
		customerOrderId = intent.getIntExtra("Customer_Order_Id",0);
		deliveryDate = intent.getExtras().getString("Delivery_Date");
		deliveryTime = intent.getExtras().getString("Delivery_Time");
		totalPrice = intent.getExtras().getString("Total_Price");
		btnSubmitFeedback= (Button) findViewById(R.id.btnsubmitfeedback);
		suggestion = (EditText) findViewById(R.id.etsuggestion);
		tvSingleOrderDetails= (TextView) findViewById(R.id.tvSingleOrderDetail);
		tvSingleOrderDetails.setText("Order:"+ "\t" + deliveryDate + "\t" + deliveryTime + "\t" + totalPrice);
		qualityRating = (RatingBar) findViewById(R.id.foodqualityRating);
		serviceRating = (RatingBar) findViewById(R.id.serviceRating);
		deliveryTimeRating = (RatingBar) findViewById(R.id.deliverytimingRating);
		valueForMoneyRating = (RatingBar) findViewById(R.id.valueformoneyRating);
		qualityRating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                    boolean fromUser) {
                
            	qualityPoints = (int) ratingBar.getRating();
               
            }
        });
		serviceRating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                    boolean fromUser) {
                
               
            	servicePoints = (int) ratingBar.getRating();
            }
        });
		deliveryTimeRating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                    boolean fromUser) {
                
            	deliveryPoints = (int) ratingBar.getRating();
               
            }
        });
		valueForMoneyRating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                    boolean fromUser) {
                
            	valueForMoneyPoints = (int) ratingBar.getRating();
            }
        });
		
		btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				submitRating();
			}
		});
		
		
		LayerDrawable stars = (LayerDrawable) qualityRating.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratting_color), PorterDuff.Mode.SRC_ATOP);
		
		LayerDrawable stars1 = (LayerDrawable) serviceRating.getProgressDrawable();
		stars1.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratting_color), PorterDuff.Mode.SRC_ATOP);
		
		LayerDrawable stars2 = (LayerDrawable) deliveryTimeRating.getProgressDrawable();
		stars2.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratting_color), PorterDuff.Mode.SRC_ATOP);
		
		LayerDrawable stars3 = (LayerDrawable) valueForMoneyRating.getProgressDrawable();
		stars3.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratting_color), PorterDuff.Mode.SRC_ATOP);
	}

	public void submitRating(){
		String getSuggestion= suggestion.getText().toString().trim();
		final ProgressDialog progressDialog = new ProgressDialog(
				FeedBackActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		/*final NumberFormat currencyFormatter = NumberFormat
				.getCurrencyInstance();*/		
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		//CommonValues.getInstance().LoginUser.UserLoginInfoID
		String order_url = String.format(
				CommonURL.getInstance().SetFeedBack,customerOrderId,getUserLoginID(), qualityPoints, servicePoints, deliveryPoints,
				valueForMoneyPoints, getSuggestion);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				order_url, null, new Response.Listener<JSONObject>() {
					
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {	
							if (progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
							Toast.makeText(
									FeedBackActivity.this,
									"Feedback Submitted Successfully",
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent(FeedBackActivity.this,
									MyFeedBackListActivity.class);

							intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(intent);
							
								
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
