package com.sqlite.ex1;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;
 public class ViewRecord extends ListActivity {
 SQLiteDatabase db;
 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
         db=openOrCreateDatabase("Student.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);         
         Cursor c=db.rawQuery("select id,name,age from Stud", null);
            ArrayList<String> list  = new ArrayList<String>();
         
            int count=c.getCount();
                       
            if(c.getCount()>0)
            {
                while(c.moveToNext())
             {
               list.add(c.getString(0)+" , "+c.getString(1)+" , "+c.getString(2));
                }                
             c.close();
             Toast.makeText(this,"Total Records: "+count, Toast.LENGTH_LONG).show();
             ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
             getListView().setAdapter(adapter);
            }
            else
            {
             Toast.makeText(this, "No Record Found" , Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
         Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();
        }
    }
 public void onDestroy()
 {
  super.onDestroy();
  db.close();
 }
}