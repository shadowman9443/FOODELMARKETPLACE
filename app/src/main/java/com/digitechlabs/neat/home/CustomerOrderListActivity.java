package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
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
import com.digitechlabs.neat.adapter.CustomerWorkOrderListAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;

import com.digitechlabs.neat.entities.CustomerOrderHolder;
import com.digitechlabs.neat.entities.CustomerOrderListHolder;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.CommonValues;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class CustomerOrderListActivity extends HeaderFooterActivity implements
		OnItemClickListener {

	ListView listView;
	TextView tvNoRecordFound;
	Gson gson = null;

	ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_work_order);
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		tvNoRecordFound = (TextView) findViewById(R.id.tvNoRecordFound);
		gson = new Gson();
		
		// registerForContextMenu(listView);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!CommonTask.isOnline(this)) {
			Toast.makeText(CustomerOrderListActivity.this,
					"Your internet connection dry!!!, Please connect...",
					Toast.LENGTH_LONG).show();

		}

		LoadData();
	}

	private void LoadData() {
		progressDialog = new ProgressDialog(
				CustomerOrderListActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("Processing....");
		progressDialog.show();
		String URL = "";
		// URL =
		// String.format(CommonURL.getInstance().CreateCustomer,URLEncoder.encode(userCreateData,
		// "UTF8"));
		//String.format(CommonUrl.getInstance().CreateCustomer,UrlEncoder.encode)
		URL = String
				.format(CommonURL.getInstance().CustomerWorkOrderByUserInfoID,
						"1",
						String.valueOf(CommonValues.getInstance().LoginUser.UserLoginInfoID));
		// URL=CommonURL.getInstance().GetCustomer;
		// JobDetailListHolder jobDetailHolder = (JobDetailListHolder) data;
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL,
				null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							progressDialog.dismiss();
							CustomerOrderListHolder userRoot = gson.fromJson(
									response.toString(),
									CustomerOrderListHolder.class);
							if (userRoot != null
									&& userRoot.customerOrderList != null) {
								tvNoRecordFound.setVisibility(View.GONE);

								listView = (ListView) findViewById(R.id.list);
								CustomerWorkOrderListAdapter adapter = new CustomerWorkOrderListAdapter(
										CustomerOrderListActivity.this,
										userRoot.customerOrderList);
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
	}

	public void onPlaceOrder(View pressed) {
		CustomerOrder();
	}

	public void onCancel(View pressed) {
		onBackPressed();
	}

	private void CustomerOrder() {
		String URL = "";
		// URL =
		// String.format(CommonURL.getInstance().CreateCustomer,URLEncoder.encode(userCreateData,
		// "UTF8"));
		URL = String
				.format(CommonURL.getInstance().CustomerPlaceOrder,
						"1",
						String.valueOf(CommonValues.getInstance().LoginUser.UserLoginInfoID));
		// URL=CommonURL.getInstance().GetCustomer;
		// JobDetailListHolder jobDetailHolder = (JobDetailListHolder) data;
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL,
				null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							CustomerOrderHolder userRoot = gson.fromJson(
									response.toString(),
									CustomerOrderHolder.class);
							if (userRoot != null
									&& userRoot.customerOrderEntity != null) {
								tvNoRecordFound.setVisibility(View.GONE);
								// CommonValues.getInstance().LoginUser=userRoot.UserLoginInfoEntity;

								Toast.makeText(CustomerOrderListActivity.this,
										"Order Places Successfully",
										Toast.LENGTH_LONG).show();

								/*
								 * listView = (ListView)
								 * findViewById(R.id.list);
								 * DriverHistoryListAdapter adapter = new
								 * DriverHistoryListAdapter(
								 * CustomerShoppingCartListActivity.this,
								 * userRoot.JobDetailList);
								 * listView.setAdapter(adapter);
								 */

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
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {

		Object row = view.getTag();
		/*
		 * Class c = row.getClass(); try { Field
		 * heightField=c.getField("ProductID"); String
		 * r=String.valueOf(heightField.get(row)); } catch (NoSuchFieldException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IllegalArgumentException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		// ImageItem imageItem=(ImageItem)row;
		// row.getClass();
		Object value = null;

		for (Field field : row.getClass().getDeclaredFields()) {
			field.setAccessible(true); // You might want to set modifier to
										// public first.

			try {
				if (field.getName().equals("ProductID")) {
					value = field.get(row);
					Intent modify_intent = new Intent(
							CustomerOrderListActivity.this,
							ProductDetailViewActivity.class);
					modify_intent.putExtra("PRODUCT_ID",
							Integer.parseInt(String.valueOf(value)));
					modify_intent
							.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(modify_intent);

					break;
					// System.out.println(field.getName() + "=" + value);
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
	
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setTitle("Really Exit?")
	        .setMessage("Are you sure you want to exit?")
	        .setNegativeButton(android.R.string.no, null)
	        .setPositiveButton(android.R.string.yes, new OnClickListener() {

	            public void onClick(DialogInterface arg0, int arg1) {
	        		Intent modify_intent = new Intent(
							CustomerOrderListActivity.this,
							CategoryListActivity.class);
	        		modify_intent
					.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(modify_intent);

	            }
	        }).create().show();
	}
	
}