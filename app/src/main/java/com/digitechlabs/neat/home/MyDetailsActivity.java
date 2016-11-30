package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.Customer;
import com.digitechlabs.neat.entities.CustomerSignupHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MyDetailsActivity extends HeaderFooterActivity {

	/*
	 * TextView etCustomerNo, etFirstName, etMiddleName, etLastName, etMobileNo,
	 * etEmail, etAddressLine1, etAddressLine2, etPostCode, etRetypePassword,
	 * etReceptEmail, etPassword, etPartySize, etDateTime, etxt_fromdate,
	 * etNotes, etxt_fromtime;
	 */
	EditText myDetailsTitle, myDetailsFirstName, myDetailsSurname, myDetailsDoor, myDetailsStreet, myDetailsAddress,
			myDetailsTown, myDetailsPostCode, myDetailsMobile, myDetailsEmail;
	Gson gson = null;
	int j;
	String T, U;
	MyNetDatabase localDb;
	Context context;
	ArrayList<Customer> customerInfo;
	ProgressDialog progressDialog;
	String ValidUser;
	String prefUserPass = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_details);
		settingsSelected();

		// startService(new Intent(this, MyNetService.class));
		init();

		// GetAllStatusByOrderId(10000052);
		gson = new Gson();

	}

	public void onEdit(View pressed) {

		final String Title = myDetailsTitle.getText().toString().trim();

		final String firstName = myDetailsFirstName.getText().toString().trim();
		final String lastName = myDetailsSurname.getText().toString().trim();
		final String Door = myDetailsDoor.getText().toString().trim();
		final String street = myDetailsStreet.getText().toString().trim();
		final String address = myDetailsAddress.getText().toString().trim();
		final String Town = myDetailsTown.getText().toString().trim();
		final String PostCode = myDetailsPostCode.getText().toString().trim();
		final String Mobile = myDetailsMobile.getText().toString().trim();
		final String Email = myDetailsEmail.getText().toString().trim();

		if (validation(Title, firstName, lastName, Door, street, address, Town, PostCode, Mobile, Email)) {
			// MyType.fName= MyType.valueOf(ValidUser);
			if (ValidUser == "Title") {
				myDetailsTitle.requestFocus();
				myDetailsTitle.setError("Please enter title.");
			}
			if (ValidUser == "firstname") {
				myDetailsFirstName.requestFocus();
				myDetailsFirstName.setError("Please enter first name.");
			} else if (ValidUser == "lastname") {
				myDetailsSurname.requestFocus();
				myDetailsSurname.setError("Please enter last name.");
			} else if (ValidUser == "Door") {
				myDetailsDoor.requestFocus();
				myDetailsDoor.setError("Please enter door no.");
			} else if (ValidUser == "street") {
				myDetailsStreet.requestFocus();
				myDetailsStreet.setError("Please enter street.");
			} /*else if (ValidUser == "address") {
				myDetailsAddress.requestFocus();
				myDetailsAddress.setError("Please enter address.");
			}*/ else if (ValidUser == "Town") {
				myDetailsTown.requestFocus();
				myDetailsTown.setError("Please enter town.");
			} else if (ValidUser == "PostCode") {
				myDetailsPostCode.requestFocus();
				myDetailsPostCode.setError("Please enter post code.");
			} else if (ValidUser == "Mobile") {
				myDetailsMobile.requestFocus();
				myDetailsMobile.setError("Please enter mobile No.");
			} else if (ValidUser == "email") {
				myDetailsEmail.requestFocus();
				myDetailsEmail.setError("Please enter email.");
			}

		}

		else if (isValidEmail(Email) == false) {

			myDetailsEmail.requestFocus();
			myDetailsEmail.setError("Please enter valid email address");

		}

		else {

			progressDialog = new ProgressDialog(MyDetailsActivity.this, AlertDialog.THEME_HOLO_LIGHT);
			progressDialog.setMessage("Processing....");
			progressDialog.show();
			String URL = "";
			// public String
			// CreateCustomer=applicationBaseUrl+"UserService/CreateCustomer?companyID=%s&firstName=%s&middleName=%s&lastName=%s&mobileNo=%s&email=%s&addressLine1=%s&addressLine2=%s&postCode=%s&cityID=%s&countryID=%s&familySize=%s&paymentPreferenceID=%s&isSMSPreference=%s&receptEmail=%s&lastLatitude=%s&lastLongitude=%s&passcode=%s&isEmailPreference=%s";
			URL = String.format(CommonURL.getInstance().UpdateCustomer, AppConstant.RESTAURANTID, firstName, Title, lastName, Mobile, Email,
					Door, address, PostCode, String.valueOf(CommonConstraints.DEFAULT_CITYID),
					String.valueOf(CommonConstraints.DEFAULT_COUNTRYID), "0", 1, Town, street, 0, 0, "", true);
			// URL=CommonURL.getInstance().GetCustomer;
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL, null,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							if (response != null) {
								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
									db.open();
									db.updateUserInfoDetails(getUserLoginID(), Title, firstName, lastName, address,
											Mobile, Email, Door, PostCode, Town, street);
									Toast.makeText(MyDetailsActivity.this, "Updated Successfuuly", Toast.LENGTH_LONG)
											.show();
									db.close();
								}
								CustomerSignupHolder userRoot = gson.fromJson(response.toString(),
										CustomerSignupHolder.class);
								if (userRoot != null && userRoot.UserLoginInfoEntity != null) {

								} else {
									/*
									 * if(progressDialog.isShowing()){
									 * progressDialog.dismiss(); }
									 * Toast.makeText(MyDetailsActivity.this,
									 * "User already exist. please try with different mobile no."
									 * , Toast.LENGTH_LONG).show();
									 */
								}
							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							if (progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
							Toast.makeText(MyDetailsActivity.this, "No Response from server", Toast.LENGTH_LONG).show();
						}
					});

			jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

			// Adding request to volley request queue
			LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
		}
	}

	// et.setFocusable(false);
	// et.setClickable(true);

	private void init() {
		// TODO Auto-generated method stub
		context = this;
		myDetailsTitle = (EditText) findViewById(R.id.myDetailsTitle);

		// etLastName = (TextView) findViewById(R.id.etLastName);
		myDetailsFirstName = (EditText) findViewById(R.id.myDetailsFirstName);
		myDetailsSurname = (EditText) findViewById(R.id.myDetailsSurname);
		myDetailsDoor = (EditText) findViewById(R.id.myDetailsDoor);
		myDetailsStreet = (EditText) findViewById(R.id.myDetailsStreet);
		myDetailsAddress = (EditText) findViewById(R.id.myDetailsAddress);
		myDetailsTown = (EditText) findViewById(R.id.myDetailsTown);
		myDetailsPostCode = (EditText) findViewById(R.id.myDetailsPostCode);
		myDetailsMobile = (EditText) findViewById(R.id.myDetailsMobile);
		myDetailsEmail = (EditText) findViewById(R.id.myDetailsEmail);
		
		prefUserPass = CommonTask.getPreferences(MyDetailsActivity.this,
				CommonConstraints.FACEBOOKUSER);
		try {
			if (prefUserPass.equals("From Facebook")) {
				myDetailsMobile.setEnabled(false);
			}
			else
			{
				myDetailsMobile.setEnabled(true);
				
			}

		} catch (Exception e) { // TODO: handle exception
		}
	}

	private int getUserLoginID() {
		localDb.open();
		context = this;
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}

	boolean validation(String Title, String firstName, String lastName, String Door, String street, String address,
			String Town, String PostCode, String Mobile, String Email) {
		// Matcher matcherObj = Pattern.compile(regEx).matcher(email);
		if (Title.isEmpty()) {
			ValidUser = "Title";

			return true;
		} else if (firstName.isEmpty()) {
			ValidUser = "firstName";

			return true;
		} else if (lastName.isEmpty()) {

			ValidUser = "lastName";

			return true;
		} else if (Door.isEmpty()) {
			ValidUser = "Door";

			return true;
		} else if (street.isEmpty()) {
			ValidUser = "street";

			return true;
		} /*else if (address.isEmpty()) {
			ValidUser = "address";

			return true;
		}*/ else if (Town.isEmpty()) {
			ValidUser = "Town";

			return false;
		} else if (PostCode.isEmpty()) {
			ValidUser = "PostCode";

			return false;
		} else if (Mobile.isEmpty()) {
			ValidUser = "Mobile";

			return false;
		} else if (Email.isEmpty()) {
			ValidUser = "Email";

			return false;
		}
		/*
		 * else if (matcherObj.matches() == false) {
		 * 
		 * etEmail.requestFocus(); etEmail.setError(
		 * "Please enter valid email address");
		 * 
		 * }
		 */
		else {
			return false;
		}
	}

	private void loadUserDetails() {

		localDb.open();
		customerInfo = localDb.getCustomerInfo();
		myDetailsTitle.setText(String.valueOf(customerInfo.get(0).Title));
		//myDetailsFirstName.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
		myDetailsFirstName.setText(String.valueOf(customerInfo.get(0).FirstName));
		//myDetailsSurname.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
		myDetailsSurname.setText(String.valueOf(customerInfo.get(0).LastName));
		myDetailsDoor.setText(String.valueOf(customerInfo.get(0).DoorNumber));
		myDetailsStreet.setText(String.valueOf(customerInfo.get(0).Street));
		myDetailsAddress.setText(String.valueOf(customerInfo.get(0).AddressLine1));
		myDetailsTown.setText(String.valueOf(customerInfo.get(0).Town));
		myDetailsPostCode.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
		myDetailsPostCode.setText(String.valueOf(customerInfo.get(0).PostCode));
		myDetailsMobile.setText(String.valueOf(customerInfo.get(0).MobileNo));
		myDetailsEmail.setText(String.valueOf(customerInfo.get(0).Email));

		// making fresh volley request and getting json
		/*
		 * final ProgressDialog progressDialog = new
		 * ProgressDialog(MyDetailsActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		 * progressDialog.setMessage("Processing...."); progressDialog.show();
		 * 
		 * String URL_Login = String.format(
		 * CommonURL.getInstance().GetExistingUser, getUserLoginID()); // Should
		 * change here JsonObjectRequest jsonReq = new
		 * JsonObjectRequest(Method.GET, URL_Login, null, new
		 * Response.Listener<JSONObject>() {
		 * 
		 * @Override public void onResponse(JSONObject response) { if (response
		 * != null) {
		 * 
		 * progressDialog.dismiss(); CustomerHolder userRoot = gson.fromJson(
		 * response.toString(), CustomerHolder.class); if (userRoot != null &&
		 * userRoot.CustomerEntity != null) {
		 * myDetailsTitle.setText(String.valueOf
		 * (userRoot.CustomerEntity.Title));
		 * myDetailsFirstName.setText(String.valueOf
		 * (userRoot.CustomerEntity.FirstName));
		 * myDetailsSurname.setText(String.
		 * valueOf(userRoot.CustomerEntity.LastName));
		 * myDetailsDoor.setText(String
		 * .valueOf(userRoot.CustomerEntity.DoorNumber));
		 * myDetailsStreet.setText
		 * (String.valueOf(userRoot.CustomerEntity.Street));
		 * myDetailsAddress.setText
		 * (String.valueOf(userRoot.CustomerEntity.AddressLine1)); //
		 * etxt_fromdate.setText(booktable.UserName);
		 * myDetailsTown.setText(String.valueOf(userRoot.CustomerEntity.Town));
		 * myDetailsPostCode
		 * .setText(String.valueOf(userRoot.CustomerEntity.PostCode));
		 * myDetailsMobile
		 * .setText(String.valueOf(userRoot.CustomerEntity.MobileNo));
		 * myDetailsEmail
		 * .setText(String.valueOf(userRoot.CustomerEntity.Email)); } else {
		 * 
		 * } } } }, new Response.ErrorListener() {
		 * 
		 * @Override public void onErrorResponse(VolleyError error) {
		 * Toast.makeText(MyDetailsActivity.this, error.getMessage(),
		 * Toast.LENGTH_LONG).show(); } });
		 * 
		 * jsonReq.setRetryPolicy(new DefaultRetryPolicy(
		 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
		 * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		 * 
		 * // Adding request to volley request queue
		 * LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
		 */

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		localDb = new MyNetDatabase(context);
		loadUserDetails();
	}

	/*
	 * private void GetAllStatusByOrderId(int customerOrderId) { String URL =
	 * ""; // URL = //
	 * String.format(CommonURL.getInstance().CreateCustomer,URLEncoder
	 * .encode(userCreateData, // "UTF8")); URL =
	 * String.format(CommonURL.getInstance().GetAllStatusByOrderId,
	 * customerOrderId);
	 * 
	 * JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL, null,
	 * new Response.Listener<JSONObject>() {
	 * 
	 * @Override public void onResponse(JSONObject response) { if (response !=
	 * null) {
	 * 
	 * // GetProductCategoryByResturantID CustomerOrderCommentsHolder
	 * productroot = gson .fromJson(response.toString(),
	 * CustomerOrderCommentsHolder.class); if (productroot != null &&
	 * productroot.CustomerOrderCommentsEntityList != null) { for (int i = 0; i
	 * < productroot.CustomerOrderCommentsEntityList .size(); i++) {
	 * 
	 * j = productroot.CustomerOrderCommentsEntityList
	 * .get(i).MDCustomerOrderStatusID;
	 * 
	 * long u = CommonTask
	 * .convertJsonDateToLong(productroot.CustomerOrderCommentsEntityList
	 * .get(i).CommentsTime); T = CommonTask.convertDateToString(u);
	 * 
	 * if (j == 2) { ivCustomerimage2 .setVisibility(View.VISIBLE);
	 * textView3.setText(T); } else if (j == 3) { ivCustomerimage3
	 * .setVisibility(View.VISIBLE); textView4.setText(T);
	 * 
	 * } else if (j == 4) { ivCustomerimage2 .setVisibility(View.VISIBLE); } }
	 * 
	 * } else {
	 * 
	 * Toast.makeText( neat_Book_Table_Reservation.this, "Something went wrong",
	 * Toast.LENGTH_LONG).show(); } } } }, new Response.ErrorListener() {
	 * 
	 * @Override public void onErrorResponse(VolleyError error) {
	 * 
	 * Toast.makeText(neat_Book_Table_Reservation.this, error.getMessage(),
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } });
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

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}
}
