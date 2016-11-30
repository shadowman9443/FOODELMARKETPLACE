package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.digitechlabs.neat.entities.Customer;
import com.digitechlabs.neat.entities.CustomerHolder;
import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.CommonValues;
import com.digitechlabs.neat.utils.GPSTracker;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

@SuppressLint("NewApi")
public class OrderSendActivity extends HeaderFooterActivity implements LocationListener {
	EditText etCustomerNo, etFirstName, etMiddleName, etLastName, etMobileNo, etEmail, etAddressLine1, etAddressLine2,
			etRetypePassword, etReceptEmail, etPassword, etTitle, ethouse, etStreet, etAddress, etTown;
	Context context;
	String ValidUser;
	TextView tvHaveActivationCode, tvSubtotal, tvDeliveryCharges, totalPriceAmount, subTotalAmount, tvCount,
			tvDeliveryMethod, tvDeliveryTime, tvCard;
	CheckBox chkIsEmailNotification, chkIsSmsNotification;
	boolean finalchkIsSMSPreference = false;
	boolean finalChkIsEmailNotification = false;
	Button btnSubmit;
	Spinner spPaymentPreferenceID;
	String firstName, lastName, phone, email, totalPrice, deliveryMethod, deliveryTime, card, title, password;
	private Random random, CustomerrandomNumber;
	private String randomNumber;
	ArrayList<CustomerShoppingCart> shoppingCart;
	Intent intent;
	String mNumber;
	String customerrandomNumber;
	Gson gson = null, gsonUser = null;
	public String[][] UserInfo;
	public String[][] FavoriteItem;
	String product, favProduct;
	ProgressDialog progressDialog;
	MyNetDatabase localDb;
	ArrayList<Customer> customerInfo;
	String house, street, address, town, postCode;
	double Currentlat, Currentlng;
	private GPSTracker gpsTracker;
	String prefUserPass = "";
	Boolean isActivityloaded;
	// For Paypal
	// private static final String TAG = "paymentdemoblog";
	/**
	 * - Set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
	 * 
	 * - Set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
	 * from https://developer.paypal.com
	 * 
	 * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
	 * without communicating to PayPal's servers.
	 */
	// private static final String CONFIG_ENVIRONMENT =
	// PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

	// note that these credentials will differ between live & sandbox
	// environments.
	private static final String CONFIG_CLIENT_ID = "AQ2edOil3jl60QQ7RZTxRiKPUyhxX6qUQfGtzK2jfiq6LDwm5I61SZlo2ZLY7TU12FLkST7vLvndb2uA";

	private static final int REQUEST_CODE_PAYMENT = 1;
	private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

	private static PayPalConfiguration config = new PayPalConfiguration().environment(CONFIG_ENVIRONMENT)
			.clientId(CONFIG_CLIENT_ID)
			// The following are only used in PayPalFuturePaymentActivity.
			.merchantName("Hipster Store").merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
			.merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

	PayPalPayment thingToBuy;

	// Initialize variables

	AutoCompleteTextView etPostCode = null;
	private ArrayAdapter<String> adapter;
	// These values show in autocomplete
	String item[] = { "January", "February", "Friday", "Fucking day", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };
	public String[] xCoords;
	String[] strings;
	String pc,o,finalpostcode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.neat_your_details_send);

		// textView.addTextChangedListener(watch);
		checkoutSelected();
		context = getApplicationContext();
		gpsTracker = new GPSTracker(context);
		Currentlat = gpsTracker.getLatitude();
		Currentlng = gpsTracker.getLongitude();
		prefUserPass = AppConstant.APP_GCM_ID;
		this.isActivityloaded = true;
		//this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	
	
	public void onBuyPressed(String totalPrice) {

		if (totalPrice.contains("$") || totalPrice.contains(getResources().getString(R.string.pound))) {
			totalPrice = totalPrice.substring(1, totalPrice.length() - 1);
		}
		thingToBuy = new PayPalPayment(new BigDecimal(totalPrice), "USD", "Food Item",
				PayPalPayment.PAYMENT_INTENT_SALE);
		Intent intent = new Intent(OrderSendActivity.this, PaymentActivity.class);

		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

		startActivityForResult(intent, REQUEST_CODE_PAYMENT);
	}

	@Override
	protected void onPause() {
		super.onPause();

		LocalListingApplication.getInstance().userTempInfo.set(4, ethouse.getText().toString());
		LocalListingApplication.getInstance().userTempInfo.set(4, ethouse.getText().toString());
		LocalListingApplication.getInstance().userTempInfo.set(5, etStreet.getText().toString());
		LocalListingApplication.getInstance().userTempInfo.set(6, etAddress.getText().toString());
		LocalListingApplication.getInstance().userTempInfo.set(7, etTown.getText().toString());
		LocalListingApplication.getInstance().userTempInfo.set(8, etPostCode.getText().toString());
	}

	public void loadDataFromTempList() {
		String CheckValue = String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(4).toString());
		if (!CheckValue.equalsIgnoreCase("")) {

			ethouse.setText(String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(4).toString()));
			etStreet.setText(String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(5).toString()));
			etAddress.setText(String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(6).toString()));
			etTown.setText(String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(7).toString()));
			etPostCode.setText(String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(8).toString()));
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}

	@Override
	protected void onResume() {
		super.onResume();
		initControls();
		checkoutSelected();
		
		if (LocalListingApplication.getInstance().userTempInfo.size() > 4) {
			loadDataFromTempList();
		} else

		LocalListingApplication.getInstance().userTempInfo.add(ethouse.getText().toString());
		LocalListingApplication.getInstance().userTempInfo.add(etStreet.getText().toString());
		LocalListingApplication.getInstance().userTempInfo.add(etAddress.getText().toString());
		LocalListingApplication.getInstance().userTempInfo.add(etTown.getText().toString());
		LocalListingApplication.getInstance().userTempInfo.add(etPostCode.getText().toString());
		etPostCode.clearFocus();
		etPostCode.setOnFocusChangeListener(new OnFocusChangeListener() {          
	        public void onFocusChange(View v, boolean hasFocus) {
	            if(hasFocus) {
	            	//onTextFieldChanged(etPostCode);
	            	etPostCode.addTextChangedListener(watch);
	            }
	            else{
	            	etPostCode.removeTextChangedListener(watch);
	            }
	        }
	    });
	}

	TextWatcher watch = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int a, int b, int c) {
			// TODO Auto-generated method stub
			pc = etPostCode.getText().toString();
			o = pc.replaceAll("\\s+", ""); 
			if (o.length() == 6) {
				finalpostcode = etPostCode.getText().toString();
				loadpostcodeFromServer(o);
			}
		}
	};


	private void initControls() {
		gson = new Gson();
		intent = getIntent();
		totalPrice = String.valueOf(db.getTotalPrice());
		totalPrice = currencyFormatter.format(Double.parseDouble(totalPrice));
		deliveryMethod = intent.getExtras().getString("DeliveryMethod");

		deliveryTime = intent.getExtras().getString("DeliveryTime");
		card = intent.getExtras().getString("Card").trim();
		phone = intent.getExtras().getString("Mobile");
		email = intent.getExtras().getString("Email");
		lastName = intent.getExtras().getString("LastName");
		firstName = intent.getExtras().getString("FirstName");
		title = intent.getExtras().getString("Title");
		password = intent.getExtras().getString("Password");

		ethouse = (EditText) findViewById(R.id.ethouse);
		etStreet = (EditText) findViewById(R.id.etStreet);
		etAddress = (EditText) findViewById(R.id.etAddress);
		etTown = (EditText) findViewById(R.id.etTown);
		// etPostCode = (EditText) findViewById(R.id.Months);
		totalPriceAmount = (TextView) findViewById(R.id.totalpriceamount);
		totalPriceAmount.setText(totalPrice);
		subTotalAmount = (TextView) findViewById(R.id.subtotalamount);
		subTotalAmount.setText(totalPrice);
		tvDeliveryMethod = (TextView) findViewById(R.id.deliverymethod);
		tvDeliveryTime = (TextView) findViewById(R.id.deliverytime);
		tvCard = (TextView) findViewById(R.id.cardmethod);
		tvDeliveryMethod.setText(deliveryMethod);
		tvDeliveryTime.setText(deliveryTime);
		tvCard.setText(" " + card + " ");
		// mNumber = etMobileNo.getText().toString().trim();
		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		etPostCode = (AutoCompleteTextView) findViewById(R.id.etPostCode);
		//onTextFieldChanged(ethouse);
		//onTextFieldChanged(etStreet);
		
		//etPostCode.setOnFocusChangeListener(l);
		
		// onTextFieldChanged(textView);
		loadUserData();
	}
	private void AutoCompleteTextForPostCode() {
		// TODO Auto-generated method stub

		// Create adapter
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, xCoords);

		etPostCode.setThreshold(1);

		// Set adapter to AutoCompleteTextView
		etPostCode.setAdapter(adapter);
		etPostCode.setOnItemSelectedListener(this);
		etPostCode.setOnItemClickListener(this);

		etPostCode.showDropDown();
	}

	public void onTextFieldChanged(final EditText textField) {
		textField.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				
					
					
				
				
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {

				
				//if (s.length() == 6) {
					
			//	}
			}
		});
	}
	private void loadpostcodeFromServer(String postcode) {
		// making fresh volley request and getting json
		/*
		 * progressDialog = new ProgressDialog(OrderSendActivity.this,
		 * AlertDialog.THEME_HOLO_LIGHT);
		 * progressDialog.setMessage("Loading...."); progressDialog.show();
		 */
		// http://82.165.197.66:3000/foodelapi/postcodeinfo?postcode=DA74QD
		// postcode=DA74QD public String
		// VerifyUserCredentials=applicationBaseUrl+"Userservice/VerifyUserCredentialsCustomer?userID=%s&pin=%s&userTypeID=%s";
		String URL_Login = "http://82.165.197.66:3000/foodelapi/postcodeinfo?postcode=%s";

		String URL_Loginfx = String.format(CommonURL.getInstance().PostCode, postcode);
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_Loginfx, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
                           finalpostcode = o +" ";
							JSONArray data;
							try {
								data = response.getJSONArray("data");
								xCoords = new String[data.length()];
								;

								for (int i = 0; i < data.length(); i++) {
									String s = data.getString(i);

									strings = new String[data.length()];
									xCoords[i] = data.getString(i);

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// final JSONArray top_list =
							// data.getJSONArray("orderdetails");
							AutoCompleteTextForPostCode();
							/*
							 * String s = response.toString(); String[] parts =
							 * s.split(","); // escape . String part1 =
							 * parts[0]; String part2 = parts[1]; String part3 =
							 * parts[2];
							 * 
							 * Intent intent = new
							 * Intent(OrderSendActivity.this,
							 * CategoryListActivity.class);
							 * intent.setFlags(Intent.
							 * FLAG_ACTIVITY_REORDER_TO_FRONT);
							 * startActivity(intent);
							 */
						} else {
							/*
							 * progressDialog.dismiss();
							 * 
							 * Toast.makeText(OrderSendActivity.this,
							 * "The user id or password is incorrect!",
							 * Toast.LENGTH_LONG).show();
							 */
						}
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						/*
						 * if (progressDialog.isShowing()) {
						 * progressDialog.dismiss(); }
						 */
						//Toast.makeText(OrderSendActivity.this, "No response from server", Toast.LENGTH_LONG).show();
					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		// Show Alert
		/*
		 * Toast.makeText(getBaseContext(), "Position:" + arg2 + " Month:" +
		 * arg0.getItemAtPosition(arg2), Toast.LENGTH_LONG).show();
		 * 
		 * Log.d("AutocompleteContacts", "Position:" + arg2 + " Month:" +
		 * arg0.getItemAtPosition(arg2));
		 */

		String s = arg0.getItemAtPosition(arg2).toString();
		String[] parts = s.split(","); // escape .
		String part1 = parts[0];
		String part2 = parts[1];
		String part3 = parts[2];
		
		part1 = part1.substring(1, part1.length());
		part2 = part2.substring(2, part2.length());
		part3 = part3.substring(2, part3.length());
		
		ethouse.setText(part1);
		//etStreet.setText(part2 + ", " + part3);
		etStreet.setText(part2);

		String p = etPostCode.getText().toString();
		
		etTown.setText(part3);
		etPostCode.dismissDropDown();
		adapter = null;
		etPostCode.setAdapter(null);
		etPostCode.clearFocus();
		etPostCode.removeTextChangedListener(watch);
		etPostCode.setText(finalpostcode);
		//adapter.re
		/// textView.setDropDownHeight(0);
	}
	private void loadUserData() {
		db.open();
		customerInfo = db.getCustomerInfo();
		if (customerInfo.size() > 0) {
			ethouse.setText(String.valueOf(customerInfo.get(0).DoorNumber));
			etStreet.setText(String.valueOf(customerInfo.get(0).Street));
			etAddress.setText(String.valueOf(customerInfo.get(0).Address));
			etTown.setText(String.valueOf(customerInfo.get(0).Town));
			etPostCode.setText(String.valueOf(customerInfo.get(0).PostCode));
			//etPostCode.removeTextChangedListener(watch);
		}
		db.close();
	}

	public void getJsonObject() {

		MyNetDatabase myNetDatabase = new MyNetDatabase(this);
		myNetDatabase.open();
		int tableIndex = 0, favIndex = 0;
		ArrayList<CustomerShoppingCart> phoneList = myNetDatabase.getAddToCart();

		if (phoneList != null) {
			UserInfo = new String[phoneList.size()][10];
			FavoriteItem = new String[myNetDatabase.getNumberOfFavItem()][4];
			for (int rowindex = 0; rowindex < phoneList.size(); rowindex++) {
				UserInfo[rowindex][0] = String.valueOf(phoneList.get(tableIndex).CustomerShoppingCartID);
				UserInfo[rowindex][1] = String.valueOf(phoneList.get(tableIndex).UserLoginInfoID);
				UserInfo[rowindex][2] = String.valueOf(phoneList.get(tableIndex).ProductID);
				UserInfo[rowindex][3] = String.valueOf(phoneList.get(tableIndex).Cost);
				UserInfo[rowindex][4] = String.valueOf(phoneList.get(tableIndex).Qty);
				UserInfo[rowindex][5] = String.valueOf(phoneList.get(tableIndex).SpicyLevelID);
				UserInfo[rowindex][6] = String.valueOf(phoneList.get(tableIndex).TotalCost);
				UserInfo[rowindex][7] = String.valueOf(phoneList.get(tableIndex).RestaurantId);
				UserInfo[rowindex][8] = String.valueOf(phoneList.get(tableIndex).SpecialNotes);
				UserInfo[rowindex][9] = String.valueOf(phoneList.get(tableIndex).IsFavorite);

				tableIndex++;
			}
			myNetDatabase.close();
		}
		gsonUser = new Gson();
		product = gsonUser.toJson(UserInfo);

	}

	boolean validation(String house, String street, String postCode) {
		// Matcher matcherObj = Pattern.compile(regEx).matcher(email);
		if (house.isEmpty()) {
			ValidUser = "house";

			return true;
		} else if (street.isEmpty()) {
			ValidUser = "street";

			return true;
		} else if (postCode.isEmpty()) {

			ValidUser = "postCode";

			return true;
		}

		else {
			return false;
		}
	}

	public int getUserLoginID() {
		db.open();
		int uID = db.getUserID();
		db.close();
		return uID;
	}

	public int getOrderStatus() {
		db.open();
		int orderStatusID = db.getOrderStatus();
		db.close();
		return orderStatusID;
	}

	public void updateUserInfo(int loginID, String Title, String Door, String Street, String Address, String Town,
			String PostCode, int isOrdered) {
		db.open();
		db.updateUserInfo(loginID, Title, Door, Street, Address, Town, PostCode, isOrdered);
		db.close();
	}

	public void createUserInLocalDb(int userLoginID, String title, String firstName, String lastName, String surName,
			String door, String street, String Address, String town, String postCode, String mobile, String email,
			int isOrdered) {
		db.open();
		db.createUser(userLoginID, title, firstName, lastName, surName, door, street, Address, town, postCode, mobile,
				email, isOrdered);
		db.close();
	}

	public void onNextPress(View pressed) {
		house = ethouse.getText().toString();
		street = etStreet.getText().toString();
		address = etAddress.getText().toString();
		town = etTown.getText().toString();
		postCode = etPostCode.getText().toString();
		getJsonObject();
		deliveryMethod = deliveryMethod.replaceAll("[@]", "");

		if (validation(house, street, postCode)) {
			// MyType.fName= MyType.valueOf(ValidUser);
			if (ValidUser == "house") {
				ethouse.requestFocus();
				ethouse.setError("Please enter door no.");
			} else if (ValidUser == "street") {
				etStreet.requestFocus();
				etStreet.setError("Please enter street");
			} else if (ValidUser == "postCode") {
				etPostCode.requestFocus();
				etPostCode.setError("Please enter postcode");
			}
		}

		else {
			totalPrice = totalPrice.replace(getResources().getString(R.string.pound), "");
			progressDialog = new ProgressDialog(OrderSendActivity.this, AlertDialog.THEME_HOLO_LIGHT);

			if (card.toString().equals("Cash")) {
				submitOrder();
			} else if (card.toString().equals("Card")) {
				// for card
				/*
				 * progressDialog.setMessage("Processing Paypal....");
				 * progressDialog.show(); Intent intent = new Intent(this,
				 * PayPalService.class);
				 * intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
				 * config); startService(intent); onBuyPressed(totalPrice);
				 */

				Intent intent = new Intent(this, PayPalService.class);
				intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
				startService(intent);
				onBuyPressed(totalPrice);
			}

		}
		// finish();
	}

	public void submitOrder() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("companyid", AppConstant.RESTAURANTID);
			obj.put("product", product);
			obj.put("firstname", firstName);
			obj.put("lastname", lastName);
			obj.put("mobileno", phone);
			obj.put("email", email);
			obj.put("title", "");
			obj.put("door", house);
			obj.put("street", street);
			obj.put("address", address);
			obj.put("town", town);
			obj.put("postcode", postCode);
			obj.put("deliverymethod", deliveryMethod);
			obj.put("deliverytime", deliveryTime);
			obj.put("paymentmethod", card);
			obj.put("password", password);
			obj.put("orderlat", Currentlat);
			obj.put("orderlang", Currentlng);
			obj.put("gcmid", prefUserPass);
			obj.put("offerid",String.valueOf(CommonValues.getInstance().acceptedOffer));
			obj.put("deliverycharge",CommonValues.getInstance().deliveryCharge);
			
			if (totalPrice.contains("$") || totalPrice.contains(getResources().getString(R.string.pound))) {
			    totalPrice = totalPrice.substring(1, totalPrice.length() - 1);
			    obj.put("totalprice", totalPrice);
			}
			else
			{
				obj.put("totalprice", totalPrice);
			}
			if(CommonValues.getInstance().redeemAmounts!=null){
				obj.put("reedemamount", CommonValues.getInstance().redeemAmounts);
			}else {
				obj.put("reedemamount", "0");
			}
			obj.put("additonalcomments", CommonValues.getInstance().additoanalinfo);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		progressDialog.setMessage("Submitting your order....");
		progressDialog.show();
		String URL = "";
		URL = String.format(CommonURL.getInstance().OrderSend);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.POST, URL, obj, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				if (response != null) {
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
					}

					CustomerHolder userRoot = gson.fromJson(response.toString(), CustomerHolder.class);

					if (userRoot != null && userRoot.CustomerEntity != null) {
						CommonValues.getInstance().deliveryCharge = "0.0";
						if(CommonValues.getInstance().acceptedOffer!=0){
							
							CommonValues.getInstance().acceptedOffer=0;
						}
						if (getUserLoginID() == 0) {
							if (!password.isEmpty()) {

								createUserInLocalDb(userRoot.CustomerEntity.UserLoginInfoID, title, firstName, lastName,
										lastName, house, street, address, town, postCode, phone, email, 1);
							}
						} else {
							updateUserInfo(getUserLoginID(), title, house, street, address, town, postCode, 1);
						}
						Intent modify_intent = new Intent(OrderSendActivity.this, OrderConfirmationActivity.class);
						modify_intent.putExtra("DeliveryMethod", deliveryMethod);
						modify_intent.putExtra("DeliveryTime", deliveryTime);
						modify_intent.putExtra("PaymentType", card);
						modify_intent.putExtra("FirstName", firstName);
						modify_intent.putExtra("LastName", lastName);
						modify_intent.putExtra("Phone", phone);
						if(CommonValues.getInstance().afterloyaltyPoints!=null)
						{
							modify_intent.putExtra("TotalPrice", CommonValues.getInstance().afterloyaltyPoints);
						}
						else 
						{
							modify_intent.putExtra("TotalPrice",totalPrice);
						}
						
						modify_intent.putExtra("Email", email);

						modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						startActivity(modify_intent);
						localDb = new MyNetDatabase(getBaseContext());
						localDb.open();
						localDb.deleteCart();
						localDb.close();
						CommonValues.getInstance().redeemAmounts="0";
						CommonValues.getInstance().afterloyaltyPoints="";
						CommonValues.getInstance().additoanalinfo="";
					}
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		jsonReq.setRetryPolicy(new DefaultRetryPolicy(20000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
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
		Intent intent = new Intent(OrderSendActivity.this, HomePageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	public void onCustomerSignUp(View pressed) {

		String house = ethouse.getText().toString().trim();
		String Street = etStreet.getText().toString().trim();

		String Address = etAddress.getText().toString().trim();
		String Town = etTown.getText().toString().trim();
		String PostCode = etPostCode.getText().toString().trim();

		if (validation(house, Street, PostCode)) {
			// MyType.fName= MyType.valueOf(ValidUser);
			if (ValidUser == "house") {
				ethouse.requestFocus();
				ethouse.setError("Please enter first name.");
			} else if (ValidUser == "last name") {
				etLastName.requestFocus();
				etLastName.setError("Please enter last name.");
			}

			else if (ValidUser == "email") {
				etEmail.requestFocus();
				etEmail.setError("Please enter email.");
			} else if (ValidUser == "mobile No") {
				etMobileNo.requestFocus();
				etMobileNo.setError("Please enter mbile No.");
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
			Intent intent = new Intent(OrderSendActivity.this, HomePageActivity.class);

			startActivity(intent);
			finish();
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

	private void sendAuthorizationToServer(PayPalAuthorization authorization) {

	}

	public void onFuturePaymentPressed(View pressed) {
		Intent intent = new Intent(OrderSendActivity.this, PayPalFuturePaymentActivity.class);

		startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						System.out.println(confirm.toJSONObject().toString(4));
						System.out.println(confirm.getPayment().toJSONObject().toString(4));

						Toast.makeText(getApplicationContext(), "PaymentConfirmation info received from PayPal",
								Toast.LENGTH_LONG).show();
						submitOrder();

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				System.out.println("The user canceled.");
			} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				System.out.println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
			}
		} else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PayPalAuthorization auth = data
						.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
				if (auth != null) {
					try {
						Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

						String authorization_code = auth.getAuthorizationCode();
						Log.i("FuturePaymentExample", authorization_code);

						sendAuthorizationToServer(auth);
						Toast.makeText(getApplicationContext(), "Future Payment code received from PayPal",
								Toast.LENGTH_LONG).show();

					} catch (JSONException e) {
						Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.i("FuturePaymentExample", "The user canceled.");
			} else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
				Log.i("FuturePaymentExample",
						"Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
			}
		}
	}

	public void onFuturePaymentPurchasePressed(View pressed) {
		// Get the Application Correlation ID from the SDK
		String correlationId = PayPalConfiguration.getApplicationCorrelationId(this);

		Log.i("FuturePaymentExample", "Application Correlation ID: " + correlationId);

		// TODO: Send correlationId and transaction details to your server for
		// processing with
		// PayPal...
		Toast.makeText(getApplicationContext(), "App Correlation ID received from SDK", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() {
		// Stop service when done
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		// Log.d("AutocompleteContacts", "onItemSelected() position " +
		// position);
		// String p = textView.getText().toString();
		// textView.setText(pc);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

	}



	
}
