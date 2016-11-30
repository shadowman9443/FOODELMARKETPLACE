package com.digitechlabs.neat.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.utils.AppConstant;

public class OrderConfirmationActivity extends HeaderFooterActivity {

	TextView deliveryType, deliveryTime, paymentType, firstName, phone,
			totalPrice, email, lastName;
	Intent intent;
	MyNetDatabase localDb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_confirmation);
		initControls();

	}

	public void initControls() {
		intent = getIntent();

		deliveryType = (TextView) findViewById(R.id.deliveryType);
		deliveryTime = (TextView) findViewById(R.id.dtime);
		paymentType = (TextView) findViewById(R.id.paymenttypo);
		firstName = (TextView) findViewById(R.id.fnametext);
		lastName = (TextView) findViewById(R.id.lnametext);
		phone = (TextView) findViewById(R.id.teltext);
		totalPrice = (TextView) findViewById(R.id.totalnum);
		email = (TextView) findViewById(R.id.emailtxt);
		deliveryType.setText(intent.getExtras().getString("DeliveryMethod"));
		deliveryTime.setText(" @"
				+ intent.getExtras().getString("DeliveryTime"));
		paymentType.setText(intent.getExtras().getString("PaymentType"));
		firstName.setText(intent.getExtras().getString("FirstName") + " ");
		lastName.setText(intent.getExtras().getString("LastName"));
		phone.setText(intent.getExtras().getString("Phone"));
		totalPrice.setText("£" + intent.getExtras().getString("TotalPrice"));
		email.setText(intent.getExtras().getString("Email"));

	}

	public void onTapCall(View v) {

		Intent callIntent = new Intent(Intent.ACTION_CALL);

		callIntent.setData(Uri.parse("tel: " +
				AppConstant.PHONE));
	    startActivity(callIntent);	
	}

	@Override
	protected void onResume() {
		localDb = new MyNetDatabase(getBaseContext());
		localDb.open();
		localDb.deleteCart();
		localDb.close();
		if (!LocalListingApplication.getInstance().userTempInfo.isEmpty()) {

			LocalListingApplication.getInstance().userTempInfo
					.removeAll(LocalListingApplication.getInstance().userTempInfo);

		}

		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(OrderConfirmationActivity.this,
				HomePageActivity.class);
		/*
		 * intent.putExtra("CHECKOUTAMOUNT", "100");
		 */
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
}
