package edu.upenn.cis573.hwk2;

import java.util.ArrayList;

import android.graphics.Color;
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
   
}
