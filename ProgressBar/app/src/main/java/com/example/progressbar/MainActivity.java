package com.example.progressbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressLoading;
    ProgressBar progressDownload;
    Button btnDownload;
    Button btnLoading;

    int t = 0;
    boolean isDownload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = findViewById(R.id.btn_download);
        btnLoading  = findViewById(R.id.btn_loading);

        progressDownload = findViewById(R.id.progress_download);
        progressLoading  = findViewById(R.id.progress_loading);

        btnDownload.setOnClickListener(this);
        btnLoading.setOnClickListener(this);

        progressDownload.setMax(500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                isDownload = true;

                Toast.makeText(this, "Downloading... ", Toast.LENGTH_SHORT).show();
                progressDownload.setVisibility(View.VISIBLE);
                btnLoading.setVisibility(View.INVISIBLE);
                btnDownload.setVisibility(View.INVISIBLE);
                handler.sendEmptyMessage(0);

                break;
            case R.id.btn_loading:
                isDownload = false;

                Toast.makeText(this, "Loading... ", Toast.LENGTH_SHORT).show();
                progressLoading.setVisibility(View.VISIBLE);
                btnLoading.setVisibility(View.INVISIBLE);
                btnDownload.setVisibility(View.INVISIBLE);
                handler.sendEmptyMessage(0);

                break;
            default:
                break;
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (!isDownload) {
                t += 1;
                handler.sendEmptyMessageDelayed(0, 1000);
                if (t > 5) {
                    progressLoading.setVisibility(View.INVISIBLE);
                    btnDownload.setVisibility(View.VISIBLE);
                    btnLoading.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Loading Complete", Toast.LENGTH_SHORT).show();
                    t = 0;
                    handler.removeMessages(0);
                }
            } else {
                t += 1;
                progressDownload.setProgress(t);
                handler.sendEmptyMessageDelayed(0, 10);
                if (t > 500) {
                    progressDownload.setVisibility(View.INVISIBLE);
                    btnDownload.setVisibility(View.VISIBLE);
                    btnLoading.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Donwload Complete", Toast.LENGTH_SHORT).show();
                    t = 0;
                    handler.removeMessages(0);

                }
            }

        }
    };
}