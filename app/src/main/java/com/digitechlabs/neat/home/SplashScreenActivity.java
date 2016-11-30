package com.digitechlabs.neat.home;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.entities.ResturantHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.SharedPreferencesHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

public class SplashScreenActivity extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 10000;
    private Context applicationContext;
    SharedPreferencesHelper helper = new SharedPreferencesHelper();
    AsyncTask<Void, Void, Void> mRegisterTask;
    Gson gson = null;
    String prefUserPass = "", deviceID;
    public static String DeviceId = "";

    /**********************
     * Google Notification Initialization
     *************************************/
    GoogleCloudMessaging gcmObj;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String regId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        applicationContext = this;

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.dgtech.bayleaf", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        gson = new Gson();
        // for badge
        if (!isServiceRunning(MyService.class.getClass().getName())) {
            Intent intSer = new Intent(getApplicationContext(), MyService.class);
            startService(intSer);
        }

        // for GCM
        registerInBackground();
        if (!CommonTask.isOnline(this)) {
            Toast.makeText(SplashScreenActivity.this, "Your internet connection dry!!!, Please connect...",
                    Toast.LENGTH_LONG).show();

        }
        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(10);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {

                } finally {

                }
            }
        };
        splashTread.start();
    }

    /*************************
     * Getting Google FCM Push Notification Registration ID
     **************************************/
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                String test = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging.getInstance(applicationContext);
                    }
                    regId = gcmObj.register(AppConstant.GOOGLE_PROJ_ID);

                    TelephonyManager tm2 = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

                    deviceID = tm2.getDeviceId();
                    CommonTask.savePreferences(SplashScreenActivity.this,
                            CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY, String.valueOf(regId));
                    Log.i("Registration ID ", regId);

                    AppConstant.APP_GCM_ID = regId.toString();

                    Intent intent = new Intent(SplashScreenActivity.this, FindPlaceActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    SplashScreenActivity.this.finish();
                    msg = "Registration ID :" + regId;


                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    /*
					 * Log.i("GCM ID", "" + regId);
					 * Toast.makeText(applicationContext,
					 * "Registered with GCM Server successfully.\n\n" + regId,
					 * Toast.LENGTH_SHORT).show();
					 */
                } else {
					/*
					 * Toast.makeText(applicationContext,
					 * "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
					 * + msg, Toast.LENGTH_LONG).show();
					 */
                }
            }
        }.execute(null, null, null);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If it
     * doesn't, display a dialog that allows users to download the APK from the
     * Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(applicationContext, "This device supports Play services, App will work normally",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }

    private void loadGCMId(String gcmid, String deviceid) {
        // making fresh volley request and getting json

        String URL_Login = String.format(CommonURL.getInstance().SetGCMId, gcmid, deviceid, AppConstant.RESTAURANTID);
        Log.i("", URL_Login);

        JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_Login, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {

                            ResturantHolder userRoot = gson.fromJson(response.toString(), ResturantHolder.class);
                            if (userRoot != null && userRoot.restaurantEntity != null) {
                                String Status = userRoot.restaurantEntity.Status;

                                if (Status == "true") {
                                    int AppfeatureId = userRoot.restaurantEntity.AppFreatureID;
                                    AppConstant.APPFEATUREID = String.valueOf(AppfeatureId);
                                    //AppConstant.APPFEATUREID = "1";
                                    AppConstant.ASSIGNEDFEATURES = userRoot.restaurantEntity.AssignedFeatures;


                                }
                            } else {
                                //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

						/*
						 * Toast.makeText(SplashScreenActivity.this,
						 * "No response from server", Toast.LENGTH_LONG)
						 * .show();
						 */
            }
        });

        jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to volley request queue
        LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

    }


    private boolean isServiceRunning(String serviceName) {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(50);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningServiceInfo runningServiceInfo = (ActivityManager.RunningServiceInfo) i.next();

            if (runningServiceInfo.service.getClassName().equals(serviceName)) {
                serviceRunning = true;
            }
        }
        Log.i("DEMO", "Is " + serviceName + " Service Running : " + serviceRunning);
        return serviceRunning;
    }

}
