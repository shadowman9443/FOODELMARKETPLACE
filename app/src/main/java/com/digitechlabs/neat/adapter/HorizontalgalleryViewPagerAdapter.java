package com.digitechlabs.neat.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.utils.ImageLoader;

public class HorizontalgalleryViewPagerAdapter extends PagerAdapter {

	Activity activity;
	int imageArray[];
	ImageLoader imageloader;
	String[] strings;
	LayoutInflater inflater;
	Context context;
	public HorizontalgalleryViewPagerAdapter(Context c,String[] strings) {
		// TODO Auto-generated constructor stub
		this.strings = strings;
		this.context = c;
	}

	

	public int getCount() {
		return strings.length;
	}

	public Object instantiateItem(ViewGroup collection, int position) {
	/*	ImageView view = new ImageView(activity);
		
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		view.setScaleType(ScaleType.FIT_XY);
		view.setBackgroundResource(Integer.parseInt(strings[position]));
		((ViewPager) collection).addView(view, 0);
		return view;
		
		
		ImageView imgflag;*/
		
		//ImageView imgView = new ImageView(context);
		ImageView imgflag;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.item_gallery, collection,
				false);
		imgflag = (ImageView) itemView.findViewById(R.id.imageNew);
		// Capture position and set to the ImageView
		//imgflag.setImageResource(Integer.parseInt(strings[position]));
		//imgflag.setImageResource(2);
		//imageloader.DisplayImage(strings[position], imgflag);
		// Add viewpager_item.xml to ViewPager
		((ViewPager) collection).addView(itemView);
 
		return itemView;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

}
