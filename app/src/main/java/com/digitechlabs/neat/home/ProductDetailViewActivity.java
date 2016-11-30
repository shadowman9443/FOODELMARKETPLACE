package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.CustomVolleyRequestQueue;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.entities.CustomerShoppingCartHolder;
import com.digitechlabs.neat.entities.CustomerShoppingCartListHolder;
import com.digitechlabs.neat.entities.Product;
import com.digitechlabs.neat.entities.ProductHolder;
import com.digitechlabs.neat.utils.CommonURL;
import com.digitechlabs.neat.utils.CommonValues;
import com.digitechlabs.neat.utils.JustifiedTextView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URLEncoder;

public class ProductDetailViewActivity extends HeaderFooterActivity {

	EditText etSellingPrice, etQty, etTotal, etComments;

	TextView tvProductName,priceText;
	
	
	JustifiedTextView productDetails;
	CheckBox cbIsmild, cbIsmideum, cbIshot, cbIsVia, cbIsExtrahot;
	Button btnSubmit, btnCancel, btnSpicy;
	boolean spycilevel = true;
	Spinner spSpicyLevelID;
	int overviewProductID = 0, categoryID=0;
	ProgressDialog progressDialog;
	TextView nImage, vImage, gImage;
	Gson gson = null;
	String alergimonic, categoryName;
	private NetworkImageView mNetworkImageView;
	private ImageLoader mImageLoader;
	boolean isRemoveFromCart = false;
	RatingBar rbproductRating;
	private int spicylevel;
	TextView priceLogo;
	//MyNetDatabase db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail_view);
		priceText = (TextView) findViewById(R.id.pricetext);
		menuSelected() ;
		if (savedInstanceState != null
				&& savedInstanceState.containsKey("PRODUCT_ID")) {
			overviewProductID = savedInstanceState.getInt("PRODUCT_ID");
			categoryID= savedInstanceState.getInt("Category_ID");
			categoryName= savedInstanceState.getString("CategoryName");
		}
		//openLocalDB();
		//setPriceInFooter();
		
		//initControls();
		
	}
	/*public void setPriceInFooter(){
		
		if(db.getTotalPrice()>0.00){
			
			priceText.setText(String.valueOf(db.getTotalPrice()));
		}
	}
	public void openLocalDB(){
		
		db= new MyNetDatabase(getBaseContext());
		db.open();
	}*/
	@Override
	protected void onResume() {
		super.onResume();

		
			initControls();
			btnSpicy.setVisibility(View.GONE);
		

	}
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first

	    // Save the note's current draft, because the activity is stopping
	    // and we want to be sure the current note progress isn't lost.
	   ProductDetailViewActivity.this.finish();
	}
	 @Override
	    public void onBackPressed() {
	            super.onBackPressed();
	            Intent intent = new Intent(ProductDetailViewActivity.this,
	    				ProductListActivity.class);
	            intent.putExtra("mdProductCategoryId",
	    				Integer.parseInt(String.valueOf(categoryID)));
	            intent.putExtra("CategoryName",
	    				categoryName);
	    		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    		startActivity(intent);
	    	    finish();
	          // finish();
	    		
	    }
	public void onClosePress(View pressed) {
		Intent intent = new Intent(ProductDetailViewActivity.this,
				ProductListActivity.class);
        intent.putExtra("mdProductCategoryId",
				Integer.parseInt(String.valueOf(categoryID)));
        intent.putExtra("CategoryName",
				categoryName);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	    finish();
	}

	

	private void initControls() {
		gson = new Gson();
		
		productDetails = (JustifiedTextView) findViewById(R.id.tvProductDetails);
		tvProductName = (TextView) findViewById(R.id.tvProductName);
		//priceLogo= (TextView) findViewById(R.id.pricetext);
		priceText.setVisibility(View.VISIBLE);
		etSellingPrice = (EditText) findViewById(R.id.etSellingPrice);
		etQty = (EditText) findViewById(R.id.etQty);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		mNetworkImageView = (NetworkImageView) findViewById(R.id.getstartedfour1);
		
		
		etTotal = (EditText) findViewById(R.id.etTotal);
		etComments = (EditText) findViewById(R.id.etComments);
		rbproductRating = (RatingBar) findViewById(R.id.rbproductRating);
		btnSpicy = (Button) findViewById(R.id.btnSpicy);
		nImage= ((TextView) findViewById(R.id.nImage));
		vImage= ((TextView) findViewById(R.id.vImage));
		gImage= ((TextView) findViewById(R.id.gImage));
		etQty.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				double sellingPrice = Double.parseDouble(etSellingPrice
						.getText().toString());
				if (s.length() > 0) {
					double quantity = Double.parseDouble(etQty.getText()
							.toString());
					double totalQuantity = sellingPrice * quantity;
					etTotal.setText(Double.toString(totalQuantity));
				} else {
					etTotal.setText(Integer.toString(0));
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				/**/
			}
		});
		etTotal.setKeyListener(null);
		getProductInfo();
	}

	public void onCancel(View pressed) {
		if (!isRemoveFromCart) {
			onBackPressed();
		} else {
			AddRemoveCart(2);
		}
	}

	public void getProductInfo() {
		progressDialog = new ProgressDialog(ProductDetailViewActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		String URL = "";
		URL = String.format(CommonURL.getInstance().GetProductByID,
				String.valueOf(overviewProductID));

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
							if(progressDialog.isShowing()){
								progressDialog.dismiss();
							}
							ProductHolder customerJobQuoteRoot = gson.fromJson(
									response.toString(), ProductHolder.class);
							if (customerJobQuoteRoot != null
									&& customerJobQuoteRoot.productEntity != null) {

								Product productEntity = customerJobQuoteRoot.productEntity;
								// productEntity.TootTip == 2 for selecting
								// spicy level

								if (productEntity.TootTip != null
										&& productEntity.TootTip.equals("2")) {

									btnSpicy.setVisibility(View.VISIBLE);
								}								
								//alergimonic= String.valueOf(productEntity.AlergimonicID);
								//alergimonic= String.valueOf(productEntity.AlergimonicID);
								String[] alergi= productEntity.AlergimonicID.split(",");
								for(String str: alergi){
									str= str.trim();
							         if(str.equals("V")){
							        	 vImage.setVisibility(View.VISIBLE);
							         }
							         else if(str.equals("G")){
							        	 gImage.setVisibility(View.VISIBLE);
							         }
							         else if(str.equals("N")){
							        	 nImage.setVisibility(View.VISIBLE);	        	 
							         }
							    }
								tvProductName
										.setText(productEntity.ProductName);
								productDetails.setText(productEntity.Details);
								productDetails.setAlignment(Paint.Align.LEFT);
								etSellingPrice.setText(String
										.valueOf(productEntity.Price));
								etQty.setText("1");
								/*getCartInfo(String
										.valueOf(productEntity.ProductID));*/

								mImageLoader = CustomVolleyRequestQueue
										.getInstance(
												ProductDetailViewActivity.this)
										.getImageLoader();

								String url = productEntity.Path;

								mImageLoader.get(
										url,
										ImageLoader
												.getImageListener(
														mNetworkImageView,
														R.drawable.spoon,
														R.drawable.spoon));

								mNetworkImageView
										.setImageUrl(url, mImageLoader);

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
						Toast.makeText(ProductDetailViewActivity.this,
								error.getMessage(), Toast.LENGTH_LONG).show();
					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
				DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}

	public void getCartInfo(String productID) {

		progressDialog = new ProgressDialog(ProductDetailViewActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("Processing....");
		progressDialog.show();
		String URL = "";
		URL = String.format(
				CommonURL.getInstance().GetProductCartByUserLoginInfoID,
				String.valueOf(4), String.valueOf(productID));

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							progressDialog.dismiss();

							CustomerShoppingCartHolder customerJobQuoteRoot = gson
									.fromJson(response.toString(),
											CustomerShoppingCartHolder.class);
							if (customerJobQuoteRoot != null
									&& customerJobQuoteRoot.customerShoppingCartEntity != null) {

								CustomerShoppingCart productEntity = customerJobQuoteRoot.customerShoppingCartEntity;

								/*
								 * if(productEntity.SpicyLevelID != 0) {
								 * onSpicy();
								 * 
								 * }
								 */

								isRemoveFromCart = true;
								etQty.setText(String.valueOf(productEntity.Qty));
								btnCancel.setText("Remove From Cart");

							} else {
							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(ProductDetailViewActivity.this,
								error.getMessage(), Toast.LENGTH_LONG).show();
					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
				DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);

	}

	@SuppressLint("NewApi")
	public void onSendJobRequest(View pressed) {
		int qty = 0;
		if (etQty.getText().toString().trim().isEmpty()) {
			etQty.setError("Qty can't be 0");
			return;
		}

		AddRemoveCart(1);

	}

	private void AddRemoveCart(int action) {
		// AddOrRemoveProductToCart?resturantID=%s&productID=%s&userLoginInfoID=%s&cartAction=%s&cost=%s&qty=%s&comment=%s&feedback=%s&spicylevel=%s";

		String URL_Login = String
				.format(CommonURL.getInstance().AddOrRemoveProductToCart,
						"1",
						String.valueOf(overviewProductID),
						String.valueOf(CommonValues.getInstance().LoginUser.UserLoginInfoID),
						String.valueOf(action), String.valueOf(etTotal
								.getText().toString().trim()), String
								.valueOf(etQty.getText().toString().trim()),
						URLEncoder.encode(String.valueOf(etComments.getText()
								.toString().trim())), URLEncoder.encode(String
								.valueOf(rbproductRating.getRating())), String
								.valueOf(spicylevel));

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				URL_Login, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							CustomerShoppingCartListHolder userRoot = gson.fromJson(
									response.toString(),
									CustomerShoppingCartListHolder.class);
							if (userRoot != null
									&& userRoot.CustomerShoppingCartList != null) {

								Toast.makeText(ProductDetailViewActivity.this,
										"Sucessfully Done", Toast.LENGTH_LONG)
										.show();

							

							} else {
								if (isRemoveFromCart) {
									Toast.makeText(
											ProductDetailViewActivity.this,
											"Sucessfully Done",
											Toast.LENGTH_LONG).show();
								
								}
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
