package com.digitechlabs.neat.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.dgtech.neat.R;
import com.digitechlabs.neat.base.CustomVolleyRequestQueue;
import com.digitechlabs.neat.base.LocalListingApplication;
import com.digitechlabs.neat.base.MyNetDatabase;
import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.entities.CustomerShoppingCartListHolder;
import com.digitechlabs.neat.home.ProductImageItem;
import com.digitechlabs.neat.utils.CommonURL;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MyOrderListAdapter extends ArrayAdapter<ProductImageItem> implements
OnClickListener{
	Context ctx; 
	Gson gson = null;
	public static String Tag = "DEBUG";
	public static String debug = "Linkon";
	LayoutInflater lInflater;
	List<ProductImageItem> objects;
	ImageView imageView,  itemNumber;
	TextView itemCount,txtPlus, tvTotalPrice, itemMinus;
	EditText etnotes;
	private ImageLoader mImageLoader;
	int counter;
	Double truncatedDouble;
	NetworkImageView image;
	private Context context;
	private int layoutResourceId;
	private ArrayList<ProductImageItem> data = new ArrayList<ProductImageItem>();
	private MyOrderAdapterCallback mAdapterCallback;
	public int i = 0, j = 0, k = 0;
	public String[][] UserInfo, restaurantId, qty;
	CustomerShoppingCart customerShoppingCart = new CustomerShoppingCart();
	MyNetDatabase db;
	int currentlyFocusedRow;
	final NumberFormat currencyFormatter = NumberFormat
			.getCurrencyInstance();
	int countItem=0;
	public class ViewHolder {

		   public TextView title;
		   public TextView tvPrice;
		   public TextView tvTotal;
		   public TextView itemCounter;
		   public ImageView plusImage;
		   public TextView minusImage;
		   public EditText etNotes;
		   public NetworkImageView networkImage;
		   public RelativeLayout minusItemLayout;
		   int position;
		   ProductImageItem item;
	}
	public MyOrderListAdapter(Context context, int layoutResourceId, List<ProductImageItem> data) {
		super(context, layoutResourceId, data);
		ctx = context;
		objects = data;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		db = new MyNetDatabase(ctx);
		
		this.data = (ArrayList<ProductImageItem>) data;

		try {
			this.mAdapterCallback = ((MyOrderAdapterCallback) context);
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement AdapterCallback.");
		}
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		
		return objects.size();
	}

	@Override
	public ProductImageItem getItem(int position) {
		return objects.get(position);
	}
	public ProductImageItem getItemByID(int id){
		
		for (ProductImageItem pID : objects) {
			if(pID.productID==id) return pID;
		}
		return null;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	   public int getViewTypeCount() {

	    return getCount();
	   }

	   @Override
	   public int getItemViewType(int position) {

	    return position;
	   }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		
		if (view == null) {
			view = lInflater.inflate(R.layout.my_order_item, parent, false);
			holder= new ViewHolder();			
			holder.title = (TextView) view.findViewById(R.id.tvDescr);
			holder.tvPrice = (TextView) view.findViewById(R.id.tvPrice);
			holder.itemCounter= (TextView) view.findViewById(R.id.itemtext);
			holder.tvTotal= (TextView) view.findViewById(R.id.tvTotal);
			holder.plusImage= ((ImageView) view.findViewById(R.id.ivPlusImage));
			holder.etNotes= (EditText) view.findViewById(R.id.etnotes);
			holder.minusImage= ((TextView) view.findViewById(R.id.itemminus));
			holder.networkImage= ((NetworkImageView) view.findViewById(R.id.image));
			holder.minusItemLayout= ((RelativeLayout) view.findViewById(R.id.clickableLayout));
			holder.position= position;
			holder.item = data.get(holder.position);
			view.setTag(holder);
		}
		else{
			holder = (ViewHolder) view.getTag();
		}
		ProductImageItem p = getProduct(position);
		String cr = currencyFormatter.format(p.getPrice());
		holder.tvPrice.setText("Item "+cr);
		String title= p.getTitle();
		if(title.length()>17) {
			title= title.substring(0, 16)+ "..";
		}
		//holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) getContext().getResources().getDimension(R.dimen.item_title_text_size));
		holder.title.setText(title);	
		if (p.itemCount>=1) {
			holder.itemCounter.setVisibility(View.VISIBLE);
			holder.minusImage.setVisibility(View.VISIBLE);
						
		} 
		else{
			holder.itemCounter.setVisibility(View.INVISIBLE);
			holder.minusImage.setVisibility(View.INVISIBLE);
			
		}
		if (p.itemCount > 9) {
			ViewGroup.LayoutParams params = holder.itemCounter
					.getLayoutParams();
			ViewGroup.LayoutParams minusParams = holder.minusImage
					.getLayoutParams();
			minusParams.width = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
			minusParams.height = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
			holder.minusImage.setLayoutParams(minusParams);
			params.width = minusParams.width;
			params.height = minusParams.height;
			holder.itemCounter.setLayoutParams(params);
			/*
			 * holder.itemCounter.setLayoutParams( new
			 * LayoutParams(LayoutParams.WRAP_CONTENT,
			 * LayoutParams.WRAP_CONTENT));
			 */

		} else {
			ViewGroup.LayoutParams minusParams = holder.minusImage
					.getLayoutParams();
			minusParams.width = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
			minusParams.height = (int) getContext().getResources().getDimension(R.dimen.text_view_size);
			holder.minusImage.setLayoutParams(minusParams);
			ViewGroup.LayoutParams params = holder.itemCounter
					.getLayoutParams();
			params.width = minusParams.width;
			params.height = minusParams.width;
			holder.itemCounter.setLayoutParams(params);
		}
		holder.itemCounter.setText(""+p.itemCount);
		if(p.specialNotes!=null) holder.etNotes.setText(p.specialNotes);
		holder.tvTotal.setText("Total "+ currencyFormatter.format(new BigDecimal((Integer.parseInt(holder.itemCounter.getText().toString())*p
				.getPrice())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()));
		holder.item.getUrl();
		holder.networkImage.setImageBitmap(holder.item.getImage());
		mImageLoader = CustomVolleyRequestQueue.getInstance(context)
				.getImageLoader();
		mImageLoader.get(holder.item.getUrl(), ImageLoader.getImageListener(holder.networkImage,
				R.drawable.spoon,
				R.drawable.spoon));
		holder.networkImage.setImageUrl(holder.item.getUrl(), mImageLoader);
		holder.itemCounter.setTag(p.productID);
		//holder.minusImage.setTag(objects.indexOf(p));
		holder.etNotes.setTag(position);
		holder.plusImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				TextView tv= (TextView) ((LinearLayout)v.getParent()).findViewById(R.id.itemtext);
				int pID= Integer.valueOf(String.valueOf(tv.getTag()));
				ProductImageItem p= getItemByID(pID);
				p.itemCount++;
				notifyDataSetChanged();
				db.open();
				if(db.isDbEmpty()){
					customerShoppingCart.Title = p.title;
					customerShoppingCart.Qty = p.itemCount;
					customerShoppingCart.ProductID = p.productID;
					customerShoppingCart.Path = p.url;
					customerShoppingCart.Cost = p.price;
					db.insertProductIntoLocalDb(customerShoppingCart);
				}
				db.updateData(p.productID, p.itemCount);
				mAdapterCallback.onAddToCart(p.price);
				db.close();
			}
		});
		holder.minusItemLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView tv= (TextView) ((LinearLayout)v.getParent()).findViewById(R.id.itemtext);
				//TextView tvMinus= (TextView) ((LinearLayout)v.getParent()).findViewById(R.id.itemminus);
				if (tv.getVisibility() == View.VISIBLE) {
					TextView ivminus = (TextView) v
							.findViewById(R.id.itemminus);
					int pID = Integer.valueOf(String.valueOf(tv.getTag()));
					ProductImageItem p = getItemByID(pID);
					p.itemCount--;
					db.open();
					if ((tv.getText().toString().equals("1"))) {
						db.deleteRowByID(p.productID);
						p.setItemCount(0);
						tv.setVisibility(View.INVISIBLE);
						ivminus.setVisibility(View.INVISIBLE);
					}
					notifyDataSetChanged();
					db.updateData(p.productID, p.itemCount);
					mAdapterCallback.onRemoveCart(p.price);
					db.close();
				}
			}
		});


		holder.etNotes.setOnFocusChangeListener(new OnFocusChangeListener() {          

		        public void onFocusChange(View v, boolean hasFocus) {
		        	//currentlyFocusedRow= holder.position; 
		        	
		        	EditText specialNotes= (EditText) ((LinearLayout)v.getParent()).findViewById(R.id.etnotes);		
		        	TextView tv= (TextView) ((LinearLayout)v.getParent().getParent()).findViewById(R.id.itemtext);
					int pID= Integer.valueOf(String.valueOf(tv.getTag()));
					//int noteTag= Integer.valueOf(String.valueOf(specialNotes.getTag()));
					//specialNotes.requestFocus();
					//currentlyFocusedRow= pID;
		            if (!hasFocus) {
		            	
						ProductImageItem p= getItemByID(pID);
		            	db.open();
		            	p.specialNotes= specialNotes.getText().toString();
		            	notifyDataSetChanged();
		            	db.updateSpecialNotes(pID, p.specialNotes); 
		            	db.close();
		               // code to execute when EditText loses focus
		            }
		        }
		    });
		
		/*holder.etNotes.addTextChangedListener(new TextWatcher() {
         
            public void onTextChanged(CharSequence currentDigits, int start,
                    int before, int count) {
            	EditText specialNotes= (EditText) ((LinearLayout)v.getParent()).findViewById(R.id.etnotes);
            	TextView tv= (TextView) ((LinearLayout)v.getParent().getParent()).findViewById(R.id.itemtext);
				int pID= Integer.valueOf(String.valueOf(tv.getTag()));
				ProductImageItem p= getItemByID(pID);
            	db.open();
            	p.specialNotes= specialNotes.getText().toString();
            }
          
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
               
            }

            
            public void afterTextChanged(Editable s) {
               
            }
        });*/
		//view.setTag(item);
	
		
		return view;
	}

	
	ProductImageItem getProduct(int position) {
		return ((ProductImageItem) getItem(position));
	}

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

	public static interface MyOrderAdapterCallback {
		// public double price;
		void onAddToCart(double price);
		void refreshList();
		void onRemoveCart(double price);
		void setTotalPriceButton(double price);
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

	private void LoadData() {
		String URL = "";
		// URL =
		// String.format(CommonURL.getInstance().CreateCustomer,URLEncoder.encode(userCreateData,
		// "UTF8"));
		;

		URL = String.format(
				CommonURL.getInstance().GetAllProductCartByUserLoginInfoID,
				"1", String.valueOf(UserInfo));
		// URL=CommonURL.getInstance().GetCustomer;
		// JobDetailListHolder jobDetailHolder = (JobDetailListHolder) data;
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL,
				null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {

							CustomerShoppingCartListHolder userRoot = gson.fromJson(
									response.toString(),
									CustomerShoppingCartListHolder.class);
							if (userRoot != null
									&& userRoot.CustomerShoppingCartList != null) {

							} else {

								/*
								 * Toast.makeText(UserSignupActivity.this,
								 * "User creation failed",
								 * Toast.LENGTH_LONG).show();
								 */
							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						/*
						 * Toast.makeText(UserSignupActivity.this,
						 * error.getMessage(), Toast.LENGTH_LONG).show();
						 */
					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
				DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to volley request queue
		LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
	}
	

}
