package com.example.loginlist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etLoginId, etPassword;
    TextView tvLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginId = findViewById(R.id.et_loginid);
        etPassword = findViewById(R.id.et_password);
        tvLogin = findViewById(R.id.btn_login);

        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String id = etLoginId.getText().toString();
        String pw = etPassword.getText().toString();
        if (id.equals("")) {
            Toast.makeText(LoginActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
        } else if (pw.equals("")) {
            Toast.makeText(LoginActivity.this, "암호를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            request(id, pw);
        }
    }

    ///- id 중복확인
    private void request(final String id, final String pw) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_login.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener) {
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

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject j = new JSONObject(response);
                if (j.optString("result").equals("OK")) {
                    // 로그인 성공
                    String token = j.optString("token");
                    Intent intent = new Intent(LoginActivity.this, com.example.loginlist.DashboardActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    finish();
                } else {
                    String msg = j.optString("error_code");
                    if (msg.equals("1000")) Toast.makeText(LoginActivity.this, "비밀번호를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                    else if (msg.equals("1005")) Toast.makeText(LoginActivity.this, "아이디를 확인하세요", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(LoginActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };


/*  /// - 만약 intent에 조건을 주고 싶을때.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            // 회원가입 성공
        } else {
            // 회원가입 실패
        }
    }

    startActivityForResult(new Intent(this, com.example.loginlist.LoginActivity.class), 1000);
 */
}


