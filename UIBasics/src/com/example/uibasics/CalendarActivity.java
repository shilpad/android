package com.example.uibasics;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;


public class CalendarActivity extends Activity {

	public final static String EXTRA_BIRTHMONTH = "com.example.uibasics.BIRTHMONTH";
	public final static String EXTRA_BIRTHDAYOFMONTH = "com.example.uibasics.BIRTHDAYOFMONTH";
	public final static String EXTRA_BIRTHYEAR = "com.example.uibasics.BIRTHYEAR";
	public final static String EXTRA_DATE = "com.example.uibasics.BIRTHDATE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_calendar, menu);
		return true;
	}

	public void onClickSave (View view) {
		
		CalendarView calendar = (CalendarView) findViewById (R.id.calendarView1);
    	DatePicker birthdate = (DatePicker) findViewById (R.id.datePickerReminder);
    	
    	Bundle bundle = new Bundle();
    	
       	Intent intent = new Intent();
       	
    	int month = birthdate.getMonth ();
    	//bundle.putInt(EXTRA_BIRTHDAYOFMONTH, month);
    	//intent.putExtra (EXTRA_BIRTHMONTH, month);
    	       	
    	int date = birthdate.getDayOfMonth ();
    	//bundle.putInt(EXTRA_BIRTHDAYOFMONTH, date);
    	//intent.putExtra (EXTRA_BIRTHDAYOFMONTH, date);
    	
    	int year = birthdate.getYear ();
    	//bundle.putInt(EXTRA_BIRTHYEAR, year);
    	
    	//intent.putExtra (EXTRA_BIRTHYEAR, year);
    	
    	String newDate = month + "/" + date + "/" + year;
    	bundle.putString(EXTRA_DATE, newDate);
    	
    	intent.putExtras(bundle);
    	
    	// Save the Birthdate
    	//startActivity (intent);
    	
	 	setResult (RESULT_OK, intent);
    	finish();
	}
}
