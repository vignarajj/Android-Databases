package com.sqlite.userform;

import android.app.ListActivity;

import android.content.Intent;
 
import android.database.Cursor;
 
import android.os.Bundle;
 
import android.widget.ListAdapter;
 
import android.widget.ListView;
 
import android.widget.SimpleCursorAdapter;
 
import android.view.View;
 
 
 
public class ListUsersActivity extends ListActivity {
 
	private static final String TAG = null;
 
	Cursor c;
 
	DBAdapter db;
 
	static int uid;
 
 
 
	protected void onCreate(Bundle savedInstanceState) {
 
 
 
		super.onCreate(savedInstanceState);
 
		setContentView(R.layout.userlist);
 
 
 
		db = new DBAdapter(ListUsersActivity.this);
 
		db.open();
 
		c = db.listUser();
 
		startManagingCursor(c);
 
		String[] users = new String[] { DBAdapter.KEY_FNAME };
 
		// String[] uid = new String[] { DBAdapter.ID };
 
		for (String i : users) {
 
			System.out.println("uid=" + i);
 
		}
 
		int[] t = new int[] { android.R.id.text1 };
 
		ListAdapter adapter = new SimpleCursorAdapter(ListUsersActivity.this,
 
				android.R.layout.simple_list_item_1, c, users, t);
 
		System.out.println("listUser End");
 
 
 
		// Bind to our new adapter.
 
		setListAdapter(adapter);
 
	}
 
 
 
	@Override
 
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		super.onListItemClick(l, v, position, id);
 
		// Get the item that was clicked
 
		String fName = null;
 
		Cursor cc = (Cursor) (this.getListAdapter().getItem(position));
 
		if (cc != null) {
 
			uid = Integer.parseInt(cc.getString(cc.getColumnIndex(db.ID)));
 
			fName = cc.getString(cc.getColumnIndex(db.KEY_FNAME));
 
		}
 
		System.out.println("uid=:" + uid);
 
 
 
		Intent intent = new Intent(this, DetailUserActivity.class);
 
		startActivity(intent);
 
	}
 
 
 
}
