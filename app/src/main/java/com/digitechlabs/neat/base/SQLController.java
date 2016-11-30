package com.digitechlabs.neat.base;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class SQLController {

	private DBhelper dbhelper;
	private Context ourcontext;
	private SQLiteDatabase database;

	// private MemberEntity member;

	public SQLController(Context c) {
		ourcontext = c;
	}

	public SQLController open() throws SQLException {
		dbhelper = new DBhelper(ourcontext);
		database = dbhelper.getWritableDatabase();
		return this;

	}

	public void close() {
		dbhelper.close();
	}

	/*
	 * public void insertData(String name,String password) { ContentValues cv =
	 * new ContentValues();
	 * 
	 * cv.put(DBhelper.MEMBER_NAME, name); cv.put(DBhelper.MEMBER_PASSWORD,
	 * password); // cv.put(DBhelper.MEMBER_TYPE, password);
	 * 
	 * database.insert(DBhelper.TABLE_MEMBER, null, cv);
	 * 
	 * // long id =database.insert(DBhelper.TABLE_MEMBER, null, cv); // // if(id
	 * != 1 ){ // Log.w(DBhelper.TAG , "Unable to insert data inti database"); }
	 * 
	 * 
	 * public void insertDataHard(MemberEntity member) { ContentValues cv = new
	 * ContentValues(); cv.put(DBhelper.MEMBER_NAME, member.getName());
	 * cv.put(DBhelper.MEMBER_PASSWORD, member.getPassword());
	 * cv.put(DBhelper.MEMBER_TYPE, member.getRole());
	 * 
	 * database.insert(DBhelper.TABLE_MEMBER, null, cv); database.close();
	 * 
	 * // long id =database.insert(DBhelper.TABLE_MEMBER, null, cv); // // if(id
	 * != 1 ){ // Log.w(DBhelper.TAG , "Unable to insert data inti database");
	 * //}
	 * 
	 * // public void insertUserDetails(UserDetailEntity userDetails) { //
	 * ContentValues cv = new ContentValues(); // //cv.put(DBhelper.USER_ID,
	 * userDe); // cv.put(DBhelper.USER_MOBILE, userDetails.mobile); //
	 * cv.put(DBhelper.USER_FIRSTNAME, userDetails.fname); //
	 * cv.put(DBhelper.USER_LASTNAME, userDetails.lname); //
	 * cv.put(DBhelper.USER_EMAIL, userDetails.email); // //
	 * cv.put(DBhelper.USER_ADDRESS, userDetails.address); //
	 * cv.put(DBhelper.USER_DOB, userDetails.date_of_birth); //
	 * cv.put(DBhelper.USER_COMPANY, userDetails.company); //
	 * cv.put(DBhelper.USER_PASSWORD, userDetails.password); //
	 * cv.put(DBhelper.USER_ROLE, userDetails.user_role); // //
	 * database.insert(DBhelper.TABLE_USER_DETAILS, null, cv); // // long id
	 * =database.insert(DBhelper.TABLE_USER_DETAILS, null, cv); //// // if(id !=
	 * 1 ){ // Log.w(DBhelper.TAG , "Unable to insert data inti database"); //
	 * // }
	 * 
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
	 * 
	 * 
	 * //tradesrecord.Req_date, tradesrecord.TotalPrice,
	 * tradesrecord.LotCount,tradesrecord.LotPrice, tradesrecord.CompanyName,
	 * tradesrecord.Status, tradesrecord.Action_Staus, tradesrecord.Req_Comment,
	 * tradesrecord.ReqBy public void insertTradeDetails(String Req_date,String
	 * TotalPrice,String LotCount,String LotPrice,String CompanyName,String
	 * Status,String Action_Staus,String Req_Comment,String ReqBy) {
	 * ContentValues cv = new ContentValues();
	 * 
	 * cv.put(DBhelper.TRADE_USER_REQ_DATE, Req_date);
	 * cv.put(DBhelper.TRADE_USER_TOTALPRICE, TotalPrice);
	 * cv.put(DBhelper.TRADE_USER_LOTCOUNT, LotCount);
	 * 
	 * cv.put(DBhelper.TRADE_USER_LOTPRICE,LotPrice);
	 * cv.put(DBhelper.TRADE_USER_COMPANY_NAME, CompanyName);
	 * 
	 * cv.put(DBhelper.TRADE_USER_STATUS, Status);
	 * cv.put(DBhelper.TRADE_USER_ACTION_STATUS, Action_Staus);
	 * 
	 * cv.put(DBhelper.TRADE_USER_REQ_COMMENT, Req_Comment);
	 * cv.put(DBhelper.TRADE_USER_REQBY, ReqBy);
	 * 
	 * 
	 * 
	 * 
	 * long id =database.insert(DBhelper.TABLE_TRADE_USER_RECORD, null, cv); //
	 * if(id != 1 ){ Log.w(DBhelper.TAG ,
	 * "Unable to insert data inti database");
	 * 
	 * } //database.close();
	 * 
	 * // long id =database.insert(DBhelper.TABLE_MEMBER, null, cv); // // if(id
	 * != 1 ){ // Log.w(DBhelper.TAG , "Unable to insert data inti database"); }
	 * 
	 * 
	 * public Cursor readbrokerDataUser() { String[] allColumns = new String[] {
	 * DBhelper.TRADE_USER_ID,
	 * DBhelper.TRADE_USER_REQ_DATE,DBhelper.TRADE_USER_EXEX_DATE
	 * ,DBhelper.TRADE_USER_REQBY,
	 * DBhelper.TRADE_USER_EXECBY,DBhelper.TRADE_USER_STATUS
	 * ,DBhelper.TRADE_USER_ACTION_STATUS
	 * ,DBhelper.TRADE_USER_LOTCOUNT,DBhelper.TRADE_USER_TOTALPRICE
	 * ,DBhelper.TRADE_USER_LOTPRICE
	 * ,DBhelper.TRADE_USER_REQ_COMMENT,DBhelper.TRADE_USER_EXECUTE_COMMENT
	 * ,DBhelper.TRADE_USER_COMPANY_NAME}; Cursor c =
	 * database.query(DBhelper.TABLE_TRADE_USER_RECORD, allColumns,
	 * DBhelper.TRADE_USER_ACTION_STATUS+" = ?",new String[]{"Request"} , null ,
	 * null,DBhelper.TRADE_USER_REQ_DATE +" DESC"); Cursor c =
	 * database.query(DBhelper.TABLE_TRADE_USER_RECORD, allColumns, null, null,
	 * null, null, null); if (c != null) { c.moveToFirst(); } return c; }
	 * 
	 * 
	 * public Cursor readHistoryDataUser(String mobNo) {
	 * 
	 * String[] allColumns = new String[] { DBhelper.TRADE_USER_ID,
	 * DBhelper.TRADE_USER_REQ_DATE, DBhelper.TRADE_USER_EXEX_DATE,
	 * DBhelper.TRADE_USER_REQBY, DBhelper.TRADE_USER_EXECBY,
	 * DBhelper.TRADE_USER_STATUS, DBhelper.TRADE_USER_ACTION_STATUS,
	 * DBhelper.TRADE_USER_LOTCOUNT, DBhelper.TRADE_USER_TOTALPRICE,
	 * DBhelper.TRADE_USER_LOTPRICE, DBhelper.TRADE_USER_REQ_COMMENT,
	 * DBhelper.TRADE_USER_EXECUTE_COMMENT, DBhelper.TRADE_USER_COMPANY_NAME };
	 * Cursor c = database.query(DBhelper.TABLE_TRADE_USER_RECORD, allColumns,
	 * DBhelper.TRADE_USER_REQBY + " = ?", new String[] { mobNo }, null,
	 * null,DBhelper.TRADE_USER_REQ_DATE +" DESC");
	 * 
	 * Cursor c = database.query(DBhelper.TABLE_TRADE_USER_RECORD, allColumns,
	 * null, null, null, null, null);
	 * 
	 * if (c != null) { c.moveToFirst(); } return c;
	 * 
	 * }
	 * 
	 * 
	 * public Cursor readBrokerActionData(String mobNo) {
	 * 
	 * String[] allColumns = new String[] { DBhelper.TRADE_USER_ID,
	 * DBhelper.TRADE_USER_REQ_DATE, DBhelper.TRADE_USER_EXEX_DATE,
	 * DBhelper.TRADE_USER_REQBY, DBhelper.TRADE_USER_EXECBY,
	 * DBhelper.TRADE_USER_STATUS, DBhelper.TRADE_USER_ACTION_STATUS,
	 * DBhelper.TRADE_USER_LOTCOUNT, DBhelper.TRADE_USER_TOTALPRICE,
	 * DBhelper.TRADE_USER_LOTPRICE, DBhelper.TRADE_USER_REQ_COMMENT,
	 * DBhelper.TRADE_USER_EXECUTE_COMMENT, DBhelper.TRADE_USER_COMPANY_NAME };
	 * Cursor c = database.query(DBhelper.TABLE_TRADE_USER_RECORD, allColumns,
	 * DBhelper.TRADE_USER_EXECBY + " = ?", new String[] { mobNo }, null,
	 * null,null);
	 * 
	 * Cursor c = database.query(DBhelper.TABLE_TRADE_USER_RECORD, allColumns,
	 * null, null, null, null, null);
	 * 
	 * if (c != null) { c.moveToFirst(); } return c;
	 * 
	 * }
	 * 
	 * public ArrayList<TradeRecordsEntity> getReadbrokerDataUser() {
	 * ArrayList<TradeRecordsEntity> tradeList = new
	 * ArrayList<TradeRecordsEntity>(); String query =
	 * "SELECT * FROM  order by  ProblemTime desc"; Cursor c =
	 * database.rawQuery(query, null); if (c != null && c.getCount() > 0) {
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
	 * public Cursor readDataUser() { String[] allColumns = new String[] {
	 * DBhelper.USER_ID,
	 * DBhelper.USER_MOBILE,DBhelper.USER_FIRSTNAME,DBhelper.USER_LASTNAME,
	 * DBhelper
	 * .USER_EMAIL,DBhelper.USER_ADDRESS,DBhelper.USER_DOB,DBhelper.USER_COMPANY
	 * ,DBhelper.USER_PASSWORD,DBhelper.USER_ROLE}; Cursor c =
	 * database.query(DBhelper.TABLE_USER_DETAILS, allColumns, null, null, null,
	 * null, null); if (c != null) { c.moveToFirst(); } return c; }
	 * 
	 * //
	 * 
	 * // // public void insertData(String name,String password) { //
	 * ContentValues cv = new ContentValues(); // cv.put(DBhelper.MEMBER_NAME,
	 * name); // cv.put(DBhelper.MEMBER_PASSWORD, password); ////
	 * cv.put(DBhelper.MEMBER_TYPE, password); // //
	 * database.insert(DBhelper.TABLE_MEMBER, null, cv); // //// long id
	 * =database.insert(DBhelper.TABLE_MEMBER, null, cv); //// //// if(id != 1
	 * ){ //// Log.w(DBhelper.TAG , "Unable to insert data inti database"); // }
	 * 
	 * 
	 * 
	 * public Cursor readData() { String[] allColumns = new String[] {
	 * DBhelper.MEMBER_ID, DBhelper.MEMBER_NAME,DBhelper.MEMBER_PASSWORD };
	 * Cursor c = database.query(DBhelper.TABLE_MEMBER, allColumns, null, null,
	 * null, null, null); if (c != null) { c.moveToFirst(); } return c; }
	 * 
	 * 
	 * // * Updating a todo //
	 */
	// public int updateToDo(Todo todo) {
	// SQLiteDatabase db = this.getWritableDatabase();
	//
	// ContentValues values = new ContentValues();
	// values.put(KEY_TODO, todo.getNote());
	// values.put(KEY_STATUS, todo.getStatus());
	//
	// // updating row
	// return db.update(TABLE_TODO, values, KEY_ID + " = ?",
	// new String[] { String.valueOf(todo.getId()) });
	// }

	public int updateData(long memberID, String memberName,
			String memberPassword) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(DBhelper.MEMBER_NAME, memberName);
		cvUpdate.put(DBhelper.MEMBER_PASSWORD, memberPassword);
		int i = database.update(DBhelper.TABLE_MEMBER, cvUpdate,
				DBhelper.MEMBER_ID + " = " + memberID, null);
		return i;
	}

	public void insertTradeDetailsBuy(String Req_date, float TotalPrice,
			float LotCount, float LotPrice, String CompanyName, String Status,
			String Action_Staus, String Req_Comment, String ReqBy) {
		ContentValues cv = new ContentValues();

		cv.put(DBhelper.TRADE_USER_REQ_DATE, Req_date);
		cv.put(DBhelper.TRADE_USER_TOTALPRICE, TotalPrice);
		cv.put(DBhelper.TRADE_USER_LOTCOUNT, LotCount);

		cv.put(DBhelper.TRADE_USER_LOTPRICE, LotPrice);
		cv.put(DBhelper.TRADE_USER_COMPANY_NAME, CompanyName);

		cv.put(DBhelper.TRADE_USER_STATUS, Status);
		cv.put(DBhelper.TRADE_USER_ACTION_STATUS, Action_Staus);

		cv.put(DBhelper.TRADE_USER_REQ_COMMENT, Req_Comment);
		cv.put(DBhelper.TRADE_USER_REQBY, ReqBy);

		long id = database.insert(DBhelper.TABLE_TRADE_USER_RECORD, null, cv);
		//
		if (id != 1) {
			Log.w(DBhelper.TAG, "Unable to insert data inti database");

		}
	}

	public void GuestUser(String deciceID, String createdate, Double lastLatitude,
			Double lastLongitude) {
		ContentValues cv = new ContentValues();

		cv.put(DBhelper.DeciceID, deciceID);
		cv.put(DBhelper.LastLatitude, lastLatitude);
		cv.put(DBhelper.LastLongitude, lastLongitude);

		cv.put(DBhelper.Createdate, createdate);

		long id = database.insert(DBhelper.GuestUser, null, cv);
		//
		if (id != 1) {
			Log.w(DBhelper.TAG, "Unable to insert data inti database");

		}
	}

	public int updateBrokerUserData(long memberID, String memberMobile_upd,
			String LotPrice_upd, String CompanyShare_upd, String LotCount_upd,
			String UpdateExecComment, String Status, String Updatedate,
			String executedBy) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(DBhelper.TRADE_USER_REQBY, memberMobile_upd);
		cvUpdate.put(DBhelper.TRADE_USER_TOTALPRICE, LotPrice_upd);
		cvUpdate.put(DBhelper.TRADE_USER_COMPANY_NAME, CompanyShare_upd);
		cvUpdate.put(DBhelper.TRADE_USER_LOTCOUNT, LotCount_upd);
		cvUpdate.put(DBhelper.TRADE_USER_EXECUTE_COMMENT, UpdateExecComment);
		cvUpdate.put(DBhelper.TRADE_USER_ACTION_STATUS, Status);
		cvUpdate.put(DBhelper.TRADE_USER_EXEX_DATE, Updatedate);
		cvUpdate.put(DBhelper.TRADE_USER_EXECBY, executedBy);
		int i = database.update(DBhelper.TABLE_TRADE_USER_RECORD, cvUpdate,
				DBhelper.TRADE_USER_ID + " = " + memberID, null);
		return i;
	}

	public Boolean checkUser(String mobno) {

		String selectQuery = "SELECT  * FROM " + DBhelper.TABLE_USER_DETAILS
				+ " WHERE " + DBhelper.USER_MOBILE + " = " + mobno;

		// String User =
		// "Select * from User_details Where mobile=name and  password=pass";

		Log.e(DBhelper.TAG, selectQuery);

		Cursor c = database.rawQuery(selectQuery, null);

		if (c != null && c.getCount() <= 0) {
			c.moveToFirst();

			return false;
		}
		return true;
	}

	// / * Deleting a todo
	// */
	// public void deleteToDo(long tado_id) {
	// SQLiteDatabase db = this.getWritableDatabase();
	// db.delete(TABLE_TODO, KEY_ID + " = ?",
	// new String[] { String.valueOf(tado_id) });
	// }
	public void deleteData(long memberID) {
		database.delete(DBhelper.TABLE_MEMBER, DBhelper.MEMBER_ID + "="
				+ memberID, null);
	}


	// public void insertSomeCountries() {
	//
	// insertUserDetails(mobile, fname, lname, email, address, dob, company,
	// password, role);
	// insertUserDetails(mobile, fname, lname, email, address, dob, company,
	// password, role);
	// createCountry("DZA","Algeria","Africa","Northern Africa");
	// createCountry("ASM","American Samoa","Oceania","Polynesia");
	// createCountry("AND","Andorra","Europe","Southern Europe");
	// createCountry("AGO","Angola","Africa","Central Africa");
	// createCountry("AIA","Anguilla","North America","Caribbean");
	//
	// }
	//
	//
	// SELECT * FROM todos WHERE id = 1;
	// /*
	// * get single todo For Sign up
	// */
	// public Boolean checkUser(String mobno,String pass) {
	//
	//
	// String selectQuery = "SELECT  * FROM " + DBhelper.TABLE_USER_DETAILS +
	// " WHERE "
	// + DBhelper.USER_MOBILE + " = " + mobno+ "AND"+DBhelper.USER_PASSWORD +
	// "=" +pass;
	//
	// String User =
	// "Select * from User_details Where mobile=name and  password=pass";
	//
	// Log.e(DBhelper.TAG, selectQuery);
	//
	// Cursor c = database.rawQuery(selectQuery, null);
	//
	// if (c != null && c.getCount()<=0){
	// c.moveToFirst();
	//
	//
	// return false;
	// }
	// return true;
	// }

}

/*
 * Creating a todo
 */
// public long createToDo(Todo todo, long[] tag_ids) {
// SQLiteDatabase db = this.getWritableDatabase();
//
// ContentValues values = new ContentValues();
// values.put(KEY_TODO, todo.getNote());
// values.put(KEY_STATUS, todo.getStatus());
// values.put(KEY_CREATED_AT, getDateTime());
//
// // insert row
// long todo_id = db.insert(TABLE_TODO, null, values);
//
// // assigning tags to todo
// for (long tag_id : tag_ids) {
// createTodoTag(todo_id, tag_id);
// }
//
// return todo_id;
// }

// 3. Fetching all Todos
//
// Fetching all todos involves reading all todo rows and adding them to a list
// array.
// SELECT * FROM todos;
// /*
// * getting all todos
// * */
// public List<Todo> getAllToDos() {
// List<Todo> todos = new ArrayList<Todo>();
// String selectQuery = "SELECT  * FROM " + TABLE_TODO;
//
// Log.e(LOG, selectQuery);
//
// SQLiteDatabase db = this.getReadableDatabase();
// Cursor c = db.rawQuery(selectQuery, null);
//
// // looping through all rows and adding to list
// if (c.moveToFirst()) {
// do {
// Todo td = new Todo();
// td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
// td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
// td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
//
// // adding to todo list
// todos.add(td);
// } while (c.moveToNext());
// }
//
// return todos;
// }

//
// 4. Fetching all Todos under a Tag name
//
// This is also same as reading all the rows but it filters the todos by tag
// name. Check the following select query which fetches the todos under
// Watchlist tag name.
// SELECT * FROM todos td, tags tg, todo_tags tt WHERE tg.tag_name = �Watchlist�
// AND tg.id = tt.tag_id AND td.id = tt.todo_id;
/*
 * getting all todos under single tag
 */
// public List<Todo> getAllToDosByTag(String tag_name) {
// List<Todo> todos = new ArrayList<Todo>();
//
// String selectQuery = "SELECT  * FROM " + TABLE_TODO + " td, "
// + TABLE_TAG + " tg, " + TABLE_TODO_TAG + " tt WHERE tg."
// + KEY_TAG_NAME + " = '" + tag_name + "'" + " AND tg." + KEY_ID
// + " = " + "tt." + KEY_TAG_ID + " AND td." + KEY_ID + " = "
// + "tt." + KEY_TODO_ID;
//
// Log.e(LOG, selectQuery);
//
// SQLiteDatabase db = this.getReadableDatabase();
// Cursor c = db.rawQuery(selectQuery, null);
//
// // looping through all rows and adding to list
// if (c.moveToFirst()) {
// do {
// Todo td = new Todo();
// td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
// td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
// td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
//
// // adding to todo list
// todos.add(td);
// } while (c.moveToNext());
// }
//
// return todos;
// }

// 10. Deleting Tag and Todos under the Tag name
//
// Following method will delete a tag from db. This also will delete all the
// todos under the tag name, but this is optional.
//
// should_delete_all_tag_todos = Passing true will delete all the todos under
// the tag name
// /*
// * Deleting a tag
// */
// public void deleteTag(Tag tag, boolean should_delete_all_tag_todos) {
// SQLiteDatabase db = this.getWritableDatabase();
//
// // before deleting tag
// // check if todos under this tag should also be deleted
// if (should_delete_all_tag_todos) {
// // get all todos under this tag
// List<Todo> allTagToDos = getAllToDosByTag(tag.getTagName());
//
// // delete all todos
// for (Todo todo : allTagToDos) {
// // delete todo
// deleteToDo(todo.getId());
// }
// }
//
// // now delete the tag
// db.delete(TABLE_TAG, KEY_ID + " = ?",
// new String[] { String.valueOf(tag.getId()) });
// }
//
//
//
// Below are the methods to access the rows from todo_tags table
// 11. Assigning a Tag to Todo
//
// Following method will assign a todo under a tag name. You can also assign
// multiple tags to a todo by calling this function multiple times.
// /*
// * Creating todo_tag
// */
// public long createTodoTag(long todo_id, long tag_id) {
// SQLiteDatabase db = this.getWritableDatabase();
//
// ContentValues values = new ContentValues();
// values.put(KEY_TODO_ID, todo_id);
// values.put(KEY_TAG_ID, tag_id);
// values.put(KEY_CREATED_AT, getDateTime());
//
// long id = db.insert(TABLE_TODO_TAG, null, values);
//
// return id;
// }
//
//
// 12. Removing Tag of Todo
//
// Following method will remove the tag assigned to a todo
// /*
// * Updating a todo tag
// */
// public int updateNoteTag(long id, long tag_id) {
// SQLiteDatabase db = this.getWritableDatabase();
//
// ContentValues values = new ContentValues();
// values.put(KEY_TAG_ID, tag_id);
//
// // updating row
// return db.update(TABLE_TODO, values, KEY_ID + " = ?",
// new String[] { String.valueOf(id) });
// }
//
//
// 13. Changing the tag of todo
//
// Following simply replaces the tag name of a todo
// /*
// * Updating a todo tag
// */
// public int updateNoteTag(long id, long tag_id) {
// SQLiteDatabase db = this.getWritableDatabase();
//
// ContentValues values = new ContentValues();
// values.put(KEY_TAG_ID, tag_id);
//
// // updating row
// return db.update(TABLE_TODO, values, KEY_ID + " = ?",
// new String[] { String.valueOf(id) });
// }
//
//
// 14. Closing Database Connection
//
// Importantly don�t forget to close the database connection once you done using
// it. Call following method when you don�t need access to db anymore.
// // closing database
// public void closeDB() {
// SQLiteDatabase db = this.getReadableDatabase();
// if (db != null && db.isOpen())
// db.close();
// }

// getContactsCount()
// // Getting contacts Count
// public int getContactsCount() {
// String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
// SQLiteDatabase db = this.getReadableDatabase();
// Cursor cursor = db.rawQuery(countQuery, null);
// cursor.close();
//
// // return count
// return cursor.getCount();
// }

// outer class end
