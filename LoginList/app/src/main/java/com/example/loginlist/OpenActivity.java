package com.example.loginlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OpenActivity extends AppCompatActivity {

    String title;
    String content;
    String writer;

    TextView detailTitle, detailContent, detailWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        writer = getIntent().getStringExtra("writer");

        detailTitle = findViewById(R.id.tv_detail_title);
        detailContent = findViewById(R.id.tv_detail_content);
        detailWriter = findViewById(R.id.tv_detail_writer);

        setup();

    }

    private void setup() {
        detailTitle.setText(title);
        detailContent.setText(content);
        detailWriter.setText(writer);
    }
}