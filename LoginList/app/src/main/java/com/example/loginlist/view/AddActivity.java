package com.example.loginlist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.example.loginlist.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etTitle, etContent;
    Button btnSubmit;

    private String token = "";
    int idx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        token = getIntent().getStringExtra("token");
        idx = getIntent().getIntExtra("idx", 0);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if (title.equals("")) Toast.makeText(this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
        else if (content.equals("")) Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
        else {
            // request
            request(token, idx, title, content);
        }
    }


    private void request(final String token, final int idx, final String title, final String content) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_board_insert.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx", idx + "");
                params.put("token", token);
                params.put("title", title);
                params.put("content", content);

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
                    // 입력 성공
                    Toast.makeText(AddActivity.this, "등록 되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddActivity.this, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(AddActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };

}