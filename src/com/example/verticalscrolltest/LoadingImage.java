package com.example.verticalscrolltest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoadingImage extends Activity
{
	ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		img = (ImageView) findViewById(R.id.img);
//		byte[] b = null;
//		b = getDefaultImg();
////		Bitmap bb = BitmapFactory.decodeByteArray(b, 0, b.length);
////		img.setImageBitmap(bb);
//		setCallOutHead(b, img);
		setContentView( new Layout1(this));
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
