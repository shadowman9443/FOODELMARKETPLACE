

package com.digitechlabs.neat.home;


public class 
LoyaltyProductItem {

	public String loyaltyDate,loyaltyTime;
	public double loyaltyPrice;
    public int loyaltyPoints;
    public int orderID;
	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public 
	LoyaltyProductItem(String loyaltyDate, String loyaltyTime, double loyaltyPrice, int loyaltyPoints, int orderID) {
		super();
		
		this.loyaltyDate= loyaltyDate;
		this.loyaltyTime= loyaltyTime;
		this.loyaltyPrice = loyaltyPrice;
		this.loyaltyPoints= loyaltyPoints;
		this.orderID = orderID;
		
	}

	public String getLoyaltyDate() {
		return loyaltyDate;
	}

	public void setLoyaltyDate(String loyaltyDate) {
		this.loyaltyDate = loyaltyDate;
	}

	public String getLoyaltyTime() {
		return loyaltyTime;
	}

	public void setLoyaltyTime(String loyaltyTime) {
		this.loyaltyTime = loyaltyTime;
	}

	public double getLoyaltyPrice() {
		return loyaltyPrice;
	}

	public void setLoyaltyPrice(double loyaltyPrice) {
		this.loyaltyPrice = loyaltyPrice;
	}

	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

}
