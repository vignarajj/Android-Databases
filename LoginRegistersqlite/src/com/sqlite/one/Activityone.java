package com.sqlite.one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activityone extends Activity 
{
	Button register,login,show1;
	Intent go1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        register=(Button)findViewById(R.id.register);
        login=(Button)findViewById(R.id.login);
        show1=(Button)findViewById(R.id.show1);
        register.setOnClickListener(new OnClickListener() 
        {			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				go1=new Intent(Activityone.this,Registerpage.class);
				startActivity(go1);
			}
		});
        login.setOnClickListener(new OnClickListener() 
        {			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				go1=new Intent(Activityone.this,Loginpage.class);
				startActivity(go1);
			}
		});
        show1.setOnClickListener(new OnClickListener() 
        {			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				go1=new Intent(Activityone.this,Showdatapage.class);
				startActivity(go1);
			}
		});
    }
}