package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.digitechlabs.neat.adapter.ProductSubCatAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.entities.ProductSubCatList;
import com.digitechlabs.neat.entities.ProductSubCatModel;
import com.digitechlabs.neat.entities.PsubcatListParser;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ProductSubCatListActivity extends HeaderFooterActivity {
	LocalListingApplication comObserver;
	private Context context;
	int productCategoryId,subparentid;
	String parentID;
	String categoryNameText,subname;
	ProductSubCatAdapter adapter;
	private TextView subcat_title;
	private ListView sub_list;
	Object value = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_sub_list);
		context = this;

		if (savedInstanceState != null && savedInstanceState.containsKey("mdProductCategoryId")) {
			productCategoryId = savedInstanceState.getInt("mdProductCategoryId");
			categoryNameText = savedInstanceState.getString("CategoryName");
			parentID = savedInstanceState.getString("productID");
			comObserver = (LocalListingApplication) getApplication();
			comObserver.getObserver().addObserver(this);
		}

		initUISUB();
	}

	private void initUISUB() {
		subcat_title = (TextView) findViewById(R.id.subcat_title);
		Log.i("Subcat NAme are ", categoryNameText);
		subcat_title.setText(categoryNameText);
		sub_list = (ListView) findViewById(R.id.sub_list);
		adapter = new ProductSubCatAdapter(context);

        getDataFromServer();
		sub_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				final ProductSubCatModel query = ProductSubCatList.getAllSubCatList().elementAt(position);

				subparentid = query.getMDProductCategoryID();
				subname = query.getName();
				SubcategorygetDataFromServer(subparentid, subname);
				//	Intent modify_intent = new Intent(ProductSubCatListActivity.this, ProductListActivity.class);
				//modify_intent.putExtra("mdProductCategoryId", query.getMDProductCategoryID());
				//modify_intent.putExtra("CategoryName", query.getName());

				//modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				//startActivity(modify_intent);
			}
		});
	}
	private void SecondinitUISUB(final int a,final String b) {
		subcat_title = (TextView) findViewById(R.id.subcat_title);
		Log.i("Subcat NAme are ", categoryNameText);
		subcat_title.setText(b);
		sub_list = (ListView) findViewById(R.id.sub_list);
		adapter = new ProductSubCatAdapter(context);

		SublistseconddatgetDataFromServer(a,b);
		sub_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				final ProductSubCatModel query = ProductSubCatList.getAllSubCatList().elementAt(position);

				subparentid = query.getMDProductCategoryID();
				subname = query.getName();
				SubcategorygetDataFromServer(subparentid, subname);
				//	Intent modify_intent = new Intent(ProductSubCatListActivity.this, ProductListActivity.class);
				//modify_intent.putExtra("mdProductCategoryId", query.getMDProductCategoryID());
				//modify_intent.putExtra("CategoryName", query.getName());

				//modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				//startActivity(modify_intent);
			}
		});
	}
	private void getDataFromServer() {
		final ProgressDialog progressDialog = new ProgressDialog(ProductSubCatListActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);
		db.open();
		final ArrayList<ProductImageItem> imageItems = new ArrayList<ProductImageItem>();

		progressDialog.setMessage("Loading....");
		progressDialog.show();
	   String SUB_CAT_URL = String.format(CommonURL.getInstance().GetParentCategory, parentID, AppConstant.RESTAURANTID);

		Log.i("Subcat Url are ", SUB_CAT_URL);

		try {
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, SUB_CAT_URL, null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							if (response != null) {
								progressDialog.dismiss();
								try {

									Log.i("Subcat response", response.toString());

									JSONObject jsonObject=new JSONObject(response.toString());
									JSONArray jsonArray=jsonObject.getJSONArray("CetagoryParentInfo");
									Log.i("Subcat Json Array ", ""+jsonArray);
									
									if(jsonArray.length()==0){
										ProductSubCatListActivity.this.finish();
										Intent modify_intent = new Intent(ProductSubCatListActivity.this,
												ProductListActivity.class);
										modify_intent.putExtra("mdProductCategoryId",
												productCategoryId);
										modify_intent.putExtra("CategoryName", categoryNameText);
										modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										startActivity(modify_intent);	
									}else {
										Log.i("Subcat response", response.toString());
										PsubcatListParser.connect(context, response.toString());

											sub_list.setAdapter(adapter);
											adapter.notifyDataSetChanged();

									}
								

									
								} catch (JSONException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}

							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							Log.i("Subcat response", error.toString());
						}
					});

			jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 3,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

			LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
		} catch (Exception e) {
			e.printStackTrace();
			//Toast.makeText(ctx, "kisu ekta", 1000);
		}
	}


	private void SubcategorygetDataFromServer( final int sub,final String sname ) {
		/*final ProgressDialog progressDialog = new ProgressDialog(CategoryListActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);*/
		//db.open();
		final ArrayList<ProductImageItem> imageItems = new ArrayList<ProductImageItem>();

		//progressDialog.setMessage("Loading....");
		//progressDialog.show();
		String SUB_CAT_URL = String.format(CommonURL.getInstance().GetParentCategory, sub, AppConstant.RESTAURANTID);

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
										Intent modify_intent = new Intent(ProductSubCatListActivity.this,
												ProductListActivity.class);
										modify_intent.putExtra("mdProductCategoryId",Integer.parseInt(String.valueOf(sub)));
										modify_intent.putExtra("CategoryName", sname);
										startActivity(modify_intent);

										//	Intent modify_intent = new Intent(ProductSubCatListActivity.this, ProductListActivity.class);
										//modify_intent.putExtra("mdProductCategoryId", query.getMDProductCategoryID());
										//modify_intent.putExtra("CategoryName", query.getName());

										//modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										//startActivity(modify_intent);
									}else {
										/*Intent modify_intent = new Intent(ProductSubCatListActivity.this, ProductSubCatListActivity.class);
										modify_intent.putExtra("mdProductCategoryId", Integer.parseInt(String.valueOf(value)));
										modify_intent.putExtra("CategoryName", categoryNameText);
										modify_intent.putExtra("productID", parentID);
										startActivity(modify_intent);*/
										SecondinitUISUB(sub,sname);
									//	initUISUB();
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
		//	Toast.makeText(ctx, "kisu ekta", 1000);
		}
	}

	private void SublistseconddatgetDataFromServer(final int d, final String e) {
		final ProgressDialog progressDialog = new ProgressDialog(ProductSubCatListActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);
		db.open();
		final ArrayList<ProductImageItem> imageItems = new ArrayList<ProductImageItem>();

		progressDialog.setMessage("Loading....");
		progressDialog.show();
		String SUB_CAT_URL = String.format(CommonURL.getInstance().GetParentCategory, d, AppConstant.RESTAURANTID);

		Log.i("Subcat Url are ", SUB_CAT_URL);

		try {
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, SUB_CAT_URL, null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							if (response != null) {
								progressDialog.dismiss();
								try {

									Log.i("Subcat response", response.toString());

									JSONObject jsonObject=new JSONObject(response.toString());
									JSONArray jsonArray=jsonObject.getJSONArray("CetagoryParentInfo");
									Log.i("Subcat Json Array ", ""+jsonArray);

									if(jsonArray.length()==0){
										ProductSubCatListActivity.this.finish();
										Intent modify_intent = new Intent(ProductSubCatListActivity.this,
												ProductListActivity.class);
										modify_intent.putExtra("mdProductCategoryId",
												productCategoryId);
										modify_intent.putExtra("CategoryName", categoryNameText);
										modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										startActivity(modify_intent);
									}else {
										Log.i("Subcat response", response.toString());
										PsubcatListParser.connect(context, response.toString());

										sub_list.setAdapter(adapter);
										adapter.notifyDataSetChanged();

									}



								} catch (JSONException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}

							}
						}
					}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Log.i("Subcat response", error.toString());
				}
			});

			jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 3,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

			LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
		} catch (Exception ex) {
			ex.printStackTrace();
			//Toast.makeText(ctx, "kisu ekta", 1000);
		}
	}
	@Override
	protected void onResume() {
		overridePendingTransition(0, 0);
		sub_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		super.onResume();
	}

}
