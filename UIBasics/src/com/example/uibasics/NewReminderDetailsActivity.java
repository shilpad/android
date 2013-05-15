package com.example.uibasics;

import java.util.ArrayList;

import com.tutorial.expandListView.Adapter.ExpandListAdapter;
import com.tutorial.expandListView.Adapter.ExpandListAdapterWithImage;
import com.tutorial.expandListView.Classes.ExpandListChild;
import com.tutorial.expandListView.Classes.ExpandListChildWithImage;
import com.tutorial.expandListView.Classes.ExpandListGroup;
import com.tutorial.expandListView.Classes.ExpandListGroupWithImage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class NewReminderDetailsActivity extends Activity {

	private ExpandableListView ExpandList;
	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_reminder_details);
		
		populateExpandableList ();
	}

	private void populateExpandableList ()
	{
		ExpandList = (ExpandableListView) findViewById(R.id.ExpReminderList);
	    ExpListItems = SetStandardGroups();
	    ExpAdapter = new ExpandListAdapter(NewReminderDetailsActivity.this, ExpListItems);
	   	ExpandList.setAdapter(ExpAdapter);
		
	    ExpandList.setOnChildClickListener(new OnChildClickListener () {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Inside setOnChildClickListener" + groupPosition + 
						childPosition + id ,
		                   Toast.LENGTH_SHORT).show();
					
				if (childPosition == 0)
				{
					Intent intent = new Intent (getApplicationContext(), SetupReminderActivity.class);
					startActivityForResult (intent, 1);
				}
				return false;
			}
	    });
	    
        ExpandList.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick (ExpandableListView parent, View v, int groupPosition,
                    long id) {
            	
				Toast.makeText(getApplicationContext(), "Inside setOnGroupClickListener" + groupPosition + id,
	                    Toast.LENGTH_SHORT).show();
				
				/*if (groupPosition == 0)
				{
					Intent intent = new Intent (getApplicationContext(), FamilyActivity.class);
					startActivityForResult (intent, 2);
				}*/
				
            	return false;
            }
        }
        ); 
	}

    public ArrayList<ExpandListGroup> SetStandardGroups() {
    	
        ArrayList<ExpandListChild> listChildren = new ArrayList<ExpandListChild>();
        ExpandListChild ch1_1 = new ExpandListChild();
        ch1_1.setName("Add a child");
        listChildren.add(ch1_1);
        
        ExpandListGroup grpDentistAppt = new ExpandListGroup();
        grpDentistAppt.setName("First dentist appointment");
        grpDentistAppt.setItems(listChildren);
    
        ExpandListGroup grpFlushots = new ExpandListGroup();
        grpFlushots.setName("Flu shots");
        grpFlushots.setItems(listChildren);

        ExpandListGroup grpSearchPreschools = new ExpandListGroup();
        grpSearchPreschools.setName("Start researching preschools");
        grpSearchPreschools.setItems(listChildren);
        
        ExpandListGroup grpCustom = new ExpandListGroup();
        grpCustom.setName("Add custom reminder");
        //grpCustom.setItems(listChildren);
        
        ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
        list.add(grpDentistAppt);
        list.add(grpFlushots);
        list.add(grpSearchPreschools);
        list.add(grpCustom);
        
        return list;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_reminder_details, menu);
		return true;
	}

}
