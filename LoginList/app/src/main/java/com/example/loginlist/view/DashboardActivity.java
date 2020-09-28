package com.example.loginlist.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.example.loginlist.Dashboard.Dashboard;
import com.example.loginlist.Dashboard.DashboardAdapter;
import com.example.loginlist.R;
import com.example.loginlist.control.PersonalInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

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

                    idxCount = dashList.length();

                    for (int i = 0; i < dashList.length(); i ++) {
                        JSONObject dash = dashList.optJSONObject(i);
                        int idx = dash.optInt("idx");

                        String title = dash.optString("title");
                        String content = dash.optString("content");
                        String wdate = dash.optString("wdate");
                        String writer = dash.optString("writer");
                        String count_reply = dash.optString("count_reply");
//                        String currToken = dash.optString("token");
                        Dashboard myContent = new Dashboard(idx, title, content, wdate, writer, count_reply);
                        board.add(myContent);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    // 데이터가 없음.
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
        dashboardList.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("idx", idxCount);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(DashboardActivity.this, OpenActivity.class);
        Dashboard detail = board.get(position);
        intent.putExtra("idx", detail.idx);
        intent.putExtra("title", detail.title);
        intent.putExtra("content", detail.content);
        intent.putExtra("writer", detail.writer);
        startActivity(intent);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Dashboard detail = board.get(position);

        if (detail.writer.equals(PersonalInfo.getWriter())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("수정하시겠습니까 ?");
            builder.setPositiveButton("수정/삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editAlert(detail);
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create().show();
        }
        return true;
    }


    void editAlert(final Dashboard dashboard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater lnf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = lnf.inflate(R.layout.edit_alert, null, false);


        final EditText etTitle = v.findViewById(R.id.et_detail_title);
        final EditText etContent = v.findViewById(R.id.et_detail_content);
        TextView btnDelete = v.findViewById(R.id.btn_delete);

        etTitle.setText(dashboard.title);
        etContent.setText(dashboard.content);

        builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 수정을 시작한다.
                edit(dashboard, etTitle.getText().toString(), etContent.getText().toString());
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 아무일 일어나지 않는다.
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert(dashboard);
            }
        });


        builder.setView(v);
        builder.create().show();
    }


    private void deleteAlert(final Dashboard dashboard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("정말로 삭제 하시겠습니까?");
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editAlert(dashboard);
            }
        });

        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 삭제 시작.
                delete(dashboard);
            }
        });

        builder.create().show();
    }



    /// - 수정
    private void edit(final Dashboard dashboard, final String title, final String content) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_board_update.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListenerEdit, errorListenerEdit) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx", dashboard.idx + "");
                params.put("token", DashboardActivity.this.token);
                params.put("title", title);
                params.put("content", content);

                return params;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq);
    }

    Response.Listener<String> successListenerEdit = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject j = new JSONObject(response);
                if (j.optString("result").equals("OK")) {
                    // 로그인 성공
                    toast("수정이 완료 되었습니다.");
                } else {
                    toast("수정에 실패했습니다.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    Response.ErrorListener errorListenerEdit = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시
            Toast.makeText(DashboardActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };




    /// - 삭제
    private void delete(final Dashboard dashboard) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_board_delete.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListenerDelete, errorListenerDelete) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx", dashboard.idx + "");
                params.put("token", DashboardActivity.this.token);

                return params;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq);
    }


    Response.Listener<String> successListenerDelete = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject j = new JSONObject(response);
                if (j.optString("result").equals("OK")) {
                    // 로그인 성공
                    toast("삭제가 완료 되었습니다.");
//                    adapter.notifyDataSetChanged();
                } else {
                    toast("삭제에 실패했습니다.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    Response.ErrorListener errorListenerDelete = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시
            Toast.makeText(DashboardActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };






    private void toast(String txt) {
        Toast.makeText(DashboardActivity.this, txt, Toast.LENGTH_SHORT).show();
    }
}
