package com.digitechlabs.neat.home;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.squareup.picasso.Picasso;

public class OfferDetailsActivity extends HeaderFooterActivity{

	ImageView offerImage, crossImage;
	TextView offerTitle, offerDescription, offerTc;
	Intent intent;
	MyNetDatabase localDb;
	Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_details);
		initControls();
	
		/*localDb= new MyNetDatabase(getBaseContext());
		localDb.open();
		localDb.deleteCart();
		localDb.close();*/
	}
	
	@SuppressLint("NewApi")
	public void initControls(){
		intent = getIntent();
		offerImage= (ImageView) findViewById(R.id.offerImg);
		crossImage= (ImageView) findViewById(R.id.crossOffer);
		offerTitle= (TextView) findViewById(R.id.offerTitle);
		offerDescription= (TextView) findViewById(R.id.offerDescription);
		offerTc= (TextView) findViewById(R.id.tcText);
		Picasso.with(context).load(intent.getExtras().getString("IMAGE_URL"))
		  .placeholder(R.drawable.spoon)
		  .error(R.drawable.spoon).into(offerImage);
		offerTitle.setText(intent.getExtras().getString("OFFER_TITLE"));
		if(!intent.getExtras().getString("OFFER_TC").isEmpty()){
			offerDescription.setVisibility(View.VISIBLE);
			offerDescription.setText(intent.getExtras().getString("OFFER_DESCRIPTION"));
			offerTc.setText(intent.getExtras().getString("OFFER_TC"));
		}
		else{
			offerDescription.setVisibility(View.GONE);
			//offerDescription.setText(intent.getExtras().getString("OFFER_DESCRIPTION"));
			offerTc.setText(intent.getExtras().getString("OFFER_DESCRIPTION"));
		}
		
		//totalPrice = intent.getExtras().getString("TotalPrice");
/* Picasso.with(context)
 .load(com.app.utility.Constants.BASE_URL+b.image)
 .placeholder(R.drawable.profile)
 .error(R.drawable.profile)
 .transform(new RoundedTransformation(50, 4))
 .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
 .centerCrop()
 .into(v.im_user);*/
		
	}
	public void goToOfferPage(View v){
		Intent intent = new Intent(OfferDetailsActivity.this,
				OfferActivity.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onResume() {
	
		// TODO Auto-generated method stub
		super.onResume();
	}
	//@Override
/*	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}*/
}
