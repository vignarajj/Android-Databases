package com.sqlite.userform;

import android.content.ContentValues;

import android.content.Context;
 
import android.database.Cursor;
 
import android.database.SQLException;
 
import android.database.sqlite.SQLiteDatabase;
 
import android.database.sqlite.SQLiteOpenHelper;
 
import android.util.Log;
 
 
 
public class DBAdapter {
 
 
 
	// Initial Configuration
 
	public static final String DB_NAME = "userform";
 
	private static final int DATABASE_VER = 1;
 
	private static final String TAG = "DBAdapter";
 
 
 
	private final Context context;
 
	private DatabaseHelper DBHelper;
 
	private SQLiteDatabase db;
 
 
 
	// Set the Tables Key Words
 
	public static final String TABLE_USERS = "users";
 
	public static final String TABLE_CITIES = "cities";
 
	public static final String TABLE_HOBBIES = "hobbies";
 
	public static final String TABLE_USERS_HOBBIES = "users_hobbies";
 
 
 
	// Table Queries
 
	private static final String CREATE_USERS = "CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, last_name TEXT, sex TEXT, dob TEXT, city_id INTEGER);";
 
	private static final String CREATE_CITIES = "CREATE TABLE cities (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE);";
 
	private static final String CREATE_HOBBIES = "CREATE TABLE hobbies (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE);";
 
	private static final String CREATE_USERS_HOBBIES = "CREATE TABLE users_hobbies (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, hobby_id INTEGER);";
 
 
 
	// Keys
 
	public static final String ID = "_id";
 
	public static final String KEY_FNAME = "first_name";
 
	public static final String KEY_LNAME = "last_name";
 
	public static final String KEY_SEX = "sex";
 
	public static final String KEY_DOB = "dob";
 
	public static final String CID = "city_id";
 
	public static final String KEY_NAME = "name";
 
	public static final String KEY_UID = "user_id";
 
	public static final String KEY_HID = "hobby_id";
 
 
 
	static String[] hobbies = new String[] { "Dancing", "Singing", "Cycling",
 
			"Swimming" };
 
 
 
	public DBAdapter(Context ctx) {
 
		this.context = ctx;
 
		DBHelper = new DatabaseHelper(context);
 
	}
 
 
 
	private static class DatabaseHelper extends SQLiteOpenHelper {
 
 
 
		public DatabaseHelper(Context context) {
 
			super(context, DB_NAME, null, DATABASE_VER);
 
		}
 
 
 
		@Override
 
		public void onCreate(SQLiteDatabase db) {
 
			db.execSQL(CREATE_USERS);
 
			db.execSQL(CREATE_CITIES);
 
			db.execSQL(CREATE_HOBBIES);
 
			db.execSQL(CREATE_USERS_HOBBIES);
 
			System.out.println("Before ...");
 
			ContentValues initialValues = new ContentValues();
 
			for (int i = 0; i < hobbies.length; i++) {
 
				initialValues.put(KEY_NAME, hobbies[i]);
 
				db.insert(TABLE_HOBBIES, null, initialValues);
 
			}
 
			System.out.println("After ...");
 
		}
 
 
 
		@Override
 
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
 
					+ newVersion + ", which will destroy all old data");
 
			db.execSQL("DROP TABLE IF EXISTS users");
 
			db.execSQL("DROP TABLE IF EXISTS cities");
 
			db.execSQL("DROP TABLE IF EXISTS hobbies");
 
			db.execSQL("DROP TABLE IF EXISTS users_hobbies");
 
			onCreate(db);
 
		}
 
 
 
	}
 
 
 
	public DBAdapter open() throws SQLException {
 
		db = DBHelper.getWritableDatabase();
 
 
 
		return this;
 
	}
 
 
 
	public void close() {
 
		DBHelper.close();
 
	}
 
 
 
	// Custom Methods
 
 
 
	// Check if City Exists
 
	public Boolean checkCityName(String name) throws SQLException {
 
		Cursor mCursor = db
 
				.query(TABLE_CITIES, new String[] { ID, KEY_NAME }, KEY_NAME
 
						+ "=" + "'" + name + "'", null, null, null, null, null);
 
 
 
		if (mCursor.moveToFirst()) {
 
			return true;
 
		}
 
		return false;
 
	}
 
 
 
	// Insert data to user table
 
	public long insertINuser(String fnm, String lnm, String sex, String dob,
 
			int city_id) {
 
		ContentValues initialValues = new ContentValues();
 
		initialValues.put(KEY_FNAME, fnm);
 
		initialValues.put(KEY_LNAME, lnm);
 
		initialValues.put(KEY_SEX, sex);
 
		initialValues.put(KEY_DOB, dob);
 
		initialValues.put(CID, city_id);
 
		return db.insert(TABLE_USERS, null, initialValues);
 
	}
 
 
 
	// Insert City
 
	public long insertCity(String name) {
 
		ContentValues initialValues = new ContentValues();
 
		initialValues.put(KEY_NAME, name);
 
		return db.insert(TABLE_CITIES, null, initialValues);
 
	}
 
 
 
	// Insert into user_hobby
 
	public long insertUers_Hobbies(int uid, int hid) {
 
		ContentValues initialValues = new ContentValues();
 
 
 
		initialValues.put(KEY_UID, uid);
 
		initialValues.put(KEY_HID, hid);
 
		db.insert(TABLE_USERS_HOBBIES, null, initialValues);
 
 
 
		return 5;
 
	}
 
 
 
	// City List
 
	public Cursor cityList() throws SQLException {
 
		return db.query(TABLE_CITIES, new String[] { ID, KEY_NAME }, null,
 
				null, null, null, KEY_NAME + " ASC", null);
 
	}
 
 
 
	// List only user Fname
 
	public Cursor listUser() {
 
		System.out.println("listUser");
 
		return db.query(TABLE_USERS, new String[] { ID, KEY_FNAME }, null,
 
				null, null, null, null);
 
 
 
	}
 
 
 
	public int find_city_id(String city) {
 
		Cursor c = db
 
				.query(TABLE_CITIES, new String[] { ID, KEY_NAME }, KEY_NAME
 
						+ "=" + "'" + city + "'", null, null, null, null, null);
 
		Cursor mCursor = db
 
				.query(TABLE_CITIES, new String[] { ID, KEY_NAME }, KEY_NAME
 
						+ "=" + "'" + city + "'", null, null, null, null, null);
 
 
 
		mCursor.moveToFirst();
 
 
 
		System.out.println(mCursor.getString(0));
 
		if (c.moveToFirst()) {
 
			do {
 
				return Integer.parseInt(c.getString(0));
 
			} while (c.moveToNext());
 
		} else
 
			return -1;
 
 
 
	}
 
 
 
	// Get city Name
 
	public Cursor find_city_name(int cityID) {
 
		return db.query(TABLE_CITIES, new String[] { ID, KEY_NAME }, ID + "="
 
				+ "'" + cityID + "'", null, null, null, null, null);
 
	}
 
 
 
	// Get the user data
 
	public Cursor getUserData(String fNm) {
 
		return db.query(TABLE_USERS, new String[] { ID, KEY_FNAME, KEY_LNAME,
 
				KEY_SEX, KEY_DOB, CID }, KEY_FNAME + "='" + fNm + "'", null,
 
				null, null, null, null);
 
	}
 
 
 
	// Get user hobbies
 
	public Cursor getUserHobbies(int uid) {
 
		return db.query(TABLE_USERS_HOBBIES, new String[] { ID, KEY_UID,
 
				KEY_HID }, KEY_UID + "='" + uid + "'", null, null, null, null,
 
				null);
 
	}
 
 
 
	// Get single User Data
 
	public Cursor getUsersData(int uid) {
 
		return db.query(TABLE_USERS, new String[] { ID, KEY_FNAME, KEY_LNAME,
 
				KEY_SEX, KEY_DOB, CID }, ID + "='" + uid + "'", null, null,
 
				null, null, null);
 
	}
 
 
 
	// Get All user data
 
	public Cursor getAllDATA() {
 
		return db.query(TABLE_USERS, new String[] { ID, KEY_FNAME, KEY_LNAME,
 
				KEY_SEX, KEY_DOB, CID }, null, null, null, null, null);
 
	}
 
 
 
}