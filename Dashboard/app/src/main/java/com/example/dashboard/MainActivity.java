package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnCheckId, btnRegister;
    EditText etId, etPw;

    boolean registable = false;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCheckId = findViewById(R.id.btn_checkid);
        btnRegister = findViewById(R.id.btn_register);

        etId = findViewById(R.id.et_id);
        etPw = findViewById(R.id.et_pw);

        btnCheckId.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_checkid:
                id = etId.getText().toString();
                if (id.equals("")) {
                    Toast.makeText(this, "Insert Id", Toast.LENGTH_SHORT);
                } else {
                    requestIdCheck(id);
                }
                break;
            case R.id.btn_register:
                String pw = etPw.getText().toString();
                if (pw.equals("")) {
                    Toast.makeText(this, "Insert Pw", Toast.LENGTH_SHORT);
                } else {
                    request(pw);
                }
                break;
        }
    }


    ///- 회원 가입 완료 여부
    private void request(final String pw) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_join.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListenerRegister, errorListenerRegister) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", id);
                params.put("pass", pw);

                return params;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq);
    }


    Response.Listener<String> successListenerRegister = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // 통신을 성공 할 시
            try {
                JSONObject j = new JSONObject(response);
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

    Response.ErrorListener errorListenerRegister = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시
            Toast.makeText(MainActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };


    ///- id 중복확인
    private void requestIdCheck(final String name) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_chk.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);

                return params;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq);

    }

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // 통신을 성공 할 시
//            Log.d("dddddd", response);
            try {
                JSONObject j = new JSONObject(response);
                Log.d("dddddd", j.optString("result"));
                if (j.optString("result").equals("OK")) {
                    registable = true;
                    Toast.makeText(MainActivity.this, "사용 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "이미 가입 입니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시
            Toast.makeText(MainActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };




}