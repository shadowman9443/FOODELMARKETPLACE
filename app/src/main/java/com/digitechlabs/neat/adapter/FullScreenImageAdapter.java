package com.digitechlabs.neat.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.home.Offeritem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FullScreenImageAdapter extends PagerAdapter {

	private Activity _activity;
	private List<Offeritem> itemOfferList;
	private LayoutInflater inflater;

	// constructor
	public FullScreenImageAdapter(Activity activity, ArrayList<Offeritem> data) {
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
		TextView offer_title;
		ImageView imgDisplay;
		TextView order_tc;
		TextView offer_desc;

		inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);

		offer_title = (TextView) viewLayout.findViewById(R.id.offer_title);
		imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
		order_tc = (TextView) viewLayout.findViewById(R.id.order_tc);
		offer_desc = (TextView) viewLayout.findViewById(R.id.offer_desc);
		Picasso.with(_activity).load(itemOfferList.get(position).getUrl()).placeholder(R.drawable.spoon)
				.error(R.drawable.spoon).into(imgDisplay);
		offer_title.setText(itemOfferList.get(position).getTitle());
		offer_desc.setText(itemOfferList.get(position).getTermscondition());
		offer_title.bringToFront();
		Log.i("Terms con ", itemOfferList.get(position).getDescription());
		if (itemOfferList.get(position).getDescription().equalsIgnoreCase("")) {
			order_tc.setVisibility(View.GONE);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) offer_desc.getLayoutParams();
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			offer_desc.setLayoutParams(params);
		} else {
			order_tc.setText(itemOfferList.get(position).getDescription());
		}

		((ViewPager) container).addView(viewLayout);

		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((RelativeLayout) object);

	}

}
