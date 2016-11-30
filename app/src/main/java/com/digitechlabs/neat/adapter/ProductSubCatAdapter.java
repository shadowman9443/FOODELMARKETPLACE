package com.digitechlabs.neat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.dgtech.neat.R;
import com.digitechlabs.neat.entities.ProductSubCatList;
import com.digitechlabs.neat.entities.ProductSubCatModel;

import org.json.JSONArray;

import java.io.File;

public class ProductSubCatAdapter extends ArrayAdapter<ProductSubCatModel> {
	Context context;

	public String response;
	public boolean asyncCheck = false;
	public String ContentCode;
	public String mobileNo;
	File pathName = null;
	JSONArray jsonArray = new JSONArray();
	private ListView mListView;
	AQuery mAQuery;
	public ProductSubCatAdapter(Context context) {
		super(context, R.layout.row_subcat, ProductSubCatList.getAllSubCatList());
		this.context = context;
		mAQuery = new AQuery(context);

	}

	static class ViewHolder {

		private TextView ps_title,ps_desc;
		//private JustifiedTextView ps_desc;
		private ImageView ps_image;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View v = convertView;

		if (v == null) {
			final LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_subcat, null);
			holder = new ViewHolder();
			holder.ps_title = (TextView) v.findViewById(R.id.ps_title);
			/*holder.ps_desc = (JustifiedTextView) v.findViewById(R.id.ps_desc);*/
			holder.ps_desc = (TextView) v.findViewById(R.id.ps_desc);
			holder.ps_image = (ImageView) v.findViewById(R.id.ps_image);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		if (position < ProductSubCatList.getAllSubCatList().size()) {

			final ProductSubCatModel query = ProductSubCatList.getAllSubCatList()
					.elementAt(position);
			holder.ps_title.setText(query.getName());
			
			
			String productDetails;
			if (query.getDescription().toString().length() >= 110) {
				productDetails = query.getDescription().toString().substring(0, 110)
						+ "...";
			} else {

				productDetails =  query.getDescription().toString();
			}
			holder.ps_desc.setText(productDetails);
			//holder.ps_desc.setAlignment(Paint.Align.LEFT);
			
			mAQuery.id(holder.ps_image).image(query.getPath(), true, true, 100, 100);

		}
		

		return v;
	}



}