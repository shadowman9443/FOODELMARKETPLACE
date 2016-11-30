package com.digitechlabs.neat.utils;

/**
 * Singleton Class Use for initializing common URL values
 */

public class CommonURL {
	String applicationSecondBaseUrl="http://212.227.22.224:3000/";


	//String applicationBaseUrl="http://82.165.197.66:9091/";	

	//private String applicationBaseUrl="http://192.168.1.112:9091/";
	//private String applicationBaseUrl="http://88.150.177.113:9091/";	
	String applicationBaseUrl="http://212.227.22.224:9091/";	// main server
	String promoURL="http://212.227.22.224:3030/";
	public String SignUpGuestUser = applicationBaseUrl+"UserService/SignUpGuestUser?UserID=%s&Password=%s&userTypeID=%s";
	public String Login=applicationBaseUrl+"CompanyService/Login?userId=%s&password=%s";
	public String CreateUser=applicationBaseUrl+"CompanyService/CreateUser?userCreationData=%s";
	public String createProfileInformation=applicationBaseUrl+"CompanyService/createProfileInformation?profilerCreationData=%s";
	public String getAllProfileByUserId=applicationBaseUrl+"CompanyService/getAllProfileByUserId?userId=%s";
	public String getEmptyUser=applicationBaseUrl+"CompanyService/getEmptyUser";
	public String updateProfilePaidInfo=applicationBaseUrl+"CompanyService/updateProfilePaidInfo?userid=%s&profileid=%s";
	public String GetPromoCode=promoURL+"promocodegenerate?userid=%s&fromos=%s";
	public String SendPromoCode=applicationBaseUrl+"UserService/CheckPromotionalCode?promotionalcode=%s&UserId=%s";

	//public String OrderSend= applicationBaseUrl+"UserService/CustomerPlaceOrderNew?companyid=%s&product=%s&favproduct=%s&firstname=%s&lastname=%s&mobileNo=%s&email=%s&title=%s&door=%s&street=%s&address=%s&town=%s&postcode=%s&totalprice=%s&deliverymethod=%s&deliverytime=%s&paymentmethod=%s&password=%s";
	public String OrderSend= applicationBaseUrl+"UserService/CustomerPlaceOrder";
	public String CustomerPayment=applicationBaseUrl+"CustomerService/CustomerPayment?jobDetailID=%s&jobCode=%s&totalDue=%s&cash=%s&card=%s&cardNo=%s&paypal=%s&paypalID=%s&prePaidAccount=%s&voucherPromo=%s&voucerPromoCode=%s&others=%s&totalPaid=%s&paymentTime=%s&customerID=%s&drivedByDriverID=%s";
	public String VerifyUserCredentials=applicationBaseUrl+"Userservice/VerifyUserCredentialsCustomer?userID=%s&pin=%s&userTypeID=%s&restaurantID=%s";
	//public String VerifyUserCredentials=applicationBaseUrl+"Userservice/VerifyUserCredentials?userID=%s&pin=%s&userTypeID=%s";
	//public String GetCustomer=applicationBaseUrl+"CustomerService/GetCustomer";
	public String CreateCustomer=applicationBaseUrl+"UserService/CreateCustomer?companyID=%s&firstName=%s&middleName=%s&lastName=%s&mobileNo=%s&email=%s&addressLine1=%s&addressLine2=%s&postCode=%s&cityID=%s&countryID=%s&familySize=%s&paymentPreferenceID=%s&isSMSPreference=%s&receptEmail=%s&lastLatitude=%s&lastLongitude=%s&passcode=%s&isEmailPreference=%s&gcmid=%s";
	public String CreateCustomerviafacebook=applicationBaseUrl+"UserService/CreateCustomerviafacebook?companyID=%s&firstName=%s&middleName=%s&lastName=%s&mobileNo=%s&email=%s&addressLine1=%s&addressLine2=%s&postCode=%s&cityID=%s&countryID=%s&familySize=%s&paymentPreferenceID=%s&isSMSPreference=%s&receptEmail=%s&lastLatitude=%s&lastLongitude=%s&passcode=%s&isEmailPreference=%s&gcmid=%s";
	public String ChangePin = applicationBaseUrl+"UserService/ChangePin?userLoginInfoID=%s&oldPin=%s&newPin=%s";
	public String TempUserPasscodeVerification = applicationBaseUrl+"UserService/TempUserPasscodeVerification?userID=%s&password=%s&userTypeID=%s";
	public String CustomerJobQuote = applicationBaseUrl+"CustomerService/CustomerJobQuote?customerID=%s&jobDate=%s&fromAddress=%s&fromPostcode=%s&toAddress=%s&toPostcode=%s&via=%s&viaPostcode=%s&distance=%s&cost=%s&currencyID=%s&jobStatusID=%s&passengerNo=%s&lagguage=%s&childSeat=%s&vehicleTypeID=%s&TableInfo=%s";
	//public String JobDetail = applicationBaseUrl+"DriverService/Jobdetail?jobCode=%s&jobQuoteID=%s&driverID=%s&customerID=%s&jobDate=%s&jobStartFrom=%s&fromPostcode=%s&fromLatitude=%s&fromLongtitude=%s&jobDestinationTo=%s&toPostcode=%s&toLatitude=%s&toLongtitude=%s&isReturn=%s&distance=%s&cost=%s&currencyID=%s&jobStatusID=%s&jobAccepted=%s&wayToPickup=%s&arrivedToPickup=%s&started=%s&completed=%s&cancelTime=%s&cancelReason=%s&customerInstructionToDriver=%s&companyInstructionToDriver=%s&paymentType=%s&passengerNo=%s&lagguage=%s&childseat=%s&vehicleTypeID=%s&vehicleRegistrationNo=%s";
	public String GetDriverInfoByDriverID=applicationBaseUrl+"DriverService/GetDriverInfoByDriverID?Driverid=%s";
	public String JobRating=applicationBaseUrl+"CustomerService/JobRating?jobDetailID=%s&puncuality=%s&cleanliness=%s&friendlyness=%s&roadKnowledge=%s&service=%s&valueforMoney=%s&comments=%s&ratedByCustomerID=%s&ratedToDriverID=%s";
	public String GetNearbyDriverLocation=applicationBaseUrl+"CustomerService/GetNearbyDriverLocation?latitiude=%s&longtitude=%s";
	public String GetJobDetailByJobID=applicationBaseUrl+"DriverService/GetJobDetailByJobID?jobDetailID=%s";
	public String GetJobHistoryByDriver=applicationBaseUrl+"DriverService/GetJobHistoryByDriver?driverID=%s";
	public String GetJobQuotationByCustomer=applicationBaseUrl+"CustomerService//GetJobQuotationByCustomer?customerID=%s";
	public String SetLiveCollaborations =  applicationBaseUrl+"UserService/SetLiveCollaboration?msgFrom=%s&msgText=%s&msgTo=%s&latitude=%s&longitude=%s&userType=%s";
	public String UserLocationService=applicationBaseUrl+"UserService/UserLocationService?userLoginInfoID=%s&userReferenceID=%s&allData=%s";
	public String GetLiveCollaborationsByMsgFrom = applicationBaseUrl+"UserService/GetLiveCollaborationsByMsgFrom?msgFrom=%s&msgTo=%s&msgToType=%s&longDate=%s&groupID=%s&rowcount=%s";
	public String GetLiveCollaborationByUserDateTime =  applicationBaseUrl+"UserService/IsNewCollaborationFound?toMe=%s&lastCollaborationTime=%s";
	public String JobStatusUpdate =  applicationBaseUrl+"DriverService/JobStatusUpdate?referenceID=%s&jobDetailID=%s&jobStatusID=%s&userTypeID=%s";
	public String CustomerCurrentJob=applicationBaseUrl+"CustomerService/CustomerCurrentJob?customerID=%s";
	public String GetAssignedJobHistoryByDriver=applicationBaseUrl+"DriverService/GetAssignedJobHistoryByDriver?driverID=%s";
	public String GetJobHistoryByCustomer=applicationBaseUrl+"CustomerService/GetJobHistoryByCustomer?customerID=%s";
	public String GetUserLoginInfoByUserLoginInfoID=applicationBaseUrl+"UserService/GetUserLoginInfoByUserLoginInfoID?userLoginInfoID=%s";
	public String DriverCurrentJob=applicationBaseUrl+"DriverService/DriverCurrentJob?driverID=%s";
	public String GetAllPedingJobRequest=applicationBaseUrl+"DriverService/GetAllPedingJobRequest?referenceID=%s&userTypeID=%s";
	public String SetDriverFeedback =applicationBaseUrl+"DriverService/SetDriverFeedback?jobDetailID=%s&feedbackDate=%sjobDetailID=%s&driverFeedbackRating=%customerPreference=%s&customerSpecialRequirement=%comments=%s&ratedByDriverID=%s&ratedToCustomerID=%s";
	public String IsNewCollaborationFound =applicationBaseUrl+"UserService/IsNewCollaborationFound?toMe=%s&lastCollaborationTime=%s";
	public String JobDetail = applicationBaseUrl+"DriverService/JobDetail?jobCode=%s&jobQuoteID=%s&driverID=%s&customerID=%s&jobDate=%s&jobStartFrom=%s&fromPostcode=%s&fromLatitude=%s&fromLongtitude=%s&jobDestinationTo=%s&toPostcode=%s&toLatitude=%s&toLongtitude=%s&isReturn=%s&distance=%s&cost=%s&currencyID=%s&jobStatusID=%s&jobAccepted=%s&wayToPickup=%s&arrivedToPickup=%s&started=%s&completed=%s&cancelTime=%s&cancelReason=%s&customerInstructionToDriver=%s&companyInstructionToDriver=%s&paymentType=%s&passengerNo=%s&lagguage=%s&childseat=%s&vehicleTypeID=%s&vehicleRegistrationNo=%s&via=%s&viaPostalcode=%s";
	public String GetDriverDetailedInfoByDriverID=applicationBaseUrl+"DriverService/GetDriverDetailedInfoByDriverID?Driverid=%s";
	public String GetProductByID=applicationBaseUrl+"UserService/GetProductByID?productID=%s";
	public String GetProductCartByUserLoginInfoID=applicationBaseUrl+"UserService/GetProductCartByUserLoginInfoID?userLoginInfoID=%s&productID=%s";
	public String GetJobDetailDetailedByJobID=applicationBaseUrl+"DriverService/GetJobDetailDetailedByJobID?jobDetailID=%s";
	public String GetProductByResturantID=applicationBaseUrl+"UserService/GetProductByResturantID?resturantID=%s&pageIndex=%s&productCategoryID=%s";
	public String AddOrRemoveProductToCart=applicationBaseUrl+"UserService/AddOrRemoveProductToCart?resturantID=%s&productID=%s&userLoginInfoID=%s&cartAction=%s&cost=%s&qty=%s&comment=%s&feedback=%s&spicylevel=%s";
	public String CustomerPlaceOrder=applicationBaseUrl+"UserService/CustomerPlaceOrder?resturantID=%s&userLoginInfoID=%s&comment=%s&deliveryto=%s&deliveryby=%s";
	public String GetAllProductCartByUserLoginInfoID=applicationBaseUrl+"UserService/GetAllProductCartByUserLoginInfoID?resturantID=%s&userLoginInfoID=%s";
	public String GetAllProductCartDetailedByUserLoginInfoID=applicationBaseUrl+"UserService/GetAllProductCartDetailedByUserLoginInfoID?resturantID=%s&userLoginInfoID=%s";
	///GetAllProductCartDetailedByUserLoginInfoID?resturantID={resturantID}&userLoginInfoID={userLoginInfoID}
	public String CustomerWorkOrderByUserInfoID=applicationBaseUrl+"UserService/CustomerWorkOrderByUserInfoID?resturantID=%s&userLoginInfoID=%s";
	public String GetAllStatusByOrderId=applicationBaseUrl+"UserService/GetAllTrackOrder?customerOrderId=%s&userLoginInfoID=%s";
	public String GetAllDeliveredOrder=applicationBaseUrl+"UserService/GetAllStatusByOrderId?customerOrderId=%s";

	///CustomerWorkOrderByUserInfoID?resturantID={resturantID}&userLoginInfoID={userLoginInfoID}
	///GetAllProductCartByUserLoginInfoID?userLoginInfoID={userLoginInfoID}
	///CustomerPlaceOrder?userLoginInfoID={userLoginInfoID}
	///AddOrRemoveProductToCart?productID={productID}&userLoginInfoID={userLoginInfoID}&cartAction={cartAction}
	///GetProductByResturantID?resturantID={resturantID}&pageIndex={pageIndex}&productCategoryID={productCategoryID}
	///SetBookTable?userId={userId}&firstName={firstName}&mobileNo={mobileNo}&email={email}&Notes={Notes}&PartySize={PartySize}&dateTime={dateTime}restaurantid
	public String SetBookTable=applicationBaseUrl+"UserService/SetBookTable?userId=%s&firstName=%s&surName=%s&mobileNo=%s&email=%s&Notes=%s&PartySize=%s&dateTime=%s&restaurantid=%s";
	public String GetProductCategoryByResturantID=applicationBaseUrl+"UserService/GetProductCategoryByResturantID?resturantID=%s&pageIndex=%s";
	public String GetParentCategory=applicationBaseUrl+"UserService/GetParentCategory?parentID=%s&restaurantid=%s";
	public String GetGalleryByResturantID=applicationBaseUrl+"UserService/GetGalleryImage";
	public String GetExistingUser=applicationBaseUrl+"UserService/GetExistingUser?userInfoLoginfo=%s";
	public String GetCustomer=applicationBaseUrl+"UserService/GetCustomer?userLoginInfoID=%s";
	public String GetAllLoyaltyPoints=applicationBaseUrl+"UserService/GetAllLoyaltyPoints?restaurantid=%s&userInfoLoginfo=%s";
	public String GetOfferByTime= applicationBaseUrl+"UserService//GetItemWiseOffer?offertime=%s&restauranyID=%s";
	public String GetCustomerOrder= applicationBaseUrl+"UserService//GetCalculationTipCalculator?userID=%s";
	public String GetFeedBackList= applicationBaseUrl+"UserService//GetFeedBack?userID=%s";
	public String GetFavoriteList= applicationBaseUrl+"UserService//GetMyFaviourite?UserInfo=%s";
	public String SetFeedBack= applicationBaseUrl+"UserService//SetFeedBack?customerOrderID=%s&userID=%s&qualityfood=%s&service=%s&deliverytiming=%s&valueofmoney=%s&comments=%s";
	public String GetOpeningDeliveryTime= applicationBaseUrl+"UserService//GetAllOpeningDelivery?restaurantID=%s";
	public String GetMyOrderList= applicationBaseUrl+"UserService//GetMyOrder?OrderNo=%s";
	//public String GalleryImage=applicationBaseUrl+"UserService/GetGalleryImage";
	public String ForgetPin=applicationBaseUrl+"UserService/GetNewPin?mobile=%s";
	public String UpdateCustomer=applicationBaseUrl+"UserService/UpdateCustomer?companyID=%s&firstName=%s&middleName=%s&lastName=%s&mobileNo=%s&email=%s&addressLine1=%s&addressLine2=%s&postCode=%s&cityID=%s&countryID=%s&familySize=%s&paymentPreferenceID=%s&isSMSPreference=%s&receptEmail=%s&lastLatitude=%s&lastLongitude=%s&passcode=%s&isEmailPreference=%s";
	public String GalleryImage=applicationBaseUrl+"UserService/GetGalleryImage?restaurantid=%s";
	public String SetGCMId=applicationBaseUrl+"UserService/SetGCMId?gcmid=%s&deviceid=%s&restaurantid=%s";
	public String PostCode=applicationSecondBaseUrl+"foodelapi/postcodeinfo?postcode=%s";
	public String GetLoyaltyPointsByUser=applicationBaseUrl+"UserService/GetLoyaltyPointsByUser?userlogInfoid=%s";
	static CommonURL commonURLInstance;

	/**
	 * Return Instance
	 *
	 * @return
	 */
	public static CommonURL getInstance() {
		return commonURLInstance;
	}

	/**
	 * Create instance
	 */
	public static void initializeInstance() {
		if (commonURLInstance == null)
			commonURLInstance = new CommonURL();
	}

	// Constructor hidden because of singleton
	private CommonURL() {

	}
}
