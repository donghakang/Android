package com.example.vocabextended;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    ProgressBar progressTime;
    RadioButton radioKor;
    RadioButton radioEng;
    TextView tvQuestion;
    EditText etAnswer;
    Button btnClick;
    Button btnDetail;
    Button btnPop;
    Button btnStart;
    TextView tvScore;

    JSONArray eng;
    JSONArray kor;

    // game setting
    int vocabCount = 0;
    boolean isKor = false;
    boolean isGame = false;
    String currentAnswer;
    int time = 0;
    int score = 0;
    int totalScore = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        Log.e("ERERER", eng.optString(0));


        progressTime = findViewById(R.id.progress_time);
        radioKor = findViewById(R.id.radio_kor);
        radioEng = findViewById(R.id.radio_eng);
        tvQuestion = findViewById(R.id.tv_question);
        etAnswer = findViewById(R.id.et_answer);
        btnClick = findViewById(R.id.btn_click);
        btnDetail = findViewById(R.id.btn_detail);
        btnPop = findViewById(R.id.btn_pop);
        btnStart = findViewById(R.id.btn_start);
        tvScore = findViewById(R.id.tv_score);


        btnClick.setOnClickListener(this);
        btnDetail.setOnClickListener(this);
        btnPop.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        radioKor.setOnCheckedChangeListener(this);
        radioEng.setOnCheckedChangeListener(this);


        loadData();         // JSONArray 가 세팅.
        isKor = false;
        radioEng.setChecked(true);
        updateQuestion();
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("단어를 추가합니다 (단어 총 " + vocabCount + "개)");
        builder.setMessage("영어, 한글 뜻을 설정해주세요");

        LayoutInflater lnf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = lnf.inflate(R.layout.add_dialog, null, false);

        builder.setView(v);
        builder.setPositiveButton("등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText etEnglish = v.findViewById(R.id.et_english);
                EditText etKorean = v.findViewById(R.id.et_korean);

                String vocabEng = etEnglish.getText().toString();
                String vocabKor = etKorean.getText().toString();

                if (vocabEng.equals("") || vocabKor.equals("")) {
                    Toast.makeText(MainActivity.this, "영어, 한국어, 난이도가 잘 맞게 들어갔는지 확인해주세요", Toast.LENGTH_LONG).show();
                } else {
                    // Using JSON to save the data.
                    eng.put(vocabEng);
                    kor.put(vocabKor);

                    saveData();
                    updateQuestion();
                }
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();

    }


    protected void saveData() {
        // 저장하기
        SharedPreferences pref = getSharedPreferences("mode", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("kor", kor.toString());
        editor.putString("eng", eng.toString());

        editor.commit();
    }

    private void loadData() {
        SharedPreferences pref = getSharedPreferences("mode", MODE_PRIVATE);
        String getKor = pref.getString("kor", "[개, 돈]");
        String getEng = pref.getString("eng", "[Dog, Money]" );

        try {
            kor = new JSONArray(getKor);
            eng = new JSONArray(getEng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void updateQuestion() {
        etAnswer.setText("");
        vocabCount = kor.length();
        isKor = radioKor.isChecked();
        int pos = new Random().nextInt(vocabCount);
        if (isKor) {
            tvQuestion.setText(kor.optString(pos));
            currentAnswer = eng.optString(pos);
        } else {
            tvQuestion.setText(eng.optString(pos));
            currentAnswer = kor.optString(pos);
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (time < progressTime.getMax()){
                // 시간이 흐르고 있을때,
                time += 1;
                progressTime.setProgress(time);
            } else {
                // 시간이 초과 될 시.
                time = 0;
                totalScore += 1;
                setScore(score, totalScore);
                updateQuestion();
            }
            handler.sendEmptyMessageDelayed(0, 10);
        }
    };

    // Tick Tock 시간은 흐른다.
    private void gameMode() {
        isGame = !isGame;
        if (isGame) {
            btnStart.setText("STOP");
            handler.sendEmptyMessage(0);
        } else {
            btnStart.setText("START");
            handler.removeMessages(0);
            time = 0;
            score = 0;
            totalScore = 0;
            setScore(score, totalScore);
        }

    }

    private void setScore(int score, int totalScore) {
        tvScore.setText("점수: " + score + "/" + totalScore);
    }

    // 정답 유 무 확인
    private void answerQuestion() {
        if (isGame) {
            String ans = etAnswer.getText().toString();
            if (currentAnswer.equals(ans)) {
                // Correct
                time = 0;
                updateQuestion();
                score += 1;
                totalScore += 1;
                setScore(score, totalScore);
            } else {
                // Not Correct
                totalScore += 1;
                setScore(score, totalScore);
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                gameMode();
                break;
            case R.id.btn_click:
                answerQuestion();
                break;
            case R.id.btn_detail:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pop:
                showDialog();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        updateQuestion();
    }
}