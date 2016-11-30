package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.entities.OpeningDeliverListHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

public class OpeningDeliveryActivity extends HeaderFooterActivity {
	
	 TextView mondayLunch, mondayDinner, tuesdayLunch, tuesdayDinner, wednesdayLunch, wednesdayDinner, 
	 thursdayLunch, thursdayDinner,fridayLunch, fridayDinner, saturdayLunch, saturdayDinner, sundayLunch, sundayDinner ;
	 Gson gson,gsonUser = null;
	ImageView imgtablebook;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opening_delivery);
		gson = new Gson();
		initControls();
		imgtablebook = (ImageView)findViewById(R.id.imgtablebook);
		if (AppConstant.TABLERESERVATION.contentEquals("") || AppConstant.ASSIGNEDFEATURES.contentEquals("")) {
			imgtablebook.setVisibility(View.VISIBLE);
		} else if (IsActive(AppConstant.TABLERESERVATION, AppConstant.ASSIGNEDFEATURES)) {
			imgtablebook.setVisibility(View.VISIBLE);
		} else {
			imgtablebook.setVisibility(View.GONE);
		}
	}
	public void initControls(){
		mondayLunch= (TextView) findViewById(R.id.mondayLunch);
		tuesdayLunch= (TextView) findViewById(R.id.tuesdayLunch);
		wednesdayLunch= (TextView) findViewById(R.id.wednesdayLunch);
		thursdayLunch= (TextView) findViewById(R.id.thursdayLunch);
		fridayLunch= (TextView) findViewById(R.id.fridayLunch);
		saturdayLunch= (TextView) findViewById(R.id.saturdayLunch);
		sundayLunch= (TextView) findViewById(R.id.sundayLunch);
		mondayDinner= (TextView) findViewById(R.id.mondayDinner);
		tuesdayDinner= (TextView) findViewById(R.id.tuesdayDinner);
		wednesdayDinner= (TextView) findViewById(R.id.wednesdayDinner);
		thursdayDinner= (TextView) findViewById(R.id.thursdayDinner);
		fridayDinner= (TextView) findViewById(R.id.fridayDinner);
		saturdayDinner= (TextView) findViewById(R.id.saturdayDinner);
		sundayDinner= (TextView) findViewById(R.id.sundayDinner);
	}
	@Override
	protected void onResume() {

		super.onResume();
		getOpenningDeliveryData();
	}
	public void getOpenningDeliveryData(){
		final ProgressDialog progressDialog = new ProgressDialog(
				OpeningDeliveryActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		/*final NumberFormat currencyFormatter = NumberFormat
				.getCurrencyInstance();*/		
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		String opening_url = String.format(
				CommonURL.getInstance().GetOpeningDeliveryTime, AppConstant.RESTAURANTID);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				opening_url, null, new Response.Listener<JSONObject>() {
					// AlergimonicID
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
							
							OpeningDeliverListHolder userRoot = gson.fromJson(
									response.toString(),
									OpeningDeliverListHolder.class);
							
							if (userRoot != null
									&& userRoot.openningDeliveryList != null) {
								
								for (int i = 0; i < userRoot.openningDeliveryList.size(); i++) {
										if(userRoot.openningDeliveryList.get(i).Day.toString().equals("MONDAY")){
											
											if (userRoot.openningDeliveryList.get(i).LunchStart.length()<7)
											{
												mondayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart +"  " + userRoot.openningDeliveryList.get(i).LunchEnd); 
												mondayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart +"  " +userRoot.openningDeliveryList.get(i).DinnerEnd);
									
											}
											else
											{
											mondayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart.substring(0,6) +" - " + userRoot.openningDeliveryList.get(i).LunchEnd.substring(0,6)); 
											mondayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart.toString().substring(0,6) +" - " +userRoot.openningDeliveryList.get(i).DinnerEnd.toString().substring(0,6));
											}
										}
										else if(userRoot.openningDeliveryList.get(i).Day.toString().equals("TUESDAY")){
											if (userRoot.openningDeliveryList.get(i).LunchStart.length()<7)
											{
												tuesdayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart +"  " + userRoot.openningDeliveryList.get(i).LunchEnd); 
												tuesdayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart +"  " +userRoot.openningDeliveryList.get(i).DinnerEnd);
									
											}
											else
											{
											tuesdayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart.substring(0,6) +" - " + userRoot.openningDeliveryList.get(i).LunchEnd.substring(0,6));
											tuesdayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart.toString().substring(0,6) +" - " +userRoot.openningDeliveryList.get(i).DinnerEnd.toString().substring(0,6));
											}
										}
										else if(userRoot.openningDeliveryList.get(i).Day.toString().equals("WEDNESDAY")){
											if (userRoot.openningDeliveryList.get(i).LunchStart.length()<7)
											{
												wednesdayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart +"  " + userRoot.openningDeliveryList.get(i).LunchEnd); 
												wednesdayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart +"  " +userRoot.openningDeliveryList.get(i).DinnerEnd);
									
											}
											else
											{
											wednesdayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart.substring(0,6) +" - " + userRoot.openningDeliveryList.get(i).LunchEnd.substring(0,6));
											wednesdayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart.toString().substring(0,6) +" - " +userRoot.openningDeliveryList.get(i).DinnerEnd.toString().substring(0,6));
											}
										}
										else if(userRoot.openningDeliveryList.get(i).Day.toString().equals("THURSDAY")){
											if (userRoot.openningDeliveryList.get(i).LunchStart.length()<7)
											{
												thursdayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart +"  " + userRoot.openningDeliveryList.get(i).LunchEnd); 
												thursdayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart +"  " +userRoot.openningDeliveryList.get(i).DinnerEnd);
									
											}
											else
											{
											thursdayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart.substring(0,6) +" - " + userRoot.openningDeliveryList.get(i).LunchEnd.substring(0,6));
											thursdayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart.toString().substring(0,6) +" - " +userRoot.openningDeliveryList.get(i).DinnerEnd.toString().substring(0,6));
											}
										}
										else if(userRoot.openningDeliveryList.get(i).Day.toString().equals("FRIDAY")){
											if (userRoot.openningDeliveryList.get(i).LunchStart.length()<7)
											{
												fridayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart +"  " + userRoot.openningDeliveryList.get(i).LunchEnd); 
												fridayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart +"  " +userRoot.openningDeliveryList.get(i).DinnerEnd);
									
											}
											else
											{
											fridayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart.substring(0,6) +" - " + userRoot.openningDeliveryList.get(i).LunchEnd.substring(0,6));
											fridayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart.toString().substring(0,6) +" - " +userRoot.openningDeliveryList.get(i).DinnerEnd.toString().substring(0,6));
											}
										}
										else if(userRoot.openningDeliveryList.get(i).Day.toString().equals("SATURDAY")){
											if (userRoot.openningDeliveryList.get(i).LunchStart.length()<7)
											{
												saturdayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart +"  " + userRoot.openningDeliveryList.get(i).LunchEnd); 
												saturdayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart +"  " +userRoot.openningDeliveryList.get(i).DinnerEnd);
									
											}
											else
											{
											saturdayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart.substring(0,6) +" - " + userRoot.openningDeliveryList.get(i).LunchEnd.substring(0,6));
											saturdayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart.toString().substring(0,6) +" - " +userRoot.openningDeliveryList.get(i).DinnerEnd.toString().substring(0,6));
											}
										}
										else if(userRoot.openningDeliveryList.get(i).Day.toString().equals("SUNDAY")){
											if (userRoot.openningDeliveryList.get(i).LunchStart.length()<7)
											{
												sundayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart +"  " + userRoot.openningDeliveryList.get(i).LunchEnd); 
												sundayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart +"  " +userRoot.openningDeliveryList.get(i).DinnerEnd);
											}
											else
											{
											sundayLunch.setText(userRoot.openningDeliveryList.get(i).LunchStart.substring(0,6) +" - " + userRoot.openningDeliveryList.get(i).LunchEnd.substring(0,6));
											sundayDinner.setText(userRoot.openningDeliveryList.get(i).DinnerStart.toString().substring(0,6) +" - " +userRoot.openningDeliveryList.get(i).DinnerEnd.toString().substring(0,6));
											}
										}
								}
								
								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
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
	public void OnOrderFood(View Preessed)
	{
		Intent intent = new Intent(OpeningDeliveryActivity.this,
				CategoryListActivity.class);
	
		 
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);	
		
	}
	
	public void OnBookTable(View Preessed)
	{
		Intent intent = new Intent(OpeningDeliveryActivity.this,
				TableBookingActivity.class);
	
		//intent.putExtra("POINTS", SumPoints);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);	
		
	}

	public void OnCallUS(View Preessed)
	{
		Intent callIntent = new Intent(Intent.ACTION_CALL);
	    callIntent.setData(Uri.parse("tel: " +
				AppConstant.PHONE));
	    startActivity(callIntent);	
		
	}
	
	public void OnMailUs(View Preessed)
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{AppConstant.EMAIL});
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(OpeningDeliveryActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
		
	}
}
