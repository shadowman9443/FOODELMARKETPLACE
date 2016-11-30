package com.digitechlabs.neat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgtech.neat.R;
import com.digitechlabs.neat.entities.CustomerOrderEntity;
import com.digitechlabs.neat.utils.CommonTask;

import java.text.ParseException;
import java.util.List;

public class CustomerWorkOrderListAdapter extends BaseAdapter {

	Context context;
	List<CustomerOrderEntity> rowItems;
	CustomerOrderEntity jobDetail;

	ImageView imageView;
	TextView tvJobCode, tvJobDate, tvFromPostcode, tvtopostcode, tvWorkOrderStatusID,
			tvCost, tvDriveridt, tvCustomerId, tvWorkOrderStatus;

	TextView tvFromAddress, tvtoAddress;

	public CustomerWorkOrderListAdapter(Context context, List<CustomerOrderEntity> items) {
		this.context = context;
		this.rowItems = items;

	}


	public View getView(int position, View convertView, ViewGroup parent) {

	
		jobDetail = rowItems.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View appBaseItemView = inflater.inflate(R.layout.work_order_item_list,
				null);
		tvFromAddress = (TextView) appBaseItemView
				.findViewById(R.id.tvFromAddress);
		tvtoAddress = (TextView) appBaseItemView.findViewById(R.id.tvtoAddress);
		tvWorkOrderStatus = (TextView) appBaseItemView.findViewById(R.id.tvWorkOrderStatus);
	
		tvFromAddress.setText(String.valueOf(jobDetail.CustomerOrderID));
		try {
			tvtoAddress.setText(CommonTask
					.toMessageTimeAsString(jobDetail.OrderDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	/*	New
Approved
Assigned
In Progress
Product Ready
On Shipment
Delivered
Paused
Canceled*/
		
		switch (jobDetail.MDCustomerOrderStatusID) {
		case 1:
			 
			tvWorkOrderStatus.setTextColor(Color.parseColor("#006666"));
			tvWorkOrderStatus.setText("New");
			break;
		case 2:
			tvWorkOrderStatus.setTextColor(Color.parseColor("#006666"));
			tvWorkOrderStatus.setText("Approved");
			break;
		case 3:
			tvWorkOrderStatus.setTextColor(Color.parseColor("#006666"));
			tvWorkOrderStatus.setText("Assigned");
			break;
		case 4:
			tvWorkOrderStatus.setTextColor(Color.parseColor("#006666"));
			tvWorkOrderStatus.setText("In Progress");
			break;
		case 5:
			tvWorkOrderStatus.setTextColor(Color.parseColor("#006666"));
			tvWorkOrderStatus.setText("Driver Rejected");
			break;
		case 6:
			tvWorkOrderStatus.setTextColor(Color.parseColor("#006666"));

			tvWorkOrderStatus.setText("Product Ready");
			
			break;
		case 7:
			tvWorkOrderStatus.setTextColor(Color.parseColor("#006666"));
			tvWorkOrderStatus.setText("On Shipment");
			
		
			break;
		case 8:
			tvWorkOrderStatus.setTextColor(Color.parseColor("#006666"));
			
			tvWorkOrderStatus.setText("Delivered");
			break;
		case 9:
			tvWorkOrderStatus.setTextColor(Color.parseColor("#C80000"));
			tvWorkOrderStatus.setText("Paused");
			
			
			break;
		case 10:
			tvWorkOrderStatus.setTextColor(Color.parseColor("#C80000"));
			tvWorkOrderStatus.setText("Canceled");
			
			break;
		
		}

		

	

		appBaseItemView.setTag(jobDetail);

		return appBaseItemView;
	}

	@Override
	public int getCount() {
		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItems.indexOf(getItem(position));
	}

}
