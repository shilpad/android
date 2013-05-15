package com.example.uibasics;

import java.io.File;

import com.example.Classes.Milestone_information;
import com.example.carousel.Carousel;
import com.example.carousel.CarouselAdapter;
import com.example.carousel.CarouselAdapter.OnItemClickListener;
import com.example.carousel.CarouselItem;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class SearchResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
	    Carousel carousel = (Carousel)findViewById(R.id.carousel);
	    
	    carousel.setOnItemClickListener(new OnItemClickListener(){
			
				public void onItemClick(CarouselAdapter<?> parent, View view,
						int position, long id) {	
					
					int index = ((CarouselItem)parent.getChildAt(position)).getIndex();
					Toast.makeText(getApplicationContext(), 
							String.format("%d has been clicked at position %d", 
							index, position), 
							Toast.LENGTH_SHORT).show();		
					setInformationData (index);
				}
	    });

	}

	protected void setInformationData(int index) {
		// TODO Auto-generated method stub
		Milestone_information information = MilestonesDbAdapter.glstMilestoneInformation.get(index);
		
		EditText editCaption = (EditText)findViewById(R.id.editName);
		editCaption.setText(information.getCaption());
		
		EditText editNotes = (EditText)findViewById(R.id.editTextNotes);
		editNotes.setText(information.getNotes());
		
		String mediaPath = information.getMediaPath();
		Toast.makeText(getApplicationContext(), "Imagepath = " + information.getMediaPath(), Toast.LENGTH_SHORT).show();
		
		String extension = mediaPath.substring(mediaPath.length() -3);
		if ((extension.equals("mp3")  || (extension.equals("mp4"))))
		{
			// Audio or video
     		Toast.makeText(getApplicationContext(), "Selected video OR audio !!!",
                    Toast.LENGTH_SHORT).show();
     		VideoView mVideoView;
     		mVideoView = (VideoView) findViewById(R.id.videoView1);
            mVideoView.setVisibility(View.VISIBLE);
            mVideoView.setVideoPath(mediaPath);
            MediaController mediaController = new MediaController(this);
           // mediaController.setPadding(0, 0, 0, 200);
            mVideoView.setMediaController(mediaController);
            mVideoView.requestFocus();
            
            mVideoView.start();
		}
		else
		{
        	Toast.makeText(getApplicationContext(), "Selected image !!!",
                    Toast.LENGTH_SHORT).show();
        	
        	VideoView mVideoView = (VideoView) findViewById(R.id.videoView1);
            mVideoView.setVisibility(View.GONE);
            
            File file=new File(mediaPath);
            
       		try {
    		    BitmapFactory.Options options = new BitmapFactory.Options();
    		    options.inSampleSize = 4;
    		    
    	  		Utility utility = new Utility();
        		Bitmap bmp = utility.decodeFile (file);
                ImageView imageView = (ImageView) findViewById(R.id.imgView);
                imageView.setImageBitmap(bmp);
    		} catch (OutOfMemoryError e) {
    		    e.printStackTrace();
    		    System.gc();

    		    try {
    		  		Utility utility = new Utility();
    		  		Bitmap bm = utility.decodeFile (file);
    		    } catch (OutOfMemoryError e2) {
    		      e2.printStackTrace();
    		      // handle gracefully.
    		    }
    		}

		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

}
