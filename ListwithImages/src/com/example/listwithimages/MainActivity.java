package com.example.listwithimages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.view.ViewGroup;

public class MainActivity extends ExpandableListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 // Construct Expandable List
	    final String NAME = "name";
	    final String IMAGE = "image";
	    final LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    final ArrayList<HashMap<String, String>> headerData = new ArrayList<HashMap<String, String>>();

	    final HashMap<String, String> group1 = new HashMap<String, String>();
	    group1.put(NAME, "Group 1");
	    headerData.add( group1 );

	    final HashMap<String, String> group2 = new HashMap<String, String>();
	    group2.put(NAME, "Group 2");
	    headerData.add( group2);


	    final ArrayList<ArrayList<HashMap<String, Object>>> childData = new ArrayList<ArrayList<HashMap<String, Object>>>();

	    final ArrayList<HashMap<String, Object>> group1data = new ArrayList<HashMap<String, Object>>();
	    childData.add(group1data);

	    final ArrayList<HashMap<String, Object>> group2data = new ArrayList<HashMap<String, Object>>();
	    childData.add(group2data);
	    
	    // Set up some sample data in both groups
	    for( int i=0; i<10; ++i) {
	        final HashMap<String, Object> map = new HashMap<String,Object>();
	        map.put(NAME, "Child " + i );
	        map.put(IMAGE, getResources().getDrawable(R.drawable.cat));
	        ( i%2==0 ? group1data : group2data ).add(map);
	    }

	    setListAdapter( new SimpleExpandableListAdapter(
	            this,
	            headerData,
	            android.R.layout.simple_expandable_list_item_1,
	            new String[] { NAME },            // the name of the field data
	            new int[] { android.R.id.text1 }, // the text field to populate with the field data
	            childData,
	            0,
	            null,
	            new int[] {}
	        ) {
	            @Override
	            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	                final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);

	                // Populate your custom view here
	                ((TextView)v.findViewById(R.id.name)).setText( (String) ((Map<String,Object>)getChild(groupPosition, childPosition)).get(NAME) );
	                ((ImageView)v.findViewById(R.id.image)).setImageDrawable( (Drawable) ((Map<String,Object>)getChild(groupPosition, childPosition)).get(IMAGE) );

	                return v;
	            }

	            @Override
	            public View newChildView(boolean isLastChild, ViewGroup parent) {
	                 return layoutInflater.inflate(R.layout.expandable_list_item_with_image, null, false);
	            }
	        }
	    );
	}
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
