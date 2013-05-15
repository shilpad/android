package com.example.uibasics;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyHorizontalLayout extends LinearLayout {
	Context myContext;
	private int mCallerId = 0;
	public final static String EXTRA_PROFILEID = "com.newdomain.myreminder.PROFILEID";
	
	ArrayList<String> itemList = new ArrayList<String>();
		
	public MyHorizontalLayout(Context context) {
		super(context);
		myContext = context;
	}

	public MyHorizontalLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		myContext = context;
	}

	public MyHorizontalLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		myContext = context;
	}
	
	void add (Bitmap bm, int profileId, boolean bHightlightImage)
	{
		addView(getImageView(bm, profileId, bHightlightImage));
	}
	
	ImageView getImageView(Bitmap bm, final int profileId, boolean bHightlightImage){
		
		ImageView imageView = new ImageView(myContext);
    	imageView.setLayoutParams(new LayoutParams(220, 220));
    	imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    	imageView.setImageBitmap(bm);
    	
    	if (bHightlightImage)
    	{
    		imageView.setPadding(2, 0, 2, 0);
    		imageView.setBackgroundColor(getResources().getColor(R.color.color_red));
    	}
    	    	
    	imageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Toast.makeText(myContext, "Inside HorizontalLayout, Profile Id=" + profileId, Toast.LENGTH_LONG).show();
        		if (mCallerId == 1)
	    		{
	    			Intent intent = new Intent (myContext, AddDetailsActivity.class);
	    			intent.putExtra(EXTRA_PROFILEID, profileId);
	    			myContext.startActivity (intent);
	    		}
	    		else  if (mCallerId == 2)
	    		{
	    			Intent intent = new Intent (myContext, SearchDetailsActivity.class);
	    			intent.putExtra(EXTRA_PROFILEID, profileId);
	    			myContext.startActivity (intent);
	    		}

			}});
    	 
		return imageView;
	}

	void add(String path){
		int newIdx = itemList.size();
		itemList.add(path);
		//Toast.makeText(myContext, path, Toast.LENGTH_LONG).show();
		addView(getImageView(newIdx));
	}
	
	ImageView getImageView(final int i){
		Bitmap bm = null;
		if (i < itemList.size()){
			bm = decodeSampledBitmapFromUri(itemList.get(i), 220, 220);
		}
		
		ImageView imageView = new ImageView(myContext);
    	imageView.setLayoutParams(new LayoutParams(220, 220));
    	imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    	imageView.setImageBitmap(bm);

    	imageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	   		    Toast.makeText(myContext, 
	      		      "Clicked - " + itemList.get(i), 
	      		      Toast.LENGTH_LONG).show();
	   		    
	    		if (mCallerId == 1)
	    		{
	    			Intent intent = new Intent (myContext, AddDetailsActivity.class);
	    			myContext.startActivity (intent);
	    		}
	    		else if (mCallerId == 2)
	    		{
	    			Intent intent = new Intent (myContext, SearchDetailsActivity.class);
	    			myContext.startActivity (intent);
	    		}

			}});
    	 
		return imageView;
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

	public int getM_CallerId() {
		return mCallerId;
	}

	public void setM_CallerId(int m_CallerId) {
		this.mCallerId = m_CallerId;
	}
}
