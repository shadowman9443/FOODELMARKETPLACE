package com.digitechlabs.neat.base;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.digitechlabs.neat.entities.GusetUser;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {

	private SQLiteDatabase database;
	private static String SQL = "";
	
	
	
	
	public static final String GuestUser = "GuestUser";
	

	public static final String GuestUserId = "_id";
	public static final String DeciceID = "DeciceID";
	public static final String LastLatitude = "LastLatitude";
	public static final String LastLongitude = "LastLongitude";
	public static final String Createdate = "Createdate";

	// TABLE INFORMATTION
	public static final String TABLE_MEMBER = "member";
	
	
	public static final String MEMBER_ID = "_id";
	public static final String MEMBER_NAME = "name";
	public static final String MEMBER_PASSWORD = "password";
	public static final String MEMBER_TYPE = "role";
	public static final String MEMBER_TYPE_ADMIN = "admin";
	public static final String MEMBER_TYPE_BROKER = "broker";
	public static final String MEMBER_TYPE_ENDUSER = "end_user";

	public static final String TABLE_USER_DETAILS = "userdetails";

	// ********primary key must be _id for simple cursoradapter android sqlite
	// exception:java.lang.IllegalArgumentException: column '_id' does not
	// exist******
	public static final String USER_ID = "_id";
	public static final String USER_FIRSTNAME = "fname";
	public static final String USER_LASTNAME = "lname";
	public static final String USER_DOB = "date_of_birth";
	public static final String USER_ADDRESS = "address";
	public static final String USER_COMPANY = "company";
	public static final String USER_ROLE = "user_role";
	public static final String USER_MOBILE = "mobile";
	public static final String USER_EMAIL = "email";
	public static final String USER_PASSWORD = "password";

	public static final String TABLE_TRADE_USER_RECORD = "TradeRecords";

	// ********primary key must be _id for simple cursoradapter android sqlite
	// exception:java.lang.IllegalArgumentException: column '_id' does not
	// exist******
	public static final String TRADE_USER_ID = "_id";
	public static final String TRADE_USER_REQ_DATE = "Req_date";
	public static final String TRADE_USER_EXEX_DATE = "Exec_date";
	public static final String TRADE_USER_REQBY = "ReqBy";
	public static final String TRADE_USER_EXECBY = "ExecBy";
	public static final String TRADE_USER_STATUS = "Status";
	public static final String TRADE_USER_ACTION_STATUS = "Action_Staus";
	public static final String TRADE_USER_LOTCOUNT = "LotCount";
	public static final String TRADE_USER_TOTALPRICE = "TotalPrice";
	public static final String TRADE_USER_LOTPRICE = "LotPrice";
	public static final String TRADE_USER_REQ_COMMENT = "Req_Comment";
	public static final String TRADE_USER_EXECUTE_COMMENT = "Execute_Comment";
	public static final String TRADE_USER_MOBILE = "User_Mobile";
	public static final String TRADE_USER_COMPANY_NAME = "Company_Name";

	// Labels table name
	private static final String TABLE_USER_ROLE = "labels";

	// Labels Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "role_type";

	public static final String TAG = "SAKIDATABASE";

	// DATABASE INFORMATION
	static final String DB_NAME = "MEMBER.DB";
	static final int DB_VERSION = 1;

	// TABLE CREATION STATEMENT
	private static final String CREATE_TABLE = "create table " + TABLE_MEMBER
			+ "(" + MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MEMBER_PASSWORD + " TEXT NOT NULL," + MEMBER_TYPE
			+ " TEXT NOT NULL," + MEMBER_NAME + " TEXT NOT NULL);";

	private static final String CREATE_TABLE_USER_DETAILS = "create table "
			+ TABLE_USER_DETAILS + "(" + USER_ID
			+ " INTEGER PRIMARY KEY  AUTOINCREMENT, " + USER_MOBILE
			+ " varchar(20) NOT NULL UNIQUE," + USER_FIRSTNAME
			+ " varchar(20)  NULL," + USER_LASTNAME + " varchar(20)  NULL,"
			+ USER_EMAIL + " varchar(20)  NULL," + USER_ADDRESS
			+ " varchar(20)  NULL," + USER_DOB + " varchar(20)  NULL,"
			+ USER_COMPANY + " varchar(20)  NULL," + USER_PASSWORD
			+ " varchar(20)  NULL," + USER_ROLE + " varchar(100)  NULL);";

	private static final String CREATE_TABLE_TRADE_USER_RECORD = "create table "
			+ TABLE_TRADE_USER_RECORD
			+ "("
			+ TRADE_USER_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ TRADE_USER_REQ_DATE
			+ " INTEGER  NULL,"
			+ TRADE_USER_EXEX_DATE
			+ " INTEGER  NULL,"
			+ TRADE_USER_REQBY
			+ " varchar(200)   NULL,"
			+ TRADE_USER_EXECBY
			+ " varchar(200)  NULL,"
			+ TRADE_USER_STATUS
			+ " varchar(200)   NULL,"
			+ TRADE_USER_ACTION_STATUS
			+ " varchar(200)   NULL,"
			+ TRADE_USER_LOTCOUNT
			+ " FLOAT  NULL,"
			+ TRADE_USER_TOTALPRICE
			+ " FLOAT  NULL,"
			+ TRADE_USER_LOTPRICE
			+ " FLOAT  NULL,"
			+ TRADE_USER_REQ_COMMENT
			+ " nvarchar(3000)   NULL,"
			+ TRADE_USER_EXECUTE_COMMENT
			+ " nvarchar(3000)  NULL,"
			+ TRADE_USER_COMPANY_NAME
			+ " nvarchar(3000)  NULL,"
			+ TRADE_USER_MOBILE
			+ " varchar(20)  NULL);";
	
	
	
	
	private static final String CREATE_TABLE_Guest_User  = "create table" + GuestUser +"("
			+ "GuestUserId INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "DeciceID nvarchar(50) NULL, "
			+ "LastLatitude float NULL, "
			+ "LastLongitude float NULL, "
			+ "Createdate Integer NULL ) ";

	
	// TABLE CREATION STATEMENT
	private static final String CREATE_TABLE_GUEST_USER = "create table " + GuestUser
			+ "(" + GuestUserId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ DeciceID + " nvarchar(300)  NULL," + LastLatitude
			+ " float NULL," + LastLongitude + " float NULL,"+Createdate+" Integer NULL);";
	// private static final String CREATE_TABLE_USER_CREATE =
	// ("INSERT INTO + TABLE_USER_DETAILS  (USER_MOBILE) VALUES('"+123+"');");
	// private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
	// TABLE_USER_ROLE + "("
	// + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
	/*
			SQL = "CREATE TABLE GuestUser("
					+ "GuestUserId INTEGER PRIMARY KEY AUTOINCREMENT, "
        			+ "DeciceID nvarchar(50) NULL, "
        			+ "LastLatitude float NULL, "
        			+ "LastLongitude float NULL, "
					+ "Createdate Integer NULL ) ";
	 * private static final String INSERT_USER_DETAILS=INSERT INTO
	 * TABLE_USER_DETAILS (USER_MOBILE) VALUES (012)";
	 */
	


	/*
	 * private static final String sql = "INSERT INTO " + TABLE_USER_DETAILS +
	 * " (" + TITLE + " text not null, " + BODY + " text not null " + ");";
	 * Log.i("haiyang:createDB=", sql);
	 */
	private static final String sql = "INSERT INTO " + TABLE_USER_DETAILS
			+ " (" + USER_MOBILE + ", " + USER_PASSWORD + "," + USER_ROLE
			+ " ) values('1234', '1234','ADMIN');";

	/*
	 * public void insertUserDetails(String mobile,String fname,String
	 * lname,String email,String address,String dob,String company,String
	 * password,String role) { ContentValues cv = new ContentValues();
	 * //cv.put(DBhelper.USER_ID, userDe); cv.put(DBhelper.USER_MOBILE, mobile);
	 * cv.put(DBhelper.USER_FIRSTNAME, fname);
	 * cv.put(DBhelper.USER_LASTNAME,lname); cv.put(DBhelper.USER_EMAIL, email);
	 * 
	 * cv.put(DBhelper.USER_ADDRESS, address); cv.put(DBhelper.USER_DOB, dob);
	 * cv.put(DBhelper.USER_COMPANY, company); cv.put(DBhelper.USER_PASSWORD,
	 * password); cv.put(DBhelper.USER_ROLE, role);
	 * 
	 * // database.insert(DBhelper.TABLE_USER_DETAILS, null, cv);
	 * 
	 * long id =database.insert(DBhelper.TABLE_USER_DETAILS, null, cv); // if(id
	 * != 1 ){ Log.w(DBhelper.TAG , "Unable to insert data inti database"); }
	 * 
	 * }
	 */

	public DBhelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// Log.d(TAG,"Oncreate"+CREATE_TABLE);
			// db.execSQL(CREATE_TABLE);
			db.execSQL(CREATE_TABLE_USER_DETAILS);
			db.execSQL(CREATE_TABLE_TRADE_USER_RECORD);
            db.execSQL(CREATE_TABLE_GUEST_USER);

			//db.execSQL(SQL);
			// db.execSQL("INSERT INTO userdetails  (USER_MOBILE) VALUES('"+123+"');");
			// db.execSQL("INSERT INTO +TABLE_USER_DETAILS  (+USER_MOBILE+) VALUES('"+123+"');");
			db.execSQL(sql);

			// db.execSQL(CREATE_CATEGORIES_TABLE);
		} catch (SQLiteException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public ArrayList<GusetUser> getGusetUser() {
		ArrayList<GusetUser> list = new ArrayList<GusetUser>();
		Cursor c = database
				.rawQuery(
						"select * FROM GusetUser",
						null);
		GusetUser guestUser = null;
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
				guestUser = new GusetUser();
				guestUser.DeciceID = c.toString();
				guestUser.Createdate = String.valueOf(c.getInt(1));
				//guestUser.LastLatitude =(c.getInt(3);
			/*	phoneSignalStrenght.SignalLevel = c.getInt(2);
				phoneSignalStrenght.Latitude = c.getDouble(3);
				phoneSignalStrenght.Longitude = c.getDouble(4);
				phoneSignalStrenght.Time = new Date(c.getLong(5));*/
				list.add(guestUser);
			}
		}
		c.close();
		return list;
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		try {

			db.execSQL("DROP TABLE IF EXISTS " + GuestUser);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRADE_USER_RECORD);
			// db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_ROLE);
			onCreate(db);
		} catch (SQLiteException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}

// How do I check in SQLite whether a table exists?
// SELECT name FROM sqlite_master WHERE type='table' AND name='table_name';

/*
 * db.execSQL("CREATE TABLE TradeRecords(" +
 * "id INTEGER PRIMARY KEY AUTOINCREMENT," + "Req_date INTEGER NOT NULL," +
 * "Exec_date INTEGER  NULL," + "ReqBy int  NULL," + "ExecBy int  NULL," +
 * "Status Varchar(200) NOT NULL," + "Action_Staus Varchar(200) NOT NULL," +
 * "LotCount int  NULL," + "TotalPrice int  NULL," + "LotPrice int  NULL," +
 * "Req_Comment nvarchar(3000) NULL" + "Execute_Comment nvarchar(3000) NULL" +
 * ");");
 */

// SQlite - Android - Foreign key syntax
/*
 * You have to define your TASK_CAT column first and then set foreign key on it.
 * 
 * private static final String TASK_TABLE_CREATE = "create table " + TASK_TABLE
 * + " (" + TASK_ID + " integer primary key autoincrement, " + TASK_TITLE +
 * " text not null, " + TASK_NOTES + " text not null, " + TASK_DATE_TIME +
 * " text not null," + TASK_CAT + " integer," +
 * " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+" ("+CAT_ID+"));";
 */