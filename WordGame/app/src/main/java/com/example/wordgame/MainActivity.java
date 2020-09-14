package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMove.findViewById(R.id.btn_move);
        btnMove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("PRESS", "press button");
    }
}