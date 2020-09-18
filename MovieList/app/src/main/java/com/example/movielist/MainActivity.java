package com.example.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*
    HTTP 통신을 이용하여 JSON 데이터를 가져온다.
    가져올때는 Volley 라는 Library를 사용한다.
     */


    TextView tv;
    Button btnClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv_title);
        btnClick = findViewById(R.id.btn_click);

        btnClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        request();
    }

    private void request() {
        //progressBar.setVisibility(View.VISIBLE);

        /*
        9.0 부터, https 만 호환이 된다. 그래서
        AndroidManifest.xml을 다시고쳐야한다.

         */
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=22d5c15941cb3125ca046e747b13551c&targetDt=20190602";


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest myReq = new StringRequest(Request.Method.GET, url, successListener, errorListener);

        requestQueue.add(myReq);
    }

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // 통신을 성공 할 시
            tv.setText(response);
            //progressBar.setVisibility(View.INVISIBLE);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시


        }
    };
}