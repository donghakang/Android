package com.example.customlistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /**
     * 1. item을 위한 레이아웃을 만든다
     * 2. item 을 위한 클래스
     * 3. item 을 위한 데이터 클래스 만들기
     * 3. list view 만들기
     * 4. adapter 클래스 만들기
     */

    ListView lv;
    ArrayList<ItemData> arr = new ArrayList<>();

    class ItemHolder {
        TextView engTvHolder;
        TextView korTvHolder;
        TextView levTvHolder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    class MyAdapter extends ArrayAdapter {



    }
}