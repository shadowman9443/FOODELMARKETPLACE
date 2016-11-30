package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.MyOrderListAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.entities.ProductListHolder;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MyOrderActivity extends HeaderFooterActivity implements
		MyOrderListAdapter.MyOrderAdapterCallback, OnItemClickListener{
	LocalListingApplication comObserver;
	Gson gson,gsonUser = null;
	final static String Tag = "DEBUG";
	Button btTotalPrice, btReorder;
	ListView myOrderListView;
	int LoginUserNumber = 0;
	MyOrderListAdapter boxAdapter;
	Bundle gsavedInstanceState = null;
	ArrayList<CustomerShoppingCart> shoppingCart;
	TextView tvMyCart, priceText;
	public int i = 0;
	public static int mdProductCategoryId = 0;
	public double itemCounter = 0;
	NumberFormat currencyFormatter = NumberFormat
			.getCurrencyInstance();
	Context ctx= this;
	MyNetDatabase db;
	String test;
	public String[][] UserInfo;
	String cr;
	int orderID;
	CustomerShoppingCart customerShoppingCart = new CustomerShoppingCart();
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order_list);
		gsavedInstanceState = savedInstanceState;
		btReorder = (Button) findViewById(R.id.reorder);
		btTotalPrice = (Button) findViewById(R.id.totalPrice);
		myOrderListView = (ListView) findViewById(R.id.my_order_list);
		priceText = (TextView) findViewById(R.id.pricetext);
		gson = new Gson();
		myOrderListView.setAdapter(boxAdapter);
		if (savedInstanceState != null
				&& savedInstanceState.containsKey("ORDER_ID")) {
			orderID = savedInstanceState.getInt("ORDER_ID");
		}
		comObserver = (LocalListingApplication) getApplication();
		comObserver.getObserver().addObserver(this);
	}


	public double getItemPrice() {
		double result = 0;
		for (ProductImageItem p : boxAdapter.getBox()) {
			if (p.IsRemoved) {
				result = p.getPrice();
				// totalAmount += p.getPrice();

				p.IsRemoved = false;
			}
		}
		return result;
		/*
		 * Toast.makeText(this, result + "\n" + "Total Amount:=" + totalAmount,
		 * Toast.LENGTH_LONG).show();
		 */
	}

	public void onPlaceOrder(View pressed){
		Intent modify_intent = new Intent(MyOrderActivity.this,
				OrderDetailsActivity.class);
		db.open();
		modify_intent.putExtra("TotalPrice", String.valueOf(db.getTotalPrice()));
		modify_intent
		.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(modify_intent);
		db.close();
	}
	
	@Override
	public void onAddToCart(double price) {		
		db.open();
		db.updateTotalCost("plus", price);
		comObserver.getObserver().setValue(db.getTotalPrice());
		cr = currencyFormatter.format(db.getTotalPrice());	
		btTotalPrice.setText("TOTAL "+ cr);
		db.close();
	}

	@Override
	public void onRemoveCart(double price) {
		// TODO Auto-generated method stub

		//MyNetDatabase db = new MyNetDatabase(ctx);
		db.open();
		if(db.isTotalPriceExist()){
			db.updateTotalCost("minus", price);
			comObserver.getObserver().setValue(db.getTotalPrice());
		}
		cr = currencyFormatter.format(db.getTotalPrice());	
		btTotalPrice.setText("TOTAL "+ cr);
		db.close();
	}
	@Override
	public void refreshList() {
		// TODO Auto-generated method stub
		//finish();
		//startActivity(getIntent());
		//getDataFromLocalDb();
	}
	@Override
	protected void onResume() {

		super.onResume();
		db = new MyNetDatabase(ctx);
		getDataFromServer();
	}
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first

	    // Save the note's current draft, because the activity is stopping
	    // and we want to be sure the current note progress isn't lost.
	   MyOrderActivity.this.finish();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	private void getDataFromServer() {
		final ProgressDialog progressDialog = new ProgressDialog(
				MyOrderActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		
		final ArrayList<ProductImageItem> imageItems = new ArrayList<ProductImageItem>();
		db.open();
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		String URL_MyOrderList = String.format(
				CommonURL.getInstance().GetMyOrderList, orderID);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				URL_MyOrderList, null, new Response.Listener<JSONObject>() {
					// AlergimonicID
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
												
							ProductListHolder userRoot = gson.fromJson(
									response.toString(),
									ProductListHolder.class);
							if (progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
							if (userRoot != null
									&& userRoot.productList != null) {
								
								// retrieve String drawable array
								TypedArray imgs = getResources()
										.obtainTypedArray(R.array.image_ids);

								for (int i = 0; i < userRoot.productList.size(); i++) {

									Bitmap bitmap = BitmapFactory
											.decodeResource(
													MyOrderActivity.this
															.getResources(),
													imgs.getResourceId(0, -1));
									String productName = userRoot.productList
											.get(i).ProductName;

									int pID = userRoot.productList.get(i).ProductID;
									
									
									final String value;
									if (productName == null
											|| productName.length() <= 0) {
										value = "_";
									} 
									else {
										value = productName;
									}
									if(userRoot.productList.get(i).SpecialNotes.equals("null")){
										userRoot.productList.get(i).SpecialNotes="";
									}
									
									imageItems
											.add(new ProductImageItem(
													bitmap,
													String.valueOf(value),
													userRoot.productList.get(i).ProductID,
													userRoot.productList.get(i).Price,
													userRoot.productList.get(i).Path,
													false,
													userRoot.productList.get(i)
															.getImagePlus(),
													userRoot.productList.get(i).Quantity,
													null,
													userRoot.productList.get(i).Details,
													false,
													0, userRoot.productList.get(i).SpecialNotes, userRoot.productList.get(i).ItemTotalPrice,0));
									if(db.checkIfProductExist(userRoot.productList.get(i).ProductID)){
										customerShoppingCart.Title = userRoot.productList.get(i).ProductName;
										customerShoppingCart.Qty = userRoot.productList.get(i).Quantity;
										customerShoppingCart.ProductID = userRoot.productList.get(i).ProductID;
										customerShoppingCart.Path = userRoot.productList.get(i).Path;
										customerShoppingCart.Cost = userRoot.productList.get(i).Price;
										customerShoppingCart.SpecialNotes = userRoot.productList.get(i).SpecialNotes;
										customerShoppingCart.TotalCost = userRoot.productList.get(i).TotalAmount;
										customerShoppingCart.IsFavorite = userRoot.productList.get(i).IsFaviorite?1:0;
										db.insertProductIntoLocalDb(customerShoppingCart);
									}
								}
								btTotalPrice.setText("TOTAL "+currencyFormatter.format(db.getTotalPrice()));
								boxAdapter = new MyOrderListAdapter(
										MyOrderActivity.this,
										LoginUserNumber, imageItems);

								myOrderListView.setAdapter(boxAdapter);
								db.close();
								
								// LoadData();

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

	/*@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		Log.d(Tag, "onItemClick");
		Object row = view.getTag();

		Object value = null;

		for (Field field : row.getClass().getDeclaredFields()) {

			field.setAccessible(true); // You might want to set modifier to
			// addToCart();
			// public first.
			try {
				if (field.getName().equals("productID")) {
					value = field.get(row);
					// Log.d(Tag, "onItemClick" + );
					// Log.d(Tag, "onItemClick);
					Intent modify_intent = new Intent(PlaceOrderActivity.this,
							ProductDetailViewActivity.class);
					modify_intent.putExtra("PRODUCT_ID",
							Integer.parseInt(String.valueOf(value)));
					modify_intent
							.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(modify_intent);

					
					 * Intent i = new Intent(MainActivity.this,
					 * FullScreenViewActivity.class); i.putExtra("position",
					 * position); startActivity(i);
					 

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
*/	
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setTotalPriceButton(double price) {
		// TODO Auto-generated method stub
		btTotalPrice.setText("Total £"+ price);
	}

	
	
}
