package com.example.coverflow;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;


public class CoverFlowExample extends Activity {
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   
	    CoverFlow coverFlow;
	    coverFlow = new CoverFlow(this);
	    
	    coverFlow.setAdapter(new ImageAdapter(this));

	    ImageAdapter coverImageAdapter =  new ImageAdapter(this);
	    
	    coverImageAdapter.createReflectedImages();
	    
	    coverFlow.setAdapter(coverImageAdapter);
	    
	    coverFlow.setSpacing(-15);
	    coverFlow.setSelection(8, true);
	    
	    
	    setContentView(coverFlow);
	    
	    //Use this if you want to use XML layout file
	    //setContentView(R.layout.main);
	    //coverFlow =  (CoverFlow) findViewById(R.id.coverflow);
	        
	
	}
	
	public class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;

	    private FileInputStream fis;
	       
	    private Integer[] mImageIds = {
	    		R.drawable.kasabian_kasabian,
	            R.drawable.starssailor_silence_is_easy,
	            R.drawable.killers_day_and_age,
	            R.drawable.garbage_bleed_like_me,
	            R.drawable.death_cub_for_cutie_the_photo_album,
	            R.drawable.kasabian_kasabian,
	            R.drawable.massive_attack_collected,
	            R.drawable.muse_the_resistance,
	            R.drawable.starssailor_silence_is_easy
	    };

	    private ImageView[] mImages;
	    
	    public ImageAdapter(Context c) {
	    	mContext = c;
	    	mImages = new ImageView[mImageIds.length];
	    }
	    
		public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
	    	Bitmap bm = null;
	    	
	    	// First decode with inJustDecodeBounds=true to check dimensions
	    	final BitmapFactory.Options options = new BitmapFactory.Options();
	    	options.inJustDecodeBounds = true;
	    	BitmapFactory.decodeFile(path, options);
	    	
	    	// Calculate inSampleSize
	    	options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    	
	    	// Decode bitmap with inSampleSize set
	    	options.inJustDecodeBounds = false;
	    	bm = BitmapFactory.decodeFile(path, options); 
	    	
	    	return bm; 	
	    }
		
	    public int calculateInSampleSize(
	    		
	        	BitmapFactory.Options options, int reqWidth, int reqHeight) {
	        	// Raw height and width of image
	        	final int height = options.outHeight;
	        	final int width = options.outWidth;
	        	int inSampleSize = 1;
	            
	        	if (height > reqHeight || width > reqWidth) {
	        		if (width > height) {
	        			inSampleSize = Math.round((float)height / (float)reqHeight);  	
	        		} else {
	        			inSampleSize = Math.round((float)width / (float)reqWidth);  	
	        		}  	
	        	}
	        	
	        	return inSampleSize;  	
	        }


		public boolean createReflectedImages() {
		        //The gap we want between the reflection and the original image
		        final int reflectionGap = 4;
		        
		        
		        int index = 0;
		        for (int imageId : mImageIds) {
			    	Bitmap originalImage = BitmapFactory.decodeResource(getResources(), 
			    			imageId);
			    /*String ExternalStorageDirectoryPath = Environment
			    		.getExternalStorageDirectory()
			    		.getAbsolutePath();
			    
				String targetPath = ExternalStorageDirectoryPath + "/Test/";
			    
				Toast.makeText(mContext, targetPath, Toast.LENGTH_LONG).show();
				File targetDirector = new File(targetPath);
			    	        
				File[] files = targetDirector.listFiles();*/
						
				/*for (File file : files){
							
					String path = file.getAbsolutePath();
					Toast.makeText(mContext, "file path=" + path, Toast.LENGTH_LONG).show();
					Bitmap originalImage = decodeSampledBitmapFromUri(path, 220, 220);
				*/
			        int width = originalImage.getWidth();
			        int height = originalImage.getHeight();
			        
		   
			        //This will not scale but will flip on the Y axis
			        Matrix matrix = new Matrix();
			        matrix.preScale(1, -1);
			        
			        //Create a Bitmap with the flip matrix applied to it.
			        //We only want the bottom half of the image
			        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height/2, width, height/2, matrix, false);
			        
			            
			        //Create a new bitmap with same width but taller to fit reflection
			        Bitmap bitmapWithReflection = Bitmap.createBitmap(width 
			          , (height + height/2), Config.ARGB_8888);
			      
			       //Create a new Canvas with the bitmap that's big enough for
			       //the image plus gap plus reflection
			       Canvas canvas = new Canvas(bitmapWithReflection);
			       //Draw in the original image
			       canvas.drawBitmap(originalImage, 0, 0, null);
			       //Draw in the gap
			       Paint deafaultPaint = new Paint();
			       canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
			       //Draw in the reflection
			       canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);
			       
			       //Create a shader that is a linear gradient that covers the reflection
			       Paint paint = new Paint(); 
			       LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, 
			         bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, 
			         TileMode.CLAMP); 
			       //Set the paint to use this shader (linear gradient)
			       paint.setShader(shader); 
			       //Set the Transfer mode to be porter duff and destination in
			       paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
			       //Draw a rectangle using the paint with our linear gradient
			       canvas.drawRect(0, height, width, 
			         bitmapWithReflection.getHeight() + reflectionGap, paint); 
			       
			       ImageView imageView = new ImageView(mContext);
			       imageView.setImageBitmap(bitmapWithReflection);
			       imageView.setLayoutParams(new CoverFlow.LayoutParams(120, 180));
			       imageView.setScaleType(ScaleType.MATRIX);
			       mImages[index++] = imageView;
			       
		        }
		    	return true;
		}

	    public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {

	    	//Use this code if you want to load from resources
	        //ImageView i = new ImageView(mContext);
	        //i.setImageResource(mImageIds[position]);
	        //i.setLayoutParams(new CoverFlow.LayoutParams(130, 130));
	        //i.setScaleType(ImageView.ScaleType.MATRIX);	        
	        //return i;
	    	
	    	return mImages[position];
	    }
		 /** Returns the size (0.0f to 1.0f) of the views 
	     * depending on the 'offset' to the center. */ 
	     public float getScale(boolean focused, int offset) { 
	       /* Formula: 1 / (2 ^ offset) */ 
	         return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset))); 
	     } 

	}

}
