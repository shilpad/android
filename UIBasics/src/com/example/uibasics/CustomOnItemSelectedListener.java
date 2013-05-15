package com.example.uibasics;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CustomOnItemSelectedListener implements OnItemSelectedListener{
	Context myContext;
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		// TODO Auto-generated method stub
		myContext = view.getContext();
		Intent intent = new Intent (myContext, CalendarActivity.class);
		myContext.startActivity(intent);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
