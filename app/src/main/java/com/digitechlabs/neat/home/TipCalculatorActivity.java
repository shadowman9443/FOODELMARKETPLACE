package com.digitechlabs.neat.home;

import java.text.NumberFormat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.dgtech.neat.R;

@SuppressLint("NewApi")
public class TipCalculatorActivity extends HeaderFooterActivity implements OnClickListener {

	TextView tvtotaltopay, tvtotalperperson, tvTotalTip, tvService, tvserviceTitle, tvSplitName, tvTotalTipPerPerson;
	EditText tvamount, peopleCounter;
	RatingBar manRatingBar, starRatingBar;
	int ratedValue = 0;
	Double billAmount = 0.0, service = 0.0, tipAmount = 0.0, starRatedValue = 0.0;
	ImageView ivManarrow, ivStararrow;
	final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.neat_tip_calculator);
		init();
	}

	@Override
	protected void onResume() {

		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	public void init() {
		manRatingBar = (RatingBar) findViewById(R.id.manRating);
		starRatingBar = (RatingBar) findViewById(R.id.starRating);
		tvamount = (EditText) findViewById(R.id.tvamount);
		tvtotaltopay = (TextView) findViewById(R.id.tvtotaltopay);
		tvtotalperperson = (TextView) findViewById(R.id.tvtotalperperson);
		tvTotalTip = (TextView) findViewById(R.id.tvTotalTip);
		tvTotalTipPerPerson = (TextView) findViewById(R.id.totalpriceamount);
		tvService = (TextView) findViewById(R.id.tvService);
		tvserviceTitle = (TextView) findViewById(R.id.tvserviceTitle);
		tvSplitName = (TextView) findViewById(R.id.tvSplitName);
		ivManarrow = (ImageView) findViewById(R.id.ivmanarrow);
		ivStararrow = (ImageView) findViewById(R.id.ivstararrow);
		/*
		 * manLayoutChildCount = manLayout.getChildCount(); startLayout =
		 * (LinearLayout) findViewById(R.id.starLayout); starLayoutCount =
		 * startLayout.getChildCount();
		 */

		tvamount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

				if (tvamount.getText().toString().isEmpty()) {
					billAmount = 0.0;
				} else {
					billAmount = Double.parseDouble(tvamount.getText().toString());
				}
				setTextAfterBillSet();
			}
		});

		tvamount.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					tvamount.setText(currencyFormatter.format((Double) (billAmount)));
					// code to execute when EditText loses focus
				}
			}
		});

		manRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

				ratedValue = (int) ratingBar.getRating();

				setTextOnView(ratedValue);

			}
		});
		starRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

				starRatedValue = (double) ratingBar.getRating() * 5;
				setTextOnServiceRating(starRatedValue);
			}
		});
		ivManarrow.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				openManCounter();
			}
		});
		ivStararrow.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				openServiceCounter();
			}
		});
	}

	String h;

	public void openServiceCounter() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.service_counter_dialog);
		dialog.setCancelable(true);
		dialog.show();
		final EditText service = (EditText) dialog.findViewById(R.id.serviceCount);
		h = service.getText().toString().trim();
		if (starRatedValue > 0.0) {
			service.setText("" + starRatedValue);
		}
		service.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (service.getText().toString().trim().equalsIgnoreCase("")) {
						starRatedValue = 0.00;
						setTextOnServiceRating(starRatedValue);
						dialog.dismiss();
					} else {
						starRatedValue = Double.parseDouble(service.getText().toString().trim());
						setTextOnServiceRating(starRatedValue);
						dialog.dismiss();
					}
				}
				return false;
			}
		});

		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

			public void onDismiss(DialogInterface dialog) {
				if (service.getText().toString().trim().equalsIgnoreCase("")) {
					starRatedValue = 0.00;
					setTextOnServiceRating(starRatedValue);
					dialog.dismiss();
				} else {
					starRatedValue = Double.parseDouble(service.getText().toString().trim());
					setTextOnServiceRating(starRatedValue);
					dialog.dismiss();

				}
			}

		});
	}

	public void openManCounter() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.man_counter_dialog);
		dialog.setCancelable(true);
		dialog.show();
		final EditText people = (EditText) dialog.findViewById(R.id.manCount);
		if (ratedValue > 0) {
			people.setText("" + ratedValue);
		}
		people.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (people.getText().toString().trim().equalsIgnoreCase("")) {
						ratedValue = 0;
						setTextOnView(ratedValue);
						dialog.dismiss();
					} else {
						ratedValue = Integer.parseInt(people.getText().toString().trim());
						setTextOnView(ratedValue);
						dialog.dismiss();
					}
				}
				return false;
			}

		});

		// service.setHint("Enter Service Raitng");
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

			public void onDismiss(DialogInterface dialog) {
				if (people.getText().toString().trim().equalsIgnoreCase("")) {
					ratedValue = 0;
					setTextOnView(ratedValue);
					dialog.dismiss();
				} else {
					ratedValue = Integer.parseInt(people.getText().toString().trim());
					setTextOnView(ratedValue);
					dialog.dismiss();
				}
			}

		});
		/*
		 * dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
		 * public void onDismiss(DialogInterface dialog) { // TODO
		 * Auto-generated method stub if (!people.getText().toString().isEmpty()
		 * || ratedValue > 0) { if (ratedValue > 5) { manRatingBar.setRating(0);
		 * }
		 * 
		 * if (ratedValue > 0) { ratedValue = 0; } else ratedValue =
		 * Integer.parseInt(people.getText() .toString()); setTextOnView(); } }
		 * });
		 */
	}

	public void setTextAfterBillSet() {
		tvtotaltopay.setText(currencyFormatter.format((Double) (billAmount)));
		tvtotalperperson.setText(currencyFormatter.format((Double) (billAmount / (ratedValue))));
		setTotalTip();
	}

	public void setTotalTip() {
		tipAmount = (Double) (billAmount * (starRatedValue / 100));
		tvTotalTip.setText(currencyFormatter.format(tipAmount));
		tvTotalTipPerPerson.setText(currencyFormatter.format(tipAmount / (ratedValue)));

	}

	public void setTextOnView(int ratedValue) {
		tvSplitName.setText(ratedValue + " People Split");
		tvtotaltopay.setText(currencyFormatter.format((Double) (billAmount)));
		if (ratedValue > 0) {
			tvtotalperperson.setText(currencyFormatter.format((Double) (billAmount / (ratedValue))));
			setTotalTip();

			if (ratedValue == 1) {
				manRatingBar.setRating(1);

			} else if (ratedValue == 2) {
				manRatingBar.setRating(2);

			} else if (ratedValue == 3) {
				manRatingBar.setRating(3);

			} else if (ratedValue == 4) {
				manRatingBar.setRating(4);

			} else if (ratedValue == 5 || ratedValue > 5) {
				manRatingBar.setRating(5);

			}
		}else{
			manRatingBar.setRating(0);
		}

	}

	public void setTextOnServiceRating(double starRatedValue) {
		tvService.setText(starRatedValue + "% Service:");
		setTotalTip();
		if (starRatedValue > 0 && starRatedValue <= 5) {
			starRatingBar.setRating(1);
			tvserviceTitle.setText("Not Good");
		} else if (starRatedValue > 5 && starRatedValue <= 10) {
			starRatingBar.setRating(2);
			tvserviceTitle.setText("Average");
		} else if (starRatedValue > 10 && starRatedValue <= 15) {
			starRatingBar.setRating(3);
			tvserviceTitle.setText("Good");
		} else if (starRatedValue > 15 && starRatedValue <= 20) {
			starRatingBar.setRating(4);
			tvserviceTitle.setText("Very Good");
		} else if (starRatedValue > 20 && starRatedValue >= 25) {
			starRatingBar.setRating(5);
			tvserviceTitle.setText("Excellent");
		} else if (starRatedValue == 0.0) {
			starRatingBar.setRating(0);
			tvserviceTitle.setText("Not Rated");
		}
	}

	

	

}
