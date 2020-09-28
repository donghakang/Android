package com.example.loginlist.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.loginlist.Dashboard.Reply;
import com.example.loginlist.Dashboard.ReplyAdapter;
import com.example.loginlist.R;
import com.example.loginlist.control.PersonalInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OpenActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    String title;
    String content;
    String writer;
    int idx;

    TextView detailTitle, detailContent, detailWriter;

    /// - 댓글
    EditText etResponse;
    Button btnSubmitResponse;
    ListView responseList;
    ReplyAdapter adapter;

    ArrayList<Reply> arr = new ArrayList<Reply>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        writer = getIntent().getStringExtra("writer");
        idx = getIntent().getIntExtra("idx", -1);

        detailTitle = findViewById(R.id.tv_detail_title);
        detailContent = findViewById(R.id.tv_detail_content);
        detailWriter = findViewById(R.id.tv_detail_writer);

        etResponse = findViewById(R.id.et_response);
        btnSubmitResponse = findViewById(R.id.btn_submit_response);
        responseList = findViewById(R.id.response_list);

        btnSubmitResponse.setOnClickListener(this);

        setup();
        getResponse();
        showResponseList();
    }

    private void setup() {
        detailTitle.setText(title);
        detailContent.setText(content);
        detailWriter.setText(writer);
    }


    @Override
    public void onClick(View v) {
        // 버튼을 누름으로서, 댓글이 작성된다.
        if (v.getId() == R.id.btn_submit_response) {
            reply();
            etResponse.setText("");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        editReply(position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        deleteReply(position);
        return true;
    }



    private void getResponse() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_reply_list.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListenerReply, errorListenerReply) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx", idx + "");
                params.put("token", PersonalInfo.getToken());

                return params;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq);
    }




    Response.Listener<String> successListenerReply = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject j = new JSONObject(response);
                if (j.optString("result").equals("OK")) {
                    // 로그인 성공
                    Log.d("reply", response);
                    JSONArray replyList = new JSONArray(j.optString("list"));
                    for (int i = 0; i < replyList.length(); i ++) {
                        JSONObject data = replyList.optJSONObject(i);
                        int idx = data.optInt("idx");
                        String content = data.optString("content");
                        String writer = data.optString("writer");
                        String mine = data.optString("mine");
                        String wdate = data.optString("wdate");

                        Reply r = new Reply(idx, content, writer, mine, wdate);

                        arr.add(r);

                        showResponseList();
                    }
                } else {
                    Toast.makeText(OpenActivity.this, "수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    Response.ErrorListener errorListenerReply = new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시
            Toast.makeText(OpenActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };


    /// - arr를 이용하여 List를 보여준다.
    private void showResponseList() {
        adapter = new ReplyAdapter(this, arr);
        responseList.setAdapter(adapter);

        adapter.notifyDataSetChanged();;
        responseList.setOnItemClickListener(this);
        responseList.setOnItemLongClickListener(this);
    }




    /// - 댓글 작성
    private void reply() {
        final String reply = etResponse.getText().toString();
        if (reply.equals("")) {
            Toast.makeText(this, "댓글을 입력하세요!", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue stringRequest = Volley.newRequestQueue(this);
            String url = "http://heutwo.dothome.co.kr/temp/test_reply_insert.php";

            StringRequest myReq = new StringRequest(Request.Method.POST, url,
                    successListenerSubmit, errorListenerSubmit) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("idx", idx + "");
                    params.put("token", PersonalInfo.getToken());
                    params.put("content", reply);

                    return params;
                }
            };

            myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
            );
            stringRequest.add(myReq);



        }
    }

    Response.Listener<String> successListenerSubmit = new com.android.volley.Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject j = new JSONObject(response);
                if (j.optString("result").equals("OK")) {
                    // 댓글 입력 성공
                    Toast.makeText(OpenActivity.this, "등록 되었습니다.", Toast.LENGTH_SHORT).show();

                    arr.clear();
                    getResponse();
                    showResponseList();

                } else {
                    Toast.makeText(OpenActivity.this, "등록이 안되었습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    Response.ErrorListener errorListenerSubmit = new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시
            Toast.makeText(OpenActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };









    /// - Delete Reply
    private void deleteReply(final int position) {
        final Reply rep = arr.get(position);

        if (rep.writer.equals(PersonalInfo.getWriter())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("삭제하시겠습니까 ?");
            builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteItem(position);
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create().show();
        }
    }

    private void deleteItem(final int position) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_reply_delete.php";
        Log.d("dddddd", idx + "     " + arr.get(position).idx);
        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListenerDelete, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx", arr.get(position).idx + "");
                params.put("board_idx", idx + "");
                params.put("token", PersonalInfo.getToken());

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
                    // 댓글 입력 성공
                    Toast.makeText(OpenActivity.this, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();

                    arr.clear();
                    getResponse();
                    showResponseList();

                } else {
                    Toast.makeText(OpenActivity.this, "등록이 안되었습니다.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(OpenActivity.this, "통신이 불가능 합니다.", Toast.LENGTH_SHORT).show();
        }
    };



    private void editReply(int position) {
        final Reply rep = arr.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("수정 하세요");
        LayoutInflater lnf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = lnf.inflate(R.layout.edit_reply, null, false);

        TextView tvReplier = v.findViewById(R.id.tv_replier_edit);
        final EditText etReply = v.findViewById(R.id.et_reply);
        tvReplier.setText(rep.writer);
        etReply.setText(rep.content);

        builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 수정을 시작한다.
                String newReply = etReply.getText().toString();
                if (newReply.equals("")) {
                    Toast.makeText(OpenActivity.this, "수정할 글을 넣으세요", Toast.LENGTH_SHORT).show();
                } else {
                    edit(rep, newReply);
                }
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 아무일 일어나지 않는다.
            }
        });

        builder.setView(v);
        builder.create().show();
    }



    /// - 수정
    private void edit(final Reply rep, final String newReply) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/temp/test_reply_update.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListenerEdit, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx", rep.idx + "");
                params.put("content", newReply);
                params.put("board_idx", idx + "");
                params.put("token", PersonalInfo.getToken());

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
                    // 댓글 입력 성공
                    Toast.makeText(OpenActivity.this, "수정 되었습니다.", Toast.LENGTH_SHORT).show();

                    arr.clear();
                    getResponse();
                    showResponseList();

                } else {
                    Toast.makeText(OpenActivity.this, "등록이 안되었습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}