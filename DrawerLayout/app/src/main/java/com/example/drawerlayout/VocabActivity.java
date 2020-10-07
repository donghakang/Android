package com.example.drawerlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.Random;

public class VocabActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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

    static ArrayList<String> eng = new ArrayList<>();
    static ArrayList<String> kor = new ArrayList<>();

    SQLiteDatabase db;

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
        setContentView(R.layout.activity_vocab);

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

        initSQL();
        loadVocabSQL();
        isKor = false;

        updateQuestion();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eng.clear();
        kor.clear();

        db.close();
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
                    Toast.makeText(VocabActivity.this, "영어, 한국어, 난이도가 잘 맞게 들어갔는지 확인해주세요", Toast.LENGTH_LONG).show();
                } else {
                    // Using JSON to save the data.
                    eng.add(vocabEng);
                    kor.add(vocabKor);

                    addVocabSQL(vocabEng, vocabKor);
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



    private void initSQL() {
        db = openOrCreateDatabase("sqlist_vocab.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS vocab("
                + "_idx INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "eng TEXT, "
                + "kor TEXT " + ");");
    }


    private void addVocabSQL(String kor, String eng) {
        String data = "INSERT INTO vocab (eng, kor) VALUES ('" + eng + "', '"+ kor +"')";
        db.execSQL(data);
    }

    private void loadVocabSQL() {
        Cursor c = db.rawQuery("SELECT * FROM vocab", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {

            String engVocab = c.getString(1);
            String korVocab = c.getString(2);
            eng.add(engVocab);
            kor.add(korVocab);
            c.moveToNext();
        }
        c.close();
    }




    private void updateQuestion() {

        if (kor.size() > 0) {
            vocabCount = kor.size();
            int pos = new Random().nextInt(vocabCount) + 1;
            Log.d("XXXX", pos + "");
            String sql = "SELECT * FROM vocab" + " WHERE _idx="+pos;
            Cursor c = db.rawQuery(sql, null);
            c.moveToFirst();
            Log.d("PRINT MORE", c.getString(0) + "   " +  c.getString(1) + "    " + c.getString(2));
            if (isKor) {
                tvQuestion.setText(c.getString(2));
                currentAnswer = c.getString(1);
            } else {
                tvQuestion.setText(c.getString(1));
                currentAnswer = c.getString(2);
            }

            c.close();
        } else {
            tvQuestion.setText("");
            etAnswer.setText("");
        }
//        vocabCount = kor.size();
//
//        int pos = new Random().nextInt(vocabCount) + 1;
//        Cursor c = db.rawQuery("SELECT * FROM vocab" + " WHERE _idx="+pos, null);
//        c.moveToFirst();
//        Log.d(c.getString(1), c.getString(2));
//
//        etAnswer.setText("");
//        isKor = radioKor.isChecked();
//        if (isKor) {
//            tvQuestion.setText(kor.get(pos));
//            currentAnswer = eng.get(pos);
//        } else {
//            tvQuestion.setText(eng.get(pos));
//            currentAnswer = kor.get(pos);
//        }
//
//        c.close();
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
        if (!radioEng.isChecked()) {
            isKor = false;
        } else {
            isKor = true;
        }
        updateQuestion();
    }

/**  //  더 쉽게 만들기위해 모든 메소드 뒤에 dbSet을 불러주고, 다 지우고 다시 다 넣는 형식으로 진행이된다.
 *   // 이렇게 해도 무방한 이유는 SQLite는 생각보다 빠르기 때문이다.
 *
 private void dbSet() {
 SQLiteDatabase db = openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
 String sql = "DELETE FROM voca";
 db.execSQL(sql);

 for (int i = 0; i < Storage.vocaArr.size(); i ++) {
 String eng = Storage.vocaArr.get(i).eng;
 String kor = Storage.vocaArr.get(i).kor;
 sql = "INSERT INTO voca (eng, kor) VALUES ('" + eng + "', '" + kor + "')";
 db.execSQL(sql);
 }

 db.close();
 }
 **/
}