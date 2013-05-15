package com.example.uibasics;

import com.example.uibasics.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FamilyActivity extends Activity {

	public final static String EXTRA_NAME = "com.example.tutorialexpandlistview.NAME";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_family);
		
		Toast.makeText(getApplicationContext(), "OnCreate FamilyActivity",
                Toast.LENGTH_SHORT).show();
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_family, menu);
		return true;
	}

	public void onClickSave (View view) {
	 	EditText editName = (EditText) findViewById (R.id.editName);
    	String name = editName.getText().toString();
    	
		Toast.makeText(getApplicationContext(), name,
                Toast.LENGTH_SHORT).show();
		
		// Do something in response to button
    	Intent intent = new Intent();
    	
    	// Save the Name
    	intent.putExtra (EXTRA_NAME, name);
    	setResult (0, intent);
    	finish();
	}
	
}
