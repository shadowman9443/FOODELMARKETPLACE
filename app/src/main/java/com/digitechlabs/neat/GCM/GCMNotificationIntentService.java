package com.digitechlabs.neat.GCM;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.dgtech.neat.R;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.home.TrackMyOrderActivity;
import com.digitechlabs.neat.home.TrackMyOrderListActivity;
import com.digitechlabs.neat.utils.AppConstant;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

public class GCMNotificationIntentService extends IntentService {
	// Sets an ID for the notification, so it can be updated
	public static final int notifyID = 9001;
	NotificationCompat.Builder builder;
	int userLoginId, userOrderStatus;
	MyNetDatabase db;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		db = new MyNetDatabase(getApplicationContext());
		userLoginId = getUserLoginID();
		userOrderStatus = getOrderStatus();
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification("Deleted messages on server: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				//before changing
				//sendNotification("Google GCM Server:\n\n"+ extras.get("message"));
				//

				//String n = json.getString("name").toString();
				if (extras.containsKey("title")) {
					String Title = extras.get("message").toString();
					sendNotification("" + Title);
				}
				else{
					String y = extras.get("si").toString();
					if(y.equalsIgnoreCase("10"))
					{
						AppConstant.APP_GCM_DELAIED_MSG = extras.get("message").toString();
						AppConstant.APP_GCM_OREDER_ID = extras.get("oi").toString();
						sendNotificationdelay("" + extras.get("message") );
					}
					else{
						sendNotification(""+ extras.get("message"));
					}
				}

			}


		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private int getUserLoginID() {
		db.open();
		int uID = db.getUserID();
		db.close();
		return uID;
	}

	private int getOrderStatus() {
		db.open();
		int orderStatusID = db.getOrderStatus();
		db.close();
		return orderStatusID;
	}

	private void sendNotification(String msg) {
		Toast.makeText(
				GCMNotificationIntentService.this,
				msg,
				Toast.LENGTH_SHORT).show();
		Log.e("Msg", msg);
		if (userLoginId != 0) {
			if (userOrderStatus > 0) {

				Intent resultIntent = new Intent(this, TrackMyOrderListActivity.class);

				PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
						resultIntent, PendingIntent.FLAG_ONE_SHOT);

				NotificationCompat.Builder mNotifyBuilder;
				NotificationManager mNotificationManager;

				mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				mNotifyBuilder = new NotificationCompat.Builder(this)
						.setContentTitle(AppConstant.NOTIFICATIONMSG)
						.setContentText(msg)
						.setSmallIcon(R.drawable.ic_launcher);
				// Set pending intent
				mNotifyBuilder.setContentIntent(resultPendingIntent);

				// Set Vibrate, Sound and Light
				int defaults = 0;
				defaults = defaults | Notification.DEFAULT_LIGHTS;
				defaults = defaults | Notification.DEFAULT_VIBRATE;
				defaults = defaults | Notification.DEFAULT_SOUND;

				mNotifyBuilder.setDefaults(defaults);
				// Set the content for Notification
				mNotifyBuilder.setContentText("" + msg);
				// Set autocancel
				mNotifyBuilder.setAutoCancel(true);
				// Post a notification
				mNotificationManager.notify(notifyID, mNotifyBuilder.build());
			}


		}
	}

	// For Delayed SEND
	private void sendNotificationdelay(String msg) {
		Toast.makeText(
				GCMNotificationIntentService.this,
				msg,
				Toast.LENGTH_SHORT).show();
		Log.e("Msg", msg);
		if (userLoginId != 0) {
			if (userOrderStatus > 0) {


				Intent resultIntent = new Intent(this,
						TrackMyOrderActivity.class);
				resultIntent.putExtra("ORDER_ID",
						Integer.parseInt(String.valueOf(AppConstant.APP_GCM_OREDER_ID)));
				PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
						resultIntent, PendingIntent.FLAG_ONE_SHOT);

				NotificationCompat.Builder mNotifyBuilder;
				NotificationManager mNotificationManager;

				mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				mNotifyBuilder = new NotificationCompat.Builder(this)
						.setContentTitle(AppConstant.NOTIFICATIONMSG)
						.setContentText(msg)
						.setSmallIcon(R.drawable.ic_launcher);
				// Set pending intent
				mNotifyBuilder.setContentIntent(resultPendingIntent);

				// Set Vibrate, Sound and Light
				int defaults = 0;
				defaults = defaults | Notification.DEFAULT_LIGHTS;
				defaults = defaults | Notification.DEFAULT_VIBRATE;
				defaults = defaults | Notification.DEFAULT_SOUND;

				mNotifyBuilder.setDefaults(defaults);
				// Set the content for Notification
				mNotifyBuilder.setContentText("" + msg);
				// Set autocancel
				mNotifyBuilder.setAutoCancel(true);
				// Post a notification
				mNotificationManager.notify(notifyID, mNotifyBuilder.build());
			}


		}
	}

}
