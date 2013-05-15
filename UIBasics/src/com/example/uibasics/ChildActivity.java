package com.example.uibasics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.tutorial.expandListView.Classes.ExpandListChildWithImage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.ContactsContract.Profile;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ChildActivity extends Activity {
    private static int RESULT_LOAD_IMAGE = 1;
    private String m_picturePath = null;
	public final static String EXTRA_NAME = "com.example.tutorialexpandlistview.CHILD_NAME";
	private Bitmap m_bitmap = null;
	private boolean mbNewItem = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child);
		
		Bundle extras = getIntent().getExtras ();
        if (extras != null)
        {
        	mbNewItem = extras.getBoolean(ProfileActivity.EXTRA_NEWITEM);
        }
        
        if (mbNewItem == false)
        	initializeScreen ();
        
		DatePicker dpResult = (DatePicker) findViewById(R.id.datePicker1);
		dpResult.setCalendarViewShown(false);
	}

	private void initializeScreen ()
	{
		Toast.makeText(getApplicationContext(), "Inside ChildActivity.onCreate=" + ProfileActivity.gCurrentChild.getName(),
                Toast.LENGTH_SHORT).show();
		
		//set Name
		EditText editName = (EditText)findViewById (R.id.editName);
		editName.setText(ProfileActivity.gCurrentChild.getName());
		
		// set Gender
		Spinner mySpinner = (Spinner)findViewById(R.id.spinnerGender);
		
		String gender = ProfileActivity.gCurrentChild.getGender();
		if (gender.equals("M"))
			mySpinner.setSelection(0, true);
		else
			mySpinner.setSelection(1, true);
		
		// set Birthdate
		DatePicker dpResult = (DatePicker) findViewById(R.id.datePicker1);
		dpResult.updateDate(ProfileActivity.gCurrentChild.getYear(), 
							ProfileActivity.gCurrentChild.getMonth(),
							ProfileActivity.gCurrentChild.getDate());
		
		// set picture
        ImageView imageView = (ImageView)findViewById (R.id.imageView1);
        imageView.setImageDrawable((Drawable)ProfileActivity.gCurrentChild.getImage());
        
        //Empty gCurrentChild
        //ProfileActivity.gCurrentChild.clear();
 	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_child, menu);
		return true;
	}

	public void onClickAdd (View v)
	{
		Intent i = new Intent(
                  Intent.ACTION_PICK,
                  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           
        startActivityForResult(i, RESULT_LOAD_IMAGE);
	}
	
	   @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        super.onActivityResult(requestCode, resultCode, intent);
	         
	        Toast.makeText(getApplicationContext(), "resultCode=" + resultCode,
	                Toast.LENGTH_SHORT).show();
	        
	        if (requestCode == 1 && resultCode == RESULT_OK && null != intent) {
	            Uri selectedImage = intent.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	    		//Toast.makeText(getApplicationContext(), "filePathColumn=" + filePathColumn,
	            //        Toast.LENGTH_SHORT).show();
	    		
	            Cursor cursor = getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            m_picturePath = cursor.getString(columnIndex);
	    		Toast.makeText(getApplicationContext(), "picturePath=" + m_picturePath,
	                    Toast.LENGTH_SHORT).show();
	            cursor.close();
	            
	            File file=new File(m_picturePath);
	            
	            if (m_bitmap != null)
	            {
	            	m_bitmap.recycle ();
	            	System.gc();
	            }
	            
	            
	            Utility utility = new Utility();
        		m_bitmap = utility.decodeFile (file);
        		
	            //m_bitmap=decodeFile(file); //this is new bitmap which you can use for your purpose 
	            ImageView imageView = (ImageView) findViewById(R.id.imageView1);
	            imageView.setImageBitmap(m_bitmap);
	        }
	    }
	
	   public Bitmap decodeFile(File f){
	        try {
	            //Decode image size
	            BitmapFactory.Options o = new BitmapFactory.Options();
	            o.inJustDecodeBounds = true;
	            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

	            //The new size we want to scale to
	            final int REQUIRED_SIZE=70;

	            //Find the correct scale value. It should be the power of 2.
	            int width_tmp=o.outWidth, height_tmp=o.outHeight;
	            int scale=1;
	            while(true){
	                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
	                    break;
	                width_tmp/=2;
	                height_tmp/=2;
	                scale*=2;
	            }

	            //Decode with inSampleSize
	            BitmapFactory.Options o2 = new BitmapFactory.Options();
	            o2.inSampleSize=scale;
	            o2.inTempStorage = new byte[16*1024];
	            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	        } catch (FileNotFoundException e) {}
	        return null;
	    }
	   
	public void onClickSave (View view) {
		
		EditText editName = (EditText) findViewById (R.id.editName);
		String name = editName.getText().toString();
	
		Spinner mySpinner = (Spinner)findViewById(R.id.spinnerGender);
		String gender = (String)mySpinner.getSelectedItem();
		if (gender.equals("Boy"))
			gender = "M";
		else
			gender = "F";
		
		// set Birthdate
		DatePicker dpResult = (DatePicker) findViewById(R.id.datePicker1);
		MilestonesDbAdapter dbHelper = new MilestonesDbAdapter(this);
		dbHelper.open();
		
		//ProfileActivity.gCurrentChild = null;
		
		if (mbNewItem)
		{
			
			// Do something in response to button
			//Intent intent = new Intent();
    	
			// Return Name to calling activity
			//intent.putExtra (EXTRA_NAME, name);
			//setResult (0, intent);
	
			long retVal = dbHelper.createProfile(name, dpResult.getDayOfMonth(), dpResult.getMonth(), dpResult.getYear(), 
					gender, m_picturePath);
			if (retVal != -1)
			{
				int profile_id = (int)retVal;
				ProfileActivity.gCurrentChild = null;
				ProfileActivity.gCurrentChild = new ExpandListChildWithImage ();
				
				ProfileActivity.gCurrentChild.setId(profile_id);
				ProfileActivity.gCurrentChild.setName(name);
				ProfileActivity.gCurrentChild.setGender(gender);
				ProfileActivity.gCurrentChild.setDate(dpResult.getDayOfMonth());
				ProfileActivity.gCurrentChild.setMonth(dpResult.getMonth());
				ProfileActivity.gCurrentChild.setYear(dpResult.getYear());
				
				File file=new File(m_picturePath);
        		
        		Utility utility = new Utility();
        		Bitmap bm = utility.decodeFile (file);
        		Drawable drawable = new BitmapDrawable(bm);
				ProfileActivity.gCurrentChild.setImage(drawable);
			}
		}
		else
		{
			// Update existing record
			long retVal = dbHelper.updateProfile(ProfileActivity.gCurrentChild.getId(), name, dpResult.getDayOfMonth(), dpResult.getMonth(), dpResult.getYear(), 
					gender, m_picturePath);
			if (retVal != -1)
			{
				//int profile_id = (int)retVal;
				//ProfileActivity.gCurrentChild = null;
				//ProfileActivity.gCurrentChild = new ExpandListChildWithImage ();
				
				//ProfileActivity.gCurrentChild.setId(profile_id);
				ProfileActivity.gCurrentChild.setName(name);
				ProfileActivity.gCurrentChild.setGender(gender);
				ProfileActivity.gCurrentChild.setDate(dpResult.getDayOfMonth());
				ProfileActivity.gCurrentChild.setMonth(dpResult.getMonth());
				ProfileActivity.gCurrentChild.setYear(dpResult.getYear());
				
				if (m_picturePath == null)
				{
					ProfileActivity.gCurrentChild.setImage((Drawable)ProfileActivity.gCurrentChild.getImage());
			    }
				else
				{
					File file=new File(m_picturePath);
        		
					Utility utility = new Utility();
					Bitmap bm = utility.decodeFile (file);
					Drawable drawable = new BitmapDrawable(bm);
					ProfileActivity.gCurrentChild.setImage(drawable);
				}
			}
		}
		
		dbHelper.close ();
    	finish();
	}
}
