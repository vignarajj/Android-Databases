package com.sqlite.ex1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
/**
 *  @author Bipin S Rupadiya , www.gtu-android.blogspot.com
 * 
 *  18) Create an application to make Insert , update , Delete and retrieve operation on the database.
 *
 *
 * */
public class Search extends Activity {
 SQLiteDatabase db;
 EditText txtSearch;
 EditText txtName;
 EditText txtAge;
 Button btnEdit;
 Button btnDelete;
 RelativeLayout rlRecord;
    RelativeLayout rlSearch;
    String recID="0";
 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.search);
       
         db=openOrCreateDatabase("Student.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
       
         
      txtName = (EditText)findViewById(R.id.txtName);
      txtAge = (EditText)findViewById(R.id.txtAge);
        txtSearch = (EditText)findViewById(R.id.txtSearch);
        btnEdit=(Button)findViewById(R.id.btnEdit);
        btnDelete=(Button)findViewById(R.id.btnDelete);
       
        txtSearch.requestFocus();
        txtName.setEnabled(false);
        txtAge.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
       
        Button btnSearch=(Button)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new OnClickListener() {
   public void onClick(View arg0) {
     if (txtSearch.getText().toString().equals(""))
     {
      Toast.makeText(Search.this, "Enter value.", Toast.LENGTH_SHORT).show();
     }
     else
     {
      searchRecord();
     }
   }
  });
        //---------------Edit/update---------------------------------
        final Button btnEdit=(Button)findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new OnClickListener() {
   public void onClick(View arg0) {
    
     if (btnEdit.getText().toString().equals("Edit"))
     {
       btnEdit.setText("Update");
       txtName.setEnabled(true);
             txtAge.setEnabled(true);
            
             txtName.requestFocus();
           
            btnDelete.setEnabled(false);
          
     }
     else
     {
      txtName.setEnabled(false);
             txtAge.setEnabled(false);
           
            btnDelete.setEnabled(true);
          
      btnEdit.setText("Edit");
      String sql="update Stud set name='"+txtName.getText().toString()+"', age="+txtAge.getText().toString()+" where id="+recID;
         db.execSQL(sql);
         Toast.makeText(Search.this, "  Record Updated Successfully" , Toast.LENGTH_LONG).show();
     }
   }
  });
        //------------------------Delete ---------------------------
       
        btnDelete.setOnClickListener(new OnClickListener() {
   
   public void onClick(View arg0)
   {
    // TODO Auto-generated method stub
    AlertDialog.Builder alertbox = new AlertDialog.Builder(arg0.getContext());
    alertbox.setIcon(android.R.drawable.ic_dialog_alert);
    alertbox.setTitle("Confirm");    
    alertbox.setMessage("Are You Sure? You want to delete this record");
    alertbox.setPositiveButton("Delete", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface arg0, int arg1)
      {
      // TODO Auto-generated method stub
       String sql="Delete from Stud where id="+recID;
         db.execSQL(sql);
      Toast.makeText(getApplicationContext(), "Record Deleted", Toast.LENGTH_LONG).show();
      //clear old search result
      txtSearch.setText("");
      txtName.setText("");
      txtAge.setText("");
      txtSearch.requestFocus();
      }
     });
       alertbox.setNegativeButton("  Cancel  ", new DialogInterface.OnClickListener()
       {
      public void onClick(DialogInterface arg0, int arg1)
     {
      // TODO Auto-generated method stub
      
     }
    });
       alertbox.show();
      } 
  });
    }
 public void searchRecord()
 {
  try
        {
         txtSearch = (EditText)findViewById(R.id.txtSearch);
         txtName = (EditText)findViewById(R.id.txtName);
         txtAge = (EditText)findViewById(R.id.txtAge);
         //Cursor c=db.rawQuery("select id,name,age from Stud where id="+ txtSearch.getText().toString(), null);         
         Cursor c=db.rawQuery("select id,name,age from Stud where id=?", new String[]{txtSearch.getText().toString()});
         
            if(c.getCount()>0)
            {
              c.moveToNext();
                 recID= c.getString(0);
              txtName.setText( c.getString(1));
                 txtAge.setText(c.getString(2));
                btnEdit.setEnabled(true);
                 btnDelete.setEnabled(true);
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
