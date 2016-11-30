package com.digitechlabs.neat.home;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonURL;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


public class TellAFriendActivity extends HeaderFooterActivity implements OnClickListener{
	BroadcastReceiver sendBroadcastReceiver = new sentReceiver();
    BroadcastReceiver deliveryBroadcastReciever = new deliverReceiver();
	Button btnSMSSend;
	ImageView cross;
	Dialog dialog;
	EditText message, mobile;
	MyNetDatabase db;
	ProgressDialog progressDialog;
	Context ctx;

	CallbackManager callbackManager;
	ShareDialog shareDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);

		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);


		setContentView(R.layout.tell_a_friend);
		db = new MyNetDatabase(ctx);
		tellafriendSelected();
		
	}
	private int getUserLoginID() {
		db.open();
		int uID = db.getUserID();
		db.close();
		return uID;
	}
	public void OnFaceBookPress(View Preessed)
	{
		
		
		/*   String mystring = getResources().getString(R.string.text_email_twitter);
		
		    Intent intent = new Intent(Intent.ACTION_SEND);
	        intent.setType(mystring);
	        intent.putExtra(android.content.Intent.EXTRA_TEXT, mystring);
	        startActivity(intent);*/


	String mystring = getResources().getString(R.string.text_email_twitter);
		String messageTextn = getResources().getString(R.string.tell_a_friend_afterend);
		mystring += "&" + messageTextn;
	        Intent i = new Intent(Intent.ACTION_SEND);
	        i.setType("text/plain");
	        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
	        i.putExtra(Intent.EXTRA_TEXT, AppConstant.PLAY_STORE_URL);
	        startActivity(Intent.createChooser(i, "Share URL"));



	}
	
	public void OnTwitterPress(View Preessed)
	{
		 Intent intent = new Intent(Intent.ACTION_SEND);
	        intent.setType("text/plain");
	        intent.putExtra(Intent.EXTRA_TEXT, "News for you!");
	        startActivity(intent);
	}
	private void sendMessage(String number, String androidURL, String iOSURL, String promoCode){
		String body = "Android Download Link: " + androidURL + "\n" + "iOS Download Link: " + iOSURL + "\n" + "Your Promo Code: " + promoCode;
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.setData(Uri.parse("sms:"+number));
        sendIntent.putExtra("sms_body", body);
        startActivity(sendIntent);
	}
	
	public void OnSMSUS(View v)
	{
		if(userLoginID!=0)
		{
		//startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "",  null)));
		String URL_Login = String.format(CommonURL.getInstance().GetPromoCode, Integer.toString(getUserLoginID()), "android");
		
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_Login, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
							//dialog.dismiss();
							
							try {
								String promoCode = response.getJSONObject("data").getString("promocode").toString();
								//String date = response.getJSONObject("data").getString("createdat").toString();
								String androidLink = response.getJSONObject("data").getString("androidownloadlink").toString();
								String iosLink = response.getJSONObject("data").getString("iosdownloadlink").toString();
								sendMessage("", androidLink, iosLink, promoCode);
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
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
		else
		{
			 Toast.makeText(getBaseContext(), "Please create or login your account for sharing app",
                     Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(TellAFriendActivity.this,NeatLoginActivity.class);

			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		}
	}
	private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
                SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        registerReceiver(sendBroadcastReceiver, new IntentFilter(SENT));

        registerReceiver(deliveryBroadcastReciever, new IntentFilter(DELIVERED));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);


    }
	class deliverReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent arg1) {
            switch (getResultCode()) {
            case Activity.RESULT_OK:
            	if (progressDialog.isShowing()) {
            		progressDialog.dismiss();
            	}
            	message.setText("");
                Toast.makeText(getBaseContext(), "Message Delivered",
                        Toast.LENGTH_SHORT).show();
                              
                break;
            case Activity.RESULT_CANCELED:
            	if (progressDialog.isShowing()) {
            		progressDialog.dismiss();
            	}
                Toast.makeText(getBaseContext(), "Message Not Delivered",
                        Toast.LENGTH_SHORT).show();
                
                break;
            }

        }

		
    }

    class sentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent arg1) {
            switch (getResultCode()) {
            case Activity.RESULT_OK:
                Toast.makeText(getBaseContext(), "Message Delivered", Toast.LENGTH_SHORT)
                        .show();
                //startActivity(new Intent(SendSMS.this, ChooseOption.class));
                //overridePendingTransition(R.anim.animation, R.anim.animation2);
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            	  if (progressDialog.isShowing()) {
              		progressDialog.dismiss();
              	}
                Toast.makeText(getBaseContext(), "Unable To Send SMS",
                        Toast.LENGTH_LONG).show();
              
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
            	  if (progressDialog.isShowing()) {
                		progressDialog.dismiss();
                	}
                Toast.makeText(getBaseContext(), "No service",
                        Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
            	  if (progressDialog.isShowing()) {
                		progressDialog.dismiss();
                	}
                Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT)
                        .show();
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
            	  if (progressDialog.isShowing()) {
                		progressDialog.dismiss();
                	}
                Toast.makeText(getBaseContext(), "Radio off",
                        Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
	@SuppressWarnings("static-access")
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
		String messageTextn = getResources().getString(R.string.tell_a_friend_afterend);
		String u = mystring + " & " + messageTextn;
		Intent testIntent = new Intent(Intent.ACTION_VIEW);  
		Uri data = Uri.parse("mailto:?subject=" + "Check out " +
				AppConstant.RESTURENT_NAME + "&body=" + Uri.encode(u) + "&to=" + "");
		testIntent.setData(data);  
		startActivity(testIntent);
		
		/*if (testIntent.ACTION_SEND != null)
		{
			Toast.makeText(TellAFriendActivity.this, "MAIL SENT", Toast.LENGTH_SHORT).show();
			
		}*/
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	private void shareTextUrl() throws IOException, URISyntaxException {

		final String myUrlStr = "http://212.227.22.224:8087/Restaurant/Bangkok_Eatery/Pro505Monkfish_Basil_&_Chilli.png";
		URL url;
		Uri uri=null;
		try {
			url = new URL(myUrlStr);
			uri = Uri.parse( url.toURI().toString() );
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (ShareDialog.canShow(ShareLinkContent.class)) {
			ShareLinkContent linkContent = new ShareLinkContent.Builder()
					.setContentTitle(AppConstant.RESTURENT_NAME)
					.setContentDescription("Hi,You should check out Bangkok Eatery.")
					.setContentUrl(Uri.parse("http://developers.facebook.com/android"))
									.build();

			shareDialog.show(linkContent);
		}

	}

	
}
