package com.sqlite.userform;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class HomeActivity extends Activity {
 
	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;
	private String sex;
	DBAdapter db;
	static final int DATE_DIALOG_ID = 0;
	CheckBox chk1, chk2, chk3, chk4;
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
 
		// Date Picker
		// capture our View elements
		mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
		mPickDate = (Button) findViewById(R.id.pickDate);
 
		// add a click listener to the button
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
 
		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
 
		// display the current date (this method is below)
		updateDisplay();
 
		// Database Conenction
		db = new DBAdapter(HomeActivity.this);
		db.open();
 
		// Populating the City Spinner
		Cursor cities = db.cityList();
		startManagingCursor(cities);
 
		// create an array to specify which fields we want to display
		String[] from = new String[] { DBAdapter.KEY_NAME };
		// create an array of the display item we want to bind our data to
		int[] to = new int[] { android.R.id.text1 };
 
		Spinner cityList = (Spinner) this.findViewById(R.id.citySpiner);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cities, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cityList.setAdapter(adapter);
 
		// On Click Save
		Button save = (Button) findViewById(R.id.btnSave);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
 
				// Get the name
				EditText Fname = (EditText) findViewById(R.id.FirstName);
				EditText Lname = (EditText) findViewById(R.id.LastName);
 
				String first_name = Fname.getText().toString();
				String last_name = Lname.getText().toString();
 
				// Get Date of birth
				String DoB = (String) ((TextView) findViewById(R.id.dateDisplay))
						.getText();
 
				// Get the Gender
				RadioButton rbM = (RadioButton) findViewById(R.id.rbMale);
 
				if (rbM.isChecked()) {
					sex = "Male";
				} else {
					sex = "Female";
				}
 
				// Get the City
				Spinner getCity = (Spinner) findViewById(R.id.citySpiner);
				String cityName = null;
				Cursor cc = (Cursor) (getCity.getSelectedItem());
				if (cc != null) {
					cityName = cc.getString(cc.getColumnIndex(db.KEY_NAME));
				}
				System.out.println("help :" + cityName);
 
				Long idUser, idHobby, idUsersHobbies;
				db.open();
				// Inseret data into User Table
				int city_id = db.find_city_id(cityName);
				idUser = db.insertINuser(first_name, last_name, sex, DoB,
						city_id);
				int uid = idUser.intValue();
 
				// Insert data into user_hobby table
				chk1 = (CheckBox) findViewById(R.id.cbDance);
				chk2 = (CheckBox) findViewById(R.id.cbSing);
				chk3 = (CheckBox) findViewById(R.id.cbCycle);
				chk4 = (CheckBox) findViewById(R.id.cbSwim);
 
				if (chk1.isChecked()) {
					idUsersHobbies = db.insertUers_Hobbies(uid, 1);
				}
				if (chk2.isChecked()) {
					idUsersHobbies = db.insertUers_Hobbies(uid, 2);
				}
				if (chk3.isChecked()) {
					idUsersHobbies = db.insertUers_Hobbies(uid, 3);
				}
				if (chk4.isChecked()) {
					idUsersHobbies = db.insertUers_Hobbies(uid, 4);
				}
 
				alertboxNeutral("Success", "Saved.", "Okay");
			}
		});
 
		db.close();
	}
 
	public void alertboxNeutral(String title, String message, String positive) {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(
				HomeActivity.this);
		alertbox.setTitle(title);
		alertbox.setMessage(message);
		alertbox.setPositiveButton(positive,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent i = new Intent(HomeActivity.this,
								HomeActivity.class);
						startActivity(i);
					}
				});
		alertbox.show();
	}
 
	public void addCheckBox(String task) {
		LinearLayout layout = (LinearLayout) findViewById(R.id.Llayout);
		final CheckBox chk = new CheckBox(HomeActivity.this); // Creating
																// checkbox
																// objects.....
		chk.setText(task);
		layout.addView(chk);
		chk.setOnClickListener(new View.OnClickListener() {
 
			public void onClick(View v) {
				chk.setVisibility(5);
			}
		});
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
 
		case R.id.list_users:
			Intent i = new Intent(this, ListUsersActivity.class);
			startActivity(i);
			break;
		case R.id.add_city:
			Intent j = new Intent(this, AddCityActivity.class);
			startActivity(j);
			break;
		case R.id.exit:
			Intent k = new Intent(this, HomeActivity.class);
			startActivity(k);
			break;
		}
 
		return true;
	}
 
	// Date Picker Methods
	// the call back received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
 
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};
 
	private void updateDisplay() {
		mDateDisplay.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
 
	}
 
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}
}
 