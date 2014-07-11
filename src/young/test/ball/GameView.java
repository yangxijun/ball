package young.test.ball;


import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

	
	   private int xMin = 0;          // This view's bounds
	   private int xMax;
	   private int yMin = 0;
	   private int yMax;
	   private int width = 200;
	   private int height = 100;
	   
	   private float ballRadius = 80; // Ball's radius
	   private float ballX = 500;  // initialize the Ball's center
	   private float ballY = 500;
	   private float ballSpeedX = 4;  // Ball's speed
	   private float ballSpeedY = 4;
	   private int clickX,clickY,moveX,moveY;
	   
	   private Rect rect = new Rect(0,0,width,height);
	      
	   // The paints
	   private Paint ballPaint;      // The paint
	   private Paint boxPaint;
	   
	   // Constructor
	   public GameView(Context context) {
	      super(context);

	      ballPaint = new Paint();
	      boxPaint = new Paint();
	      
	      
	   }
	  
	   @Override
	   protected void onDraw(Canvas canvas) {
	      // Draw the ball
	      
	      
	      
	      // Draw the box
	      ballPaint.setColor(Color.RED);
	      canvas.drawCircle(ballX, ballY, ballRadius, ballPaint);
	      
	      boxPaint.setColor(Color.BLUE);
	      canvas.drawRect(rect, boxPaint);
	        
	      // Update the position of the ball
	      update();
	  
	      invalidate();
	   }
	   
	   @Override
	   public boolean onTouchEvent(MotionEvent event){
		  
		  clickX = (int) event.getX();
		  clickY = (int) event.getY();
		  switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(!rect.contains(clickX,clickY))
				return false;
			moveX = clickX - rect.left;
			moveY = clickY - rect.top;
			break;
		
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			Rect old = new Rect(rect);
            rect.left = clickX - moveX;
            rect.top = clickY - moveY;
            rect.right = rect.left + width;
            rect.bottom = rect.top + height;
            old.union(rect);
            invalidate(old);
            break;
		}
		  return true;
		  
	   }
	   
	   private boolean collision(float circleX,float circleY,Rect rect){
		   
		   
		   float x = Math.abs(circleX - rect.left - width/2);
		   float y = Math.abs(circleY - rect.top - height/2);
		   
		   if(x > (width/2 + ballRadius))
			   return false;
		   if(y > (height/2 + ballRadius))
			   return false;
		   
		   if(x <= width/2)
			   return true;
		   if(y <= height/2)
			   return true;
		   
		   float distance = (float) (Math.pow(x - width/2, 2) + Math.pow(y - height/2, 2));
		   return(distance <= Math.pow(ballRadius, 2));
				   
		   /*
		   if((Math.abs(circleX-(rect.left+width/2))>width/2+ballRadius)
				   ||Math.abs(circleY-(rect.top+height/2))>height/2+ballRadius)
			   return false;
		   return true;*/
		   
		   
	   }
	   
	   private void update() {
		   
	      // bouncing ball of the wall
	      ballX += ballSpeedX;
	      ballY += ballSpeedY;
	      // bouncing ball
	      if (ballX + ballRadius > xMax) {
	          ballSpeedX = -ballSpeedX;
	      } else if (ballX - ballRadius < xMin) {
	          ballSpeedX = -ballSpeedX;
	      }
	      if (ballY + ballRadius > yMax) {
	          ballSpeedY = -ballSpeedY;
	      } else if (ballY - ballRadius < yMin) {
	          ballSpeedY = -ballSpeedY;
	      }
	      
	      
	      if(collision(ballX, ballY, rect)){
	    	  
	    	  // top and bottom
	    	  if((ballY < rect.top)||(ballY > rect.top+height)){
	    		  ballSpeedY = -ballSpeedY;
	    	  }
	    	  
	    	  // left and right
	    	  else if((ballX < rect.left)||(ballX > rect.left+width)){
				ballSpeedX = -ballSpeedX;
			  }
	    	  else{
	    		  ballSpeedX = -ballSpeedX;
	    		  ballSpeedY = -ballSpeedY;
	    	  }
	      }
	    	  
	      
	   }
	   
	   @Override
	   public void onSizeChanged(int w, int h, int oldW, int oldH) {
	      // Set the movement bounds for the ball
	      xMax = w-1;
	      yMax = h-1;
	   }

}
