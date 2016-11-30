package com.digitechlabs.neat.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.digitechlabs.neat.utils.CommonObserver;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.CommonValues;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LocalListingApplication extends Application {
	public static final String TAG = LocalListingApplication.class
			.getSimpleName();
	
	private RequestQueue mRequestQueue;
	
	private ImageLoader mImageLoader;
	LruBitmapCache mLruBitmapCache;
	public CommonObserver mTest;
	private static LocalListingApplication mInstance;
	
	public List<String> userTempInfo = new ArrayList<String>();
	@Override
	public void onCreate() {
		super.onCreate();
		printHashKey();
		initializeCommonInstance();
		mInstance = this;
		mTest = new CommonObserver();

	}

	private void initializeCommonInstance() {
		CommonURL.initializeInstance();
		CommonValues.initializeInstance();
	}

	public void onLowMemory() {
	}

	public static synchronized LocalListingApplication getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}
	public RequestQueue emptyRequestQueue() {
		if (mRequestQueue!= null) {
			
		}

		return mRequestQueue;
	}
	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			getLruBitmapCache();
			mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
		}

		return this.mImageLoader;
	}

	public LruBitmapCache getLruBitmapCache() {
		if (mLruBitmapCache == null)
			mLruBitmapCache = new LruBitmapCache();
		return this.mLruBitmapCache;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public CommonObserver getObserver() {
		return mTest;
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
