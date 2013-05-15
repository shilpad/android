package com.example.uibasics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.example.Classes.Milestone_information;
import com.example.carousel.Carousel;
import com.example.carousel.CarouselAdapter;
import com.example.carousel.CarouselItem;
import com.example.carousel.CarouselAdapter.OnItemClickListener;

import com.example.uibasics.R;
import com.tutorial.expandListView.Adapter.ExpandListAdapter;
import com.tutorial.expandListView.Classes.ExpandListChild;
import com.tutorial.expandListView.Classes.ExpandListGroup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class SearchDetailsActivity extends Activity {

//	private ExpandListAdapter ExpAdapter;
//	private ArrayList<ExpandListGroup> ExpListItems;
//	private ExpandableListView ExpandList;
	private Spinner spinner1;
	private int mProfileId = -1;
	MyHorizontalLayout myHorizontalLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_details);
		
		
		myHorizontalLayout = (MyHorizontalLayout)findViewById(R.id.mygalleryAdd);
	        
	    int callerId = 0;
		Bundle extras = getIntent().getExtras ();
	    if (extras != null)
	    {
	    	callerId = extras.getInt (MainActivity.EXTRA_CALLERID);
	    	mProfileId = extras.getInt (MyHorizontalLayout.EXTRA_PROFILEID);
	  	 }
	        
	    myHorizontalLayout.setM_CallerId (callerId);
        MilestonesDbAdapter mDbHelper = new MilestonesDbAdapter(this);
        mDbHelper.open();
        Cursor cursor = mDbHelper.fetchAllProfiles() ;
        if (cursor.moveToFirst())
        {
        	boolean bHightlightImage = false;
        	do
        	{
        		Log.v ("&&&", "Database values. Row# = " + cursor.getString(0));
        		Log.v ("&&&", "Database values. Name = " + cursor.getString(1));
        		Log.v ("&&&", "Database values. Birthdate = " + cursor.getString(2));
        		Log.v ("&&&", "Database values. BirthMonth = " + cursor.getString(3));
        		Log.v ("&&&", "Database values. BirthYear = " + cursor.getString(4));
        		Log.v ("&&&", "Database values. Gender = " + cursor.getString(5));
        		Log.v ("&&&", "Database values. PicturePath = " + cursor.getString(6));
            	
        		String picturePath = cursor.getString(6);
        		Bitmap bm = null;
                
        		/*if (bm != null)
        		{
        			bm.recycle();
        			System.gc ();
        		}*/
        		
        		/*try {
        		    BitmapFactory.Options options = new BitmapFactory.Options();
        		    options.inSampleSize = 4;
        		    bm = BitmapFactory.decodeFile(file, options);
        		} catch (OutOfMemoryError e) {
        		    e.printStackTrace();
        		    System.gc();

        		    try {
        		        bm = BitmapFactory.decodeFile(file);
        		    } catch (OutOfMemoryError e2) {
        		      e2.printStackTrace();
        		      // handle gracefully.
        		    }
        		}*/
        		
        		File file=new File(picturePath);
        		
        		Utility utility = new Utility();
        		bm = utility.decodeFile (file);
        		
        		if (mProfileId != -1 && mProfileId == cursor.getInt(0))
        		{
            		Toast.makeText(getApplicationContext(), "Hightlight Image with Profile Id=" + cursor.getInt(0), Toast.LENGTH_LONG).show();
            	    bHightlightImage = true;
        		}
        		//byte[] byteImage = cursor.getBlob(cursor.getColumnIndex(com.example.uibasics.MilestonesDbAdapter.KEY_PICTURE)); 
        		//Bitmap bm = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        		myHorizontalLayout.add (bm, cursor.getInt(0), bHightlightImage);
        	}while (cursor.moveToNext());
        }	
        
        cursor.close();
        mDbHelper.close();
        
		addItemsOnSpinner1 ();
		
	 	  /* RelativeLayout sv = (RelativeLayout) findViewById(R.id.relativeSearch);
		    
		    final LinearLayout ll = new LinearLayout(this);
		    ll.setOrientation(LinearLayout.VERTICAL);
		    sv.addView(ll);

		    for(int i = 0; i < 5	; i++) {
		         CheckBox ch = new CheckBox(getApplicationContext());
		         if (i == 0)
		        	 ch.setPadding(55, 200, 0, 0);
		         ch.setTextColor(getResources().getColor(R.color.color_black));
		         ch.setText("A");
		         //Toast.makeText(getApplicationContext(), "Dynamic checkbox " + i , Toast.LENGTH_SHORT).show();
			     ll.addView(ch);
		        }

*/
		    
/*	    Carousel carousel = (Carousel)findViewById(R.id.carousel);
	    
	    carousel.setOnItemClickListener(new OnItemClickListener(){
			
				public void onItemClick(CarouselAdapter<?> parent, View view,
						int position, long id) {	
					
					Toast.makeText(getApplicationContext(), 
							String.format("%s has been clicked", 
							((CarouselItem)parent.getChildAt(position)).getName()), 
							Toast.LENGTH_SHORT).show();				
				}
	    });*/
				
	    /*ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
	    ExpListItems = SetStandardGroups();
	    ExpAdapter = new ExpandListAdapter(SearchDetailsActivity.this, ExpListItems);
	    ExpandList.setAdapter(ExpAdapter);
	        ExpandList.setOnChildClickListener(new OnChildClickListener () {

				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Inside setOnChildClickListener",
		                    Toast.LENGTH_SHORT).show();
					
					Intent intent = new Intent (getApplicationContext(), CalendarActivity.class);
					startActivityForResult (intent, 1);
					
					return false;
				}
	        });*/
	}

	 // add items into spinner dynamically
 	public void addItemsOnSpinner1() {

	spinner1 = (Spinner) findViewById(R.id.spinnerGender);
	List<String> list = new ArrayList<String>();
	
	list.add("Birthdate");
	list.add("1st Birthday");
	list.add("2nd Birthday");
	list.add("3rd Birthday");
	list.add("4th Birthday");
	list.add("5th Birthday");
	list.add("6th Birthday");
	list.add("7th Birthday");
	list.add("8th Birthday");
	list.add("9th Birthday");
	list.add("10th Birthday");
			
	/*ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_spinner_item, list);
	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner1.setAdapter(dataAdapter);*/
	
	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			R.layout.simple_spinner_item, list);
	dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
	spinner1.setAdapter(dataAdapter);
 }

 	public void onClickSearch (View v)
 	{
 		Toast.makeText(getApplicationContext(), "In onClickSearch 1", Toast.LENGTH_LONG).show();
		
 	   	MilestonesDbAdapter mDbHelper = new MilestonesDbAdapter(this);
 	   	Toast.makeText(getApplicationContext(), "In onClickSearch 2", Toast.LENGTH_LONG).show();
 	 	Toast.makeText(getApplicationContext(), "In onClickSearch 2.1", Toast.LENGTH_LONG).show();
		
 	   	mDbHelper.open();
 	   	Toast.makeText(getApplicationContext(), "In onClickSearch 3", Toast.LENGTH_LONG).show();
		
 	   	// get selected tags and their values
 	   	// This needs to be made generic : iterate through widgets and if checkbox and if selected
 	   	// get the name and the value
 	   	
 	   	
 	   CheckBox cbFirst = (CheckBox) findViewById(R.id.checkBoxTagFirst);
 	   CheckBox cbSchool = (CheckBox) findViewById(R.id.checkBoxTagSchool);
 	   
 	   ArrayList<String> lstTagNames = new ArrayList<String>();
 	   ArrayList<String> lstTagValues = new ArrayList<String>();
 		
 	   if (cbFirst.isChecked())
 	   {
 			lstTagNames.add("First");
 			lstTagValues.add ("1");
 	   }

 	   if (cbSchool.isChecked())
 	   {
 			lstTagNames.add("School");
 			lstTagValues.add ("1");
 	   }

 	   Spinner spinner = (Spinner) findViewById(R.id.spinnerGender);
 	   String txtSpinner = spinner.getSelectedItem().toString();
 	   if (!txtSpinner.equals("Birthdate"))
 	   {
 		   lstTagNames.add("Birthdate");
 		   lstTagValues.add(txtSpinner);
 	   }
 	   
 	  
 	 // mProfileId = myHorizontalLayout.getActiveProfileId ();
 	  
 	  Toast.makeText(getApplicationContext(), "Before fetchMedia, profileId=" + mProfileId, Toast.LENGTH_SHORT).show();
 	 	
 	  ArrayList<Milestone_information> lstMilestoneInformation = mDbHelper.fetchMediaPaths (mProfileId, lstTagNames, lstTagValues);
 	 Toast.makeText(getApplicationContext(), "After fetchMedia" , Toast.LENGTH_SHORT).show();
 	 
 	  mDbHelper.close();
 	   
	 Intent intent = new Intent (getApplicationContext(), SearchResultActivity.class);
 	 startActivityForResult (intent, 1);
		
 	/*  Carousel carousel = (Carousel)findViewById(R.id.carousel);
 	  
 	  XmlPullParser parser = getResources().getXml (R.layout.attrs);
 	  AttributeSet attrs = Xml.asAttributeSet(parser);
 	  carousel.abc(getApplicationContext(), attrs, 0);
	  
	  carousel.setOnItemClickListener(new OnItemClickListener(){
		
		  public void onItemClick(CarouselAdapter<?> parent, View view,
						int position, long id) {	
					
					Toast.makeText(getApplicationContext(), 
							String.format("%s has been clicked", 
							((CarouselItem)parent.getChildAt(position)).getName()), 
							Toast.LENGTH_SHORT).show();				
				}
	    });*/

 	}
 	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_details, menu);
		return true;
	}
	
	
	/*public ArrayList<ExpandListGroup> SetStandardGroups() {
	    	ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
	    	
	    	ExpandListGroup gru1 = new ExpandListGroup();
	        gru1.setName("Birthday");
	        
	        ArrayList<ExpandListChild> listChildren = new ArrayList<ExpandListChild>();
	        listChildren = new ArrayList<ExpandListChild>();
	        
	        ExpandListChild ch1_1 = new ExpandListChild();
	        ch1_1.setName("1");
	        ch1_1.setTag(null);
	        
	        ExpandListChild ch1_2 = new ExpandListChild();
	        ch1_2.setName("2");
	        ch1_2.setTag(null);
	        
	        ExpandListChild ch1_3 = new ExpandListChild();
	        ch1_3.setName("3");
	        ch1_3.setTag(null);
	        
	        ExpandListChild ch1_4 = new ExpandListChild();
	        ch1_4.setName("4");
	        ch1_4.setTag(null);
	        
	        ExpandListChild ch1_5 = new ExpandListChild();
	        ch1_5.setName("5");
	        ch1_5.setTag(null);
	        
	        listChildren.add(ch1_1);
	        listChildren.add(ch1_2);
	        listChildren.add(ch1_3);
	        listChildren.add(ch1_4);
	        listChildren.add(ch1_5);
	        
	        gru1.setItems(listChildren);
	        
	        list.add(gru1);
	        
	        return list;
	  }*/
}
