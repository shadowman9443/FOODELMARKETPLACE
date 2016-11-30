package com.digitechlabs.neat.home;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.utils.AppConstant;
import com.google.gson.Gson;

import java.math.BigDecimal;

public class TableBookingReservationActivity extends HeaderFooterActivity
		implements OnClickListener {

	/*
	 * TextView etCustomerNo, etFirstName, etMiddleName, etLastName, etMobileNo,
	 * etEmail, etAddressLine1, etAddressLine2, etPostCode, etRetypePassword,
	 * etReceptEmail, etPassword, etPartySize, etDateTime, etxt_fromdate,
	 * etNotes, etxt_fromtime;
	 */
	TextView etCustomerNo, etFirstName, etMiddleName, etLastName, etMobileNo,
			etEmail, etAddressLine1, etAddressLine2, etPostCode,
			etRetypePassword, etReceptEmail, etPassword, etPartySize,
			etDateTime, etxt_fromdate, etNotes, etxt_fromtime;
	String ValidUser, Date, time, dateTime;
	Gson gson = null;
	int j;
	String T, U;
	private static final int NOTIFY_ME_ID = 1337;
	ImageView fbImg;
	//BroadcastReceiver sendBroadcastReceiver = new sentReceiver();
   // BroadcastReceiver deliveryBroadcastReciever = new deliverReceiver();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.neat_reservation_confirmation);

		// startService(new Intent(this, MyNetService.class));
		init();
		// GetAllStatusByOrderId(10000052);
		// gson = new Gson();
		show();

	}

	public void OnFaceBookPress(View Preessed)
	{
		
		
		/*   String mystring = getResources().getString(R.string.text_email_twitter);
		
		    Intent intent = new Intent(Intent.ACTION_SEND);
	        intent.setType(mystring);
	        intent.putExtra(android.content.Intent.EXTRA_TEXT, mystring);
	        startActivity(intent);*/
		String mystring = getResources().getString(R.string.text_email_twitter);
	        
	        Intent i = new Intent(Intent.ACTION_SEND);
	        i.setType("text/plain");
	        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
	        i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" +
					AppConstant.PACKAGE_NAME +
					"&hl=en");
	        startActivity(Intent.createChooser(i, "Share URL"));

	}
	
	public void OnTwitterPress(View Preessed)
	{
		 Intent intent = new Intent(Intent.ACTION_SEND);
	        intent.setType("text/plain");
	        intent.putExtra(Intent.EXTRA_TEXT, "News for you!");
	        startActivity(intent);
	}
	public void OnMailPress(View Preessed)
	{
	/*	Intent mailClient = new Intent(Intent.ACTION_VIEW);
		mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
		startActivity(mailClient);
		*/
		
	/*	Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"siddique.linkon@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(TellAFriendActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}*/
		String mystring = getResources().getString(R.string.tell_a_friend_mail);
		mystring +=  "https://play.google.com/store/apps/details?id=" +
				AppConstant.PACKAGE_NAME +
				"&hl=en";
		Intent testIntent = new Intent(Intent.ACTION_VIEW);  
		Uri data = Uri.parse("mailto:?subject=" + "Check our App" + "&body=" + mystring + "&to=" + "");
		testIntent.setData(data);  
		startActivity(testIntent);
		
	}
	public void OnSMSUS(View v)
	{
		//startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "",  null)));
		
		String messageText = getResources().getString(R.string.text_email_twitter);
		String _messageNumber="";
		//String messageText = "Hi , Just SMSed to say hello";
		messageText +=  "https://play.google.com/store/apps/details?id=" +
				AppConstant.PACKAGE_NAME +
				"&hl=en";
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.setData(Uri.parse("sms:"+_messageNumber));
        sendIntent.putExtra("sms_body", messageText);
        startActivity(sendIntent);
		/*dialog = new Dialog(this);
		progressDialog = new ProgressDialog(
				TellAFriendActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.sms_dialog);
		btnSMSSend = (Button) dialog.findViewById(R.id.sendSMS);
		cross = (ImageView) dialog.findViewById(R.id.crossMessage);
		message= (EditText) dialog.findViewById(R.id.sms);
		mobile = (EditText) dialog.findViewById(R.id.phoneNumber);
		dialog.setCancelable(true);
		dialog.show();
		btnSMSSend.setOnClickListener(new View.OnClickListener() {
			 @Override
			public void onClick(View v) {
				//String phnNo = "+8801917079684";
				//SmsManager smsManager = SmsManager.getDefault();
				 
				 progressDialog.setMessage("Sending....");
				 progressDialog.show();
				 sendSMS(mobile.getText().toString(),  message.getText().toString());
				 
			    //smsManager.sendTextMessage(phnNo, null, message.getText().toString(), null, null);  
			}
		});
		cross.setOnClickListener(new View.OnClickListener() {
			 @Override
			public void onClick(View v) {
				 if(dialog.isShowing()){
	                	dialog.dismiss();
	                }
			}
		});*/
	}
	

	private void init() {
		// TODO Auto-generated method stub
		etFirstName = (TextView) findViewById(R.id.etFirstName);

		etLastName = (TextView) findViewById(R.id.etLastName);
		etMobileNo = (TextView) findViewById(R.id.etMobileNo);
		etEmail = (TextView) findViewById(R.id.etEmail);
		etNotes = (TextView) findViewById(R.id.etNotes);
		etPartySize = (TextView) findViewById(R.id.etPartySize);
		etxt_fromdate = (TextView) findViewById(R.id.etxt_fromdate);
		etDateTime = (TextView) findViewById(R.id.etDateTime);
		etxt_fromtime = (TextView) findViewById(R.id.etxt_fromtime);
		fbImg = (ImageView) findViewById(R.id.fbImg);
		// fbImg.setOnClickListener(this);
	}

	private void show() {

		Intent i = getIntent();
		String Name = i.getStringExtra("Name");
		String email = i.getStringExtra("Email");
		String mobile = i.getStringExtra("MobileNo");
		String Surname = i.getStringExtra("SurName");
		// String datetime = i.getStringExtra("DateTime");
		String date = i.getStringExtra("Date");
		String time = i.getStringExtra("Time");

		String partysize = i.getStringExtra("PartySize");
		String notes = i.getStringExtra("Notes");
		/*
		 * try { T = CommonTask.convertJsonDateToLiveAlarm1(datetime); U =
		 * CommonTask.convertJsonDateToLiveAlarm2(datetime); } catch
		 * (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		etFirstName.setText(String.valueOf(Name));
		etLastName.setText(String.valueOf(Surname));
		etMobileNo.setText(String.valueOf(mobile));
		etEmail.setText(email);
		etNotes.setText(notes);
		etPartySize.setText(partysize);
		// etxt_fromdate.setText(booktable.UserName);
		if(date.length()>10) {
			date= date.substring(0, 10);
		}
		etxt_fromdate.setText(date);
		etxt_fromtime.setText(time);
		/*
		 * long u = CommonTask .convertJsonDateToLong(notes); T =
		 * CommonTask.convertDateToString(u);
		 */
		/*
		 * long d =CommonTask
		 * .convertDateToStringWithCustomFormat(booktable.DateTime);
		 */

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}


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

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}
}
