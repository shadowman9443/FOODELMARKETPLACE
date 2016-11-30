package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.PlaceOrderListAdapter;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.entities.EndlessScrollListener;
import com.digitechlabs.neat.entities.Product;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonValues;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PlaceOrderActivity extends HeaderFooterActivity implements
		PlaceOrderListAdapter.PlaceOrderAdapterCallback, OnItemClickListener{
	LocalListingApplication comObserver;
	Gson gson,gsonUser = null;
	Button tvNoRecordFounq;
	EndlessScrollListener endlessScrollListener;
	final static String Tag = "DEBUG";
	Button btTotalPrice;
	int pageIndex = 1;
	ListView listView;
	int LoginUserNumber = 0;
	PlaceOrderListAdapter boxAdapter;
	Bundle gsavedInstanceState = null;
	ArrayList<CustomerShoppingCart> shoppingCart;
	private NetworkImageView mNetworkImageView;
	private ImageLoader mImageLoader;
	TextView tvMyCart, priceText;
	public int i = 0;
	public static int mdProductCategoryId = 0;
	public double itemCounter = 0;
	//String[] UserInfo;
	// change by jani
	FrameLayout priceLogo;
	Context ctx= this;
	String test;
	private int deliveryby=1;
	ArrayList<Product> products = new ArrayList<Product>();
	public String[][] UserInfo;
	String cr;
	MyNetDatabase localDb;
	public int offerID=0;
	
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_order_list);
		gsavedInstanceState = savedInstanceState;
		tvNoRecordFounq = (Button) findViewById(R.id.tvNoRecordFounq);
		
		checkoutSelected();
		btTotalPrice = (Button) findViewById(R.id.totalPrice);
		listView = (ListView) findViewById(R.id.order_list);
		priceText = (TextView) findViewById(R.id.pricetext);
		localDb= new MyNetDatabase(ctx);
		
		//listView.setOnItemClickListener(this);
		//priceLogo= (FrameLayout) findViewById(R.id.priceImg);
		//priceLogo.setVisibility(View.VISIBLE);
		getDataFromLocalDb();
		
		endlessScrollListener = new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {

				pageIndex++;
				getDataFromLocalDb();
			}
		};

		gson = new Gson();

		// change start by jani

		// fillData();

		// ListView lvMain = (ListView) findViewById(R.id.list);
		listView.setAdapter(boxAdapter);

		// registerLitsener();
		// Before change of category list
		/*
		 * if (savedInstanceState != null &&
		 * savedInstanceState.containsKey("LoginUserNumber")&&
		 * savedInstanceState.containsKey("mdProductCategoryId")) { String
		 * productCategoryId =
		 * savedInstanceState.getString("mdProductCategoryId");
		 * mdProductCategoryId = Integer.parseInt(productCategoryId);
		 * LoginUserNumber = savedInstanceState.getInt("LoginUserNumber", 0); if
		 * (LoginUserNumber > 0) {
		 * GetUserInfoByUserID(String.valueOf(LoginUserNumber)); } }
		 */
		if (savedInstanceState != null
				&& savedInstanceState.containsKey("mdProductCategoryId")) {
			int productCategoryId = savedInstanceState
					.getInt("mdProductCategoryId");
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

			
		}
		comObserver = (LocalListingApplication) getApplication();
		comObserver.getObserver().addObserver(this);
	}
	@Override
	protected void onResume() {

		super.onResume();
		checkoutSelected();
		pageIndex = 1;
		getDataFromLocalDb();
		/*if(CommonValues.getInstance().acceptedOffer==0){0
			showOfferDialog();
			
		}*/
		
	}
	private void showOfferDialog(){
		localDb.open();
		Double price = localDb.getTotalPrice();
		if(price>35){
			this.offerDialog(CommonConstraints.OFFERS.OFFER1); // free bottle of wine
		}
		else if(price>15 && price<=25){
			this.offerDialog(CommonConstraints.OFFERS.OFFER2); // free delivery
		}
		/*else if(price>25 && price<=35){
			this.offerDialog(OFFERS.OFFER5); // free starter
		}*/
		localDb.close();
		
		
	}
	private void offerDialog(final CommonConstraints.OFFERS offer){
		final Dialog dialog;
		dialog = new Dialog(this);
		dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_offer);
		RelativeLayout yes = (RelativeLayout) dialog.findViewById (R.id.leftYes);
		RelativeLayout no = (RelativeLayout) dialog.findViewById (R.id.rightNo);
		final ImageView img = (ImageView) dialog.findViewById(R.id.imgDisplay);
		final TextView title = (TextView) dialog.findViewById(R.id.offer_dialog_title);
		dialog.show();
		dialog.setCancelable(true);
		switch (offer) {
		case OFFER1:
			Picasso.with(ctx).load(R.drawable.offer_1).into(img);
	
			title.setText(CommonConstraints.OFFER1_TITLE);
			offerID = CommonConstraints.OFFER_ID_1;
			break;
		case OFFER2:
			Picasso.with(ctx).load(R.drawable.offer_2).into(img);
			title.setText(CommonConstraints.OFFER2_TITLE);
			offerID = CommonConstraints.OFFER_ID_2;
			break;
		case OFFER3:
			Picasso.with(ctx).load(R.drawable.offer_3).into(img);
			title.setText(CommonConstraints.OFFER3_TITLE);
			offerID = CommonConstraints.OFFER_ID_3;
			break;
		case OFFER4:
			Picasso.with(ctx).load(R.drawable.offer_4).into(img);
			title.setText(CommonConstraints.OFFER4_TITLE);
			offerID = CommonConstraints.OFFER_ID_4;
			break;
		case OFFER5:
			Picasso.with(ctx).load(R.drawable.offer_5).into(img);
			title.setText(CommonConstraints.OFFER5_TITLE);
			offerID = CommonConstraints.OFFER_ID_5;
			break;
		default:
			
			break;
			
		}
		yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(CommonValues.getInstance().acceptedOffer==0){
            		CommonValues.getInstance().acceptedOffer = offerID;
            	}
            	else{
            		Toast.makeText(ctx, "No offer", 1000);
            	}
            	dialog.dismiss();
                //Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
            }
        });
		no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	dialog.dismiss();
                //Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
            }
        });
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

	public void onPlaceOrder(View pressed){
		Intent modify_intent = new Intent(PlaceOrderActivity.this,
				OrderDetailsActivity.class);
		
		localDb.open();
		modify_intent.putExtra("TotalPrice", String.valueOf(localDb.getTotalPrice()));
		modify_intent
		.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
startActivity(modify_intent);
		localDb.close();
	}
	
	@Override
	public void onAddToCart(double price) {
		// Log.d(Tag, "override callback");
		// TODO Auto-generated method stub

		// Log.d(Tag, ""+ v.getId());
		// Toast.makeText(this, "changed" + i, 0).show();
		// itemCounter++;
		
		localDb.open();
		//Log.d(Tag, "total price: "+ getItemPrice());
		if(localDb.isTotalPriceExist()){
			localDb.updateTotalCost("plus", price);
			comObserver.getObserver().setValue(localDb.getTotalPrice());
		}
		cr = currencyFormatter.format(localDb.getTotalPrice());	
		btTotalPrice.setText("TOTAL "+ cr);
		localDb.close();
		//comObserver.getObserver().setValue(getItemPrice());

	}

	@Override
	public void onRemoveCart(double price) {
		// TODO Auto-generated method stub

		
		localDb.open();
		if(db.isTotalPriceExist()){
			localDb.updateTotalCost("minus", price);
			comObserver.getObserver().setValue(localDb.getTotalPrice());
		}
		cr = currencyFormatter.format(localDb.getTotalPrice());	
		btTotalPrice.setText("TOTAL "+ cr);
		localDb.close();
		//comObserver.getObserver().deductValue(getItemPrice());
	}
	@Override
	public void refreshList() {
		// TODO Auto-generated method stub
		//finish();
		//startActivity(getIntent());
		getDataFromLocalDb();
	}
	
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first

	    // Save the note's current draft, because the activity is stopping
	    // and we want to be sure the current note progress isn't lost.
	   PlaceOrderActivity.this.finish();
	}
	public void showResult(View pressed) {
		

		localDb.open();

		ArrayList<CustomerShoppingCart> phoneList = localDb
				.getAddToCart();

		if (phoneList != null) {
			UserInfo = new String[phoneList.size()][8];
			
				
				for(int rowindex= 0; rowindex <phoneList.size(); rowindex++ )
				{
					UserInfo[rowindex][0] = String.valueOf(phoneList.get(rowindex).CustomerShoppingCartID);
					UserInfo[rowindex][1] = String.valueOf(phoneList.get(rowindex).UserLoginInfoID);
					UserInfo[rowindex][2] = String.valueOf(phoneList.get(rowindex).ProductID);
					UserInfo[rowindex][3] = String.valueOf(phoneList.get(rowindex).Cost);
					UserInfo[rowindex][4] = String.valueOf(phoneList.get(rowindex).Qty);
					UserInfo[rowindex][5] = String.valueOf(phoneList.get(rowindex).SpicyLevelID);
					UserInfo[rowindex][6] = String.valueOf(phoneList.get(rowindex).TotalCost);
					UserInfo[rowindex][7] = String.valueOf(phoneList.get(rowindex).RestaurantId);				
			}
			
			

		//}

				localDb.close();

		
		}
		gsonUser = new Gson();
		
		test = gsonUser.toJson(UserInfo);
		//CustomerOrder();
		Log.d(Tag, ""+gson);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	
	@SuppressLint("Recycle")
	private void getDataFromLocalDb() {

		
		localDb.open();
		shoppingCart= localDb.getAddToCart();
		if(shoppingCart.size()>0){
		listView = (ListView) findViewById(R.id.order_list);
		cr = currencyFormatter.format(db.getTotalPrice());	
		btTotalPrice.setText("TOTAL "+ cr);
		ArrayList<ProductImageItem> imageItems = new ArrayList<ProductImageItem>();
		
		TypedArray imgs = getResources()
				.obtainTypedArray(R.array.image_ids);
		Log.d(Tag, "size: "+ shoppingCart.size());
		for (int i = 0; i < shoppingCart.size(); i++) {

			Bitmap bitmap = BitmapFactory
					.decodeResource(
							PlaceOrderActivity.this
									.getResources(),
							imgs.getResourceId(0, -1));
			String productName = shoppingCart.get(i).Title;
			final String value;
			
			if (productName == null
					|| productName.length() <= 0) {
				value = "_";
			} else{
				value = productName;
			}
			imageItems
					.add(new ProductImageItem(
							bitmap,
							String.valueOf(value),
							shoppingCart.get(i).ProductID,
							shoppingCart.get(i).Cost,
							shoppingCart.get(i).Path,
							false,
							1,
									shoppingCart.get(i).Qty, null, null, true,shoppingCart.get(i).Qty, 
									shoppingCart.get(i).SpecialNotes, 0,shoppingCart.get(i).IsFavorite));
						
		}
		boxAdapter = new PlaceOrderListAdapter(
				PlaceOrderActivity.this,
				LoginUserNumber, imageItems);
		listView.setAdapter(boxAdapter);
		localDb.close();
		}
		else{
			
			listView.setAdapter(null);
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
