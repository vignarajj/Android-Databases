package com.sqlite.one;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Registerpage extends Activity
{
	Button confirm, reset1;
	EditText name,email,pass1,pass2,phone;
	private SQLiteAdapter db;
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.registerpage);
	name=(EditText)findViewById(R.id.name);
	email=(EditText)findViewById(R.id.email);
	pass1=(EditText)findViewById(R.id.pass1);
	pass2=(EditText)findViewById(R.id.pass2);
	phone=(EditText)findViewById(R.id.phone);
	confirm=(Button)findViewById(R.id.confirm);
	reset1=(Button)findViewById(R.id.reset1);
	reset1.setOnClickListener(new OnClickListener() 
	{		
		public void onClick(View arg0) 
		{
			// TODO Auto-generated method stub
			name.setText("");
			email.setText("");
			pass1.setText("");
			pass2.setText("");
			phone.setText("");
		}
	});
	confirm.setOnClickListener(new OnClickListener() 
	{		
		public void onClick(View arg0) 
		{
			// TODO Auto-generated method stub
			String n=name.getText().toString();
			String e= email.getText().toString();
			String p1=pass1.getText().toString();
			String p2=pass2.getText().toString();
			String ph=phone.getText().toString();
			db=new SQLiteAdapter(getBaseContext());
			db.openToWrite();
			db.insert(n, e, p1, p2, ph);
			db.close();
		}
	});
}
}