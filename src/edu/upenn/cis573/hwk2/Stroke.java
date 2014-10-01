package edu.upenn.cis573.hwk2;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Stroke {
	
	private ArrayList<Point> xyPoints;
    private static final int lineColor = Color.RED;
    private static final int lineWidth = 10;
    
    Stroke(){
    	xyPoints = new ArrayList<Point>();	
    }
     
    public Point getPointXY(int idx){
    	return xyPoints.get(idx);
    }
	   
    public void setPointXY(Point xy){
     	xyPoints.add(xy);
    }
    
    public void clearPoints(){
    	xyPoints.clear();
    }
    
    public int numberOfPoints(){
    	return xyPoints.size();
    }
    
    public int getLineWidth(){
    	return lineWidth;
    }
    
    public int getLineColor(){
    	return lineColor;
    }
    
    public void drawStroke(Canvas canvas){
    	if (numberOfPoints() > 1) {
    		for (int i = 0; i < numberOfPoints()-1; i++) {
    			int startX = getPointXY(i).x;
    			int stopX = getPointXY(i).x;
    			int startY = getPointXY(i).y;
    			int stopY = getPointXY(i+1).y;
    			Paint paint = new Paint();
    			paint.setColor(getLineColor());
    			paint.setStrokeWidth(getLineWidth());
    			canvas.drawLine(startX, startY, stopX, stopY, paint);
    		}
    	}
    }
   
}
