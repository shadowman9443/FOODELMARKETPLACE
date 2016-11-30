package com.digitechlabs.neat.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgtech.neat.R;
import com.digitechlabs.neat.utils.AppConstant;
import com.google.gson.Gson;

public class AboutUsActivity extends HeaderFooterActivity {

	TextView pointsnumber, worthprice;
	Gson gson, gsonUser = null;
	ImageView imgtablebook;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		gson = new Gson();
		imgtablebook = (ImageView)findViewById(R.id.imgtablebook);
		if (AppConstant.TABLERESERVATION.contentEquals("") || AppConstant.ASSIGNEDFEATURES.contentEquals("")) {
			imgtablebook.setVisibility(View.VISIBLE);
		} else if (IsActive(AppConstant.TABLERESERVATION, AppConstant.ASSIGNEDFEATURES)) {
			imgtablebook.setVisibility(View.VISIBLE);
		} else {
			imgtablebook.setVisibility(View.GONE);
		}
	}

	public void OnOrderFood(View Preessed) {
		Intent intent = new Intent(AboutUsActivity.this,
				CategoryListActivity.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}

	public void OnBookTable(View Preessed) {
		Intent intent = new Intent(AboutUsActivity.this,
				TableBookingActivity.class);

		// intent.putExtra("POINTS", SumPoints);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}

	public void OnCallUS(View Preessed) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel: " +
				AppConstant.PHONE));
		startActivity(callIntent);

	}

	public void OnMailUs(View Preessed) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_EMAIL,
				new String[] {AppConstant.EMAIL});
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT, "body of email");
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(AboutUsActivity.this,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}

	}
}
