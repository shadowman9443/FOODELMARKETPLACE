package com.digitechlabs.neat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.home.FeedbackItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FeedbackListAdapter extends ArrayAdapter<FeedbackItem> implements
		OnClickListener {
	Context ctx;
	final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	public static String Tag = "DEBUG";
	LayoutInflater lInflater;
	List<FeedbackItem> objects;
	private FeedbackAdapterCallback mAdapterCallback;
	private ArrayList<FeedbackItem> data = new ArrayList<FeedbackItem>();
	public class ViewHolder {

		public TextView feedbackDate;
		public TextView feedbackTime;
		public TextView itemPrice;
		public TextView feedbackCircle;
		public TextView feedbackArrow;
		int position;
	}
	public void clearAdaper(){
		data.clear();
		notifyDataSetInvalidated();
	}
	public FeedbackItem getItemByID(int id) {

		for (FeedbackItem pID : objects) {
			if (pID.customerOrderID == id)
				return pID;
		}
		return null;
	}

	public FeedbackListAdapter(Context context,
			List<FeedbackItem> data) {
		super(context, 1, data);
		ctx = context;
		objects = data;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.ctx = context;
		
		this.data = (ArrayList<FeedbackItem>) data;
		try {
			this.mAdapterCallback = ((FeedbackAdapterCallback) context);
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement FeedbackAdapterCallback.");
		}
		
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public FeedbackItem getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InlinedApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (view == null) {
			view = lInflater.inflate(R.layout.feedback_item, parent, false);
			holder = new ViewHolder();
			holder.feedbackDate = (TextView) view.findViewById(R.id.feedBackDate);
			holder.feedbackTime = (TextView) view.findViewById(R.id.feedBackTime);
			holder.itemPrice = (TextView) view.findViewById(R.id.feedBackPrice);
			holder.feedbackCircle = (TextView) view.findViewById(R.id.feedBackCircle);
			holder.feedbackArrow = (TextView) view.findViewById(R.id.feedBackArrow);
			holder.position = position;
			view.setTag(holder);
			holder.feedbackArrow.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
			
		} else { // view recycling
					// row already contains Holder object
			holder = (ViewHolder) view.getTag();
		}
		FeedbackItem p = getProduct(position);
		String cr = currencyFormatter.format(p.totalPrice);
		holder.feedbackDate.setText(p.deliveredDate);
		holder.feedbackTime.setText(p.deliveredTime);
		holder.itemPrice.setText(cr);
		if(p.hasFeedBack){
			holder.feedbackCircle.setBackgroundResource(R.drawable.round_bk_1);	
		}
		else{
			holder.feedbackCircle.setBackgroundResource(R.drawable.round_bk_2);
		}
		ViewGroup.LayoutParams params = holder.feedbackCircle
					.getLayoutParams();
		ViewGroup.LayoutParams minusParams = holder.feedbackArrow
					.getLayoutParams();
		minusParams.width = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
		minusParams.height = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
		holder.feedbackArrow.setLayoutParams(minusParams);
		params.width = minusParams.width;
		params.height = minusParams.height;
		holder.feedbackCircle.setLayoutParams(params);
		holder.feedbackArrow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView tvArrow = (TextView)
						((LinearLayout) v.getParent()).findViewById(R.id.feedBackArrow);
				int pID = Integer.valueOf(String.valueOf(tvArrow.getTag()));
				FeedbackItem p = getItemByID(pID);
				if(!p.hasFeedBack){
					TextView tvDate = (TextView)
							((LinearLayout) v.getParent()).findViewById(R.id.feedBackDate);
					TextView tvTime = (TextView)
							((LinearLayout) v.getParent()).findViewById(R.id.feedBackTime);
					TextView tvTotalPrice = (TextView)
							((LinearLayout) v.getParent()).findViewById(R.id.feedBackPrice);
					
					mAdapterCallback.onFeedbackPage(tvDate.getText().toString(), tvTime.getText().toString(),tvTotalPrice.getText().toString(), p.customerOrderID);
			}
								
			}
		});
		holder.feedbackArrow.setTag(p.customerOrderID);
		
		return view;
	}

	FeedbackItem getProduct(int position) {
		return ((FeedbackItem) getItem(position));
	}

	public static interface FeedbackAdapterCallback {
		// public double price;
		void onFeedbackPage(String date, String time, String totoalPrice, int customerOrderID);

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

	}



	
}