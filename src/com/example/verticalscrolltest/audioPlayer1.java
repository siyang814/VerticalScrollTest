package com.example.verticalscrolltest;

import java.util.HashMap;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


	public class audioPlayer1{
		
		private boolean play = false;
		private SoundPool sp;  
	    private HashMap<Integer,Integer> spMap;  
		
		private Context mContext;
		
		public audioPlayer1 ( Context c )
		{
			mContext = c;
			 sp=new SoundPool(2,AudioManager.STREAM_MUSIC,0);  
		        spMap = new HashMap<Integer,Integer>();  
		        spMap.put(1, sp.load(mContext, R.raw.alarm, 1));  
		}
		int num = 1;
	synchronized public void playSounds(){  
		if ( play )
		{
			return ;
		}
		play = true;
        new soundd().start();
    }  
	
	private void played ()
	{
		AudioManager am = (AudioManager)mContext.getSystemService(mContext.AUDIO_SERVICE);  
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);  
        float volumnRatio = audioCurrentVolumn/audioMaxVolumn;  
          
        num = sp.play(spMap.get(1), volumnRatio, volumnRatio, 1, -1, 1);
	}
	
	public void PauseSounds ()
	{
		if ( !play ) return;
		sp.pause(num);  
		play = false;
	}
	
	

	class  soundd extends Thread
	{
		@Override
		public void run() {
			super.run();
			try
			{
				Thread.sleep(500);
			} catch (Exception e)
			{
				// TODO: handle exception
			}
			played();
			
		}
	}
	
	
}