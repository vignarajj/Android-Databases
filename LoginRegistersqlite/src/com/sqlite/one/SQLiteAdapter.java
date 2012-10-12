package com.sqlite.one;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteAdapter {

public static final String MYDATABASE_NAME = "DATABASEONE";
public static final String MYDATABASE_TABLE = "usertable";
public static final int MYDATABASE_VERSION = 1;
public static final String KEY_ID = "_id";
public static final String KEY_NAME = "uname";
public static final String KEY_EMAIL="email";
public static final String KEY_PASS1="pass1";
public static final String KEY_PASS2="pass2";
public static final String KEY_PHONE="phone";

//create table MY_DATABASE (ID integer primary key, Content text not null);
private static final String SCRIPT_CREATE_DATABASE =
"create table " + MYDATABASE_TABLE + " ("
+ KEY_ID + " integer primary key autoincrement, "
+ KEY_NAME + " text not null, "
+ KEY_EMAIL + " text not null, "
+ KEY_PASS1 + " text not null, "
+ KEY_PASS2 + " text not null, "
+ KEY_PHONE + " text not null );";

private SQLiteHelper sqLiteHelper;
private SQLiteDatabase sqLiteDatabase;

private Context context;

public SQLiteAdapter(Context c){
context = c;
}

public SQLiteAdapter openToRead() throws android.database.SQLException {
sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
sqLiteDatabase = sqLiteHelper.getReadableDatabase();
return this;
}

public SQLiteAdapter openToWrite() throws android.database.SQLException {
sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
sqLiteDatabase = sqLiteHelper.getWritableDatabase();
return this;
}

public void close(){
sqLiteHelper.close();
}

public long insert(String name, String email, String pass1, String pass2, String phone)
{
ContentValues contentValues = new ContentValues();
contentValues.put(KEY_NAME, name);
contentValues.put(KEY_EMAIL, email);
contentValues.put(KEY_PASS1, pass1);
contentValues.put(KEY_PASS2, pass2);
contentValues.put(KEY_PHONE, phone);
return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
}
public int deleteAll()
{
return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
}
public Cursor queueAll()
{
String[] columns = new String[]{KEY_ID, KEY_NAME,KEY_EMAIL,KEY_PASS1,KEY_PASS2,KEY_PHONE};
Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns,
  null, null, null, null, null);
return cursor;
}
public class SQLiteHelper extends SQLiteOpenHelper 
{
public SQLiteHelper(Context context, String name,
  CursorFactory factory, int version) {
 super(context, name, factory, version);
}
@Override
public void onCreate(SQLiteDatabase db) {
 // TODO Auto-generated method stub
 db.execSQL(SCRIPT_CREATE_DATABASE);
}
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
{
 // TODO Auto-generated method stub

}
}
public boolean Login(String username, String password) throws SQLException  
{  
    Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MYDATABASE_TABLE + " WHERE uname=? AND pass1=?", new String[]{username,password});  
    if (mCursor != null) {  
        if(mCursor.getCount() > 0)  
        {  
            return true;  
        }  
    }  
 return false;  
}  

}