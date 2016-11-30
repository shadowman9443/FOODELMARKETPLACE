package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;

public class Payment {

	@SerializedName("PaymentID")
    public int PaymentID;
	@SerializedName("JobDetailID")
    public int JobDetailID;
	@SerializedName("JobCode")
    public String JobCode;
	@SerializedName("TotalDue")
    public double TotalDue;
	@SerializedName("Cash")
    public double Cash;
	@SerializedName("Card")
    public double Card;
	@SerializedName("CardNo")
    public double CardNo;
	@SerializedName("Paypal")
    public double Paypal;
	@SerializedName("PaypalID")
    public String PaypalID;
	@SerializedName("PrePaidAccount")
    public double PrePaidAccount;
	@SerializedName("VoucherPromo")
    public double VoucherPromo;
	@SerializedName("VoucerPromoCode")
    public String VoucerPromoCode;
	@SerializedName("Others")
    public double Others;
	@SerializedName("TotalPaid")
    public double TotalPaid;
	@SerializedName("PaymentTime")
    public String PaymentTime;
	@SerializedName("CustomerID")
    public int CustomerID;
	@SerializedName("DrivedByDriverID")
    public int DrivedByDriverID;

    public Payment()
    {
    }
}
