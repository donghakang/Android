package com.example.click;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvTitle;
    Button btnSubmit;
    EditText etInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvTitle = findViewById(R.id.tv_title);
        btnSubmit = findViewById(R.id.btn_submit);
        etInsert = findViewById(R.id.et_insert);

        btnSubmit.setOnClickListener(this);
//
//        int randomNumber = new Random().nextInt(100);
//
//        tvTitle.setTextColor(Color.parseColor("#00ffff"));
//        tvTitle.setText(String.valueOf(randomNumber));
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_submit) {
            // 버튼이 두개일시, getId를 사용해서 버튼 을 나눌수 잇다
            
        }

        String str = etInsert.getText().toString();

        int randomColor  = new Random().nextInt(3);
        if (randomColor == 0) {
            tvTitle.setTextColor(Color.RED);
        } else if (randomColor == 1) {
            tvTitle.setTextColor(Color.BLUE);
        } else {
            tvTitle.setTextColor(Color.GREEN);
        }
        tvTitle.setText(str);

        Toast.makeText(this, "입력 완료", Toast.LENGTH_SHORT).show();
    }
}