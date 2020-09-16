package com.example.handle2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img;
    TextView tvTime;

    int time = 0;
    boolean isImg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img1);
        tvTime = findViewById(R.id.tv_time);

        img.setOnClickListener(this);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            time += 1;
            tvTime.setText(time + " sec");
            handler.sendEmptyMessageDelayed(0, 1000);

            if (time > 5) {
//                time = 0;
//                isImg = false;
//                img.setImageResource(R.drawable.image);
                handler.removeMessages(0);
                time = 0;
                tvTime.setText(time+"");
                if (isImg) {
                    img.setImageResource(R.drawable.image);
                } else {
                    img.setImageResource(R.drawable.image2);
                }

                isImg = !isImg;
            }
        }
    };


    @Override
    public void onClick(View v) {
        time = 0;
        handler.removeMessages(0);
        handler.sendEmptyMessage(0);

        isImg = !isImg;

        if (isImg) {
            img.setImageResource(R.drawable.image);
        } else {
            img.setImageResource(R.drawable.image2);
        }
    }
}