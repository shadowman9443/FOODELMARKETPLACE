
package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.digitechlabs.neat.entities.CustomerHolder;
import com.digitechlabs.neat.entities.CustomerSignupHolder;
import com.digitechlabs.neat.entities.UserInfoPinHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.CommonValues;
import com.digitechlabs.neat.utils.GPSTracker;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NeatLoginActivity extends HeaderFooterActivity implements OnClickListener {
	EditText etUserName, etUserPassword;
	TextView tvSignUp, tvHaveActivationCode, tvLogin, tvforgotpass;
	Gson gson = null;
	String ValidUser;
	String prefUserPass;
	Bundle savedInstanceStateGolbal;
	public static String mobileNumber = "", passWord;
	ProgressDialog progressDialog;


	Boolean IsActivatedMode = false;
	Context context;
	Button btnivNext, btnSIgnUp, btnSignout, btnFbLogin, btnFbGetProfile, btnPostToWall, btnShowAccessTokens,
			btn_logout;
	MyNetDatabase localDb;
	LinearLayout llforgotpass;

	String APP_ID = "569157216594184"; // Replace with your App ID
	// Instance of Facebook Class
	//Facebook facebook = new Facebook(APP_ID);
	//AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	SharedPreferences mPrefs;
	boolean finalchkIsSMSPreference = false;
	boolean finalChkIsEmailNotification = false;
	double Currentlat, Currentlng;
	private GPSTracker gpsTracker;
	String facebookname, facebookid;

	private CallbackManager callbackManager;
	private AccessTokenTracker accessTokenTracker;
	private ProfileTracker profileTracker;
	LoginButton loginButton;







	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		fbInit();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_neat);

		prefUserPass = AppConstant.APP_GCM_ID;


		printHashKey();
		// savedInstanceStateGolbal = savedInstanceState;
		// Play(savedInstanceState);

		// Your Facebook APP ID
		// String APP_ID = "Your APP_ID"; // Replace with your App ID
		homeSelected();
		context = this;
		gpsTracker = new GPSTracker(context);
		Currentlat = gpsTracker.getLatitude();
		Currentlng = gpsTracker.getLongitude();
		initializeControls();
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		hideSoftKeyboard();

		// Buttons
		Button btnFbLogin;
		Button btnFbGetProfile;
		Button btnPostToWall;
		Button btnShowAccessTokens;

	}

	private void Play(Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.containsKey("IsActivatedMode")) {
			String isActivatedMode = savedInstanceState.getString("IsActivatedMode");
			IsActivatedMode = Boolean.parseBoolean(isActivatedMode);
			mobileNumber = savedInstanceState.getString("MOBILENUMBER");
		}

		initializeControls();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * Hides the soft keyboard
	 */
	public void hideSoftKeyboard() {
		if (getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}

	/**
	 * Shows the soft keyboard
	 */
	public void showSoftKeyboard(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		view.requestFocus();
		inputMethodManager.showSoftInput(view, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//loginButton.registerCallback(callbackManager, callback);
		Profile profile = Profile.getCurrentProfile();
		displayMessage(profile);

		localDb = new MyNetDatabase(context);
		hideSoftKeyboard();
		/*
		 * prefUserPass = CommonTask.getPreferences( neat_LoginActivity.this,
		 * CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY);
		 */
		/*
		 * if(getUserLoginID()!=0) { btnivNext.setVisibility(View.GONE);
		 * btnSignout.setVisibility(View.VISIBLE); } else {
		 *
		 * }
		 */
		/*
		 * Session session = Session.getActiveSession(); if (session.isOpened())
		 * { Toast.makeText(this, session.getAccessToken(),
		 * Toast.LENGTH_LONG).show(); }
		 */
	}

	/*
	 * @Override public void onBackPressed() { Intent intent = new
	 * Intent(Intent.ACTION_MAIN); intent.addCategory(Intent.CATEGORY_HOME);
	 * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(intent); }
	 */
	private int getUserLoginID() {
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}

	@SuppressWarnings("deprecation")
	private void initializeControls() {
		gson = new Gson();



		context = this;
		etUserName = (EditText) findViewById(R.id.etUserName);
		etUserPassword = (EditText) findViewById(R.id.etUserPassword);
		tvforgotpass = (TextView) findViewById(R.id.tvforgotpass);
		etUserName.setText(mobileNumber);
		// tvSignUp = (TextView) findViewById(R.id.tvSignUp);
		// tvHaveActivationCode = (TextView)
		// findViewById(R.id.tvHaveActivationCode);
		// tvLogin = (TextView) findViewById(R.id.tvLogin);
		btnivNext = (Button) findViewById(R.id.btnivNext);
		btnSIgnUp = (Button) findViewById(R.id.btnSIgnUp);
		btnSignout = (Button) findViewById(R.id.btnSignout);
		llforgotpass = (LinearLayout) findViewById(R.id.llforgotpass);

		// for facebook
		btnFbLogin = (Button) findViewById(R.id.btn_fblogin);
		btnFbGetProfile = (Button) findViewById(R.id.btn_get_profile);
		btnPostToWall = (Button) findViewById(R.id.btn_fb_post_to_wall);
		btnShowAccessTokens = (Button) findViewById(R.id.btn_show_access_tokens);
		///mAsyncRunner = new AsyncFacebookRunner(facebook);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		/**
		 * Login button Click event
		 */
		btnFbLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Image Button", "button Clicked");
				loginToFacebook();
			}
		});

		/**
		 * Getting facebook Profile info
		 */
		btnFbGetProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//getProfileInformation();
			}
		});

		/**
		 * Posting to Facebook Wall
		 */
		btnPostToWall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//postToWall();
			}
		});

		/**
		 * Showing Access Tokens
		 */
		btnShowAccessTokens.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//showAccessTokens();
			}
		});

		/**
		 * Showing log out
		 */
		btn_logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//logoutFromFacebook();
			}
		});

		/*
		 * SpannableString s = new SpannableString("Sign Up"); s.setSpan(new
		 * UnderlineSpan(), 0, s.length(), 0); tvSignUp.setText(s);
		 */

		/*
		 * SpannableString str = new SpannableString("I have Activation Code");
		 * str.setSpan(new UnderlineSpan(), 0, str.length(), 0);
		 * tvHaveActivationCode.setText(str);
		 */

		/*
		 * SpannableString strLogin = new SpannableString("Login");
		 * strLogin.setSpan(new UnderlineSpan(), 0, strLogin.length(), 0);
		 * tvLogin.setText(strLogin);
		 */

		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		if (TelephonyManager.SIM_STATE_READY == tm.getSimState() && IsActivatedMode == false) {
			etUserName.setText(tm.getLine1Number());
		}

		if (mobileNumber != null && IsActivatedMode == true) {

		}

	}

	public void onCustomerSignUp(View pressed) {
		Intent intent = new Intent(NeatLoginActivity.this, CreateAccountActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	public void onForgot(View pressed) {
		String user = etUserName.getText().toString();
		if (user.equalsIgnoreCase("")) {
			etUserName.requestFocus();
			etUserName.setError("Please enter your mobile number");

		} else
			loadForgotPin(user);

	}

	public void onSignOut(View pressed) {
		CommonValues.getInstance().LoginUser = null;
		CommonValues.getInstance().customerInfo = null;
		CommonTask.savePreferences(NeatLoginActivity.this, CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY, null);
		CommonTask.savePreferences(NeatLoginActivity.this, CommonConstraints.FACEBOOKUSER, null);
		CommonTask.clear(NeatLoginActivity.this);
		localDb.open();
		localDb.deleteUserFromTable();
		localDb.close();
		Toast.makeText(NeatLoginActivity.this, "Sign out successfully", Toast.LENGTH_LONG).show();
		btnivNext.setVisibility(View.VISIBLE);
		btnSignout.setVisibility(View.GONE);


		//
		SharedPreferences mySPrefs =PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = mySPrefs.edit();
		editor.remove("facebook_user");
		editor.apply();
		mPrefs = getPreferences(MODE_PRIVATE);
		String test = mPrefs.getString("facebook_user", null);


		CommonTask .savePreferences( NeatLoginActivity.this,
				CommonConstraints. FACEBOOKUSER,
				String.valueOf("0"));
		// loadDataFromServer("ferdous@conio.com.au","Haider123");
	}

	public void onLogin(View pressed) {
		String user = etUserName.getText().toString();
		String pass = etUserPassword.getText().toString();
		/*
		 * if (user.isEmpty()) { etUserName.setError("Please enter your mobile."
		 * ); return; } if (pass.isEmpty()) { etUserPassword.setError(
		 * "Please enter your password."); return; }
		 */
		/*
		 * if(validation(user, pass)) {
		 *
		 * }
		 */
		if (!CommonTask.isOnline(this)) {
			Toast.makeText(NeatLoginActivity.this, "Your internet connection dry!!!, Please connect...", Toast.LENGTH_LONG)
					.show();
		} else {
			if (validation(user, pass)) {
				if (ValidUser == "mobile") {
					etUserName.requestFocus();
					etUserName.setError("Please enter your " + ValidUser);
				} else if (ValidUser == "password") {
					etUserPassword.requestFocus();
					etUserPassword.setError("Please enter your " + ValidUser);
				}
			} else
				loadDataFromServer(user, pass);
		}
		// loadDataFromServer("ferdous@conio.com.au","Haider123");
	}

	@SuppressLint("NewApi")
	boolean validation(String user, String pass) {
		if (user.isEmpty()) {
			ValidUser = "mobile";
			return true;
		} else if (pass.isEmpty()) {
			ValidUser = "password";

			return true;
		} else {
			return false;
		}
	}

	public void createUserInLocalDb(int userLogin, String title, String firstName, String lastName, String surName,
									String door, String street, String Address, String town, String postCode, String mobile, String email,
									int isOrdered) {
		localDb.open();
		localDb.createUser(userLogin, title, firstName, lastName, surName, door, street, Address, town, postCode,
				mobile, email, isOrdered);
		localDb.close();
	}

	private void loadDataFromServer(String user, String pass) {
		// making fresh volley request and getting json
		progressDialog = new ProgressDialog(NeatLoginActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		String URL_Login = String.format(CommonURL.getInstance().VerifyUserCredentials, user, pass,
				CommonConstraints.USER_TYPE_CUSTOMER_ID,AppConstant.RESTAURANTID);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_Login, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							CustomerHolder userRoot = gson.fromJson(response.toString(), CustomerHolder.class);
							if (userRoot != null && userRoot.CustomerEntity != null) {

								CommonValues.getInstance().customerInfo = userRoot.CustomerEntity;

								CommonTask.savePreferences(NeatLoginActivity.this,
										CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY,
										String.valueOf(CommonValues.getInstance().customerInfo.UserLoginInfoID));

								CommonTask.savePreferences(NeatLoginActivity.this,
										CommonConstraints.FACEBOOKUSER,
										String.valueOf(CommonValues.getInstance().customerInfo.GuestUserPass));


								progressDialog.dismiss();
								Toast.makeText(NeatLoginActivity.this, "You're logged in", Toast.LENGTH_LONG).show();
								createUserInLocalDb(userRoot.CustomerEntity.UserLoginInfoID,
										userRoot.CustomerEntity.Title, userRoot.CustomerEntity.FirstName,
										userRoot.CustomerEntity.LastName, userRoot.CustomerEntity.Surname,
										userRoot.CustomerEntity.DoorNumber, userRoot.CustomerEntity.Street,
										userRoot.CustomerEntity.AddressLine1, userRoot.CustomerEntity.Town,
										userRoot.CustomerEntity.PostCode, userRoot.CustomerEntity.MobileNo,
										userRoot.CustomerEntity.Email, userRoot.CustomerEntity.IsOrdered);
								finish();
								Intent intent = new Intent(NeatLoginActivity.this, CategoryListActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								startActivity(intent);
							} else {
								progressDialog.dismiss();
								etUserName.setError("Please enter your mobile number");
								etUserPassword.setError("Please enter valid password.");
								Toast.makeText(NeatLoginActivity.this, "The user id or password is incorrect!",
										Toast.LENGTH_LONG).show();
							}
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				Toast.makeText(NeatLoginActivity.this, "No response from server", Toast.LENGTH_LONG).show();
			}
		});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}

	private void loadForgotPin(String mobile) {
		// making fresh volley request and getting json
		progressDialog = new ProgressDialog(NeatLoginActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		String URL_Login = String.format(CommonURL.getInstance().ForgetPin, mobile);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_Login, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							UserInfoPinHolder userRoot = gson.fromJson(response.toString(), UserInfoPinHolder.class);
							if (userRoot != null && userRoot.UserLoginInfoEntity != null) {
								String pass = userRoot.UserLoginInfoEntity.Password;

								if (pass != null) {
									try {
										SmsManager smsManager = SmsManager.getDefault();
										smsManager.sendTextMessage(etUserName.getText().toString(), null, pass, null,
												null);
										Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG)
												.show();
										progressDialog.dismiss();
									} catch (Exception ex) {
										Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
												Toast.LENGTH_LONG).show();
										ex.printStackTrace();
										progressDialog.dismiss();
									}
								}
							} else {
								progressDialog.dismiss();
								etUserName.setError("Please enter valid user id.");

								Toast.makeText(NeatLoginActivity.this, "The user id is incorrect!", Toast.LENGTH_LONG)
										.show();
							}
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				Toast.makeText(NeatLoginActivity.this, "No response from server", Toast.LENGTH_LONG).show();
			}
		});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}

	public void CustomerSignupviafacebook(final String mobile, final String firstname) {
		/*ProgressDialog progressDialogt;
		progressDialogt = new ProgressDialog(NeatLoginActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		progressDialogt.setMessage("Loading....");
		progressDialogt.show();*/
		/*
		 * ProgressDialog progressDialogn;
		 *
		 * progressDialogn = new ProgressDialog(NeatLoginActivity.this,
		 * AlertDialog.THEME_HOLO_LIGHT);
		 * progressDialogn.setMessage("Loading...."); progressDialogn.show();
		 */
		Uri fname = Uri.parse(firstname);
		String URL = "";
		// public String
		// CreateCustomer=applicationBaseUrl+"UserService/CreateCustomer?companyID=%s&firstName=%s&middleName=%s&lastName=%s&mobileNo=%s&email=%s&addressLine1=%s&addressLine2=%s&postCode=%s&cityID=%s&countryID=%s&familySize=%s&paymentPreferenceID=%s&isSMSPreference=%s&receptEmail=%s&lastLatitude=%s&lastLongitude=%s&passcode=%s&isEmailPreference=%s";
		try {
			URL = String.format(CommonURL.getInstance().CreateCustomerviafacebook, AppConstant.RESTAURANTID,
					URLEncoder.encode(firstname, CommonConstraints.EncodingCode), "", "", mobile, "", "", "", "",
					String.valueOf(CommonConstraints.DEFAULT_CITYID),
					String.valueOf(CommonConstraints.DEFAULT_COUNTRYID), "1", 1, finalchkIsSMSPreference, "",
					Currentlat, Currentlng, "1234", finalChkIsEmailNotification, prefUserPass);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// URL=CommonURL.getInstance().GetCustomer;
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				if (response != null) {
					/*
					 * if (progressDialog.isShowing()) {
					 * progressDialog.dismiss(); }
					 */
					CustomerSignupHolder userRoot = gson.fromJson(response.toString(), CustomerSignupHolder.class);
					if (userRoot != null && userRoot.UserLoginInfoEntity != null) {
						CommonValues.getInstance().LoginUser = null;
						/*
						 * CommonTask .savePreferences( neatCreateAccount.this,
						 * CommonConstraints. FMP_LOGIN_USERPASS_SHAREDPREF_KEY,
						 * String.valueOf("0"));
						 */
						CommonValues.getInstance().LoginUser = userRoot.UserLoginInfoEntity;
						CommonTask.savePreferences(NeatLoginActivity.this, CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY,
								String.valueOf(CommonValues.getInstance().LoginUser.UserLoginInfoID));
						CommonTask.savePreferences(NeatLoginActivity.this,
								CommonConstraints.FACEBOOKUSER,
								String.valueOf("From Facebook"));
						Toast.makeText(NeatLoginActivity.this, "Facebook sign in successfully", Toast.LENGTH_LONG).show();

						Intent intent = new Intent(NeatLoginActivity.this, HomePageActivity.class);
						/*
						 * intent.setFlags(Intent.
						 * FLAG_ACTIVITY_REORDER_TO_FRONT);
						 * intent.putExtra("IsActivatedMode", "true");
						 * intent.putExtra("MOBILENUMBER", mNumber);
						 */
						createUserInLocalDb(userRoot.UserLoginInfoEntity.UserLoginInfoID, "", firstname, "", "", "", "",
								"", "", "", mobile, "", 0);
						startActivity(intent);
						finish();
					} else {
						/*
						 * if (progressDialog.isShowing()) {
						 * progressDialog.dismiss(); }
						 */
						/*
						 * Toast.makeText(CreateAccountActivity.this,
						 * "User already exist. please try with different mobile no."
						 * , Toast.LENGTH_LONG).show();
						 * etMobileNo.requestFocus(); etMobileNo.setError(
						 * "Number already exist. Sign up with different mobile no."
						 * );
						 */
					}
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				/*
				 * if (progressDialog.isShowing()) { progressDialog.dismiss(); }
				 */
				Toast.makeText(NeatLoginActivity.this, "No Response from server", Toast.LENGTH_LONG).show();
			}
		});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}

	/**
	 * Function to login into facebook
	 */
	@SuppressWarnings("deprecation")
	public void loginToFacebook() {
		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		String facebookidnew = mPrefs.getString("facebookid", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			loadDataFromServer(facebookidnew, "1234");
		}else {
			loginButton= (LoginButton)findViewById(R.id.login_button);
			loginButton.setReadPermissions("user_friends");
			loginButton.setReadPermissions("user_mobile_phone");
			loginButton.setReadPermissions("user_address");
			loginButton.performClick();
			loginButton.setPressed(true);
			loginButton.registerCallback(callbackManager, callback);
		}
	}



	/***************New Facebook ***********************/

	private void fbInit(){
		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		accessTokenTracker = new AccessTokenTracker() {
			@Override
			protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

			}
		};
		profileTracker = new ProfileTracker() {
			@Override
			protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
				displayMessage(newProfile);
			}
		};
		accessTokenTracker.startTracking();
		profileTracker.startTracking();


	}

	private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
		@Override
		public void onSuccess(LoginResult loginResult) {
			AccessToken accessToken = loginResult.getAccessToken();
			// accessToken = AccessToken.getCurrentAccessToken().getToken();


			Log.i("Access Tocken are ", "" + AccessToken.getCurrentAccessToken().getToken());
			SharedPreferences.Editor editor = mPrefs.edit();
			editor.putString("access_token", AccessToken.getCurrentAccessToken().getToken());
			editor.commit();
			Profile profile = Profile.getCurrentProfile();
			//Log.i("Name are ", "" + profile.getName());

			// App code
			GraphRequest request = GraphRequest.newMeRequest(
					loginResult.getAccessToken(),
					new GraphRequest.GraphJSONObjectCallback() {
						@Override
						public void onCompleted(
								JSONObject object,
								GraphResponse response) {

							Log.i("response: ", response + "");
							try {
								/*user = new User();
								user.facebookID = object.getString("id").toString();
								user.email = object.getString("email").toString();
								user.name = object.getString("name").toString();
								user.gender = object.getString("gender").toString();
								PrefUtils.setCurrentUser(user,LoginActivity.this);*/
								SharedPreferences.Editor editor = mPrefs.edit();
								editor.putString("facebookid", object.getString("id").toString());
								editor.commit();
								CustomerSignupviafacebook(object.getString("id").toString(), object.getString("name").toString());
								Toast.makeText(NeatLoginActivity.this,"Welcome "+object.getString("name").toString(), Toast.LENGTH_LONG).show();

							}catch (Exception e){
								e.printStackTrace();
							}



						}

					});
			Bundle parameters = new Bundle();
			parameters.putString("fields", "id,name,email,gender, birthday");
			request.setParameters(parameters);
			request.executeAsync();



			displayMessage(profile);
		}

		@Override
		public void onCancel() {

		}

		@Override
		public void onError(FacebookException e) {

		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);

	}

	private void displayMessage(Profile profile) {
		if (profile != null) {
			Log.i("NAME are ",profile.getName());
			//textView.setText(profile.getName()+"Id are \n"+profile.getId()+"Id are \n"+profile.getProfilePictureUri(20,20));
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		accessTokenTracker.stopTracking();
		profileTracker.stopTracking();
	}


	public void printHashKey(){
		// Add code to print out the key hash
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.dgtech.neat",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (PackageManager.NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}
}