package com.example.intent.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intent.R;
import com.example.intent.control.Storage;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvWord;
    TextView tvScore;
    TextView tvTime;
    Button btnChange;
    EditText etWord;
    Button btnSubmit;

    boolean engMode = true;
    int[] orderArr = new int[10];
    int pos = 0;
    int score = 0;
    int totalVocab;
    int time = 0;



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

    private void reset() {
        time = 0;
        pos = 0;
        totalVocab = Storage.vocaArr.size();
        updateOrder(totalVocab);                    // pick random 10 words (or less)
        if (totalVocab > 10) totalVocab = 10;
        updateQuiz();                               // first word becomes the quiz
        tvScore.setText("점수 : " + score + " / " + totalVocab);  // quiz pop up
    }


    final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (time == 10) {
                // 만약 시간 10초가 넘어가면, 다음 문제로 전환
                Toast.makeText(GameActivity.this, "오답입니다", Toast.LENGTH_SHORT).show();
                time = 0;
                pos += 1;
                updateQuiz();
            }

            time += 1;
            tvTime.setText("시간 : " + time + " sec");
            handler.sendEmptyMessageDelayed(0, 1000);

        }
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvWord = findViewById(R.id.tv_word);
        tvScore = findViewById(R.id.tv_score);
        tvTime = findViewById(R.id.tv_time);
        btnChange = findViewById(R.id.btn_change);
        btnSubmit = findViewById(R.id.btn_submit);
        etWord = findViewById(R.id.et_word);

        btnChange.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        reset();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            if (engMode) {
                String currentKor = Storage.vocaArr.get(orderArr[pos]).kor;
                if (etWord.getText().toString().equals(currentKor)) {
                    // 정답을 맞추다
                    score += 1;
                    Toast.makeText(this, "정답입니다", Toast.LENGTH_SHORT).show();
                    time = 0;
                    handler.removeMessages(0);
                    handler.sendEmptyMessage(0);
                } else {
                    Toast.makeText(this, "오답입니다", Toast.LENGTH_SHORT).show();

                }
                tvScore.setText("점수 : " + score + " / " + totalVocab);

            } else {
                String currentEng = Storage.vocaArr.get(orderArr[pos]).eng;
                if (etWord.getText().toString().equals(currentEng)) {
                    // 정답을 맞추다
                    score += 1;
                    Toast.makeText(this, "정답입니다", Toast.LENGTH_SHORT).show();
                    time = 0;
                    handler.removeMessages(0);
                    handler.sendEmptyMessage(0);
                } else {
                    Toast.makeText(this, "오답입니다", Toast.LENGTH_SHORT).show();
                }
                tvScore.setText("점수 : " + score + " / " + totalVocab);
            }
            pos += 1;



            if (pos > totalVocab) {
                Log.d("MESSAGE", pos + "     " + totalVocab);
                // 게임 종료
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "최종 스코어 " + score + " / " + totalVocab, Toast.LENGTH_LONG).show();
                finish();
            } else {
                updateQuiz();
            }



        } else if (v.getId() == R.id.btn_change) {
            engMode = !engMode;
//            updateQuiz();
            handler.removeMessages(0);
            handler.sendEmptyMessage(0);
            reset();
        }
    }
}