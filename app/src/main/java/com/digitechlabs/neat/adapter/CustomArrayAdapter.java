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
import com.digitechlabs.neat.home.Offeritem;
import com.squareup.picasso.Picasso;

import java.util.List;

/** An array adapter that knows how to render views when given CustomData classes */
public class CustomArrayAdapter extends ArrayAdapter<Offeritem> {
    private LayoutInflater mInflater;
    private ImageLoader imageloader;
    private List<Offeritem> itemOfferList;

    public CustomArrayAdapter(Context context, List<Offeritem> data) {
        super(context, R.layout.row_gallery);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemOfferList=data;
    }
	@Override
	public int getCount() {
		return itemOfferList.size();
	}

	@Override
	public Offeritem getItem(int position) {
		return itemOfferList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_gallery, parent, false);
            holder = new Holder();
            holder.offer_image = (ImageView) convertView.findViewById(R.id.offer_image);
            holder.offer_pos=(TextView)convertView.findViewById(R.id.offer_pos);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        
        Offeritem p = getItem(position);
        Log.i("dsadas asd asd", ""+p.getUrl());
        Picasso.with(getContext()).load(p.getUrl())
		  .placeholder(R.drawable.spoon)
		  .error(R.drawable.spoon).into(holder.offer_image);
       // holder.offer_pos.setText("OFFER "+(position+1));
        holder.offer_pos.setText(""+(p.getTitle()));
        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public ImageView offer_image;
        private TextView offer_pos;
    }
}
