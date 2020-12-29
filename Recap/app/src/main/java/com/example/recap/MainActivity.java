package com.example.recap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_name;
    EditText et_age;
    EditText et_hobby;

    Button btnSubmit;

    String name, age, hobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_hobby = findViewById(R.id.et_hobby);


        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {

            name = et_name.getText().toString();
            hobby = et_hobby.getText().toString();
            age = et_age.getText().toString();
            // Request data from Php File.
            RequestQueue stringRequest = Volley.newRequestQueue(this);
            String url = "http://172.30.1.47:8098/app/insertProc.do";

            StringRequest myReq = new StringRequest(Request.Method.POST, url,
                    successListener, errorListener) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("age", age);
                    params.put("hobby", hobby);
                    Log.d("ddd", params.toString());
                    return params;
                }
            };

            myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
            stringRequest.add(myReq);
        }
    }

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // 통신을 성공 할 시
            Log.d("dddd", response);
            try {
                JSONObject j = new JSONObject(response);
                Log.d("ddddd", j.optString("name"));
                if (j.optString("result").equals("OK")) {
                    Toast.makeText(MainActivity.this, "회원가입이 완료 되었습니다..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "회원가입 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    // when obtaining data is unsuccessful.
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시
            Toast.makeText(MainActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };
}