package com.example.pastblockbuster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    EditText etYY, etMM, etDD;
    Button btnSearch;
    ProgressBar loading;

    ListView movieList;
    MovieAdapter adapter;

    String y, m, d;

    private static ArrayList<Movie> movieData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etYY = findViewById(R.id.et_yy);
        etMM = findViewById(R.id.et_mm);
        etDD = findViewById(R.id.et_dd);

        y = "2020";
        m = "9";
        d = "17";

        etYY.setText(y);
        etMM.setText(m);
        etDD.setText(d);

        btnSearch = findViewById(R.id.btn_search);
        loading = findViewById(R.id.movie_progress);
        movieList = findViewById(R.id.movie_list);

        btnSearch.setOnClickListener(this);

        requestData("2020", "9", "17");
        setupMoiveList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData(y, m, d);
        setupMoiveList();
    }

    // BUTTON CALL
    @Override
    public void onClick(View v) {
        // 년, 월, 일을 가져온다.
        String yy = etYY.getText().toString();
        String mm = etMM.getText().toString();
        String dd = etDD.getText().toString();

        if (yy.equals("") || mm.equals("") || dd.equals("")) {
            Toast.makeText(this, "년, 월, 일을 정확하게 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            requestData(yy, mm, dd);
            clearMovieList();
            setupMoiveList();
        }

    }

    // MARK: http 에서 데이터 가져오기
    private void requestData(String yy, String mm, String dd) {

        loading.setVisibility(View.VISIBLE);

        String dateForm = datePrint(yy, mm, dd);
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=22d5c15941cb3125ca046e747b13551c&targetDt=" + dateForm;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest myReq = new StringRequest(Request.Method.GET, url, successListener, errorListener);

        requestQueue.add(myReq);
    }

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // 통신을 성공 할 시
            readData(response);
            loading.setVisibility(View.INVISIBLE);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시
            Toast.makeText(MainActivity.this, "INVALID FORM", Toast.LENGTH_SHORT).show();

        }
    };


    private void readData(String response) {
        try {
            JSONObject data = new JSONObject(response);
            JSONObject boxOfficeResult = data.optJSONObject("boxOfficeResult");
            JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");

            // 오늘의 블록버스터 저장
            for (int i = 0; i < 10; i++) {
                JSONObject rankedMovie = dailyBoxOfficeList.optJSONObject(i);
                String movieNm = rankedMovie.getString("movieNm");
                String audiAcc = rankedMovie.getString("audiAcc");
                String audiCnt = rankedMovie.getString("audiCnt");
                String openDt = rankedMovie.getString("openDt");
//                String showRange = rankedMovie.getString("showRange");
                String movieCd = rankedMovie.getString("movieCd");
                movieData.add(new Movie(movieNm, openDt, audiAcc, audiCnt, movieCd));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // MARK: 커스텀 list view 에서 데이터 정리
    private void setupMoiveList() {
        adapter = new MovieAdapter(this, movieData);
        movieList.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        movieList.setOnItemClickListener(this);
    }

    private void clearMovieList() {
        movieData.clear();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Intent call.
        Intent intent = new Intent(this, com.example.pastblockbuster.MovieActivity.class);
        Movie selectMovie = movieData.get(position);
        intent.putExtra("movieCode", selectMovie.movieId);
        startActivity(intent);
    }




    private String datePrint(String yy, String mm, String dd) {
        int m = Integer.parseInt(mm);
        int d = Integer.parseInt(dd);

        if (m < 10) {
            mm = "0" + mm;
        }

        if (d < 10) {
            dd = "0" + dd;
        }

        return yy + mm + dd;
    }



}