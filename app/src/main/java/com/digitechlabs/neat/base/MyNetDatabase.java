package com.digitechlabs.neat.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.digitechlabs.neat.entities.CustomerShoppingCart;
import com.digitechlabs.neat.entities.Customer;


import java.util.ArrayList;


public class MyNetDatabase {
	private static String DATABASE_NAME = "SKTHAIDB";
	private static int DATABASE_VERSION = 1;

	private static DbHelper _DbHelper;
	private static Context context;
	public static SQLiteDatabase mynetDatabase;
	private static String SQL_TABLE = "PhoneCallInformation";
	private static String SQL = "";
	public static final String AddToCart = "AddToCart";
	 public static final String ID = "_id";
	 public static final String UserLoginInfoID = "UserLoginInfoID";
	 public static final String ResturantID = "ResturantID";
	 public static final String Qty = "Qty";
	 public static final String Cost = "Cost";
	 public static final String ProductID = "ProductID";
	 public static final String SpecialNotes = "SpecialNotes";
	 public static final String TotalCost = "TotalCost";
	 public static final String Path = "Path";
	 public static final String Title = "Title";
	 public static final String Customer = "Customer";
	 public static final String IsOrdered = "IsOrdered";
	  
	 private static final String insertCost = "INSERT INTO " + AddToCart
				+ " (" + TotalCost
				+ " ) values('0.0');";
	 
	 public static final String MyFavorite = "MyFavorite";
	public static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public static DbHelper getInstance(Context context) {

			// Use the application context, which will ensure that you
			// don't accidentally leak an Activity's context.
			// See this article for more information: http://bit.ly/6LRzfx
			if (_DbHelper == null) {
				_DbHelper = new DbHelper(context.getApplicationContext());
			}

			if (mynetDatabase == null || !mynetDatabase.isOpen()) {
				mynetDatabase = _DbHelper.getWritableDatabase();
			}
			while ((mynetDatabase.isDbLockedByCurrentThread() || mynetDatabase
					.isDbLockedByOtherThreads())) {

			}
			return _DbHelper;
		}
		public ArrayList<Cursor> getData(String Query){
			//get writable database
			SQLiteDatabase sqlDB = this.getWritableDatabase();
			String[] columns = new String[] { "mesage" };
			//an array list of cursor to save two cursors one has results from the query 
			//other cursor stores error message if any errors are triggered
			ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
			MatrixCursor Cursor2= new MatrixCursor(columns);
			alc.add(null);
			alc.add(null);
			
			
			try{
				String maxQuery = Query ;
				//execute the query results will be save in Cursor c
				Cursor c = sqlDB.rawQuery(maxQuery, null);
				

				//add value to cursor2
				Cursor2.addRow(new Object[] { "Success" });
				
				alc.set(1,Cursor2);
				if (null != c && c.getCount() > 0) {

					
					alc.set(0,c);
					c.moveToFirst();
					
					return alc ;
				}
				return alc;
			} catch(SQLException sqlEx){
				Log.d("printing exception", sqlEx.getMessage());
				//if any exceptions are triggered save the error message to cursor an return the arraylist
				Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
				alc.set(1,Cursor2);
				return alc;
			} catch(Exception ex){

				Log.d("printing exception", ex.getMessage());

				//if any exceptions are triggered save the error message to cursor an return the arraylist
				Cursor2.addRow(new Object[] { ""+ex.getMessage() });
				alc.set(1,Cursor2);
				return alc;
			}

			
		}
		@Override
		public void onCreate(SQLiteDatabase db) {

			SQL = "CREATE TABLE PhoneBasicInformation("
					+ "PhoneId INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "Model nvarchar(50) NULL, "
					+ "DeciceID nvarchar(50) NULL, "
					+ "DeviceType nvarchar(50) NULL, "
					+ "MACID nvarchar(50) NULL, "
					+ "MobileNo nvarchar(20) NULL, "
					+ "OperatorName nvarchar(20) NULL, "
					+ "OperatorCountryCode nvarchar(6) NULL, "
					+ "OperatorCountry nvarchar(20) NULL, "
					+ "SIMID nvarchar(30) NULL, "
					+ "NetworkType nvarchar(15) NULL,"
					+ "LocationName nvarchar(1000) NULL ) ";

			db.execSQL(SQL);

			SQL = "CREATE TABLE PhoneSignalStrength( "
					+ "SignalId INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "PhoneId int NOT NULL, " + "SignalLevel int NOT NULL, "
					+ "Latitude float NULL, " + "Longitude float NULL, "
					+ "LAC nvarchar(40) NULL, " + "CellID nvarchar(40) NULL, "
					+ "Time datetime NULL," + "IsSync int NOT NULL,"
					+ "SiteLang  float NULL, " + "SiteLong  float NULL, "
					+ "LocationName nvarchar(1000) NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE PhoneCallInformation( "
					+ "CallId INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "PhoneId int NOT NULL, " + "CallType int NOT NULL, "
					+ "Number nvarchar(20) NULL, " + "DurationInSec int NULL, "
					+ "Latitude float NULL, " + "Longitude float NULL, "
					+ "TextCallMemo nvarchar(2000),"
					+ "CallTime datetime NULL," + " Reson nvarchar(20) NULL,"
					+ "IsSync int NOT NULL," + "CellID nvarchar(40) NULL, "
					+ "LAC nvarchar(40) NULL, "
					+ "VoiceRecordPath nvarchar(2000) NULL, "
					+ "SiteLang  float NULL, " + "SiteLong  float NULL, "
					+ "IsLocal  int NOT NULL," + "CallLogId  int NOT NULL,"
					+ "LocationName nvarchar(1000) NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE PhoneSMSInformation( "
					+ "SMSId INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "PhoneId int NOT NULL, " + "SMSType int NOT NULL, "
					+ "Number nvarchar(20) NULL, "
					+ "SMSBody nvarchar(20) NULL, " + "Latitude float NULL, "
					+ "Longitude float NULL, " + "SMSTime datetime NULL,"
					+ "IsSync int NOT NULL," + "SmsLogId int  NULL,"
					+ "LAC nvarchar(40) NULL, " + "CellID nvarchar(40) NULL, "
					+ "SiteLang  float NULL, " + "SiteLong  float NULL, "
					+ "IsLocal  int NOT NULL,"
					+ "LocationName nvarchar(1000) NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE PhoneDataInformation( "
					+ "DataId INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "PhoneId int NOT NULL, " + "DownLoadSpeed int NOT NULL, "
					+ "UpLoadSpeed int NOT NULL, " + "Latitude float NULL, "
					+ "Longitude float NULL, " + "DataTime datetime NULL,"
					+ "IsSync int NOT NULL," + "LAC nvarchar(40) NULL, "
					+ "CellID nvarchar(40) NULL, " + "SiteLang  float NULL, "
					+ "SiteLong  float NULL, "
					+ "TotalDownloadData float  NULL, "
					+ "TotalUploadData  float NULL, "
					+ "LocationName nvarchar(1000) NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE ReportProblemAndBadExperience("
					+ "SLNO INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "Latitude float NULL," + "Longitude float NULL,"
					+ "LocationName nvarchar(500) NULL,"
					+ "RxLevel nvarchar(500) NULL,"
					+ "Problem nvarchar(500) NULL,"
					+ "ProblemTime datetime NULL,"
					+ "ReportTime datetime NULL,"
					+ "Status nvarchar(500) NULL,"
					+ "Comment nvarchar(500) NULL," + "ProblemType int NULL,"
					+ "Failed int NULL,"
					+ "ProblemDetailCategory nvarchar(500) NULL,"
					+ "ProblemDetailSubCategory nvarchar(500) NULL,"
					+ "Remarks nvarchar(500) NULL,"
					+ "Extra1 nvarchar(500) NULL,"
					+ "Extra2 nvarchar(500) NULL,"
					+ "ProblemHeader varchar(50) NULL,"
					+ "LatestFeedBack varchar(500) NULL,"
					+ "TTNumber varchar(100) NULL," + "IsSync int NOT NULL"
					+ ")";
			db.execSQL(SQL);

			// Added New Table Here(FB Info)

			SQL = "CREATE TABLE Facebook_Person("
					+ "FBNo INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "FB_UserID bigint NOT NULL,"
					+ "FB_UserName nvarchar(300) NULL,"
					+ "Name varchar(150) NULL,"
					+ "PrimaryEmail nvarchar(300) NULL,"
					+ "PP_Path nvarchar (3000) NULL,"
					+ "Gender varchar(50) NULL,"
					+ "Relationship_Status nvarchar(50) NULL,"
					+ "DateOfBirth datetime NULL,"
					+ "Religion nvarchar(50) NULL,"
					+ "Professional_Skills nvarchar(3000) NULL,"
					+ "About nvarchar(3000) NULL,"
					+ "Pages nvarchar(3000) NULL,"
					+ "Groups nvarchar(3000) NULL,"
					+ "Apps nvarchar(3000) NULL," + "IsSync int NOT NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE Facebook_Qualification_Experience("
					+ "FBQENo INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "FBNo int NOT NULL,"
					+ "Qualification_Experience nvarchar(3000) NULL,"
					+ "Position nvarchar(3000) NULL,"
					+ "Duration_From datetime NULL,"
					+ "Duration_To datetime NULL,"
					+ "Qualification_Experience_Type nvarchar(300) NULL,"
					+ "isWorkSpace int NULL," + "IsSync int NOT NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE Facebook_Friends("
					+ "FBFRDNo INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "FBFRD_Name varchar(150) NULL,"
					+ "FBFRD_UserID bigint NOT NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE LiveCollaboration("
					+ "MsgID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "MsgFrom int NOT NULL,"
					+ "MsgText varchar(3000) NOT NULL," + "GroupID int NULL,"
					+ "MsgTo int NULL," + "PostedDate datetime NOT NULL,"
					+ "isRead int NOT NULL," + "isAttachment int NOT NULL,"
					+ "Latitude float NULL," + "Longitude float NULL,"
					+ "FilePath varchar(500) NULL,"
					+ "MsgFromName varchar(100) NULL,"
					+ "GroupName varchar(100) NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE LocalUserInformation("
					+ "UserID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "UserName nvarchar(100) NOT NULL,"
					+ "ReffId int NOT NULL," + "IsFriend int NOT NULL,"
					+ "MobileNumber nvarchar(20) NOT NULL,"
					+ "UserOnlinestatus int NOT NULL,"
					+ "isFavourite int NOT NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE LocalGroupInformation("
					+ "GroupID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "GroupName nvarchar(100) NOT NULL,"
					+ "ReffId int NOT NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE LocalUserGroup("
					+ "UserGroupID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "GroupID int NOT NULL," + "UserId int NOT NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE CustomMode("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "ModeName nvarchar(500) NOT NULL,"
					+ "Brightness int NOT NULL," + "TimeOut int NOT NULL,"
					+ "Data int NOT NULL," + "Wifi int NOT NULL,"
					+ "Bluetooth int NOT NULL," + "AutomaticSync int NOT NULL,"
					+ "Silence int NOT NULL," + "Vibration int NOT NULL" + ")";
			db.execSQL(SQL);

			SQL = "CREATE TABLE AddToCart("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"					
					+ "UserLoginInfoID int NULL,"
					+ "ResturantID int NULL,"
					+ "Qty int  NULL,"
					+ "Cost decimal  NULL,"
					+ "ProductID int NULL,"
					+ "TotalCost decimal NULL,"
					+ "Path nvarchar(1000) NULL,"
					+ "Title nvarchar(1000)  NULL,"
					+ "SpecialNotes nvarchar(1000)  NULL,"
					+ "ItemTotal nvarchar(1000) NULL,"
					+ "IsFavorite int DEFAULT 0"
					+ ")";
			db.execSQL(SQL);
			//db.execSQL(insertCost);
			SQL = "CREATE TABLE MyFavorite("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"				
					+ "UserLoginInfoID int NOT NULL,"
					+ "ResturantID int NOT NULL,"
					+ "Qty int NOT NULL,"
					+ "Cost decimal NOT NULL,"
					+ "ProductID int NOT NULL,"
					+ "TotalCost decimal NOT NULL," 
					+ "Path nvarchar(1000) NOT NULL,"
					+ "Title nvarchar(1000) NOT NULL" 
					+ ")";
			db.execSQL(SQL);
			SQL = "CREATE TABLE FavoriteOrder("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"				
					+ "UserLoginInfoID int NULL,"
					+ "ResturantID int NULL,"
					+ "Qty int NOT NULL,"
					+ "OrderID int NOT NULL,"
					+ "Cost decimal NOT NULL,"
					+ "ProductID int NOT NULL,"
					+ "TotalCost decimal NOT NULL," 
					+ "Path nvarchar(1000) NOT NULL,"
					+ "DeliveredDate nvarchar(1000) NOT NULL" 
					+ ")";
			db.execSQL(SQL);
			/*SQL = "CREATE TABLE Customer("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"				
					+ "UserLoginInfoID int NULL,"
					+ "ResturantID int NULL"
					+ ")";*/
			
			SQL = "CREATE TABLE Customer("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"				
					+ "UserLoginInfoID int NULL,"   
					+ "ResturantID int NULL,"
					+ "Title nvarchar(1000) NULL,"
					+ "FirstName nvarchar(1000) NULL,"
					+ "LastName nvarchar(1000) NULL,"
					+ "Surname nvarchar(1000) NULL,"
					+ "Door nvarchar(1000) NULL,"
					+ "Street nvarchar(1000) NULL,"
					+ "Address nvarchar(1000) NULL,"
					+ "Town nvarchar(1000) NULL,"
					+ "PostCode nvarchar(1000) NULL,"
					+ "Mobile nvarchar(1000) NULL,"
					+ "Email nvarchar(1000) NULL,"
					+ "IsOrdered int NULL"
					+ ")";
			db.execSQL(SQL);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS AddToCart");
			db.execSQL("DROP TABLE IF EXISTS Customer");
			db.execSQL("DROP TABLE IF EXISTS PhoneSignalStrength");
			db.execSQL("DROP TABLE IF EXISTS PhoneCallInformation");
			db.execSQL("DROP TABLE IF EXISTS PhoneSMSInformation");
			db.execSQL("DROP TABLE IF EXISTS PhoneDataInformation");
			onCreate(db);
		}
	}

	public MyNetDatabase(Context _context) {
		context = _context;
		DbHelper.getInstance(context);
	}
	
	public MyNetDatabase open() {

		return this;
	}

	public void close() {
		// _DbHelper.close();
	}
	public long createSignalStrenght(CustomerShoppingCart phoneSignalStrenght) {
/*		Cursor c = mynetDatabase.rawQuery(
				"select * FROM AddToCart", null);
		int phoneId = 0;
		ArrayList<CustomerShoppingCart> customModes = new ArrayList<CustomerShoppingCart>();
		if (c != null && c.getCount() > 0) {
			CustomerShoppingCart customMode = null;
			c.moveToFirst();
			for(int rowIndex = 0;rowIndex<c.getCount();rowIndex++){
				customMode = new CustomerShoppingCart();
				customMode.UserLoginInfoID = c.getInt(1);
				customMode.RestaurantId = c.getInt(2);
				customMode.Qty = c.getInt(3);
				customMode.Cost = c.getDouble(4);
				customMode.TotalCost = c.getDouble(6);
				customMode.ProductID = c.getInt(5);
				//customMode.ProductID = c.getInt(6);
		
				customModes.add(customMode);
				c.moveToNext();
			
			}
		}*/
		ContentValues contentValues = new ContentValues();
		contentValues.put("UserLoginInfoID", phoneSignalStrenght.UserLoginInfoID);
		contentValues.put("ResturantID",1);
		contentValues.put("Qty", phoneSignalStrenght.Qty);
		contentValues.put("Cost", phoneSignalStrenght.Cost);
		contentValues.put("ProductID", phoneSignalStrenght.ProductID);
		contentValues.put("TotalCost", phoneSignalStrenght.TotalCost);
		contentValues.put("Path", phoneSignalStrenght.Path);
		contentValues.put("Title", phoneSignalStrenght.Title);
		return mynetDatabase.insertOrThrow("AddToCart", null,
				contentValues);

	}
	
	public long insertProductIntoLocalDb(CustomerShoppingCart phoneSignalStrenght) {

				ContentValues contentValues = new ContentValues();
				contentValues.put("UserLoginInfoID", 1);
				contentValues.put("ResturantID",1);
				contentValues.put("Qty", phoneSignalStrenght.Qty);
				contentValues.put("Cost", phoneSignalStrenght.Cost);
				contentValues.put("ProductID", phoneSignalStrenght.ProductID);
				contentValues.put("SpecialNotes", phoneSignalStrenght.SpecialNotes);
				contentValues.put("TotalCost", phoneSignalStrenght.TotalCost);
				contentValues.put("IsFavorite", phoneSignalStrenght.IsFavorite);
				contentValues.put("Path", phoneSignalStrenght.Path);
				contentValues.put("Title", phoneSignalStrenght.Title);
				return mynetDatabase.insertOrThrow("AddToCart", null,
						contentValues);
			}
	
/*	public long createUser(int userLoginID) {

				ContentValues contentValues = new ContentValues();
				contentValues.put("UserLoginInfoID", userLoginID);
				contentValues.put("ResturantID",1);
				
				return mynetDatabase.insertOrThrow("Customer", null,
						contentValues);
			}*/
			
	public long createUser(int userLoginID,String title,String firstName,String lastName,String surName,String door,
			String street,String Address,String town,String postCode,String mobile,String email, int isOrdered) {
				ContentValues contentValues = new ContentValues();
				contentValues.put("UserLoginInfoID", userLoginID);
				contentValues.put("ResturantID",1);
				contentValues.put("Title", title);
				contentValues.put("FirstName", firstName);
				contentValues.put("LastName", lastName);
				contentValues.put("Surname", surName);
				contentValues.put("Door", door);
				contentValues.put("Street", street);
				contentValues.put("Address", Address);
				contentValues.put("Town", town);
				contentValues.put("PostCode", postCode);
				contentValues.put("Mobile", mobile);
				contentValues.put("Email", email);
				contentValues.put("IsOrdered", isOrdered);
				return mynetDatabase.insertOrThrow("Customer", null,
						contentValues);
			}	
	
	 public int updateUserInfo(int loginID, String Title,String Door,
			 String Street,String Address,String Town,String PostCode, int isOrdered) {	  
		/*  String strSQL = "Update Customer Set IsOrdered = "+ isOrdered + " Door= "+ Door + " Street= "+ Street 
				  + " Address = "+ Address + " Town = "+ Town + " PostCode = "+ PostCode + " Title = "+ Title 
				  +" where UserLoginInfoID = "+ loginID ;
		  mynetDatabase.execSQL(strSQL);*/
		  ContentValues cvUpdate = new ContentValues();
		  cvUpdate.put("Title", Title);
		  cvUpdate.put("Door", Door);
		  cvUpdate.put("Street", Street);
		  cvUpdate.put("Address", Address);
		  cvUpdate.put("Town", Town);
		  cvUpdate.put("PostCode", PostCode);
		  cvUpdate.put("IsOrdered", isOrdered);

		  int i = mynetDatabase.update(Customer, cvUpdate,
				  UserLoginInfoID + " = " + loginID, null);
		  return i;
	 }
	 public int updateUserInfoDetails(int loginID, String Title,String firstName,
			 String lastName, String address,String Mobile,String Email,String Door, String postCode, String Town,String street) {	  
		/*  String strSQL = "Update Customer Set IsOrdered = "+ isOrdered + " Door= "+ Door + " Street= "+ Street 
				  + " Address = "+ Address + " Town = "+ Town + " PostCode = "+ PostCode + " Title = "+ Title 
				  +" where UserLoginInfoID = "+ loginID ;
				  Title,
												firstName, lastName, address, Mobile,Email,Door,
												PostCode,Town,street
				  
				  
				  getUserLoginID(), Title,
												firstName, lastName, address, Mobile,Email,Door,
												postCode,Town,street
												
												
													+ "Title nvarchar(1000) NULL,"
					+ "FirstName nvarchar(1000) NULL,"
					+ "LastName nvarchar(1000) NULL,"
					+ "Surname nvarchar(1000) NULL,"
					+ "Door nvarchar(1000) NULL,"
					+ "Street nvarchar(1000) NULL,"
					+ "Address nvarchar(1000) NULL,"
					+ "Town nvarchar(1000) NULL,"
					+ "PostCode nvarchar(1000) NULL,"
					+ "Mobile nvarchar(1000) NULL,"
					+ "Email nvarchar(1000) NULL,"
		  mynetDatabase.execSQL(strSQL);*/
		  ContentValues cvUpdate = new ContentValues();
		  cvUpdate.put("Title", Title);
		  cvUpdate.put("FirstName", firstName);
		  cvUpdate.put("lastName", lastName);
		  cvUpdate.put("Address", address);
		  cvUpdate.put("Mobile", Mobile);
		  cvUpdate.put("Email", Email);
		  cvUpdate.put("Door", Door);
		  cvUpdate.put("PostCode", postCode);
		  cvUpdate.put("Town", Town);
		  
		  cvUpdate.put("Street", street);

		  int i = mynetDatabase.update(Customer, cvUpdate,
				  UserLoginInfoID + " = " + loginID, null);
		  return i;
	 }
	
	 public void updateOrderStatus(int isOrdered, int userID) {	  
		  String strSQL = "Update Customer Set IsOrdered = " + isOrdered + " where UserLoginInfoID = "+ userID ;
		  mynetDatabase.execSQL(strSQL);
	 }
	public void updateCart(int qty, int id){
		String strSQL = "Update AddToCart Set Qty = " + qty + " where ProductID = "+ id ;
		
		mynetDatabase.execSQL(strSQL);
	}
	public void updateFavorite(int id, int favoriteValue){
		String strSQL = "Update AddToCart Set IsFavorite = " + favoriteValue + " where ProductID = "+ id ;		
		mynetDatabase.execSQL(strSQL);
	}
	
	public Boolean isFavorite(int id){
		 String selectQuery = "SELECT  * FROM AddToCart"
				    + " WHERE ProductID"  + " = " + id;
		 int fav=0;
		 Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		 if(cursor != null && cursor.moveToFirst()){
			 fav=Integer.parseInt(cursor.getString(11));
			 cursor.close();
		 }
		 
		 return (fav==0) ? false: true;
		 
	}
	 public int updateData(int id, int quantity) {
			  ContentValues cvUpdate = new ContentValues();
			   cvUpdate.put(Qty, quantity);
			  //else cvUpdate.put(Qty, decreaseQuantity(id));
			  cvUpdate.put(ProductID, id);
			  int i = mynetDatabase.update(AddToCart, cvUpdate,
					  ProductID + " = " + id, null);
			  return i;
	}
	 public void updateSpecialNotes(int id, String notes) {
		  ContentValues cvUpdate = new ContentValues();
		   cvUpdate.put(SpecialNotes, notes);
		  //else cvUpdate.put(Qty, decreaseQuantity(id));
		  cvUpdate.put(ProductID, id);
		  mynetDatabase.update(AddToCart, cvUpdate,
				  ProductID + " = " + id, null);
		 
	 }
	
	public Boolean checkIfProductExist(int id) {

		  String selectQuery = "SELECT  * FROM AddToCart"
		    + " WHERE ProductID"  + " = " + id;

		  // String User =
		  // "Select * from User_details Where mobile=name and  password=pass";

		  Log.e(DBhelper.TAG, selectQuery);

		  Cursor c = mynetDatabase.rawQuery(selectQuery, null);

		  if (c.moveToFirst()) {
		   

		   return false;
		  }
		  else{
			  return true;
		  }
		  
		  
		 }
	
	public Boolean isSingleItemExist(int id){
		
		int totalItem=getTotalItem(id);
		
		if(totalItem==1) return true;
		else return false;
		
		
	} 
	
	public Boolean getProductID( int id){
		String strSQL = "Select ProductID = "+ id +"  from  AddToCart";
		boolean inserted ;
		
		try {
			inserted =false;
				mynetDatabase.execSQL(strSQL);
			
			} catch (SQLException e) {
			//  return false;
			  inserted = true;
			}
		return inserted;
	}
	
	public int getUserID(){
		String selectQuery = "SELECT  * FROM Customer";
		int userID=0;
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		 if (cursor.moveToFirst() && cursor.getCount() > 0) {
			 userID=cursor.getInt(1);
			 cursor.close();
		 }
		 return userID;
				
	}
	public int getOrderStatus(){
		String selectQuery = "SELECT  * FROM Customer";
		int orderStatus=-1;
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		 if (cursor.moveToFirst() && cursor.getCount() > 0) {
			 orderStatus=cursor.getInt(14);
			 cursor.close();
		 }
		 return orderStatus;
				
	}
	
	public int getTotalItem(int id){
		String selectQuery = "SELECT  * FROM AddToCart"
			    + " WHERE ProductID"  + " = " + id;
		int qty=0;
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		 if (cursor != null && cursor.getCount() > 0) {
			 cursor.moveToFirst();
			 qty=Integer.parseInt(cursor.getString(3));
			 //cursor.close();
			 return qty;
		 }
		 else return 0;
				
	}
	

	public double getItemPrice(int id){
		String selectQuery = "SELECT  * FROM AddToCart"
				 + " WHERE ProductID"  + " = " + id;
		double price=0;
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		 if (cursor != null && cursor.getCount() > 0) {
			 cursor.moveToFirst();
			 price=Double.parseDouble(cursor.getString(4));
			 //cursor.close();
		 }
		 //else return 0;
		 return price;		
	}
	
	public int getNumberOfFavItem(){
		String selectQuery = "SELECT  * FROM AddToCart"
				 + " WHERE IsFavorite"  + " = " + 1;
		int favCounter=0;
		Cursor c= mynetDatabase.rawQuery(selectQuery, null);
		 if (c != null && c.getCount() > 0) {
			   c.moveToFirst();
			   favCounter = c.getCount();
			  }
		 //else return 0;
		 return favCounter;		
	}
	public double getTotalPrice(){
		String selectQuery = "SELECT  * FROM AddToCart WHERE TotalCost>=0.0";
		double price=0.0;
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		 if (cursor != null) {
			 try {
				 cursor.moveToFirst();
				 price=Double.parseDouble(cursor.getString(6));
			 }
			 catch(Exception exception){
				 price=0.0;
			 }
			
			 //cursor.close();
		 }
		 //else return 0;
		 return price;		
	}
	public String getProductName(int id){
		String selectQuery = "SELECT  * FROM AddToCart"
				 + " WHERE ProductID"  + " = " + id;
		String productName="";
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		 if (cursor != null) {
			 try {
				 cursor.moveToFirst();
				 productName=cursor.getString(6);
				 cursor.close();
			 }
			 catch(Exception exception){
				 productName="";
			 }
			
			 //cursor.close();
		 }
		 //else return 0;
		 return productName;		
	}
	public Boolean isTotalPriceExist(){
		Cursor cursor = mynetDatabase.rawQuery("SELECT count(*) FROM AddToCart", null);
		if (cursor.getCount()==0){
			return false;	
		}
		else{
			return true;
		}
		//String selectQuery = "SELECT  * FROM AddToCart WHERE TotalCost>=0.0";
		//Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		//cursor.moveToFirst();
		
		//Double i=Double.parseDouble(cursor.getString(6));
		
		
		 //else return 0;
		 	
	}
	public Boolean isDbEmpty(){
		String countRow ="SELECT count(*) FROM AddToCart";
		Cursor mcursor = mynetDatabase.rawQuery(countRow, null);
		mcursor.moveToFirst();
		int icount = mcursor.getInt(0);
		return icount==0?true:false;
	}
	
	public double updateTotalCost(String updateStatus, double itemPrice){
		  ContentValues cvUpdate = new ContentValues();
		  if(updateStatus=="plus")  cvUpdate.put(TotalCost, increaseTotalCost(itemPrice));
		  else cvUpdate.put(TotalCost, decreaseTotalCost(itemPrice));
		  //cvUpdate.put(ProductID, id);
		  double i = mynetDatabase.update(AddToCart, cvUpdate, null, null);
		  return i;
				
	}
	
	
	public double increaseTotalCost(double itemPrice){
		String selectQuery = "SELECT  * FROM AddToCart WHERE TotalCost>=0.0";
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		Double i=0.0;
		if(cursor.moveToFirst()){
			i=Double.parseDouble(cursor.getString(6))+itemPrice;
			//tCost=Double.parseDouble(cursor.getString(6))+itemPrice;
			cursor.close();
		}
		return i;
				
	}
	public double decreaseTotalCost(double itemPrice){
		String selectQuery = "SELECT  * FROM AddToCart WHERE TotalCost>0.0";
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		Double i=0.0;
		if(cursor.moveToFirst()){
			i=Double.parseDouble(cursor.getString(6))-itemPrice;
		}
		if(i<=0) i=0.0;
		//tCost=Double.parseDouble(cursor.getString(6))+itemPrice;
			 //cursor.close();
		return i;
		 
	}
	public int increaseQuantity(int id){
		String selectQuery = "SELECT  * FROM AddToCart"
			    + " WHERE ProductID"  + " = " + id;
		int qty=0;
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		qty=Integer.parseInt(cursor.getString(3))+1;
			 //cursor.close();
		return qty;
				
	}
	public int decreaseQuantity(int id){
		String selectQuery = "SELECT  * FROM AddToCart"
			    + " WHERE ProductID"  + " = " + id;
		int qty=0;
		Cursor cursor= mynetDatabase.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		qty=Integer.parseInt(cursor.getString(3))-1;
			 //cursor.close();
		return qty;
		 		
	}
	
	public void deleteRowByID(int id){
		
		String deleteQuery = "DELETE FROM AddToCart"
			    + " WHERE ProductID"  + " = " + id;
		mynetDatabase.execSQL(deleteQuery);
		//return mynetDatabase.delete(AddToCart, deleteQuery, null)>0;
		
	}
	public void deleteCart(){
		
		//String deleteQuery = "DELETE  FROM AddToCart";
		//mynetDatabase.execSQL(deleteQuery);
		//mynetDatabase.delete(AddToCart,null, null);
		mynetDatabase.execSQL("delete from "+ AddToCart);
		//return mynetDatabase.delete(AddToCart, deleteQuery, null)>0;
		
	}
	public void deleteUserFromTable(){
		
		//String deleteQuery = "DELETE  FROM AddToCart";
		//mynetDatabase.execSQL(deleteQuery);
		//mynetDatabase.delete(AddToCart,null, null);
		mynetDatabase.execSQL("delete from "+ Customer);
		//return mynetDatabase.delete(AddToCart, deleteQuery, null)>0;
		
	}
	//CustomerShoppingCart customMode = new CustomerShoppingCart();
/*	public ArrayList<CustomerShoppingCart> getAddToCart() {
		ArrayList<String> customModes = new ArrayList<String>();
		
		Cursor c = mynetDatabase.rawQuery(
				"select *  FROM AddToCart", null);
		
		
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			for(int index=0; index<c.getCount(); index++){
				
				customMode.UserLoginInfoID = c.getInt(1);
				customMode.RestaurantId = c.getInt(2);
				customMode.Qty = c.getInt(3);
				customMode.Cost = c.getDouble(4);
				customMode.TotalCost = c.getDouble(6);
				customMode.ProductID = c.getInt(5);
				customMode.Path= c.getString(7);
				customMode.Title= c.getString(8);
				
				//customMode.ProductID = c.getInt(6);
		
				customModes.add(index,customMode);
				//index++;
				c.moveToNext();
			}
		}
		c.close();
		return customModes;
	}*/
	
	 public ArrayList<CustomerShoppingCart> getAddToCart() {
		  ArrayList<CustomerShoppingCart> customModes = new ArrayList<CustomerShoppingCart>();
		  Cursor c = mynetDatabase.rawQuery(
		    "select * FROM AddToCart", null);
		  CustomerShoppingCart customMode = null;
		  if (c != null && c.getCount() > 0) {
		   c.moveToFirst();
		   for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
		    customMode = new CustomerShoppingCart();
		    customMode.UserLoginInfoID = c.getInt(1);
		    customMode.RestaurantId = c.getInt(2);
		    customMode.Qty = c.getInt(3);
		    customMode.Cost = c.getDouble(4);
		    customMode.TotalCost = c.getDouble(6);
		    customMode.ProductID = c.getInt(5);
		    customMode.Path= c.getString(7);
			customMode.Title= c.getString(8);
			customMode.SpecialNotes= c.getString(9);
			customMode.IsFavorite = c.getInt(11);
		    customModes.add(customMode);
		    c.moveToNext();
		   }
		  }
		  c.close();
		  return customModes;
		 }

	 public ArrayList<Customer> getCustomerInfo() {
		  ArrayList<Customer> customModes = new ArrayList<Customer>();
		  Cursor c = mynetDatabase.rawQuery(
		    "select * FROM Customer", null);
		  Customer customMode = null;
		  if (c != null && c.getCount() > 0) {
		   c.moveToFirst();
		   for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
		    customMode = new Customer();
		    customMode.UserLoginInfoID = c.getInt(1);
		    customMode.RestaurentID = c.getInt(2);
		    customMode.Title = c.getString(3);
		    customMode.FirstName = c.getString(4);
		    customMode.LastName = c.getString(5);
		    customMode.Surname = c.getString(6);
		    customMode.DoorNumber= c.getString(7);
			customMode.Street= c.getString(8);
			customMode.AddressLine1= c.getString(9);
			customMode.Address= c.getString(9);
			customMode.Town = c.getString(10);
			customMode.PostCode = c.getString(11);
			customMode.MobileNo = c.getString(12);
			customMode.Email = c.getString(13);
			customMode.IsOrdered = c.getInt(14);
		    customModes.add(customMode);
		    c.moveToNext();
		   }
		  }
		  c.close();
		  return customModes;
		 }
	 
	/*
	 * public long createBasicInformation( PhoneBasicInformation
	 * phoneBasicInformation) {
	 * 
	 * Cursor c = mynetDatabase.rawQuery(
	 * "select PhoneId FROM PhoneBasicInformation where DeciceID='" +
	 * phoneBasicInformation.DeciceID + "'", null); if (c != null &&
	 * c.getCount() == 0) { ContentValues contentValues = new ContentValues();
	 * contentValues.put("Model", phoneBasicInformation.Model);
	 * contentValues.put("DeciceID", phoneBasicInformation.DeciceID);
	 * contentValues.put("DeviceType", phoneBasicInformation.DeviceType);
	 * contentValues.put("MACID", phoneBasicInformation.MACID);
	 * contentValues.put("MobileNo", phoneBasicInformation.MobileNo);
	 * contentValues.put("OperatorName", phoneBasicInformation.OperatorName);
	 * contentValues.put("OperatorCountryCode",
	 * phoneBasicInformation.OperatorCountryCode);
	 * contentValues.put("OperatorCountry",
	 * phoneBasicInformation.OperatorCountry); contentValues.put("SIMID",
	 * phoneBasicInformation.SIMID); contentValues.put("NetworkType",
	 * phoneBasicInformation.NetworkType); contentValues.put("LocationName",
	 * phoneBasicInformation.LocationName); return
	 * mynetDatabase.insertOrThrow("PhoneBasicInformation", null,
	 * contentValues); } return 0; }
	 * 
	 * public PhoneBasicInformation getEntry() { Cursor c =
	 * mynetDatabase.rawQuery( "select * FROM PhoneBasicInformation", null);
	 * PhoneBasicInformation phoneBasicInformation = null; if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); phoneBasicInformation = new
	 * PhoneBasicInformation(); phoneBasicInformation.PhoneId = c.getInt(0);
	 * phoneBasicInformation.Model = c.getString(1);
	 * phoneBasicInformation.DeciceID = c.getString(2);
	 * phoneBasicInformation.DeviceType = c.getString(3);
	 * phoneBasicInformation.MACID = c.getString(4);
	 * phoneBasicInformation.MobileNo = c.getString(5);
	 * phoneBasicInformation.OperatorName = c.getString(6);
	 * phoneBasicInformation.OperatorCountryCode = c.getString(7);
	 * phoneBasicInformation.OperatorCountry = c.getString(8);
	 * phoneBasicInformation.SIMID = c.getString(9);
	 * phoneBasicInformation.NetworkType = c.getString(10);
	 * phoneBasicInformation.LocationName = c.getString(11); } return
	 * phoneBasicInformation; }
	 * 
	 * public void deleteSignal() {
	 * mynetDatabase.rawQuery("delete  FROM PhoneSignalStrength", null); }
	 * 
	 * public long createSignalStrenght(PhoneSignalStrenght phoneSignalStrenght)
	 * { Cursor c = mynetDatabase.rawQuery(
	 * "select PhoneId FROM PhoneBasicInformation", null); int phoneId = 0; if
	 * (c != null && c.getCount() > 0) { c.moveToFirst(); phoneId = c.getInt(0);
	 * } ContentValues contentValues = new ContentValues();
	 * contentValues.put("PhoneId", phoneId); contentValues.put("SignalLevel",
	 * phoneSignalStrenght.SignalLevel == 99 ? 0 :
	 * phoneSignalStrenght.SignalLevel); contentValues.put("Latitude",
	 * phoneSignalStrenght.Latitude); contentValues.put("Longitude",
	 * phoneSignalStrenght.Longitude); contentValues.put("Time",
	 * phoneSignalStrenght.Time.getTime()); contentValues.put("LAC",
	 * phoneSignalStrenght.LAC); contentValues.put("CellID",
	 * phoneSignalStrenght.CellID); contentValues.put("IsSync", 0);
	 * contentValues.put("SiteLang", phoneSignalStrenght.SiteLang);
	 * contentValues.put("SiteLong", phoneSignalStrenght.SiteLong);
	 * contentValues.put("LocationName", phoneSignalStrenght.LocationName);
	 * return mynetDatabase.insertOrThrow("PhoneSignalStrength", null,
	 * contentValues);
	 * 
	 * }
	 * 
	 * public long createPhoneCallInformation( PhoneCallInformation
	 * phoneCallInformation) { ContentValues contentValues = new
	 * ContentValues(); contentValues.put("PhoneId",
	 * phoneCallInformation.PhoneId); contentValues.put("Number",
	 * phoneCallInformation.Number); contentValues.put("Reson",
	 * phoneCallInformation.Reson); contentValues.put("CallType",
	 * phoneCallInformation.CallType); contentValues.put("CallTime",
	 * phoneCallInformation.CallTime.getTime());
	 * contentValues.put("DurationInSec", phoneCallInformation.DurationInSec);
	 * contentValues.put("TextCallMemo", phoneCallInformation.TextCallMemo);
	 * contentValues.put("VoiceRecordPath",
	 * phoneCallInformation.VoiceRecordPath); contentValues.put("Latitude",
	 * phoneCallInformation.Latitude); contentValues.put("Longitude",
	 * phoneCallInformation.Longitude); contentValues.put("IsSync", 0);
	 * contentValues.put("LAC", phoneCallInformation.LAC);
	 * contentValues.put("CellID", phoneCallInformation.CellID);
	 * contentValues.put("SiteLang", phoneCallInformation.SiteLang);
	 * contentValues.put("SiteLong", phoneCallInformation.SiteLong);
	 * contentValues.put("IsLocal", phoneCallInformation.IsLocal);
	 * contentValues.put("CallLogId", phoneCallInformation.CallLogId);
	 * contentValues.put("LocationName", phoneCallInformation.LocationName);
	 * mynetDatabase .insertOrThrow("PhoneCallInformation", null,
	 * contentValues); Cursor c = mynetDatabase .rawQuery(
	 * "SELECT CallId FROM PhoneCallInformation ORDER BY CallId desc LIMIT 1",
	 * null);
	 * 
	 * if (c != null && c.getCount() > 0) { c.moveToFirst(); return c.getInt(0);
	 * } return 0; }
	 * 
	 * public long createPhoneSMSInformation( PhoneSMSInformation
	 * phoneSMSInformation) {
	 * 
	 * ContentValues contentValues = new ContentValues();
	 * contentValues.put("PhoneId", phoneSMSInformation.PhoneId);
	 * contentValues.put("Number", phoneSMSInformation.Number);
	 * contentValues.put("SMSBody", phoneSMSInformation.SMSBody);
	 * contentValues.put("SMSType", phoneSMSInformation.SMSType);
	 * contentValues.put("SMSTime", phoneSMSInformation.SMSTime.getTime());
	 * contentValues.put("Latitude", phoneSMSInformation.Latitude);
	 * contentValues.put("Longitude", phoneSMSInformation.Longitude);
	 * 
	 * contentValues.put("IsSync", 0); contentValues.put("SmsLogId",
	 * phoneSMSInformation.SmsLogId); contentValues.put("LAC",
	 * phoneSMSInformation.LAC); contentValues.put("CellID",
	 * phoneSMSInformation.CellID); contentValues.put("SiteLang",
	 * phoneSMSInformation.SiteLang); contentValues.put("SiteLong",
	 * phoneSMSInformation.SiteLong); contentValues.put("IsLocal",
	 * phoneSMSInformation.IsLocal); contentValues.put("LocationName",
	 * phoneSMSInformation.LocationName); return
	 * mynetDatabase.insertOrThrow("PhoneSMSInformation", null, contentValues);
	 * }
	 * 
	 * public void updatePhoneCallInformation( PhoneCallInformation
	 * phoneCallInformation) { ContentValues contentValues = new
	 * ContentValues();
	 * 
	 * contentValues.put("TextCallMemo", phoneCallInformation.TextCallMemo);
	 * 
	 * mynetDatabase.update("PhoneCallInformation", contentValues, " CallId=" +
	 * phoneCallInformation.CallId, null);
	 * 
	 * }
	 * 
	 * public void updatePhoneSMSInformation( PhoneSMSInformation
	 * phoneSMSInformation) { ContentValues contentValues = new ContentValues();
	 * contentValues.put("SMSType", phoneSMSInformation.SMSType);
	 * contentValues.put("SMSTime", phoneSMSInformation.SMSTime.getTime());
	 * contentValues.put("Latitude", phoneSMSInformation.Latitude);
	 * contentValues.put("Longitude", phoneSMSInformation.Longitude);
	 * contentValues.put("IsSync", 0);
	 * mynetDatabase.update("PhoneSMSInformation", contentValues, " SmsLogId=" +
	 * phoneSMSInformation.SmsLogId, null); }
	 * 
	 * public void updateUserList(int reffid){ ContentValues contentValues = new
	 * ContentValues(); contentValues.put("IsFriend", 0);
	 * mynetDatabase.update("LocalUserInformation", contentValues,
	 * " ReffId="+reffid, null); }
	 * 
	 * 
	 * 
	 * public void updateDataSyncInfo(ArrayList<PhoneCallInformation> callList,
	 * ArrayList<PhoneSMSInformation> smsList, ArrayList<PhoneSignalStrenght>
	 * signalList) { ContentValues contentValues = new ContentValues(); if
	 * (signalList != null && signalList.size() > 0) { for (PhoneSignalStrenght
	 * signal : signalList) { contentValues.put("IsSync", 1);
	 * mynetDatabase.update("PhoneSignalStrength", contentValues, " SignalId=" +
	 * signal.SignalId, null); } } if (callList != null && callList.size() > 0)
	 * { for (PhoneCallInformation call : callList) {
	 * contentValues.put("IsSync", 1);
	 * mynetDatabase.update("PhoneCallInformation", contentValues, " CallId=" +
	 * call.CallId, null); } } if (smsList != null && smsList.size() > 0) { for
	 * (PhoneSMSInformation sms : smsList) { contentValues.put("IsSync", 1);
	 * mynetDatabase.update("PhoneSMSInformation", contentValues, " SMSId=" +
	 * sms.SMSId, null); } } }
	 * 
	 * public long createPhoneDataInformation( PhoneDataInformation
	 * phoneDataInformation) {
	 * 
	 * Cursor c = mynetDatabase.rawQuery(
	 * "select PhoneId FROM PhoneBasicInformation", null); int phoneId = 0; if
	 * (c != null && c.getCount() > 0) { c.moveToFirst(); phoneId = c.getInt(0);
	 * } c.close(); ContentValues contentValues = new ContentValues();
	 * contentValues.put("PhoneId", phoneId); contentValues.put("DownLoadSpeed",
	 * phoneDataInformation.DownLoadSpeed); contentValues.put("UpLoadSpeed",
	 * phoneDataInformation.UpLoadSpeed); contentValues.put("DataTime", new
	 * Date().getTime()); contentValues.put("Latitude",
	 * phoneDataInformation.Latitude); contentValues.put("Longitude",
	 * phoneDataInformation.Longitude); contentValues.put("IsSync", 1);
	 * contentValues.put("LAC", phoneDataInformation.LAC);
	 * contentValues.put("CellID", phoneDataInformation.CellID);
	 * contentValues.put("SiteLang", 0); contentValues.put("SiteLong", 0);
	 * contentValues.put("TotalDownloadData",
	 * phoneDataInformation.TotalDownloadData);
	 * contentValues.put("TotalUploadData",
	 * phoneDataInformation.TotalUploadData); contentValues.put("LocationName",
	 * phoneDataInformation.LocationName); return
	 * mynetDatabase.insertOrThrow("PhoneDataInformation", null, contentValues);
	 * }
	 * 
	 * public long CreateCustomMode(CustomMode customMode) { ContentValues
	 * contentValues = new ContentValues(); contentValues.put("ModeName",
	 * customMode.ModeName); contentValues.put("Brightness",
	 * customMode.Brightness); contentValues.put("TimeOut", customMode.TimeOut);
	 * contentValues.put("Data", customMode.Data); contentValues.put("Wifi",
	 * customMode.Wifi); contentValues.put("Bluetooth", customMode.Bluetooth);
	 * contentValues.put("AutomaticSync", customMode.AutomaticSync);
	 * contentValues.put("Silence", customMode.Silence);
	 * contentValues.put("Vibration", customMode.Vibration); return
	 * mynetDatabase.insertOrThrow("CustomMode", null, contentValues); }
	 * 
	 * public int getTotalCountOfCustomMode(){ int count = 0; Cursor c =
	 * mynetDatabase.rawQuery("select count(_id) from CustomMode", null); if(c
	 * != null && c.getCount() > 0){ c.moveToFirst(); count = c.getInt(0); }
	 * c.close(); return count; }
	 * 
	 * public ArrayList<CustomMode> getAllCustomModeList(){
	 * ArrayList<CustomMode> customModes = new ArrayList<CustomMode>(); Cursor c
	 * = mynetDatabase.rawQuery("select * from CustomMode", null); CustomMode
	 * customMode = null; if(c != null && c.getCount()>0){ c.moveToFirst();
	 * for(int rowIndex = 0;rowIndex<c.getCount();rowIndex++){ customMode = new
	 * CustomMode(); customMode._id = c.getInt(0); customMode.ModeName =
	 * c.getString(1); customMode.Brightness = c.getInt(2); customMode.TimeOut =
	 * c.getInt(3); customMode.Data = c.getInt(4); customMode.Wifi =
	 * c.getInt(5); customMode.Bluetooth = c.getInt(6); customMode.AutomaticSync
	 * = c.getInt(7); customMode.Silence = c.getInt(8); customMode.Vibration =
	 * c.getInt(9); customModes.add(customMode); c.moveToNext(); } } return
	 * customModes; }
	 * 
	 * public CustomMode getSpecificCustomMode(int _id){ CustomMode customMode =
	 * new CustomMode(); Cursor c = mynetDatabase.rawQuery(
	 * "select ModeName,Brightness,TimeOut,Data,Wifi,Bluetooth,AutomaticSync,Silence,Vibration from CustomMode where _id='"
	 * +_id+"'", null); if(c != null && c.getCount()>0){ c.moveToFirst();
	 * customMode.ModeName = c.getString(0); customMode.Brightness =
	 * c.getInt(1); customMode.TimeOut = c.getInt(2); customMode.Data =
	 * c.getInt(3); customMode.Wifi = c.getInt(4); customMode.Bluetooth =
	 * c.getInt(5); customMode.AutomaticSync = c.getInt(6); customMode.Silence =
	 * c.getInt(7); customMode.Vibration = c.getInt(8); } c.close(); return
	 * customMode; }
	 * 
	 * public ArrayList<PhoneSignalStrenght> getSignalStrenghtList() {
	 * ArrayList<PhoneSignalStrenght> list = new
	 * ArrayList<PhoneSignalStrenght>(); Cursor c = mynetDatabase .rawQuery(
	 * "select * FROM PhoneSignalStrength where SignalLevel<100", null);
	 * PhoneSignalStrenght phoneSignalStrenght = null; if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); for (int rowIndex = 0; rowIndex <
	 * c.getCount(); rowIndex++) { phoneSignalStrenght = new
	 * PhoneSignalStrenght(); phoneSignalStrenght.SignalId = c.getInt(0);
	 * phoneSignalStrenght.PhoneId = c.getInt(1);
	 * phoneSignalStrenght.SignalLevel = c.getInt(2);
	 * phoneSignalStrenght.Latitude = c.getDouble(3);
	 * phoneSignalStrenght.Longitude = c.getDouble(4); phoneSignalStrenght.Time
	 * = new Date(c.getLong(5)); list.add(phoneSignalStrenght); } } c.close();
	 * return list; }
	 * 
	 * public PhoneSignalStrenght getAvgSignalStrenght(long lastSyncTime) {
	 * 
	 * 
	 * Cursor c = mynetDatabase.rawQuery(
	 * "select * FROM PhoneSignalStrength where SignalLevel<100 and Time>"
	 * +lastSyncTime +" order by Time", null);
	 * 
	 * Cursor c = mynetDatabase .rawQuery(
	 * "select * FROM PhoneSignalStrength where SignalLevel<100", null);
	 * PhoneSignalStrenght phoneSignalStrenght = null; if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); phoneSignalStrenght = new
	 * PhoneSignalStrenght(); phoneSignalStrenght.SignalId = c.getInt(0);
	 * phoneSignalStrenght.PhoneId = c.getInt(1);
	 * phoneSignalStrenght.SignalLevel = c.getInt(2);
	 * phoneSignalStrenght.Latitude = c.getDouble(3);
	 * phoneSignalStrenght.Longitude = c.getDouble(4); phoneSignalStrenght.Time
	 * = new Date(System.currentTimeMillis()); return phoneSignalStrenght;
	 * 
	 * } return null; }
	 * 
	 * public ArrayList<PhoneSignalStrenght> getSignalStrenght(long
	 * lastSyncTime) { ArrayList<PhoneSignalStrenght> list = new
	 * ArrayList<PhoneSignalStrenght>(); Cursor c = mynetDatabase.rawQuery(
	 * "select * FROM PhoneSignalStrength where SignalLevel<100 and Time>" +
	 * lastSyncTime + " order by SignalId", null);
	 * 
	 * PhoneSignalStrenght phoneSignalStrenght = null; if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); for (int count = 0; count <
	 * c.getCount(); count++) { phoneSignalStrenght = new PhoneSignalStrenght();
	 * phoneSignalStrenght.SignalId = c.getInt(0); phoneSignalStrenght.PhoneId =
	 * c.getInt(1); phoneSignalStrenght.SignalLevel = c.getInt(2);
	 * phoneSignalStrenght.Latitude = c.getDouble(3);
	 * phoneSignalStrenght.Longitude = c.getDouble(4); phoneSignalStrenght.LAC =
	 * c.getString(5); phoneSignalStrenght.CellID = c.getString(6);
	 * phoneSignalStrenght.Time = new Date(c.getLong(7));
	 * list.add(phoneSignalStrenght); c.moveToNext(); }
	 * 
	 * } return list; }
	 * 
	 * public ArrayList<PhoneSignalStrenght> getSignalStrenghtListForSync() {
	 * ArrayList<PhoneSignalStrenght> list = new
	 * ArrayList<PhoneSignalStrenght>(); Cursor c = mynetDatabase .rawQuery(
	 * "select * FROM PhoneSignalStrength where IsSync=0 and Latitude>0 order by SignalId"
	 * , null);
	 * 
	 * PhoneSignalStrenght phoneSignalStrenght = null; if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); for (int count = 0; count <
	 * c.getCount(); count++) { phoneSignalStrenght = new PhoneSignalStrenght();
	 * phoneSignalStrenght.SignalId = c.getInt(0); phoneSignalStrenght.PhoneId =
	 * c.getInt(1); phoneSignalStrenght.SignalLevel = c.getInt(2);
	 * phoneSignalStrenght.Latitude = c.getDouble(3);
	 * phoneSignalStrenght.Longitude = c.getDouble(4); phoneSignalStrenght.LAC =
	 * c.getString(5); phoneSignalStrenght.CellID = c.getString(6);
	 * phoneSignalStrenght.Time = new Date(c.getLong(7));
	 * phoneSignalStrenght.LocationName = c.getString(11);
	 * list.add(phoneSignalStrenght); c.moveToNext(); }
	 * 
	 * } return list; }
	 * 
	 * public int getAgvSignalStrenght() { Cursor c = mynetDatabase.rawQuery(
	 * "select avg(SignalLevel) from PhoneSignalStrength", null); if (c != null
	 * && c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getAgvSignalStrenght(long date) { Cursor c =
	 * mynetDatabase.rawQuery(
	 * "select avg(SignalLevel) from PhoneSignalStrength where Time > " + date,
	 * null); try { if (c != null && c.getCount() > 0) { c.moveToFirst(); return
	 * c.getInt(0); } } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return 0; }
	 * 
	 * public int getAgvSignalStrenght(long dateFrom, long dateTo) { Cursor c =
	 * mynetDatabase.rawQuery(
	 * "select avg(SignalLevel) from PhoneSignalStrength where Time > " +
	 * dateFrom + " and Time < " + dateTo, null); try { if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * return 0; }
	 * 
	 * public int getAgvSignalStrenght(int type) { Calendar cal =
	 * Calendar.getInstance(); cal.set(Calendar.MINUTE, 0);
	 * cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0); String sql
	 * = "select avg(SignalLevel) from PhoneSignalStrength ";
	 * 
	 * if (type == 1) { // cal.add(Calendar.HOUR, -1); sql = sql +
	 * " WHERE Time >" + cal.getTimeInMillis(); } else if (type == 2) {
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0); sql = sql +
	 * " WHERE Time >" + cal.getTimeInMillis(); } else if (type == 3) {
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0);
	 * cal.add(Calendar.DAY_OF_MONTH, -1); sql = sql + " WHERE Time >" +
	 * cal.getTimeInMillis(); cal.add(Calendar.DAY_OF_MONTH, +1); sql = sql +
	 * " and Time <" + cal.getTimeInMillis(); } else if (type == 4) {
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0);
	 * cal.add(Calendar.DAY_OF_MONTH, -7); sql = sql + " WHERE Time >" +
	 * cal.getTimeInMillis(); }
	 * 
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getMaxSignalStrenght() { String sql =
	 * "select SignalLevel from PhoneSignalStrength ORDER BY SignalLevel desc LIMIT 1"
	 * ; try { Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } } catch
	 * (Exception e) { String ss = e.getMessage(); ss = ss + ""; } return 0; }
	 * 
	 * public int getMaxSignalStrength(long date) { String sql =
	 * "select SignalLevel from PhoneSignalStrength where Time > " + date +
	 * " ORDER BY SignalLevel desc LIMIT 1"; try { Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); return c.getInt(0); } } catch (Exception e) { String ss
	 * = e.getMessage(); ss = ss + ""; } return 0; }
	 * 
	 * public int getMaxSignalStrength(long dateFrom, long dateTo) { String sql
	 * = "select SignalLevel from PhoneSignalStrength where Time > " + dateFrom
	 * + " and Time < " + dateTo + " ORDER BY SignalLevel desc LIMIT 1"; try {
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } } catch
	 * (Exception e) { String ss = e.getMessage(); ss = ss + ""; } return 0; }
	 * 
	 * public int getminSignalStrenght() { String sql =
	 * "select SignalLevel from PhoneSignalStrength ORDER BY SignalLevel asc LIMIT 1"
	 * ; Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getMinSignalStrength(long date) { String sql =
	 * "select SignalLevel from PhoneSignalStrength where Time > " + date +
	 * " ORDER BY SignalLevel asc LIMIT 1"; try { Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); return c.getInt(0); } } catch (Exception e) { String ss
	 * = e.getMessage(); ss = ss + ""; } return 0; }
	 * 
	 * public int getMinSignalStrength(long dateFrom, long dateTo) { String sql
	 * = "select SignalLevel from PhoneSignalStrength where Time > " + dateFrom
	 * + " and Time < " + dateTo + " ORDER BY SignalLevel asc LIMIT 1"; try {
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } } catch
	 * (Exception e) { String ss = e.getMessage(); ss = ss + ""; } return 0; }
	 * 
	 * public int getAvgCallDuration() { String sql =
	 * "select avg(DurationInSec) from PhoneCallInformation"; Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getAvgCallDuration(long dateFrom, long dateTo) { String sql =
	 * "select avg( DurationInSec) from PhoneCallInformation where CallTime  >"
	 * + dateFrom + " and CallTime  <" + dateTo; Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getAvgCallDuration(long date) { String sql =
	 * "select avg( DurationInSec) from PhoneCallInformation where CallTime  >"
	 * + date; Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getMaxCallDuration() { String sql =
	 * "select DurationInSec from PhoneCallInformation ORDER BY DurationInSec desc LIMIT 1"
	 * ; Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getMaxCallDuration(long date) { String sql =
	 * "select  DurationInSec from PhoneCallInformation where CallTime > " +
	 * date + " ORDER BY DurationInSec desc LIMIT 1"; Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getMaxCallDuration(long dateFrom, long DateTo) { String sql =
	 * "select  DurationInSec from PhoneCallInformation where CallTime > " +
	 * dateFrom + " and CallTime < " + DateTo +
	 * " ORDER BY DurationInSec desc LIMIT 1"; Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getMinCallDuration() { String sql =
	 * "select DurationInSec from PhoneCallInformation where DurationInSec>0  ORDER BY DurationInSec asc LIMIT 1"
	 * ; Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getMinCallDuration(long date) { String sql =
	 * "select  DurationInSec from PhoneCallInformation where CallTime > " +
	 * date + " ORDER BY DurationInSec asc LIMIT 1"; Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public int getMinCallDuration(long dateFrom, long DateTo) { String sql =
	 * "select  DurationInSec from PhoneCallInformation where CallTime > " +
	 * dateFrom + " and CallTime < " + DateTo +
	 * " ORDER BY DurationInSec asc LIMIT 1"; Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public PhoneDataInformation getAgvDownLoadUploadSpeed(int type) {
	 * 
	 * Calendar cal = Calendar.getInstance(); cal.set(Calendar.MINUTE, 0);
	 * cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0); String sql
	 * =
	 * "select avg(DownLoadSpeed) downspeed,avg(UpLoadSpeed) upspeed from PhoneDataInformation "
	 * ; if (type == 1) { // cal.add(Calendar.HOUR, -1); sql = sql +
	 * " WHERE DataTime >=" + cal.getTimeInMillis(); } else if (type == 2) {
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0); sql = sql +
	 * " WHERE DataTime >=" + cal.getTimeInMillis(); } else if (type == 3) {
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0);
	 * cal.add(Calendar.DAY_OF_MONTH, -1); sql = sql + " WHERE DataTime >=" +
	 * cal.getTimeInMillis(); cal.add(Calendar.DAY_OF_MONTH, 1); sql = sql +
	 * " and DataTime <" + cal.getTimeInMillis(); } else if (type == 4) {
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0);
	 * cal.add(Calendar.DAY_OF_MONTH, -7); sql = sql + " WHERE DataTime >=" +
	 * cal.getTimeInMillis(); }
	 * 
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); PhoneDataInformation
	 * phoneDataInformation = new PhoneDataInformation();
	 * phoneDataInformation.DownLoadSpeed = c.getInt(0);
	 * phoneDataInformation.UpLoadSpeed = c.getInt(1); return
	 * phoneDataInformation; } return null; }
	 * 
	 * public ArrayList<PhoneDataInformation> getDownLoadUploadSpeedList() {
	 * ArrayList<PhoneDataInformation> phoneCallList = new
	 * ArrayList<PhoneDataInformation>(); Cursor c =
	 * mynetDatabase.rawQuery("select * from PhoneDataInformation", null); if (c
	 * != null && c.getCount() > 0) { c.moveToFirst(); PhoneDataInformation
	 * phoneDataInformation = null; for (int rowIndex = 0; rowIndex <
	 * c.getCount(); rowIndex++) { phoneDataInformation = new
	 * PhoneDataInformation(); phoneDataInformation.DataId = c.getInt(0);
	 * phoneDataInformation.PhoneId = c.getInt(1);
	 * phoneDataInformation.DownLoadSpeed = c.getInt(2);
	 * phoneDataInformation.UpLoadSpeed = c.getInt(3);
	 * phoneDataInformation.Latitude = c.getDouble(4);
	 * phoneDataInformation.Longitude = c.getDouble(5);
	 * phoneDataInformation.DataTime = new Date(c.getLong(6));
	 * phoneCallList.add(phoneDataInformation); c.moveToNext(); } } return
	 * phoneCallList; }
	 * 
	 * public ArrayList<PhoneCallInformation> getTotalCallInfo(int type) {
	 * ArrayList<PhoneCallInformation> phoneCallList = new
	 * ArrayList<PhoneCallInformation>();
	 * 
	 * Calendar cal = Calendar.getInstance(); cal.set(Calendar.MINUTE, 0);
	 * cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0); String sql
	 * =
	 * "select CallType, count(Number) CallCount,sum(DurationInSec) duration from PhoneCallInformation"
	 * ; if (type == CommonConstraints.INFO_TYPE_LASTHOUR) { //
	 * cal.add(Calendar.HOUR, -1); sql = sql + " WHERE CallTime >=" +
	 * cal.getTimeInMillis(); } else if (type ==
	 * CommonConstraints.INFO_TYPE_TODAY) { cal.set(Calendar.HOUR, 0);
	 * cal.set(Calendar.HOUR_OF_DAY, 0); sql = sql + " WHERE CallTime >=" +
	 * cal.getTimeInMillis(); } else if (type ==
	 * CommonConstraints.INFO_TYPE_YESTERDAY) { cal.set(Calendar.HOUR, -24); sql
	 * = sql + " WHERE CallTime >=" + cal.getTimeInMillis();
	 * 
	 * } else if (type == CommonConstraints.INFO_TYPE_WEEK) {
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0);
	 * cal.add(Calendar.DAY_OF_MONTH, -7); sql = sql + " WHERE CallTime >=" +
	 * cal.getTimeInMillis(); } else if (type ==
	 * CommonConstraints.INFO_TYPE_MONTH) { cal.set(Calendar.HOUR, 0);
	 * cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.DAY_OF_MONTH, 1); sql
	 * = sql + " WHERE CallTime >=" + cal.getTimeInMillis(); }
	 * 
	 * sql = sql + " group by CallType order by CallType";
	 * 
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); PhoneCallInformation
	 * phoneCallInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.CallType = c.getInt(0);
	 * phoneCallInformation.CallCount = c.getInt(1);
	 * phoneCallInformation.DurationInSec = c.getInt(0) == 3 ? 0 : c .getInt(2);
	 * phoneCallList.add(phoneCallInformation); c.moveToNext(); } } return
	 * phoneCallList; }
	 * 
	 * public ArrayList<PhoneCallInformation> getTotalCallInfoByPhoneNumber() {
	 * ArrayList<PhoneCallInformation> phoneCallList = new
	 * ArrayList<PhoneCallInformation>();
	 * 
	 * Cursor c = mynetDatabase.rawQuery(
	 * "select Number, MAX(CallTime) MaxCallTime, count(CallId) CallCount,sum( CASE when CallType=3 then 0 else DurationInSec end ) duration from PhoneCallInformation group by Number order by MaxCallTime desc"
	 * , null);
	 * 
	 * Cursor c = mynetDatabase .rawQuery(
	 * "select substr(Number, -11) as subnumber,Number, Max(CallTime) callTime,count(CallId) CallCount,sum(CASE when CallType=3 then 0 else DurationInSec end) duration From PhoneCallInformation group by subnumber order by CallTime desc"
	 * , null); if (c != null && c.getCount() > 0) { c.moveToFirst();
	 * PhoneCallInformation phoneCallInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.Number = c.getString(1);
	 * phoneCallInformation.CallTime = new Date(c.getLong(2));
	 * phoneCallInformation.CallCount = c.getInt(3);
	 * phoneCallInformation.DurationInSec = c.getInt(4);
	 * phoneCallList.add(phoneCallInformation); c.moveToNext(); } } return
	 * phoneCallList; }
	 * 
	 * public ArrayList<PhoneSMSInformation> getTotalSMSInfo(int type) {
	 * ArrayList<PhoneSMSInformation> phoneSmsList = new
	 * ArrayList<PhoneSMSInformation>();
	 * 
	 * Calendar cal = Calendar.getInstance(); cal.set(Calendar.MINUTE, 0);
	 * cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0); String sql
	 * = "select SMSType, count(SMSId) CallCount from PhoneSMSInformation"; if
	 * (type == 1) { // cal.add(Calendar.HOUR, -1); sql = sql +
	 * " WHERE SMSTime >=" + cal.getTimeInMillis(); } else if (type == 2) {
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0); sql = sql +
	 * " WHERE SMSTime >=" + cal.getTimeInMillis(); } else if (type == 3) {
	 * cal.set(Calendar.HOUR, -24); sql = sql + " WHERE SMSTime >=" +
	 * cal.getTimeInMillis();
	 * 
	 * cal.set(Calendar.HOUR_OF_DAY, 0); cal.add(Calendar.DAY_OF_MONTH, -1);
	 * sql=sql+ " WHERE SMSTime >="+cal.getTimeInMillis();
	 * cal.add(Calendar.DAY_OF_MONTH, 1); sql=sql+
	 * " and SMSTime <"+cal.getTimeInMillis();
	 * 
	 * } else if (type == 4) { cal.set(Calendar.HOUR, 0);
	 * cal.set(Calendar.HOUR_OF_DAY, 0); cal.add(Calendar.DAY_OF_MONTH, -7); sql
	 * = sql + " WHERE SMSTime >=" + cal.getTimeInMillis(); }
	 * 
	 * sql = sql + " group by SMSType order by SMSType";
	 * 
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); PhoneSMSInformation
	 * phoneSMSInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneSMSInformation = new PhoneSMSInformation();
	 * phoneSMSInformation.SMSType = c.getInt(0); phoneSMSInformation.SMSCount =
	 * c.getInt(1); phoneSmsList.add(phoneSMSInformation); c.moveToNext(); } }
	 * return phoneSmsList; }
	 * 
	 * public int getTotalDataCount() { String sql =
	 * "select count(DataId) from PhoneDataInformation "; Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public ArrayList<PhoneCallInformation> getCallInfoList() { return
	 * getCallInfoList(0); }
	 * 
	 * public ArrayList<PhoneCallInformation> getCallInfoList(long lastSyncTime)
	 * { ArrayList<PhoneCallInformation> phoneCallList = new
	 * ArrayList<PhoneCallInformation>(); String sql =
	 * "select * from PhoneCallInformation "; if (lastSyncTime > 0) sql = sql +
	 * " where Latitude>0 and CallTime>" + lastSyncTime; sql = sql +
	 * " order by CallTime desc"; Cursor c = mynetDatabase.rawQuery(sql, null);
	 * if (c != null && c.getCount() > 0) { c.moveToFirst();
	 * PhoneCallInformation phoneCallInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.CallId = c.getInt(0); phoneCallInformation.PhoneId =
	 * c.getInt(1); phoneCallInformation.CallType = c.getInt(2);
	 * phoneCallInformation.Number = c.getString(3);
	 * phoneCallInformation.DurationInSec = c.getInt(2) == 3 ? 0 : c .getInt(4);
	 * phoneCallInformation.Latitude = c.getDouble(5);
	 * phoneCallInformation.Longitude = c.getDouble(6);
	 * phoneCallInformation.TextCallMemo = c.getString(7);
	 * phoneCallInformation.CallTime = new Date(c.getLong(8));
	 * phoneCallInformation.Reson = c.getString(10); phoneCallInformation.Name =
	 * CommonTask.getContentName(context, phoneCallInformation.Number);
	 * phoneCallList.add(phoneCallInformation); c.moveToNext(); } } return
	 * phoneCallList; }
	 * 
	 * public Cursor getCallInfoListCursor() { String sql =
	 * "select * from PhoneCallInformation order by CallTime desc"; Cursor c =
	 * mynetDatabase.rawQuery(sql, null); return c; }
	 * 
	 * public ArrayList<PhoneCallInformation> getCallInfoListForSync() {
	 * ArrayList<PhoneCallInformation> phoneCallList = new
	 * ArrayList<PhoneCallInformation>(); String sql =
	 * "select * from PhoneCallInformation where IsSync=0 and Latitude>0 order by CallId"
	 * ;
	 * 
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); PhoneCallInformation
	 * phoneCallInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.CallId = c.getInt(0); phoneCallInformation.PhoneId =
	 * c.getInt(1); phoneCallInformation.CallType = c.getInt(2);
	 * phoneCallInformation.Number = c.getString(3);
	 * phoneCallInformation.DurationInSec = c.getInt(2) == 3 ? 0 : c .getInt(4);
	 * phoneCallInformation.Latitude = c.getDouble(5);
	 * phoneCallInformation.Longitude = c.getDouble(6);
	 * phoneCallInformation.TextCallMemo = c.getString(7);
	 * phoneCallInformation.CallTime = new Date(c.getLong(8));
	 * phoneCallInformation.Reson = c.getString(9); phoneCallInformation.CellID
	 * = c.getString(11); phoneCallInformation.LAC = c.getString(12);
	 * 
	 * phoneCallInformation.IsLocal = c.getInt(16) > 0;
	 * phoneCallInformation.LocationName = c.getString(18);
	 * phoneCallList.add(phoneCallInformation); c.moveToNext(); } } return
	 * phoneCallList; }
	 * 
	 * public ArrayList<PhoneCallInformation> getTotalCallInforByNumber(int
	 * type, String number) { ArrayList<PhoneCallInformation> phoneCallList =
	 * new ArrayList<PhoneCallInformation>(); Calendar cal =
	 * Calendar.getInstance(); cal.set(Calendar.MINUTE, 0);
	 * cal.set(Calendar.SECOND, 0); // / number =
	 * CommonTask.trimTentativeCountryCode(number); // / String sql =
	 * "select Number,CallType,CallTime,count(Number) CallCount,sum(CASE when CallType=3 then 0 else DurationInSec end) duration from PhoneCallInformation"
	 * ; if (type == CommonConstraints.INFO_TYPE_ALL) { sql = sql +
	 * " WHERE Number like '%" + number + "%'"; } else if (type ==
	 * CommonConstraints.INFO_TYPE_TODAY) { cal.set(Calendar.HOUR, 0); //
	 * sql=sql
	 * +" WHERE CallTime >="+cal.getTimeInMillis()+" AND Number='"+number+"'";
	 * sql = sql + " WHERE CallTime >=" + cal.getTimeInMillis() +
	 * " AND Number like '%" + number + "%'"; } else if (type ==
	 * CommonConstraints.INFO_TYPE_WEEK) { cal.set(Calendar.HOUR, 0);
	 * cal.add(Calendar.DAY_OF_MONTH, -7); //
	 * sql=sql+" WHERE CallTime >="+cal.getTimeInMillis
	 * ()+" AND Number='"+number+"'"; sql = sql + " WHERE CallTime >=" +
	 * cal.getTimeInMillis() + " AND Number like '%" + number + "%'"; } else if
	 * (type == CommonConstraints.INFO_TYPE_MONTH) { cal.set(Calendar.HOUR, 0);
	 * cal.set(Calendar.DAY_OF_MONTH, 1); //
	 * sql=sql+" WHERE CallTime >="+cal.getTimeInMillis
	 * ()+" AND Number='"+number+"'"; sql = sql + " WHERE CallTime >=" +
	 * cal.getTimeInMillis() + " AND Number like '%" + number + "%'"; } sql =
	 * sql + " group by CallType order by CallType"; Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); PhoneCallInformation phoneCallInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.Number = c.getString(0);
	 * phoneCallInformation.CallType = c.getInt(1);
	 * phoneCallInformation.CallTime = new Date(c.getLong(2));
	 * phoneCallInformation.CallCount = c.getInt(3);
	 * phoneCallInformation.DurationInSec = c.getInt(4);
	 * phoneCallList.add(phoneCallInformation); c.moveToNext(); } } return
	 * phoneCallList; }
	 * 
	 * public ArrayList<PhoneSMSInformation> getSMSInfoListForSync() {
	 * ArrayList<PhoneSMSInformation> phoneSmsList = new
	 * ArrayList<PhoneSMSInformation>(); String sql =
	 * "select * from PhoneSMSInformation where IsSync=0 and Latitude>0 order by SMSId"
	 * ;
	 * 
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); PhoneSMSInformation
	 * phoneSmsInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneSmsInformation = new PhoneSMSInformation();
	 * phoneSmsInformation.SMSId = c.getInt(0); phoneSmsInformation.PhoneId =
	 * c.getInt(1); phoneSmsInformation.SMSType = c.getInt(2);
	 * phoneSmsInformation.Number = c.getString(3); phoneSmsInformation.SMSBody
	 * = c.getString(4); phoneSmsInformation.Latitude = c.getDouble(5);
	 * phoneSmsInformation.Longitude = c.getDouble(6);
	 * phoneSmsInformation.SMSTime = new Date(c.getLong(7));
	 * phoneSmsInformation.LAC = c.getString(10); phoneSmsInformation.CellID =
	 * c.getString(11); phoneSmsInformation.IsLocal = c.getInt(14) > 0;
	 * phoneSmsInformation.LocationName = c.getString(15);
	 * phoneSmsList.add(phoneSmsInformation); c.moveToNext(); } } return
	 * phoneSmsList; }
	 * 
	 * public ArrayList<PhoneSMSInformation> getSMSInfoList(long lastSyncTime) {
	 * ArrayList<PhoneSMSInformation> phoneSmsList = new
	 * ArrayList<PhoneSMSInformation>(); String sql =
	 * "select * from PhoneSMSInformation "; if (lastSyncTime > 0) sql = sql +
	 * " where Latitude>0 and SMSTime>" + lastSyncTime; sql = sql +
	 * " order by SMSTime desc"; Cursor c = mynetDatabase.rawQuery(sql, null);
	 * if (c != null && c.getCount() > 0) { c.moveToFirst(); PhoneSMSInformation
	 * phoneSmsInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneSmsInformation = new PhoneSMSInformation();
	 * phoneSmsInformation.SMSId = c.getInt(0); phoneSmsInformation.PhoneId =
	 * c.getInt(1); phoneSmsInformation.SMSType = c.getInt(2);
	 * phoneSmsInformation.Number = c.getString(3); phoneSmsInformation.SMSBody
	 * = c.getString(4); phoneSmsInformation.Latitude = c.getDouble(5);
	 * phoneSmsInformation.Longitude = c.getDouble(6);
	 * phoneSmsInformation.SMSTime = new Date(c.getLong(7));
	 * phoneSmsInformation.Name = CommonTask.getContentName(context,
	 * phoneSmsInformation.Number); phoneSmsList.add(phoneSmsInformation);
	 * c.moveToNext(); } } return phoneSmsList; }
	 * 
	 * public ArrayList<PhoneSMSInformation> getSMSInfoListByPageIndex( int
	 * pageIndex, ArrayList<PhoneSMSInformation> sms, boolean isAllNeeded) {
	 * ArrayList<PhoneSMSInformation> phoneSmsList = sms; String sql =
	 * "select SMSId,PhoneId,SMSType,Number,SMSBody,SMSTime from PhoneSMSInformation"
	 * ; // sql=sql+" where SMSId Between " +(pageIndex*40+1) + " and " //
	 * +(pageIndex+1)*40; sql = sql + " order by SMSTime desc  limit 40 OFFSET "
	 * + ((pageIndex - 1) * 40);
	 * 
	 * if(!isAllNeeded)
	 * sql+="limit "+((pageIndex*20)+1)+" OFFSET "+((pageIndex-1)*20); // Should
	 * change here Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null
	 * && c.getCount() > 0) { c.moveToFirst(); PhoneSMSInformation
	 * phoneSmsInformation = null; for (int rowIndex = 0; rowIndex <
	 * c.getCount(); rowIndex++) { phoneSmsInformation = new
	 * PhoneSMSInformation(); phoneSmsInformation.SMSId =
	 * CommonTask.TryParseInt(c .getString(0)); phoneSmsInformation.PhoneId =
	 * CommonTask.TryParseInt(c .getString(1)); phoneSmsInformation.SMSType =
	 * CommonTask.TryParseInt(c .getString(2)); phoneSmsInformation.Number =
	 * c.getString(3); phoneSmsInformation.SMSBody = c.getString(4); //
	 * phoneSmsInformation.Latitude=CommonTask.TryParseDouble(c.getString(5));
	 * //
	 * phoneSmsInformation.Longitude=CommonTask.TryParseDouble(c.getString(6));
	 * phoneSmsInformation.SMSTime = new Date(
	 * CommonTask.TryParseLong(c.getString(5))); phoneSmsInformation.Name =
	 * phoneSmsInformation.Number != null ? CommonTask .getContentName(context,
	 * phoneSmsInformation.Number) : ""; phoneSmsList.add(phoneSmsInformation);
	 * c.moveToNext(); } } return phoneSmsList; }
	 * 
	 * public ArrayList<PhoneCallInformation> getCallInfoListByPageIndex( int
	 * pageIndex, ArrayList<PhoneCallInformation> call) {
	 * ArrayList<PhoneCallInformation> phoneCallList = call; // String // sql=
	 * "select CallId,PhoneId,CallType,Number,DurationInSec,CallTime  from PhoneCallInformation order by CallTime  desc limit "
	 * +((pageIndex*20)+1)+" OFFSET "+((pageIndex-1)*20); // // Should change
	 * here String sql =
	 * "select CallId,PhoneId,CallType,Number,DurationInSec,CallTime  from PhoneCallInformation"
	 * ; // sql=sql+" where CallId Between " +(pageIndex*40+1) + " and " //
	 * +(pageIndex+1)*40; sql = sql + " order by CallTime desc limit 40 OFFSET "
	 * + ((pageIndex - 1) * 40);
	 * 
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); PhoneCallInformation
	 * phoneCallInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.CallId = CommonTask.TryParseInt(c .getString(0));
	 * phoneCallInformation.PhoneId = CommonTask.TryParseInt(c .getString(1));
	 * phoneCallInformation.CallType = CommonTask.TryParseInt(c .getString(2));
	 * phoneCallInformation.Number = c.getString(3);
	 * phoneCallInformation.DurationInSec = CommonTask.TryParseInt(c
	 * .getString(2)) == 3 ? 0 : CommonTask.TryParseInt(c .getString(4)); //
	 * phoneCallInformation.Latitude=CommonTask.TryParseDouble(c.getString(5));
	 * //
	 * phoneCallInformation.Longitude=CommonTask.TryParseDouble(c.getString(6));
	 * // phoneCallInformation.TextCallMemo = c.getString(7);
	 * phoneCallInformation.CallTime = new Date(
	 * CommonTask.TryParseLong(c.getString(5))); //
	 * phoneCallInformation.Reson=c.getString(10); phoneCallInformation.Name =
	 * phoneCallInformation.Number != null ? CommonTask .getContentName(context,
	 * phoneCallInformation.Number) : "";
	 * phoneCallList.add(phoneCallInformation); c.moveToNext(); } } return
	 * phoneCallList; }
	 * 
	 * public ArrayList<PhoneSMSInformation> getSMSInfoList() { return
	 * getSMSInfoList(0); }
	 * 
	 * public PhoneDataInformation getAvgDataInfoByTime(long lastSyncTime) {
	 * 
	 * String sql = "SELECT * FROM PhoneDataInformation " + " where DataTime > "
	 * + lastSyncTime + " order by DataTime desc";
	 * 
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); PhoneDataInformation
	 * phoneDataInformation = new PhoneDataInformation();
	 * phoneDataInformation.DataId = c.getInt(0); phoneDataInformation.PhoneId =
	 * c.getInt(1); phoneDataInformation.DownLoadSpeed = c.getInt(2);
	 * phoneDataInformation.UpLoadSpeed = c.getInt(3);
	 * phoneDataInformation.Latitude = c.getDouble(4);
	 * phoneDataInformation.Longitude = c.getDouble(5);
	 * phoneDataInformation.DataTime = new Date(c.getLong(6)); return
	 * phoneDataInformation; } return null; }
	 * 
	 * public ArrayList<PhoneDataInformation> getDataInfoList() {
	 * ArrayList<PhoneDataInformation> phoneDataList = new
	 * ArrayList<PhoneDataInformation>(); Cursor c =
	 * mynetDatabase.rawQuery("select * from PhoneDataInformation", null); if (c
	 * != null && c.getCount() > 0) { c.moveToFirst(); PhoneDataInformation
	 * phoneDataInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneDataInformation = new PhoneDataInformation();
	 * phoneDataInformation.DataId = c.getInt(0); phoneDataInformation.PhoneId =
	 * c.getInt(1); phoneDataInformation.DownLoadSpeed = c.getInt(2);
	 * phoneDataInformation.UpLoadSpeed = c.getInt(3);
	 * phoneDataInformation.Latitude = c.getDouble(4);
	 * phoneDataInformation.Longitude = c.getDouble(5);
	 * phoneDataInformation.DataTime = new Date(c.getLong(6));
	 * phoneDataList.add(phoneDataInformation); c.moveToNext(); } } return
	 * phoneDataList; }
	 * 
	 * public ArrayList<Object> getUsersHistry(long date) {
	 * 
	 * ArrayList<Object> userHistryList = new ArrayList<Object>();
	 * ArrayList<PhoneCallInformation> phoneCallList = new
	 * ArrayList<PhoneCallInformation>(); ArrayList<PhoneSMSInformation>
	 * phoneSmsList = new ArrayList<PhoneSMSInformation>();
	 * 
	 * String Callquery = "SELECT * FROM PhoneCallInformation WHERE CallTime>='"
	 * + String.valueOf(date) + "' order by CallTime desc"; Cursor callHistry =
	 * mynetDatabase.rawQuery(Callquery, null); if (callHistry != null &&
	 * callHistry.getCount() > 0) { callHistry.moveToFirst();
	 * PhoneCallInformation phoneCallInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < callHistry.getCount(); rowIndex++) {
	 * phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.CallId = callHistry.getInt(0);
	 * phoneCallInformation.PhoneId = callHistry.getInt(1);
	 * phoneCallInformation.CallType = callHistry.getInt(2);
	 * phoneCallInformation.Number = callHistry.getString(3);
	 * phoneCallInformation.DurationInSec = callHistry.getInt(2) == 3 ? 0 :
	 * callHistry.getInt(4); phoneCallInformation.Latitude =
	 * callHistry.getDouble(5); phoneCallInformation.Longitude =
	 * callHistry.getDouble(6); phoneCallInformation.TextCallMemo =
	 * callHistry.getString(7); phoneCallInformation.CallTime = new
	 * Date(callHistry.getLong(8)); phoneCallInformation.Reson =
	 * callHistry.getString(9);
	 * 
	 * phoneCallList.add(phoneCallInformation); callHistry.moveToNext(); } }
	 * userHistryList.add(phoneCallList);
	 * 
	 * String SMSquery = "SELECT * FROM PhoneSMSInformation WHERE SMSTime>='" +
	 * String.valueOf(date) + "' order by SMSTime desc"; Cursor c =
	 * mynetDatabase.rawQuery(SMSquery, null); if (c != null && c.getCount() >
	 * 0) { c.moveToFirst(); PhoneSMSInformation phoneSmsInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneSmsInformation = new PhoneSMSInformation();
	 * phoneSmsInformation.SMSId = c.getInt(0); phoneSmsInformation.PhoneId =
	 * c.getInt(1); phoneSmsInformation.SMSType = c.getInt(2);
	 * phoneSmsInformation.Number = c.getString(3); phoneSmsInformation.SMSBody
	 * = c.getString(4); phoneSmsInformation.Latitude = c.getDouble(5);
	 * phoneSmsInformation.Longitude = c.getDouble(6);
	 * phoneSmsInformation.SMSTime = new Date(c.getLong(7));
	 * phoneSmsList.add(phoneSmsInformation); c.moveToNext(); } }
	 * userHistryList.add(phoneSmsList); return userHistryList; }
	 * 
	 * public ArrayList<Object> getUsersHistry(long dateFrom, long dateTo) {
	 * 
	 * ArrayList<Object> userHistryList = new ArrayList<Object>();
	 * ArrayList<PhoneCallInformation> phoneCallList = new
	 * ArrayList<PhoneCallInformation>(); ArrayList<PhoneSMSInformation>
	 * phoneSmsList = new ArrayList<PhoneSMSInformation>();
	 * 
	 * String Callquery = "SELECT * FROM PhoneCallInformation WHERE CallTime>="
	 * + dateFrom + " and CallTime<" + dateTo + " order by CallTime desc";
	 * Cursor callHistry = mynetDatabase.rawQuery(Callquery, null); if
	 * (callHistry != null && callHistry.getCount() > 0) {
	 * callHistry.moveToFirst(); PhoneCallInformation phoneCallInformation =
	 * null;
	 * 
	 * for (int rowIndex = 0; rowIndex < callHistry.getCount(); rowIndex++) {
	 * phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.CallId = callHistry.getInt(0);
	 * phoneCallInformation.PhoneId = callHistry.getInt(1);
	 * phoneCallInformation.CallType = callHistry.getInt(2);
	 * phoneCallInformation.Number = callHistry.getString(3);
	 * phoneCallInformation.DurationInSec = callHistry.getInt(2) == 3 ? 0 :
	 * callHistry.getInt(4); phoneCallInformation.Latitude =
	 * callHistry.getDouble(5); phoneCallInformation.Longitude =
	 * callHistry.getDouble(6); phoneCallInformation.TextCallMemo =
	 * callHistry.getString(7); phoneCallInformation.CallTime = new
	 * Date(callHistry.getLong(8)); phoneCallInformation.Reson =
	 * callHistry.getString(9);
	 * 
	 * phoneCallList.add(phoneCallInformation); callHistry.moveToNext(); } }
	 * userHistryList.add(phoneCallList);
	 * 
	 * String SMSquery = "SELECT * FROM PhoneSMSInformation WHERE SMSTime>=" +
	 * dateFrom + " and SMSTime<" + dateTo + " order by SMSTime desc"; Cursor c
	 * = mynetDatabase.rawQuery(SMSquery, null); if (c != null && c.getCount() >
	 * 0) { c.moveToFirst(); PhoneSMSInformation phoneSmsInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneSmsInformation = new PhoneSMSInformation();
	 * phoneSmsInformation.SMSId = c.getInt(0); phoneSmsInformation.PhoneId =
	 * c.getInt(1); phoneSmsInformation.SMSType = c.getInt(2);
	 * phoneSmsInformation.Number = c.getString(3); phoneSmsInformation.SMSBody
	 * = c.getString(4); phoneSmsInformation.Latitude = c.getDouble(5);
	 * phoneSmsInformation.Longitude = c.getDouble(6);
	 * phoneSmsInformation.SMSTime = new Date(c.getLong(7));
	 * phoneSmsList.add(phoneSmsInformation); c.moveToNext(); } }
	 * userHistryList.add(phoneSmsList); return userHistryList; }
	 * 
	 * public ArrayList<PhoneCallInformation> getCallInformation(String number)
	 * { ArrayList<PhoneCallInformation> phoneCallList = new
	 * ArrayList<PhoneCallInformation>(); String query =
	 * "SELECT * FROM PhoneCallInformation WHERE Number='" + number +
	 * "'order by  CallTime desc"; Cursor c = mynetDatabase.rawQuery(query,
	 * null); if (c != null && c.getCount() > 0) { c.moveToFirst();
	 * PhoneCallInformation phoneCallInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.CallId = c.getInt(0); phoneCallInformation.PhoneId =
	 * c.getInt(1); phoneCallInformation.CallType = c.getInt(2);
	 * phoneCallInformation.Number = c.getString(3);
	 * phoneCallInformation.DurationInSec = c.getInt(2) == 3 ? 0 : c .getInt(4);
	 * phoneCallInformation.Latitude = c.getDouble(5);
	 * phoneCallInformation.Longitude = c.getDouble(6);
	 * phoneCallInformation.TextCallMemo = c.getString(7);
	 * phoneCallInformation.CallTime = new Date(c.getLong(8));
	 * phoneCallInformation.Reson = c.getString(9);
	 * 
	 * phoneCallList.add(phoneCallInformation); c.moveToNext(); } } return
	 * phoneCallList; }
	 * 
	 * public ArrayList<PhoneSMSInformation> getSMSInformation(String number) {
	 * ArrayList<PhoneSMSInformation> smsList = new
	 * ArrayList<PhoneSMSInformation>(); String query =
	 * "SELECT * FROM PhoneSMSInformation WHERE Number='" + number +
	 * "'order by SMSTime asc"; Cursor c = mynetDatabase.rawQuery(query, null);
	 * if (c != null && c.getCount() > 0) { c.moveToFirst(); PhoneSMSInformation
	 * phoneSMSInformation = null; for (int rowIndex = 0; rowIndex <
	 * c.getCount(); rowIndex++) { phoneSMSInformation = new
	 * PhoneSMSInformation(); phoneSMSInformation.SMSId = c.getInt(0);
	 * phoneSMSInformation.PhoneId = c.getInt(1); phoneSMSInformation.SMSType =
	 * c.getInt(2); phoneSMSInformation.Number = c.getString(3);
	 * phoneSMSInformation.SMSBody = c.getString(4);
	 * phoneSMSInformation.Latitude = c.getDouble(5);
	 * phoneSMSInformation.Longitude = c.getDouble(6);
	 * phoneSMSInformation.SMSTime = new Date(c.getLong(7));
	 * smsList.add(phoneSMSInformation); c.moveToNext(); } } return smsList; }
	 * 
	 * public int CreateReportProblemAndBadExperience(
	 * ReportProblemAndBadExperience rpbe) { ContentValues contentValues = new
	 * ContentValues(); contentValues.put("Latitude", rpbe.Latitude);
	 * contentValues.put("Longitude", rpbe.Longitude);
	 * contentValues.put("LocationName", rpbe.LocationName);
	 * contentValues.put("RxLevel", rpbe.RxLevel); contentValues.put("Problem",
	 * rpbe.Problem); contentValues.put("ProblemTime",
	 * CommonTask.convertDatetoLong(rpbe.ProblemTime));
	 * contentValues.put("ReportTime",
	 * CommonTask.convertDatetoLong(rpbe.ReportTime));
	 * contentValues.put("Status", rpbe.Status); contentValues.put("Comment",
	 * rpbe.Comment); contentValues.put("ProblemType", rpbe.ProblemType);
	 * contentValues.put("Failed", rpbe.Failed);
	 * contentValues.put("ProblemDetailCategory", rpbe.ProblemDetailCategory);
	 * contentValues.put("ProblemDetailSubCategory",
	 * rpbe.ProblemDetailSubCategory); contentValues.put("Remarks",
	 * rpbe.Remarks); contentValues.put("Extra1", rpbe.Extra1);
	 * contentValues.put("Extra2", rpbe.Extra2);
	 * contentValues.put("ProblemHeader", rpbe.problemHeader);
	 * contentValues.put("LatestFeedBack", rpbe.LatestFeedBack);
	 * contentValues.put("TTNumber", rpbe.TTNumber); contentValues.put("IsSync",
	 * 0); return (int) mynetDatabase.insertOrThrow(
	 * "ReportProblemAndBadExperience", null, contentValues); }
	 * 
	 * public ArrayList<ReportProblemAndBadExperience>
	 * getReportProblemAndBadExperience() {
	 * ArrayList<ReportProblemAndBadExperience> rpbeList = new
	 * ArrayList<ReportProblemAndBadExperience>(); String query =
	 * "SELECT * FROM ReportProblemAndBadExperience order by  ProblemTime desc";
	 * Cursor c = mynetDatabase.rawQuery(query, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); ReportProblemAndBadExperience rpbe =
	 * null; for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) { rpbe
	 * = new ReportProblemAndBadExperience(); rpbe.SLNO = c.getInt(0);
	 * rpbe.Latitude = c.getDouble(1); rpbe.Longitude = c.getDouble(2);
	 * rpbe.LocationName = c.getString(3); rpbe.RxLevel = c.getString(4);
	 * rpbe.Problem = c.getString(5); rpbe.ProblemDate = new Date(c.getLong(6));
	 * rpbe.ReportDate = new Date(c.getLong(7)); rpbe.ProblemTime =
	 * String.valueOf(c.getLong(6)); rpbe.ReportTime =
	 * String.valueOf(c.getLong(7)); rpbe.Status = c.getString(8); rpbe.Comment
	 * = c.getString(9); rpbe.ProblemType = c.getInt(10) == 1 ? true : false;
	 * rpbe.Failed = c.getInt(11); rpbe.ProblemDetailCategory = c.getString(12);
	 * rpbe.ProblemDetailSubCategory = c.getString(13); rpbe.Remarks =
	 * c.getString(14); rpbe.Extra1 = c.getString(15); rpbe.Extra2 =
	 * c.getString(16); rpbe.problemHeader = c.getString(17);
	 * rpbeList.add(rpbe); c.moveToNext(); } } return rpbeList; }
	 * 
	 * public ArrayList<ReportProblemAndBadExperience>
	 * getCountOfDropAndBlockCalls( int type) {
	 * ArrayList<ReportProblemAndBadExperience>
	 * reportProblemAndBadExperienceList = new
	 * ArrayList<ReportProblemAndBadExperience>(); Calendar cal =
	 * Calendar.getInstance(); String sql =
	 * "select Problem, count(Problem) Failed from ReportProblemAndBadExperience"
	 * ; if (type == CommonConstraints.INFO_TYPE_LASTHOUR) {
	 * cal.add(Calendar.HOUR, -1); sql = sql + " WHERE ProblemTime >=" +
	 * cal.getTimeInMillis(); } else if (type ==
	 * CommonConstraints.INFO_TYPE_TODAY) { cal.set(Calendar.HOUR, 0);
	 * cal.set(Calendar.HOUR_OF_DAY, 0); sql = sql + " WHERE ProblemTime >=" +
	 * cal.getTimeInMillis(); } else if (type ==
	 * CommonConstraints.INFO_TYPE_YESTERDAY) { cal.set(Calendar.HOUR, -24);
	 * 
	 * cal.set(Calendar.HOUR_OF_DAY, 0); cal.add(Calendar.DAY_OF_MONTH, -1);
	 * 
	 * sql = sql + " WHERE ProblemTime >=" + cal.getTimeInMillis();
	 * 
	 * cal.add(Calendar.DAY_OF_MONTH, 1); sql=sql+
	 * " and ProblemTime <"+cal.getTimeInMillis() + ")";
	 * 
	 * } else if (type == CommonConstraints.INFO_TYPE_WEEK) {
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0);
	 * cal.add(Calendar.DAY_OF_MONTH, -7); sql = sql + " WHERE ProblemTime >=" +
	 * cal.getTimeInMillis(); }
	 * 
	 * sql = sql +
	 * " and (Problem = 'Dropped Call' or Problem = 'Blocked Call')";
	 * 
	 * sql = sql + " group by Problem";
	 * 
	 * Cursor c = mynetDatabase.rawQuery(sql, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); ReportProblemAndBadExperience
	 * reportProblemAndBadExperience = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * reportProblemAndBadExperience = new ReportProblemAndBadExperience();
	 * reportProblemAndBadExperience.Problem = c.getString(0);
	 * reportProblemAndBadExperience.Failed = c.getInt(1);
	 * reportProblemAndBadExperienceList .add(reportProblemAndBadExperience);
	 * c.moveToNext(); } } return reportProblemAndBadExperienceList; }
	 * 
	 * public ArrayList<ReportProblemAndBadExperience>
	 * getReportProblemAndBadExperienceForSync() {
	 * ArrayList<ReportProblemAndBadExperience> rpbeList = new
	 * ArrayList<ReportProblemAndBadExperience>(); String query =
	 * "SELECT * FROM ReportProblemAndBadExperience where IsSync=0"; Cursor c =
	 * mynetDatabase.rawQuery(query, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); ReportProblemAndBadExperience rpbe = null; for (int
	 * rowIndex = 0; rowIndex < c.getCount(); rowIndex++) { rpbe = new
	 * ReportProblemAndBadExperience(); rpbe.SLNO = c.getInt(0); rpbe.Latitude =
	 * c.getDouble(1); rpbe.Longitude = c.getDouble(2); rpbe.LocationName =
	 * c.getString(3); rpbe.RxLevel = c.getString(4); rpbe.Problem =
	 * c.getString(5); rpbe.ProblemDate = new Date(c.getLong(6));
	 * rpbe.ReportDate = new Date(c.getLong(7)); rpbe.ProblemTime =
	 * String.valueOf(c.getLong(6)); rpbe.ReportTime =
	 * String.valueOf(c.getLong(7)); rpbe.Status = c.getString(8); rpbe.Comment
	 * = c.getString(9); rpbe.ProblemType = c.getInt(10) == 1 ? true : false;
	 * rpbe.Failed = c.getInt(11); rpbe.ProblemDetailCategory = c.getString(12);
	 * rpbe.ProblemDetailSubCategory = c.getString(13); rpbe.Remarks =
	 * c.getString(14); rpbe.Extra1 = c.getString(15); rpbe.Extra2 =
	 * c.getString(16); rpbe.problemHeader = c.getString(17);
	 * rpbeList.add(rpbe); c.moveToNext(); } } return rpbeList; }
	 * 
	 * public void deleteFacebook_person() {
	 * mynetDatabase.rawQuery("delete  FROM Facebook_Person", null); }
	 * 
	 * public FacebokPerson getFacebokPerson() { FacebokPerson facebokPerson =
	 * new FacebokPerson(); String query =
	 * "SELECT * FROM Facebook_Person order by FBNo desc limit 1"; Cursor c =
	 * mynetDatabase.rawQuery(query, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); facebokPerson.FBNo = c.getInt(0);
	 * facebokPerson.FB_UserID = c.getString(1); facebokPerson.FB_UserName =
	 * c.getString(2); facebokPerson.Name = c.getString(3);
	 * facebokPerson.PrimaryEmail = c.getString(4); facebokPerson.PP_Path =
	 * c.getString(5); facebokPerson.Gender = c.getString(6);
	 * facebokPerson.Relationship_Status = c.getString(7);
	 * facebokPerson.DateOfBirth = c.getString(8); facebokPerson.Religion =
	 * c.getString(9); facebokPerson.Professional_Skills = c.getString(10);
	 * facebokPerson.About = c.getString(11); facebokPerson.Pages =
	 * c.getString(12); facebokPerson.Groups = c.getString(13);
	 * facebokPerson.Apps = c.getString(14); facebokPerson.isSync =
	 * c.getInt(15); }
	 * 
	 * return facebokPerson; }
	 * 
	 * public ArrayList<FacebookQualificationExperience>
	 * getFacebook_Qualification_Experience() {
	 * ArrayList<FacebookQualificationExperience>
	 * facebookQualificationExperienceList = new
	 * ArrayList<FacebookQualificationExperience>(); String query =
	 * "SELECT * FROM Facebook_Qualification_Experience"; Cursor c =
	 * mynetDatabase.rawQuery(query, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); FacebookQualificationExperience
	 * facebookQualificationExperience = null; for (int rowIndex = 0; rowIndex <
	 * c.getCount(); rowIndex++) { facebookQualificationExperience = new
	 * FacebookQualificationExperience(); facebookQualificationExperience.FBQENo
	 * = c.getInt(0); facebookQualificationExperience.FBNo = c.getInt(1);
	 * facebookQualificationExperience.QualificationExperience = c
	 * .getString(2); facebookQualificationExperience.Position = c.getString(3);
	 * facebookQualificationExperience.Duration_From = c.getString(4);
	 * facebookQualificationExperience.Duration_To = c.getString(5);
	 * facebookQualificationExperience.QualificationExperienceType = c
	 * .getString(6); facebookQualificationExperience.isWorkSpace = c.getInt(7)
	 * == 1 ? true : false; facebookQualificationExperience.isSync =
	 * c.getInt(8); facebookQualificationExperienceList
	 * .add(facebookQualificationExperience); c.moveToNext(); } } return
	 * facebookQualificationExperienceList; }
	 * 
	 * public int CreateFacebookPerson(FacebokPerson facebookPerson) {
	 * ContentValues contentValues = new ContentValues();
	 * contentValues.put("FB_UserID", facebookPerson.FB_UserID);
	 * contentValues.put("FB_UserName", facebookPerson.FB_UserName);
	 * contentValues.put("Name", facebookPerson.Name);
	 * contentValues.put("PrimaryEmail", facebookPerson.PrimaryEmail);
	 * contentValues.put("PP_Path", facebookPerson.PP_Path);
	 * contentValues.put("Gender", facebookPerson.Gender);
	 * contentValues.put("Relationship_Status",
	 * facebookPerson.Relationship_Status); contentValues.put("DateOfBirth",
	 * facebookPerson.DateOfBirth); contentValues.put("Religion",
	 * facebookPerson.Religion); contentValues.put("Professional_Skills",
	 * facebookPerson.Professional_Skills); contentValues.put("About",
	 * facebookPerson.About); contentValues.put("Pages", facebookPerson.Pages);
	 * contentValues.put("Groups", facebookPerson.Groups);
	 * contentValues.put("Apps", facebookPerson.Apps);
	 * contentValues.put("IsSync", 0); return (int)
	 * mynetDatabase.insertOrThrow("Facebook_Person", null, contentValues); }
	 * 
	 * public int CreateFacebook_Qualification_Experience(
	 * FacebookQualificationExperience facebookQualificationExperience) {
	 * ContentValues contentValues = new ContentValues();
	 * contentValues.put("FBNo", facebookQualificationExperience.FBNo);
	 * contentValues.put("Qualification_Experience",
	 * facebookQualificationExperience.QualificationExperience);
	 * contentValues.put("Position", facebookQualificationExperience.Position);
	 * contentValues.put("Duration_From",
	 * facebookQualificationExperience.Duration_From);
	 * contentValues.put("Duration_To",
	 * facebookQualificationExperience.Duration_To);
	 * contentValues.put("Qualification_Experience_Type",
	 * facebookQualificationExperience.QualificationExperienceType);
	 * contentValues.put("isWorkSpace", 0); contentValues.put("IsSync", 0);
	 * return (int) mynetDatabase.insertOrThrow(
	 * "Facebook_Qualification_Experience", null, contentValues); }
	 * 
	 * public int CreateFacebookFriends(FacebookFriends facebookFriends) {
	 * ContentValues contentValues = new ContentValues();
	 * contentValues.put("FBFRD_Name", facebookFriends.FriendName);
	 * contentValues.put("FBFRD_UserID", facebookFriends.FriendsID); return
	 * (int) mynetDatabase.insertOrThrow("Facebook_Friends",
	 * null,contentValues); }
	 * 
	 * public void deleteFacebook_Friends() {
	 * mynetDatabase.execSQL("delete FROM Facebook_Friends"); }
	 * 
	 * public ArrayList<FacebookFriends> getFacebookFriends() {
	 * ArrayList<FacebookFriends> facebookFriendsList = new
	 * ArrayList<FacebookFriends>(); String query =
	 * "SELECT * FROM Facebook_Friends"; Cursor c =
	 * mynetDatabase.rawQuery(query, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); FacebookFriends facebookFriends = null; for (int
	 * rowIndex = 0; rowIndex < c.getCount(); rowIndex++) { facebookFriends =
	 * new FacebookFriends(); facebookFriends.FriendName = c.getString(1);
	 * facebookFriends.FriendsID = c.getString(2);
	 * facebookFriendsList.add(facebookFriends); c.moveToNext(); } } return
	 * facebookFriendsList; }
	 * 
	 * public ArrayList<UserActivityInformation> getUserActivityInformation(
	 * String othersPhoneNumber) { ArrayList<UserActivityInformation>
	 * userActivityInformationList = new ArrayList<UserActivityInformation>();
	 * 
	 * othersPhoneNumber = CommonTask
	 * .trimTentativeCountryCode(othersPhoneNumber);
	 * 
	 * String query =
	 * "Select ActivityTypeID,ActivityDetails,ActivityTime,Number from (SELECT 1 as ActivityTypeID,Number as ActivityDetails,CallTime as ActivityTime,Number FROM PhoneCallInformation union all SELECT 2 as ActivityTypeID,SMSBody as ActivityDetails,SMSTime as ActivityTime,Number FROM PhoneSMSInformation) as a "
	 * + "where a.number like '%" + othersPhoneNumber +
	 * "%' order by ActivityTime desc limit 3"; Cursor c =
	 * mynetDatabase.rawQuery(query, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); UserActivityInformation userActivityInformation = null;
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * userActivityInformation = new UserActivityInformation();
	 * userActivityInformation.ActivityTypeID = c.getInt(0);
	 * userActivityInformation.ActivityDetails = c.getString(1);
	 * userActivityInformation.ActivityTime = c.getString(2);
	 * userActivityInformation.Number = c.getString(3);
	 * userActivityInformationList.add(userActivityInformation); c.moveToNext();
	 * } } return userActivityInformationList; }
	 * 
	 * public ArrayList<UserLocationActivityInformation>
	 * getTotalCallAndSMSInforByNumber( int type, String number) {
	 * ArrayList<UserLocationActivityInformation>
	 * userLocationActivityInformationList = new
	 * ArrayList<UserLocationActivityInformation>(); Calendar cal =
	 * Calendar.getInstance(); cal.set(Calendar.MINUTE, 0);
	 * cal.set(Calendar.SECOND, 0); // / number =
	 * CommonTask.trimTentativeCountryCode(number); // / String sql =
	 * "Select ActivityTypeID,Latitude,Longitude,ActivityTime,Number,ActivityType from (SELECT 1 as ActivityTypeID,Latitude,Longitude,CallTime as ActivityTime,Number,CallType as ActivityType FROM PhoneCallInformation union all SELECT 2 as ActivityTypeID,Latitude,Longitude,SMSTime as ActivityTime,Number,SMSType AS ActivityType FROM PhoneSMSInformation) as a "
	 * ; if (type == CommonConstraints.INFO_TYPE_ALL) { sql = sql +
	 * " WHERE a.Number like '%" + number + "%'"; } else if (type ==
	 * CommonConstraints.INFO_TYPE_TODAY) { cal.set(Calendar.HOUR, 0); //
	 * sql=sql
	 * +" WHERE CallTime >="+cal.getTimeInMillis()+" AND Number='"+number+"'";
	 * sql = sql + " WHERE a.ActivityTime >=" + cal.getTimeInMillis() +
	 * " AND a.Number like '%" + number + "%'"; } else if (type ==
	 * CommonConstraints.INFO_TYPE_WEEK) { cal.set(Calendar.HOUR, 0);
	 * cal.add(Calendar.DAY_OF_MONTH, -7); //
	 * sql=sql+" WHERE CallTime >="+cal.getTimeInMillis
	 * ()+" AND Number='"+number+"'"; sql = sql + " WHERE a.ActivityTime >=" +
	 * cal.getTimeInMillis() + " AND a.Number like '%" + number + "%'"; } else
	 * if (type == CommonConstraints.INFO_TYPE_MONTH) { cal.set(Calendar.HOUR,
	 * 0); cal.set(Calendar.DAY_OF_MONTH, 1); //
	 * sql=sql+" WHERE CallTime >="+cal
	 * .getTimeInMillis()+" AND Number='"+number+"'"; sql = sql +
	 * " WHERE a.ActivityTime >=" + cal.getTimeInMillis() +
	 * " AND a.Number like '%" + number + "%'"; } Cursor c =
	 * mynetDatabase.rawQuery(sql, null); if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); UserLocationActivityInformation
	 * userLocationActivityInformation = null;
	 * 
	 * for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
	 * userLocationActivityInformation = new UserLocationActivityInformation();
	 * userLocationActivityInformation.ActivityTypeID = c.getInt(0);
	 * userLocationActivityInformation.Latitude = c.getDouble(1);
	 * userLocationActivityInformation.Longitude = c.getDouble(2);
	 * userLocationActivityInformation.ActivityTime = c.getString(3);
	 * userLocationActivityInformation.Number = c.getString(4);
	 * userLocationActivityInformation.ActivityType = c.getString(5);
	 * userLocationActivityInformationList
	 * .add(userLocationActivityInformation); c.moveToNext(); } } return
	 * userLocationActivityInformationList; }
	 * 
	 * public void updateReportProblemAndBadExperience() { ContentValues
	 * contentValues = new ContentValues(); contentValues.put("IsSync", 1);
	 * mynetDatabase.update("ReportProblemAndBadExperience", contentValues,
	 * null, null); }
	 * 
	 * public void updateFacebookPerson(int issync) { ContentValues
	 * contentValues = new ContentValues(); contentValues.put("IsSync", issync);
	 * mynetDatabase.update("Facebook_Person", contentValues, null, null); }
	 * 
	 * public void deleteReportProblemAndBadExperience() {
	 * mynetDatabase.execSQL("delete from ReportProblemAndBadExperience"); }
	 * 
	 * public void deleteLocalUserGroup(int groupID, String commaSeperateUserID)
	 * { mynetDatabase.execSQL("delete from LocalUserGroup where groupID=" +
	 * groupID + " and UserID in(" + commaSeperateUserID + ")"); }
	 * 
	 * public ArrayList<PhoneCallInformation> getAllCallInformationByCallMemo()
	 * { ArrayList<PhoneCallInformation> callList = new
	 * ArrayList<PhoneCallInformation>(); String query =
	 * "SELECT * FROM PhoneCallInformation where TextCallMemo !='' order by CallId desc"
	 * ; Cursor c = mynetDatabase.rawQuery(query, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); PhoneCallInformation
	 * phoneCallInformation = null; for (int rowIndex = 0; rowIndex <
	 * c.getCount(); rowIndex++) { phoneCallInformation = new
	 * PhoneCallInformation(); phoneCallInformation.CallId = c.getInt(0);
	 * phoneCallInformation.PhoneId = c.getInt(1); phoneCallInformation.CallType
	 * = c.getInt(2); phoneCallInformation.Number = c.getString(3);
	 * phoneCallInformation.DurationInSec = c.getInt(2) == 3 ? 0 : c .getInt(4);
	 * phoneCallInformation.Latitude = c.getDouble(5);
	 * phoneCallInformation.Longitude = c.getDouble(6);
	 * phoneCallInformation.TextCallMemo = c.getString(7);
	 * phoneCallInformation.CallTime = new Date(c.getLong(8));
	 * phoneCallInformation.VoiceRecordPath = c.getString(13);
	 * phoneCallInformation.Reson = c.getString(10);
	 * callList.add(phoneCallInformation); c.moveToNext(); } } return callList;
	 * }
	 * 
	 * public Cursor getAllCaLLMemo() { String query =
	 * "SELECT CallId as _id, Number,TextCallMemo,VoiceRecordPath,CallTime,LocationName FROM PhoneCallInformation where TextCallMemo !='' order by CallTime desc"
	 * ; Cursor c = mynetDatabase.rawQuery(query, null); if (c != null)
	 * c.moveToFirst(); return c; }
	 * 
	 * public long createCollaboration(Collaboration collaboration) {
	 * ContentValues contentValues = new ContentValues();
	 * contentValues.put("MsgFrom", collaboration.MsgFrom);
	 * contentValues.put("MsgText", collaboration.MsgText);
	 * contentValues.put("Latitude", collaboration.Latitude);
	 * contentValues.put("Longitude", collaboration.Longitude);
	 * contentValues.put("GroupID", collaboration.GroupID);
	 * contentValues.put("MsgTo", collaboration.MsgTo);
	 * contentValues.put("PostedDate",
	 * CommonTask.convertDatetoLong(collaboration.PostedDate));
	 * contentValues.put("isRead", 0); contentValues.put("isAttachment", 0);
	 * contentValues.put("FilePath", collaboration.FilePath);
	 * contentValues.put("MsgFromName", collaboration.MsgFromName);
	 * contentValues.put("GroupName", collaboration.GroupName); return
	 * mynetDatabase.insertOrThrow("LiveCollaboration", null, contentValues); }
	 * 
	 * public Collaborations GetLiveCollaborationsByMsgTo(int id, String type,
	 * int limit) { String query = "SELECT * FROM LiveCollaboration "; if
	 * (type.equals("U")) query = query + "where GroupID=0 and (MsgFrom =" + id
	 * + " or MsgTo=" + id+")"; else query = query + "where GroupID =" + id;
	 * 
	 * if(longLastMessageTime>0)
	 * query=query+" and PostedDate >"+longLastMessageTime;
	 * 
	 * 
	 * //query = query + " order by PostedDate asc LIMIT " + limit; query =
	 * query + " order by PostedDate asc "; Cursor c =
	 * mynetDatabase.rawQuery(query, null);
	 * 
	 * List<Collaboration> collaborationList = null; if (c != null &&
	 * c.getCount() > 0) { collaborationList = new ArrayList<Collaboration>();
	 * c.moveToFirst(); Collaboration collaboration = null; for (int rowIndex =
	 * 0; rowIndex < c.getCount(); rowIndex++) { collaboration = new
	 * Collaboration(); collaboration.MsgFrom = c.getInt(1);
	 * collaboration.MsgText = c.getString(2); collaboration.GroupID =
	 * c.getInt(3); collaboration.MsgTo = c.getInt(4); collaboration.PostedDate
	 * = String.valueOf(c.getLong(5)); collaboration.Latitude = c.getDouble(8);
	 * collaboration.Longitude = c.getDouble(9); collaboration.FilePath =
	 * c.getString(10); collaboration.MsgFromName = c.getString(11);
	 * collaboration.GroupName = c.getString(12);
	 * collaborationList.add(collaboration); c.moveToNext(); } } Collaborations
	 * collaborations = new Collaborations(); collaborations.collaborationList =
	 * collaborationList; return collaborations; }
	 * 
	 * 
	 * public void GetIMbyId(int id, String type) { String query =
	 * "SELECT * FROM LiveCollaboration "; if (type.equals("U")) query = query +
	 * "where GroupID=0 and (MsgFrom =" + id + " or MsgTo=" + id+")"; else query
	 * = query + "where GroupID =" + id; query = query +
	 * " order by PostedDate asc "; Cursor c = mynetDatabase.rawQuery(query,
	 * null); if (c != null && c.getCount() > 0) { Intent i =null;
	 * c.moveToFirst(); Collaboration collaboration = null; for (int rowIndex =
	 * 0; rowIndex < c.getCount(); rowIndex++) { collaboration = new
	 * Collaboration(); collaboration.MsgFrom = c.getInt(1);
	 * collaboration.MsgText = c.getString(2); collaboration.GroupID =
	 * c.getInt(3); collaboration.MsgTo = c.getInt(4); collaboration.PostedDate
	 * = String.valueOf(c.getLong(5)); collaboration.Latitude = c.getDouble(8);
	 * collaboration.Longitude = c.getDouble(9); collaboration.FilePath =
	 * c.getString(10); collaboration.MsgFromName = c.getString(11);
	 * collaboration.GroupName = c.getString(12); c.moveToNext(); i = new
	 * Intent(CommonValues.getInstance().XmppReceiveMessageIntent);
	 * 
	 * i.putExtra("msg", collaboration);
	 * 
	 * context.sendBroadcast(i); } } }
	 * 
	 * public UserGroupUnions GetLiveCollaborationsHistory() { UserGroupUnions
	 * userGroupUnions = new UserGroupUnions(); Calendar cal =
	 * Calendar.getInstance(); cal.set(Calendar.MINUTE, 0);
	 * cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0);
	 * cal.set(Calendar.HOUR, 0); cal.set(Calendar.HOUR_OF_DAY, 0);
	 * cal.add(Calendar.DAY_OF_MONTH, -7); // String query = //
	 * "SELECT distinct MsgFrom,GroupID,MsgTo,MsgFromName FROM LiveCollaboration PostedDate>"
	 * +cal.getTimeInMillis() //
	 * +" and (MsgFrom!="+CommonValues.getInstance().LoginUser
	 * .UserNumber+" or MsgTo!="
	 * +CommonValues.getInstance().LoginUser.UserNumber+")"; String query =
	 * "select c.MsgFrom,c.MsgTo,c.GroupID,c.MsgText,c.PostedDate,c.MsgFromName,c.GroupName from LiveCollaboration c,"
	 * + "(select Max(msgid) as MsgId from LiveCollaboration  where MsgFrom=" +
	 * CommonValues.getInstance().LoginUser.UserNumber+" and MsgTo>0" +
	 * " group by MsgTo" +" union " +
	 * "select Max(msgid) as MsgId from LiveCollaboration  where MsgTo="
	 * +CommonValues.getInstance().LoginUser.UserNumber+ " group by MsgFrom" +
	 * " union " +
	 * "select Max(msgid) as MsgId from LiveCollaboration where GroupID>0 group by GroupID) t "
	 * + "where c.MsgID=t.MsgId and c.PostedDate>" + cal.getTimeInMillis() +
	 * " order by c.PostedDate desc"; Cursor c = mynetDatabase.rawQuery(query,
	 * null); List<UserGroupUnion> collaborationList = null; if (c != null &&
	 * c.getCount() > 0) { collaborationList = new ArrayList<UserGroupUnion>();
	 * c.moveToFirst(); UserGroupUnion collaboration = null; boolean
	 * isAdded=false; for (int rowIndex = 0; rowIndex < c.getCount();
	 * rowIndex++) { collaboration = new UserGroupUnion(); if (c.getInt(2) > 0)
	 * { collaboration.ID = c.getInt(2); collaboration.Type = "G";
	 * collaboration.Name = c.getString(6); } else { collaboration.ID =
	 * c.getInt(0) != CommonValues .getInstance().LoginUser.UserNumber ?
	 * c.getInt(0) : c.getInt(1); collaboration.Type = "U"; collaboration.Name =
	 * c.getString(5); //collaboration.userOnlinestatus =
	 * CommonTask.TryParseInt(String.valueOf(c.getInt(7))); }
	 * 
	 * collaboration.LastMessage = c.getString(3); collaboration.PostedDate =
	 * String.valueOf(c.getLong(4)); isAdded=false; for (UserGroupUnion
	 * userGroupUnion : collaborationList) { if((userGroupUnion.Type.equals("U")
	 * && userGroupUnion.ID==collaboration.ID)||(userGroupUnion.Type.equals("G")
	 * && userGroupUnion.ID==collaboration.ID)){ isAdded=true; break; } }
	 * if(!isAdded) collaborationList.add(collaboration); c.moveToNext(); } }
	 * userGroupUnions.userGroupUnionList = collaborationList;
	 * 
	 * return userGroupUnions; }
	 * 
	 * public PhoneCallInformation getLatestPhoneCallInfo(String number) {
	 * number = CommonTask.trimTentativeCountryCode(number); String query =
	 * "SELECT * FROM PhoneCallInformation where number like'%" + number +
	 * "%' order by CallTime desc limit 1"; Cursor c =
	 * mynetDatabase.rawQuery(query, null); PhoneCallInformation
	 * phoneCallInformation = null; if (c != null && c.getCount() > 0) {
	 * c.moveToFirst(); for (int rowIndex = 0; rowIndex < c.getCount();
	 * rowIndex++) { phoneCallInformation = new PhoneCallInformation();
	 * phoneCallInformation.CallId = c.getInt(0); phoneCallInformation.PhoneId =
	 * c.getInt(1); phoneCallInformation.CallType = c.getInt(2);
	 * phoneCallInformation.Number = c.getString(3);
	 * phoneCallInformation.DurationInSec = c.getInt(2) == 3 ? 0 : c .getInt(4);
	 * phoneCallInformation.Latitude = c.getDouble(5);
	 * phoneCallInformation.Longitude = c.getDouble(6);
	 * phoneCallInformation.TextCallMemo = c.getString(7);
	 * phoneCallInformation.CallTime = new Date(c.getLong(8));
	 * phoneCallInformation.VoiceRecordPath = c.getString(13);
	 * phoneCallInformation.Reson = c.getString(10); c.moveToNext(); } } return
	 * phoneCallInformation; }
	 * 
	 * public PhoneSMSInformation getLatestPhoneSMSInfo(String number) { number
	 * = CommonTask.trimTentativeCountryCode(number); String sql =
	 * "select * from PhoneSMSInformation where number like'%" + number +
	 * "%' order by SMSTime  desc limit 1"; PhoneSMSInformation
	 * phoneSmsInformation = null; Cursor c = mynetDatabase.rawQuery(sql, null);
	 * if (c != null && c.getCount() > 0) { c.moveToFirst(); for (int rowIndex =
	 * 0; rowIndex < c.getCount(); rowIndex++) { phoneSmsInformation = new
	 * PhoneSMSInformation(); phoneSmsInformation.SMSId = c.getInt(0);
	 * phoneSmsInformation.PhoneId = c.getInt(1); phoneSmsInformation.SMSType =
	 * c.getInt(2); phoneSmsInformation.Number = c.getString(3);
	 * phoneSmsInformation.SMSBody = c.getString(4);
	 * phoneSmsInformation.Latitude = c.getDouble(5);
	 * phoneSmsInformation.Longitude = c.getDouble(6);
	 * phoneSmsInformation.SMSTime = new Date(c.getLong(7));
	 * phoneSmsInformation.LAC = c.getString(10); phoneSmsInformation.CellID =
	 * c.getString(11); phoneSmsInformation.IsLocal = c.getInt(14) > 0;
	 * phoneSmsInformation.LocationName = c.getString(15); c.moveToNext(); } }
	 * return phoneSmsInformation; }
	 * 
	 * public void deleteFacebook_Qualification_Experience() {
	 * mynetDatabase.execSQL("delete FROM Facebook_Qualification_Experience"); }
	 * 
	 * public long createUser(LocalUser localUserGroup) { String query = "";
	 * ContentValues contentValues = new ContentValues();
	 * contentValues.put("UserName", localUserGroup.UserName);
	 * contentValues.put("ReffId", localUserGroup.ReffId);
	 * contentValues.put("IsFriend", localUserGroup.IsFriend);
	 * contentValues.put("MobileNumber", localUserGroup.MobileNumber);
	 * contentValues.put("UserOnlinestatus", localUserGroup.UserOnlinestatus);
	 * contentValues.put("isFavourite", localUserGroup.isFavourite); query =
	 * "SELECT UserID FROM LocalUserInformation where ReffId=" +
	 * localUserGroup.ReffId; Cursor c = mynetDatabase.rawQuery(query, null); if
	 * (c != null && c.getCount() > 0) { c.moveToFirst();
	 * mynetDatabase.update("LocalUserInformation", contentValues, " UserID=" +
	 * c.getInt(0), null);
	 * 
	 * } else { return mynetDatabase.insertOrThrow("LocalUserInformation", null,
	 * contentValues); } return 0; }
	 * 
	 * public long updateFavourite(int reffId, boolean isFeb) { String query =
	 * ""; ContentValues contentValues = new ContentValues();
	 * contentValues.put("isFavourite", isFeb ? 1 : 0); query =
	 * "SELECT UserID FROM LocalUserInformation where ReffId=" + reffId; Cursor
	 * c = mynetDatabase.rawQuery(query, null); if (c != null && c.getCount() >
	 * 0) { c.moveToFirst(); mynetDatabase.update("LocalUserInformation",
	 * contentValues, " UserID=" + c.getInt(0), null);
	 * 
	 * } else { return mynetDatabase.insertOrThrow("LocalUserInformation", null,
	 * contentValues); } return 0; }
	 * 
	 * public int getUserId(int reffId) { String query = ""; query =
	 * "SELECT UserID FROM LocalUserInformation where ReffId=" + reffId; Cursor
	 * c = mynetDatabase.rawQuery(query, null); if (c != null && c.getCount() >
	 * 0) { c.moveToFirst(); return c.getInt(0); } return 0; }
	 * 
	 * public long createGroup(String name, int reffId) { String query = "";
	 * ContentValues contentValues = new ContentValues();
	 * contentValues.put("GroupName", name); contentValues.put("ReffId",
	 * reffId); query =
	 * "SELECT GroupID FROM LocalGroupInformation where ReffId=" + reffId;
	 * Cursor c = mynetDatabase.rawQuery(query, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst();
	 * mynetDatabase.update("LocalGroupInformation", contentValues, " GroupID="
	 * + c.getInt(0), null);
	 * 
	 * } else { return mynetDatabase.insertOrThrow("LocalGroupInformation",
	 * null, contentValues); } return 0; }
	 * 
	 * public long createUserGroup(int groupID, int userId) { String query = "";
	 * ContentValues contentValues = new ContentValues();
	 * contentValues.put("GroupID", groupID); contentValues.put("UserId",
	 * userId); query = "SELECT GroupID FROM LocalUserGroup where GroupID=" +
	 * groupID + " and UserId=" + userId; Cursor c =
	 * mynetDatabase.rawQuery(query, null); if (c != null && c.getCount() > 0) {
	 * 
	 * } else { return mynetDatabase.insertOrThrow("LocalUserGroup", null,
	 * contentValues); } return 0; }
	 * 
	 * public String getGroupName(int groupID) { String query = ""; query =
	 * "SELECT GroupName FROM LocalGroupInformation where ReffId=" + groupID;
	 * Cursor c = mynetDatabase.rawQuery(query, null); if (c != null &&
	 * c.getCount() > 0) { c.moveToFirst(); return c.getString(0); } return "";
	 * }
	 * 
	 * public UserGroupUnions GetUserGroupList() { UserGroupUnions
	 * userGroupUnions = new UserGroupUnions(); String query =
	 * "SELECT GroupName,ReffId FROM LocalGroupInformation order by GroupName";
	 * Cursor c = mynetDatabase.rawQuery(query, null); List<UserGroupUnion>
	 * collaborationList = null; UserGroupUnion userGroupUnion = null; if (c !=
	 * null && c.getCount() > 0) { collaborationList = new
	 * ArrayList<UserGroupUnion>(); c.moveToFirst(); for (int rowIndex = 0;
	 * rowIndex < c.getCount(); rowIndex++) { userGroupUnion = new
	 * UserGroupUnion(); userGroupUnion.ID = c.getInt(1); userGroupUnion.Type =
	 * "G"; userGroupUnion.Name = c.getString(0);
	 * collaborationList.add(userGroupUnion); c.moveToNext(); } }
	 * 
	 * query =
	 * "SELECT MobileNumber,ReffId,isFavourite,UserOnlinestatus FROM LocalUserInformation where IsFriend=1 order by MobileNumber"
	 * ; c = mynetDatabase.rawQuery(query, null);
	 * 
	 * if (c != null && c.getCount() > 0) { if (collaborationList == null)
	 * collaborationList = new ArrayList<UserGroupUnion>(); c.moveToFirst(); for
	 * (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) { userGroupUnion
	 * = new UserGroupUnion(); userGroupUnion.ID = c.getInt(1);
	 * userGroupUnion.Type = "U"; userGroupUnion.Name = c.getString(0);
	 * userGroupUnion.isFavourite = c.getInt(2) == 1 ? true : false;
	 * userGroupUnion.userOnlinestatus = c.getInt(3);
	 * collaborationList.add(userGroupUnion); c.moveToNext(); } }
	 * 
	 * userGroupUnions.userGroupUnionList = collaborationList; return
	 * userGroupUnions; }
	 * 
	 * public List<Group> GetGroupList() {
	 * 
	 * String query =
	 * "SELECT GroupName,ReffId FROM LocalGroupInformation order by GroupName";
	 * Cursor c = mynetDatabase.rawQuery(query, null); List<Group> list = null;
	 * Group group = null; if (c != null && c.getCount() > 0) { list = new
	 * ArrayList<Group>(); c.moveToFirst(); for (int rowIndex = 0; rowIndex <
	 * c.getCount(); rowIndex++) { group = new Group(); group.GroupID =
	 * c.getInt(1); group.Name = c.getString(0); list.add(group);
	 * c.moveToNext(); } } return list; }
	 * 
	 * public UserGroupUnions GetUserLists() { UserGroupUnions userGroupUnions =
	 * new UserGroupUnions(); String query =
	 * "SELECT MobileNumber,ReffId,isFavourite,UserOnlinestatus FROM LocalUserInformation where IsFriend=1 order by MobileNumber"
	 * ; Cursor c = mynetDatabase.rawQuery(query, null); List<UserGroupUnion>
	 * collaborationList = null; UserGroupUnion userGroupUnion = null; if (c !=
	 * null && c.getCount() > 0) { if (collaborationList == null)
	 * collaborationList = new ArrayList<UserGroupUnion>(); c.moveToFirst(); for
	 * (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) { userGroupUnion
	 * = new UserGroupUnion(); userGroupUnion.ID = c.getInt(1);
	 * userGroupUnion.Type = "U"; userGroupUnion.Name = c.getString(0);
	 * userGroupUnion.isFavourite = c.getInt(2) == 1 ? true : false;
	 * userGroupUnion.userOnlinestatus = c.getInt(3);
	 * collaborationList.add(userGroupUnion); c.moveToNext(); } }
	 * userGroupUnions.userGroupUnionList = collaborationList; return
	 * userGroupUnions; }
	 * 
	 * public UserFamilyMembers GetFebUserList() { UserFamilyMembers
	 * userGroupUnions = new UserFamilyMembers(); String query =
	 * "SELECT MobileNumber,ReffId,UserOnlinestatus FROM LocalUserInformation where IsFriend=1 and isFavourite=1 order by MobileNumber"
	 * ; Cursor c = mynetDatabase.rawQuery(query, null); List<UserFamilyMember>
	 * collaborationList = null; UserFamilyMember userGroupUnion = null; if (c
	 * != null && c.getCount() > 0) { collaborationList = new
	 * ArrayList<UserFamilyMember>(); c.moveToFirst(); for (int rowIndex = 0;
	 * rowIndex < c.getCount(); rowIndex++) { userGroupUnion = new
	 * UserFamilyMember(); userGroupUnion.UserNumber = c.getInt(1);
	 * userGroupUnion.Name = c.getString(0); userGroupUnion.OnlineStatus =
	 * c.getInt(2); collaborationList.add(userGroupUnion); c.moveToNext(); } }
	 * 
	 * userGroupUnions.userFamilyMemberList = collaborationList; return
	 * userGroupUnions; }
	 * 
	 * public UserGroupUnions GetGroupMember(int groupId) { UserGroupUnions
	 * userGroupUnions = new UserGroupUnions(); String query =
	 * "SELECT MobileNumber,ReffId,UserOnlinestatus FROM LocalUserInformation u,(SELECT UserId FROM LocalUserGroup where GroupID="
	 * + groupId + ") t where u.ReffId=t.UserId order by u.MobileNumber"; Cursor
	 * c = mynetDatabase.rawQuery(query, null); List<UserGroupUnion>
	 * collaborationList = null; UserGroupUnion userGroupUnion = null; if (c !=
	 * null && c.getCount() > 0) { collaborationList = new
	 * ArrayList<UserGroupUnion>(); c.moveToFirst(); for (int rowIndex = 0;
	 * rowIndex < c.getCount(); rowIndex++) { userGroupUnion = new
	 * UserGroupUnion(); userGroupUnion.ID = c.getInt(1); userGroupUnion.Type =
	 * "U"; userGroupUnion.Name = c.getString(0);
	 * userGroupUnion.userOnlinestatus = c.getInt(1);
	 * collaborationList.add(userGroupUnion); c.moveToNext(); } }
	 * userGroupUnions.userGroupUnionList = collaborationList; return
	 * userGroupUnions; }
	 * 
	 * public HashMap<Integer, ContactUser> GetUserHashMap() { String query =
	 * "SELECT MobileNumber,ReffId,UserOnlinestatus FROM LocalUserInformation  order by MobileNumber"
	 * ; Cursor c = mynetDatabase.rawQuery(query, null);
	 * 
	 * HashMap<Integer, ContactUser> contactList=null; ContactUser
	 * contactUser=null; if (c != null && c.getCount() > 0) { contactList = new
	 * HashMap<Integer, ContactUser>(); c.moveToFirst(); ContentResolver cr =
	 * context.getContentResolver(); Cursor cursor =null; for (int rowIndex = 0;
	 * rowIndex < c.getCount(); rowIndex++) { contactUser = new ContactUser();
	 * contactUser.ID = c.getInt(1); contactUser.PhoneNumber = c.getString(0);
	 * contactUser.Name = "Unknown("+c.getString(0)+")"; cursor =
	 * cr.query(android
	 * .provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
	 * " replace(data1,' ', '') like '%" + contactUser.PhoneNumber.substring(3)
	 * +"%'", null, null);
	 * 
	 * if(cursor.getCount() > 0){ cursor.moveToFirst(); contactUser.Name=
	 * cursor.
	 * getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone
	 * .DISPLAY_NAME));
	 * contactUser.Image=CommonTask.fetchContactImageThumbnail(context
	 * ,cursor.getInt
	 * (cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone
	 * .PHOTO_ID))); } cursor.close();
	 * contactList.put(contactUser.ID,contactUser); c.moveToNext(); }
	 * contactUser = new ContactUser(); contactUser.ID
	 * =CommonValues.getInstance().LoginUser.UserNumber; contactUser.PhoneNumber
	 * = CommonValues.getInstance().LoginUser.Mobile; contactUser.Name =
	 * "Unknown("+CommonValues.getInstance().LoginUser.Mobile+")"; cursor =
	 * cr.query
	 * (android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
	 * null, " replace(data1,' ', '') like '%" +
	 * CommonValues.getInstance().LoginUser.Mobile.substring(3) +"%'", null,
	 * null);
	 * 
	 * if(cursor.getCount() > 0){ cursor.moveToFirst(); contactUser.Name=
	 * cursor.
	 * getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone
	 * .DISPLAY_NAME));
	 * contactUser.Image=CommonTask.fetchContactImageThumbnail(context
	 * ,cursor.getInt
	 * (cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone
	 * .PHOTO_ID))); } cursor.close();
	 * contactList.put(contactUser.ID,contactUser); } c.close(); return
	 * contactList; }
	 * 
	 * public UserGroupUnions GetUserList() { UserGroupUnions userGroupUnions =
	 * new UserGroupUnions(); String query =
	 * "SELECT MobileNumber,ReffId,UserOnlinestatus FROM LocalUserInformation u where u.IsFriend=1 order by u.MobileNumber"
	 * ; Cursor c = mynetDatabase.rawQuery(query, null); List<UserGroupUnion>
	 * collaborationList = null; UserGroupUnion userGroupUnion = null; if (c !=
	 * null && c.getCount() > 0) { collaborationList = new
	 * ArrayList<UserGroupUnion>(); c.moveToFirst(); for (int rowIndex = 0;
	 * rowIndex < c.getCount(); rowIndex++) { userGroupUnion = new
	 * UserGroupUnion(); userGroupUnion.ID = c.getInt(1); userGroupUnion.Type =
	 * "U"; userGroupUnion.Name = c.getString(0);
	 * userGroupUnion.userOnlinestatus = c.getInt(1);
	 * collaborationList.add(userGroupUnion); c.moveToNext(); } }
	 * userGroupUnions.userGroupUnionList = collaborationList; return
	 * userGroupUnions; }
	 */
}
