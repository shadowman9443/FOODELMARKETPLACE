package com.digitechlabs.neat.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dgtech.neat.R;
import com.digitechlabs.neat.utils.GPSTracker;

public class InformationActivity extends HeaderFooterActivity {

	RelativeLayout lloffer, llAboutus, llFINDUS, llOPENINGTIMES, llGALLERY,
			llTIPCALCULATER;

	Context context;
	Button btnSubmit, btnCancel;
	private GPSTracker gpsTracker;

	// Onoffer, OnAboutus, OnFINDUS,OnOPENINGTIMES, OnGALLERY, OnTIPCALCULATER,

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		infoSelected();
		context=this;
		gpsTracker=new GPSTracker(context);

		init();
	
	}

	private void init() {
		// TODO Auto-generated method stub

		lloffer = (RelativeLayout) findViewById(R.id.lloffer);
		llAboutus = (RelativeLayout) findViewById(R.id.llAboutus);
		llFINDUS = (RelativeLayout) findViewById(R.id.llFINDUS);
		llOPENINGTIMES = (RelativeLayout) findViewById(R.id.llOPENINGTIMES);
		llGALLERY = (RelativeLayout) findViewById(R.id.llGALLERY);
		llTIPCALCULATER = (RelativeLayout) findViewById(R.id.llTIPCALCULATER);

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();


	}

	public void Onoffer(View pressed) {

		
		
		
		Intent intent = new Intent(InformationActivity.this,
				OfferNewActivity.class);
		/*
		 * intent.putExtra("CHECKOUTAMOUNT", "100");
		 */
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		
	}

	public void OnAboutus(View pressed) {

		Intent intent = new Intent(InformationActivity.this,
				AboutUsActivity.class);
		/*
		 * intent.putExtra("CHECKOUTAMOUNT", "100");
		 */
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	public void OnFINDUS(View pressed) {
		
		if(gpsTracker.canGetLocation()){
			try {
				
				Intent intent = new Intent(InformationActivity.this,
						GMAPLineDrawActivity.class);

				// * intent.putExtra("CHECKOUTAMOUNT", "100");

				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(InformationActivity.this,
					"Map Problem", Toast.LENGTH_LONG)
					.show();
		}
		}else {
			gpsTracker.showSettingsAlert();
		}
	

	}

	public void OnOPENINGTIMES(View pressed) {

			Intent intent = new Intent(InformationActivity.this,
					OpeningDeliveryActivity.class);
			/*
			 * intent.putExtra("CHECKOUTAMOUNT", "100");
			 */
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		

	}

	public void OnGALLERY(View pressed) {

		Intent intent = new Intent(InformationActivity.this,
				GalleryNewActivity.class);
		/*
		 * intent.putExtra("CHECKOUTAMOUNT", "100");
		 */
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}

	public void OnTIPCALCULATER(View pressed) {

		Intent intent = new Intent(InformationActivity.this,
				TipCalculatorActivity.class);
		/*
		 * intent.putExtra("CHECKOUTAMOUNT", "100");
		 */
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	
	@Override
	protected void onPostResume() {
		
		super.onPostResume();
		gpsTracker=new GPSTracker(context);
	
	}
}
