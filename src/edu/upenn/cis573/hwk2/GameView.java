package edu.upenn.cis573.hwk2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class GameView extends View {
    //private Bitmap image;
    private Stroke stroke;
    private boolean killed = false;
    private boolean newUnicorn = true;
    private Image bitmapImage;
    //private Point imagePointXY;
    private int score = 0;
    private int yChange = 0;
    public long startTime;
    public long endTime;

    public GameView(Context context) {
	    super(context);
	    //setBackgroundResource(R.drawable.space);
	    
	    //image = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn);
	    //image = Bitmap.createScaledBitmap(image, 150, 150, false);
	    //imagePointXY = new Point(-150,100);
	    
	    //bitmapImage = new Image(-150,100);
	    
	    //bitmapImage.image = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn);
	    //bitmapImage.image = Bitmap.createScaledBitmap(bitmapImage.image, 150, 150, false);
	    
	    //setImageProperties(R.drawable.unicorn);
	    //stroke = new Stroke();
	    initializeParameters();
    }
    
    public GameView(Context context, AttributeSet attributeSet) {
    	super(context, attributeSet);
	    
    	//setBackgroundResource(R.drawable.space);
	   
    	// image = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn);
	   // image = Bitmap.createScaledBitmap(image, 150, 150, false);
	    //imagePointXY = new Point(-150,100);
	    
    	//bitmapImage = new Image(-150,100);
	    
    	//bitmapImage.image = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn);
	    //bitmapImage.image = Bitmap.createScaledBitmap(bitmapImage.image, 150, 150, false);
	    
	    //setImageProperties(R.drawable.unicorn);
	    
	    //stroke = new Stroke();
    	initializeParameters();
    }
    
    /*
     * This method is automatically invoked when the View is displayed.
     * It is also called after you call "invalidate" on this object.
     */
    protected void onDraw(Canvas canvas) {    	

    	// resets the position of the unicorn if one is killed or reaches the right edge
    	if (newUnicorn || bitmapImage.getImagePointXY().x >= this.getWidth()) {
    		
    		//bitmapImage.image = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn);
    		//bitmapImage.image = Bitmap.createScaledBitmap(bitmapImage.image, 150, 150, false);
    		setImageProperties(R.drawable.unicorn);
    		
    		bitmapImage.getImagePointXY().x = -150;
    		bitmapImage.getImagePointXY().y = (int)(Math.random() * 200 + 200);
    		yChange = (int)(10 - Math.random() * 20);
    		newUnicorn = false;
    		killed = false;
    	}

		// show the exploding image when the unicorn is killed
    	if (killed) {
    		//Bitmap explode = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
    	    //explode = Bitmap.createScaledBitmap(explode, 150, 150, false);
    		//canvas.drawBitmap(explode, bitmapImage.getImagePointXY().x, bitmapImage.getImagePointXY().y, null);
    		
    		//bitmapImage.image = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
    		//bitmapImage.image = Bitmap.createScaledBitmap(bitmapImage.image, 150, 150, false);
    		setImageProperties(R.drawable.explosion);
    		
    		canvas.drawBitmap(bitmapImage.image, bitmapImage.getImagePointXY().x, bitmapImage.getImagePointXY().y, null);
    		newUnicorn = true;
    		try { Thread.sleep(10); } catch (Exception e) { }
    		invalidate();
    		return;
    	}

    	// draws the unicorn at the specified point
		canvas.drawBitmap(bitmapImage.image, bitmapImage.getImagePointXY().x, bitmapImage.getImagePointXY().y, null);
    	
		// draws the stroke
		if (stroke.numberOfPoints() > 1) {
    		for (int i = 0; i < stroke.numberOfPoints()-1; i++) {
    			int startX = stroke.getPointXY(i).x;
    			int stopX = stroke.getPointXY(i).x;
    			int startY = stroke.getPointXY(i).y;
    			int stopY = stroke.getPointXY(i+1).y;
    			Paint paint = new Paint();
    			paint.setColor(stroke.getLineColor());
    			paint.setStrokeWidth(stroke.getLineWidth());
    			canvas.drawLine(startX, startY, stopX, stopY, paint);
    		}
    	}
    }

    /* 
     * This method is automatically called when the user touches the screen.
     */
    public boolean onTouchEvent(MotionEvent event) {
    	//Point pointxy = new Point();
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		//pointxy.set((int)event.getX(),(int)event.getY());
    		//stroke.setPointXY(pointxy);
    		recordPointXY(event);
    	}
    	else if (event.getAction() == MotionEvent.ACTION_MOVE) {
    		//pointxy.set((int)event.getX(),(int)event.getY());
    		//stroke.setPointXY(pointxy);
    		recordPointXY(event);
    	}
    	else if (event.getAction() == MotionEvent.ACTION_UP) {
    		stroke.clearPoints();
    	}
    	else {
    		return false;
    	}
    	
    	// see if the point is within the boundary of the image
    	int width = bitmapImage.image.getWidth();
    	int height = bitmapImage.image.getHeight();
    	float x = event.getX();
    	float y = event.getY();
    	// the !killed thing here is to prevent a "double-kill" that could occur
    	// while the "explosion" image is being shown
    	//if (!killed && x > bitmapImage.getImagePointXY().x && x < bitmapImage.getImagePointXY().x + width && y > bitmapImage.getImagePointXY().y && y < bitmapImage.getImagePointXY().y + height) {
    	if (!killed && bitmapImage.pointInBounds(x,y, height, width)) {	
    		killed = true;
    		score++;
    		((TextView)(GameActivity.instance.getScoreboard())).setText(""+score);
    	}
    	
    	// forces a redraw of the View
    	invalidate();
    	
    	return true;
    }    

    public void recordPointXY(MotionEvent event){
    	Point pointxy = new Point();
    	pointxy.set((int)event.getX(),(int)event.getY());
    	stroke.setPointXY(pointxy);
    }
    
    private void setImageProperties(int resId){
    	//Bitmap img;
    	
    	bitmapImage.setImage(BitmapFactory.decodeResource(getResources(), resId));
    	bitmapImage.setImage(Bitmap.createScaledBitmap(bitmapImage.getImage(), 150, 150, false));
    	
    	//bitmapImage.image = BitmapFactory.decodeResource(getResources(), resId);
		//bitmapImage.image = Bitmap.createScaledBitmap(bitmapImage.image, 150, 150, false);	
    }
    
    private void initializeParameters(){
    	setBackgroundResource(R.drawable.space);
    	bitmapImage = new Image(-150,100);
    	setImageProperties(R.drawable.unicorn);
	    stroke = new Stroke();
    }
    
    /*
     * This inner class is responsible for making the unicorn appear to move.
     * When "exec" is called on an object of this class, "doInBackground" gets
     * called in a background thread. It just waits 10ms and then updates the
     * image's position. Then "onPostExecute" is called.
     */
    class BackgroundDrawingTask extends AsyncTask<Integer, Void, Integer> {
    	
    	// this method gets run in the background
    	protected Integer doInBackground(Integer... args) {
    		try { 
    			// note: you can change these values to make the unicorn go faster/slower
    			Thread.sleep(10); 
    			Point xy = bitmapImage.getImagePointXY();
    			xy.x += 10;
    			xy.y += yChange;
    			bitmapImage.setImagePointXY(xy); 
    			 
    		} 
    		catch (Exception e) { }
    		// the return value is passed to "onPostExecute" but isn't actually used here
    		return 1; 
    	}
    	
    	// this method gets run in the UI thread
    	protected void onPostExecute(Integer result) {
    		// redraw the View
    		invalidate();
    		if (score < 10) {
    			// need to start a new thread to make the unicorn keep moving
    			BackgroundDrawingTask task = new BackgroundDrawingTask();
    			task.execute();
    		}
    		else {
    			// game over, man!
    			endTime = System.currentTimeMillis();
    			// these methods are deprecated but it's okay to use them... probably.
    			GameActivity.instance.removeDialog(1);
    			GameActivity.instance.showDialog(1);
    		}
    	}    	
    }

}

