package com.sqlite.one;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Showdatapage extends Activity
{
	ListView list;
	private SQLiteAdapter db1;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showdata);
		list=(ListView)findViewById(R.id.list);
		db1 = new SQLiteAdapter(this);
		 db1.openToRead();
	      Cursor cursor = db1.queueAll();
	      startManagingCursor(cursor);
	      String[] from = new String[]{SQLiteAdapter.KEY_NAME};
	      int[] to = new int[]{R.id.text};
	      SimpleCursorAdapter cursorAdapter =new SimpleCursorAdapter(this, R.layout.showdata_row, cursor, from, to);
	      list.setAdapter(cursorAdapter);
	      db1.close();
	}
}
