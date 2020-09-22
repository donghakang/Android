package com.example.savedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView showTv;
    EditText inputEt;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showTv = findViewById(R.id.tv_load);
        inputEt = findViewById(R.id.input_et);
        btnSave = findViewById(R.id.btn_save);

        SharedPreferences pref = getSharedPreferences("mode", MODE_PRIVATE);
        String getStr = pref.getString("str", "printed sentence if there is no input");
        int getInt = pref.getInt("age", -1);
        showTv.setText(getStr + ", " + getInt);

        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String str = inputEt.getText().toString();

        // 저장하기
        SharedPreferences pref = getSharedPreferences("mode", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("str", str);
        editor.putInt("age", 1000);
        editor.commit();

        // 불러오기
        String getStr = pref.getString("str", "printed sentence if there is no input");
        int getInt = pref.getInt("age", -1);

        showTv.setText(getStr + ", " + getInt);

    }
}