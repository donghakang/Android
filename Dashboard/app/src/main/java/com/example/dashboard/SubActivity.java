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

public class SubActivity extends AppCompatActivity implements View.OnClickListener{

    String id = "";
    int idx = 0;
    EditText etTitle, etContent;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        id = getIntent().getStringExtra("name");

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (etTitle.getText().toString().equals("")) {
            Toast.makeText(this, "제목을 작성하세요", Toast.LENGTH_SHORT).show();
        } else if (etContent.getText().toString().equals("")) {
            Toast.makeText(this, "내용을 작성하세요", Toast.LENGTH_SHORT).show();
        } else {
            request();
        }




    }

    private void request() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_board_insert.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("idx", idx + "");
                params.put("title", etTitle.getText().toString());
                params.put("content", etContent.getText().toString());
                params.put("token", id);


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
            try {
                JSONObject j = new JSONObject(response);
                if (j.optString("result").equals("OK")) {
                    Toast.makeText(SubActivity.this, "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("누구냐 넌", response);
                    idx += 1;
                } else {
                    Toast.makeText(SubActivity.this, "등록할 수 없습니다..", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SubActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };


}