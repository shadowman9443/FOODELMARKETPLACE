package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.HorizontalGalleryAdapter;
import com.digitechlabs.neat.adapter.HorizontalgalleryViewPagerAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.entities.GalleryImageHolder;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.HorizontalListView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;


public class Activity_horizontal_gallery extends HeaderFooterActivity {
    private HorizontalListView mHlvCustomList;
    ViewPager myPager;
    
    ArrayList<GalleryImageitem> imageItems = new ArrayList<GalleryImageitem>();
    Gson gson = null;
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_gallery);
   	 //HorizontalgalleryViewPagerAdapter viewadapter = new HorizontalgalleryViewPagerAdapter(Activity_horizontal_gallery.this, strings[position]);
        // Get references to UI widgets
        mHlvCustomList = (HorizontalListView) findViewById(R.id.hlvSimpleList);
        myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        gson = new Gson();
        LoadData();
       
    }
    
 
	private void LoadData() {
		String URL = "";
		final ProgressDialog progressDialog;
		progressDialog = new ProgressDialog(Activity_horizontal_gallery.this,
				AlertDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("Please wait a while....");
		progressDialog.show();
		
		URL = String.format(CommonURL.getInstance().GalleryImage, "1");
		

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL,
				null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
							progressDialog.dismiss();
							GalleryImageHolder productroot = gson.fromJson(
									response.toString(),
									GalleryImageHolder.class);

							if (productroot != null
									&& productroot.MDProductCategoryList != null) {
								final String[] strings = new String[productroot.MDProductCategoryList
										.size()];
								// retrieve String drawable array
								TypedArray imgs = getResources()
										.obtainTypedArray(R.array.image_ids);
								for (int i = 0; i < productroot.MDProductCategoryList
										.size(); i++) {

									Bitmap bitmap = BitmapFactory
											.decodeResource(
													Activity_horizontal_gallery.this
															.getResources(),
													imgs.getResourceId(0, -1));

									imageItems.add(new GalleryImageitem(bitmap,

											productroot.MDProductCategoryList
													.get(i).Path));

									strings[i] = productroot.MDProductCategoryList
											.get(i).Path;
									/*Picasso.with(Activity_horizontal_gallery.this)
											.load(strings[0]).into(image1);*/
									 HorizontalgalleryViewPagerAdapter viewadapter = new HorizontalgalleryViewPagerAdapter(Activity_horizontal_gallery.this,strings);
								        myPager.setAdapter(viewadapter);
										myPager.setCurrentItem(1);
								}

								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
								 mHlvCustomList = (HorizontalListView) findViewById(R.id.hlvSimpleList);
							       
								HorizontalGalleryAdapter adapter = new HorizontalGalleryAdapter(
										Activity_horizontal_gallery.this,
										imageItems);
								mHlvCustomList.setAdapter(adapter);
								
								
								
								
								/*imageloader = new ImageLoader(
										GalleryActivity.this);
								gallery = (Gallery) findViewById(R.id.gallery1);
								MarginLayoutParams mlp = (MarginLayoutParams) gallery
										.getLayoutParams();
								mlp.setMargins(-(metrics.widthPixels / 2), 0,
										0, 0);
								gallery.setLayoutParams(mlp);
								gallery.setAdapter(new ImageAdapter(
										GalleryActivity.this, strings));
								gallery.setOnItemClickListener(new OnItemClickListener() {
									public void onItemClick(
											AdapterView<?> parent, View v,
											int position, long id) {
										v.getId();

										Picasso.with(GalleryActivity.this)
												.load(strings[position])
												.into(image1);

									}
								});*/

							} else {

							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
				DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
	}
}
