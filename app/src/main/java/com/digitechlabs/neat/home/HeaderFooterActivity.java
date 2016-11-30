package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonConstraints;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.CommonValues;
import com.digitechlabs.neat.utils.GPSTracker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@SuppressLint("ClickableViewAccessibility")
public class HeaderFooterActivity extends AppCompatActivity
		implements Observer, OnClickListener, OnTouchListener, OnItemSelectedListener, OnItemClickListener {
	RelativeLayout linBase;
	LocalListingApplication myBase;
	TextView priceText, txthome, txtMEnu, txtcheckout, txtctellfriend;
	Context ctx;
	Activity currActivity;
	ArrayList<CustomerShoppingCart> shoppingCart;
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	public static String Tag = "DEBUG";
	private GPSTracker gpsTracker;
	public List<String> userTempInfosend = new ArrayList<String>();
	MyNetDatabase db;
	private boolean isMenuopen = false, openforOutside = false;
	public ImageView home_image;
	public ImageView menu_image;
	public ImageView checkout_image;
	public ImageView tellafriend_imageview;
	public ImageView ivsettings;
	public ImageView ivinfo, quran_menuber, drawer_arrow;
	int userLoginID, orderStatus;
	private LinearLayout ll_mydetails_dr, ll_order_history_dr, ll_myfeedback_dr, ll_myfaviourite_dr, ll_offer_dr,
			ll_aboutus_dr, ll_find_us, ll_opening_times, ll_gallery_dr, ll_tip_calulator_dr, ll_messagecenter_dr,
			ll_help,ll_log_out_dr, menu_li;
	private LinearLayout menu_linear;
	RelativeLayout lldrawer, rlheader, llhamber;
	float xAxis = 0f;
	float yAxis = 0f;

	float lastXAxis = 0f;
	float lastYAxis = 0f;
	ProgressDialog progressDialog;


	/*FOR FOODEL MARKETPLACE*/
     RelativeLayout llcheckout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.neat_base);
		linBase = (RelativeLayout) findViewById(R.id.linBase);
		myBase = (LocalListingApplication) getApplication();
		myBase.getObserver().addObserver(this);
		ctx = this;
		priceText = (TextView) findViewById(R.id.pricetext);
		openLocalDB();
		setPriceInFooter();
		gpsTracker = new GPSTracker(ctx);

		initUI();

	}
	public void showProgressDialog(String loadingText){
		progressDialog = new ProgressDialog(HeaderFooterActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage(loadingText);
		progressDialog.show();
		
	}
	public void closeProgressDialog(){
		progressDialog.dismiss();
		
	}
	private void initUI() {
		home_image = (ImageView) findViewById(R.id.home_image);
		menu_image = (ImageView) findViewById(R.id.menu_image);
		checkout_image = (ImageView) findViewById(R.id.checkout_image);
		tellafriend_imageview = (ImageView) findViewById(R.id.tellafriend_imageview);
		txthome = (TextView) findViewById(R.id.txthome);
		txtMEnu = (TextView) findViewById(R.id.txtMEnu);
		txtcheckout = (TextView) findViewById(R.id.txtcheckout);
		txtctellfriend = (TextView) findViewById(R.id.txtctellfriend);
		rlheader = (RelativeLayout) findViewById(R.id.rlheader);
		menu_li = (LinearLayout) findViewById(R.id.menu_li);



		/*FOR FOODEL MARKEPLACE*/

		llcheckout= (RelativeLayout)findViewById(R.id.llcheckout);

	}

	public void setPriceInFooter() {

		if (db.getTotalPrice() > 0.00) {
			ViewGroup.LayoutParams params = priceText.getLayoutParams();
			priceText.setVisibility(View.VISIBLE);
			String cr = currencyFormatter.format(db.getTotalPrice());
			priceText.setText((getResources().getString(R.string.pound)+"\n") + cr.replace(getResources().getString(R.string.pound), ""));
			if (priceText.getText().toString().length() > 7) {
				params.width = (int) getBaseContext().getResources().getDimension(R.dimen.basket_text_view_size) + 5;
				params.height = (int) getBaseContext().getResources().getDimension(R.dimen.basket_text_view_size) + 5;
				priceText.setLayoutParams(params);
			} else {
				params.width = (int) getBaseContext().getResources().getDimension(R.dimen.basket_text_view_size);
				params.height = (int) getBaseContext().getResources().getDimension(R.dimen.basket_text_view_size);
				priceText.setLayoutParams(params);
			}
		} else {
			priceText.setVisibility(View.GONE);
		}
	}

	public void openLocalDB() {

		db = new MyNetDatabase(getBaseContext());
		db.open();
	}

	@Override
	public void setContentView(int id) {
		LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(id, linBase);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		leftmenuPopulate();
		overridePendingTransition(0, 0);
		super.onResume();
		db = new MyNetDatabase(ctx);
		openLocalDB();
		
		
		setPriceInFooter();
		db.close();
		if (!CommonTask.isOnline(this)) {
			Toast.makeText(HeaderFooterActivity.this, "Your internet connection dry!!!, Please connect...",
					Toast.LENGTH_LONG).show();

		}
		userLoginID = getUserLoginID();
		orderStatus = getOrderStatus();

		/*
		 * menu_li.setOnTouchListener(new LinearLayout.OnTouchListener() {
		 * public boolean onTouch(View v, MotionEvent m) { // Perform tasks here
		 * //boolean test = ; if (dispatchTouchEvent(m) == false) { return
		 * false; } return true; } });
		 */
	}



	private int getOrderStatus() {
		db.open();
		int orderStatusID = db.getOrderStatus();
		db.close();
		return orderStatusID;
	}

	private int getUserLoginID() {
		db.open();
		int uID = db.getUserID();
		db.close();
		return uID;
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();
	}




	public void OnMenu(View pressed) {

	/*	Intent intent = new Intent(HeaderFooterActivity.this, CategoryListActivity.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);*/

		Toast.makeText(this,"we are on development stage",Toast.LENGTH_LONG).show();
	}

	public void OnCheckOut(View pressed) {
		/*shoppingCart = db.getAddToCart();
		if (shoppingCart.size() > 0) {
			Intent intent = new Intent(HeaderFooterActivity.this, PlaceOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		} else {
			Toast.makeText(HeaderFooterActivity.this, "No item in your cart. Please add item", Toast.LENGTH_LONG)
					.show();
			Intent intent = new Intent(HeaderFooterActivity.this, CategoryListActivity.class);

			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		}*/

		Toast.makeText(this,"we are on development stage",Toast.LENGTH_LONG).show();
	}

	public void Ontell(View pressed) {

		Intent intent = new Intent(HeaderFooterActivity.this, TellAFriendActivity.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}

	public void onAccount(View pressed) {
		Toast.makeText(this,"we are on development stage",Toast.LENGTH_LONG).show();

	}
	public void onBackArrow(View pressed) {
		onBackPressed();


	}
	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		// double t=8;
		// Log.d(Tag, "neat base getValue"+ myBase.getObserver().getValue());
		// NumberFormat nm = NumberFormat.getNumberInstance();
		// Double truncatedDouble=new
		// BigDecimal(myBase.getObserver().getValue()).setScale(3,
		// BigDecimal.ROUND_HALF_UP).doubleValue();
		if (myBase.getObserver().getValue() <= 0.0) {
			priceText.setVisibility(View.GONE);
		} else {
			priceText.setVisibility(View.VISIBLE);
		}
		ViewGroup.LayoutParams params = priceText.getLayoutParams();
		String cr = currencyFormatter.format(myBase.getObserver().getValue());
		priceText.setText("£\n" + cr.replace("£", ""));
		if (priceText.getText().toString().length() > 7) {
			params.width = (int) getBaseContext().getResources().getDimension(R.dimen.basket_text_view_size) + 5;
			params.height = (int) getBaseContext().getResources().getDimension(R.dimen.basket_text_view_size) + 5;
			priceText.setLayoutParams(params);
		} else {
			params.width = (int) getBaseContext().getResources().getDimension(R.dimen.basket_text_view_size);
			params.height = (int) getBaseContext().getResources().getDimension(R.dimen.basket_text_view_size);
			priceText.setLayoutParams(params);
		}

	}

	public void homeSelected() {
		home_image.setSelected(true);
		menu_image.setSelected(false);
		checkout_image.setSelected(false);
		tellafriend_imageview.setSelected(false);
		ivsettings.setSelected(false);
		// ivinfo.setSelected(false);

		txthome.setTextColor(Color.parseColor("#8B8052"));
	}

	public void menuSelected() {
		home_image.setSelected(false);
		menu_image.setSelected(true);
		checkout_image.setSelected(false);
		tellafriend_imageview.setSelected(false);
		ivsettings.setSelected(false);
		// ivinfo.setSelected(false);
		txtMEnu.setTextColor(Color.parseColor("#8B8052"));
	}

	public void checkoutSelected() {
		home_image.setSelected(false);
		menu_image.setSelected(false);
		checkout_image.setSelected(true);
		tellafriend_imageview.setSelected(false);
		ivsettings.setSelected(false);
		// ivinfo.setSelected(false);
		txtcheckout.setTextColor(Color.parseColor("#8B8052"));
	}

	public void tellafriendSelected() {

		menu_image.setSelected(false);
		checkout_image.setSelected(false);
		tellafriend_imageview.setSelected(true);

		txtctellfriend.setTextColor(Color.parseColor("#8B8052"));
	}

	public void settingsSelected() {
		home_image.setSelected(false);
		menu_image.setSelected(false);
		checkout_image.setSelected(false);
		tellafriend_imageview.setSelected(false);
		ivsettings.setSelected(true);
		// ivinfo.setSelected(false);

	}

	public void infoSelected() {
		home_image.setSelected(false);
		menu_image.setSelected(false);
		checkout_image.setSelected(false);
		tellafriend_imageview.setSelected(false);
		ivsettings.setSelected(false);
		// ivinfo.setSelected(true);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {


		}
	}
	private void deleteUserFromTable() {
		db.open();
		db.deleteUserFromTable();
		db.close();

	}
	private void leftmenuPopulate() {
		if (menu_li.getVisibility() == View.VISIBLE) {
			menu_li.setVisibility(View.GONE);
		}
	}



	/*
	 * private String TAG = GestureActivity.class.getSimpleName(); float
	 * initialX, initialY;
	 * 
	 * @Override public boolean onTouchEvent(MotionEvent event) {
	 * //mGestureDetector.onTouchEvent(event);
	 * 
	 * int action = event.getActionMasked();
	 * 
	 * switch (action) {
	 * 
	 * case MotionEvent.ACTION_DOWN: initialX = event.getX(); initialY =
	 * event.getY();
	 * 
	 * Log.d(TAG, "Action was DOWN"); break;
	 * 
	 * case MotionEvent.ACTION_MOVE: Log.d(TAG, "Action was MOVE"); break;
	 * 
	 * case MotionEvent.ACTION_UP: float finalX = event.getX(); float finalY =
	 * event.getY();
	 * 
	 * Log.d(TAG, "Action was UP");
	 * 
	 * if (initialX < finalX) { Log.d(TAG, "Left to Right swipe performed"); }
	 * 
	 * if (initialX > finalX) { Log.d(TAG, "Right to Left swipe performed"); }
	 * 
	 * if (initialY < finalY) { Log.d(TAG, "Up to Down swipe performed"); }
	 * 
	 * if (initialY > finalY) { Log.d(TAG, "Down to Up swipe performed"); }
	 * 
	 * break;
	 * 
	 * case MotionEvent.ACTION_CANCEL: Log.d(TAG,"Action was CANCEL"); break;
	 * 
	 * case MotionEvent.ACTION_OUTSIDE: Log.d(TAG,
	 * "Movement occurred outside bounds of current screen element"); break; }
	 * 
	 * return super.onTouchEvent(event); }
	 */

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * @Override public boolean onTouch(View v, MotionEvent event) { //
	 * Toast.makeText(this, "Touch!", 1000); if (event.getAction() ==
	 * MotionEvent.ACTION_DOWN) { // Toast.makeText(this, "Down!", 1000); return
	 * true; } return false; }
	 */
	@Override
	protected void onPause() {
		leftmenuPopulate();
		super.onPause();
	}

	/**
	 * Hides the soft keyboard
	 */
	public void hideSoftKeyboard() {
		if (getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}

	/**
	 * Shows the soft keyboard
	 */
	public void showSoftKeyboard(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		view.requestFocus();
		inputMethodManager.showSoftInput(view, 0);
	}

	public void onGlobalLayout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	public boolean IsActive(String s, String allvalue) {
		boolean result = false;

		List<String> list = Arrays.asList(allvalue.split(","));
		if (list.contains(s)) {
			result = true;
		}
		return result;
	}

}
