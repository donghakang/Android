package com.example.vocabsql;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etKorean;
    EditText etEnglish;
    Button btnSubmit;

    SQLiteDatabase db;

    ArrayList<String> eng = new ArrayList<>();
    ArrayList<String> kor = new ArrayList<>();

    CustomList adapter;
    ListView vocabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etKorean = findViewById(R.id.et_kor);
        etEnglish = findViewById(R.id.et_eng);
        btnSubmit = findViewById(R.id.btn_submit);
        vocabList = findViewById(R.id.custom_list);
        btnSubmit.setOnClickListener(this);


        db = openOrCreateDatabase("sqlist_vocab.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS vocab("
                + "_idx INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "eng TEXT, "
                + "kor TEXT " + ");");

        loadVocab();
        showVocab();
    }

    @Override
    public void onClick(View v) {
        String engWord = etEnglish.getText().toString();
        String korWord = etKorean.getText().toString();

        if (engWord.equals("") || korWord.equals("")) {
            Toast.makeText(this, "단어를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            // save data
            eng.add(engWord);
            kor.add(korWord);

            addVocabSQL(korWord, engWord);

            // show data
            showVocab();
        }


    }

    private void loadVocab() {
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

    private void showVocab() {
        adapter = new CustomList(this, eng, kor);
        vocabList.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void addVocabSQL(String kor, String eng) {
        String data = "INSERT INTO vocab (eng, kor) VALUES ('" + eng + "', '"+ kor +"')";
        db.execSQL(data);
    }

}