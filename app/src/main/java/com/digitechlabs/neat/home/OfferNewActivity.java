package com.digitechlabs.neat.home;


import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.CustomArrayAdapter;
import com.digitechlabs.neat.adapter.FullScreenImageAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.entities.OfferListHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.HorizontalListView;
import com.digitechlabs.neat.utils.ImageLoader;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class OfferNewActivity extends HeaderFooterActivity implements AdapterView.OnItemClickListener {
	private String LOG = "OfferNewActivity";

	ImageLoader imageloader;
	Gson gson = null;
	LayoutInflater lInflater;
	GalleryImageitem GC;
	ArrayList<Offeritem> imageItems = new ArrayList<Offeritem>();

	String[] strings;
	DisplayMetrics metrics;

	private HorizontalListView mHlvCustomList;
	private Context context;
	CustomArrayAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_page);
		context = this;
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		gson = new Gson();
		initUI();
		LoadData();

		// gallery();
	}

	private void initUI() {
		mHlvCustomList = (HorizontalListView) findViewById(R.id.hlvCustomList);
		mHlvCustomList.setOnItemClickListener(this);

	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
			viewPager.setCurrentItem(position);
			adapter.notifyDataSetChanged();
		
	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private int itemBackground;
		int GalItemBg;

		String[] strings;

		public ImageAdapter(Context c, String[] strings) {
			context = c;
			this.strings = strings;
			// sets a grey background; wraps around the images
			TypedArray a = obtainStyledAttributes(R.styleable.MyGallery);
			itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return strings.length;
		}

		public Object getItem(int position) {
			return position;
		}

		Offeritem getProduct(int position) {
			return ((Offeritem) getItem(position));
		}

		@SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView imgView = new ImageView(context);
			/*
			 * Offeritem o = getProduct(position);
			 * offerTitle.setText(String.valueOf(o.getTitle()));
			 */
			imgView.setLayoutParams(new Gallery.LayoutParams(200, LayoutParams.WRAP_CONTENT));
			imgView.setScaleType(ImageView.ScaleType.FIT_XY);
			imgView.scrollTo(0, position);
			imageloader.DisplayImage(strings[position], imgView);
			imgView.setBackgroundResource(itemBackground);

			return imgView;

		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
	}

	public String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	private void LoadData() {
		String URL = "";
		final ProgressDialog progressDialog;
		progressDialog = new ProgressDialog(OfferNewActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("Please wait a while....");
		progressDialog.show();
		URL = String.format(CommonURL.getInstance().GetOfferByTime, getCurrentDate(), AppConstant.RESTAURANTID);
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				if (response != null) {
					progressDialog.dismiss();
					final OfferListHolder productroot = gson.fromJson(response.toString(), OfferListHolder.class);
					if (productroot != null && productroot.itemOfferList != null) {
						final String[] strings = new String[productroot.itemOfferList.size()];
						// retrieve String drawable array
						TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
						for (int i = 0; i < productroot.itemOfferList.size(); i++) {

							Bitmap bitmap = BitmapFactory.decodeResource(OfferNewActivity.this
													.getResources(),
											imgs.getResourceId(0, -1));
							imageItems.add(new Offeritem(bitmap,
									productroot.itemOfferList.get(i).OfferImageUrl,
									productroot.itemOfferList.get(i).OfferTitle,
									productroot.itemOfferList.get(i).OfferTermsCondition,
									productroot.itemOfferList.get(i).Description));
							strings[i] = productroot.itemOfferList
									.get(i).OfferImageUrl;
							
							ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
							FullScreenImageAdapter adapter = new FullScreenImageAdapter(OfferNewActivity.this, imageItems);
							viewPager.setAdapter(adapter);
							viewPager.setCurrentItem(0);
						}

						if (progressDialog.isShowing()) {
							progressDialog.dismiss();
						}

						 adapter = new CustomArrayAdapter(OfferNewActivity.this, imageItems);
						mHlvCustomList.setAdapter(adapter);

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

	/*private Drawable LoadThumbnailFromURL(String url) {
		try {
			URLConnection connection = new URL(url).openConnection();
			String contentType = connection.getHeaderField("Content-Type");
			boolean isImage = contentType.startsWith("image/");
			if (isImage) {
				HttpGet httpRequest = new HttpGet(url);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);

				InputStream is = bufferedHttpEntity.getContent();
				Drawable d = Drawable.createFromStream(is, "src Name");
				return d;
			} else {
				Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
				Drawable d = new BitmapDrawable(b);
				return d;
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

			return null;
		}
	}*/

	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	private String downloadUrl(String myurl) throws IOException {
		InputStream is = null;
		// Only display the first 500 characters of the retrieved
		// web page content.
		int len = 500;

		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			// Log.d(DEBUG_TAG, "The response is: " + response);
			is = conn.getInputStream();

			// Convert the InputStream into a string
			String contentAsString = readIt(is, len);
			return contentAsString;

			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	private String readIt(InputStream is, int len) {
		// TODO Auto-generated method stub
		return null;
	}

}
