package com.example.fragmentvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // VIEW
    public static final int FRAGMENT_A = 0;
    public static final int FRAGMENT_B = 1;
    public static final int FRAGMENT_C = 2;



    DrawerLayout drawer;

    // Menu
    ListView lv;
    ArrayList<String> menuList = new ArrayList<>();



    // Fragment
    VocabFragment vocabFragment = new VocabFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        lv = findViewById(R.id.lv);
        menuList.add("내 정보");
        menuList.add("단어 게임하기");
        menuList.add("설정");

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, menuList);
        lv.setAdapter(adapter);        // ListView list = findViewById(R.id.list);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        drawer.closeDrawer(GravityCompat.START);
        switch (position) {
            case 0:
                Log.d("dd", "내 정보");
                break;
            case 1:
                changeScrVocab();
                Log.d("dd", "단어 게임하기");
                break;
            case 2:
                Log.d("dd", "설정");
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public void changeScrVocab() {
        // finish 를 사용하면 stack 구조가 아닌, 전 화면을 없애는 역할을 해준다.
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // R.id.body_rl is in activity_main.xml. This is where fragment view shows up.
        ft.replace(R.id.body_rl, vocabFragment);
        ft.commit();
    }
}