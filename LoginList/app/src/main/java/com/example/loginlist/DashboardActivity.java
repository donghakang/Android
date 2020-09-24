package com.example.loginlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginlist.Dashboard.Dashboard;
import com.example.loginlist.Dashboard.DashboardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ListView dashboardList;
    Button btnAdd;

    boolean isDashboard = false;
    ArrayList<Dashboard> board;
    private String token;

    DashboardAdapter adapter;

    int idxCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        board = new ArrayList<>();

        this.token = getIntent().getStringExtra("token");

        dashboardList = findViewById(R.id.dashboard_list);
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        request(this.token);
        setup();
    }

    private void request(final String token) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_board_list.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx", "-1");
                params.put("token", token);

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
                    // 리스트를 가져옵니다.
                    JSONArray dashList = new JSONArray(j.optString("list"));
                    Log.d("dddddd", dashList.toString());
                    idxCount = dashList.length();

                    for (int i = 0; i < dashList.length(); i ++) {
                        JSONObject dash = dashList.optJSONObject(i);
                        int idx = dash.optInt("idx");
                        String title = dash.optString("title");
                        String content = dash.optString("content");
                        String wdate = dash.optString("wdate");
                        String writer = dash.optString("writer");
                        Dashboard myContent = new Dashboard(idx, DashboardActivity.this.token, title, content, wdate, writer);
                        board.add(myContent);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    // 데이터가 없음.
                    Log.d("dddddddd", "데이터가 없음 ?");
                    isDashboard = false;
                    Toast.makeText(DashboardActivity.this, "첫번째 글을 등록하세요!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(DashboardActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };



    /// - setup custom list
    private void setup(){
        board.clear();
        
        adapter = new DashboardAdapter(this, board);
        dashboardList.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        dashboardList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, com.example.loginlist.AddActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("idx", idxCount);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(DashboardActivity.this, com.example.loginlist.OpenActivity.class);
        Dashboard detail = board.get(position);
        intent.putExtra("title", detail.title);
        intent.putExtra("content", detail.content);
        intent.putExtra("writer", detail.writer);
        startActivity(intent);
    }
}