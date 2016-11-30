package com.digitechlabs.neat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.home.LoyaltyProductItem;

import java.sql.Date;
import java.util.List;

public class TrackOrderAdapter extends ArrayAdapter<LoyaltyProductItem> {
	private Context ctx;
	LayoutInflater lInflater;
	List<LoyaltyProductItem> objects;
	
	Date date;
	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public LoyaltyProductItem getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	LoyaltyProductItem getProduct(int position) {
		return ((LoyaltyProductItem) getItem(position));
	}

	public class ViewHolder {

		public TextView loyaltyDate;
		public TextView loyaltyTime;
		public TextView loyaltyPrice;
		public TextView loyaltypoints;

	}

	public TrackOrderAdapter(Context context, List<LoyaltyProductItem> data) {
		super(context, 1, data);
		ctx = context;
		objects = data;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (view == null) {
			view = lInflater.inflate(R.layout.track_order_item, parent,
					false);
			holder = new ViewHolder();
			holder.loyaltyDate = (TextView) view.findViewById(R.id.loyaltyDate);
			holder.loyaltyTime = (TextView) view.findViewById(R.id.loyaltyTime);
			holder.loyaltyPrice = (TextView) view.findViewById(R.id.loyaltyPrice);
			holder.loyaltypoints = (TextView) view
					.findViewById(R.id.loyaltypoints);
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		LoyaltyProductItem p = getProduct(position);
		holder.loyaltyDate.setText(String.valueOf(p.getLoyaltyDate()));
		holder.loyaltyTime.setText(p.getLoyaltyTime() + "");
		holder.loyaltyPrice.setText(p.getLoyaltyPrice() + "");
		holder.loyaltypoints.setText(p.getLoyaltyPoints() + "");
		holder.loyaltyDate.setTag(p.orderID);
		return view;
	}

}
