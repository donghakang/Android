package com.example.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    boolean isReady = false;
    MediaPlayer mp;
    ImageButton btnPlay;
    ImageButton btnStop;
    SeekBar seekBar;
    TextView currentTime, totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);
        seekBar = findViewById(R.id.seekbar);
        currentTime = findViewById(R.id.current_time);
        totalTime = findViewById(R.id.total_time);

        mp = new MediaPlayer();

        new MyThread().start();

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isReady = true;
                btnPlay.setEnabled(true);
                btnStop.setEnabled(true);
                seekBar.setVisibility(View.VISIBLE);

                seekBar.setProgress(0);
                seekBar.setMax(mp.getDuration());
                totalTime.setText(calculateTime(mp.getDuration()));
            }
        });
        handler.sendEmptyMessage(0);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // When Progress changes
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // when tracking touch starts
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // when tracking touch ends -> change the position of the video.
                mp.seekTo(seekBar.getProgress());
            }
        });
    }

    ///- current position
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            seekBar.setProgress(mp.getCurrentPosition());
            updateTime(mp.getCurrentPosition());
            handler.sendEmptyMessageDelayed(0, 100);
        }
    };


    ///- waiting until the Media Player is set up
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                mp.setDataSource("https://ccrma.stanford.edu/~jos/mp3/harpsi-cs.mp3");
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
        mp = null;
    }

    public void playClick(View v){
        if(isReady){
            if (mp.isPlaying()) {
                mp.pause();
                btnPlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
            } else {
                mp.start();
                btnPlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
            }

        }
    }

    public void stopClick(View v) {
        if(isReady) {
            mp.pause();
            mp.seekTo(0);
            btnPlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
        }
    }


    /// - caculating ms to sec and min.
    public String calculateTime(int ms) {
        int min;
        int sec;
        int t = ms / 1000;
        min = t / 60;
        sec = t - (60 * min);

        if (sec < 10) {
            return min + ":0" + sec;
        } else {
            return min + ":" + sec;
        }

    }

    public void updateTime(int current) {
        currentTime.setText(calculateTime(current));
    }
}