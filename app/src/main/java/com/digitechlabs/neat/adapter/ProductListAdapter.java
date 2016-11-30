package com.digitechlabs.neat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dgtech.neat.R;
import com.digitechlabs.neat.entities.MDProductCategory;
import com.digitechlabs.neat.home.CategoryImageItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends ArrayAdapter<CategoryImageItem> {

	Context context;
	// List<MDProductCategory> rowItems;
	MDProductCategory jobDetail;
	List<CategoryImageItem> objects;
	ImageView imageView;
	private ImageLoader mImageLoader;
	ImageView image;
	TextView tvJobCode, tvJobDate, tvFromPostcode, tvtopostcode, tvJobStatusID,
			tvCost, tvDriveridt, tvCustomerId, tvJobStatus,perentID;
	TextView tvFromAddress, tvtoAddress, tvVia;
	private ArrayList<CategoryImageItem> data = new ArrayList<CategoryImageItem>();
	private int layoutResourceId;
	LayoutInflater lInflater;

	public ProductListAdapter(Context context, int layoutResourceId,
			List<CategoryImageItem> data) {
		super(context, layoutResourceId, data);
		context = context;
		objects = data;
		lInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = (ArrayList<CategoryImageItem>) data;

	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (view == null) {
			view = lInflater
					.inflate(R.layout.category_item_list, parent, false);
		}
		final CategoryImageItem rowx = data.get(position);
		CategoryImageItem p = getProduct(position);

		image = (ImageView) view.findViewById(R.id.image);

		tvFromAddress = (TextView) view.findViewById(R.id.tvFromAddress);
		tvtoAddress = (TextView) view.findViewById(R.id.tvtoAddress);
		perentID=(TextView)view.findViewById(R.id.perentID);

		tvFromAddress.setText(String.valueOf(p.getName()));
		tvtoAddress.setText(String.valueOf(p.getDescription()));
		perentID.setText(String.valueOf(p.getProductID()));
		Log.i("Parent Id",""+ p.getParentID());
		CategoryImageItem item = data.get(position);

		if(item.getUrl().equalsIgnoreCase("")){
			image.setBackgroundResource(R.drawable.spoon);
		}else {
			Picasso.with(context).load(item.getUrl())
					.placeholder(R.drawable.spoon)
					.error(R.drawable.spoon).into(image);
		}



	/*	image.setImageBitmap(item.getImage());

		mImageLoader = CustomVolleyRequestQueue.getInstance(context)
				.getImageLoader();
		mImageLoader.get(item.getUrl(), ImageLoader.getImageListener(image,
				android.R.drawable.ic_dialog_alert,
				android.R.drawable.ic_dialog_alert));
		image.setImageUrl(item.getUrl(), mImageLoader);
		image.setImageBitmap(item.getImage());

		mImageLoader = CustomVolleyRequestQueue.getInstance(context)
				.getImageLoader();
		mImageLoader.get(item.getUrl(), ImageLoader.getImageListener(image,
				android.R.drawable.ic_dialog_alert,
				android.R.drawable.ic_dialog_alert));
		image.setImageUrl(item.getUrl(), mImageLoader);*/
		int productID = rowx.getProductID();

		view.setTag(rowx);

		return view;
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public CategoryImageItem getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	CategoryImageItem getProduct(int position) {
		return ((CategoryImageItem) getItem(position));
	}

}
