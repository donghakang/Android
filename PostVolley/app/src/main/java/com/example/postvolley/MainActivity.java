package com.example.postvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    private void request() {
        // post 방식
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://gvc.woobi.co.kr/test/post_test.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", "kkkaaabbb");
                params.put("passwd", "1234");

                return params;
            }
        };

        // 용량 자유로움, 빠르다.
        // Get은
        /**
        $id=$_POST['id'];
         $pass = $_GET{'passwd');
         cho $id."<br"r>";
         echo $pass
        **/


        // 3초 뒤에 까지 통신이 없으면 ...
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)

        );
        stringRequest.add(myReq);

    }

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // 통신을 성공 할 시
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // 통신을 실패할 시

        }
    };

}