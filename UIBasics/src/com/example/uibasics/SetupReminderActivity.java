package com.example.uibasics;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.example.dateTime.DatePickerFragment;
import com.example.dateTime.TimePickerFragment;

import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SetupReminderActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_reminder);
		
		intializeScreen();
	}

	public void intializeScreen ()
	{
		initializeNames ();
		initializeDateTime ();
		initializeRemindMeSpinner ();
		initializeRepeatSpinner ();

			 	
	}
	
	private void initializeNames ()
	{
		Spinner spinnerName = (Spinner) findViewById(R.id.spinnerName);
		List<String> list = new ArrayList<String>();
		list.add("Name");
		
        MilestonesDbAdapter mDbHelper = new MilestonesDbAdapter(this);
        mDbHelper.open();
        Cursor cursor = mDbHelper.fetchAllProfiles() ;
        if (cursor.moveToFirst())
        {
        	do
        	{
        		list.add(cursor.getString(1));
        	}while (cursor.moveToNext());
        }	

    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_spinner_item, list);
    		
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinnerName.setAdapter(dataAdapter);
   
        cursor.close();
        mDbHelper.close();
	}
	
	private void initializeRemindMeSpinner ()
	{
		Spinner spinnerRemindMe = (Spinner) findViewById(R.id.spinnerRemindMe);
		List<String> list = new ArrayList<String>();
		
		list.add("Remind Me");
		list.add("At time of event");
		list.add("5 minutes before");
		list.add("15 minutes before");
		list.add("30 minutes before");
		list.add("1 hour before");
		list.add("2 hours before");
		list.add("1 day before");
		list.add("2 days before");
	
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerRemindMe.setAdapter(dataAdapter);
	}

	private void initializeRepeatSpinner ()
	{
		Spinner spinnerRemindMe = (Spinner) findViewById(R.id.spinnerRepeat);
		List<String> list = new ArrayList<String>();
		
		list.add("Repeat");
		list.add("Every Day");
		list.add("Every week");
		list.add("Every 2 weeks");
		list.add("Every Month");
		list.add("Every Year");
	
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerRemindMe.setAdapter(dataAdapter);
	}
	
	private void initializeDateTime ()
	{
		Calendar c = Calendar.getInstance();

	    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	    String formattedDate = df.format(c.getTime());
	    
	 	TextView textDate = (TextView) findViewById (R.id.textViewDate);
	 	textDate.setText(formattedDate);

	    df = new SimpleDateFormat("hh:mm a", Locale.US);
	    formattedDate = df.format(c.getTime());
	   
	 	TextView textTime = (TextView) findViewById (R.id.textViewTime);
	 	textTime.setText(formattedDate);
	}

	public void onClickDate (View v)
	{
    	Toast.makeText(getApplicationContext(), "Clicked DatePicker !", Toast.LENGTH_SHORT).show();
    	
    	 DialogFragment newFragment = new DatePickerFragment();
    	 newFragment.show(getFragmentManager(), "datePicker");
    }
	
    public void onClickTime (View v)
	{
    	Toast.makeText(getApplicationContext(), "Clicked TimePicker !", Toast.LENGTH_SHORT).show();
    	
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_reminder, menu);
		return true;
	}

}
