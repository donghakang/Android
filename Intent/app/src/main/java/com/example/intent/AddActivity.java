package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    EditText etKorean;
    EditText etEnglish;
    Button btnClick;

//    TextView tvKorean;
//    TextView tvEnglish;

    ListView listVocab;
    ArrayList<String> eng = new ArrayList<>();
    ArrayList<String> kor = new ArrayList<>();
    ArrayList<String> vocab = new ArrayList<>();

    private void setVocab() {
//        String english = "";
//        String korean = "";
//        for (int i = 0; i < Storage.vocaArr.size(); i++) {
//            Voca v = Storage.vocaArr.get(i);
//            english += v.eng + "\n";
//            korean += v.kor + "\n";
//
//        }


        VocabList adapter = new VocabList(this, this.eng, this.kor);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.eng);
        listVocab.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listVocab.setOnItemClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if (Storage.vocaArr.size() > 0) {
            for (int i = 0; i < Storage.vocaArr.size(); i++) {
                String en = Storage.vocaArr.get(i).eng;
                String ko = Storage.vocaArr.get(i).kor;
                eng.add(en);
                kor.add(ko);
                vocab.add(en + "\n" + ko);
            }
        }


        etEnglish = findViewById(R.id.et_english);
        etKorean  = findViewById(R.id.et_korean);

//        tvEnglish = findViewById(R.id.tv_english);
//        tvKorean = findViewById(R.id.tv_korean);

        btnClick = findViewById(R.id.btn_click);
        btnClick.setOnClickListener(this);

        listVocab = findViewById(R.id.list_vocab);
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

                this.eng.add(eng);
                this.kor.add(kor);
                this.vocab.add(eng + "\n" + kor);

                Voca voca = new Voca (eng, kor);
                Storage.vocaArr.add(voca);


                setVocab();

                Toast.makeText(this, "등록이 완료되었습니다", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(this, "입력하세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, this.kor.get(position), Toast.LENGTH_SHORT).show();
    }
}