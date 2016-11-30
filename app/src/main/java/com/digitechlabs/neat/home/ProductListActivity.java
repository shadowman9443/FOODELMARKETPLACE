package com.digitechlabs.neat.home;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.ListAdapter;
import com.digitechlabs.neat.base.CustomVolleyRequestQueue;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.entities.EndlessScrollListener;
import com.digitechlabs.neat.entities.Product;
import com.digitechlabs.neat.entities.ProductHolder;
import com.digitechlabs.neat.entities.ProductListHolder;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * 
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class ProductListActivity extends HeaderFooterActivity implements OnItemClickListener, ListAdapter.AdapterCallback {
	// private GridView gridView;
	// private GridViewAdapter customGridAdapter;
	LocalListingApplication comObserver;
	Gson gson, gsonUser = null;
	Button tvNoRecordFounq;
	EndlessScrollListener endlessScrollListener;
	final static String Tag = "DEBUG";
	int pageIndex = 1;
	ListView listView;
	int LoginUserNumber = 0;
	ListAdapter boxAdapter;
	FrameLayout priceLogo;
	TextView priceText;
	Bundle gsavedInstanceState = null;
	private ImageView mNetworkImageView;
	private ImageLoader mImageLoader;
	TextView tvMyCart, categoryName;
	public int i = 0, itemcountlocaldb = 0;
	TextView sellingprice, detailsviewtext, itemtext, itemminus, tvsingleitem, tvsingleitemminus, vImage, nImage,
			gImage, prdName;
	public TextView C1Image;
	public TextView C2Image;
	public TextView C3Image;
	public static int mdProductCategoryId = 0;
	public double itemCounter = 0;
	Context ctx;
	// String[] UserInfo;
	// change by jani
	String test;
	String totalprice;
	Dialog dialog;
	private int deliveryby = 1;
	ArrayList<Product> products = new ArrayList<Product>();
	public String[][] UserInfo;
	ImageView imageplus, image;
	RelativeLayout clickableLayout;
	int pID;
	public int itemCount = 0;
	PlaceOrderActivity placeorder = new PlaceOrderActivity();
	CustomerShoppingCart c = new CustomerShoppingCart();
	String url;
	double singleitemprice;
	final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

	// MyNetDatabase db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_list);

		gsavedInstanceState = savedInstanceState;
		tvNoRecordFounq = (Button) findViewById(R.id.tvNoRecordFounq);
		// clickableLayout = (RelativeLayout)findViewById(R.id.clickableLayout);
		priceText = (TextView) findViewById(R.id.pricetext);
		categoryName = (TextView) findViewById(R.id.starterText);
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		gson = new Gson();
		// listView.setAdapter(null);
		menuSelected();
		listView.setAdapter(boxAdapter);
		// getDataFromServer();

		if (savedInstanceState != null && savedInstanceState.containsKey("mdProductCategoryId")) {
			int productCategoryId = savedInstanceState.getInt("mdProductCategoryId");
			String categoryNameText = savedInstanceState.getString("CategoryName");
			categoryName.setText(categoryNameText);
			/*
			 * String productCategoryId = savedInstanceState
			 * .getString("mdProductCategoryId");
			 */
			mdProductCategoryId = productCategoryId;
			/*
			 * LoginUserNumber = savedInstanceState.getInt("LoginUserNumber",
			 * 0); if (LoginUserNumber > 0) {
			 * GetUserInfoByUserID(String.valueOf(LoginUserNumber)); }
			 */

			comObserver = (LocalListingApplication) getApplication();
			comObserver.getObserver().addObserver(this);

		}
	}

	@Override
	protected void onStop() {
		super.onStop(); // Always call the superclass method first

		// Save the note's current draft, because the activity is stopping
		// and we want to be sure the current note progress isn't lost.
		try {
			boxAdapter.clearAdaper();
			listView.setAdapter(null);
			ProductListActivity.this.finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Intent intent = new Intent(ProductListActivity.this, CategoryListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		}

	}

	@Override
	protected void onResume() {

		super.onResume();
		menuSelected();
		pageIndex = 1;
		getDataFromServer();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	void fillData() {
		for (int i = 1; i <= 20; i++) {
			products.add(new Product("Product " + i, i * 1, false));
		}
	}

	public double getItemPrice() {
		double result = 0;
		// int totalAmount = 0;

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

	@Override
	public void onAddToCart(double cost) {

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
	public void onRemoveCart(double price) {
		// TODO Auto-generated method stub
		MyNetDatabase db = new MyNetDatabase(ctx);
		db.open();
		if (db.isTotalPriceExist()) {
			db.updateTotalCost("minus", price);
			comObserver.getObserver().setValue(db.getTotalPrice());
		}
		db.close();
	}

	public void onIconClick(View v) {

	}

	public void showResult(View pressed) {

		MyNetDatabase myNetDatabase = new MyNetDatabase(this);
		myNetDatabase.open();

		ArrayList<CustomerShoppingCart> phoneList = myNetDatabase.getAddToCart();

		if (phoneList != null) {
			UserInfo = new String[phoneList.size()][8];

			for (int rowindex = 0; rowindex < phoneList.size(); rowindex++) {
				UserInfo[rowindex][0] = String.valueOf(phoneList.get(rowindex).CustomerShoppingCartID);
				UserInfo[rowindex][1] = String.valueOf(phoneList.get(rowindex).UserLoginInfoID);
				UserInfo[rowindex][2] = String.valueOf(phoneList.get(rowindex).ProductID);
				UserInfo[rowindex][3] = String.valueOf(phoneList.get(rowindex).Cost);
				UserInfo[rowindex][4] = String.valueOf(phoneList.get(rowindex).Qty);
				UserInfo[rowindex][5] = String.valueOf(phoneList.get(rowindex).SpicyLevelID);
				UserInfo[rowindex][6] = String.valueOf(phoneList.get(rowindex).TotalCost);
				UserInfo[rowindex][7] = String.valueOf(phoneList.get(rowindex).RestaurantId);

			}

			// }

			myNetDatabase.close();

		}
		gsonUser = new Gson();

		test = gsonUser.toJson(UserInfo);
		// CustomerOrder();
		Log.d(Tag, "" + gson);
	}

	private void getDataFromServer() {
		final ProgressDialog progressDialog = new ProgressDialog(ProductListActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);
		// MyNetDatabase localDb = new MyNetDatabase(ctx);
		db.open();
		/*
		 * final NumberFormat currencyFormatter = NumberFormat
		 * .getCurrencyInstance();
		 */
		final ArrayList<ProductImageItem> imageItems = new ArrayList<ProductImageItem>();

		progressDialog.setMessage("Loading....");
		progressDialog.show();
		String URL_Login = String.format(CommonURL.getInstance().GetProductByResturantID, AppConstant.RESTAURANTID,
				String.valueOf(pageIndex), mdProductCategoryId);
		Log.i("Url aAre ", URL_Login);

		try {
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_Login, null,
					new Response.Listener<JSONObject>() {
						// AlergimonicID
						@Override
						public void onResponse(JSONObject response) {
							if (response != null) {
								Boolean existInLocalDb;
								// int itemCountLocalDb = 0;
								listView = (ListView) findViewById(R.id.list);
								ProductListHolder userRoot = gson.fromJson(response.toString(),
										ProductListHolder.class);
								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
								if (userRoot != null && userRoot.productList != null) {
									// retrieve String drawable array
									TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);

									for (int i = 0; i < userRoot.productList.size(); i++) {

										Bitmap bitmap = BitmapFactory.decodeResource(
												ProductListActivity.this.getResources(), imgs.getResourceId(0, -1));
										String productName = userRoot.productList.get(i).ProductName;

										int pID = userRoot.productList.get(i).ProductID;
										if (!db.checkIfProductExist(pID)) {

											existInLocalDb = true;
											userRoot.productList.get(i).ItemCountLocalDb = db.getTotalItem(pID);

										} else {
											userRoot.productList.get(i).ItemCountLocalDb = 0;
											existInLocalDb = false;
										}

										final String value;
										if (productName == null || productName.length() <= 0) {
											value = "_";
										} else {
											value = productName;
										}

										imageItems.add(new ProductImageItem(bitmap, String.valueOf(value),
												userRoot.productList.get(i).ProductID,
												userRoot.productList.get(i).Price, userRoot.productList.get(i).MPath,
												userRoot.productList.get(i).IsRemoved,
												userRoot.productList.get(i).getImagePlus(),
												userRoot.productList.get(i).itemCount,
												userRoot.productList.get(i).AlergimonicID,
												userRoot.productList.get(i).Details, existInLocalDb,
												userRoot.productList.get(i).ItemCountLocalDb, null, 0, 0));
										// Log.d(Tag, "" +
										// userRoot.productList.get(i).Price);

									}
									// boxAdapter = new
									// ListAdapter(ProductListActivity.this,R.layout.row_grid,
									// imageItems);
									boxAdapter = new ListAdapter(ProductListActivity.this, LoginUserNumber, imageItems);

									listView.setAdapter(boxAdapter);
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

			jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

			// Adding request to volley request queue
			LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(ctx, "kisu ekta", 1000);
		}
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		TextView tv = (TextView) view.findViewById(R.id.itemtext);
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
		
		C1Image = (TextView) dialog.findViewById(R.id.C1Image);
		C2Image = (TextView) dialog.findViewById(R.id.C2Image);
		C3Image = (TextView) dialog.findViewById(R.id.C3Image);
		//prdName = (TextView) dialog.findViewById(R.id.prdName);
		mNetworkImageView = (ImageView) dialog.findViewById(R.id.getstartedfour1);
		getProductInfo();
		dialog.show();
		itemcountlocaldb = db.getTotalItem(pID);

		dialog.setCancelable(true);

		dialog.show();
		dialog.setOnDismissListener(new OnDismissListener() {

			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				getDataFromServer();

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

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public void getProductInfo() {

		String URL = "";
		URL = String.format(CommonURL.getInstance().GetProductByID, String.valueOf(pID));

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				if (response != null) {

					ProductHolder customerJobQuoteRoot = gson.fromJson(response.toString(), ProductHolder.class);
					if (customerJobQuoteRoot != null && customerJobQuoteRoot.productEntity != null) {
						Product productEntity = customerJobQuoteRoot.productEntity;
						String[] alergi = productEntity.AlergimonicID.split(",");
						for (String str : alergi) {
							str = str.trim();
							if (str.equals("V")) {
								vImage.setVisibility(View.VISIBLE);
							} else if (str.equals("G")) {
								gImage.setVisibility(View.VISIBLE);
							} else if (str.equals("N")) {
								nImage.setVisibility(View.VISIBLE);
							}
						}
						prdName.setText(productEntity.ProductName);
						detailsviewtext.setText(productEntity.Details);
						String cr = currencyFormatter.format(productEntity.Price);
						sellingprice.setText(String.valueOf("" + cr));

						singleitemprice = productEntity.Price;
						mImageLoader = CustomVolleyRequestQueue.getInstance(ProductListActivity.this).getImageLoader();
						url = productEntity.MPath;
						/*
						 * mImageLoader.get(url, ImageLoader
						 * .getImageListener(mNetworkImageView,
						 * R.drawable.spoon, R.drawable.spoon));
						 */

						/*
						 * mNetworkImageView .setImageUrl(url, mImageLoader);
						 */




						if(productEntity.MPath.equalsIgnoreCase("")){
							mNetworkImageView.setBackgroundResource(R.drawable.spoon);
						}else {
							Picasso.with(getApplicationContext()).load(url).placeholder(R.drawable.spoon)
									.error(R.drawable.spoon).into(mNetworkImageView);
						}



					} else {
						/*
						 * etNewpin.setError("Please enter new pin.");
						 * etoldpin.setError("Please enter old pin.");
						 */
					}
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(ProductListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
			}
		});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}
}
