package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.FavoriteListAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.entities.FavoriteListHolder;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MyFavouriteListActivity extends HeaderFooterActivity implements OnItemClickListener,
		FavoriteListAdapter.FavoriteAdapterCallback {

	TextView tvamount, tvtotaltopay, tvtotalperperson, tvTotalTip, tvService,
			tvserviceTitle, tvSplitName,tvTotalTipPerPerson;	
	ListView favoriteListView;
	FavoriteListAdapter favoriteListAdapter;
	TextView sellingprice, detailsviewtext, itemtext, itemminus, tvsingleitem, tvsingleitemminus, vImage, nImage,
	gImage, prdName;
	double singleitemprice;
	int pID;
	private ImageView mNetworkImageView;
	RelativeLayout clickableLayout;
	Dialog dialog;
	public int i = 0, itemcountlocaldb = 0;
	ImageView imageplus, image;
	Gson gson, gsonUser = null;
	public int itemCount = 0;
	String url,details,ItemName;
	final NumberFormat currencyFormatter = NumberFormat
			.getCurrencyInstance();
	 MyNetDatabase localDb;
		CustomerShoppingCart c = new CustomerShoppingCart();

	 ArrayList<CustomerShoppingCart> shoppingCart;
		Context context;
		LocalListingApplication comObserver;
		String totalprice;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.favourite_list);
		favoriteListView = (ListView) findViewById(R.id.favorite_list);
		settingsSelected();
		favoriteListView.setOnItemClickListener(this);
		
		settingsSelected();
		gson = new Gson();
		init();
		favoriteListView.setAdapter(favoriteListAdapter);
		comObserver = (LocalListingApplication) getApplication();
		comObserver.getObserver().addObserver(this);
	}

	@Override
	protected void onResume() {

		super.onResume();
		
		localDb = new MyNetDatabase(context);
		getFeedBackList();
	}

	public void init() {
		
		
	}
	private int getUserLoginID(){
		localDb.open();
		context = this;
		int uID = localDb.getUserID();
		localDb.close();
		return uID;
	}
	public void onPlaceOrder(View pressed){
		localDb.open();
		shoppingCart= localDb.getAddToCart();
		if(shoppingCart.size()>0){
			Intent modify_intent = new Intent(MyFavouriteListActivity.this,
					OrderDetailsActivity.class);
			modify_intent.putExtra("TotalPrice", String.valueOf(localDb.getTotalPrice()));
			modify_intent
			.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(modify_intent);			
		}
		else{
			Toast.makeText(
					MyFavouriteListActivity.this,
					"No Item in your cart. Please add item in cart to place order",
					Toast.LENGTH_LONG).show();
		}
		localDb.close();
	}
	public void getFeedBackList(){
		final ArrayList<FavoriteImageItem> imageItems = new ArrayList<FavoriteImageItem>();
		final ProgressDialog progressDialog = new ProgressDialog(
				MyFavouriteListActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		/*final NumberFormat currencyFormatter = NumberFormat
				.getCurrencyInstance();*/		
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		//CommonValues.getInstance().LoginUser.UserLoginInfoID
		String order_url = String.format(
				CommonURL.getInstance().GetFavoriteList, getUserLoginID());

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				order_url, null, new Response.Listener<JSONObject>() {
					
					@Override
					public void onResponse(JSONObject response) {
						JSONArray jsonArray = null;
						try {
							jsonArray = response.getJSONArray("AllFaviorteList");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						if (response != null && jsonArray.length()!=0 ) {
							Boolean existInLocalDb;
							FavoriteListHolder userRoot = gson.fromJson(
									response.toString(),
									FavoriteListHolder.class);
							
							if (userRoot != null
									&& userRoot.favoriteList != null) {
								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
								for (int i = 0; i < userRoot.favoriteList.size(); i++) {
								int pID = userRoot.favoriteList.get(i).ProductID;
								if (!db.checkIfProductExist(pID)) {
									
									existInLocalDb = true;
									userRoot.favoriteList.get(i).ItemCountLocalDb = db.getTotalItem(pID);

								} else {
									userRoot.favoriteList.get(i).ItemCountLocalDb = 0;
									existInLocalDb = false;
								}
								
									imageItems
											.add(new FavoriteImageItem(
													userRoot.favoriteList.get(i).ProductName,
													userRoot.favoriteList
													.get(i).ProductID,
													userRoot.favoriteList
													.get(i).MPath,
													userRoot.favoriteList
													.get(i).Details,
													userRoot.favoriteList
													.get(i).Price,
													userRoot.favoriteList
													.get(i).IsFaviorite,userRoot.favoriteList.get(i).ItemCountLocalDb,userRoot.favoriteList.get(i).AlergimonicID
													));
								}
								favoriteListAdapter = new FavoriteListAdapter(
										MyFavouriteListActivity.this,R.layout.favourite_item,
										 imageItems);
								singleitemprice = userRoot.favoriteList
										.get(i).Price;
								url = userRoot.favoriteList
										.get(i).MPath;
								details = userRoot.favoriteList
										.get(i).Details;
							ItemName=	userRoot.favoriteList.get(i).ProductName;
								favoriteListView.setAdapter(favoriteListAdapter);
								
							} else {

							}
						}
						if (progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
						Toast.makeText(
								MyFavouriteListActivity.this,
								"You have no items in faviourite",
								Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(
								MyFavouriteListActivity.this,
								"No response from server",
								Toast.LENGTH_LONG).show();
					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
				DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}

	@Override
	public void onAddToCart(double cost) {
		// TODO Auto-generated method stub
		MyNetDatabase db = new MyNetDatabase(ctx);
		db.open();
		// Log.d(Tag, "total price: "+ getItemPrice());
		if (db.isTotalPriceExist()) {
			db.updateTotalCost("plus", cost);
			comObserver.getObserver().setValue(db.getTotalPrice());

		} else {
			comObserver.getObserver().setValue(cost);
			db.updateTotalCost("plus", cost);
		}
		db.close();
	}

	@Override
	public void onRemoveCart(double cost) {
		// TODO Auto-generated method stub
		MyNetDatabase db = new MyNetDatabase(ctx);
		db.open();
		if (db.isTotalPriceExist()) {
			db.updateTotalCost("minus", cost);
			comObserver.getObserver().setValue(db.getTotalPrice());
		}
		db.close();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		// TODO Auto-generated method stub
		final TextView tv = (TextView) view.findViewById(R.id.itemtext);
		pID = Integer.valueOf(String.valueOf(tv.getTag()));
		/*
		 * Intent modify_intent = new Intent(ProductListActivity.this,
		 * ProductDetailViewActivity.class);
		 * modify_intent.putExtra("PRODUCT_ID",
		 * Integer.parseInt(String.valueOf(pID)));
		 * modify_intent.putExtra("Category_ID",
		 * Integer.parseInt(String.valueOf(mdProductCategoryId)));
		 * modify_intent.putExtra("CategoryName",
		 * categoryName.getText().toString());
		 * modify_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		 * startActivity(modify_intent);
		 */

		// for new design

		dialog = new Dialog(this);
		dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_product_detail_view);
		sellingprice = (TextView) dialog.findViewById(R.id.sellingprice);
		detailsviewtext = (TextView) dialog.findViewById(R.id.detailsviewtext);
		imageplus = (ImageView) dialog.findViewById(R.id.imageplus);
		clickableLayout = (RelativeLayout) dialog.findViewById(R.id.clickableLayout);
		image = (ImageView) dialog.findViewById(R.id.imageplus);
		itemminus = (TextView) dialog.findViewById(R.id.itemminussingle);
		itemtext = (TextView) dialog.findViewById(R.id.itemtextsingle);
		vImage = (TextView) dialog.findViewById(R.id.vImage);
		nImage = (TextView) dialog.findViewById(R.id.nImage);
		gImage = (TextView) dialog.findViewById(R.id.gImage);
		prdName = (TextView) dialog.findViewById(R.id.prdName);
		mNetworkImageView = (ImageView) dialog.findViewById(R.id.getstartedfour1);
		getFeedBackList();
		dialog.show();
		itemcountlocaldb = db.getTotalItem(pID);
		Picasso.with(getApplicationContext()).load(url).placeholder(R.drawable.spoon)
		.error(R.drawable.spoon).into(mNetworkImageView);
		String cr = currencyFormatter.format(singleitemprice);
		sellingprice.setText(String.valueOf("" + cr));
		prdName.setText(ItemName);
		detailsviewtext.setText(details);
		dialog.setCancelable(true);

		dialog.show();
		dialog.setOnDismissListener(new OnDismissListener() {

			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				getFeedBackList();

			}
		});
		if (itemcountlocaldb > 0) {
			// itemtext.setVisibility(View.VISIBLE);
			itemminus.setVisibility(View.VISIBLE);
			itemCount = itemcountlocaldb;
			itemtext.setText("" + itemcountlocaldb);
		}
		if (itemcountlocaldb == 0) {
			// itemtext.setVisibility(View.INVISIBLE);
			itemminus.setVisibility(View.INVISIBLE);
			itemCount = itemcountlocaldb;
			itemtext.setText("" + itemcountlocaldb);
		}

		if (itemcountlocaldb == 1) {
			// itemtext.setVisibility(View.VISIBLE);
			itemminus.setVisibility(View.VISIBLE);
			itemtext.setText("" + itemcountlocaldb);

		}
		if (itemCount > 9) {
			// holder.minusImage.measure(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT);
			ViewGroup.LayoutParams params = itemtext.getLayoutParams();
			ViewGroup.LayoutParams minusParams = itemminus.getLayoutParams();
			minusParams.width = (int) getResources().getDimension(R.dimen.text_view_size);
			minusParams.height = (int) getResources().getDimension(R.dimen.text_view_size);
			itemminus.setLayoutParams(minusParams);
			params.width = minusParams.width;
			params.height = minusParams.height;
			itemtext.setLayoutParams(params);

			/*
			 * final float scale =
			 * getContext().getResources().getDisplayMetrics().density; int
			 * pixels = (int) (25 * scale + 0.5f);
			 * holder.itemCounter.setMaxWidth(pixels);
			 */
		} else {
			/*
			 * final float scale =
			 * getContext().getResources().getDisplayMetrics().density; int
			 * pixels = (int) (20 * scale + 0.5f);
			 * holder.itemCounter.setMaxWidth(pixels);
			 */
			ViewGroup.LayoutParams minusParams = itemminus.getLayoutParams();
			minusParams.width = (int) getResources().getDimension(R.dimen.text_view_size);
			minusParams.height = (int) getResources().getDimension(R.dimen.text_view_size);
			itemminus.setLayoutParams(minusParams);
			ViewGroup.LayoutParams params = itemtext.getLayoutParams();
			params.width = minusParams.width;
			params.height = minusParams.width;
			itemtext.setLayoutParams(params);
		}
		imageplus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

				tvsingleitem = (TextView) ((LinearLayout) v.getParent().getParent()).findViewById(R.id.itemtextsingle);

				TextView tv2 = (TextView) findViewById(R.id.itemminus);
				int prid = Integer.valueOf(pID);

				/*
				 * if (itemcountlocaldb>1) { itemCount = itemcountlocaldb; }
				 */
				itemCount++;
				if (itemCount == 1) {
					itemminus.setVisibility(View.VISIBLE);

					c.ProductID = pID;
					c.UserLoginInfoID = 1;
					c.RestaurantId = 1;
					Log.d(Tag, "not duplicate" + c.RestaurantId);
					c.Qty = itemCount;
					c.Cost = singleitemprice;

					c.Path = url;
					c.Title = prdName.getText().toString();
					db.createSignalStrenght(c);
					

				} else
					db.open();
				db.updateData(prid, itemCount);
				itemminus.setVisibility(View.VISIBLE);

				String o = (String) sellingprice.getText();
				onAddToCart(singleitemprice);
				db.close();
				tvsingleitem.setText("" + itemCount);
				//tv.setText("" +itemCount);
			}
		});

		itemminus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

				TextView tv2 = (TextView) findViewById(R.id.itemminus);
				int prid = Integer.valueOf(pID);

				String o = itemtext.getText().toString();
				db.open();
				if (itemCount > 1) {
					itemCount--;
					db.updateData(prid, itemCount);
					totalprice = (String) sellingprice.getText();
					onRemoveCart(singleitemprice);
					itemtext.setText("" + itemCount);

				} else if (itemCount == 1) {
					db.deleteRowByID(pID);
					onRemoveCart(singleitemprice);
					itemminus.setVisibility(View.GONE);
					itemtext.setText("0");

				} else if (itemtext.getText().toString().equals("1")) {
					db.deleteRowByID(pID);
					onRemoveCart(singleitemprice);
					itemminus.setVisibility(View.GONE);
					itemtext.setText("0");
					itemCount = 0;
				}
			}
		});

	}
}
