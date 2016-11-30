package com.digitechlabs.neat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.home.GalleryImageitem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HorizontalGalleryAdapter extends ArrayAdapter<GalleryImageitem> {
	private Context ctx;
	LayoutInflater lInflater;
	List<GalleryImageitem> objects;
	
	//private Context context;
	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public GalleryImageitem getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	GalleryImageitem getProduct(int position) {
		return ((GalleryImageitem) getItem(position));
	}

	public class ViewHolder {
		
		public ImageView networkImage;

	}
	

	public HorizontalGalleryAdapter(Context context, List<GalleryImageitem> data) {
		super(context, R.layout.item_gallery, data);
		ctx = context;
		objects = data;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (view == null) {
			GalleryImageitem p = getProduct(position);
			view = lInflater.inflate(R.layout.item_gallery, parent,
					false);
			holder = new ViewHolder();
			holder.networkImage = ((ImageView) view
					.findViewById(R.id.image));
			view.setTag(holder);
			Picasso.with(ctx).load(p.getUrl())
			  .placeholder(R.drawable.spoon)
			  .error(R.drawable.spoon).into(holder.networkImage);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		return view;
	}

}
