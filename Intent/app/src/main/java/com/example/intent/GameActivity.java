package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvWord;
    TextView tvScore;
    Button btnChange;
    EditText etWord;
    Button btnSubmit;

    boolean engMode = true;
    int[] orderArr = new int[10];
    int pos = 0;


    private void updateOrder(int count) {
        /**
         * 이 메소드는 단어가 나올때 중복되는 것을 예방하기 위한 순서를 찾기위한 메소드입니다.
          */
        if (count > 10) {
            for (int i = 0; i < 10; i++) {
                orderArr[i] = new Random().nextInt(count);
                for (int j = 0; j < i; j ++ ) {
                    if (orderArr[i] == orderArr[j]) {
                        i --;
                    }
                }
            }
        } else {
            for (int i = 0; i < count; i++) {
                orderArr[i] = new Random().nextInt(count);
                for (int j = 0; j < i; j ++ ) {
                    if (orderArr[i] == orderArr[j]) {
                        i --;
                    }
                }
            }
        }
    }

    private void updateQuiz() {
        if (engMode) {
            tvWord.setText(Storage.vocaArr.get(orderArr[pos]).eng);
        } else {
            tvWord.setText(Storage.vocaArr.get(orderArr[pos]).kor);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvWord = findViewById(R.id.tv_word);
        tvScore = findViewById(R.id.tv_score);
        btnChange = findViewById(R.id.btn_change);
        btnSubmit = findViewById(R.id.btn_submit);
        etWord = findViewById(R.id.et_word);

        btnChange.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int totalVocab = Storage.vocaArr.size();
        tvScore.setText(tvScore.getText().toString() + "  / " + totalVocab);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            pos += 1;
        } else if (v.getId() == R.id.btn_change) {
            engMode = !engMode;
            updateQuiz();

        }
    }
}