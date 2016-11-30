package com.digitechlabs.neat.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dgtech.neat.R;
import com.digitechlabs.neat.home.GalleryImageitem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GalleryFullScreenImageAdapter extends PagerAdapter {

	private Activity _activity;
	private List<GalleryImageitem> itemOfferList;
	private LayoutInflater inflater;

	// constructor
	public GalleryFullScreenImageAdapter(Activity activity, ArrayList<GalleryImageitem> data) {
		this._activity = activity;
		itemOfferList = data;
	}

	@Override
	public int getCount() {
		return this.itemOfferList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		ImageView imgDisplay;
	

		inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.gallery_layout_fullscreen_image, container, false);

		
		imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
		
		Picasso.with(_activity).load(itemOfferList.get(position).getUrl()).placeholder(R.drawable.spoon)
				.error(R.drawable.spoon).into(imgDisplay);
		
		

		((ViewPager) container).addView(viewLayout);

		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((RelativeLayout) object);

	}

}
