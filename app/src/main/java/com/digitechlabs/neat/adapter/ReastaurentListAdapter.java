package com.digitechlabs.neat.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgtech.neat.R;

/**
 * Created by PC on 07/11/2016.
 */
public class ReastaurentListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public ReastaurentListAdapter(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.restaurent_list_item, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.restaurent_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.bangEat);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageId);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
