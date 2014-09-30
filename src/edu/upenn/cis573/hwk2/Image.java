package edu.upenn.cis573.hwk2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.widget.TextView;

public class Image {
	public Bitmap image;
	private Point imagePointXY;
	
	Image(int x, int y){
		imagePointXY = new Point(x, y);    
	}

	public Point getImagePointXY(){
		return imagePointXY;
	}
	
	public void setImagePointXY(Point xy){
		imagePointXY = xy;
	}
	
	public boolean pointInBounds(float xcoordinate, float ycoordinate, int height, int width){	
		if (xcoordinate > getImagePointXY().x && xcoordinate < getImagePointXY().x + width && ycoordinate > getImagePointXY().y && ycoordinate < getImagePointXY().y + height) {
			return true;
		}
		return false;
	}
	
	public void setImage(Bitmap image){
		this.image = image;
	}
	
	public Bitmap getImage(){
		return image;
	}
}
