package com.digitechlabs.neat.utils;


import com.digitechlabs.neat.entities.Customer;
import com.digitechlabs.neat.entities.UserLoginInfo;

/**
 * Singleton class
 * Use for initializing some common values used in application
 */

public class CommonValues {	
	
	public boolean IsServerConnectionError = false;	
	static CommonValues commonValuesInstance;
	public int ExceptionCode = CommonConstraints.NO_EXCEPTION;	
	public Long CollaborationMessageTime=0l;
	public UserLoginInfo LoginUser;
	public Customer customerInfo;
	public static String currency = "£";
	public String redeemPoints="";
	public String redeemAmounts="0";
	public String afterloyaltyPoints;
	public String additoanalinfo;
	public static String SERVER_DRY_CONNECTIVITY_MESSAGE ="Ayso cabita Server not responding, please try after sometimes.";
	public int acceptedOffer=0;
	public boolean isFirsttimeorderplace = false;
	public static String deliveryChargeValue = "0.50";
	public String deliveryCharge = "0.0";
	public String deliveryChargebelow = "1.0";
	public String deliveryChargebelowwithcard = "1.5";
	public static CommonValues getInstance() {		
		return commonValuesInstance;
	}

	
	public static void initializeInstance() {
		if (commonValuesInstance == null)
			commonValuesInstance = new CommonValues();
	}
	// Constructor hidden because of singleton
	private CommonValues(){
		
	}
}
