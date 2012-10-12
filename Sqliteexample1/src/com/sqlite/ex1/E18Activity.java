package com.sqlite.ex1;

import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class E18Activity extends Activity 
{  
 SQLiteDatabase db; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        createDB();
      //do insert
  Button btnInsert=(Button)findViewById(R.id.btnInsert );
        btnInsert.setOnClickListener(new OnClickListener() {
   
   public void onClick(View arg0) {
    
    insert();
   }
  });
        Button btnClear=(Button)findViewById(R.id.btnClear );
        btnClear.setOnClickListener(new OnClickListener() {   
   public void onClick(View arg0) {    
    clear();
   }
  });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     super.onCreateOptionsMenu(menu);
     CreateMenu(menu);
     return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
     return MenuChoice(item);
    }
   
    private void CreateMenu(Menu menu)
    {
     MenuItem mnu1 = menu.add(0, 0, 0, "Insert");
     {
      mnu1.setAlphabeticShortcut('i');
      mnu1.setIcon(android.R.drawable.ic_input_add);
     }
     MenuItem mnu2 = menu.add(0, 1, 1, "Search");
     {
      mnu2.setAlphabeticShortcut('s');
      mnu2.setIcon(android.R.drawable.ic_search_category_default);
      
     }
     MenuItem mnu3 = menu.add(0, 2, 2, "Delete");
     {
      mnu3.setAlphabeticShortcut('d');
      mnu3.setIcon(android.R.drawable.ic_delete);

     }
     MenuItem mnu4 = menu.add(0, 3, 3, "View");
     {
      mnu4.setAlphabeticShortcut('d');
      mnu4.setIcon(android.R.drawable.ic_menu_info_details);
     }
     }
    private boolean MenuChoice(MenuItem item)
    {
     Intent intent=new Intent();
     switch (item.getItemId()) {
      case 0:
       insert();
       return true;
      case 1:
        intent.setClass(E18Activity.this, Search.class);
    startActivity(intent);
    return true;
      case 2:
       intent.setClass(E18Activity.this, Search.class);
    startActivity(intent);  
       return true;

      case 3:
       intent.setClass(E18Activity.this, ViewRecord.class);
       startActivity(intent);  
          return true;

     }
     return false;
    }
    public void createDB()
 {  
  db=openOrCreateDatabase("Student.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
  db.setLocale(Locale.getDefault());
  db.setLockingEnabled(true);
  db.setVersion(1);
  String sql="create table if not exists Stud(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)";
  db.execSQL(sql);
 }
 public void insert()
 {  
   EditText txtName=(EditText)findViewById(R.id.txtName);
   EditText txtAge=(EditText)findViewById(R.id.txtAge);
   if(txtName.getText().toString().equals(""))
   {
    Toast.makeText(E18Activity.this, "Enter Name.", Toast.LENGTH_SHORT).show();
      }
   else if (txtAge.getText().toString().equals(""))
   {
    Toast.makeText(E18Activity.this, "Enter Age.", Toast.LENGTH_SHORT).show();
   }
   else
   {
  
    String sql="insert into Stud(name,age) values('"+ txtName.getText().toString() +"',"+txtAge.getText().toString()+")";
    db.execSQL(sql);
    clear();
    Toast.makeText(E18Activity.this, "Record Successfully Inserted.", Toast.LENGTH_SHORT).show();
   }     
 }
 public void clear()
 {
  EditText txtName=(EditText)findViewById(R.id.txtName);
   EditText txtAge=(EditText)findViewById(R.id.txtAge);
  txtName.setText("");
  txtAge.setText("");
   
  txtName.clearFocus();
  txtAge.clearFocus();
  txtName.requestFocus();
  
 
 }
 @Override
    public void onDestroy()
 {
  super.onDestroy();
  db.close();
 }
}