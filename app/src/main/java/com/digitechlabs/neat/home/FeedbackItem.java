

package com.digitechlabs.neat.home;


public class 
FeedbackItem {

	public String deliveredDate,deliveredTime;
	public String getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public String getDeliveredTime() {
		return deliveredTime;
	}

	public void setDeliveredTime(String deliveredTime) {
		this.deliveredTime = deliveredTime;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Boolean getHasFeedBack() {
		return hasFeedBack;
	}

	public void setHasFeedBack(Boolean hasFeedBack) {
		this.hasFeedBack = hasFeedBack;
	}

	public double totalPrice;
    public Boolean hasFeedBack;
    public int customerOrderID;
	public int getCustomerOrderID() {
		return customerOrderID;
	}

	public void setCustomerOrderID(int customerOrderID) {
		this.customerOrderID = customerOrderID;
	}

	public 
	FeedbackItem(String deliveredDate, String deliveredTime, double totalPrice, Boolean hasFeedBack, int customerOrderID) {
		super();
		
		this.deliveredDate= deliveredDate;
		this.deliveredTime= deliveredTime;
		this.totalPrice = totalPrice;
		this.hasFeedBack= hasFeedBack;
		this.customerOrderID = customerOrderID;
		
	}

	
}
