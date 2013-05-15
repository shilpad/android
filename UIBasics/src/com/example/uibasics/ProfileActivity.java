package com.example.uibasics;

import java.io.File;
import java.util.ArrayList;


import com.example.uibasics.FamilyActivity;
import com.example.uibasics.ChildActivity;
import com.example.uibasics.R;
import com.tutorial.expandListView.Adapter.ExpandListAdapter;
import com.tutorial.expandListView.Adapter.ExpandListAdapterWithImage;
import com.tutorial.expandListView.Classes.ExpandListChild;
import com.tutorial.expandListView.Classes.ExpandListChildWithImage;
import com.tutorial.expandListView.Classes.ExpandListGroup;
import com.tutorial.expandListView.Classes.ExpandListGroupWithImage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.support.v4.app.NavUtils;

public class ProfileActivity extends Activity {

	public final static String EXTRA_NEWITEM = "com.newdomain,myreminder.NEWITEM";
	
	private ExpandableListView ExpandList;
	
	private ExpandListAdapterWithImage mExpAdapterWithImage;
	private ArrayList<ExpandListGroupWithImage> mExpListItemsWithImage;
	
	public static ExpandListChildWithImage gCurrentChild;// rename to gCurrentChild
	private boolean mbNewItem = false;
	private int mChildPosition = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		populateExpandableList ();
	}

	private void populateExpandableList ()
	{
		ExpandList = (ExpandableListView) findViewById(R.id.ExpProfileList);
	    /*ExpListItems = SetStandardGroups();
	    ExpAdapter = new ExpandListAdapter(ProfileActivity.this, ExpListItems);
	   	ExpandList.setAdapter(ExpAdapter);*/
		
		mExpListItemsWithImage = SetStandardGroupsWithImage ();
		mExpAdapterWithImage = new ExpandListAdapterWithImage (ProfileActivity.this, mExpListItemsWithImage);
	    ExpandList.setAdapter(mExpAdapterWithImage);
	    
	    ExpandList.setOnChildClickListener(new OnChildClickListener () {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Inside setOnChildClickListener" + groupPosition + 
						childPosition + id + " Number of children=" + mExpAdapterWithImage.getChildrenCount(groupPosition),
		                   Toast.LENGTH_SHORT).show();
					
				if (groupPosition == 1)
				{
					Intent intent = new Intent (getApplicationContext(), ChildActivity.class);
					gCurrentChild = (ExpandListChildWithImage) mExpAdapterWithImage.getChild(groupPosition, childPosition);
					mChildPosition = childPosition;
					
					if (childPosition + 1 == mExpAdapterWithImage.getChildrenCount(groupPosition))//i.e 'Add a Child'
					{
						intent.putExtra(EXTRA_NEWITEM, true);
						mbNewItem = true;
					}
					else
					{
						intent.putExtra(EXTRA_NEWITEM, false);
						mbNewItem = false;
					}
					
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
				
				if (groupPosition == 0)
				{
					Intent intent = new Intent (getApplicationContext(), FamilyActivity.class);
					startActivityForResult (intent, 2);
				}
				
            	return false;
            }
        }
        );
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_family, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public ArrayList<ExpandListGroupWithImage> SetStandardGroupsWithImage () {
    	ArrayList<ExpandListGroupWithImage> list = new ArrayList<ExpandListGroupWithImage>();
    	
    	Toast.makeText(getApplicationContext(), "In SetStandardGroupsWithImage !!!",
        Toast.LENGTH_SHORT).show();
    	
    	ExpandListGroupWithImage gru1 = new ExpandListGroupWithImage();
        gru1.setName("Account");
        
        ArrayList<ExpandListChildWithImage> listChildren = new ArrayList<ExpandListChildWithImage>();
        
        ExpandListGroupWithImage grpChildren = new ExpandListGroupWithImage();
        grpChildren.setName("Children");
        
        ///
        MilestonesDbAdapter mDbHelper = new MilestonesDbAdapter(this);
        mDbHelper.open();
        Cursor cursor = mDbHelper.fetchAllProfiles() ;
        if (cursor.moveToFirst())
        {
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
        		/*if (bm != null)
        		{
        			bm.recycle();
        			System.gc ();
        		}*/
        		
        		File file=new File(picturePath);
        		
        		Utility utility = new Utility();
        		Bitmap bm = utility.decodeFile (file);
        		
                ExpandListChildWithImage ch2_1 = new ExpandListChildWithImage();
                ch2_1.setId(cursor.getInt(0));
                ch2_1.setName(cursor.getString(1));
                ch2_1.setDate(cursor.getInt(2));
                ch2_1.setMonth(cursor.getInt(3));
                ch2_1.setYear (cursor.getInt(4));
                ch2_1.setGender(cursor.getString(5));
                
                Drawable drawable = new BitmapDrawable(bm);
                ch2_1.setImage(drawable);
                listChildren.add(ch2_1);
                grpChildren.setItems(listChildren);
        		
        	}while (cursor.moveToNext());
        }	
        
        cursor.close();
        mDbHelper.close();

         ExpandListChildWithImage ch2_1 = new ExpandListChildWithImage();
        ch2_1.setName("Add a child");
        //ch2_1.setImage(getResources().getDrawable(R.drawable.cat));
        listChildren.add(ch2_1);
        grpChildren.setItems(listChildren);

        ArrayList<ExpandListChildWithImage> listPets = new ArrayList<ExpandListChildWithImage>();
        
        ExpandListGroupWithImage grpPets = new ExpandListGroupWithImage();
        grpPets.setName("Pets");
        ExpandListChildWithImage ch3_1 = new ExpandListChildWithImage();
        ch3_1.setName ("Add a Pet");
        ch3_1.setImage(getResources().getDrawable(R.drawable.cat));
        
        listPets.add (ch3_1);
        grpPets.setItems (listPets);

        ArrayList<ExpandListChildWithImage> listAdults = new ArrayList<ExpandListChildWithImage>();
        
        ExpandListGroupWithImage grpAdults = new ExpandListGroupWithImage();
        grpAdults.setName("Adults");
        ExpandListChildWithImage ch4_1 = new ExpandListChildWithImage();
        ch4_1.setName ("Add Name");
        ch4_1.setImage(getResources().getDrawable(R.drawable.cat));
        
        listAdults.add (ch4_1);
        grpAdults.setItems (listAdults);
        
        ArrayList<ExpandListChildWithImage> listTags = new ArrayList<ExpandListChildWithImage>();
        
        ExpandListGroupWithImage grpTags = new ExpandListGroupWithImage();
        grpTags.setName("Tags");
        ExpandListChildWithImage ch5_1 = new ExpandListChildWithImage();
        ch5_1.setName ("Add a Tag");
        ch5_1.setImage(getResources().getDrawable(R.drawable.cat));
         
        listTags.add (ch5_1);
        grpTags.setItems (listTags);
        
        list.add(gru1);
        list.add(grpChildren);
        list.add(grpPets);
        list.add(grpAdults);
        list.add(grpTags);
      
        return list;
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        Toast.makeText(getApplicationContext(), "Got resultCode in ProfileActivity=" + resultCode + " RequestCode=" + requestCode,
                Toast.LENGTH_SHORT).show();
        
        // switch based on RequestCode
        //if(data != null)
        {
            // doing something
            //Bundle extras = data.getExtras ();
            //if (extras != null)
            {
            	switch (requestCode)
            	{
            	case 1:
            		//populateExpandableList ();
            		//if (gCurrentChild.getId() != -1)
            		{
            			ExpandListGroupWithImage grpChildren = mExpListItemsWithImage.get(1);
            	    
            			ArrayList<ExpandListChildWithImage> listChildren = grpChildren.getItems();
                    	
            			Toast.makeText(getApplicationContext(), "Number of children=" + listChildren.size(),
            		                Toast.LENGTH_SHORT).show();
            			
            			if (mbNewItem)
            				listChildren.add(listChildren.size()-1,gCurrentChild);
            			else // edit existing item
            			{
            				listChildren.remove(mChildPosition);
            				listChildren.add(listChildren.size()-1,gCurrentChild);
                		}
                   
            			mExpAdapterWithImage.notifyDataSetChanged();
            		}
            		/*name = extras.get (ChildActivity.EXTRA_NAME).toString ();
            		if (name != null)
            		{
            			// use the value 
            			Toast.makeText(getApplicationContext(), "Got name in MainActivity" + name,
                            Toast.LENGTH_SHORT).show();
            		}*/
            		break;            	
            	case 2:
            		/*name = extras.get (FamilyActivity.EXTRA_NAME).toString ();
            		if (name != null)
            		{
            			// use the value 
            			Toast.makeText(getApplicationContext(), "Got name in MainActivity" + name,
                            Toast.LENGTH_SHORT).show();
            		}*/
            		break;
            		
            	}
            }
        }     
    }
}
