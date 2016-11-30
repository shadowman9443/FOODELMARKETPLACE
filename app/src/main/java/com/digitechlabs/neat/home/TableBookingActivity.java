package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.BookATableHolder;
import com.digitechlabs.neat.entities.Customer;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.RangeTimePickerDialog;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@SuppressLint("NewApi")
public class TableBookingActivity extends HeaderFooterActivity implements LocationListener {
	EditText etCustomerNo, etFirstName, etMiddleName, etLastName, etMobileNo, etEmail, etAddressLine1, etAddressLine2,
			etPostCode, etRetypePassword, etReceptEmail, etPassword, etPartySize, etDateTime, etNotes, etSurName;
	String ValidUser, Date, time, dateTime;
	TextView tvHaveActivationCode, txtTime, textViewTime;
	CheckBox chkIsEmailNotification, chkIsSmsNotification;
	boolean finalchkIsSMSPreference = false;
	boolean finalChkIsEmailNotification = false;
	Button btnSubmit;
	Spinner spPaymentPreferenceID;
	public static int minMinute, minHour, finalizer = 0;
	private Random random, CustomerrandomNumber;
	private String randomNumber;

	String mNumber;
	String customerrandomNumber;
	Gson gson = null;

	public String newDateStr;
	// Datetime picker;
	private DatePickerDialog fromDatePickerDialog;
	private DatePickerDialog toDatePickerDialog;

	private SimpleDateFormat dateFormatter, dbDateFormatter;
	ProgressDialog progressDialog;
	private EditText fromDateEtxt;
	private EditText toDateEtxt;
	private TimePicker timePicker;
	private Button button;

	private int hour;
	String bookDate;
	private int minute;

	static final int TIME_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID = 0;
	private int mYear, mMonth, mDay;
	MyNetDatabase localDb;
	Context context;
	ArrayList<Customer> customerInfo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_table);
		initControls();
		localDb = new MyNetDatabase(context);

		if (getUserLoginID() != 0) {
			loadGuestDataFromServer();

		}
		else
		{
			ClearAllloadeddata();
		}
	}
	private void loadGuestDataFromServer() {
		localDb.open();
		customerInfo = localDb.getCustomerInfo();

		etFirstName.setText(String.valueOf(customerInfo.get(0).FirstName));
		etSurName.setText(String.valueOf(customerInfo.get(0).LastName));
		etMobileNo.setText(String.valueOf(customerInfo.get(0).MobileNo));
		etEmail.setText(String.valueOf(customerInfo.get(0).Email));
		localDb.close();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!CommonTask.isOnline(this)) {
			Toast.makeText(TableBookingActivity.this, "Your internet connection dry!!!, Please connect...",
					Toast.LENGTH_LONG).show();

		}
		localDb = new MyNetDatabase(context);

		if (getUserLoginID() != 0) {
			loadGuestDataFromServer();

		}
		else
		{
			ClearAllloadeddata();
		}
	}
	private void ClearAllloadeddata() {

		etFirstName.setText("");
		etSurName.setText("");
		etMobileNo.setText("");
		etEmail.setText("");

	}

	private void initControls() {
		gson = new Gson();

		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etSurName = (EditText) findViewById(R.id.etSurName);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etMobileNo = (EditText) findViewById(R.id.etMobileNo);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etNotes = (EditText) findViewById(R.id.etNotes);
		etPartySize = (EditText) findViewById(R.id.etPartySize);

		etDateTime = (EditText) findViewById(R.id.etDateTime);
		mNumber = etMobileNo.getText().toString().trim();

		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		// datetime
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
		dbDateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
		fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
		fromDateEtxt.setInputType(InputType.TYPE_NULL);
		fromDateEtxt.requestFocus();
		toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
		toDateEtxt.setInputType(InputType.TYPE_NULL);
		setDateTimeField();
		textViewTime = (TextView) findViewById(R.id.textViewTime);
		// fromDateEtxt.setOnClickListener(this);
		toDateEtxt.setOnClickListener(this);
		textViewTime.setOnClickListener(this);

		etMobileNo.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

					fromDatePickerDialog.show();
					setDateTimeField();
					return true;
				}
				return false;
			}

			/*
			 * public boolean onKey(View arg0, int arg1, KeyEvent arg2) { //
			 * TODO Auto-generated method stub return false; }
			 */
		});

		setCurrentTimeOnView();
		addButtonListener();
		// ki

		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		if (TelephonyManager.SIM_STATE_READY == tm.getSimState()) {
			etMobileNo.setText(tm.getLine1Number());
		}



	}

	private int getUserLoginID() {
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}

	public void OnHome(View pressed) {
		Intent intent = new Intent(TableBookingActivity.this, HomePageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	private String getDayFromInteger(int dayNumber) {
		String day = null;
		switch (dayNumber) {
		case 1:

			break;

		default:
			break;
		}

		return day;

	}

	private void setDateTimeField() {
		// fromDateEtxt.setOnClickListener(this);
		toDateEtxt.setOnClickListener(this);
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);

		final SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

		// SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		Date d = new Date();
		String dayOfTheWeek = sdf.format(d);

		Calendar newCalendar = Calendar.getInstance();

		fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				String currDay = sdf.format(newDate.getTime());
				fromDateEtxt.setText(currDay + " " + dateFormatter.format(newDate.getTime()));

				bookDate = dbDateFormatter.format(newDate.getTime());
			}

		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		fromDatePickerDialog.setTitle(dayOfTheWeek + " ");

		toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				toDateEtxt.setText(dateFormatter.format(newDate.getTime()));

			}

		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}

	public void onCustomerSignUp(View pressed) {

		String firstName = null;
		firstName = etFirstName.getText().toString().trim();
		String surName = null;
		surName = etSurName.getText().toString().trim();
		String mobileNo = etMobileNo.getText().toString().trim();
		String email = etEmail.getText().toString().trim();
		String Notes = etNotes.getText().toString().trim();
		String PartySize = etPartySize.getText().toString().trim();
		long date;
		date = System.currentTimeMillis();
		String Dates = String.valueOf(date);
		Date = fromDateEtxt.getText().toString();
		dateTime = (Date + " " + time);
		bookDate = (bookDate + " " + time);
		mNumber = mobileNo;
		if (validation(firstName, surName, mobileNo, email, Date, time, PartySize)) {
			// MyType.fName= MyType.valueOf(ValidUser);
			if (ValidUser == "first name") {
				etFirstName.requestFocus();
				etFirstName.setError("Please enter first name.");
			} else if (ValidUser == "sur name") {
				etSurName.requestFocus();
				etSurName.setError("Please enter sur name");
			} else if (ValidUser == "email") {
				etEmail.requestFocus();
				etEmail.setError("Please enter email.");
			} else if (ValidUser == "mobile No") {
				etMobileNo.requestFocus();
				etMobileNo.setError("Please enter mbile No.");
			} else if (ValidUser == "date") {
				fromDateEtxt.requestFocus();
				fromDateEtxt.setError("Please set date.");
			} else if (ValidUser == "time") {
				textViewTime.requestFocus();
				textViewTime.setError("Please set time.");
			} else if (ValidUser == "party size") {
				etPartySize.requestFocus();
				etPartySize.setError("Please enter partysize.");
			}

		}

		else {
			if (!isValidEmail(email)) {
				etEmail.requestFocus();
				etEmail.setError("Please enter valid email address");
			} else {
				String URL = "";
				progressDialog = new ProgressDialog(TableBookingActivity.this, AlertDialog.THEME_HOLO_LIGHT);
				progressDialog.setMessage("Loading....");
				progressDialog.show();

				try {

					if(getUserLoginID() != 0)
					{
						URL = String.format(CommonURL.getInstance().SetBookTable,getUserLoginID(),

								URLEncoder.encode(firstName, CommonConstraints.EncodingCode),
								URLEncoder.encode(surName, CommonConstraints.EncodingCode),
								URLEncoder.encode(mobileNo, CommonConstraints.EncodingCode),
								URLEncoder.encode(email, CommonConstraints.EncodingCode),
								URLEncoder.encode(Notes, CommonConstraints.EncodingCode),
								URLEncoder.encode(PartySize, CommonConstraints.EncodingCode),
								URLEncoder.encode(bookDate, CommonConstraints.EncodingCode), AppConstant.RESTAURANTID);

					}
					else
					URL = String.format(CommonURL.getInstance().SetBookTable, "4",

					URLEncoder.encode(firstName, CommonConstraints.EncodingCode),
							URLEncoder.encode(surName, CommonConstraints.EncodingCode),
							URLEncoder.encode(mobileNo, CommonConstraints.EncodingCode),
							URLEncoder.encode(email, CommonConstraints.EncodingCode),
							URLEncoder.encode(Notes, CommonConstraints.EncodingCode),
							URLEncoder.encode(PartySize, CommonConstraints.EncodingCode),
							URLEncoder.encode(bookDate, CommonConstraints.EncodingCode), AppConstant.RESTAURANTID);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL, null,
						new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								if (response != null) {

									//
									BookATableHolder userRoot = gson.fromJson(response.toString(),
											BookATableHolder.class);

									if (userRoot != null && userRoot.bookATableEntity != null) {

										progressDialog.dismiss();
										Toast.makeText(TableBookingActivity.this,
												"Your table hase been booked successfully", Toast.LENGTH_LONG).show();
										Intent modify_intent = new Intent(TableBookingActivity.this,
												TableBookingReservationActivity.class);
										modify_intent.putExtra("Name", userRoot.bookATableEntity.UserName);
										modify_intent.putExtra("SurName", userRoot.bookATableEntity.SurName);
										modify_intent.putExtra("Email", userRoot.bookATableEntity.EmailAddress);
										modify_intent.putExtra("MobileNo", userRoot.bookATableEntity.ContactNumber);
										modify_intent.putExtra("Date", bookDate);
										modify_intent.putExtra("Time", time);
										modify_intent.putExtra("PartySize",
												String.valueOf(userRoot.bookATableEntity.PartySize));
										modify_intent.putExtra("Notes", userRoot.bookATableEntity.Notes);
										startActivity(modify_intent);
										finish();
										onBackPressed();
									} else {
										etMobileNo.setError("Please enter new pin.");
										etMobileNo.setError("Please enter old pin.");
									}
								}
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								progressDialog.dismiss();
								Toast.makeText(TableBookingActivity.this, "No response from server", Toast.LENGTH_LONG)
										.show();
								/*
								 * Intent modify_intent = new Intent(
								 * TableBookingActivity.this,
								 * neat_Book_Table_Reservation.class);
								 * modify_intent.putExtra("Name",
								 * etFirstName.getText().toString().trim());
								 * modify_intent .putExtra( "Email",
								 * etEmail.getText().toString().trim());
								 * modify_intent .putExtra( "MobileNo",
								 * etMobileNo.getText().toString().trim());
								 * modify_intent.putExtra("Date", bookDate);
								 * modify_intent.putExtra("Time", time);
								 * modify_intent.putExtra( "PartySize",
								 * String.valueOf
								 * (etPartySize.getText().toString().trim()));
								 * modify_intent.putExtra("Notes",
								 * etNotes.getText().toString().trim());
								 * 
								 * startActivity(modify_intent); finish();
								 */
							}
						});

				jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
						DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

				// Adding request to volley request queue
				LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
			}
		}
	}

	boolean validation(String firstName, String surName, String mobileNo, String email, String date, String time,
			String PartySize) {
		if (firstName.isEmpty()) {
			ValidUser = "first name";

			return true;
		} else if (surName.isEmpty()) {
			ValidUser = "sur name";

			return true;
		} else if (email.isEmpty()) {
			ValidUser = "email";

			return true;
		} else if (mobileNo.isEmpty()) {
			ValidUser = "mobile No";

			return true;
		} else if (date.isEmpty()) {
			ValidUser = "date";

			return true;
		} else if (time.isEmpty()) {
			ValidUser = "date";

			return true;
		} else if (PartySize.isEmpty()) {
			ValidUser = "party size";

			return true;
		} else {
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

	/*
	 * @Override public void onClick(View view) { // TODO Auto-generated method
	 * stub if (view == fromDateEtxt) {
	 * 
	 * fromDatePickerDialog.show(); // fromDatePickerDialog.setTitle("");
	 * 
	 * } else if (view == toDateEtxt) { toDatePickerDialog.show();
	 * toDatePickerDialog.setTitle("frdfrd"); } }
	 */

	public void EXTFROMDATE(View view) {
		if (view == fromDateEtxt) {

			fromDatePickerDialog.show();
			// fromDatePickerDialog.setTitle("");

		} else if (view == toDateEtxt) {
			toDatePickerDialog.show();
			toDatePickerDialog.setTitle("frdfrd");
		}
	}

	public void setCurrentTimeOnView() {

		textViewTime = (TextView) findViewById(R.id.textViewTime);

		timePicker = (TimePicker) findViewById(R.id.timePicker);

		final Calendar c = Calendar.getInstance();

		hour = c.get(Calendar.HOUR_OF_DAY);

		minute = c.get(Calendar.MINUTE);
		// set current time into textview

		/*
		 * textViewTime.setText(new StringBuilder().append(padding_str(hour))
		 * .append(":").append(padding_str(minute)));
		 */

		// set current time into timepicker
		time = textViewTime.getText().toString();
		// timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(hour);

		timePicker.setCurrentMinute(minute);

	}

	public void addButtonListener() {

		button = (Button) findViewById(R.id.button);

		textViewTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialog(TIME_DIALOG_ID);

			}

		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set time picker as current time
			minMinute = minute;
			minHour = hour;

			RangeTimePickerDialog timeRange = new RangeTimePickerDialog(TableBookingActivity.this, timeSetListener,
					hour, minute, true);
			timeRange.setMin(hour, minute);
			timeRange.setTitle("" + hour + " : " + minute);
			return timeRange;
		}
		return null;
	}

	public static class CustomTimePickerDialog extends TimePickerDialog {

		public static final int TIME_PICKER_INTERVAL = 15;
		private boolean mIgnoreEvent = false, hourIndicator = false;
		public static int hour;
		public static int minuteOfDay;
		public int oldValue, newValue, maxValue, minValue, minHour, minMinute, newHourValue, oldHourValue, hourFromView;
		private Integer integer;

		public CustomTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute,
				boolean is24HourView) {

			super(context, callBack, hourOfDay, minute, is24HourView);

			minValue = 0;
			maxValue = 59;
			hourFromView = hourOfDay;
			minHour = hourOfDay;
			if (minute > 60) {
				oldValue = minute - 60;
				minMinute = minute - 60;
			} else {
				oldValue = minute;
				minMinute = minute;
			}

			oldHourValue = hourOfDay;
			newHourValue = hourOfDay;
			newValue = 0;
		}

		@Override
		public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
			super.onTimeChanged(timePicker, hourOfDay, minute);
			/*
			 * if(timePicker.getCurrentHour()!=hourOfDay){ mIgnoreEvent= false;
			 * return; }
			 */

			if (!mIgnoreEvent) {
				// hour spinner change

				// minutespinner change

				newValue = minute;
				if (newValue > oldValue) {
					newValue = newValue + 14;
				} else {
					if (hourOfDay <= minHour && (newValue + 1) <= minMinute) {
						minute = minMinute;
						hourOfDay = minHour;
						mIgnoreEvent = true;

						timePicker.setCurrentMinute(minute);
						timePicker.setCurrentHour(hourOfDay);
						return;
					} else {
						if (newValue > 14) {
							newValue = newValue - 14;
						} else {
							newValue = 60 - (14 - newValue);
							hourOfDay = hourOfDay - 1;

						}

					}
					/*
					 * if(newValue<0){ hourOfDay= hourOfDay + 1;
					 * newValue=60-newValue;
					 * 
					 * 
					 * }
					 */
				}

				if (newValue >= 60) {
					hourOfDay = hourOfDay + 1;

					newValue = newValue - 60;

				}
				oldValue = newValue;
				minute = newValue;
				mIgnoreEvent = true;
				timePicker.setCurrentMinute(minute);
				mIgnoreEvent = true;
				timePicker.setCurrentHour(hourOfDay);
				hourFromView = hourOfDay;
			}

			mIgnoreEvent = false;
		}

		public static int getMinute(int minute) {
			minute = minute + 60;
			minuteOfDay = minute;
			return minute;
		}

		public static int getRoundedMinute(int minute) {
			/*
			 * if(minute % TIME_PICKER_INTERVAL != 0){ int minuteFloor = minute
			 * - (minute % TIME_PICKER_INTERVAL); minute = minuteFloor + (minute
			 * == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0); if (minute == 60)
			 * { minute=0; //hourOfDay= hourOfDay+1; } }
			 */
			minute = minute + 14;
			return minute;
		}
	}

	private CustomTimePickerDialog.OnTimeSetListener timeSetListener = new CustomTimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hour = hourOfDay;

			TableBookingActivity.this.minute = minute;
			// set current time into textview

			textViewTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));
			time = textViewTime.getText().toString();
			// set current time into timepicker
			timePicker.setCurrentHour(hour);

			timePicker.setCurrentMinute(minute);
			// txt_time.setText(String.format("%02d", hourOfDay) + ":"
			// +String.format("%02d", minute));
		}
	};

	/*
	 * private DatePickerDialog.OnDateSetListener datePickerListner= new
	 * DatePickerDialog.OnDateSetListener() {
	 * 
	 * @Override public void onDateSet(DatePicker view, int year, int
	 * monthOfYear, int dayOfMonth) { // TODO Auto-generated method stub
	 * 
	 * } };
	 */

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {

			hour = selectedHour;

			minute = selectedMinute;

			// set current time into textview

			textViewTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));
			time = textViewTime.getText().toString();
			// set current time into timepicker
			timePicker.setCurrentHour(hour);
			timePicker.setCurrentMinute(minute);
		}

	};

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

	private static String padding_str(int c) {

		if (c >= 10)

			return String.valueOf(c);

		else

			return "0" + String.valueOf(c);

	}

}
