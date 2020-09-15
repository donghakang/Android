package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnVocab;
    Button btnGame;
    Button btnExit;
    Button btnHangman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVocab = findViewById(R.id.btn_vocab);
        btnGame  = findViewById(R.id.btn_game);
        btnExit  = findViewById(R.id.btn_exit);
        btnHangman = findViewById(R.id.btn_hangman);

        btnVocab.setOnClickListener(this);
        btnGame.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnHangman.setOnClickListener(this);
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
                if (Storage.vocaArr.size() == 0) {
                    Toast.makeText(this, "먼저 단어장에 단어를 입력하세요", Toast.LENGTH_LONG).show();
                } else {
                    intent = new Intent(this, com.example.intent.GameActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_hangman:
                if (Storage.vocaArr.size() == 0) {
                    Toast.makeText(this, "먼저 단어장에 단어를 입력하세요", Toast.LENGTH_LONG).show();
                } else {
                    intent = new Intent(this, com.example.intent.HangmanActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_exit:
                finish();
                System.exit(0);
                break;
        }
    }
}