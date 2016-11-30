package com.digitechlabs.neat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.home.PostCodeItem;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class postcodelistADAPTER extends ArrayAdapter<PostCodeItem> {
	private Context ctx;
	LayoutInflater lInflater;
	List<PostCodeItem> objects;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
	Date date;
	static String Month;

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public PostCodeItem getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	PostCodeItem getProduct(int position) {
		return ((PostCodeItem) getItem(position));
	}

	public class ViewHolder {

		public TextView postcode;
	

	}

	public postcodelistADAPTER(Context context, List<PostCodeItem> data) {
		super(context, 1, data);
		ctx = context;
		objects = data;
		lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (view == null) {
			view = lInflater.inflate(R.layout.postcode_item, parent, false);
			holder = new ViewHolder();
			holder.postcode = (TextView) view.findViewById(R.id.postcode);
			
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		PostCodeItem p = getProduct(position);

		
		holder.postcode.setText(p.getPostcode());
		view.setTag(p);
		return view;
	}

	

}
