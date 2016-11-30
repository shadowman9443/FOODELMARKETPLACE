package com.digitechlabs.neat.utils;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.dgtech.neat.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommonTask {
	public static String getString(String value) {
		if (value == null)
			return "";
		try {
			return new String(value.getBytes(CommonConstraints.EncodingCode));
		} catch (UnsupportedEncodingException e) {
			return e.toString();
		}
	}

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static void showMessage(final Context context, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.app_name).setMessage(message)
				.setCancelable(false)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						context.startActivity(new Intent(
								android.provider.Settings.ACTION_SETTINGS));
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static BitmapDrawable getDrawableImage(String url) {
		try {
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is, 8 * 512);
			Bitmap bmp = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
			return new BitmapDrawable(null, bmp);

		} catch (Exception e) {
		}
		return null;
	}

	public static Bitmap getBitmapImage(String url) {
		try {
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is, 8 * 512);
			Bitmap bmp = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
			return bmp;

		} catch (Exception e) {
			String log = e.getMessage();
			log = log + log;
		}
		return null;
	}

	public static Bitmap getImageBitmap(String url) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			//Log.e(TAG, "Error getting bitmap", e);
		}
		return bm;
	}

	public static Bitmap get_bitmap(String url) {
		url = url.replaceAll("0.0.0.0", "10.0.2.2");
		Bitmap x=null;
		URL myFileUrl =null;
		try {
			myFileUrl= new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();

			Bitmap raw_image = BitmapFactory.decodeByteArray(buffer.toByteArray(), 0, buffer.size());
			x = Bitmap.createScaledBitmap(raw_image,75, 75, true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}


	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			// Log exception
			return null;
		}
	}

	public static String getRealPathFromURI(Context context, Uri contentUri) {

		Cursor cursor = context
				.getContentResolver()
				.query(contentUri,
						new String[] { MediaStore.Images.ImageColumns.DATA },
						null, null, null);

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private static final String[] PHOTO_BITMAP_PROJECTION = new String[] { ContactsContract.CommonDataKinds.Photo.PHOTO };

	public static Bitmap fetchContactImageThumbnail(Context context,
													final int thumbnailId) {

		final Uri uri = ContentUris.withAppendedId(
				ContactsContract.Data.CONTENT_URI, thumbnailId);
		ContentResolver contentResolver = context.getContentResolver();
		final Cursor cursor = contentResolver.query(uri,
				PHOTO_BITMAP_PROJECTION, null, null, null);

		try {
			Bitmap thumbnail = null;
			if (cursor.moveToFirst()) {
				final byte[] thumbnailBytes = cursor.getBlob(0);
				if (thumbnailBytes != null) {
					thumbnail = BitmapFactory.decodeByteArray(thumbnailBytes,
							0, thumbnailBytes.length);
				}
			}
			return thumbnail;
		} finally {
			cursor.close();
		}

	}

	public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
		int targetWidth = scaleBitmapImage.getWidth() - 10;
		int targetHeight = scaleBitmapImage.getHeight() - 10;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
				Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
				sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
				targetHeight), null);
		return targetBitmap;
	}

	public static String getContentName(Context context, String Number) {
		String name = "";
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(Number));
		Cursor cursor = context.getContentResolver().query(uri,
				new String[] { PhoneLookup.DISPLAY_NAME }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			name = cursor.getString(cursor
					.getColumnIndex(PhoneLookup.DISPLAY_NAME));
			cursor.close();
		}
		return name;
	}

	public static int getContentPhotoId(Context context, String Number) {
		int photoId = -1;
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(Number));
		Cursor cursor = context.getContentResolver()
				.query(uri,
						new String[] { PhoneLookup.DISPLAY_NAME,
								PhoneLookup.PHOTO_ID }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			photoId = cursor.getInt(cursor
					.getColumnIndex(PhoneLookup.PHOTO_ID));
			cursor.close();
		}
		return photoId;
	}

	public static String getString(double value) {

		String dVal = NumberFormat.getInstance(Locale.FRANCE).format(
				CommonTask.round(value, 2, BigDecimal.ROUND_HALF_UP));
		String[] vals = dVal.split(",");
		if (vals.length == 1) {
			return vals[0] + "," + "00";
		} else if (vals.length > 1) {
			return vals[1].length() > 1 ? dVal : vals[0] + "," + vals[1] + "0";
		}
		return dVal;

	}

	public static String getContentString(double value) {

		String dVal = NumberFormat.getInstance(Locale.FRANCE).format(
				CommonTask.round(value, 2, BigDecimal.ROUND_HALF_UP));
		String[] vals = dVal.split(",");
		if (vals.length > 1) {
			return vals[1].length() > 1 ? dVal
					: (!vals[1].equals("0") ? (vals[0] + "," + vals[1] + "0")
					: "");
		}
		return dVal;

	}

	public static int convertToDimensionValue(Context context, int inputValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				inputValue, context.getResources().getDisplayMetrics());
	}

	public static double round(double unrounded, int precision, int roundingMode) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(precision, roundingMode);
		return rounded.doubleValue();
	}

	public static String getstring(int i) {
		String aString = Integer.toString(i);
		return aString;

	}

	public static String toCamelCase(String s) {
		return s.substring(0, 1).toUpperCase(Locale.US)
				+ s.substring(1).toLowerCase(Locale.US);
	}

	/*	public static String toCamelCase(String s, String separator) {
            String[] parts = s.split(separator);
            String camelCaseString = "";
            for (String part : parts) {
                if (!part.equals(null) && !part.isEmpty() && !part.equals("")) {
                    camelCaseString = camelCaseString + toCamelCase(part);
                    camelCaseString = camelCaseString + " ";
                } else {
                    camelCaseString = part;
                }
            }
            return camelCaseString;
        }
    */
	public static void ShowMessage(Context context, String message) {
		/*
		 * AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 * builder.setTitle(R.string.app_name) .setMessage(message)
		 * .setCancelable(false) .setNegativeButton(R.string.button_ok, new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int id) { dialog.cancel(); } });
		 * AlertDialog alert = builder.create(); alert.show();
		 */
	}

	public static void ShowConfirmation(Context context, String message,
										DialogInterface.OnClickListener event) {
		/*
		 * AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 * builder.setTitle(R.string.app_name).setMessage(message)
		 * .setPositiveButton(R.string.button_yes, event)
		 * .setNegativeButton(R.string.button_no, event); AlertDialog alert =
		 * builder.create(); alert.show();
		 */
	} // confirmation dialogue used to exit from the app

	/*
	 * public static void CloseApplication(Context context) {
	 * CommonTask.ShowConfirmation(context, "Vil du forlade?",
	 * CommonTask.ProgramExitEvent(context)); }
	 */

	public static DialogInterface.OnClickListener ProgramExitEvent(
			final Context context) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						dialog.cancel();
						break;
				}
			}
		};
		return dialogClickListener;

	}

	// for the previous movement
	public static Animation inFromRightAnimation() {
		return inFromRightAnimation(350);
	}

	public static Animation outToLeftAnimation() {
		return outToLeftAnimation(350);
	}

	public static Animation inFromLeftAnimation() {
		return inFromLeftAnimation(350);
	}

	public static Animation outToRightAnimation() {
		return outToRightAnimation(350);
	}

	public static Animation inFromRightAnimation(int speed) {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(speed);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	public static Animation outToLeftAnimation(int speed) {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(speed);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	// for the next movement
	public static Animation inFromLeftAnimation(int speed) {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(speed);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	public static Animation outToRightAnimation(int speed) {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(speed);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	public static int SWIPE_MIN_DISTANCE() {
		return 25;
	}

	public static int SWIPE_MAX_OFF_PATH() {
		return 25;
	}

	public static int SWIPE_THRESHOLD_VELOCITY() {
		return 50;
	}

	// Methods for saving data to shared preferences
	public static void savePreferences(Context context, String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void clear(Context context)
	{

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}
	public static void savePreferencesForReasonCode(Context context,
													String key, Integer value) {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void saveLinkedInPreferencesForLogIn(Context context,
													   String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void savePreferenceForCallNOC(Context context, String Key,
												String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor callNocEditor = sharedPreferences.edit();
		callNocEditor.putString(Key, value);
		callNocEditor.commit();
	}

	public static void savePreferenceForCallManager(Context context,
													String Key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor callManagerEditor = sharedPreferences.edit();
		callManagerEditor.putString(Key, value);
		callManagerEditor.commit();
	}

	public static String getPreferences(Context context, String prefKey) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(prefKey, "");
	}

	public static void setStringArrayPref(Context context, String key,
										  ArrayList<String> values) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		JSONArray a = new JSONArray();
		for (int i = 0; i < values.size(); i++) {
			a.put(values.get(i));
		}
		if (!values.isEmpty()) {
			editor.putString(key, a.toString());
		} else {
			editor.putString(key, null);
		}
		editor.commit();
	}

	public static ArrayList<String> getStringArrayPref(Context context,
													   String key) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String json = prefs.getString(key, null);
		ArrayList<String> urls = null;
		if (json != null) {
			try {
				urls = new ArrayList<String>();
				JSONArray a = new JSONArray(json);
				for (int i = 0; i < a.length(); i++) {
					String url = a.optString(i);
					urls.add(url);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return urls;
	}

	public static Bitmap decodeImage(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 512;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale++;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> getContainStringArrayPref(Context context,
															  String key, String charsequence) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String json = prefs.getString(key, null);
		ArrayList<String> urls = null;
		if (json != null) {
			urls = new ArrayList<String>();
			JSONArray a;
			try {
				a = new JSONArray(json);
				for (int i = 0; i < a.length(); i++) {
					if (a.optString(i).toString().contains(charsequence)) {
						String value = a.optString(i);
						urls.add(value);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return urls;
	}

	// Method for Checking Email Validation..Tanvir
	public static boolean checkEmail(String email) {
		return Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	/**
	 * This method checks that whether the server address field is kept empty or
	 * notkept empty or not If no vlaue is provided it will remind the user of
	 * provisding a valid server address with a error message of server not
	 * found.
	 */

	/**
	 * Indicates whether network connectivity exists or is in the process of
	 * being established. For the latter, call isConnected() instead, which
	 * guarantees that the network is fully usable.
	 *
	 * @param context
	 * @return Returns true if network connectivity exists or is in the process
	 *         of being established, false otherwise.
	 */
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		State networkState = null;
		if (connectivityManager != null) {
			networkInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (networkInfo != null) {
				networkState = networkInfo.getState();
				if (networkState == State.CONNECTED
						|| networkState == State.CONNECTING) {
					return true;
				}
			}
			networkInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (networkInfo != null) {
				networkState = networkInfo.getState();
				if (networkState == State.CONNECTED
						|| networkState == State.CONNECTING) {
					return true;
				}
			}
			networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null) {
				networkState = networkInfo.getState();
				if (networkState == State.CONNECTED
						|| networkState == State.CONNECTING) {
					return true;
				}
			}
		}
		// CommonTask.ShowMessage(context,context.getString(R.string.networkError));
		return false;
	}

	/**
	 * User for getting exception message
	 *
	 * @param context
	 * @param exceptionCode
	 * @return
	 */
	public static String getCustomExceptionMessage(Context context,
												   int exceptionCode) {

		/*
		 * switch (exceptionCode) { case CommonConstraints.GENERAL_EXCEPTION:
		 * return context.getString(R.string.GeneralExceptionMessage); case
		 * CommonConstraints.CLIENT_PROTOCOL_EXCEPTION: return
		 * context.getString(R.string.ClientProtocolExceptionMessage); case
		 * CommonConstraints.ILLEGAL_STATE_EXCEPTION: return
		 * context.getString(R.string.IllegalStateExceptionMessage); case
		 * CommonConstraints.IO_EXCEPTION: return
		 * context.getString(R.string.IOExceptionMessage); case
		 * CommonConstraints.UNSUPPORTED_ENCODING_EXCEPTION: return context
		 * .getString(R.string.UnsupportedEncodingExceptionMessage); default:
		 * return ""; }
		 */
		return "";
	}

	public static Date parseDate(String input) throws ParseException {

		/*
		 * Date result = null; input = input.replaceAll("[^0-9]", ""); if
		 * (!TextUtils.isEmpty(input)) { try { result= new
		 * Date(Long.parseLong(input)); } catch (NumberFormatException e) { } }
		 * return result;
		 */

		String result = input.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		return new Date(Long.parseLong(result));

		/*
		 * String timeString = input.substring(input.indexOf("(")+1,
		 * input.indexOf(")")); String timeSegment1 = timeString.substring(0,
		 * timeString.indexOf("+")); String timeSegment2 =
		 * timeString.substring(timeString.indexOf("+")+1); // May have to
		 * handle negative timezones int timeZoneOffSet =
		 * Integer.valueOf(timeSegment2) * 36000; // (("0100" / 100) * 3600 *
		 * 1000) int millis = Integer.valueOf(timeSegment1); return new
		 * Date(millis + timeZoneOffSet);
		 */

	}

	public static String toStringDate(Date date) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return df.format(date);

	}

	public static String convertDateToString(Date date) {

		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm aa");

		return df.format(date);

	}

	public static long convertDatetoString(String inputDate) {
		if (inputDate == null)
			return 0;
		if (inputDate.equals(""))
			return 0;
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
		return Long.parseLong(df.format(inputDate));
	}

	public static String convertCallDateToString(String time) {
		long millisecond = Long.parseLong(time);
		SimpleDateFormat callDate = new SimpleDateFormat("EEE, dd MMM HH:MM:SS");
		return callDate.format(new Date(millisecond));
	}

	public static String convertDateToString(long time) {
		SimpleDateFormat callDate = new SimpleDateFormat("hh:mm");
		return callDate.format(new Date(time));
	}

	public static String convertDateToStringWithCustomFormat(long time) {
		SimpleDateFormat callDate = new SimpleDateFormat("dd MMM yyyy");
		return callDate.format(new Date(time));
	}

	public static String toMessageDateAsString(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("EEE dd MMM yyyy");
		return df.format(new Date(Long.parseLong(result)));

	}

	public static String toMessageDateShortFormat(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yy");
		return df.format(new Date(Long.parseLong(result)));

	}

	public static String toMessageTimeAsString(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy");
		Date date = new Date(Long.parseLong(result));

		if (date.before(df.parse(df.format(new Date()))))
			df = new SimpleDateFormat("EEE, dd MMM hh:mm:ss aa");
		else
			df = new SimpleDateFormat("hh:mm:ss aa");
		return df.format(date);

	}

	public static long convertJsonDateToLong(String inputDate) {
		if (inputDate == null)
			return 0;
		if (inputDate.equals(""))
			return 0;
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		return Long.parseLong(result);
	}

	public static String convertJsonDateToGraphTime(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("HH");
		return df.format(new Date(Long.parseLong(result)));
	}

	public static String convertJsonDateToDailyKPI(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("dd");
		return df.format(new Date(Long.parseLong(result)));
	}

	public static String convertJsonDateToTTStatusTime(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		return df.format(new Date(Long.parseLong(result)));
	}
	public static String convertJsonDateToLiveAlarm(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("dd MMM HH:MM");
		return df.format(new Date(Long.parseLong(result)));
	}
	public static String convertJsonDateToLiveAlarm1(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("dd MMM ");
		return df.format(new Date(Long.parseLong(result)));
	}
	public static String convertJsonDateToLiveAlarm2(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("hh:mm");
		Log.i("Date Result Are",result);
		Date date = new Date(Long.parseLong(result));
		//return df.format(new Date(Long.parseLong(result)));
		return String.valueOf(df.format(date));
	}
	public static String convertJsonDateToCriticalAlarmWorkingUser(
			String inputDate) throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("HH:MM");
		return df.format(new Date(Long.parseLong(result)));
	}

	public static String convertJsonDateToUserGroup(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
		return df.format(new Date(Long.parseLong(result)));
	}

	public static String convertJsonDateToDateWithDay(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("EEE dd MMM yyyy hh:mm aa");
		return df.format(new Date(Long.parseLong(result)));
	}

	public static void makeLinkedTextview(Context context, TextView tv,
										  String text) {

		SpannableString content = new SpannableString(text);
		content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
		tv.setText(content);
		tv.setTextColor(context.getResources().getColor(R.color.blue));
	}

	public static String convertJsonDateToMessageTime(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss aa");
		return df.format(new Date(Long.parseLong(result)));
	}

	public static long convertJsonDateToMessageTimeLong(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return 0;
		if (inputDate.equals(""))
			return 0;
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss aa");
		// return df.format(new Date(Long.parseLong(result)));
		return Long.parseLong(result);
	}

	public static String convertJsonDateToTTDetails(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:MM");
		return df.format(new Date(Long.parseLong(result)));
	}

	public static String getCurrentDateTimeAsString() {
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
		return df.format(new Date());
	}

	public static String convertJsonDateToDateofBirth(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
		return df.format(new Date(Long.parseLong(result)));
	}

	public static boolean isWifiEnabled(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (wifiNetwork != null && wifiNetwork.isConnected()) {

			return true;
		}
		return false;
	}

	public static int distanceCalculationInMeter(double lat1, double lng1,
												 double lat2, double lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return (int) (dist * meterConversion);
	}

	public static int getquotationPreferenceID(String quotationPreferenceName) {

		if (quotationPreferenceName.equals("Cheapest 1st")) {
			return 1;
		} else if (quotationPreferenceName.equals("Earliest/Quickest 1st")) {
			return 2;
		} else if (quotationPreferenceName.equals("Top Rated 1st")) {
			return 3;
		}

		else if (quotationPreferenceName.equals("Expensive 1st")) {
			return 4;
		}

		return 0;
	}

	public static int getPaymentPreferenceID(String paymentPreferenceName) {

		if (paymentPreferenceName.equals("Cash")) {
			return 1;
		} else if (paymentPreferenceName.equals("Card")) {
			return 2;
		} else if (paymentPreferenceName.equals("Paypal")) {
			return 3;
		}

		else if (paymentPreferenceName.equals("Prepaid Accont")) {
			return 4;
		}

		return 0;

	}

	public static int getVehicleTypeID(String vehicleTypeName) {

		if (vehicleTypeName.equals("Sedan")) {
			return 1;
		} else if (vehicleTypeName.equals("7 seater")) {
			return 2;
		} else if (vehicleTypeName.equals("SUV")) {
			return 3;
		}

		else if (vehicleTypeName.equals("7+seater")) {
			return 4;
		}

		return 0;

	}

	public static long convertDatetolong(String stringdate) {
		String dateString = stringdate;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long longDate = date.getTime();
		return longDate;
	}
	public static int getSPicyLevelID(String paymentPreferenceName) {

		if (paymentPreferenceName.equals("Mild")) {
			return 1;
		} else if (paymentPreferenceName.equals("Medium")) {
			return 2;
		} else if (paymentPreferenceName.equals("Hot")) {
			return 3;
		}

		else if (paymentPreferenceName.equals("Extra Hot")) {
			return 4;
		}

		return 0;

	}
	public static int getDeliverbyID(String paymentPreferenceName) {

		if (paymentPreferenceName.equals("30 min")) {
			return 1;
		} else if (paymentPreferenceName.equals("20 min")) {
			return 2;
		} else if (paymentPreferenceName.equals("50 min")) {
			return 3;
		}

		else if (paymentPreferenceName.equals("1 hour")) {
			return 4;
		}

		return 0;

	}

	public static String convertdatetimewithoutslash(String inputDate)
			throws ParseException {
		if (inputDate == null)
			return "";
		if (inputDate.equals(""))
			return "";
		String result = inputDate.replaceAll("^/Date\\(", "");
		result = result.substring(0, result.indexOf('+'));
		SimpleDateFormat df = new SimpleDateFormat("HH:MM");
		return df.format(new Date(Long.parseLong(result)));
	}

}
