package com.example.verticalscrolltest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Layout1 extends LinearLayout
{
	public Context mContext;
	private ImageView img;
	public Layout1(Context context)
	{
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
		((Activity) mContext).getLayoutInflater().inflate(R.layout.activity_main, this);
		
		
		img = (ImageView) findViewById(R.id.img);
		byte[] b = null;
		b = getDefaultImg();
//		Bitmap bb = BitmapFactory.decodeByteArray(b, 0, b.length);
//		img.setImageBitmap(bb);
		setCallOutHead(b, img);
		
		
	}
	
	
	public static void setCallOutHead ( byte[] by, ImageView imgBtn )
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(by);
		
		Drawable b = BitmapDrawable.createFromStream(bais, "img");
		imgBtn.setImageDrawable(b);
	}
	
	private byte[] getDefaultImg ()
	{
		InputStream is = this.getResources().openRawResource(R.drawable.audiocall_bg);
		try
		{
			byte[] b = new byte[is.available()];
			is.read(b);
			return b;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
