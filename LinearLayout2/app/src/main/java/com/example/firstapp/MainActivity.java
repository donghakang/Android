package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    /**
     * 핸드폰 연결 하는 방법
     * 1. 본인 컴퓨터 제조사 usb 드라이버 설치
     * 2. 스마트폰의 개발자모드를 on
     * 3. usb 컴퓨터 연결 (데이터 케이블)
     * 4. 연결시 본인 스마트폰에 보안 질문 및 수락
     * **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 처음 앱을 실행 시킬때,
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                      // res/layout/activity_main 안에 있는 것을 실행 시킨다

        Log.d("tag", "onCreate Debug Printer");           // Debug
    }

    /**
    @Override
    protected void onResume() {
        // 일시정지 후 다시 재접속 할때,
        super.onResume();
        Log.d("tag", "onResume Debug Printer");           // Debug
    }

    @Override
    protected void onPause() {
        // 접속 상태를 잠시 일시정지 시킬때 (홈으로 갈때 및 다른 화면으로 넘어갈때)
        super.onPause();
        Log.d("tag", "onPause Debug Printer");           // Debug
    }

    @Override
    protected void onDestroy() {
        // 앱이 종료 되었을때
        super.onDestroy();
        Log.d("tag", "onDestroy Debug Printer");           // Debug
    }
    */
}