package com.example.verticalscrolltest;

import java.util.HashMap;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SPActivity extends Activity {  
    private Button playBtn;  
    private Button pauseBtn;  
    private SoundPool sp;  
    private HashMap<Integer,Integer> spMap;  
    
    audioPlayer1 ap1;
    
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        
        ap1 = new audioPlayer1(this);
        ap1.playSounds();
        setContentView(R.layout.activity_sp);  
        playBtn=(Button)findViewById(R.id.button1);  
        pauseBtn=(Button)findViewById(R.id.button2);  
        sp=new SoundPool(2,AudioManager.STREAM_MUSIC,0);  
        spMap = new HashMap<Integer,Integer>();  
        spMap.put(1, sp.load(this, R.raw.alarm, 1));  
        playBtn.setOnClickListener(new OnClickListener() {          
            public void onClick(View v) {  
                // TODO Auto-generated method stub   
//                playSounds(1,-1);  
//            	playSounds();
            	ap1.playSounds();
            }  
        });  
        pauseBtn.setOnClickListener(new OnClickListener() {  
              
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub   
//                sp.pause(spMap.get(1)); 
            	ap1.PauseSounds();
            }  
        });          
    }  
  
    int num = 1;
    public void playSounds(int sound, int number){  
        AudioManager am = (AudioManager)this.getSystemService(this.AUDIO_SERVICE);  
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);  
        float volumnRatio = audioCurrentVolumn/audioMaxVolumn;  
          
        sp.play(spMap.get(sound), volumnRatio, volumnRatio, 1, number, 1);
    }  
    public void playSounds(){  
        AudioManager am = (AudioManager)this.getSystemService(this.AUDIO_SERVICE);  
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);  
        float volumnRatio = audioCurrentVolumn/audioMaxVolumn;  
          
        sp.play(spMap.get(1), volumnRatio, volumnRatio, 1, -1, 1);
    }  
}  

