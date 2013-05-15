package com.newdomain.myreminder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SaveImageActivity extends Activity implements OnClickListener{

    protected static TextView textView;
    protected static ImageView bmImage;
    protected Button start;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_image);
		
	    bmImage = (ImageView)findViewById(R.id.imageView1);
	        
	    textView = (TextView) findViewById(R.id.textView1);

	    start = (Button) findViewById(R.id.button1);
	    start.setOnClickListener(this); 

	    DownloadFile();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_image, menu);
		return true;
	}

	   public void onClick(View v) 
	    {
	     SQLiteDatabase myDb;       
	     String MySQL;

	     byte[] byteImage1 = null;
	     byte[] byteImage2 = null;
	     
	     MySQL="create table emp1(_id INTEGER primary key autoincrement, fio TEXT not null, picture BLOB);";
	     myDb = openOrCreateDatabase("/sdcard/Test/MyWeatherDB.db", Context.MODE_PRIVATE, null);
	     myDb.execSQL(MySQL);
	     String s=myDb.getPath();
	     textView.append("\r\n" + s+"\r\n");       
	     //myDb.execSQL("delete from emp1");
	     ContentValues newValues = new ContentValues();
	     newValues.put("fio", "Palaash");

	 /////////// insert picture to blob field ///////////////////// 
	    try
	    {
	    FileInputStream instream = new FileInputStream("/sdcard/IMG_9495(1).JPG"); 
	    BufferedInputStream bif = new BufferedInputStream(instream); 
	    byteImage1 = new byte[bif.available()]; 
	    bif.read(byteImage1); 
	textView.append("\r\n" + byteImage1.length+"\r\n"); 
	    newValues.put("picture", byteImage1); 

	    long ret = myDb.insert("emp1", null, newValues); 
	    if(ret<0) textView.append("\r\n!!! Error add blob filed!!!\r\n");
	    } catch (IOException e) 
	    {
	        textView.append("\r\n!!! Error: " + e+"!!!\r\n");   
	    }

	////////////Read data ////////////////////////////  
	Cursor cur = myDb.query("emp1",null, null, null, null, null, null);
	    cur.moveToFirst();
	    while (cur.isAfterLast() == false)
	    {
	        textView.append("\r\n" + cur.getString(1)+"\r\n");
	        cur.moveToNext();
	    }
	///////Read data from blob field////////////////////
	    cur.moveToFirst();
	    byteImage2=cur.getBlob(cur.getColumnIndex("picture")); 
	bmImage.setImageBitmap(BitmapFactory.decodeByteArray(byteImage2, 0, byteImage2.length));
	    textView.append("\r\n" + byteImage2.length+"\r\n"); 
	//////////////////////////    
	    cur.close();
	    myDb.close();
	  }

    public void DownloadFile() 
    {   
      Bitmap bitmap1 = null;                    
      bitmap1 = BitmapFactory.decodeFile("/mnt/sdcard/IMG_9471.JPG"); //weather.png");
      bmImage.setImageBitmap(bitmap1);
    }
}
