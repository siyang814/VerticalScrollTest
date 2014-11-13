package com.example.verticalscrolltest;

import java.util.zip.Inflater;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Class<android.os.Build> build_class = android.os.Build.class;

        try
		{
        	
            //取得型號

            java.lang.reflect.Field field2 = build_class.getField("MODEL");

            String model = (String) field2.get(new android.os.Build());
            
            Log.w("MYUTIL", "model = "+ model);
            
		} catch (Exception e)
		{
			// TODO: handle exception
		}
        
        
        init ();
        
        
    }
   
    @Override
    protected void onDestroy()
    {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }
    
    private int width, height;
    private FrameLayout linearLayout;
    private View v1, v2;
    private int state = -1;
    private final int CLOSE = 1;
    private final int OPEN = 2;
    private boolean isMoveed = false;
    private HandlerUp handlerUp;
    public void setHandlerUp(HandlerUp handlerUp)
	{
		this.handlerUp = handlerUp;
	}

	private void init ( )
    {
    	linearLayout = new FrameLayout(this);
    	v1 = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
    	v2 = LayoutInflater.from(this).inflate(R.layout.activity_main1, null );
    	
    	LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams  
        (LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
    	
    	linearLayout.addView(v1, p3 );
    	linearLayout.addView(v2, p3);
    	
    	setContentView( linearLayout );
    }
    
    private float currentY, moveY, downY, upY, currentViewY, currentView1Y;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
    	// TODO Auto-generated method stub
    	
    	switch ( event.getAction() )
    	{
    	case MotionEvent.ACTION_DOWN:
    		downY = event.getY();
    		currentViewY = v2.getY();
    		break;
    	case MotionEvent.ACTION_UP:
    		upY = event.getY();
    		if ( changeView( downY, upY ) )
    		{
    			isMoveed = true;
    			//切换View
//    			MyUtil.Log("切换");
    			if ( state == CLOSE )
    			{
    				moveToY(-height);
    			}
    			else
    			{
    				moveToY(0);
    			}
    			
    		}
    		else
    		{
//    			v1.setY(currentV1Y);
//    			MyUtil.Log("返回");
//    			moveToY(-height);
    			isMoveed = false;
    			moveToY(currentViewY);
    		}
    		
    		break;
    	case MotionEvent.ACTION_MOVE:
    		if ( moveing ) break;
    		moveY = event.getY();
    		float moveedY = moveY - downY;
    		v2.setY(currentViewY + moveedY);
//    		v2.setY(v2.getY() + moveY);
    		break;
    	}
    	
    	return super.onTouchEvent(event);
    }
    private boolean moveing = false;
    private void moveToY ( final float Y )
    {
    	if ( moveing ) return;
    	moveing = true;
    	final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg)
			{
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				v2.setY(msg.what);
//				v2.postInvalidate();
			}
    		
    	};
    	
    	new Thread(new Runnable()
		{
    		float num = v2.getY();
    		final int currentPixel = 50;
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				MyUtil.Log("moveing Start");
				if ( Y == 0 )
				{
					while ( num < Y )
					{
						MyUtil.Log("num = " + num + "Y = "+ Y);
						try
						{
							Thread.sleep(10);
						} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						num += currentPixel;
//						v2.setY(num);
						handler.sendEmptyMessage((int)num);
					}
					MyUtil.Log("moveing End");
					handler.sendEmptyMessage((int)Y);
					moveing = false;
				}
				else if ( Y == -height )
				{
					while ( num > Y )
					{
						MyUtil.Log("num = " + num + "Y = "+ Y);
						try
						{
							Thread.sleep(10);
						} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						num -= currentPixel;
//						v2.setY(num);
						handler.sendEmptyMessage((int)num);
					}
					MyUtil.Log("moveing End");
					handler.sendEmptyMessage((int)Y);
					moveing = false;
				}
//				v2.setY(Y);
				//动作结束后执行方法
				if ( isMoveed )
				{
					handlerUp();
				}
			}
		}).start();
    }
    
    interface HandlerUp{
    	public void Open();
    	public void Close();
    }
    
    private void handlerUp ()
    {	
//    	startActivity( new Intent(this, SPActivity.class));
    	startActivity( new Intent(this, LoadingImage.class));
    	if ( handlerUp != null )
    	{
    		switch ( state )
    		{
    		case OPEN:
    			handlerUp.Open();
    			break;
    		case CLOSE:
    			handlerUp.Close();
    			break;
    		}
    	}
    }
    
    private boolean changeView ( float downY, float upY )
    {
    	if ( upY < downY )
    	{
    		state = CLOSE;
    	}
    	else
    	{
    		state = OPEN;
    	}
    	return (height / 3) < Math.abs(upY - downY);
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
    	// TODO Auto-generated method stub
    	getHeight();
    	super.onWindowFocusChanged(hasFocus);
    	Log.w("MYUTIL", "onWindowFocusChanged");
    }
    
    private void getHeight ()
    {
    	
    	DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        
        width = display.widthPixels;
        height = display.heightPixels;
        
    	height = height - getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    	View v = linearLayout.getChildAt(1);
    	v.setY(-height);
    }
    
}
