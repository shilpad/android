package com.newdomain.myreminder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EXTRA_NAME = "com.newdomain.myreminder.NAME";
	public final static String EXTRA_RELATIONSHIP = "com.newdomain,myreminder.RELATIONSHIP";
	public final static String EXTRA_BIRTHMONTH = "com.newdomain,myreminder.BIRTHMONTH";
	public final static String EXTRA_BIRTHDAYOFMONTH = "com.newdomain,myreminder.BIRTHDAYOFMONTH";
	public final static String EXTRA_BIRTHYEAR = "com.newdomain,myreminder.BIRTHYEAR";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        
    	// Do something in response to button
    	Intent intent = new Intent(this, SaveBirthdateActivity.class);
    	
    	// Save the Name
    	EditText editName = (EditText) findViewById (R.id.edit_name);
    	String name = editName.getText().toString();
    	intent.putExtra (EXTRA_NAME, name);
    	
    	// Save the Relationship
    	EditText editRelationship = (EditText) findViewById (R.id.edit_relationship);
    	String relationship = editRelationship.getText().toString();
    	intent.putExtra (EXTRA_RELATIONSHIP, relationship);
    
    	//CalendarView birthdate = (CalendarView) findViewById (R.id.datePicker1);
    	DatePicker birthdate = (DatePicker) findViewById (R.id.datePicker1);
    	int month = birthdate.getMonth ();
    	intent.putExtra (EXTRA_BIRTHMONTH, month);
    	       	
    	int date = birthdate.getDayOfMonth ();
    	intent.putExtra (EXTRA_BIRTHDAYOFMONTH, date);
    	
    	int year = birthdate.getYear ();
    	intent.putExtra (EXTRA_BIRTHYEAR, year);
    	
    	// Save the Birthdate
    	startActivity (intent);
    }
}
