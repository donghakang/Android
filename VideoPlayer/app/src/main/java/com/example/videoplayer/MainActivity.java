package com.example.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    VideoView videoView;
    boolean isPrepared = false;

    SeekBar duration;

    int i = 0;
    ImageButton btnPlay, btnForward, btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.vv);
        btnPlay = findViewById(R.id.btn_play);
        btnForward = findViewById(R.id.btn_forward);
        btnStop = findViewById(R.id.btn_stop);
        duration = findViewById(R.id.duration);


        Uri uri = Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        videoView.setVideoURI(uri);
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 재생 준비가 완료 되었을때
                isPrepared = true;

                duration.setMax(videoView.getDuration());
            }
        });


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 재생이 끝났을 때
            }
        });


        duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                videoView.seekTo(seekBar.getProgress());
            }
        });

        /**
         * MediaController controller = new MediaController(this);
         * vv.setMediaController(controller);
         *
         */

        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnForward.setOnClickListener(this);

        handler.sendEmptyMessage(0);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            handler.sendEmptyMessageDelayed(0, 10);
            duration.setProgress(videoView.getCurrentPosition());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (isPrepared) {
                    if (videoView.isPlaying()) {
                        videoView.pause();
                        btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    } else {
                        videoView.start();
                        btnPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                    }
                }
                break;
            case R.id.btn_forward:
                int currentTime = videoView.getCurrentPosition();
                Log.d(videoView.getDuration() + "", currentTime + "");
                videoView.seekTo(currentTime + 3000);
                Toast.makeText(this, "3초 빨리감기", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_stop:
//                videoView.stopPlayback();
                videoView.seekTo(0);
                btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                break;
        }
    }
}