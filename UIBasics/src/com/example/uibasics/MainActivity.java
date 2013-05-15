package com.example.uibasics;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String EXTRA_CALLERID = "com.newdomain.myreminder.CALLERID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.rainbow);
		setContentView(R.layout.relative);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClickProfile (View v)
	{
		Toast.makeText(getApplicationContext(), "Text Touch",
                Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent (this, ProfileActivity.class);
		startActivity (intent);
		
		//setContentView (R.layout.rainbow);
	}
	
	public void onClickAdd (View v)
	{
		Toast.makeText(getApplicationContext(), "Clicked Add",
                Toast.LENGTH_SHORT).show();
		
		//Intent intent = new Intent (this, Pick1Activity.class);
		Intent intent = new Intent (this, AddDetailsActivity.class);
		intent.putExtra (EXTRA_CALLERID, 1);
		startActivity (intent);
	}
	
	public void onClickSearch (View v)
	{
		Toast.makeText(getApplicationContext(), "Clicked Search",
                Toast.LENGTH_SHORT).show();
		
		//Intent intent = new Intent (this, Pick1Activity.class);
		Intent intent = new Intent (this, SearchDetailsActivity.class);
		intent.putExtra (EXTRA_CALLERID, 2);
		startActivity (intent);
	}
	
	public void onClickRemind (View v)
	{
		Toast.makeText(getApplicationContext(), "Clicked Remind",
                Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent (this, NewReminderDetailsActivity.class);
		intent.putExtra (EXTRA_CALLERID, 2);
		startActivity (intent);
	}
}