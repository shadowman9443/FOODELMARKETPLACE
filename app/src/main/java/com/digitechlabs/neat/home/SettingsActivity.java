package com.digitechlabs.neat.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dgtech.neat.R;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;


public class SettingsActivity extends HeaderFooterActivity {

	RatingBar rbPuncuality, rbCleanliness, rbFriendlyness, rbRoadKnowledge,
			rbServices;

	EditText etValueforMoney, etComments;
	RelativeLayout llMydetails, llOrderHistory, llMyFeedBack,
			llMyFaviouriteItems, llSignIn, llSignOut;
	String prefUserPass = "";
	TextView tvJobNo, txtSignIn, txtSignOut;
	Context context;
	Button btnSubmit, btnCancel;
	int userLoginID, orderStatus;
	private static final int NOTIFY_ME_ID = 1337;
	MyNetDatabase localDb;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		settingsSelected();
		// startService(new Intent(this, MyNetService.class));
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		context = this;
		llMydetails = (RelativeLayout) findViewById(R.id.llMydetails);
		llOrderHistory = (RelativeLayout) findViewById(R.id.llOrderHistory);
		llMyFeedBack = (RelativeLayout) findViewById(R.id.llMyFeedBack);
		llMyFaviouriteItems = (RelativeLayout) findViewById(R.id.llMyFaviouriteItems);

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		context = this;
		localDb = new MyNetDatabase(context);
		userLoginID = getUserLoginID();
		orderStatus = getOrderStatus();
	}
	private int getUserLoginID(){
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}
	private int getOrderStatus(){
		localDb.open();
		int orderStatusID = localDb.getOrderStatus();
		localDb.close();
		return orderStatusID;
	}
	public void OnMyDetails(View pressed) {
		if(userLoginID!=0){
			
				Intent intent = new Intent(SettingsActivity.this,
						MyDetailsActivity.class);
				/*
				 * intent.putExtra("CHECKOUTAMOUNT", "100");
				 */
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			
			
			
		}
		else{
			Toast.makeText(
					SettingsActivity.this,
					"Please login or create account to see your details",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void OnOrderHistory(View pressed) {
		try {
			

			if (userLoginID!=0) {
				if(orderStatus>0){
				Intent intent = new Intent(SettingsActivity.this,
						OrderHistory.class);

				// * intent.putExtra("CHECKOUTAMOUNT", "100");

				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				}
				else{
					Toast.makeText(
							SettingsActivity.this,
							"You have no order",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(
						SettingsActivity.this,
						"Please login or create account to see your order history",
						Toast.LENGTH_SHORT).show();

			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(SettingsActivity.this,
					"Your are not logged in.please login", Toast.LENGTH_LONG)
					.show();
		}

	}

	public void OnMyfeedBack(View pressed) {

		prefUserPass = CommonTask.getPreferences(SettingsActivity.this,
				CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY);

		if (userLoginID!=0) {
			if(orderStatus>0){
				Intent intent = new Intent(SettingsActivity.this,
						MyFeedBackListActivity.class);
				/*
				 * intent.putExtra("CHECKOUTAMOUNT", "100");
				 */
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
			else{
				Toast.makeText(
						SettingsActivity.this,
						"You have no order. To provide feedback you need to order first",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(
					SettingsActivity.this,
					"Please login or create account to see your feedback",
					Toast.LENGTH_SHORT).show();

		}

	}

	public void OnMyfaviouriteItems(View pressed) {
		prefUserPass = CommonTask.getPreferences(SettingsActivity.this,
				CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY);
		if (userLoginID!=0) {
			if(orderStatus>0){
				Intent intent = new Intent(SettingsActivity.this, MyFavouriteListActivity.class);
				/*
				 * intent.putExtra("CHECKOUTAMOUNT", "100");
				 */
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
			else{
				Toast.makeText(
						SettingsActivity.this,
						"You have no order. To see favorite item you need to order first",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(
					SettingsActivity.this,
					"Please login or create account to see your favorite items",
					Toast.LENGTH_SHORT).show();

		}
	}

}
