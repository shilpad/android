package com.example.uibasics;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MyView extends View {

	Paint mTextPaint;
	Context mContext;
	int mXcoordinate, mYcoordinate = 0;
	
	public MyView(Context context) {
		super(context);
		init(context);
	}
	
	public MyView (Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}
	
    public MyView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    
	public void init (Context context)
	{
		mContext = context;
		mTextPaint = new Paint();
		
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		mXcoordinate = width/2 -10;
		int height = size.y;
		mYcoordinate = height/2;
	}
	
	protected void onDraw (Canvas canvas)
	{
		super.onDraw (canvas);
		
		ImageView imageView = (ImageView) ((Activity)mContext).findViewById(R.id.imgView);
		
		canvas.drawText("Shilpa", mXcoordinate, mYcoordinate, mTextPaint);
		imageView.draw(canvas);
	}
}