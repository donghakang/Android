package com.example.webtoon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String url = "https://m.comic.naver.com/webtoon/detail.nhn?titleId=651673&weekday=sat";
    WebView webView;
    EditText episode;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        episode = findViewById(R.id.et_episode);
        webView = findViewById(R.id.web_view);

        webView.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String s = episode.getText().toString();
        String new_url = url + "&no=" + s;

        episode.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(new_url);

        Log.d("Website URL", new_url);
    }
}