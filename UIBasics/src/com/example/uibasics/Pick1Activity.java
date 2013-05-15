package com.example.uibasics;

import java.io.File;

import com.example.uibasics.MyHorizontalLayout;
import com.example.uibasics.R;
import com.example.uibasics.MilestonesDbAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class Pick1Activity extends Activity {
	
	MyHorizontalLayout myHorizontalLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick1);

        myHorizontalLayout = (MyHorizontalLayout)findViewById(R.id.mygalleryAdd);
        
        int callerId = 0;
		Bundle extras = getIntent().getExtras ();
        if (extras != null)
        {
        	callerId = extras.getInt (MainActivity.EXTRA_CALLERID);
        	
        }
        
        myHorizontalLayout.setM_CallerId (callerId);
             
        /*String ExternalStorageDirectoryPath = Environment
        		.getExternalStorageDirectory()
        		.getAbsolutePath();
        
        String targetPath = ExternalStorageDirectoryPath + "/Test/";
        
        //Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
        File targetDirector = new File(targetPath);
        	        
        File[] files = targetDirector.listFiles();
        for (File file : files){ // this CRASHED on the tablet !!!!
        	String strAbsolutePath = file.getAbsolutePath();
        	//Toast.makeText(getApplicationContext(), strAbsolutePath, Toast.LENGTH_LONG).show();
    		myHorizontalLayout.add(strAbsolutePath);
        }  */

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
        		
        		//byte[] byteImage = cursor.getBlob(cursor.getColumnIndex(com.example.uibasics.MilestonesDbAdapter.KEY_PICTURE)); 
        		//Bitmap bm = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        		//myHorizontalLayout.add (bm, cursor.getInt(0));
        		Toast.makeText(getApplicationContext(), "Profile Id=" + cursor.getInt(0), Toast.LENGTH_LONG).show();
        	}while (cursor.moveToNext());
        }	
        
        cursor.close();
        mDbHelper.close();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pick1, menu);
		return true;
	}

}