package com.example.uibasics;

import it.sephiroth.demo.slider.R;
import it.sephiroth.demo.slider.widget.MultiDirectionSlidingDrawer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class AddDetailsActivity extends Activity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LAUNCH_CALENDAR = 2;
    private static int RESULT_LOAD_AUDIO = 3;
    private static int RESULT_TAKE_PICTURE = 4;
    Bitmap bmp;
	private Spinner spinner1;
	VideoView mVideoView;
	private int mProfileId = -1;
	String mMediaPath = null;
	
	String mCaption;
	String mNotes;
	List<String> mlstTagNames = new ArrayList<String>();
	List<String> mlstTagValues = new ArrayList<String>();
	
	private boolean mBirthdateSpinnerClicked = false;
	MyHorizontalLayout myHorizontalLayout;
	com.example.Classes.MultiDirectionSlidingDrawer mDrawer;

	//private MyView mDrawingArea;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_details);
		
		//myHorizontalLayout = (MyHorizontalLayout)findViewById(R.id.mygalleryAdd);
        
	    int callerId = 0;
		Bundle extras = getIntent().getExtras ();
	    if (extras != null)
	    {
	    	callerId = extras.getInt (MainActivity.EXTRA_CALLERID);
	    	mProfileId = extras.getInt (MyHorizontalLayout.EXTRA_PROFILEID);
	  	 }
	        
	    //myHorizontalLayout.setM_CallerId (callerId);
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
        		//myHorizontalLayout.add (bm, cursor.getInt(0), bHightlightImage);
        	}while (cursor.moveToNext());
        }	
        
        cursor.close();
        mDbHelper.close();

		/*addItemsOnSpinner1 ();
		addListenerOnSpinnerItemSelection();
		
		Calendar c = Calendar.getInstance();
	    System.out.println("Current time => " + c.getTime());

	    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	    String formattedDate = df.format(c.getTime());
	    
	 	TextView textDate = (TextView) findViewById (R.id.textViewDate);
	 	textDate.setText(formattedDate);
	 	
	 	ImageView image = (ImageView) findViewById(R.id.imgView);
	 	
	 	registerForContextMenu(image);*/
	 	
	 }	

	  @Override
	   public void onContentChanged()
	   {
	   	super.onContentChanged();
	  // 	mCloseButton = (Button) findViewById( R.id.button_close );
	  // 	mOpenButton = (Button) findViewById( R.id.button_open );
	   	mDrawer = (com.example.Classes.MultiDirectionSlidingDrawer) findViewById( R.id.drawer );
	   }
	  
	  @Override  
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) 
	{  
		  super.onCreateContextMenu(menu, v, menuInfo);
	      Toast.makeText(this, "onCreateContextMenu called", Toast.LENGTH_SHORT).show();  
	       
		//  menu.setHeaderTitle("Select existing audio, video or image Or ");  
		  menu.add(0, v.getId(), 0, "Choose Photo or Video");  
		  menu.add(0, v.getId(), 0, "Choose Audio");  
		  menu.add(0, v.getId(), 0, "Take Photo, Video or record Audio");  
	}  
	
	public boolean onContextItemSelected(MenuItem item)
	{
		if(item.getTitle()=="Choose Photo or Video"){function1(item.getItemId());}
		else if(item.getTitle()=="Choose Audio"){function2(item.getItemId());}  
	    else if(item.getTitle()=="Take Photo, Video or record Audio"){function3(item.getItemId());}  
	    else {return false;}  
	    return true;  
	}  
	   
    public void function1(int id){  
        Toast.makeText(this, "function 1 called", Toast.LENGTH_SHORT).show();  
    	
        /*Intent i = new Intent(
        Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
 
        startActivityForResult(i, RESULT_LOAD_IMAGE);*/

        Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
        //comma-separated MIME types
        mediaChooser.setType("video/*, images/*");
        mediaChooser.putExtra(Intent.CATEGORY_OPENABLE, true);
        mediaChooser.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(mediaChooser,"Choose video or image"), RESULT_LOAD_IMAGE);
    }  
    
    public void function2(int id){  
        Toast.makeText(this, "function 2 called", Toast.LENGTH_SHORT).show(); 
        
        Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
        
        mediaChooser.setType("audio/*");
        mediaChooser.putExtra(Intent.CATEGORY_OPENABLE, true);
        mediaChooser.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(mediaChooser,"Choose audio"), RESULT_LOAD_IMAGE);
    } 
    
    public void function3(int id){  
        Toast.makeText(this, "function 3 called", Toast.LENGTH_SHORT).show();  
        //Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE );
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE );
        Intent intent = new Intent("android.media.action.VIDEO_CAMERA");
        startActivityForResult(intent, RESULT_TAKE_PICTURE);
    }
    
	public void redraw ()
	{
		//mDrawingArea.invalidate();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_details, menu);
		return true;
	}

	public void onClickDate (View v)
	{
		Intent intent = new Intent (getApplicationContext(), CalendarActivity.class);
		startActivityForResult(intent, RESULT_LAUNCH_CALENDAR);
	}
	
	public void onClickAdd (View v)
	{
		/*Intent i = new Intent(
                  Intent.ACTION_PICK,
                  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           
        startActivityForResult(i, RESULT_LOAD_IMAGE);*/
        
      //  Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
        //comma-separated MIME types
       // mediaChooser.setType("*/*");//video/*, images/*, audio/*");
       // startActivityForResult(Intent.createChooser(mediaChooser,"Select picture, video or audio"), RESULT_LOAD_IMAGE);
	}
	
	public void onClickSave (View v)
	{
	 	EditText editCaption = (EditText) findViewById (R.id.editName);
    	String caption = editCaption.getText().toString();
    	
    	EditText editNotes = (EditText) findViewById (R.id.editTextNotes);
    	String notes = editNotes.getText().toString();
 
    	TextView textDate = (TextView) findViewById (R.id.textViewDate);
    	String date = textDate.getText().toString();
    	
    	CheckBox checkboxFirst = (CheckBox) findViewById (R.id.checkBoxTagFirst);
    	if (checkboxFirst.isChecked())
    	{
    		mlstTagNames.add("First");
    		mlstTagValues.add("1");
    	}
    	
    	CheckBox checkboxSchool = (CheckBox) findViewById (R.id.checkBoxTagSchool);
    	if (checkboxSchool.isChecked())
    	{
    		mlstTagNames.add("School");
    		mlstTagValues.add("1");
    	}
    	
    	if (mBirthdateSpinnerClicked)
    	{
    		Spinner mySpinner = (Spinner)findViewById(R.id.spinnerGender);
    		String tagBirthdate = mySpinner.getSelectedItem().toString();


    		mlstTagNames.add("Birthdate");
    		mlstTagValues.add(tagBirthdate);
    	}
    	
    	MilestonesDbAdapter mDbHelper = new MilestonesDbAdapter(this);
        mDbHelper.open();
        mDbHelper.createInformation(mProfileId, caption, notes,
	    	 mMediaPath, date, mlstTagNames, mlstTagValues);
        
        mDbHelper.close ();
        finish();
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         
        Toast.makeText(getApplicationContext(), "resultCode=" + resultCode,
                Toast.LENGTH_SHORT).show();
        
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != intent) {
            Uri selectedImage = intent.getData();
    		Toast.makeText(getApplicationContext(), "selectedImage=" + selectedImage,
                    Toast.LENGTH_SHORT).show();
    		
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mMediaPath = cursor.getString(columnIndex);
    		Toast.makeText(getApplicationContext(), "picturePath=" + mMediaPath,
                    Toast.LENGTH_SHORT).show();
            cursor.close();

            List<String> lstSegments = selectedImage.getPathSegments();
            
            if (lstSegments.get(1).contentEquals("video") || lstSegments.get(1).contentEquals("audio"))
            {
        		Toast.makeText(getApplicationContext(), "Selected video OR audio !!!",
                        Toast.LENGTH_SHORT).show();
        		
        		ImageView imageView = (ImageView) findViewById(R.id.imgView);
                imageView.setImageDrawable(null);
                
                mVideoView = (VideoView) findViewById(R.id.videoView1);
                mVideoView.setVisibility(View.VISIBLE);
                mVideoView.setVideoPath(mMediaPath);
                MediaController mediaController = new MediaController(this);
                mediaController.setPadding(0, 0, 0, 150);
                mVideoView.setMediaController(mediaController);
                mVideoView.requestFocus();
                
                mVideoView.start();
            }
            else if (lstSegments.get(1).contentEquals("images"))
            {
            	Toast.makeText(getApplicationContext(), "Selected image !!!",
                        Toast.LENGTH_SHORT).show();
            	
            	mVideoView = (VideoView) findViewById(R.id.videoView1);
                mVideoView.setVisibility(View.GONE);
                
                if(bmp != null && !bmp.isRecycled())
                {
                	bmp.recycle();
                    bmp = null;               
                }
                
                File file=new File(mMediaPath);
                
           		try {
        		    BitmapFactory.Options options = new BitmapFactory.Options();
        		    options.inSampleSize = 4;
        		    
                   bmp= decodeFile(file); //this is new bitmap which you can use for your purpose 
                    ImageView imageView = (ImageView) findViewById(R.id.imgView);
                    imageView.setImageBitmap(bmp);
        		} catch (OutOfMemoryError e) {
        		    e.printStackTrace();
        		    System.gc();

        		    try {
        		        bmp = decodeFile(file);
        		    } catch (OutOfMemoryError e2) {
        		      e2.printStackTrace();
        		      // handle gracefully.
        		    }
        		}

            } 
            else if (lstSegments.get(1).contentEquals("audio"))
            {
            	Toast.makeText(getApplicationContext(), "Audio file selected",
                        Toast.LENGTH_SHORT).show();
            	try {
            		final MediaPlayer mediaPlayer = new MediaPlayer();
            		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            		mediaPlayer.setDataSource(getApplicationContext(), selectedImage);
            		mediaPlayer.prepareAsync();
            		
            		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
						
						@Override
						public void onPrepared(MediaPlayer mp) {
							// TODO Auto-generated method stub
							mediaPlayer.start();
						}
					});
            		
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            }
        }
        else if (requestCode == RESULT_LOAD_AUDIO && resultCode == RESULT_OK && null != intent) {
        }
        else if (requestCode == RESULT_LAUNCH_CALENDAR && resultCode == RESULT_OK && null != intent) 
        {
        	//set the value in the spinner
            Bundle extras = intent.getExtras ();
            if (extras != null)
            {
            	Toast.makeText(getApplicationContext(), "!!!!!", Toast.LENGTH_SHORT).show();
            	

            	String date = extras.get (CalendarActivity.EXTRA_DATE).toString ();
            	if (date != null)
            	{
            		// use the value 
            	 	TextView textDate = (TextView) findViewById (R.id.textViewDate);
            	 	textDate.setText(date);
            	}
            }
        }
    }
    
 private Bitmap decodeFile(File f){
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
			
	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_spinner_item, list);
	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner1.setAdapter(dataAdapter);
 }
 	
 	public void addListenerOnSpinnerItemSelection() {
 		spinner1 = (Spinner) findViewById(R.id.spinnerGender);
 		
        Toast.makeText(getApplicationContext(), "Position clicked=" + spinner1.getSelectedItemPosition(),
                Toast.LENGTH_SHORT).show();
        
        spinner1.setOnItemSelectedListener(Spinner_OnItemSelected);
 	  }
 	
 	
 private AdapterView.OnItemSelectedListener Spinner_OnItemSelected = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			// TODO Auto-generated method stub
		
			String selected = parent.getItemAtPosition(pos).toString();
			Toast.makeText(getApplicationContext(), "onItemSelected" + pos + id + " Text = " + selected,
	                Toast.LENGTH_SHORT).show();
			
			if (pos != 0)
			{
				Toast.makeText(getApplicationContext(), "Birthdate tag selected",
		                Toast.LENGTH_SHORT).show();
				mBirthdateSpinnerClicked = true;
			}
			
    	}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "onNothingSelected",
	                Toast.LENGTH_SHORT).show();
		}
	};
	
 	private View.OnFocusChangeListener Spinner_OnFocus = new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			int item = spinner1.getSelectedItemPosition();
			Toast.makeText(getApplicationContext(), "OnFocusChangeListener Id=" + v.getId() + " Item=" + item,
	                Toast.LENGTH_SHORT).show();
		}
	};
 	
 	private View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			
			int item = spinner1.getSelectedItemPosition();
			Toast.makeText(getApplicationContext(), "OnTouchListener Id=" + v.getId() + " Item=" + item,
	                Toast.LENGTH_SHORT).show();
			
			
	        if (/*event.getAction() == MotionEvent.ACTION_UP &&*/ item==1) {
	    		Intent intent = new Intent (getApplicationContext(), CalendarActivity.class);
	    		startActivity(intent);
 	        }
 	        return false;
		}
 	};
 	
 	private View.OnKeyListener Spinner_OnKey = new View.OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			int item = spinner1.getSelectedItemPosition();
			Toast.makeText(getApplicationContext(), "OnKeyListener Id=" + v.getId() + " Item=" + item,
	                Toast.LENGTH_SHORT).show();
 	        if (/*keyCode == KeyEvent.KEYCODE_DPAD_CENTER && */item==1) {
 	          Intent intent = new Intent (getApplicationContext(), CalendarActivity.class);
	    		startActivity(intent);
 	            return true;
 	        } else {
 	            return false;
 	        }
		}
 	};
}
