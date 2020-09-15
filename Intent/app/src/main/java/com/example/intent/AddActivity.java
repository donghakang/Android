package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etKorean;
    EditText etEnglish;
    Button btnClick;

    TextView tvKorean;
    TextView tvEnglish;

    private void setVocab() {
        String english = "";
        String korean = "";
        for (int i = 0; i < Storage.vocaArr.size(); i++) {
            Voca v = Storage.vocaArr.get(i);
            english += v.eng + "\n";
            korean += v.kor + "\n";
        }

        tvKorean.setText(korean);
        tvEnglish.setText(english);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        etEnglish = findViewById(R.id.et_english);
        etKorean  = findViewById(R.id.et_korean);

        tvEnglish = findViewById(R.id.tv_english);
        tvKorean = findViewById(R.id.tv_korean);

        btnClick = findViewById(R.id.btn_click);

        btnClick.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVocab();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_click) {
            try {
                String kor = etKorean.getText().toString();
                String eng = etEnglish.getText().toString();

                Voca voca = new Voca (eng, kor);
                Storage.vocaArr.add(voca);

                setVocab();

                Toast.makeText(this, "등록이 완료되었습니다", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(this, "입력하세요", Toast.LENGTH_SHORT).show();
            }

        }



    }
}