package com.example.pastblockbuster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MovieActivity extends AppCompatActivity {

    TextView movieName;
    TextView audience;
    TextView cumulativeAudience;
    TextView director;
    TextView actor;
    TextView movieTime;

    ProgressBar loading;

    String movieCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        loading = findViewById(R.id.movie_loading);
        movieName = findViewById(R.id.movie_name);
        audience = findViewById(R.id.audience);
        cumulativeAudience = findViewById(R.id.cumulative_audience);
        director = findViewById(R.id.director);
        actor = findViewById(R.id.movie_actor);
        movieTime = findViewById(R.id.movie_time);

        loadStart();
        movieCode = getIntent().getStringExtra("movieCode");

        requestData();

        loadEnd();
    }

    // MARK: Request Data
    private void requestData() {
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
        String value = "?key=22d5c15941cb3125ca046e747b13551c&movieCd=" + movieCode;


        url = url + value;

        Log.d("http: ", url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest myReq = new StringRequest(Request.Method.GET, url, successListener, errorListener);

        requestQueue.add(myReq);
    }

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // 통신을 성공 할 시
            Log.d("RESP", response);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시
            Toast.makeText(MovieActivity.this, "INVALID FORM", Toast.LENGTH_SHORT).show();
        }
    };

    private void setData(String response) {

    }


    // MARK: Loading handler
    private void loadStart() {
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        loading.setVisibility(View.VISIBLE);

        movieName.setVisibility(View.INVISIBLE);
        audience.setVisibility(View.INVISIBLE);
        cumulativeAudience.setVisibility(View.INVISIBLE);
        director.setVisibility(View.INVISIBLE);
        actor.setVisibility(View.INVISIBLE);
        movieTime.setVisibility(View.INVISIBLE);
    }

    private void loadEnd() {
        loading.setVisibility(View.INVISIBLE);

        movieName.setVisibility(View.VISIBLE);
        audience.setVisibility(View.VISIBLE);
        cumulativeAudience.setVisibility(View.VISIBLE);
        director.setVisibility(View.VISIBLE);
        actor.setVisibility(View.VISIBLE);
        movieTime.setVisibility(View.VISIBLE);
    }


}