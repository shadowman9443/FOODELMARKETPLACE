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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LoyaltyPointAdapter extends ArrayAdapter<LoyaltyProductItem> {
	private Context ctx;
	LayoutInflater lInflater;
	List<LoyaltyProductItem> objects;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
	Date date;
	static String Month;

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

	public LoyaltyPointAdapter(Context context, List<LoyaltyProductItem> data) {
		super(context, 1, data);
		ctx = context;
		objects = data;
		lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (view == null) {
			view = lInflater.inflate(R.layout.loyalty_history_item, parent, false);
			holder = new ViewHolder();
			holder.loyaltyDate = (TextView) view.findViewById(R.id.loyaltyDate);
			holder.loyaltyTime = (TextView) view.findViewById(R.id.loyaltyTime);
			holder.loyaltyPrice = (TextView) view.findViewById(R.id.loyaltyPrice);
			holder.loyaltypoints = (TextView) view.findViewById(R.id.loyaltypoints);
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		LoyaltyProductItem p = getProduct(position);

		String s = p.getLoyaltyDate();
		String[] parts = s.split("/"); // escape .
		String part1 = parts[0];
		String part2 = parts[1];
		String part3 = parts[2];
		String Monthvalue = result(part2);
		Double Calculatedpoints = Math.ceil(p.getLoyaltyPrice() * 5);
		String Calculatedpointsworth = Double.toString(p.getLoyaltyPrice() * 5 / 100);
		//	Math.ceil(p.getLoyaltyPrice() * 5 / 100);
		String loyaltydate = (part1 + Monthvalue + part3);
		holder.loyaltyDate.setText(loyaltydate);
		holder.loyaltyTime.setText(p.getLoyaltyTime() + "");
		holder.loyaltyPrice.setText(getContext().getResources().getString(R.string.pound)  +  round(Double.parseDouble(Calculatedpointsworth),2) + "");
		holder.loyaltypoints.setText(Calculatedpoints + "");
		holder.loyaltyDate.setTag(p.orderID);
		return view;
	}

	public String result(String value) {

		if (value.equalsIgnoreCase("Jan"))
			Month = "01";
		else if (value.equalsIgnoreCase("Feb"))
			Month = "02";
		else if (value.equalsIgnoreCase("Mar"))
			Month = "03";
		else if (value.equalsIgnoreCase("Apr"))
			Month = "04";
		else if (value.equalsIgnoreCase("May"))
			Month = "05";
		else if (value.equalsIgnoreCase("Jun"))
			Month = "06";
		else if (value.equalsIgnoreCase("Jul"))
			Month = "07";
		else if (value.equalsIgnoreCase("Aug"))
			Month = "08";
		else if (value.equalsIgnoreCase("Sep"))
			Month = "09";
		else if (value.equalsIgnoreCase("Oct"))
			Month = "10";
		else if (value.equalsIgnoreCase("Nov"))
			Month = "11";
		else if (value.equalsIgnoreCase("Dec"))
			Month = "11";

		return Month;
	}
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
