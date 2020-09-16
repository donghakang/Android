package com.example.intent.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.intent.R;
import com.example.intent.control.Storage;
import com.example.intent.control.Voca;
import com.example.intent.control.VocabList;

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

    int pos = -1;

    private void setVocab() {
//        String english = "";
//        String korean = "";

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
            if (pos == -1) {
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
            } else {
                try {
                    String kor = etKorean.getText().toString();
                    String eng = etEnglish.getText().toString();

                    this.eng.set(pos, eng);
                    this.kor.set(pos, kor);

                    Voca voca = new Voca (eng, kor);
                    Storage.vocaArr.set(pos, voca);


                    setVocab();

                    pos = -1;

                    Toast.makeText(this, "등록이 완료되었습니다", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Toast.makeText(this, "입력하세요", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("잠시만요!");
        builder.setMessage("이 단어를 수정/삭제 하시겠습니까?");
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Storage.vocaArr.remove(position);
                eng.remove(position);
                kor.remove(position);
                setVocab();
            }
        });

        builder.setNegativeButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etEnglish.setText(eng.get(position));
                etKorean.setText(kor.get(position));
                pos = position;
            }
        });

        builder.create().show();
    }
}