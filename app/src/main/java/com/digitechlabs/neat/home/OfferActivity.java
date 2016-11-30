package com.digitechlabs.neat.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.OfferListHolder;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OfferActivity extends HeaderFooterActivity{

	ImageView leftOffer1, leftOffer2, leftOffer3, rightOffer1, rightOffer2;
	Intent intent;
	MyNetDatabase localDb;
	Context context;
	RelativeLayout lefttOffer1Layout, leftOffer2Layout, leftOffer3Layout, rightOffer1Layout, rightOffer2Layout;
	Gson gson, gsonUser = null;
	TextView leftOffer1Header, leftOffer2Header, leftOffer3Header, leftOffer1Footer,leftOffer2Footer,leftOffer3Footer, 
	rightOffer1Header, rightOffer2Header, rightOffer1Footer,rightOffer2Footer;
	String header, footer, tc, imageURL;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_name);
		gson = new Gson();
		initControls();
		
	}
	
	public void initControls(){
		intent = getIntent();
		
		leftOffer1= (ImageView) findViewById(R.id.leftOffer1);
		leftOffer2= (ImageView) findViewById(R.id.leftOffer2);
		leftOffer3= (ImageView) findViewById(R.id.leftOffer3);
		rightOffer1= (ImageView) findViewById(R.id.rightOffer1);
		rightOffer2= (ImageView) findViewById(R.id.rightOffer2);
		leftOffer1Header= (TextView) findViewById(R.id.l1Offer1TextHeader);
		leftOffer2Header= (TextView) findViewById(R.id.l2Offer2TextHeader);
		leftOffer3Header= (TextView) findViewById(R.id.l3Offer3TextHeader);
		leftOffer1Footer= (TextView) findViewById(R.id.l1Offer1TextFooter);
		leftOffer2Footer= (TextView) findViewById(R.id.l2Offer2TextFooter);
		leftOffer3Footer= (TextView) findViewById(R.id.l3Offer3TextFooter);
		rightOffer1Header= (TextView) findViewById(R.id.r1Offer1TextHeader);
		rightOffer2Header= (TextView) findViewById(R.id.r2Offer2TextHeader);
		rightOffer1Footer= (TextView) findViewById(R.id.r1Offer1TextFooter);
		rightOffer2Footer= (TextView) findViewById(R.id.r2Offer2TextFooter);
		rightOffer1Layout= (RelativeLayout) findViewById(R.id.r1Offer);
		rightOffer2Layout= (RelativeLayout) findViewById(R.id.r2Offer);
		lefttOffer1Layout= (RelativeLayout) findViewById(R.id.l1Offer);
		leftOffer2Layout= (RelativeLayout) findViewById(R.id.l2Offer);
		leftOffer3Layout= (RelativeLayout) findViewById(R.id.l3Offer);
		context= rightOffer1.getContext();
		
		//rightOffer1.setImageBitmap(roundCornerImage(BitmapFactory.decodeResource(getResources(), R.drawable.offer3),100));
/*		Bitmap bMap= BitmapFactory.decodeResource(getResources(), 3);
		BitmapShader shader;
		shader = new BitmapShader(bMap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);

		RectF rect = new RectF(0.0f, 0.0f, width, height);

		// rect contains the bounds of the shape
		// radius is the radius in pixels of the rounded corners
		// paint contains the shader that will texture the shape
		canvas.drawRoundRect(rect, radius, radius, paint);*/		
		
	/*	Picasso.with(getApplicationContext())
        .load("../drawable/offer3.png")
        .transform(new RoundedTransformation(11, 0))
        .fit()
        .into(rightOffer1);*/
		
	}
	
	public Bitmap roundCornerImage(Bitmap src, float round) {
		  // Source image size
		  int width = src.getWidth();
		  int height = src.getHeight();
		  // create result bitmap output
		  Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		  // set canvas for painting
		  Canvas canvas = new Canvas(result);
		  canvas.drawARGB(0, 0, 0, 0);
		 
		  // configure paint
		  final Paint paint = new Paint();
		  paint.setAntiAlias(true);
		  paint.setColor(Color.BLACK);
		 
		  // configure rectangle for embedding
		  final Rect rect = new Rect(0, 0, width, height);
		  final RectF rectF = new RectF(rect);
		  // draw Round rectangle to canvas
		  canvas.drawRoundRect(rectF, round, round, paint);
		  // create Xfer mode
		  paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
		  // draw source image to canvas
		  canvas.drawBitmap(src, rect, rect, paint);
		  // return final image
		  return result;
		 }
	
	public void onLeftOffer1(View pressed){
		
		try {
			Intent intent = new Intent(OfferActivity.this,
					OfferDetailsActivity.class);
			intent.putExtra("OFFER_TITLE",
					leftOffer1Header.getText().toString());
			intent.putExtra("IMAGE_URL", leftOffer1.getTag().toString());
			intent.putExtra("OFFER_DESCRIPTION", leftOffer1Footer.getText().toString());
			intent.putExtra("OFFER_TC", lefttOffer1Layout.getTag().toString());
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void onLeftOffer2(View pressed){
		
		try {
			Intent intent = new Intent(OfferActivity.this,
					OfferDetailsActivity.class);
			intent.putExtra("OFFER_TITLE",
					leftOffer2Header.getText().toString());
			intent.putExtra("IMAGE_URL", leftOffer2.getTag().toString());
			intent.putExtra("OFFER_DESCRIPTION", leftOffer1Footer.getText().toString());
			intent.putExtra("OFFER_TC", leftOffer2Layout.getTag().toString());
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void onLeftOffer3(View pressed){
		try {
			Intent intent = new Intent(OfferActivity.this,
					OfferDetailsActivity.class);
			intent.putExtra("OFFER_TITLE",
					leftOffer3Header.getText().toString());
			intent.putExtra("IMAGE_URL", leftOffer3.getTag().toString());
			intent.putExtra("OFFER_DESCRIPTION", leftOffer3Footer.getText().toString());
			intent.putExtra("OFFER_TC", leftOffer3Layout.getTag().toString());
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onRightOffer1(View pressed){
		try {
			Intent intent = new Intent(OfferActivity.this,
					OfferDetailsActivity.class);
			intent.putExtra("OFFER_TITLE",
					rightOffer1Header.getText().toString());
			intent.putExtra("IMAGE_URL", rightOffer1.getTag().toString());
			intent.putExtra("OFFER_DESCRIPTION", rightOffer1Footer.getText().toString());
			intent.putExtra("OFFER_TC", rightOffer1Layout.getTag().toString());
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onRightOffer2(View pressed){
		try {
			Intent intent = new Intent(OfferActivity.this,
					OfferDetailsActivity.class);
			intent.putExtra("OFFER_TITLE",
					rightOffer2Header.getText().toString());
			intent.putExtra("IMAGE_URL", rightOffer2.getTag().toString());
			intent.putExtra("OFFER_DESCRIPTION", rightOffer2Footer.getText().toString());
			intent.putExtra("OFFER_TC", rightOffer2Layout.getTag().toString());
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getCurrentDate() {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    return sdf.format(cal.getTime());
	 }
	public void getOfferDataFromServer(){
		final ProgressDialog progressDialog = new ProgressDialog(
				OfferActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		/*final NumberFormat currencyFormatter = NumberFormat
				.getCurrencyInstance();*/		
		progressDialog.setMessage("Loading....");
		progressDialog.show();
		String URL_Login = String.format(
				CommonURL.getInstance().GetOfferByTime, getCurrentDate());

		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				URL_Login, null, new Response.Listener<JSONObject>() {
					// AlergimonicID
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
							
							OfferListHolder userRoot = gson.fromJson(
									response.toString(),
									OfferListHolder.class);
							
							if (userRoot != null
									&& userRoot.itemOfferList != null) {

								// retrieve String drawable array
								//userRoot.itemOfferList.get(0).ProductID,
								for (int i = 0; i < userRoot.itemOfferList.size(); i++) {

										if(userRoot.itemOfferList.get(i).OfferPosition.toString().equals("L1")){
											leftOffer1Header.setText(userRoot.itemOfferList.get(i).OfferTitle); 
											leftOffer1Footer.setText(userRoot.itemOfferList.get(i).Description);
											Picasso.with(context).load(userRoot.itemOfferList.get(i).OfferImageUrl)
											  .placeholder(R.drawable.spoon)
											  .error(R.drawable.spoon).into(leftOffer1);
											leftOffer1.setTag(userRoot.itemOfferList.get(i).OfferImageUrl);
											if(!userRoot.itemOfferList.get(i).OfferTermsCondition.equals("null")){
												lefttOffer1Layout.setTag(userRoot.itemOfferList.get(i).OfferTermsCondition);
											}
										}
										else if(userRoot.itemOfferList.get(i).OfferPosition.toString().equals("L2")){
											leftOffer2Header.setText(userRoot.itemOfferList.get(i).OfferTitle); 
											leftOffer2Footer.setText(userRoot.itemOfferList.get(i).Description);
											Picasso.with(context).load(userRoot.itemOfferList.get(i).OfferImageUrl)
											  .placeholder(R.drawable.spoon)
											  .error(R.drawable.spoon).into(leftOffer2);
											leftOffer2.setTag(userRoot.itemOfferList.get(i).OfferImageUrl);
											if(!userRoot.itemOfferList.get(i).OfferTermsCondition.equals("null")){
												leftOffer2Layout.setTag(userRoot.itemOfferList.get(i).OfferTermsCondition);
											}
										}
										else if(userRoot.itemOfferList.get(i).OfferPosition.toString().equals("L3")){
											leftOffer3Header.setText(userRoot.itemOfferList.get(i).OfferTitle); 
											leftOffer3Footer.setText(userRoot.itemOfferList.get(i).Description);
											Picasso.with(context).load(userRoot.itemOfferList.get(i).OfferImageUrl)
											  .placeholder(R.drawable.spoon)
											  .error(R.drawable.spoon).into(leftOffer3);
											leftOffer3.setTag(userRoot.itemOfferList.get(i).OfferImageUrl);
											if(!userRoot.itemOfferList.get(i).OfferTermsCondition.equals("null")){
											leftOffer3Layout.setTag(userRoot.itemOfferList.get(i).OfferTermsCondition);
											}
										}
										else if(userRoot.itemOfferList.get(i).OfferPosition.toString().equals("R1")){
											rightOffer1Header.setText(userRoot.itemOfferList.get(i).OfferTitle);
											rightOffer1Footer.setText(userRoot.itemOfferList.get(i).Description);
											Picasso.with(context).load(userRoot.itemOfferList.get(i).OfferImageUrl)
											  .placeholder(R.drawable.spoon)
											  .error(R.drawable.spoon).into(rightOffer1);
											rightOffer1.setTag(userRoot.itemOfferList.get(i).OfferImageUrl);
											if(!userRoot.itemOfferList.get(i).OfferTermsCondition.equals("null")){
											rightOffer1Layout.setTag(userRoot.itemOfferList.get(i).OfferTermsCondition);
											}
										}
										else if(userRoot.itemOfferList.get(i).OfferPosition.toString().equals("R2")){
											rightOffer2Header.setText(userRoot.itemOfferList.get(i).OfferTitle);
											rightOffer2Footer.setText(userRoot.itemOfferList.get(i).Description);
											Picasso.with(context).load(userRoot.itemOfferList.get(i).OfferImageUrl)
											  .placeholder(R.drawable.spoon)
											  .error(R.drawable.spoon).into(rightOffer2);
											rightOffer2.setTag(userRoot.itemOfferList.get(i).OfferImageUrl);
											if(!userRoot.itemOfferList.get(i).OfferTermsCondition.equals("null")){
											rightOffer2Layout.setTag(userRoot.itemOfferList.get(i).OfferTermsCondition);
											}
										}
									
								}
								
								if (progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
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
	
	@Override
	protected void onResume() {
		getOfferDataFromServer();
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
