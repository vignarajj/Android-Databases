
package com.example.loginfromlocal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		final EditText txtUserName = (EditText)findViewById(R.id.txtUsername);
		final EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
		Button btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				String username = txtUserName.getText().toString();
				String password = txtPassword.getText().toString();
				try{
					if(username.length() > 0 && password.length() >0)
					{
						DBUserAdapter dbUser = new DBUserAdapter(LoginActivity.this);
						dbUser.open();
						
						if(dbUser.Login(username, password))
						{
							Toast.makeText(LoginActivity.this,"Successfully Logged In", Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(LoginActivity.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
						}
						dbUser.close();
					}
					
				}catch(Exception e)
				{
					Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
			
		});
	}
}

