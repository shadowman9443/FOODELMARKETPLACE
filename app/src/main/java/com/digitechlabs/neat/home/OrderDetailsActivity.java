package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.digitechlabs.neat.entities.LoyaltyHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.CommonValues;
import com.digitechlabs.neat.utils.RangeTimePickerDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

@SuppressLint("ResourceAsColor")
public class OrderDetailsActivity extends HeaderFooterActivity {
	RelativeLayout layout1, layout2, layout3;
	private Button btnCollection;
	private Button btnDelivery;
	private Button btnCash;
	private Button btnCard;
	private Button btnAsap;
	private Button btnChangetime;
	final static String Tag = "DEBUG";
	private TextView deliveryMethod, deliveryTime, card, subTotalAmount,
			totalPriceAmount,redempointsvalue,reedemamount, deliveryCharges;
	private TimePicker timePicker1;
	String totalPrice;
	public String method = "delivery", payment = "Cash";
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private int hour;
	private int currentMinute;
	public static int minMinute, minHour, finalizer = 0;
	String cr;
	Intent intent;
	EditText etEmail;
	static final int TIME_DIALOG_ID = 999;
	Calendar c;
	RelativeLayout llRight;
    MyNetDatabase localDb;
    Context context;
    Gson gson = null;
	private String redeemPoints,afterloyaity;
	private String redeemAmounts;
	EditText etaddtionalinfo;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.order_details);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		hideSoftKeyboard();
		checkoutSelected();
		gson = new Gson();
		init();
		// linBase = (LinearLayout) findViewById(R.id.linBase);
		// textview.append(MY_NUMBER);
	}

	@Override
	protected void onResume() {
		super.onResume();
		localDb = new MyNetDatabase(context);
		init();
		LoadReedimpoints();
	}
	/**
	 * Hides the soft keyboard
	 */
	public void hideSoftKeyboard() {
	    if(getCurrentFocus()!=null) {
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
	private void init() {
		btnCollection = (Button) findViewById(R.id.btncollection);
		btnDelivery = (Button) findViewById(R.id.btndelivery);
		btnCash = (Button) findViewById(R.id.btncash);
		btnCard = (Button) findViewById(R.id.btncard);
		btnAsap = (Button) findViewById(R.id.btnasap);
		btnChangetime = (Button) findViewById(R.id.btnchangetime);
		etEmail = (EditText)findViewById(R.id.etEmail);

		layout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
		layout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
		layout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
		setButtonBackGround();
		
		
		redempointsvalue = (TextView) findViewById(R.id.redempointsvalue);
		reedemamount = (TextView) findViewById(R.id.reedemamount);
		llRight = (RelativeLayout) findViewById(R.id.llRight);
		deliveryCharges = (TextView) findViewById(R.id.deliveryChargeValue);
		setCurrentTimeOnView();
		/*
		 * btnCollection.setOnClickListener(this);
		 * btnDelivery.setOnClickListener(this);
		 * btnCash.setOnClickListener(this); btnCard.setOnClickListener(this);
		 * btnAsap.setOnClickListener(this);
		 * btnChangetime.setOnClickListener(this);
		 */
	}

	public void onPresscollection(View v) {
		// btnCollection.setSelected(true);
		btnCollection.setBackgroundResource(R.drawable.left_selected);
		// btnDelivery.setSelected(false);
		// setButtonState(btnCollection, btnDelivery);
		btnDelivery.setBackgroundResource(R.drawable.right_non_selected);
		// setFirstRowWidth();
		btnCollection.setWidth(v.getWidth());
		btnDelivery.setWidth(v.getWidth());
		btnCollection.setHeight(v.getHeight());
		btnDelivery.setHeight(v.getHeight());
		deliveryMethod.setText("Collection @");
		btnCollection.setTextColor(getResources().getColor(
				R.color.application_common_text_button_inner));
		btnDelivery.setTextColor(getResources().getColor(R.color.orderdetails));
		method = "collection";
	}

	public void onPressdelivery(View v) {
		btnCollection.setBackgroundResource(R.drawable.left_non_selected);
		// btnDelivery.setSelected(true);
		btnDelivery.setBackgroundResource(R.drawable.right_selected);
		btnCollection.setWidth(v.getWidth());
		btnDelivery.setWidth(v.getWidth());
		btnCollection.setHeight(v.getHeight());
		btnDelivery.setHeight(v.getHeight());
		deliveryMethod.setText("Delivery @");
		btnDelivery.setTextColor(getResources().getColor(
				R.color.application_common_text_button_inner));
		btnCollection.setTextColor(getResources()
				.getColor(R.color.orderdetails));
		method = "delivery";
	}

	public void onPresscash(View v) {
		// btnCard.setSelected(false);
		btnCard.setBackgroundResource(R.drawable.right_non_selected);
		// btnCash.setSelected(true);
		btnCash.setBackgroundResource(R.drawable.left_selected);
		btnCash.setWidth(v.getWidth());
		btnCard.setWidth(v.getWidth());
		btnCash.setHeight(v.getHeight());
		btnCard.setHeight(v.getHeight());
		card.setText(" Cash ");
		btnCard.setTextColor(getResources().getColor(R.color.orderdetails));
		btnCash.setTextColor(getResources().getColor(
				R.color.application_common_text_button_inner));
		payment = "Cash";
		if(db.getTotalPrice()<15){
			setDeliveryCharge(CommonValues.getInstance().deliveryChargebelow);
		}
		else{
			setDeliveryCharge("0.0");
		}
		
	}

	public void onPresscard(View v) {
		// btnCard.setSelected(true);
		btnCard.setBackgroundResource(R.drawable.right_selected);
		// btnCash.setSelected(false);
		btnCash.setBackgroundResource(R.drawable.left_non_selected);
		btnCash.setWidth(v.getWidth());
		btnCard.setWidth(v.getWidth());
		btnCash.setHeight(v.getHeight());
		btnCard.setHeight(v.getHeight());
		card.setText(" Card ");
		btnCash.setTextColor(getResources().getColor(R.color.orderdetails));
		btnCard.setTextColor(getResources().getColor(
				R.color.application_common_text_button_inner));
		payment = "Card";
		setDeliveryCharge(CommonValues.deliveryChargeValue);
		Toast.makeText(OrderDetailsActivity.this, AppConstant.DELIVERY_MSG, Toast.LENGTH_LONG).show();
	}
	private void setDeliveryCharge(String charge){
		
		
		Double priceTotal = db.getTotalPrice();
		if( priceTotal<15)
		{
			deliveryCharges.setText(CommonValues.currency + CommonValues.getInstance().deliveryChargebelowwithcard);
			CommonValues.getInstance().deliveryCharge = CommonValues.getInstance().deliveryChargebelowwithcard;
		}
		else{
			CommonValues.getInstance().deliveryCharge = charge;
		    deliveryCharges.setText(CommonValues.currency + CommonValues.getInstance().deliveryCharge);
		}
	}
	public void onPressasap(View v) {
		// btnAsap.setSelected(true);
		btnAsap.setBackgroundResource(R.drawable.left_selected);
		// btnChangetime.setSelected(false);
		btnChangetime.setBackgroundResource(R.drawable.right_non_selected);
		btnAsap.setWidth(v.getWidth());
		btnChangetime.setWidth(v.getWidth());
		btnAsap.setHeight(v.getHeight());
		btnChangetime.setHeight(v.getHeight());
		deliveryTime.setText("ASAP");
		btnChangetime.setTextColor(getResources()
				.getColor(R.color.orderdetails));
		btnAsap.setTextColor(getResources().getColor(
				R.color.application_common_text_button_inner));
		
	}

	public void onPresschangetime(View v) {
		// btnAsap.setSelected(false);
		btnAsap.setBackgroundResource(R.drawable.left_non_selected);
		// btnChangetime.setSelected(true);
		btnChangetime.setBackgroundResource(R.drawable.right_selected);
		btnAsap.setWidth(v.getWidth());
		btnChangetime.setWidth(v.getWidth());
		btnAsap.setHeight(v.getHeight());
		btnChangetime.setHeight(v.getHeight());
		showDialog(TIME_DIALOG_ID);
		btnAsap.setTextColor(getResources().getColor(R.color.orderdetails));
		btnChangetime.setTextColor(getResources().getColor(
				R.color.application_common_text_button_inner));

	}

	public void setFirstRowWidth() {
		ViewGroup.LayoutParams btnCollectionParams = btnCollection
				.getLayoutParams();
		btnCollectionParams.width = (int) getBaseContext().getResources()
				.getDimension(R.dimen.order_details_box_size);
		// btnCollectionParams.height= btnCollection.getHeight();
		btnCollection.setLayoutParams(btnCollectionParams);
		ViewGroup.LayoutParams btnDeliveryParams = btnDelivery
				.getLayoutParams();
		btnDeliveryParams.width = btnCollectionParams.width;
		// btnDeliveryParams.height= btnDelivery.getHeight();
		btnDelivery.setLayoutParams(btnDeliveryParams);
	}

	public void setSecondRowWidth() {
		ViewGroup.LayoutParams btnCashParams = btnCash.getLayoutParams();
		btnCashParams.width = (int) getBaseContext().getResources()
				.getDimension(R.dimen.order_details_box_size);
		// btnCashParams.height= btnCash.getHeight();
		btnCash.setLayoutParams(btnCashParams);
		ViewGroup.LayoutParams btnCardParams = btnCard.getLayoutParams();
		btnCardParams.width = btnCashParams.width;
		// btnCardParams.height= btnCard.getHeight();
		btnCard.setLayoutParams(btnCardParams);
	}

	public void setThirdRowWidth() {

		ViewGroup.LayoutParams btnAsapParams = btnAsap.getLayoutParams();
		btnAsapParams.width = (int) getBaseContext().getResources()
				.getDimension(R.dimen.order_details_box_size);
		// btnAsapParams.height= btnAsap.getHeight();
		btnAsap.setLayoutParams(btnAsapParams);
		ViewGroup.LayoutParams btnChangetimeParams = btnChangetime
				.getLayoutParams();
		btnChangetimeParams.width = btnAsapParams.width;
		// btnChangetimeParams.height= btnChangetime.getHeight();
		btnChangetime.setLayoutParams(btnChangetimeParams);
	}

	public void setButtonBackGround() {
		setFirstRowWidth();
		setSecondRowWidth();
		setThirdRowWidth();

	}

	public void setCurrentTimeOnView() {
		intent = getIntent();
		// totalPrice="100.00";
		totalPrice = String.valueOf(db.getTotalPrice());
		etaddtionalinfo = (EditText)findViewById(R.id.etaddtionalinfo);
		totalPrice = currencyFormatter.format(Double.parseDouble(totalPrice));
		if (totalPrice == null)
			totalPrice = String.valueOf(0.0);
		timePicker1 = (TimePicker) findViewById(R.id.timePicker);
		totalPriceAmount = (TextView) findViewById(R.id.totalpriceamount);
		totalPriceAmount.setText(totalPrice);
		deliveryMethod = (TextView) findViewById(R.id.deliverymethod);
		deliveryTime = (TextView) findViewById(R.id.deliverytime);
		subTotalAmount = (TextView) findViewById(R.id.subtotalamount);
		subTotalAmount.setText(totalPrice);
		Double priceTotal = db.getTotalPrice();
		if( priceTotal<15)
		{
			deliveryCharges.setText(CommonValues.currency + CommonValues.getInstance().deliveryChargebelow);
		}
		card = (TextView) findViewById(R.id.cardmethod);
		c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		currentMinute = c.get(Calendar.MINUTE) + 45;
		if (currentMinute >= 60) {
			if (currentMinute == 60) {
				currentMinute = 0;
			} else {
				currentMinute = currentMinute - 60;
			}
			hour = hour + 1;
		}
		// set current time into textview
		deliveryTime.setText(""
				+ new StringBuilder().append(pad(hour)).append(":")
						.append(pad(currentMinute)));

		// set current time into timepicker
		timePicker1.setCurrentHour(hour);
		timePicker1.setCurrentMinute(currentMinute);

	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	@SuppressLint({ "ResourceAsColor", "NewApi" })
	
	public void onNextPress(View v) {
		CommonValues.getInstance().additoanalinfo = etaddtionalinfo.getText()
				.toString();
		Intent modify_intent = new Intent(OrderDetailsActivity.this,
				YourDetailsActivity.class);

		modify_intent.putExtra("TotalPrice", totalPrice);
		modify_intent.putExtra("DeliveryMethod", deliveryMethod.getText()
				.toString());
		modify_intent.putExtra("DeliveryTime", deliveryTime.getText()
				.toString());
		modify_intent.putExtra("Card", payment);
		modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(modify_intent);

	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:

			minMinute = currentMinute;
			minHour = hour;

			RangeTimePickerDialog timeRange = new RangeTimePickerDialog(
					OrderDetailsActivity.this, timeSetListener, hour,
					currentMinute, true);
			timeRange.setMin(hour, currentMinute);
			return timeRange;
		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener setTimeListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hour = hourOfDay;
			currentMinute = minute;
			// set current time into textview
			deliveryTime.setText(""
					+ new StringBuilder().append(pad(hour)).append(":")
							.append(pad(currentMinute)));
			timePicker1.setCurrentHour(hour);
			timePicker1.setCurrentMinute(currentMinute);
			// txt_time.setText(String.format("%02d", hourOfDay) + ":"
			// +String.format("%02d", minute));
		}
	};

	public static class CustomTimePickerDialog extends TimePickerDialog {

		public static final int TIME_PICKER_INTERVAL = 15;
		private boolean mIgnoreEvent = false, hourIndicator = false;
		public static int hour;
		public static int minuteOfDay;
		public int oldValue, newValue, maxValue, minValue, newHourValue,
				oldHourValue, hourFromView;
		private Integer integer;
		public int initMinute, initHour;
		private boolean isHourChanged = false, isMinuteChanged = false;

		public CustomTimePickerDialog(Context context,
				OnTimeSetListener callBack, int hourOfDay, int minute,
				boolean is24HourView) {

			super(context, callBack, hourOfDay, minute, is24HourView);
			initHour = hourOfDay;
			initMinute = minute;
		}

		@Override
		public void onTimeChanged(TimePicker timePicker, int hourOfDay,
				int minute) {

			super.onTimeChanged(timePicker, hourOfDay, minute);
			if (hourOfDay <= minHour && minute == minMinute) {
				initHour = hourOfDay;
				initMinute = minute;
				timePicker.setCurrentHour(minHour);
				timePicker.setCurrentMinute(minMinute);
			} else {
				if (hourOfDay != initHour) {
					if (isMinuteChanged) {
						isMinuteChanged = false;
						timePicker.setCurrentHour(initHour);

					} else {
						initHour = hourOfDay;
						timePicker.setCurrentHour(hourOfDay);
					}

				}
				if (minute != initMinute) {

					if (minute > initMinute) {
						minute = minute + 14;
					} else
						minute = minute - 14;
					if (minute >= 60) {
						minute = minute - 60;
						hourOfDay = hourOfDay + 1;
						isMinuteChanged = true;
						// timePicker.setCurrentHour(hourOfDay);
						initHour = hourOfDay;
					} else if (minute < 0) {
						minute = 60 + minute;
						hourOfDay = hourOfDay - 1;
						isMinuteChanged = true;
						// timePicker.setCurrentHour(hourOfDay);
						initHour = hourOfDay;
					}
					initMinute = minute;
					timePicker.setCurrentMinute(minute);

				}
			}
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
			currentMinute = minute;
			// set current time into textview
			deliveryTime.setText(""
					+ new StringBuilder().append(pad(hour)).append(":")
							.append(pad(currentMinute)));
			timePicker1.setCurrentHour(hour);
			timePicker1.setCurrentMinute(currentMinute);
			// txt_time.setText(String.format("%02d", hourOfDay) + ":"
			// +String.format("%02d", minute));
		}
	};

	public void onRedeemClick(View v) {
		if (redeemAmounts!=null){
			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.redeem_popup);
			TextView totalPriceWithLoyalty = (TextView) dialog.findViewById(R.id.totalPriceWithLoyaltyPoints);
			TextView loyaltyPoints = (TextView) dialog.findViewById(R.id.redeemloyaltyPoints);
			TextView totalPrice = (TextView) dialog.findViewById(R.id.totalPrice);
			Button yesBtn = (Button) dialog.findViewById(R.id.yesBtn);
			Button noBtn = (Button) dialog.findViewById(R.id.noBtn);
			localDb.open();

			Double priceTotal = localDb.getTotalPrice();
			final Double loyaltyAmounts = Double.parseDouble(redeemPoints);
			DecimalFormat df = new DecimalFormat("#.00");
			df.format(loyaltyAmounts);
			
			Double afterLoyalty = priceTotal - loyaltyAmounts;
			totalPrice.setText(CommonValues.getInstance().currency + Double.toString(priceTotal));
			loyaltyPoints.setText(CommonValues.getInstance().currency + Double.toString(loyaltyAmounts));
			DecimalFormat dff = new DecimalFormat("#.00");
			afterLoyalty = Math.floor(afterLoyalty * 100) / 100;
			dff.format(afterLoyalty);
			
			afterloyaity = Double.toString(afterLoyalty); 
			totalPriceWithLoyalty.setText(CommonValues.getInstance().currency + afterloyaity);
			dialog.show();
			dialog.setCancelable(true);
			localDb.close();
			yesBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//CommonValues.getInstance().redeemPoints = redeemPoints;
					CommonValues.getInstance().redeemAmounts = Double.toString(loyaltyAmounts);
					CommonValues.getInstance().afterloyaltyPoints = afterloyaity;
					dialog.dismiss();
				}
			});
			noBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}
		else {
			
			 Toast.makeText( OrderDetailsActivity.this, "You have not loyaity points to reedem"
			  , Toast.LENGTH_SHORT).show();
			 

		}
		
	}
	private int getUserLoginID() {
		localDb.open();
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}
	private void LoadReedimpoints() {
		// making fresh volley request and getting json
		
		String URL_Login = String.format(CommonURL.getInstance().GetLoyaltyPointsByUser, getUserLoginID());
		
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_Login, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							LoyaltyHolder userRoot = gson.fromJson(response.toString(), LoyaltyHolder.class);
							if (userRoot != null && userRoot.Loyalty != null) {
								
								
								redeemPoints = Double.toString(userRoot.Loyalty.LoyalityPointValue);
								redeemAmounts =  Double.toString(Math.round(userRoot.Loyalty.LoyalityPointValue*100)).split("\\.")[0];
								String cr = currencyFormatter.format(userRoot.Loyalty.LoyalityPointValue);
								redempointsvalue.setText(cr);
								reedemamount.setText(redeemAmounts);

							} else {
								
							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}
	
	public void onPromoCodePress(View v){
		
		final Dialog dialog = new Dialog(this);
		dialog.getWindow();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.promocode_popup);
		final EditText promoField = (EditText) dialog.findViewById(R.id.promoText);
		Button okBtn = (Button) dialog.findViewById(R.id.okBtn);
		dialog.show();
		dialog.setCancelable(true);
		
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!promoField.getText().toString().isEmpty()){
					showProgressDialog("Loading....");
					String URL_Login = String.format(CommonURL.getInstance().SendPromoCode, promoField.getText().toString(), Integer.toString(getUserLoginID()));
					
					JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_Login, null,
							new Response.Listener<JSONObject>() {

								@Override
								public void onResponse(JSONObject response) {
									try {
										if (response.getJSONObject("PromotionalInfo") != null) {
											
											closeProgressDialog();
											dialog.dismiss();
											Toast.makeText( OrderDetailsActivity.this, "Your Promo Code has been sent successfully"
													  , Toast.LENGTH_SHORT).show();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										closeProgressDialog();
										dialog.dismiss();
										Toast.makeText( OrderDetailsActivity.this, "Please enter valid PROMOTIONAL CODE"
												  , Toast.LENGTH_SHORT).show();
									}
								}
							}, new Response.ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError error) {
									
								}
							});

					jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
							DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

					// Adding request to volley request queue
					LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
				}
				else{
					 Toast.makeText( OrderDetailsActivity.this, "Please Enter your Promo Code"
							  , Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void sendSMS(String number, String body){
		
		//String messageText = getResources().getString(R.string.tell_a_friend_mail);
		
		//String messageTextn ="https://play.google.com/store/apps/details?id=com.dgtech.neat&hl=en"; //getResources().getString(R.string.tell_a_friend_afterend);
		//String _messageNumber="";
		//String u = messageText + " & " + messageTextn;
		//String messageText = "Hi , Just SMSed to say hello";

		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.setData(Uri.parse("sms:"+number));
        sendIntent.putExtra("sms_body", body);
        startActivity(sendIntent);
	}
}
