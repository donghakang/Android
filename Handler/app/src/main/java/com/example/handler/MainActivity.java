package com.example.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    - 안드로이드에서 UI에 변화를 줄 수 있는 것은 메인 쓰레드 뿐이다
    - 그 외에 쓰레드 에ㅓㅅ는 UI에 변화를 주기 위해서는 핸들러를 통해 UI에 변화를 주어야한다.

    -
     */

    TextView tvLabel;
    Button btnClick;
    Button btnCancel;
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLabel = findViewById(R.id.tv_label);
        btnClick = findViewById(R.id.btn_click);
        btnCancel = findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(this);
        btnClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*
        MyThread th = new MyThread();
        th.start();
         */

        if (v.getId() == R.id.btn_click) {
            handler.sendEmptyMessageDelayed(0, 1000);
        } else if (v.getId() == R.id.btn_cancel) {
            handler.removeMessages(0);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            value++;
            tvLabel.setText("현재: "+value);
            if(value < 10){
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };


    class MyThread extends Thread {
        @Override
        public void run() {
            value = 0;
            for (int i = 0; i < 10; i++) {
                value ++;

                /**
                 *  만약 tvLabel.setText(value)를 넣으면 에러가 뜬다
                 *  안드로이드안에서는 기존 Thread가 돌아가기 때문에,
                 *  자기가 만든 Thread에 시각적인 UI를 변경할 수 없도록 막기 때문이다.
                 *  그래서 핸들러를 사용한다.
                 */
                handler.sendEmptyMessage(0);
                // handler.sendEmptyMessageDelayed(0, 1000);     1초뒤에 실행

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}