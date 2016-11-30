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
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonValues;


public class HomePageActivity extends HeaderFooterActivity {

	RatingBar rbPuncuality, rbCleanliness, rbFriendlyness, rbRoadKnowledge, rbServices;

	EditText etValueforMoney, etComments;
	RelativeLayout llOrderfood, llTrackMyOrder, llLoyaltyPoints, llBookaTable, llSignIn, llSignOut;
	String prefUserPass = "";
	TextView tvJobNo, txtSignIn, txtSignOut;
	Context context;
	Button btnSubmit, btnCancel;
	MyNetDatabase localDb;
	int userLoginId, userOrderStatus;
	private static final int NOTIFY_ME_ID = 1337;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_neat_activity);
		homeSelected();

		init();
		context = this;

	}

	private void init() {
		// TODO Auto-generated method stub
		llOrderfood = (RelativeLayout) findViewById(R.id.llOrderfood);
		llTrackMyOrder = (RelativeLayout) findViewById(R.id.llTrackMyOrder);
		llLoyaltyPoints = (RelativeLayout) findViewById(R.id.llLoyaltyPoints);
		llBookaTable = (RelativeLayout) findViewById(R.id.llBookaTable);
		llSignIn = (RelativeLayout) findViewById(R.id.llSignIn);
		txtSignIn = (TextView) findViewById(R.id.txtSignIn);
		// txtSignOut = (TextView) findViewById(R.id.txtSignOut);
		llSignOut = (RelativeLayout) findViewById(R.id.llSignOut);

		/*if (AppConstant.APPFEATUREID.equalsIgnoreCase("3")) {
			llTrackMyOrder.setVisibility(View.VISIBLE);
		}

		if (AppConstant.APPFEATUREID.equalsIgnoreCase("2") || AppConstant.APPFEATUREID.equalsIgnoreCase("3")) {

			llLoyaltyPoints.setVisibility(View.VISIBLE);
		}*/

		if (AppConstant.DIGITALLOYALTYCARD.contentEquals("") || AppConstant.ASSIGNEDFEATURES.contentEquals("")) {
			llLoyaltyPoints.setVisibility(View.VISIBLE);
		} else if (IsActive(AppConstant.DIGITALLOYALTYCARD, AppConstant.ASSIGNEDFEATURES)) {
			llLoyaltyPoints.setVisibility(View.VISIBLE);
		} else {
			llLoyaltyPoints.setVisibility(View.GONE);
		}

		if (AppConstant.TABLERESERVATION.contentEquals("") || AppConstant.ASSIGNEDFEATURES.contentEquals("")) {
			llLoyaltyPoints.setVisibility(View.VISIBLE);
		} else if (IsActive(AppConstant.TABLERESERVATION, AppConstant.ASSIGNEDFEATURES)) {
			llBookaTable.setVisibility(View.VISIBLE);
		} else {
			llBookaTable.setVisibility(View.GONE);
		}
			

		if (AppConstant.REALTIMEORDERPROCESSING.contentEquals("") || AppConstant.ASSIGNEDFEATURES.contentEquals("")) {
			llLoyaltyPoints.setVisibility(View.VISIBLE);
		} else if (IsActive(AppConstant.REALTIMEORDERPROCESSING, AppConstant.ASSIGNEDFEATURES)) {
			llTrackMyOrder.setVisibility(View.VISIBLE);
		} else {
			llTrackMyOrder.setVisibility(View.GONE);
		}
			
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
		userOrderStatus = getOrderStatus();
		userLoginId = getUserLoginID();
		OnLog();

	}

	private void deleteUserFromTable() {
		localDb.open();
		localDb.deleteUserFromTable();
		localDb.close();

	}

	private int getUserLoginID() {
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}

	private int getOrderStatus() {
		localDb.open();
		int orderStatusID = localDb.getOrderStatus();
		localDb.close();
		return orderStatusID;
	}

	public void OnSignOut(View pressed) {

		CommonValues.getInstance().LoginUser = null;
		CommonTask.savePreferences(HomePageActivity.this, CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY, null);
		CommonTask.savePreferences(HomePageActivity.this, CommonConstraints.FACEBOOKUSER, String.valueOf("0"));
		deleteUserFromTable();
		Toast.makeText(HomePageActivity.this, "Sign out successfully", Toast.LENGTH_LONG).show();
		llSignIn.setVisibility(View.VISIBLE);
		llSignOut.setVisibility(View.GONE);
	}

	public void OnOrderfood(View pressed) {

		Intent intent = new Intent(HomePageActivity.this, CategoryListActivity.class);
		/*
		 * intent.putExtra("CHECKOUTAMOUNT", "100");
		 */
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	public void OnTrackMyOrder(View pressed) {
		// if (AppConstant.APPFEATUREID.equalsIgnoreCase("3")) {
		try {
			/*
			 * prefUserPass = CommonTask.getPreferences(
			 * neat_HomeMainActivity.this,
			 * CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY);
			 */

			if (userLoginId != 0) {
				if (userOrderStatus > 0) {
					Intent intent = new Intent(HomePageActivity.this, TrackMyOrderListActivity.class);

					// * intent.putExtra("CHECKOUTAMOUNT", "100");

					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(intent);
				} else {
					Toast.makeText(HomePageActivity.this, "You have no order to track", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(HomePageActivity.this, "Please login or create an account to track your order",
						Toast.LENGTH_LONG).show();

			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(HomePageActivity.this, "Your are not logged in.please login", Toast.LENGTH_LONG).show();
		}
		/*
		 * else { Toast.makeText(HomePageActivity.this,
		 * "You are not eligible this feature ", Toast.LENGTH_SHORT).show(); }
		 */
	}

	public void OnLog() {

		if (getUserLoginID() != 0) {
			llSignIn.setVisibility(View.GONE);
			llSignOut.setVisibility(View.VISIBLE);
		} else {
			llSignIn.setVisibility(View.VISIBLE);
			llSignOut.setVisibility(View.GONE);
		}

	}

	public void OnLoyaltyPoints(View pressed) {

		/*
		 * prefUserPass = CommonTask.getPreferences(neat_HomeMainActivity.this,
		 * CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY);
		 */
		// if (AppConstant.APPFEATUREID.equalsIgnoreCase("2") ||
		// AppConstant.APPFEATUREID.equalsIgnoreCase("3")) {
		if (userLoginId != 0) {
			if (userOrderStatus > 0) {
				Intent intent = new Intent(HomePageActivity.this, LoyaltyIntroductionActivity.class);
				/*
				 * intent.putExtra("CHECKOUTAMOUNT", "100");
				 */
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			} else {
				Toast.makeText(HomePageActivity.this, "You have no order. Please make an order to get Loyalty points",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(HomePageActivity.this,
					"Please login or create an account to start collecting and redeeming your points",
					Toast.LENGTH_LONG).show();

		}
		/*
		 * else { Toast.makeText(HomePageActivity.this,
		 * "You are not eligible this feature ", Toast.LENGTH_SHORT).show(); }
		 */
	}

	public void OnBookaTable(View pressed) {
		/* if (AppConstant.APPFEATUREID.equalsIgnoreCase("3")) { */

		Intent intent = new Intent(HomePageActivity.this, TableBookingActivity.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		/*
		 * } else { Toast.makeText(HomePageActivity.this,
		 * "You are not eligible this feature ", Toast.LENGTH_SHORT).show(); }
		 */
	}

	public void OnSignIn(View pressed) {

		Intent intent = new Intent(HomePageActivity.this, NeatLoginActivity.class);
		/*
		 * intent.putExtra("CHECKOUTAMOUNT", "100");
		 */
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	/*
	 * public void onMakePayment(View pressed) {
	 * 
	 * Intent intent = new Intent(neat_HomeMainActivity.this,
	 * JobPaymentActivity.class); intent.putExtra("CHECKOUTAMOUNT", "100");
	 * 
	 * intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	 * startActivity(intent); }
	 * 
	 * public void onFeedback(View pressed) {
	 * 
	 * final Dialog dialog = new Dialog(this);
	 * dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
	 * dialog.setContentView(R.layout.job_ratings); dialog.setCancelable(false);
	 * 
	 * tvJobNo = (TextView) dialog.findViewById(R.id.tvJobNo); rbPuncuality =
	 * (RatingBar) dialog.findViewById(R.id.rbPuncuality); rbCleanliness =
	 * (RatingBar) dialog.findViewById(R.id.rbCleanliness); rbFriendlyness =
	 * (RatingBar) dialog.findViewById(R.id.rbFriendlyness); rbRoadKnowledge =
	 * (RatingBar) dialog.findViewById(R.id.rbRoadKnowledge); rbServices =
	 * (RatingBar) dialog.findViewById(R.id.rbServices);
	 * 
	 * etValueforMoney = (EditText) dialog.findViewById(R.id.etValueforMoney);
	 * etComments = (EditText) dialog.findViewById(R.id.etComments);
	 * 
	 * btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit); btnCancel =
	 * (Button) dialog.findViewById(R.id.btnCancel);
	 * 
	 * btnSubmit.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { String URL_Login = String.format(
	 * CommonURL.getInstance().JobRating, "15", String
	 * .valueOf(rbPuncuality.getRating()), String
	 * .valueOf(rbCleanliness.getRating()), String
	 * .valueOf(rbFriendlyness.getRating()), String
	 * .valueOf(rbRoadKnowledge.getRating()), String
	 * .valueOf(rbServices.getRating()), String
	 * .valueOf(etValueforMoney.getText().toString() .trim()),
	 * etComments.getText() .toString().trim(), String.valueOf(CommonValues
	 * .getInstance().LoginUser.UserReferenceID), "1"); JsonObjectRequest
	 * jsonReq = new JsonObjectRequest(Method.GET, URL_Login, null, new
	 * Response.Listener<JSONObject>() {
	 * 
	 * @Override public void onResponse(JSONObject response) { if (response !=
	 * null) { Gson gson = new Gson(); JobRatingHolder profileInformationRoot =
	 * gson .fromJson(response.toString(), JobRatingHolder.class); if
	 * (profileInformationRoot != null && profileInformationRoot.JobRatingEntity
	 * != null) {
	 * 
	 * dialog.dismiss();
	 * 
	 * } else { Toast.makeText(neat_HomeMainActivity.this, "No data found",
	 * Toast.LENGTH_LONG).show(); dialog.dismiss(); } } } }, new
	 * Response.ErrorListener() {
	 * 
	 * @Override public void onErrorResponse(VolleyError error) {
	 * Toast.makeText(neat_HomeMainActivity.this, error.getMessage(),
	 * Toast.LENGTH_LONG) .show(); dialog.dismiss(); } });
	 * 
	 * jsonReq.setRetryPolicy(new DefaultRetryPolicy(
	 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
	 * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	 * 
	 * // Adding request to volley request queue
	 * LocalListingApplication.getInstance() .addToRequestQueue(jsonReq);
	 * 
	 * }
	 * 
	 * });
	 * 
	 * btnCancel.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { dialog.dismiss(); }
	 * 
	 * });
	 * 
	 * dialog.show();
	 * 
	 * }
	 * 
	 * private void loadDataFromServer() { // making fresh volley request and
	 * getting json String URL_Login = String.format(
	 * CommonURL.getInstance().getAllProfileByUserId,
	 * CommonValues.getInstance().LoginUser.UserID); JsonObjectRequest jsonReq =
	 * new JsonObjectRequest(Method.GET, URL_Login, null, new
	 * Response.Listener<JSONObject>() {
	 * 
	 * @Override public void onResponse(JSONObject response) { if (response !=
	 * null) { Gson gson = new Gson(); ProfileInformationRoot
	 * profileInformationRoot = gson .fromJson(response.toString(),
	 * ProfileInformationRoot.class); if (profileInformationRoot != null &&
	 * profileInformationRoot.ProfileInformations != null) { if
	 * (profileInformationRoot.ProfileInformations .size() == 1) { Intent intent
	 * = new Intent( neat_HomeMainActivity.this, HomeActivity.class);
	 * intent.putExtra("PROFILE_INFORMATION", profileInformationRoot);
	 * intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	 * startActivity(intent); } } else {
	 * Toast.makeText(neat_HomeMainActivity.this, "No data found",
	 * Toast.LENGTH_LONG) .show(); } } } }, new Response.ErrorListener() {
	 * 
	 * @Override public void onErrorResponse(VolleyError error) {
	 * Toast.makeText(neat_HomeMainActivity.this, error.getMessage(),
	 * Toast.LENGTH_LONG).show(); } });
	 * 
	 * jsonReq.setRetryPolicy(new DefaultRetryPolicy(
	 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
	 * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	 * 
	 * // Adding request to volley request queue
	 * LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
	 * 
	 * }
	 */
}
