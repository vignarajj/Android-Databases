package com.sqlite.userform;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.database.Cursor;
 
public class DetailUserActivity extends Activity {
	TextView tv1, tv2, tv3;
	Cursor cr1, cr2, cr3;
	int cityID;
	String hobby = "";
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailuser);
 
		tv1 = (TextView) findViewById(R.id.txtVwUser);
		tv2 = (TextView) findViewById(R.id.txtVwCity);
		tv3 = (TextView) findViewById(R.id.txtVwHobby);
		tv1.setTextSize(20);
		tv2.setTextSize(20);
		tv3.setTextSize(20);
		int uid = ListUsersActivity.uid;
		DBAdapter db = new DBAdapter(DetailUserActivity.this);
		db.open();
		cr1 = db.getUsersData(uid);
 
		if (cr1.moveToFirst()) {
			do {
				tv1.setText("User ID :" + cr1.getString(0) + "\n"
						+ "First Name :" + cr1.getString(1) + "\n"
						+ "Last Name :" + cr1.getString(2) + "\n" + "Sex :"
						+ cr1.getString(3) + "\n" + "DOB :" + cr1.getString(4));
				cityID = Integer.parseInt(cr1.getString(5));
 
			} while (cr1.moveToNext());
		}
 
		cr2 = db.find_city_name(cityID);
		if (cr2.moveToFirst()) {
			do {
				tv2.setText("City :" + cr2.getString(1));
			} while (cr2.moveToNext());
		}
		cr3 = db.getUserHobbies(uid);
		if (cr3.moveToFirst()) {
			do {
 
				if (Integer.parseInt(cr3.getString(2)) == 1) {
					hobby = "Dancing ";
				}
				if (Integer.parseInt(cr3.getString(2)) == 2) {
					hobby = hobby + "Singing ";
				}
				if (Integer.parseInt(cr3.getString(2)) == 3) {
					hobby = hobby + "Cycling ";
				}
				if (Integer.parseInt(cr3.getString(2)) == 4) {
					hobby = hobby + "Swiming ";
				}
 
			} while (cr3.moveToNext());
		}
		tv3.setText("Hobbies :" + hobby);
	}
}
