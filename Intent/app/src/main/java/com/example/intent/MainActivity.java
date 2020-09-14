package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnVocab;
    Button btnGame;
    Button btnExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVocab = findViewById(R.id.btn_vocab);
        btnGame  = findViewById(R.id.btn_game);
        btnExit  = findViewById(R.id.btn_exit);

        btnVocab.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_vocab:
                intent = new Intent(this, com.example.intent.AddActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_game:
                intent = new Intent(this, com.example.intent.GameActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_exit:
                finish();
                System.exit(0);
                break;
        }
    }
}