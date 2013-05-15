package com.example.tutorialexpandlistview;

import java.util.ArrayList;

import com.example.tutorialexpandlistview.R;
import com.example.tutorialexpandlistview.FamilyActivity;

import com.tutorial.expandListView.Adapter.ExpandListAdapter;
import com.tutorial.expandListView.Classes.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class MainActivity extends Activity {
	
	public static String SharedString;
	
    /** Called when the activity is first created. */
	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(MainActivity.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
        ExpandList.setOnChildClickListener(new OnChildClickListener () {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Inside setOnChildClickListener",
	                    Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent (getApplicationContext(), ChildActivity.class);
				startActivityForResult (intent, 1);
				
				return false;
			}
        });
        
        ExpandList.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick (ExpandableListView parent, View v, int groupPosition,
                    long id) {
            	
				Toast.makeText(getApplicationContext(), "Inside setOnGroupClickListener" + groupPosition + id,
	                    Toast.LENGTH_SHORT).show();
				
				if (groupPosition == 0)
				{
					Intent intent = new Intent (getApplicationContext(), FamilyActivity.class);
					startActivityForResult (intent, 2);
				}
				
            	return false;
            }
        }
        );
        
	    ExpandList.setOnGroupExpandListener(new OnGroupExpandListener () {

			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Inside setOnGroupExpandListener",
	                    Toast.LENGTH_SHORT).show();
			}
        }
        );
    }
    
    public ArrayList<ExpandListGroup> SetStandardGroups() {
    	ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
    	
    	ExpandListGroup gru1 = new ExpandListGroup();
        gru1.setName("Family");
        
        ArrayList<ExpandListChild> listChildren = new ArrayList<ExpandListChild>();
        listChildren = new ArrayList<ExpandListChild>();
        
        ExpandListGroup grpChildren = new ExpandListGroup();
        grpChildren.setName("Children");
        ExpandListChild ch2_1 = new ExpandListChild();
        ch2_1.setName("Add a child");
        ch2_1.setTag(null);
        listChildren.add(ch2_1);
        grpChildren.setItems(listChildren);
        
        ArrayList<ExpandListChild> listPets = new ArrayList<ExpandListChild>();
        listPets = new ArrayList<ExpandListChild>();
        
        ExpandListGroup grpPets = new ExpandListGroup();
        grpPets.setName("Pets");
        ExpandListChild ch3_1 = new ExpandListChild();
        ch3_1.setName ("Add a Pet");
        ch3_1.setTag (null);
        listPets.add (ch3_1);
        grpPets.setItems (listPets);
        
        list.add(gru1);
        list.add(grpChildren);
        list.add(grpPets);
        
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        Toast.makeText(getApplicationContext(), "Got resultCode in MainActivity=" + resultCode + " RequestCode=" + requestCode,
                Toast.LENGTH_SHORT).show();
        
        // switch based on RequestCode
        if(data != null) {
            // doing something
            Bundle extras = data.getExtras ();
            if (extras != null)
            {
            	String name = null;
            	switch (requestCode)
            	{
            	case 1:
            		name = extras.get (ChildActivity.EXTRA_NAME).toString ();
            		if (name != null)
            		{
            			// use the value 
            			Toast.makeText(getApplicationContext(), "Got name in MainActivity" + name,
                            Toast.LENGTH_SHORT).show();
            		}
            		break;            	
            	case 2:
            		name = extras.get (FamilyActivity.EXTRA_NAME).toString ();
            		if (name != null)
            		{
            			// use the value 
            			Toast.makeText(getApplicationContext(), "Got name in MainActivity" + name,
                            Toast.LENGTH_SHORT).show();
            		}
            		break;
            		
            	}
            }
        }     
    }
}
