package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.ProductListAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.entities.EndlessScrollListener;
import com.digitechlabs.neat.entities.MDProductCategoryHolder;
import com.digitechlabs.neat.entities.UserInfoHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.CommonValues;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CategoryListActivity extends HeaderFooterActivity implements OnItemClickListener {

	ListView listView;
	TextView tvNoRecordFound, priceText;
	Gson gson = null;
	int pageIndex = 1;
	int LoginUserNumber = 0;
	EndlessScrollListener endlessScrollListener;
	
	private ArrayList<CategoryImageItem> data = new ArrayList<CategoryImageItem>();
	Object value = null;
	String title;
	private String parentIDString="";

	// MyNetDatabase db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_list);
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		menuSelected();

		tvNoRecordFound = (TextView) findViewById(R.id.tvNoRecordFound);
		gson = new Gson();

		if (savedInstanceState != null && savedInstanceState.containsKey("LoginUserNumber")) {
			String productCategoryId = savedInstanceState.getString("mdProductCategoryId");

			LoginUserNumber = savedInstanceState.getInt("LoginUserNumber", 0);
			if (LoginUserNumber > 0) {
				GetUserInfoByUserID(String.valueOf(LoginUserNumber));
			}
		}

		listView.setOnScrollListener(endlessScrollListener);
		// LoadData();

		// openLocalDB();
		// setPriceInFooter();
		// registerForContextMenu(listView);
	}

	/*
	 * public void setPriceInFooter(){
	 * 
	 * if(db.getTotalPrice()>0.00){
	 * 
	 * priceText.setText(String.valueOf(db.getTotalPrice())); } } public void
	 * openLocalDB(){
	 * 
	 * db= new MyNetDatabase(getBaseContext()); db.open(); }
	 */
	@Override
	protected void onPause() {
		super.onPause();
		// db.close();
	}

	@Override
	protected void onResume() {
		overridePendingTransition(0, 0);
		super.onResume();
		menuSelected();
		LoadData();
	}

	@Override
	protected void onStop() {
		super.onStop(); // Always call the superclass method first
		// CategoryListActivity.this.finish();
	}

	public void OnHome(View pressed) {
		Intent intent = new Intent(CategoryListActivity.this, HomePageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	private void LoadData() {
		String URL = "";
		final ProgressDialog progressDialog;
 	progressDialog = new ProgressDialog(
				CategoryListActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		URL = String.format(
				CommonURL.getInstance().GetProductCategoryByResturantID, AppConstant.RESTAURANTID,
				String.valueOf(pageIndex));
		Log.i("Url aAre ", URL);
		try {
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL,
					null, new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							if (response != null) {
						progressDialog.dismiss();
								MDProductCategoryHolder productroot = gson
										.fromJson(response.toString(),
												MDProductCategoryHolder.class);

								if (productroot != null
										&& productroot.MDProductCategoryList != null) {
								
									ArrayList<CategoryImageItem> imageItems = new ArrayList<CategoryImageItem>();

									// retrieve String drawable array
									TypedArray imgs = getResources()
											.obtainTypedArray(R.array.image_ids);
									for (int i = 0; i < productroot.MDProductCategoryList
											.size(); i++) {

										Bitmap bitmap = BitmapFactory.decodeResource(
														CategoryListActivity.this
																.getResources(),
														imgs.getResourceId(0, -1));
										String productName = productroot.MDProductCategoryList
												.get(i).Name;

										final String value;
										if (productName == null
												|| productName.length() <= 0) {
											value = "_";
										} else {
											value = productName;
										}

										imageItems.add(new CategoryImageItem(
												bitmap,
												String.valueOf(value),
												productroot.MDProductCategoryList.get(i).MDProductCategoryID,
												productroot.MDProductCategoryList.get(i).Description,
												productroot.MDProductCategoryList.get(i).Path,
												productroot.MDProductCategoryList.get(i).ParentID));
									}
									tvNoRecordFound.setVisibility(View.GONE);
									if (progressDialog.isShowing()) {
										progressDialog.dismiss();
									}
									listView = (ListView) findViewById(R.id.list);
									ProductListAdapter adapter = new ProductListAdapter(
											CategoryListActivity.this,
											R.layout.category_item_list,imageItems);
									listView.setAdapter(adapter);

								} else {
									/*
									 * Toast.makeText(UserSignupActivity.this,
									 * "User creation failed",
									 * Toast.LENGTH_LONG).show();
									 */
								}
							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							/*
							 * Toast.makeText(UserSignupActivity.this,
							 * error.getMessage(), Toast.LENGTH_LONG).show();
							 */
						}
					});

			jsonReq.setRetryPolicy(new DefaultRetryPolicy(
					DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

			// Adding request to volley request queue
			LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * @Override public void onBackPressed() { Intent intent = new Intent();
	 * //intent.putIntegerArrayListExtra(SELECTION_LIST, selected);
	 * setResult(RESULT_OK, intent); finish(); // code here to show dialog
	 * //Intent super.onBackPressed(); // optional depending on your needs }
	 */
	private void GetUserInfoByUserID(String userLoginInfoID) {
		String URL = "";
		// URL =
		// String.format(CommonURL.getInstance().CreateCustomer,URLEncoder.encode(userCreateData,
		// "UTF8"));
		URL = String.format(CommonURL.getInstance().GetUserLoginInfoByUserLoginInfoID, userLoginInfoID);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				if (response != null) {

					UserInfoHolder userRoot = gson.fromJson(response.toString(), UserInfoHolder.class);
					if (userRoot != null && userRoot.UserLoginInfoEntity != null) {

						CommonValues.getInstance().LoginUser = userRoot.UserLoginInfoEntity;

						CommonTask.savePreferences(CategoryListActivity.this,
								CommonConstraints.FMP_LOGIN_USERPASS_SHAREDPREF_KEY,
								String.valueOf(CommonValues.getInstance().LoginUser.UserLoginInfoID));

						// LoadData();

						/*
						 * Toast.makeText(CustomerHomeActivity.this,
						 * String.valueOf(CommonValues
						 * .getInstance().LoginUser.UserLoginInfoID),
						 * Toast.LENGTH_LONG).show();
						 */

					} else {

						Toast.makeText(CategoryListActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
					}
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

				Toast.makeText(CategoryListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

			}
		});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}

	public Bitmap getImage(URL url) {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				return BitmapFactory.decodeStream(connection.getInputStream());
			} else
				return null;
		} catch (Exception e) {
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/*
	 * @Override public void onItemClick(AdapterView<?> arg0, View view, int
	 * position, long arg3) {
	 * 
	 * MDProductCategory categorydetails = (MDProductCategory) view.getTag(); //
	 * TODO Auto-generated method stub Intent modify_intent = new
	 * Intent(CategoryListActivity.this, MainActivity.class); // JobDetail
	 * jobdetail = (JobDetail) view.getTag();
	 * modify_intent.putExtra("mdProductCategoryId",
	 * String.valueOf(categorydetails.MDProductCategoryID));
	 * 
	 * startActivity(modify_intent); // TODO Auto-generated method stub }
	 */

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

		Object row = view.getTag();
		TextView text = (TextView) view.findViewById(R.id.tvFromAddress);
		TextView perentID=(TextView)view.findViewById(R.id.perentID);
		parentIDString=perentID.getText().toString();
		 title = text.getText().toString();
		if (title == "") {
		}
		

		for (Field field : row.getClass().getDeclaredFields()) {
			field.setAccessible(true); // You might want to set modifier to
										// public first.
			
			Log.i("Parent ID rgftdgdgfd", String.valueOf(perentID.getText().toString()));
			try {
				if (field.getName().equals("productID")) {
					value = field.get(row);
					getDataFromServer();
					break;
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	private void getDataFromServer() {
		/*final ProgressDialog progressDialog = new ProgressDialog(CategoryListActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);*/
		//db.open();
		final ArrayList<ProductImageItem> imageItems = new ArrayList<ProductImageItem>();

		//progressDialog.setMessage("Loading....");
		//progressDialog.show();
	   String SUB_CAT_URL = String.format(CommonURL.getInstance().GetParentCategory, parentIDString, AppConstant.RESTAURANTID);

		Log.i("Category Url are ", SUB_CAT_URL);

		try {
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, SUB_CAT_URL, null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							if (response != null) {
								//progressDialog.dismiss();
								try {

									Log.i("Category response)", response.toString());

									JSONObject jsonObject=new JSONObject(response.toString());
									JSONArray jsonArray=jsonObject.getJSONArray("CetagoryParentInfo");
									if(jsonArray.length()==0){
										Intent modify_intent = new Intent(CategoryListActivity.this,
												ProductListActivity.class);
										modify_intent.putExtra("mdProductCategoryId",Integer.parseInt(String.valueOf(value)));
										modify_intent.putExtra("CategoryName", title);
										startActivity(modify_intent);
									}else {
										Intent modify_intent = new Intent(CategoryListActivity.this, ProductSubCatListActivity.class);
										modify_intent.putExtra("mdProductCategoryId", Integer.parseInt(String.valueOf(value)));
										modify_intent.putExtra("CategoryName", title);
										modify_intent.putExtra("productID", parentIDString);
										startActivity(modify_intent);
									}
								

									
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {

						}
					});

			jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 3,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

			LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(ctx, "kisu ekta", 1000);
		}
	}
	
}