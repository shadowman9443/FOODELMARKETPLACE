package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.Customer;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YourDetailsActivity extends HeaderFooterActivity implements OnItemSelectedListener, OnClickListener {
	EditText etCustomerNo, etFirstName, etMiddleName, etLastName, etMobileNo, etEmail, etAddressLine1, etAddressLine2,
			etPostCode, etRetypePassword, etReceptEmail, etPassword, etTitle;
	String ValidUser, salutationValue;
	String pass = "";
	TextView ivquestionimage, spinnerError;
	TextView tvHaveActivationCode, tvSubtotal, tvDeliveryCharges, totalPriceAmount, subTotalAmount, tvCount,
			tvDeliveryMethod, tvDeliveryTime, tvCard, spinnerTitle;
	CheckBox chkIsEmailNotification, chkIsSmsNotification;
	boolean finalchkIsSMSPreference = false;
	boolean finalChkIsEmailNotification = false;
	Button btnSubmit, etSpinner;
	Spinner salutationSpninner;
	TextView salutext,yourdetailsdiscountcharge;
	CheckBox chkloginasaguest;
	Intent intent;
	private Random random, CustomerrandomNumber;
	private String randomNumber;
	String totalPrice, deliveryMethod, deliveryTime, card;
	String mNumber;
	String customerrandomNumber;
	Gson gson = null;
	LinearLayout llpasword;
	ProgressDialog progressDialog;
	Spinner salutation;
	String prefUserPass = "";
	final static String Tag = "DEBUG";
	private Spinner selectionSpinner;
	MyNetDatabase localDb;
	Context context;
	ArrayList<Customer> customerInfo;
	List<String> list = new ArrayList<String>();
	// SeekBar sbfamilySize;
	String regEx = "^(([w-]+.)+[w-]+|([a-zA-Z]{1}|[w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[w-]+.)+[a-zA-Z]{2,4})$";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.neat_your_details);
		checkoutSelected();
		initControls();

		

		
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (LocalListingApplication.getInstance().userTempInfo.size() <= 0) {
			LocalListingApplication.getInstance().userTempInfo.add(etFirstName.getText().toString());
			LocalListingApplication.getInstance().userTempInfo.add(etLastName.getText().toString());
			LocalListingApplication.getInstance().userTempInfo.add(etMobileNo.getText().toString());
			LocalListingApplication.getInstance().userTempInfo.add(etEmail.getText().toString());

		} else {
			LocalListingApplication.getInstance().userTempInfo.set(0, etFirstName.getText().toString());
			LocalListingApplication.getInstance().userTempInfo.set(1, etLastName.getText().toString());
			LocalListingApplication.getInstance().userTempInfo.set(2, etMobileNo.getText().toString());
			LocalListingApplication.getInstance().userTempInfo.set(3, etEmail.getText().toString());
			// LocalListingApplication.getInstance().userTempInfo.add(4, "");
			// LocalListingApplication.getInstance().userTempInfo.add(5, "");
			// LocalListingApplication.getInstance().userTempInfo.add(6, "");
			// LocalListingApplication.getInstance().userTempInfo.add(7, "");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initControls();
		checkoutSelected();
		onCheckedChange();
		context = this;
		localDb = new MyNetDatabase(context);
		if (list.size() <= 0) {
			setTitleSpinner();
		}
		if (getUserLoginID() != 0) {
			loadGuestDataFromServer();
			llpasword.setVisibility(View.GONE);
		} else {
			if (!LocalListingApplication.getInstance().userTempInfo.isEmpty()) {
				loadDataFromTempList();
			}
		}

	}

	public void loadDataFromTempList() {
		etFirstName.setText(String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(0).toString()));
		etLastName.setText(String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(1).toString()));
		etMobileNo.setText(String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(2).toString()));
		etEmail.setText(String.valueOf(LocalListingApplication.getInstance().userTempInfo.get(3).toString()));
	}

	private int getUserLoginID() {
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}

	private void initSelectionSpinner() {

		// Set SpinnerActivity as the item selected listener
		selectionSpinner.setOnItemSelectedListener(this);

	}
/*private void setDeliveryCharge(String charge){
		
		
		Double priceTotal = db.getTotalPrice();
		if( priceTotal<15)
		{
			yourdetailsdiscountcharge.setText(CommonValues.currency + CommonValues.getInstance().deliveryChargebelowwithcard);
			CommonValues.getInstance().deliveryCharge = CommonValues.getInstance().deliveryChargebelowwithcard;
		}
		else{
			CommonValues.getInstance().deliveryCharge = charge;
			yourdetailsdiscountcharge.setText(CommonValues.currency + CommonValues.getInstance().deliveryCharge);
		}
	}*/
	class clicker implements OnClickListener {
		public void onClick(View v) {
			// String s = selectionSpinner.getSelectedItem().toString();
			selectionSpinner = (Spinner) findViewById(R.id.selection_spinner);
			selectionSpinner.setVisibility(View.VISIBLE);
			initSelectionSpinner();
			// etSpinner.setVisibility(View.GONE);
		}
	}

	public void spinnerAppear(View pressed) {

		salutation.setVisibility(View.VISIBLE);

	}

	public void setTitleSpinner() {

		if (getUserLoginID() == 0) {
			list.add(" ");
		}

		list.add("Mr.");
		list.add("Mrs.");
		list.add("Miss.");
		list.add("Ms.");
		list.add("Dr.");

		// Populate the spinner using a customized ArrayAdapter that hides the
		// first (dummy) entry
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.salutation, list) {
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				View v = null;
				TextView tv = new TextView(getContext());
				// getResources().getTextArray(R.array.cities_array);
				// tv.setTextColor(R.color.application_common_text);
				// If this is the initial dummy entry, make it hidden
				if (position == 0) {
					// TextView tv = new TextView(getContext());
					// ((TextView)
					// parent.getChildAt(0)).setTextColor(Color.BLUE);
					tv.setHeight(0);
					tv.setVisibility(View.GONE);
					v = tv;
				} else {
					// Pass convertView as null to prevent reuse of special case
					// views
					v = super.getDropDownView(position, null, parent);
				}

				// Hide scroll bar because it appears sometimes unnecessarily,
				// this does not prevent scrolling
				parent.setVerticalScrollBarEnabled(false);
				return v;
			}
			/*
			 * public void setError(View v, CharSequence s) { TextView name =
			 * new TextView(getContext());
			 * 
			 * name.setError(s); }
			 */
		};

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectionSpinner.setAdapter(dataAdapter);

	}

	private void initControls() {
		context = this;
		selectionSpinner = (Spinner) findViewById(R.id.selection_spinner);
		intent = getIntent();
		totalPrice = String.valueOf(db.getTotalPrice());
		totalPrice = currencyFormatter.format(Double.parseDouble(totalPrice));
		deliveryMethod = intent.getExtras().getString("DeliveryMethod");
		deliveryTime = intent.getExtras().getString("DeliveryTime");
		card = intent.getExtras().getString("Card");
		if (totalPrice == null)
			totalPrice = String.valueOf(0.0);
		totalPriceAmount = (TextView) findViewById(R.id.totalpriceamount);
		totalPriceAmount.setText(totalPrice);
		subTotalAmount = (TextView) findViewById(R.id.subtotalamount);
		subTotalAmount.setText(totalPrice);
		tvDeliveryMethod = (TextView) findViewById(R.id.deliverymethod);
		tvDeliveryTime = (TextView) findViewById(R.id.deliverytime);
		tvCard = (TextView) findViewById(R.id.cardmethod);
		/*
		 * etSpinner = (Button) findViewById(R.id.etSpinner);
		 * etSpinner.setOnClickListener(new clicker());
		 */
		yourdetailsdiscountcharge = (TextView) findViewById(R.id.yourdetailsdiscountcharge);
		tvDeliveryMethod.setText(deliveryMethod);
		tvDeliveryTime.setText(deliveryTime);
		tvCard.setText(" " + card + " ");
		gson = new Gson();
		llpasword = (LinearLayout) findViewById(R.id.llpasword);
		chkloginasaguest = (CheckBox) findViewById(R.id.chkloginasaguest);
		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etPassword = (EditText) findViewById(R.id.etUserPassword);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etMobileNo = (EditText) findViewById(R.id.etMobileNo);
		etEmail = (EditText) findViewById(R.id.etEmail);
		spinnerError = (TextView) findViewById(R.id.spinnerError);
		ivquestionimage = (TextView) findViewById(R.id.ivquestionimage);
		// ivquestionimage.setOnClickListener(this);
		ivquestionimage.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				onQuestion();
			}

		});
		// mNumber = etMobileNo.getText().toString().trim();

		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		etPassword.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				chkloginasaguest.setChecked(false);
				if (!etPassword.getText().equals("")) {
					etPassword.setError(null);
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// pass = etPassword.getText().toString();
			}
		});
		onTextFieldChanged(etFirstName);
		onTextFieldChanged(etLastName);
		onTextFieldChanged(etMobileNo);
		onTextFieldChanged(etEmail);
		selectionSpinner.setOnItemSelectedListener(this);
		/*
		 * etPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
		 * 
		 * public void onFocusChange(View v, boolean hasFocus) {
		 * //currentlyFocusedRow= holder.position;
		 * 
		 * if (!hasFocus) { pass = etPassword.getText().toString(); // code to
		 * execute when EditText loses focus } } });
		 */
		
		prefUserPass = CommonTask.getPreferences(YourDetailsActivity.this,
				CommonConstraints.FACEBOOKUSER);
		try {
			if (prefUserPass.equals("From Facebook")) {
				etMobileNo.setEnabled(false);
				llpasword.setVisibility(View.GONE);
			}
			else
			{
				etMobileNo.setEnabled(true);
				
			}

		} catch (Exception e) { // TODO: handle exception
		}

	}

	public void onTextFieldChanged(final EditText textField) {
		textField.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!textField.getText().equals("")) {

					textField.setError(null);

				}
			}
		});
	}

	public void onCheckedChange() {
		if (chkloginasaguest.isChecked()) {

		} else {
			pass = etPassword.getText().toString();

		}
	}

	public void onQuestion() {

		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.guest_login_dialog);
		dialog.setCancelable(true);
		dialog.show();

	}

	public void OnHome(View pressed) {
		Intent intent = new Intent(YourDetailsActivity.this, HomePageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	/**
	 * This method will invoke when an entry is selected. Invoked once when
	 * Spinner is first displayed, then again for each time the user selects
	 * something
	 */
	@Override
	public void onItemSelected(AdapterView<?> spinner, View selectedView, int selectedIndex, long id) {
		// Do not want to display the toast while the activity first loaded.

		String selection = spinner.getItemAtPosition(0).toString();
		spinnerError.setError(null);
		spinnerError.setVisibility(View.GONE);
		// String message =
		// String.format(mItemSelectedMessageTemplate, selection);
		// se2a.showToast("You selected "+selection);

		if (selectedIndex == 0) {

		}
	}

	public void onNextPress(View pressed) {

		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		String title = selectionSpinner.getSelectedItem().toString();
		String firstName = etFirstName.getText().toString().trim();
		String lastName = etLastName.getText().toString().trim();
		String mobileNo = etMobileNo.getText().toString().trim();
		String email = etEmail.getText().toString().trim();
		pass = etPassword.getText().toString().trim();

		if (validation(firstName, lastName, email, mobileNo, title)) {
			// MyType.fName= MyType.valueOf(ValidUser);
			if (ValidUser == "Title1") {

				spinnerError.setVisibility(View.VISIBLE);
				spinnerError.requestFocus();
				spinnerError.setError("Please Enter Title");
				/*
				 * Toast.makeText( YourDetailsActivity.this,
				 * "Please Enter Title", Toast.LENGTH_LONG).show();
				 */
				// selectionSpinner.setError("Please enter title.");
			} else if (ValidUser == "first name") {
				etFirstName.requestFocus();
				etFirstName.setError("Please enter first name.");
			} else if (ValidUser == "last name") {

				etLastName.requestFocus();
				etLastName.setError("Please enter surname");
			} else if (ValidUser == "mobile No") {

				etMobileNo.requestFocus();
				etMobileNo.setError("Please enter mbile No.");
			} else if (ValidUser == "email") {

				etEmail.requestFocus();
				etEmail.setError("Please enter email.");
			}

		}

		else {
			if (!isValidEmail(email)) {
				etEmail.requestFocus();
				etEmail.setError("Please enter valid email address");
			} else {

				Intent intent = new Intent(YourDetailsActivity.this, OrderSendActivity.class);
				intent.putExtra("Title", title);
				intent.putExtra("FirstName", firstName);
				intent.putExtra("LastName", lastName);
				intent.putExtra("Email", email);
				intent.putExtra("Mobile", mobileNo);
				intent.putExtra("TotalPrice", totalPrice);
				intent.putExtra("DeliveryMethod", tvDeliveryMethod.getText().toString());
				intent.putExtra("DeliveryTime", tvDeliveryTime.getText().toString());
				intent.putExtra("Card", tvCard.getText().toString());
				intent.putExtra("Password", pass);
				startActivity(intent);
				// finish();
			}
		}
	}

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

	@SuppressLint("NewApi")
	boolean validation(String firstName, String lastName, String email, String mobileNo, String title) {

		if (selectionSpinner.getSelectedItem().toString().equals("Title")) {
			ValidUser = "Title";

			return true;
		} else if (firstName.isEmpty()) {
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

	@SuppressLint("NewApi")
	private void loadGuestDataFromServer() {
		localDb.open();
		customerInfo = localDb.getCustomerInfo();
		if (customerInfo.get(0).Title.isEmpty()) {
			list.add("Title");
		} else {
			list.add(String.valueOf(customerInfo.get(0).Title));
		}

		etFirstName.setText(String.valueOf(customerInfo.get(0).FirstName));
		etLastName.setText(String.valueOf(customerInfo.get(0).LastName));
		etMobileNo.setText(String.valueOf(customerInfo.get(0).MobileNo));
		etEmail.setText(String.valueOf(customerInfo.get(0).Email));
		localDb.close();
		// making fresh volley request and getting json
		/*
		 * progressDialog = new ProgressDialog(neat_your_details.this,
		 * AlertDialog.THEME_HOLO_LIGHT);
		 * progressDialog.setMessage("Processing...."); progressDialog.show();
		 * 
		 * String URL_Login = String.format(
		 * CommonURL.getInstance().GetExistingUser, userLoginInfo); // Should
		 * change here JsonObjectRequest jsonReq = new
		 * JsonObjectRequest(Method.GET, URL_Login, null, new
		 * Response.Listener<JSONObject>() {
		 * 
		 * @Override public void onResponse(JSONObject response) { if (response
		 * != null) { progressDialog.dismiss(); CustomerHolder userRoot =
		 * gson.fromJson( response.toString(), CustomerHolder.class); if
		 * (userRoot != null && userRoot.CustomerEntity != null) {
		 * etSpinner.setText(userRoot.CustomerEntity.Title); etFirstName
		 * .setText(userRoot.CustomerEntity.FirstName); etLastName
		 * .setText(userRoot.CustomerEntity.LastName); etMobileNo
		 * .setText(userRoot.CustomerEntity.MobileNo);
		 * etEmail.setText(userRoot.CustomerEntity.Email);
		 * 
		 * } else {
		 * 
		 * } } } }, new Response.ErrorListener() {
		 * 
		 * @Override public void onErrorResponse(VolleyError error) {
		 * Toast.makeText(neat_your_details.this, error.getMessage(),
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
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 */

}
