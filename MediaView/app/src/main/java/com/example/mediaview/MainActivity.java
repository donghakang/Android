package com.example.mediaview;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;
    boolean isPrepared = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.vv);

        Uri uri = Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        videoView.setVideoURI(uri);
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 재생 준비가 완료 되었을때
                isPrepared = true;
            }
        });


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 재생이 끝났을 때
            }
        });

        videoView.getDuration();            // 총 재생 시간 (ms)
        videoView.getCurrentPosition();     // 현재 재생부분 가져옴 ms
        videoView.seekTo(1000);      // 해댕 하는 재생 위치로 이동
        videoView.pause();                  // 일시 정지
        videoView.stopPlayback();           // 완전 스탑

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPrepared) {
                    videoView.start();
                }

            }
        });
    }
}