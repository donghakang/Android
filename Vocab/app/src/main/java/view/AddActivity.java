package view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vocab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import control.JSON;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button btnInsert;
    ListView vocabList;


    private void init() {
        ArrayList<String> engArrayList = new ArrayList<>();
        ArrayList<String> korArrayList = new ArrayList<>();
        ArrayList<Integer> levelArrayList = new ArrayList<>();
        for (int i = 0; i < JSON.vocab.length(); i ++) {
            try {
                JSONObject data = (JSONObject) JSON.vocab.get(i);
                engArrayList.add(data.getString("vocabEng"));
                korArrayList.add(data.getString("vocabKor"));
                levelArrayList.add(data.getInt("vocabLevel"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        VocabList adapter = new VocabList(this, engArrayList, korArrayList, levelArrayList);
        vocabList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        vocabList.setOnItemClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        vocabList = findViewById(R.id.vocab_list);
        btnInsert = findViewById(R.id.btn_insert);

        btnInsert.setOnClickListener(this);
        init();
    }

    @Override
    public void onClick(View v) {
        // Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("단어를 추가합니다");
        builder.setMessage("영어, 한글 뜻, 그리고 난이도를 (0-4) 설정해주세요");
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add, null);
        builder.setView(customLayout);


        builder.setPositiveButton("등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText etEnglish = customLayout.findViewById(R.id.et_english);
                EditText etKorean = customLayout.findViewById(R.id.et_korean);
                EditText etLevel = customLayout.findViewById(R.id.et_level);

                String vocabEng = etEnglish.getText().toString();
                String vocabKor = etKorean.getText().toString();
                String vocabLevel = etLevel.getText().toString();

                if (vocabEng.equals("") || vocabKor.equals("") ||vocabLevel.equals("")) {
                    Toast.makeText(AddActivity.this, "영어, 한국어, 난이도가 잘 맞게 들어갔는지 확인해주세요", Toast.LENGTH_LONG).show();
                } else {
                    // Using JSON to save the data.
                    JSONObject vocabulary = new JSONObject();
                    try {
                        vocabulary.put("vocabEng", vocabEng);
                        vocabulary.put("vocabKor", vocabKor);
                        vocabulary.put("vocabLevel", Integer.parseInt(vocabLevel));
                        JSON.vocab.put(vocabulary);
                        init();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("잠시만요!");
        builder.setMessage("이 단어를 수정/삭제 하시겠습니까?");
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JSON.vocab.remove(position);
                init();
            }
        });

        builder.setNegativeButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setTitle("단어를 수정합니다.");
                builder.setMessage("영어, 한글 뜻, 그리고 난이도를 (0-4) 설정해주세요");
                final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add, null);
                builder.setView(customLayout);
            }
        });

        builder.create().show();
    }




}