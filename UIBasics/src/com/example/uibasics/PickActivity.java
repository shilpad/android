package com.example.uibasics;

import com.example.uibasics.MainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class PickActivity extends Activity {
	private int m_callerId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras ();
        if (extras != null)
        {
        	m_callerId = extras.getInt (MainActivity.EXTRA_CALLERID);
        	
        }
        
		setContentView(R.layout.activity_pick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pick, menu);
		return true;
	}

	public void onClickAdd (View v)
	{
		if (m_callerId == 1)
		{
			Intent intent = new Intent (this, AddDetailsActivity.class);
			startActivity (intent);
		}
		else if (m_callerId == 2)
		{
			Intent intent = new Intent (this, SearchDetailsActivity.class);
			startActivity (intent);
		}
	}
}

