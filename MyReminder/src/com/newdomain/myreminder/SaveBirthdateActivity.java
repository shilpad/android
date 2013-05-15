package com.newdomain.myreminder;

import com.newdomain.myreminder.BirthdateDbAdapter;

import android.os.Bundle;
import android.app.Activity;
//import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

import android.database.Cursor;

public class SaveBirthdateActivity extends Activity {

	private BirthdateDbAdapter mDbHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_save_birthdate);
        
        // Get the message from the intent
        
        /*Intent intent = getIntent();
        
        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        int birthmonth = intent.getIntExtra(MainActivity.EXTRA_BIRTHMONTH, 0);
        int birthdate = intent.getIntExtra(MainActivity.EXTRA_BIRTHDAYOFMONTH, 0);
        int birthyear = intent.getIntExtra(MainActivity.EXTRA_BIRTHYEAR, 0);*/
        
        Integer birthmonth = 0;
        Integer birthdate = 0;
        Integer birthyear = 0;
        String name = null;
        String relationship = null;
        
        Bundle extras = getIntent().getExtras ();
        if (extras != null)
        {
        	name = extras.get (MainActivity.EXTRA_NAME).toString ();
        	if (name != null)
        	{
        		// use the value 
        	}
        	
         	relationship = extras.get (MainActivity.EXTRA_RELATIONSHIP).toString ();
        	if (relationship != null)
        	{
        		// use the value 
        	}
/*        	birthmonth = extras.get (MainActivity.EXTRA_BIRTHMONTH).toString();
        	birthdate = extras.get (MainActivity.EXTRA_BIRTHDAYOFMONTH).toString();
        	birthyear = extras.get (MainActivity.EXTRA_BIRTHYEAR).toString();*/
        	birthmonth = extras.getInt (MainActivity.EXTRA_BIRTHMONTH);
        	birthdate = extras.getInt (MainActivity.EXTRA_BIRTHDAYOFMONTH);
        	birthyear = extras.getInt (MainActivity.EXTRA_BIRTHYEAR);
        }
          
        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(String.valueOf(birthmonth) + "" + String.valueOf(birthdate) + "" + String.valueOf(birthyear));
        Log.v ("&&&", "Calling createBirthdate.");
        
//        textView.setText(birthmonth + "" + birthdate + "" + birthyear);

        mDbHelper = new BirthdateDbAdapter(this);
        mDbHelper.open();
        
        mDbHelper.createBirthdate(name, relationship, birthdate.intValue (), birthmonth.intValue(), birthyear.intValue());
        //mDbHelper.createBirthdate("Palaash", "son", 16, 6, 2010);
        
        // Set the text view as the activity layout
        setContentView(textView);
        
        Cursor cursor = mDbHelper.fetchAllBirthdates() ;
        if (cursor.moveToFirst())
        {
        	do
        	{
        	Log.v ("&&&", "Database values. Row# = " + cursor.getString(0));
        	Log.v ("&&&", "Database values. Name = " + cursor.getString(1));
        	Log.v ("&&&", "Database values. Relationship = " + cursor.getString(2));
        	Log.v ("&&&", "Database values. Birthdate = " + cursor.getString(3));
        	Log.v ("&&&", "Database values. BirthMonth = " + cursor.getString(4));
        	Log.v ("&&&", "Database values. BirthYear = " + cursor.getString(5));
        	}while (cursor.moveToNext());
        }	
       
        mDbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_save_birthdate, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
