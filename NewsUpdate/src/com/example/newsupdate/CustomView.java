package com.example.newsupdate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class CustomView extends View {
	 
    public CustomView (Context context) {
        super(context);
    }
 
    public CustomView (Context context, AttributeSet attrs) {
        super(context);
    }
    @Override
    public void onDraw (Canvas canvas) {
        int dCenter = 40;    //Distance from center in px, NOT in dp
        int centerX = (int)(getWidth()/2);
        int centerY = (int)(getHeight()/2);
 
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //The square will draw in the color blue now
 
        Rect rect = new Rect(centerX-dCenter, centerY-dCenter, centerX+dCenter, centerY+dCenter);
        canvas.drawRect(rect, paint);
    }
}