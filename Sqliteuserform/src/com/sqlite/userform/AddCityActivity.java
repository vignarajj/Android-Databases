package com.sqlite.userform;

import android.app.Activity;

import android.app.AlertDialog;
 
import android.content.DialogInterface;
 
import android.content.Intent;
 
import android.os.Bundle;
 
import android.view.View;
 
import android.widget.Button;
 
import android.widget.EditText;
 
 
 
public class AddCityActivity extends Activity {
 
 
 
	protected void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
 
		setContentView(R.layout.add_city);
 
 
 
		Button save = (Button) findViewById(R.id.btnSave);
 
		save.setOnClickListener(new View.OnClickListener() {
 
			public void onClick(View arg0) {
 
				EditText city_name = (EditText) findViewById(R.id.cityname);
 
				String cityName = city_name.getText().toString();
 
 
 
				if (cityName.equals("")) {
 
					alertbox("Error", "Please Enter the City Name", "Back",
 
							null);
 
 
 
				} else {
 
					// Database Connection
 
					DBAdapter db = new DBAdapter(AddCityActivity.this);
 
					db.open();
 
 
 
					if (db.checkCityName(cityName).equals(true)) {
 
						alertboxNeutral("Error", "City Already exits.", "Okay",
 
								"Add Another");
 
					} else {
 
						long cityID = db.insertCity(cityName);
 
 
 
						if (cityID != -1) {
 
							alertboxNeutral("Success", "City was added.",
 
									"Okay", "Add Another");
 
						} else {
 
							alertbox("Error", "Couldnt Insert. please try",
 
									"Back", null);
 
						}
 
					}
 
 
 
					db.close();
 
				}
 
			}
 
		});
 
 
 
	}
 
 
 
	public void alertbox(String title, String message, String positive,
 
			String negative) {
 
		AlertDialog.Builder alertbox = new AlertDialog.Builder(
 
				AddCityActivity.this);
 
		alertbox.setTitle(title);
 
		alertbox.setMessage(message);
 
		alertbox.setPositiveButton(positive, null);
 
		alertbox.show();
 
	}
 
 
 
	public void alertboxNeutral(String title, String message, String positive,
 
			String neutral) {
 
		AlertDialog.Builder alertbox = new AlertDialog.Builder(
 
				AddCityActivity.this);
 
		alertbox.setTitle(title);
 
		alertbox.setMessage(message);
 
		alertbox.setPositiveButton(positive,
 
				new DialogInterface.OnClickListener() {
 
					public void onClick(DialogInterface dialog, int id) {
 
						Intent i = new Intent(AddCityActivity.this,
 
								HomeActivity.class);
 
						startActivity(i);
 
					}
 
				});
 
		alertbox.setNeutralButton(neutral,
 
				new DialogInterface.OnClickListener() {
 
					public void onClick(DialogInterface dialog, int id) {
 
						EditText ed = (EditText) findViewById(R.id.cityname);
 
						ed.setText("");
 
					}
 
				});
 
 
 
		alertbox.show();
 
	}
 
}
 
