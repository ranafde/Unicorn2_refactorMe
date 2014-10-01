package edu.upenn.cis573.hwk2;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;

public class BackgroundDrawingTask extends AsyncTask<GameView, Void, GameView> {

	// this method gets run in the background
	protected GameView doInBackground(GameView... gameview) {
		try { 
			// note: you can change these values to make the unicorn go faster/slower
			Thread.sleep(10); 
			Point xy = gameview[0].getBitmapImage().getImagePointXY();
			Image img;
					//gameview[0].getBitmapImage().getImagePointXY());
			xy.x += 10;
			xy.y += gameview[0].getYChange();
			//bitmapImage.setImagePointXY(xy); 
			img = gameview[0].getBitmapImage();
			img.setImagePointXY(xy);
			gameview[0].setBitmapImage(img); 
		} 
		catch (Exception e) { }
		// the return value is passed to "onPostExecute" but isn't actually used here
		return gameview[0]; 
	}
	
	// this method gets run in the UI thread
	protected void onPostExecute(GameView result) {
		// redraw the View
		result.invalidate();
		if (result.getScore() < 10) {
			// need to start a new thread to make the unicorn keep moving
			BackgroundDrawingTask task = new BackgroundDrawingTask();
			task.execute(result);
		}
		else {
			// game over, man!
			result.endTime = System.currentTimeMillis();
			// these methods are deprecated but it's okay to use them... probably.
			GameActivity.instance.removeDialog(1);
			GameActivity.instance.showDialog(1);
		}
	}    	

}
