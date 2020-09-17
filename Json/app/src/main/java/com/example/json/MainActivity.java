package com.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

        // JSONObject
        String profile = "{name : 강동하, age : 1995, address : 서울시 광진구 }";

        try {
            JSONObject obj = new JSONObject(profile);
            Log.d("NAME PRINT", "Name: " + obj.getString("name"));
            Log.d("AGE  PRINT", "Age : " + obj.getString("age"));
            Log.d("ADDR PRINT", "Addr: " + obj.getString("address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // JSONArray
        String arr1 = "[가, 나, 다, 라]";
        String arr2 = "[ {name : 강동하, age : 1995, address : 서울시 광진구 }" +
                "{name : 강동하, age : 1995, address : 서울시 광진구 }" +
                "{name : 강동하, age : 1995, address : 서울시 광진구 } ]";
        String arr3 = "[ [가, 나, 다], [라, 마, 바], [사, 아, 자] ]";

        try {
            JSONArray jrr1 = new JSONArray(arr1);
            for (int i = 0; i < jrr1.length(); i ++) {
                Log.d("PRINT JRR1", "jrr1: " + jrr1.getString(i));
            }

            JSONArray jrr2 = new JSONArray(arr2);
            for (int j = 0; j < jrr2.length(); j ++) {
                JSONObject temp = jrr2.getJSONObject(j);
                Log.d("PRINT JRR2", "jrr2: " + temp.getString("name"));
            }

            JSONArray jrr3 = new JSONArray(arr3);
            for (int j = 0; j < jrr3.length(); j ++) {
                JSONArray temp2 = jrr3.getJSONArray(j);
                Log.d("PRINT JRR2", "jrr2: " + temp2.getString(0));     // 맨 첫번째 원소만 아웃
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}