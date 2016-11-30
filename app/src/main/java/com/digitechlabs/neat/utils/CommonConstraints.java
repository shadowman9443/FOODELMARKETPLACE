package com.digitechlabs.neat.utils;



public class CommonConstraints {
	public static final int NO_EXCEPTION = -1;
	public static final int GENERAL_EXCEPTION = 0;
	public static final int CLIENT_PROTOCOL_EXCEPTION=1;
	public static final int ILLEGAL_STATE_EXCEPTION=2;
	public static final int UNSUPPORTED_ENCODING_EXCEPTION=3;
	public static final int IO_EXCEPTION=4;	
	public final static String EncodingCode = "UTF8";

	public static final String _2FINDLOCAL = "2findlocal.png";
	public static final String AUBIZ = "aubiz.png";
	public static final String AUSIEWEB = "ausieweb.png";
	public static final String BING = "bing.png";
	public static final String BLOO = "bloo.png";
	public static final String BROWNBOOK = "brownbook.png";
	public static final String CYLEX = "cylex.png";
	public static final String DLOOK = "dlook.png";
	public static final String FACEBOOK = "facebook.png";
	public static final String FACTUAL = "factual.png";
	public static final String FOURSQUARE = "foursquare.png";
	public static final String FYPLE = "fyple.png";
	public static final String GOOGLE = "google.png";
	public static final String HOTFROG = "hotfrog.png";
	public static final String LOCAL = "local.png";
	public static final String LOCALBUSINESSGUIDE = "localbusinessguide.png";
	public static final String LOCALDIRECTORIES = "localdirectories.png";
	public static final String LOCALSTORE = "localstore.png";
	public static final String MANATA = "manata.png";
	public static final String NEARYOUAU = "nearyouau.png";
	public static final String ODOVO = "odovo.png";
	public static final String OPENDI = "opendi.png";
	public static final String PINTEREST = "pinterest.png";
	public static final String POIDB = "poidb.png";
	public static final String SAVVYSME = "savvysme.png";
	public static final String SHOPSEEK = "shopseek.png";
	public static final String STARTLOCAL = "startlocal.png";
	public static final String SUPERPAGES = "superpages.png";
	public static final String TRUELOCAL = "truelocal.png";
	public static final String TUPALO = "tupalo.png";
	public static final String TWITTER = "twitter.png";
	public static final String WHITEPAGES = "whitepages.png";
	public static final String WOMO = "womo.png";
	public static final String YALWA = "yalwa.png";
	public static final String YELLOW = "yellow.png";
	public static final String YELP = "yelp.png";
	public static final String YOUTUBE = "youtube.png";
	public final static String FMP_LOGIN_USERPASS_SHAREDPREF_KEY = "login_user_pref";
	public final static String IsActivatedUser = "false";
	public final static int DEFAULT_COUNTRYID = 1;
	public final static int DEFAULT_CITYID = 1;
	   //Current User Collaboration Status
    public final static int USER_MESSAGE_COUNT = 20;
    
    public final static int USER_TYPE_DRIVER_ID = 1;
    public final static int USER_TYPE_CUSTOMER_ID = 2;
    public final static int USER_TYPE_COMPANY_ID = 3;
    
    public final static int CURRENCY_ID = 1;
    public final static int JOB_STATUS_ID = 1;
    
    public final static int ORDER_RECIEVED = 1;
    public final static int ORDER_BEING_COOKED = 2;
    public final static int COOKING_FINISHED = 3;
    public final static int ORDER_DISPATCHED = 4;
    public final static int  ORDER_DELIVERED = 5;
    
    public static final String OFFER1_TITLE = "Free Bottle of Wine";
    public static final String OFFER2_TITLE = "Free Delivery";
    public static final String OFFER3_TITLE = "10% Cash Discount";
    public static final String OFFER4_TITLE = "Banquet Night";
    public static final String OFFER5_TITLE = "Free Starter";
    
	public enum OFFERS {
		OFFER1(1), OFFER2(2), OFFER3(3), OFFER4(4), OFFER5(5);
		private int value;
		 
		private OFFERS(int value) {
			this.value = value;
		}
	}
	public final static int OFFER_ID_1 = OFFERS.OFFER1.value;
	public final static int OFFER_ID_2 = OFFERS.OFFER2.value;
	public final static int OFFER_ID_3 = OFFERS.OFFER3.value;
	public final static int OFFER_ID_4 = OFFERS.OFFER4.value;
	public final static int OFFER_ID_5 = OFFERS.OFFER5.value;
	
    public final static String FACEBOOKUSER = "facebook_user";
    
    public static CommonConstraints commonValuesInstance;
    public static CommonConstraints getInstance() {		
		return commonValuesInstance;
	}

}
