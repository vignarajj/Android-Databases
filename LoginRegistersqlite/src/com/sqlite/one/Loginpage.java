package com.sqlite.one;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Loginpage extends Activity
{
	EditText username,password;
	Button login,reset;
	SQLiteAdapter dbUser;
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.loginpage);
	username=(EditText)findViewById(R.id.username);
	password=(EditText)findViewById(R.id.password);
	login=(Button)findViewById(R.id.login);
	reset=(Button)findViewById(R.id.reset);
	dbUser = new SQLiteAdapter(getBaseContext()); 
	reset.setOnClickListener(new OnClickListener() 
	{		
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			username.setText("");
			password.setText("");
		}
	});
	login.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) 
		{
			// TODO Auto-generated method stub
			 String un = username.getText().toString();  
             String pass = password.getText().toString();  
             try{  
                 if(un.length() > 0 && pass.length() >0)  
                 {   
                     dbUser.openToRead();  
                     if(dbUser.Login(un, pass))  
                     {  
                         Toast.makeText(Loginpage.this,"Successfully Logged In", Toast.LENGTH_LONG).show();  
                     }
                     else if(un.toString()==""||pass.toString()=="")
                     {
                    	 Toast.makeText(Loginpage.this, "Enter the Fields", Toast.LENGTH_LONG).show();
                     }
                     else
                     {  
                         Toast.makeText(Loginpage.this,"Invalid Username or Password", Toast.LENGTH_LONG).show();  
                     }  
//                     dbUser.close();  
                 }  
             }
             catch(Exception e)  
             {  
                 Toast.makeText(Loginpage.this,e.getMessage(), Toast.LENGTH_LONG).show();  
             }  	
             dbUser.close(); 
		}
	});
}
}
