package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.digitechlabs.neat.entities.CustomerSignupHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.CommonValues;
import com.digitechlabs.neat.utils.GPSTracker;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("NewApi")
public class CreateAccountActivity extends HeaderFooterActivity implements LocationListener {
	EditText etCustomerNo, etFirstName, etMiddleName, etLastName, etMobileNo, etEmail, etAddressLine1, etAddressLine2,
			etPostCode, etRetypePassword, etReceptEmail, etPassword;
	String ValidUser;
	TextView tvHaveActivationCode;
	CheckBox chkIsEmailNotification, chkIsSmsNotification;
	boolean finalchkIsSMSPreference = false;
	boolean finalChkIsEmailNotification = false;
	Button btnSubmit;
	Spinner spPaymentPreferenceID;
	Context context;
	private Random random, CustomerrandomNumber;
	private String randomNumber;
	MyNetDatabase localDb;
	String mNumber;
	String customerrandomNumber;
	Gson gson = null;
	ProgressDialog progressDialog;
	String prefUserPass = "";
	double Currentlat, Currentlng;
	private GPSTracker gpsTracker;
	// SeekBar sbfamilySize;
	String regEx = "^(([w-]+.)+[w-]+|([a-zA-Z]{1}|[w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[w-]+.)+[a-zA-Z]{2,4})$";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_form);
		prefUserPass = AppConstant.APP_GCM_ID;
		// context = getApplicationContext();
		context = this;
		gpsTracker = new GPSTracker(context);
		Currentlat = gpsTracker.getLatitude();
		Currentlng = gpsTracker.getLongitude();
		homeSelected();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initControls();
		localDb = new MyNetDatabase(context);
		// generateRamdomNumber();
		// generateRamdomNumberCustomer();
	}

	private void generateRamdomNumber() {
		random = new Random();
		randomNumber = "";
		for (int i = 0; i < 4; i++) {
			randomNumber += String.valueOf(random.nextInt(9));
		}
	}

	private void generateRamdomNumberCustomer() {
		CustomerrandomNumber = new Random();
		customerrandomNumber = "";
		for (int i = 0; i < 4; i++) {
			customerrandomNumber += String.valueOf(CustomerrandomNumber.nextInt(9));
		}

		etCustomerNo.setText(customerrandomNumber);

	}

	private void initControls() {
		gson = new Gson();
		etFirstName = (EditText) findViewById(R.id.etFirstName);
		// etMiddleName = (EditText) findViewById(R.id.etMiddleName);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etMobileNo = (EditText) findViewById(R.id.etMobileNo);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etRetypePassword = (EditText) findViewById(R.id.etRetypePassword);
		// sbfamilySize = (SeekBar) findViewById(R.id.sbfamilySize);

		// etKeepHistory = (EditText) findViewById(R.id.etKeepHistory);
		mNumber = etMobileNo.getText().toString().trim();
		chkIsEmailNotification = (CheckBox) findViewById(R.id.chkIsEmailNotification);
		chkIsSmsNotification = (CheckBox) findViewById(R.id.chkIsSmsNotification);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		// generateRamdomNumberCustomer();

		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		if (TelephonyManager.SIM_STATE_READY == tm.getSimState()) {
			etMobileNo.setText(tm.getLine1Number());
		}
		// userCountryCode = tm.getSimCountryIso();

		// chkIsSMSPreference=(CheckBox) findViewById(R.id.chkIsSMSPreference);
		/*
		 * tvHaveActivationCode = (TextView)
		 * findViewById(R.id.tvHaveActivationCode);
		 * 
		 * tvHaveActivationCode.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { finish(); Intent intent = new
		 * Intent(UserSignupActivity.this, NeatLoginActivity.class);
		 * intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		 * intent.putExtra("IsActivatedMode", true); startActivity(intent);
		 * 
		 * } });
		 */
	}

	public void onCheckedChange(View pressed) {
		// isAgency=!isAgency;
		/*
		 * if(isAgency){ ((LinearLayout)
		 * findViewById(R.id.llUserAgency)).setVisibility(View.VISIBLE);
		 * ((CheckBox) findViewById(R.id.cbIsAgency)).setChecked(true); }else{
		 * ((LinearLayout)
		 * findViewById(R.id.llUserAgency)).setVisibility(View.GONE);
		 * ((CheckBox) findViewById(R.id.cbIsAgency)).setChecked(false);
		 * etUserAgencyName.setText(""); etUserAgencyPhoneNumber.setText(""); }
		 */
	}

	public void OnHome(View pressed) {
		Intent intent = new Intent(CreateAccountActivity.this, HomePageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	public void createUserInLocalDb(int userLogin, String title, String firstName, String lastName, String surName,
			String door, String street, String Address, String town, String postCode, String mobile, String email,
			int isOrdered) {
		localDb.open();
		localDb.createUser(userLogin, title, firstName, lastName, surName, door, street, Address, town, postCode,
				mobile, email, isOrdered);
		localDb.close();
	}

	public void onCustomerSignUp(View pressed) throws UnsupportedEncodingException {

		// String customerNo = etCustomerNo.getText().toString().trim();
		etFirstName.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
		final String firstName = etFirstName.getText().toString().trim().toUpperCase();
		etLastName.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
		// etLastName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
		final String lastName = etLastName.getText().toString().trim().toUpperCase();

		final String mobileNo = etMobileNo.getText().toString().trim();
		final String email = etEmail.getText().toString().trim();
		String password = etPassword.getText().toString().trim();
		String Retypepassword = etRetypePassword.getText().toString().trim();
		// Matcher matcherObj = Pattern.compile(regEx).matcher(email);
		if (chkIsEmailNotification.isChecked()) {
			finalChkIsEmailNotification = true;
		}

		if (chkIsSmsNotification.isChecked()) {
			finalchkIsSMSPreference = true;
		}

		if (validation(firstName, lastName, email, mobileNo, password, Retypepassword)) {
			// MyType.fName= MyType.valueOf(ValidUser);
			if (ValidUser == "first name") {
				etFirstName.requestFocus();
				etFirstName.setError("Please enter first name.");
			} else if (ValidUser == "last name") {
				etLastName.requestFocus();
				etLastName.setError("Please enter last name.");
			}

			else if (ValidUser == "email") {
				etEmail.requestFocus();
				etEmail.setError("Please enter email address.");
			} else if (ValidUser == "mobile No") {
				etMobileNo.requestFocus();
				etMobileNo.setError("Please enter mobile number.");
			} else if (ValidUser == "password") {
				etPassword.requestFocus();
				etPassword.setError("Please enter password.");
			}

			else if (ValidUser == "password again") {
				etRetypePassword.requestFocus();
				etRetypePassword.setError("Please enter password again.");
			}

		}

		else {
			if (!password.equals(Retypepassword)) {

				etRetypePassword.requestFocus();
				etRetypePassword.setError("Password doesn't match");

			} else if (isValidEmail(email) == false) {

				etEmail.requestFocus();
				etEmail.setError("Please enter valid email address");

			}

			else {
				mNumber = mobileNo;

				LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
				Location currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				progressDialog = new ProgressDialog(CreateAccountActivity.this, AlertDialog.THEME_HOLO_LIGHT);
				progressDialog.setMessage("Processing....");
				progressDialog.show();
				String URL = "";
				// public String
				// CreateCustomer=applicationBaseUrl+"UserService/CreateCustomer?companyID=%s&firstName=%s&middleName=%s&lastName=%s&mobileNo=%s&email=%s&addressLine1=%s&addressLine2=%s&postCode=%s&cityID=%s&countryID=%s&familySize=%s&paymentPreferenceID=%s&isSMSPreference=%s&receptEmail=%s&lastLatitude=%s&lastLongitude=%s&passcode=%s&isEmailPreference=%s";
				URL = String.format(CommonURL.getInstance().CreateCustomer, AppConstant.RESTAURANTID, URLEncoder.encode(firstName, CommonConstraints.EncodingCode), URLEncoder.encode(firstName, CommonConstraints.EncodingCode),URLEncoder.encode(lastName, CommonConstraints.EncodingCode) ,
						mobileNo, email, "", "", "", String.valueOf(CommonConstraints.DEFAULT_CITYID),
						String.valueOf(CommonConstraints.DEFAULT_COUNTRYID), "1", 1, finalchkIsSMSPreference, email,
						Currentlat, Currentlng, password, finalChkIsEmailNotification, prefUserPass);
				// URL=CommonURL.getInstance().GetCustomer;
				JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL, null,
						new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								if (response != null) {
									if (progressDialog.isShowing()) {
										progressDialog.dismiss();
									}
									CustomerSignupHolder userRoot = gson.fromJson(response.toString(),
											CustomerSignupHolder.class);
									if (userRoot != null && userRoot.UserLoginInfoEntity != null) {
										CommonValues.getInstance().LoginUser = null;
										/*
										 * CommonTask .savePreferences(
										 * neatCreateAccount.this,
										 * CommonConstraints.
										 * FMP_LOGIN_USERPASS_SHAREDPREF_KEY,
										 * String.valueOf("0"));
										 */
										CommonValues.getInstance().LoginUser = userRoot.UserLoginInfoEntity;
										CommonTask.savePreferences(CreateAccountActivity.this,
												CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY,
												String.valueOf(CommonValues.getInstance().LoginUser.UserLoginInfoID));
										Toast.makeText(CreateAccountActivity.this, "User created successfully",
												Toast.LENGTH_LONG).show();
										
										Intent intent = new Intent(CreateAccountActivity.this, HomePageActivity.class);
									
										createUserInLocalDb(userRoot.UserLoginInfoEntity.UserLoginInfoID, "", firstName,
												lastName, "", "", "", "", "", "", mobileNo, email, 0);
										startActivity(intent);
										finish();
									} else {
										if (progressDialog.isShowing()) {
											progressDialog.dismiss();
										}
										Toast.makeText(CreateAccountActivity.this,
												"User already exist. please try with different mobile no.",
												Toast.LENGTH_LONG).show();
										etMobileNo.requestFocus();
										etMobileNo.setError("Number already exist. Sign up with different mobile no.");
									}
								}
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
								Toast.makeText(CreateAccountActivity.this, "No Response from server", Toast.LENGTH_LONG)
										.show();
							}
						});

				jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
						DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

				// Adding request to volley request queue
				LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
			}
		}
	}

	boolean validation(String firstName, String lastName, String email, String mobileNo, String password,
			String Retypepassword) {
		Matcher matcherObj = Pattern.compile(regEx).matcher(email);
		if (firstName.isEmpty()) {
			ValidUser = "first name";

			return true;
		} else if (lastName.isEmpty()) {
			ValidUser = "last name";

			return true;
		} else if (email.isEmpty()) {

			ValidUser = "email";

			return true;
		} else if (email.matches(regEx)) {
			ValidUser = "email";

			return true;
		} else if (mobileNo.isEmpty()) {
			ValidUser = "mobile No";

			return true;
		} else if (password.isEmpty()) {
			ValidUser = "password";

			return true;
		} else if (Retypepassword.isEmpty()) {
			ValidUser = "password again";

			return true;
		} else if (matcherObj.matches()) {
			ValidUser = "password again";

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

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

}
