package com.digitechlabs.neat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.home.ProductImageItem;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<ProductImageItem>implements OnClickListener {
	Context ctx;
	final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	Gson gson = null;
	LinearLayout llImageMinus;
	public static String Tag = "DEBUG";
	LayoutInflater lInflater;
	List<ProductImageItem> objects;
	ImageView imageView, itemMinus, itemNumber, nImage, vImage, gImage;
	TextView itemCount;
	MyNetDatabase db;
	// EditText etnotes;
	private ImageLoader mImageLoader;
	ImageView image;
	FrameLayout fLayout;
	private Context context;
	private int layoutResourceId;
	private ArrayList<ProductImageItem> data = new ArrayList<ProductImageItem>();
	private AdapterCallback mAdapterCallback;
	public int i = 0, j = 0, k = 0;
	public String[][] UserInfo, restaurantId, qty;
	String[] alergimonic;
	public String pID = null;
	int countItem = 0;
	CustomerShoppingCart c = new CustomerShoppingCart();

	public class ViewHolder {

		public TextView title;
		public TextView tvDetails;
		public TextView tvPrice;

		public TextView itemCounter;
		public ImageView favImage;
		public ImageView plusImage;
		public TextView nImage;
		public TextView vImage;
		public TextView gImage;
		public TextView minusImage;
		public TextView C1Image;
		public TextView C2Image;
		public TextView C3Image;
		public RelativeLayout minusItemLayout;
		public ImageView networkImage;
		int position;
		// ProductImageItem item;
	}

	public void clearAdaper() {
		data.clear();
		notifyDataSetInvalidated();
	}

	public ProductImageItem getItemByID(int id) {

		for (ProductImageItem pID : objects) {
			if (pID.productID == id)
				return pID;
		}
		return null;
	}

	public ListAdapter(Context context, int layoutResourceId, List<ProductImageItem> data) {
		super(context, layoutResourceId, data);
		ctx = context;
		objects = data;
		lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		db = new MyNetDatabase(ctx);
		db.open();
		this.data = (ArrayList<ProductImageItem>) data;

		try {
			this.mAdapterCallback = ((AdapterCallback) context);
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity must implement AdapterCallback.");
		}
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public ProductImageItem getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InlinedApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (view == null) {
			view = lInflater.inflate(R.layout.item, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.tvDescr);
			holder.tvDetails = (TextView) view.findViewById(R.id.tvDetails);
			holder.tvPrice = (TextView) view.findViewById(R.id.tvPrice);
			holder.itemCounter = (TextView) view.findViewById(R.id.itemtext);
			holder.nImage = ((TextView) view.findViewById(R.id.nImage));
			holder.vImage = ((TextView) view.findViewById(R.id.vImage));
			holder.gImage = ((TextView) view.findViewById(R.id.gImage));
			
			holder.C1Image = ((TextView) view.findViewById(R.id.C1Image));
			holder.C2Image = ((TextView) view.findViewById(R.id.C2Image));
			holder.C3Image = ((TextView) view.findViewById(R.id.C3Image));
			holder.plusImage = ((ImageView) view.findViewById(R.id.ivImage));
			holder.minusImage = ((TextView) view.findViewById(R.id.itemminus));
			holder.networkImage = ((ImageView) view.findViewById(R.id.image));
			holder.minusItemLayout = ((RelativeLayout) view.findViewById(R.id.clickableLayout));
			holder.position = position;
			// holder.item = data.get(holder.position);
			view.setTag(holder);
			holder.minusImage.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

		} else { // view recycling
					// row already contains Holder object
			holder = (ViewHolder) view.getTag();
		}
		ProductImageItem p = getProduct(position);
		String cr = currencyFormatter.format(p.getPrice());
		holder.tvPrice.setText(cr);
		String title = p.getTitle();
		if (title.length() > 26) {
			title = title.substring(0, 25) + "....";
		}
		// holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int)
		// getContext().getResources().getDimension(R.dimen.item_title_text_size));
		holder.title.setText(title);
		String productDetails;
		if (p.getProductDetails().toString().length() >= 110) {
			productDetails = p.getProductDetails().toString().substring(0, 110) + "....";
		} else {

			productDetails = p.getProductDetails().toString();
		}
		// holder.tvDetails.setText(productDetails);
		holder.tvDetails.setText(productDetails);
		// holder.tvDetails.setAlignment(Paint.Align.LEFT);
		alergimonic = String.valueOf(p.getAlergimonic()).split(",");
		holder.vImage.setVisibility(View.GONE);
		holder.gImage.setVisibility(View.GONE);
		holder.nImage.setVisibility(View.GONE);
		holder.C1Image.setVisibility(View.GONE);
		holder.C2Image.setVisibility(View.GONE);
		holder.C3Image.setVisibility(View.GONE);
		holder.itemCounter.setVisibility(View.INVISIBLE);
		holder.minusImage.setVisibility(View.INVISIBLE);

		for (String str : alergimonic) {
			str = str.trim();
			if (str.equals("V")) {
				holder.vImage.setVisibility(View.VISIBLE);
			} else if (str.equals("G")) {
				holder.gImage.setVisibility(View.VISIBLE);
			} else if (str.equals("N")) {
				holder.nImage.setVisibility(View.VISIBLE);
			}
			else if (str.equals("C1")) {
				holder.C1Image.setVisibility(View.VISIBLE);
			}
			
			else if (str.equals("C2")) {
				holder.C1Image.setVisibility(View.VISIBLE);
				holder.C2Image.setVisibility(View.VISIBLE);
			}
			else if (str.equals("C3")) {
				holder.C1Image.setVisibility(View.VISIBLE);
				holder.C2Image.setVisibility(View.VISIBLE);
				holder.C3Image.setVisibility(View.VISIBLE);
			}
		}

		if (p.itemCountFromLocalDb > 0) {
			holder.itemCounter.setVisibility(View.VISIBLE);
			holder.minusImage.setVisibility(View.VISIBLE);
			p.itemCount = p.itemCountFromLocalDb;
			holder.itemCounter.setText("" + p.itemCount);
		}

		if (p.itemCount == 1) {
			holder.itemCounter.setVisibility(View.VISIBLE);
			holder.minusImage.setVisibility(View.VISIBLE);
			holder.itemCounter.setText("" + p.itemCount);

		}
		if (p.itemCount > 9) {
			// holder.minusImage.measure(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT);
			ViewGroup.LayoutParams params = holder.itemCounter.getLayoutParams();
			ViewGroup.LayoutParams minusParams = holder.minusImage.getLayoutParams();
			minusParams.width = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
			minusParams.height = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
			holder.minusImage.setLayoutParams(minusParams);
			params.width = minusParams.width;
			params.height = minusParams.height;
			holder.itemCounter.setLayoutParams(params);

			/*
			 * final float scale =
			 * getContext().getResources().getDisplayMetrics().density; int
			 * pixels = (int) (25 * scale + 0.5f);
			 * holder.itemCounter.setMaxWidth(pixels);
			 */
		} else {
			/*
			 * final float scale =
			 * getContext().getResources().getDisplayMetrics().density; int
			 * pixels = (int) (20 * scale + 0.5f);
			 * holder.itemCounter.setMaxWidth(pixels);
			 */
			ViewGroup.LayoutParams minusParams = holder.minusImage.getLayoutParams();
			minusParams.width = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
			minusParams.height = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
			holder.minusImage.setLayoutParams(minusParams);
			ViewGroup.LayoutParams params = holder.itemCounter.getLayoutParams();
			params.width = minusParams.width;
			params.height = minusParams.width;
			holder.itemCounter.setLayoutParams(params);
		}

		if(p.getUrl().equalsIgnoreCase("")){
			holder.networkImage.setBackgroundResource(R.drawable.spoon);
		}else {
			Picasso.with(context).load(p.getUrl()).placeholder(R.drawable.spoon).error(R.drawable.spoon)
					.into(holder.networkImage);
		}

		
		/*
		 * holder.networkImage.setImageBitmap(p.getImage()); mImageLoader =
		 * CustomVolleyRequestQueue.getInstance(context) .getImageLoader();
		 * mImageLoader.get(p.getUrl(), ImageLoader.getImageListener(
		 * holder.networkImage, android.R.drawable.ic_dialog_alert,
		 * android.R.drawable.ic_dialog_alert));
		 * holder.networkImage.setImageUrl(p.getUrl(), mImageLoader);
		 */

		holder.itemCounter.setTag(p.productID);

		holder.plusImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				TextView tv = (TextView) ((LinearLayout) v.getParent()).findViewById(R.id.itemtext);
				TextView tvMinus = (TextView) ((LinearLayout) v.getParent()).findViewById(R.id.itemminus);
				int pID = Integer.valueOf(String.valueOf(tv.getTag()));
				ProductImageItem p = getItemByID(pID);
				p.itemCount++;
				if (p.itemCount == 1) {
					tv.setVisibility(View.VISIBLE);
					tvMinus.setVisibility(View.VISIBLE);
					c.ProductID = p.productID;
					c.UserLoginInfoID = 1;
					c.RestaurantId = 1;
					Log.d(Tag, "not duplicate" + c.RestaurantId);
					c.Qty = p.itemCount;
					c.Cost = p.price;
					c.Path = p.url;
					c.Title = p.title;
					db.createSignalStrenght(c);

				} else {
					p.itemCountFromLocalDb = p.itemCount;
				}
				// p.itemCount++;
				notifyDataSetChanged();
				db.updateData(p.productID, p.itemCount);
				mAdapterCallback.onAddToCart(p.price);
			}
		});

		holder.minusItemLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView tv = (TextView) ((LinearLayout) v.getParent()).findViewById(R.id.itemtext);

				if (tv.getVisibility() == View.VISIBLE) {
					TextView ivminus = (TextView) v.findViewById(R.id.itemminus);
					int pID = Integer.valueOf(String.valueOf(tv.getTag()));
					ProductImageItem p = getItemByID(pID);
					p.itemCount--;
					p.itemCountFromLocalDb = p.itemCount;
					notifyDataSetChanged();
					if ((tv.getText().toString().equals("1"))) {
						db.deleteRowByID(p.productID);
						p.setItemCount(0);
						tv.setVisibility(View.INVISIBLE);
						ivminus.setVisibility(View.INVISIBLE);
					}

					db.updateData(p.productID, p.itemCount);
					mAdapterCallback.onRemoveCart(p.price);
				}

			}
		});

		return view;
	}

	ProductImageItem getProduct(int position) {
		return ((ProductImageItem) getItem(position));
	}

	public void CartItem() {

		MyNetDatabase myNetDatabase = new MyNetDatabase(ctx);
		myNetDatabase.open();

		ArrayList<CustomerShoppingCart> phoneList = myNetDatabase.getAddToCart();
		if (phoneList != null) {
			UserInfo = new String[phoneList.size()][6];
			for (int rowindex = 0; rowindex < phoneList.size(); rowindex++) {

				for (int columindex = 0; columindex < 6; columindex++) {
					UserInfo[rowindex][columindex] = String.valueOf(phoneList.get(1));
					/*
					 * UserInfo[1] = String.valueOf(phoneList.get(2)) + ",";
					 * UserInfo[3] = String.valueOf(phoneList.get(3)) + ",";
					 * UserInfo[4] = String.valueOf(phoneList.get(4)) + ",";
					 * UserInfo[5] = String.valueOf(phoneList.get(5)) + ",";
					 * UserInfo[6] = String.valueOf(phoneList.get(6)) + ",";
					 */
				}
			}

			Log.d(Tag, "" + UserInfo[0][1]);

		}

		myNetDatabase.close();

	}

	/*
	 * private View.OnClickListener minus = new View.OnClickListener() {
	 * 
	 * @Override
	 * 
	 * public void onClick(View v) { View view = (View) v.getParent(); TextView
	 * tv = (TextView) view.findViewById(R.id.itemtextcounter);
	 * if(!(tv.getText().toString().equals("0"))){ countItem=
	 * Integer.parseInt(tv.getText().toString())-1; ImageView ivminus =
	 * (ImageView)view.findViewById(R.id.itemminus); ImageView ivnumber =
	 * (ImageView)view.findViewById(R.id.itemnumber);
	 * 
	 * if((tv.getText().toString().equals("1"))){
	 * db.deleteRowByID(getProduct((Integer) v.getTag()).productID);
	 * getProduct(((Integer) v.getTag())).setItemCount(0); countItem=0;
	 * //tv.setText("0"); tv.setVisibility(View.GONE);
	 * ivminus.setVisibility(View.GONE); ivnumber.setVisibility(View.GONE); }
	 * else{
	 * 
	 * // tv.setText("" + countItem); db.updateData(getProduct((Integer)
	 * v.getTag()).productID, countItem); }
	 * 
	 * tv.setText(""+countItem); getProduct((Integer) v.getTag()).IsRemoved=
	 * true; mAdapterCallback.onRemoveCart(getProduct((Integer)
	 * v.getTag()).price);
	 * 
	 * }
	 * 
	 * //db.close(); }
	 * 
	 * };
	 */
	public ArrayList<ProductImageItem> getBox() {
		ArrayList<ProductImageItem> box = new ArrayList<ProductImageItem>();
		for (ProductImageItem p : objects) {
			/* if (p.imageplus!=0) */
			box.add(p);
		}
		return box;
	}

	/*
	 * OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener()
	 * { public void onCheckedChanged(CompoundButton buttonView, boolean
	 * isChecked) { getProduct((Integer) buttonView.getTag()).IsRemoved =
	 * isChecked; } };
	 */

	public static interface AdapterCallback {
		// public double price;
		void onAddToCart(double cost);

		void onRemoveCart(double cost);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

		/*
		 * if(ctx instanceof ProductListActivity){
		 * ((ProductListActivity)ctx).addToCart(); }
		 */
		// getProduct((Integer) imageView.getTag()).productID= which;
		// imageView.setId(getProduct((Integer) imageView.getTag()).productID);
		// TODO Auto-generated method stub

	}

	/*
	 * public void addToCart(View v){ ImageView plusimage= (ImageView)
	 * view.findViewById(R.id.ivImage); //Toast.makeText(this, "changed",
	 * 0).show(); //i++; // showResult(); // Log.d(Tag, ""+ v.getId());
	 * //Toast.makeText(this, "changed" + i, 0).show(); //
	 * comObserver.getObserver().setValue(i);
	 * 
	 * }
	 */

}