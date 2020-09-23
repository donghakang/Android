package com.example.vocabextended;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etKorean;
    EditText etEnglish;
    Button btnSubmit;

    JSONArray eng;
    JSONArray kor;

    ArrayList<String> engArr;
    ArrayList<String> korArr;

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


        loadData();
        setupListView();

        showData();
    }

    @Override
    public void onClick(View v) {
        String engWord = etEnglish.getText().toString();
        String korWord = etKorean.getText().toString();

        if (engWord.equals("") || korWord.equals("")) {
            Toast.makeText(this, "단어를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            // save data
            engArr.add(engWord);
            korArr.add(korWord);

            eng.put(engWord);
            kor.put(korWord);

            saveData();

            // show data
            showData();
        }


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

    private void showData() {
        adapter = new CustomList(this, engArr, korArr);
        vocabList.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void setupListView() {
        korArr = new ArrayList<String>();
        engArr = new ArrayList<String>();
        for (int i = 0; i < kor.length(); i ++) {
            Log.d(kor.optString(i), eng.optString(i));
            korArr.add(kor.optString(i));
            engArr.add(eng.optString(i));
        }


    }
}