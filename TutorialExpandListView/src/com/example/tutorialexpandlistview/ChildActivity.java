package com.example.tutorialexpandlistview;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChildActivity extends Activity {
	
	public final static String EXTRA_NAME = "com.example.tutorialexpandlistview.CHILD_NAME";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_child, menu);
		return true;
	}

	public void onClickSave (View view) {
	 	EditText editName = (EditText) findViewById (R.id.editText1);
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
